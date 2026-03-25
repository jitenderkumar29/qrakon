package com.example.qrakon.components.homescreen

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.qrakon.ui.theme.customColors
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.collections.isNotEmpty
import  com.example.qrakon.R
import kotlin.collections.isNotEmpty

enum class DotPosition {
    OVERLAY,      // Dots overlay on bottom of image
    BELOW_IMAGE,  // Dots below the image
    NONE          // No dots indicator
}

data class BannerPadding(
    val start: Dp = 0.dp,
    val top: Dp = 0.dp,
    val end: Dp = 0.dp,
    val bottom: Dp = 0.dp,
) {
    companion object {
        val Zero = BannerPadding(0.dp, 0.dp, 0.dp, 0.dp)

        fun all(padding: Dp) = BannerPadding(padding, padding, padding, padding)

        fun horizontal(horizontal: Dp) = BannerPadding(start = horizontal, end = horizontal)

        fun vertical(vertical: Dp) = BannerPadding(top = vertical, bottom = vertical)

        fun symmetric(horizontal: Dp = 0.dp, vertical: Dp = 0.dp) =
            BannerPadding(start = horizontal, top = vertical, end = horizontal, bottom = vertical)
    }
}

@Composable
fun BannerFood(
    images: List<Painter>,
    onImageClick: (Int) -> Unit = {},
    modifier: Modifier = Modifier,
    height: Dp = 270.dp,
    roundedCornerShape: Dp = 16.dp,
    dotSize: Dp? = null,
    dotPadding: Dp? = null,
    selectedDotColor: Color = MaterialTheme.customColors.linkColor,
    unselectedDotColor: Color = Color.White.copy(alpha = 0.9f),
    backgroundColor: Color = Color.White,
    contentScale: ContentScale = ContentScale.FillBounds,
    autoScrollDelay: Long = 5000,
    autoScrollEnabled: Boolean = true,
    dotPosition: DotPosition = DotPosition.BELOW_IMAGE,
    overlayGradient: Boolean = true,
    showDots: Boolean = true, // New parameter to control dots visibility
    padding: BannerPadding = BannerPadding.Zero // New padding parameter
) {
    require(images.isNotEmpty()) { "Images list cannot be empty" }

    val pagerState = rememberPagerState(
        pageCount = { images.size },
        initialPage = 0
    )
    val coroutineScope = rememberCoroutineScope()

    val bannerShape = RoundedCornerShape(roundedCornerShape)

    // Auto-scroll effect
    LaunchedEffect(autoScrollEnabled, showDots) {
        if (!autoScrollEnabled) return@LaunchedEffect

        while (true) {
            delay(autoScrollDelay)
            val nextPage = (pagerState.currentPage + 1) % images.size
            pagerState.animateScrollToPage(nextPage)
        }
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(backgroundColor)
            .padding(
                start = padding.start,
                top = padding.top,
                end = padding.end,
                bottom = padding.bottom
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(height)
        ) {
            // Pager with images and rounded corners
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(bannerShape)
            ) { page ->
                Image(
                    painter = images[page],
                    contentDescription = "Banner ${page + 1}",
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(bannerShape)
                        .clickable { onImageClick(page) },
                    contentScale = contentScale
                )
            }

            // Overlay dots - shown only when dotPosition is OVERLAY and showDots is true
            if (dotPosition == DotPosition.OVERLAY && showDots && (dotSize == null || dotSize > 0.dp)) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .then(
                            if (overlayGradient) {
                                Modifier.drawWithContent {
                                    drawContent()
                                    // Draw gradient overlay for better dot visibility
                                    drawRect(
                                        brush = Brush.verticalGradient(
                                            colors = listOf(
                                                Color.Transparent,
                                                Color.Black.copy(alpha = 0.3f)
                                            ),
                                            startY = size.height * 0.6f,
                                            endY = size.height
                                        )
                                    )
                                }
                            } else {
                                Modifier
                            }
                        ),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    OverlayDotsFashion(
                        pagerState = pagerState,
                        imageCount = images.size,
                        dotSize = dotSize,
                        dotPadding = dotPadding,
                        selectedDotColor = Color.White,
                        unselectedDotColor = Color.White.copy(alpha = 0.9f),
                        indicatorDuration = autoScrollDelay,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )
                }
            }
        }

        // Below-image dots - shown only when dotPosition is BELOW_IMAGE and showDots is true
        if (dotPosition == DotPosition.BELOW_IMAGE && showDots && (dotSize == null || dotSize > 0.dp)) {
            Column {
                Spacer(
                    modifier = Modifier.height(8.dp)
                        .fillMaxWidth()
                        .background(backgroundColor)
                )

                OverlayDotsFashion(
                    pagerState = pagerState,
                    imageCount = images.size,
                    dotSize = dotSize,
                    dotPadding = dotPadding,
                    selectedDotColor = MaterialTheme.customColors.onPrimaryContainer,
                    unselectedDotColor = Color.Gray,
                    indicatorDuration = autoScrollDelay
                )

                Spacer(
                    modifier = Modifier.height(3.dp)
                        .fillMaxWidth()
                        .background(backgroundColor)
                )
            }
        }

        // No dots shown when showDots is false or dotPosition is NONE
    }
}

