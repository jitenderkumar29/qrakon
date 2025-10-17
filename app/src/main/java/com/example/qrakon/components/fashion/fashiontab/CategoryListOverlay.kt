package com.example.qrakon.components.fashion.fashiontab

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.qrakon.ui.theme.customColors
import com.example.qrakon.R

/**
 * Component for displaying a category list overlay with heading, optional subtitle and right arrow
 * Similar to the "Shop by Room" layout from the image
 */
data class CategoryItem(
    val id: Int,
    val name: String,
    val imageRes: Int,
    val subtitle: String? = null, // Optional subtitle like "View products"
    val overlayTitle: String? = null, // Optional title for image overlay
    val overlaySubtitle: String? = null // Optional subtitle for image overlay
)

@Composable
fun CategoryListOverlay(
    heading: String,
    subtitle: String? = null,
    items: List<CategoryItem>,
    onItemClick: (CategoryItem) -> Unit,
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.customColors.white,
    itemBackgroundColor: Color = MaterialTheme.customColors.white,
    textColor: Color = Color.Black,
    showOverlayOnImage: Boolean = false,
    overlayBackground: Color = Color.Black.copy(alpha = 0.6f),
    overlayTextColor: Color = Color.White
) {
    require(items.isNotEmpty()) { "Items list cannot be empty" }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(backgroundColor)
            .padding(horizontal = 16.dp, vertical = 20.dp)
    ) {
        // Header with heading and subtitle + arrow
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                // Heading
                Text(
                    text = heading,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = textColor
                )

                // Subtitle with arrow (optional)
                subtitle?.let {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.clickable { /* Handle subtitle click if needed */ }
                    ) {
                        Text(
                            text = it,
                            style = MaterialTheme.typography.bodyMedium,
                            color = textColor.copy(alpha = 0.7f),
                            modifier = Modifier.padding(end = 4.dp)
                        )
                        Image(
                            painter = painterResource(id = R.drawable.baseline_arrow_forward_24),
                            contentDescription = "View more",
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }
        }

        // Horizontal scrollable list of category items
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(items) { item ->
                CategoryListItem(
                    item = item,
                    onClick = { onItemClick(item) },
                    backgroundColor = itemBackgroundColor,
                    textColor = textColor,
                    showOverlayOnImage = showOverlayOnImage,
                    overlayBackground = overlayBackground,
                    overlayTextColor = overlayTextColor
                )
            }
        }
    }
}

/**
 * Updated CategoryListItem — title, subtitle & arrow appear as overlay over the image.
 */
@Composable
fun CategoryListItem(
    item: CategoryItem,
    onClick: () -> Unit,
    backgroundColor: Color = MaterialTheme.customColors.white,
    textColor: Color = Color.Black,
    showOverlayOnImage: Boolean = false,
    overlayBackground: Color = Color.Black.copy(alpha = 0.6f),
    overlayTextColor: Color = Color.White
) {
    Box(
        modifier = Modifier
            .width(150.dp)
            .height(200.dp)
            .clip(RoundedCornerShape(8.dp))
            .clickable(onClick = onClick)
            .background(backgroundColor),
        contentAlignment = Alignment.BottomStart
    ) {
        // Image
        Image(
            painter = painterResource(id = item.imageRes),
            contentDescription = item.name,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(8.dp))
        )

        // Overlay (title + subtitle + arrow)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color.Transparent, overlayBackground),
                        startY = 80f
                    )
                )
                .padding(horizontal = 10.dp, vertical = 8.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Bottom
            ) {
                Text(
                    text = item.name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = overlayTextColor,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                item.subtitle?.let { subtitle ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth(), // ensures full width for spacing
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = subtitle,
                            fontSize = 12.sp,
                            color = overlayTextColor.copy(alpha = 0.9f),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )

                        Icon(
                            painter = painterResource(id = R.drawable.baseline_arrow_forward_24),
                            contentDescription = "View more",
                            tint = overlayTextColor,
                            modifier = Modifier.size(16.dp)
                        )
                    }

                }
            }
        }
    }
}

/**
 * Optional solid overlay version
 */
@Composable
fun CategoryListItemWithSolidOverlay(
    item: CategoryItem,
    onClick: () -> Unit,
    backgroundColor: Color = MaterialTheme.customColors.white,
    overlayBackground: Color = Color.Black.copy(alpha = 0.8f),
    overlayTextColor: Color = Color.White
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(150.dp)
            .clickable(onClick = onClick)
    ) {
        Box(
            modifier = Modifier
                .width(150.dp)
                .height(200.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(backgroundColor)
        ) {
            Image(
                painter = painterResource(id = item.imageRes),
                contentDescription = item.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(8.dp))
            )

            // Solid overlay at bottom
            if (item.overlayTitle != null || item.overlaySubtitle != null) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(overlayBackground)
                        .align(Alignment.BottomStart)
                        .padding(12.dp)
                ) {
                    item.overlayTitle?.let { title ->
                        Text(
                            text = title,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = overlayTextColor,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    item.overlaySubtitle?.let { subtitle ->
                        Text(
                            text = subtitle,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Normal,
                            color = overlayTextColor.copy(alpha = 0.9f),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
        }
    }
}

/**
 * Predefined room categories
 */
val roomCategories = listOf(
    CategoryItem(0, "Kitchen", R.drawable.ic_kitchen, "View products"),
    CategoryItem(1, "Bathroom", R.drawable.ic_bathroom, "View products"),
    CategoryItem(2, "Bedroom", R.drawable.ic_bedroom, "View products"),
    CategoryItem(3, "Living Room", R.drawable.ic_living_room, "View products")
)

/**
 * Predefined categories with overlay info
 */
val roomCategoriesWithOverlay = listOf(
    CategoryItem(0, "Kitchen", R.drawable.ic_kitchen, "View products", "Modern Kitchen", "50+ products"),
    CategoryItem(1, "Bathroom", R.drawable.ic_bathroom, "View products", "Luxury Bath", "30+ products"),
    CategoryItem(2, "Bedroom", R.drawable.ic_bedroom, "View products", "Cozy Bedroom", "45+ products"),
    CategoryItem(3, "Living Room", R.drawable.ic_living_room, "View products", "Elegant Living", "60+ products")
)

/**
 * Convenience composable for room section
 */
@Composable
fun RoomCategoriesSection(
    onItemClick: (CategoryItem) -> Unit,
    modifier: Modifier = Modifier,
    showOverlayOnImage: Boolean = false
) {
    val items = if (showOverlayOnImage) roomCategoriesWithOverlay else roomCategories

    CategoryListOverlay(
        heading = "Shop by Room",
        subtitle = null,
        items = items,
        onItemClick = onItemClick,
        modifier = modifier,
        showOverlayOnImage = showOverlayOnImage
    )
}

/**
 * Preview
 */
@androidx.compose.ui.tooling.preview.Preview(showBackground = true)
@Composable
fun CategoryListOverlayPreview() {
    MaterialTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            // Overlay-on-image version
            CategoryListOverlay(
                heading = "Shop by Room",
                subtitle = "View all categories",
                items = roomCategories,
                onItemClick = { item -> println("Selected: ${item.name}") },
                showOverlayOnImage = true // key change
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Solid overlay variant
            Text(
                text = "Popular Categories",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                items(roomCategoriesWithOverlay) { item ->
                    CategoryListItemWithSolidOverlay(
                        item = item,
                        onClick = { println("Selected: ${item.name}") }
                    )
                }
            }
        }
    }
}
