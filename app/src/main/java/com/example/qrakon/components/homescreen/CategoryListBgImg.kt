package com.example.qrakon.components.homescreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import com.example.qrakon.ui.theme.customColors
import kotlin.collections.any
import kotlin.ranges.coerceIn
import kotlin.text.ifEmpty
import kotlin.text.isNotEmpty
import kotlin.text.isNullOrEmpty

/* -------------------- DATA MODEL -------------------- */

data class CategoryItemBgImg(
    val id: Int,
    val name: String,
    val imageRes: Int,
    val subtitle: String? = null
)

/* -------------------- MAIN COMPONENT -------------------- */

@Composable
fun CategoryListBgImg(
    backgroundImageRes: Int,
    items: List<CategoryItemBgImg>,
    onItemClick: (CategoryItemBgImg) -> Unit,
    modifier: Modifier = Modifier,
    backgroundImageHeight: Dp = 200.dp,
    listItemWidth: Dp = 140.dp,
    listItemHeight: Dp = 180.dp,
    overlayItemSize: Dp = 72.dp,
    /* NEW PARAMETER FOR INDEPENDENT HEIGHT CONTROL */
    overlayItemHeight: Dp? = null, // If null, uses overlayItemSize for both width and height
    overlayTextSize: TextUnit = 12.sp,
    backgroundOverlay: Color = Color.Black.copy(alpha = 0.3f),
    title: String? = null,
    showHorizontalList: Boolean = false,
    /* POSITIONING PARAMETERS */
    overlayBottomPosition: OverlayPosition = OverlayPosition.Half,
    overlayBottomOffset: Dp = 0.dp,
    /* DYNAMIC SPACING PARAMETERS */
    overlayItemsSpacing: DynamicSpacing = DynamicSpacing.Fixed(28.dp),
    listItemsSpacing: DynamicSpacing = DynamicSpacing.Fixed(12.dp)
) {
    // Use provided height or fall back to size for square shape
    val actualOverlayHeight = overlayItemHeight ?: overlayItemSize
    val actualOverlayWidth = overlayItemSize

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.customColors.white)
    ) {

        /* -------- BACKGROUND IMAGE WITH OVERLAY ITEMS -------- */

        BoxWithConstraints(
            modifier = Modifier
                .fillMaxWidth()
                .height(backgroundImageHeight)
        ) {
            val boxHeight = maxHeight
            val boxWidth = maxWidth

            Image(
                painter = painterResource(backgroundImageRes),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillBounds
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Transparent)
            )

            // Container for overlay items
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(when (overlayBottomPosition) {
                        is OverlayPosition.Pixels -> overlayBottomPosition.height
                        is OverlayPosition.Percentage -> boxHeight * overlayBottomPosition.percentage
                        OverlayPosition.Half -> boxHeight / 2
                        OverlayPosition.WrapContent -> calculateWrapContentHeight(items, actualOverlayHeight)
                    })
                    .align(Alignment.BottomCenter)
                    .offset(y = overlayBottomOffset)
            ) {
                // Calculate dynamic spacing for overlay items
                val overlaySpacing = calculateDynamicSpacing(
                    spacing = overlayItemsSpacing,
                    screenWidth = boxWidth,
                    itemCount = items.size,
                    itemWidth = if (items.any { it.name.isNotEmpty() }) 100.dp else actualOverlayWidth,
                    defaultSpacing = 28.dp
                )

                // Scrollable overlay items with dynamic spacing and independent height
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .padding(horizontal = 16.dp, vertical = 0.dp),
                    horizontalArrangement = when (overlaySpacing) {
                        is CalculatedSpacing.Fixed -> Arrangement.spacedBy(overlaySpacing.value)
                        is CalculatedSpacing.SpaceBetween -> Arrangement.SpaceBetween
                        is CalculatedSpacing.SpaceAround -> Arrangement.SpaceAround
                        is CalculatedSpacing.SpaceEvenly -> Arrangement.SpaceEvenly
                        is CalculatedSpacing.Start -> Arrangement.Start
                        is CalculatedSpacing.End -> Arrangement.End
                        is CalculatedSpacing.Center -> Arrangement.Center
                    },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    items(items) { item ->
                        OverlayItemOnBackground(
                            item = item,
                            width = actualOverlayWidth,
                            height = actualOverlayHeight, // Pass independent height
                            textSize = overlayTextSize,
                            onClick = { onItemClick(item) }
                        )
                    }
                }
            }
        }

        /* -------- OPTIONAL TITLE -------- */

        if (!title.isNullOrEmpty()) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(16.dp)
            )
        }

        /* -------- CONDITIONAL HORIZONTAL SCROLLABLE LIST -------- */

        if (showHorizontalList) {
            BoxWithConstraints(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {
                val boxWidth = maxWidth

                // Calculate dynamic spacing for list items
                val listSpacing = calculateDynamicSpacing(
                    spacing = listItemsSpacing,
                    screenWidth = boxWidth,
                    itemCount = items.size,
                    itemWidth = listItemWidth,
                    defaultSpacing = 12.dp
                )

                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = when (listSpacing) {
                        is CalculatedSpacing.Fixed -> Arrangement.spacedBy(listSpacing.value)
                        is CalculatedSpacing.SpaceBetween -> Arrangement.SpaceBetween
                        is CalculatedSpacing.SpaceAround -> Arrangement.SpaceAround
                        is CalculatedSpacing.SpaceEvenly -> Arrangement.SpaceEvenly
                        is CalculatedSpacing.Start -> Arrangement.Start
                        is CalculatedSpacing.End -> Arrangement.End
                        is CalculatedSpacing.Center -> Arrangement.Center
                    }
                ) {
                    items(items) { item ->
                        CategoryListItemBgImg(
                            item = item,
                            width = listItemWidth,
                            height = listItemHeight,
                            onClick = { onItemClick(item) }
                        )
                    }
                }
            }
        }
    }
}