// Overloaded version with showDots parameter (for backward compatibility)
@Composable
fun BannerFood(
    images: List<Painter>,
    onImageClick: (Int) -> Unit = {},
    modifier: Modifier = Modifier,
    height: Dp = 270.dp,
    roundedCornerShape: Dp = 16.dp,
    dotSize: Dp? = null,
    dotPadding: Dp? = null,
    selectedDotColor: Color = MaterialTheme.customColors.linkColor,
    unselectedDotColor: Color = Color.White.copy(alpha = 0.9f),
    backgroundColor: Color = Color.White,
    contentScale: ContentScale = ContentScale.FillBounds,
    autoScrollDelay: Long = 5000,
    autoScrollEnabled: Boolean = true,
    dotPosition: DotPosition = DotPosition.BELOW_IMAGE,
    overlayGradient: Boolean = true
) {
    BannerFood(
        images = images,
        onImageClick = onImageClick,
        modifier = modifier,
        height = height,
        roundedCornerShape = roundedCornerShape,
        dotSize = dotSize,
        dotPadding = dotPadding,
        selectedDotColor = selectedDotColor,
        unselectedDotColor = unselectedDotColor,
        backgroundColor = backgroundColor,
        contentScale = contentScale,
        autoScrollDelay = autoScrollDelay,
        autoScrollEnabled = autoScrollEnabled,
        dotPosition = dotPosition,
        overlayGradient = overlayGradient,
        showDots = true, // Default to showing dots
        padding = BannerPadding.Zero // Default to no padding
    )
}

// New overload with padding but without showDots parameter
@Composable
fun BannerFood(
    images: List<Painter>,
    onImageClick: (Int) -> Unit = {},
    modifier: Modifier = Modifier,
    height: Dp = 270.dp,
    roundedCornerShape: Dp = 16.dp,
    dotSize: Dp? = null,
    dotPadding: Dp? = null,
    selectedDotColor: Color = MaterialTheme.customColors.linkColor,
    unselectedDotColor: Color = Color.White.copy(alpha = 0.9f),
    backgroundColor: Color = Color.White,
    contentScale: ContentScale = ContentScale.FillBounds,
    autoScrollDelay: Long = 5000,
    autoScrollEnabled: Boolean = true,
    dotPosition: DotPosition = DotPosition.BELOW_IMAGE,
    overlayGradient: Boolean = true,
    padding: BannerPadding // New overload with padding parameter
) {
    BannerFood(
        images = images,
        onImageClick = onImageClick,
        modifier = modifier,
        height = height,
        roundedCornerShape = roundedCornerShape,
        dotSize = dotSize,
        dotPadding = dotPadding,
        selectedDotColor = selectedDotColor,
        unselectedDotColor = unselectedDotColor,
        backgroundColor = backgroundColor,
        contentScale = contentScale,
        autoScrollDelay = autoScrollDelay,
        autoScrollEnabled = autoScrollEnabled,
        dotPosition = dotPosition,
        overlayGradient = overlayGradient,
        showDots = true, // Default to showing dots
        padding = padding // Use provided padding
    )
}

// Enhanced Dot indicator with modifier support
@Composable
fun OverlayDotsFashion(
    pagerState: PagerState,
    imageCount: Int,
    dotSize: Dp? = null,
    dotPadding: Dp? = null,
    selectedDotColor: Color = Color.White,
    unselectedDotColor: Color = Color.Gray,
    indicatorDuration: Long = 3000L,
    modifier: Modifier = Modifier
) {
    val coroutineScope = rememberCoroutineScope()

    val defaultDotSize = 8.dp
    val defaultDotPadding = 6.dp

    val actualDotSize = dotSize ?: defaultDotSize
    val actualDotPadding = dotPadding ?: defaultDotPadding

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 1.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.padding(bottom = 1.dp)
        ) {
            repeat(imageCount) { index ->
                val isSelected = pagerState.currentPage == index
                val isHighlighted = index <= pagerState.currentPage

                val progress = remember { Animatable(0f) }
                if (isSelected) {
                    LaunchedEffect(pagerState.currentPage) {
                        progress.snapTo(0f)
                        progress.animateTo(
                            targetValue = 1f,
                            animationSpec = tween(
                                durationMillis = indicatorDuration.toInt(),
                                easing = LinearEasing
                            )
                        )
                    }
                } else {
                    LaunchedEffect(pagerState.currentPage) {
                        progress.snapTo(0f)
                    }
                }

                val targetSize = if (isSelected) actualDotSize * 1.2f else actualDotSize
                val animatedSize = animateDpAsState(targetValue = targetSize)

                Box(
                    modifier = Modifier
                        .padding(horizontal = actualDotPadding)
                        .size(animatedSize.value)
                        .background(
                            color = if (isHighlighted) selectedDotColor else unselectedDotColor,
                            shape = CircleShape
                        )
                        .clickable {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        }
                )
            }
        }
    }
}

