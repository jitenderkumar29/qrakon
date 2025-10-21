package com.example.qrakon.components.fashion.fashiontab

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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.qrakon.R
import com.example.qrakon.ui.theme.customColors

/**
 * Simple category list component without arrow icons and optional headings
 */
@Composable
fun CategoryListSimple(
    items: List<CategoryItem>,
    onItemClick: (CategoryItem) -> Unit,
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.customColors.white,
    itemBackgroundColor: Color = MaterialTheme.customColors.white,
    textColor: Color = Color.Black,
    showOverlayOnImage: Boolean = false,
    overlayBackground: Color = Color.Black.copy(alpha = 0.6f),
    overlayTextColor: Color = Color.White,
    showItemName: Boolean = true,
    customListItem: @Composable ((CategoryItem, () -> Unit) -> Unit)? = null,
    itemWidth: Dp = 150.dp, // Dynamic width
    itemHeight: Dp = 200.dp, // Dynamic height
    horizontalSpacing: Dp = 16.dp, // Dynamic spacing between items
    verticalPadding: Dp = 12.dp, // Dynamic vertical padding
    horizontalPadding: Dp = 16.dp // Dynamic horizontal padding
) {
    require(items.isNotEmpty()) { "Items list cannot be empty" }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(backgroundColor)
            .padding(horizontal = horizontalPadding, vertical = verticalPadding)
    ) {
        // Horizontal scrollable list of category items
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(horizontalSpacing),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(items) { item ->
                if (customListItem != null) {
                    customListItem(item, { onItemClick(item) })
                } else {
                    CategoryListItemSimple(
                        item = item,
                        onClick = { onItemClick(item) },
                        backgroundColor = itemBackgroundColor,
                        textColor = textColor,
                        showOverlayOnImage = showOverlayOnImage,
                        overlayBackground = overlayBackground,
                        overlayTextColor = overlayTextColor,
                        showItemName = showItemName,
                        itemWidth = itemWidth,
                        itemHeight = itemHeight
                    )
                }
            }
        }
    }
}

/**
 * Simple category list item without arrow icon
 */
@Composable
fun CategoryListItemSimple(
    item: CategoryItem,
    onClick: () -> Unit,
    backgroundColor: Color = MaterialTheme.customColors.white,
    textColor: Color = Color.Black,
    showOverlayOnImage: Boolean = false,
    overlayBackground: Color = Color.Black.copy(alpha = 0.6f),
    overlayTextColor: Color = Color.White,
    showItemName: Boolean = true,
    itemWidth: Dp = 150.dp, // Dynamic width
    itemHeight: Dp = 200.dp // Dynamic height
) {
    Box(
        modifier = Modifier
            .width(itemWidth)
            .height(itemHeight)
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

        // Overlay (title only - no subtitle or arrow)
        if (showItemName) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
//                    .background(overlayBackground)
//                    .background(
//                        Brush.verticalGradient(
//                            colors = listOf(Color.Transparent, overlayBackground),
//                            startY = 80f
//                        )
//                    )
                    .padding(horizontal = 10.dp, vertical = 8.dp)
            ) {
                Text(
                    text = item.name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = overlayTextColor,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

/**
 * Simple category list with optional heading
 */
@Composable
fun CategoryListSimpleWithHeading(
    heading: String? = null,
    subtitle: String? = null,
    items: List<CategoryItem>,
    onItemClick: (CategoryItem) -> Unit,
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.customColors.white,
    itemBackgroundColor: Color = MaterialTheme.customColors.white,
    textColor: Color = Color.Black,
    showOverlayOnImage: Boolean = false,
    overlayBackground: Color = Color.Black.copy(alpha = 0.6f),
    overlayTextColor: Color = Color.White,
    showItemName: Boolean = true,
    customListItem: @Composable ((CategoryItem, () -> Unit) -> Unit)? = null,
    itemWidth: Dp = 150.dp, // Dynamic width
    itemHeight: Dp = 200.dp, // Dynamic height
    horizontalSpacing: Dp = 16.dp, // Dynamic spacing between items
    verticalPadding: Dp = 12.dp, // Dynamic vertical padding
    horizontalPadding: Dp = 16.dp, // Dynamic horizontal padding
    headingBottomPadding: Dp = 12.dp // Dynamic padding below heading
) {
    require(items.isNotEmpty()) { "Items list cannot be empty" }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(backgroundColor)
            .padding(horizontal = horizontalPadding, vertical = verticalPadding)
    ) {
        // Optional header with heading and subtitle
        heading?.let {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = headingBottomPadding)
            ) {
                Text(
                    text = heading,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = textColor
                )

                // Optional subtitle
                subtitle?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodyMedium,
                        color = textColor.copy(alpha = 0.7f),
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }
        }

        // Horizontal scrollable list of category items
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(horizontalSpacing),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(items) { item ->
                if (customListItem != null) {
                    customListItem(item, { onItemClick(item) })
                } else {
                    CategoryListItemSimple(
                        item = item,
                        onClick = { onItemClick(item) },
                        backgroundColor = itemBackgroundColor,
                        textColor = textColor,
                        showOverlayOnImage = showOverlayOnImage,
                        overlayBackground = overlayBackground,
                        overlayTextColor = overlayTextColor,
                        showItemName = showItemName,
                        itemWidth = itemWidth,
                        itemHeight = itemHeight
                    )
                }
            }
        }
    }
}