/* -------------------- SPACING HELPERS -------------------- */

/**
 * Sealed class for dynamic spacing options
 */
sealed class DynamicSpacing {
    data class Fixed(val value: Dp) : DynamicSpacing()
    data class Responsive(
        val minSpacing: Dp = 8.dp,
        val maxSpacing: Dp = 32.dp,
        val preferredItemCount: Int = 4
    ) : DynamicSpacing()
    object SpaceBetween : DynamicSpacing()
    object SpaceAround : DynamicSpacing()
    object SpaceEvenly : DynamicSpacing()
    object Start : DynamicSpacing()
    object End : DynamicSpacing()
    object Center : DynamicSpacing()

    companion object {
        fun fixed(value: Dp) = Fixed(value)
        fun responsive(minSpacing: Dp = 8.dp, maxSpacing: Dp = 32.dp, preferredItemCount: Int = 4) =
            Responsive(minSpacing, maxSpacing, preferredItemCount)
    }
}

/**
 * Internal sealed class for calculated spacing
 */
private sealed class CalculatedSpacing {
    data class Fixed(val value: Dp) : CalculatedSpacing()
    object SpaceBetween : CalculatedSpacing()
    object SpaceAround : CalculatedSpacing()
    object SpaceEvenly : CalculatedSpacing()
    object Start : CalculatedSpacing()
    object End : CalculatedSpacing()
    object Center : CalculatedSpacing()
}

/**
 * Helper function to calculate dynamic spacing based on screen width
 */