// New composable for banner without dots (simplified API)
@Composable
fun BannerFoodWithoutDots(
    images: List<Painter>,
    onImageClick: (Int) -> Unit = {},
    modifier: Modifier = Modifier,
    height: Dp = 270.dp,
    roundedCornerShape: Dp = 16.dp,
    backgroundColor: Color = Color.White,
    contentScale: ContentScale = ContentScale.FillBounds,
    autoScrollDelay: Long = 5000,
    autoScrollEnabled: Boolean = true,
    padding: BannerPadding = BannerPadding.Zero // Added padding parameter
) {
    BannerFood(
        images = images,
        onImageClick = onImageClick,
        modifier = modifier,
        height = height,
        roundedCornerShape = roundedCornerShape,
        dotSize = 0.dp, // Set dotSize to 0 to hide dots
        backgroundColor = backgroundColor,
        contentScale = contentScale,
        autoScrollDelay = autoScrollDelay,
        autoScrollEnabled = autoScrollEnabled,
        dotPosition = DotPosition.NONE,
        overlayGradient = false,
        showDots = false,
        padding = padding // Pass padding
    )
}

// Preview function to demonstrate both versions
@Composable
fun BannerFoodPreview() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray)
            .padding(16.dp)
    ) {
        // 1. Banner with all-around padding
        Text("Banner with All-Around Padding", modifier = Modifier.padding(bottom = 8.dp))
        BannerFood(
            images = listOf(
                painterResource(R.drawable.restaurant_1),
                painterResource(R.drawable.popular_chain_1),
                painterResource(R.drawable.popular_chain_2)
            ),
            height = 150.dp,
            autoScrollEnabled = false,
            padding = BannerPadding.all(8.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // 2. Banner with horizontal padding only
        Text("Banner with Horizontal Padding", modifier = Modifier.padding(bottom = 8.dp))
        BannerFood(
            images = listOf(
                painterResource(R.drawable.popular_chain_3),
                painterResource(R.drawable.popular_chain_4)
            ),
            height = 140.dp,
            showDots = false,
            autoScrollEnabled = false,
            padding = BannerPadding.horizontal(16.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // 3. Banner with symmetric padding
        Text("Banner with Symmetric Padding", modifier = Modifier.padding(bottom = 8.dp))
        BannerFood(
            images = listOf(
                painterResource(R.drawable.popular_chain_5),
                painterResource(R.drawable.popular_chain_6)
            ),
            height = 130.dp,
            autoScrollEnabled = false,
            padding = BannerPadding.symmetric(horizontal = 24.dp, vertical = 8.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // 4. Banner with custom padding
        Text("Banner with Custom Padding", modifier = Modifier.padding(bottom = 8.dp))
        BannerFoodWithoutDots(
            images = listOf(
                painterResource(R.drawable.restaurant_1),
                painterResource(R.drawable.popular_chain_1)
            ),
            height = 120.dp,
            autoScrollEnabled = false,
            padding = BannerPadding(start = 32.dp, top = 4.dp, end = 32.dp, bottom = 4.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // 5. Banner with no padding (default)
        Text("Banner with No Padding (Default)", modifier = Modifier.padding(bottom = 8.dp))
        BannerFood(
            images = listOf(
                painterResource(R.drawable.popular_chain_2),
                painterResource(R.drawable.popular_chain_3)
            ),
            height = 110.dp,
            autoScrollEnabled = false
        )
    }
}

// Example usage in your app:
@Composable
fun ExampleUsageBanner() {
    // Example 1: With all-around padding
    BannerFood(
        images = listOf(/* your images */),
        padding = BannerPadding.all(16.dp)
    )

    // Example 2: With horizontal padding only
    BannerFood(
        images = listOf(/* your images */),
        padding = BannerPadding.horizontal(24.dp)
    )

    // Example 3: With symmetric padding
    BannerFood(
        images = listOf(/* your images */),
        padding = BannerPadding.symmetric(horizontal = 16.dp, vertical = 8.dp)
    )

    // Example 4: With custom padding
    BannerFood(
        images = listOf(/* your images */),
        padding = BannerPadding(start = 20.dp, top = 10.dp, end = 20.dp, bottom = 10.dp)
    )

    // Example 5: Without dots with padding
    BannerFoodWithoutDots(
        images = listOf(/* your images */),
        padding = BannerPadding.all(12.dp)
    )
}