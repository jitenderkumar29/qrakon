// File: com.example.qrakon.components.homescreen.BannerPortrait.kt
package com.example.qrakon.components.homescreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlin.math.absoluteValue

// Data class to hold image and its associated background color
data class BannerItem(
    val imageRes: Int,
    val backgroundColor: Color
)

@Composable
fun BannerPortrait(
    items: List<BannerItem>, // List of banner items with images and background colors
    onImageClick: (Int) -> Unit, // Click handler passes the image resource ID
    modifier: Modifier = Modifier,
    height: Dp = 260.dp,
    roundedCornerShape: Dp = 12.dp,
    contentScale: ContentScale = ContentScale.Crop,
    autoScrollDelay: Long = 5000,
    autoScrollEnabled: Boolean = true,
    placeholderImage: Int? = null, // Optional placeholder image resource
    onBackgroundColorChange: ((Color) -> Unit)? = null // Callback for background color changes
) {
    require(items.isNotEmpty()) { "Items list cannot be empty" }

    val actualCount = items.size
    val infiniteCount = Int.MAX_VALUE
    val startPage = infiniteCount / 2 - ((infiniteCount / 2) % actualCount)

    val pagerState = rememberPagerState(
        pageCount = { infiniteCount },
        initialPage = startPage
    )

    // Track the current target page (including during swipe)
    val currentPage by remember { derivedStateOf { pagerState.currentPage } }
    val currentPageOffset by remember { derivedStateOf { pagerState.currentPageOffsetFraction } }

    // Determine which page is most visible (for background color)
    val targetPage = remember(currentPage, currentPageOffset) {
        if (currentPageOffset > 0.5f) {
            currentPage + 1
        } else if (currentPageOffset < -0.5f) {
            currentPage - 1
        } else {
            currentPage
        }
    }

    // Update background color when the most visible page changes
    LaunchedEffect(targetPage) {
        val realIndex = ((targetPage % actualCount) + actualCount) % actualCount // Handle negative indices
        val currentItem = items[realIndex]

        // Update the global background color
        BannerBackgroundManager.updateBackgroundColor(currentItem.backgroundColor)

        // Also call the callback if provided
        onBackgroundColorChange?.invoke(currentItem.backgroundColor)
    }

    // Also update when page finally settles (backup)
    LaunchedEffect(pagerState.currentPage) {
        val realIndex = pagerState.currentPage % actualCount
        val currentItem = items[realIndex]

        // Update the global background color
        BannerBackgroundManager.updateBackgroundColor(currentItem.backgroundColor)

        // Also call the callback if provided
        onBackgroundColorChange?.invoke(currentItem.backgroundColor)
    }

    // AutoScroll
    LaunchedEffect(autoScrollEnabled) {
        if (!autoScrollEnabled) return@LaunchedEffect

        while (true) {
            delay(autoScrollDelay)
            pagerState.animateScrollToPage(pagerState.currentPage + 1)
        }
    }

    val shape = RoundedCornerShape(roundedCornerShape)
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp

    // Calculate widths: 70% for current, 15% peeking for previous/next
    val currentWidth = screenWidth * 0.80f
    val peekWidth = screenWidth * 0.10f

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(height)
//            .background(MaterialTheme.colorScheme.background)
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = peekWidth),
            pageSpacing = 0.dp
        ) { page ->
            val realIndex = page % actualCount
            val item = items[realIndex]

            // Calculate the scale factor based on position
            val pageOffset = ((pagerState.currentPage - page) + pagerState.currentPageOffsetFraction).absoluteValue

            // Current item gets full width, others are scaled down
            val scale = if (pageOffset < 0.5f) 1f else 0.9f

            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(currentWidth)
                    .graphicsLayer {
                        scaleX = scale
                        scaleY = scale
                        alpha = if (pageOffset < 0.5f) 1f else 0.8f
                    }
                    .clip(shape)
                    .clickable { onImageClick(item.imageRes) }
                    .shadow(elevation = 4.dp, shape = shape)
            ) {
                // Display the image
                Image(
                    painter = painterResource(id = item.imageRes),
                    contentDescription = "Banner image ${realIndex + 1}",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.FillBounds
                )

                // Optional: Show placeholder while loading
                if (placeholderImage != null) {
                    // You could show a placeholder here
                }
            }
        }
    }
}

// Simplified version with default background colors
@Composable
fun BannerPortraitSimple(
    imageResList: List<Int>,
    backgroundColors: List<Color> = emptyList(),
    modifier: Modifier = Modifier,
    height: Dp = 260.dp,
    roundedCornerShape: Dp = 12.dp,
    contentScale: ContentScale = ContentScale.Crop,
    autoScrollDelay: Long = 5000,
    autoScrollEnabled: Boolean = true,
    onBackgroundColorChange: ((Color) -> Unit)? = null
) {
    val items = if (backgroundColors.isNotEmpty() && backgroundColors.size == imageResList.size) {
        imageResList.mapIndexed { index, imageRes ->
            BannerItem(imageRes, backgroundColors[index])
        }
    } else {
        // Generate default colors if not provided
        imageResList.mapIndexed { index, imageRes ->
            BannerItem(
                imageRes,
                when (index % 5) {
                    0 -> Color(0xFF2196F3) // Blue
                    1 -> Color(0xFF4CAF50) // Green
                    2 -> Color(0xFFFF9800) // Orange
                    3 -> Color(0xFF9C27B0) // Purple
                    else -> Color(0xFFF44336) // Red
                }
            )
        }
    }

    BannerPortrait(
        items = items,
        onImageClick = {},
        modifier = modifier,
        height = height,
        roundedCornerShape = roundedCornerShape,
        contentScale = contentScale,
        autoScrollDelay = autoScrollDelay,
        autoScrollEnabled = autoScrollEnabled,
        onBackgroundColorChange = onBackgroundColorChange
    )
}