private fun calculateDynamicSpacing(
    spacing: DynamicSpacing,
    screenWidth: Dp,
    itemCount: Int,
    itemWidth: Dp,
    defaultSpacing: Dp
): CalculatedSpacing {
    return when (spacing) {
        is DynamicSpacing.Fixed -> CalculatedSpacing.Fixed(spacing.value)
        is DynamicSpacing.Responsive -> {
            if (itemCount == 0) return CalculatedSpacing.Fixed(defaultSpacing)
            val totalItemsWidth = itemCount * itemWidth
            val availableWidth = screenWidth - 32.dp
            if (totalItemsWidth >= availableWidth) {
                CalculatedSpacing.Fixed(spacing.minSpacing)
            } else {
                val spaceCount = itemCount - 1
                if (spaceCount > 0) {
                    val remainingSpace = availableWidth - totalItemsWidth
                    val calculatedSpacing = (remainingSpace / spaceCount).coerceIn(
                        spacing.minSpacing,
                        spacing.maxSpacing
                    )
                    CalculatedSpacing.Fixed(calculatedSpacing)
                } else {
                    CalculatedSpacing.Fixed(defaultSpacing)
                }
            }
        }
        DynamicSpacing.SpaceBetween -> CalculatedSpacing.SpaceBetween
        DynamicSpacing.SpaceAround -> CalculatedSpacing.SpaceAround
        DynamicSpacing.SpaceEvenly -> CalculatedSpacing.SpaceEvenly
        DynamicSpacing.Start -> CalculatedSpacing.Start
        DynamicSpacing.End -> CalculatedSpacing.End
        DynamicSpacing.Center -> CalculatedSpacing.Center
    }
}

/* -------------------- POSITIONING HELPERS -------------------- */

/**
 * Sealed class to define how overlay items should be positioned from the bottom
 */
sealed class OverlayPosition {
    object Half : OverlayPosition()
    data class Pixels(val height: Dp) : OverlayPosition()
    data class Percentage(val percentage: Float) : OverlayPosition()
    object WrapContent : OverlayPosition()

    companion object {
        fun pixels(height: Dp) = Pixels(height)
        fun percentage(percentage: Float) = Percentage(percentage)
    }
}

/**
 * Helper function to calculate height needed to wrap content
 */
private fun calculateWrapContentHeight(
    items: List<CategoryItemBgImg>,
    overlayItemHeight: Dp
): Dp {
    val maxItemHeight = overlayItemHeight + if (items.any { it.name.isNotEmpty() }) 30.dp else 0.dp
    return maxItemHeight + 16.dp
}

/* -------------------- OVERLAY ITEM -------------------- */

@Composable
fun OverlayItemOnBackground(
    item: CategoryItemBgImg,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    width: Dp = 72.dp,
    height: Dp = 72.dp, // New parameter for independent height
    textSize: TextUnit = 12.sp,
    elevation: Dp = 6.dp,
    /* OPTIONAL: Custom shape corner radius */
    cornerRadius: Dp = 12.dp
) {
    Column(
        modifier = modifier
            .width(if (item.name.isNotEmpty()) 100.dp else width)
            .clickable { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = if (item.name.isNotEmpty()) Arrangement.Top else Arrangement.Center
    ) {
        // Image container with independent width and height
        Box(
            modifier = Modifier
                .width(width)
                .height(height) // Using independent height
                .shadow(elevation, RoundedCornerShape(cornerRadius))
                .clip(RoundedCornerShape(cornerRadius))
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(item.imageRes),
                contentDescription = item.name.ifEmpty { "Category item ${item.id}" },
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillBounds // Changed to Crop for better fit with different aspect ratios
            )
        }

        if (item.name.isNotEmpty()) {
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = item.name,
                fontSize = textSize,
                fontWeight = FontWeight.SemiBold,
                color = Color.White,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

/* -------------------- LIST ITEM -------------------- */

@Composable
fun CategoryListItemBgImg(
    item: CategoryItemBgImg,
    onClick: () -> Unit,
    width: Dp = 140.dp,
    height: Dp = 140.dp
) {
    Box(
        modifier = Modifier
            .width(width)
            .height(height)
            .clip(RoundedCornerShape(12.dp))
            .clickable { onClick() }
    ) {
        Image(
            painter = painterResource(item.imageRes),
            contentDescription = item.name.ifEmpty { "Category item ${item.id}" },
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )

        if (item.name.isNotEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomStart)
                    .background(Color.Black.copy(alpha = 0.5f))
            ) {
                Text(
                    text = item.name,
                    modifier = Modifier.padding(8.dp),
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}