/**
 * Example custom list item - Square shape with circular image and text below
 */
@Composable
fun CustomCategoryListItem(
    item: CategoryItem,
    onClick: () -> Unit,
    backgroundColor: Color = MaterialTheme.customColors.white,
    textColor: Color = Color.Black,
    itemWidth: Dp = 100.dp, // Dynamic width
    itemHeight: Dp = 120.dp, // Dynamic height
    imageSize: Dp = 80.dp, // Dynamic image size
    textSpacing: Dp = 8.dp // Dynamic text spacing
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(itemWidth)
            .height(itemHeight)
            .clickable(onClick = onClick)
    ) {
        // Circular image
        Box(
            modifier = Modifier
                .size(imageSize)
                .clip(RoundedCornerShape(40.dp))
                .background(backgroundColor)
        ) {
            Image(
                painter = painterResource(id = item.imageRes),
                contentDescription = item.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(40.dp))
            )
        }

        // Text below image
        Spacer(modifier = Modifier.height(textSpacing))
        Text(
            text = item.name,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = textColor,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )
    }
}

/**
 * Another example custom list item - Card style with shadow
 */
@Composable
fun CustomCardCategoryListItem(
    item: CategoryItem,
    onClick: () -> Unit,
    backgroundColor: Color = MaterialTheme.customColors.white,
    textColor: Color = Color.Black,
    itemWidth: Dp = 120.dp, // Dynamic width
    itemHeight: Dp = 160.dp, // Dynamic height
    imageSize: Dp = 70.dp, // Dynamic image size
    textSpacing: Dp = 12.dp, // Dynamic text spacing
    cardPadding: Dp = 8.dp // Dynamic card padding
) {
    Box(
        modifier = Modifier
            .width(itemWidth)
            .height(itemHeight)
            .clip(RoundedCornerShape(12.dp))
            .background(backgroundColor)
            .clickable(onClick = onClick)
            .padding(cardPadding),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = item.imageRes),
                contentDescription = item.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(imageSize)
                    .clip(RoundedCornerShape(8.dp))
            )

            Spacer(modifier = Modifier.height(textSpacing))
            Text(
                text = item.name,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = textColor,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
        }
    }
}

/**
 * Convenience composable for simple room categories
 */
@Composable
fun SimpleRoomCategories(
    onItemClick: (CategoryItem) -> Unit,
    modifier: Modifier = Modifier,
    showOverlayOnImage: Boolean = true,
    showItemName: Boolean = true,
    withHeading: Boolean = false,
    customListItem: @Composable ((CategoryItem, () -> Unit) -> Unit)? = null,
    itemWidth: Dp = 150.dp,
    itemHeight: Dp = 200.dp,
    horizontalSpacing: Dp = 16.dp,
    verticalPadding: Dp = 12.dp,
    horizontalPadding: Dp = 16.dp,
    backgroundColor: Color = MaterialTheme.customColors.white
) {
    val roomCategoriesSimple = listOf(
        CategoryItem(0, "Kitchen", R.drawable.ic_kitchen, "View products"),
        CategoryItem(1, "Bathroom", R.drawable.ic_bathroom, "View products"),
        CategoryItem(2, "Bedroom", R.drawable.ic_bedroom, "View products"),
        CategoryItem(3, "Living Room", R.drawable.ic_living_room, "View products")
    )
    val items = roomCategoriesSimple

    if (withHeading) {
        CategoryListSimpleWithHeading(
            heading = "Shop by Room",
            items = items,
            onItemClick = onItemClick,
            modifier = modifier,
            showOverlayOnImage = showOverlayOnImage,
            showItemName = showItemName,
            customListItem = customListItem,
            itemWidth = itemWidth,
            itemHeight = itemHeight,
            horizontalSpacing = horizontalSpacing,
            verticalPadding = verticalPadding,
            horizontalPadding = horizontalPadding,
            backgroundColor = backgroundColor
        )
    } else {
        CategoryListSimple(
            items = items,
            onItemClick = onItemClick,
            modifier = modifier,
            showOverlayOnImage = showOverlayOnImage,
            showItemName = showItemName,
            customListItem = customListItem,
            itemWidth = itemWidth,
            itemHeight = itemHeight,
            horizontalSpacing = horizontalSpacing,
            verticalPadding = verticalPadding,
            horizontalPadding = horizontalPadding,
            backgroundColor = backgroundColor
        )
    }
}

/**
 * Preview with different dynamic configurations
 */
@androidx.compose.ui.tooling.preview.Preview(showBackground = true)
@Composable
fun CategoryListSimplePreview() {
    MaterialTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            // Small items version
            CategoryListSimple(
                items = roomCategoriesSimple,
                onItemClick = { item -> println("Selected: ${item.name}") },
                showOverlayOnImage = true,
                showItemName = true,
                itemWidth = 120.dp,
                itemHeight = 160.dp,
                horizontalSpacing = 12.dp,
                backgroundColor = Color.LightGray
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Medium items version
            CategoryListSimple(
                items = roomCategoriesSimple,
                onItemClick = { item -> println("Selected: ${item.name}") },
                customListItem = { item, onClick ->
                    CustomCategoryListItem(
                        item = item,
                        onClick = onClick,
                        itemWidth = 90.dp,
                        itemHeight = 110.dp,
                        imageSize = 60.dp,
                        backgroundColor = Color.White
                    )
                }
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Large items with heading
            CategoryListSimpleWithHeading(
                heading = "Popular Categories",
                items = roomCategoriesSimple,
                onItemClick = { item -> println("Selected: ${item.name}") },
                customListItem = { item, onClick ->
                    CustomCardCategoryListItem(
                        item = item,
                        onClick = onClick,
                        itemWidth = 140.dp,
                        itemHeight = 180.dp,
                        imageSize = 80.dp,
                        backgroundColor = Color(0xFFF5F5F5)
                    )
                },
                backgroundColor = Color(0xFFE8F5E8)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Compact version
            CategoryListSimple(
                items = roomCategoriesSimple,
                onItemClick = { item -> println("Selected: ${item.name}") },
                itemWidth = 100.dp,
                itemHeight = 140.dp,
                horizontalSpacing = 8.dp,
                verticalPadding = 8.dp,
                horizontalPadding = 12.dp,
                backgroundColor = Color(0xFFFFF8E1)
            )
        }
    }
}

// Keep your existing roomCategoriesSimple list
val roomCategoriesSimple = listOf(
    CategoryItem(0, "Kitchen", R.drawable.ic_kitchen, "View products"),
    CategoryItem(1, "Bathroom", R.drawable.ic_bathroom, "View products"),
    CategoryItem(2, "Bedroom", R.drawable.ic_bedroom, "View products"),
    CategoryItem(3, "Living Room", R.drawable.ic_living_room, "View products")
)