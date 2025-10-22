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
 * Category list item for double-column layout (two items stacked vertically)
 */
@Composable
fun CategoryListItemDouble(
    item: CategoryItem,
    onClick: () -> Unit,
    backgroundColor: Color = MaterialTheme.customColors.white,
    textColor: Color = Color.Black,
    showOverlayOnImage: Boolean = false,
    overlayBackground: Color = Color.Black.copy(alpha = 0.6f),
    overlayTextColor: Color = Color.White,
    showItemName: Boolean = true,
    itemWidth: Dp = 150.dp,
    itemHeight: Dp = 200.dp
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
        Image(
            painter = painterResource(id = item.imageRes),
            contentDescription = item.name,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(8.dp))
        )

        if (showOverlayOnImage) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(overlayBackground)
            )
        }

        if (showItemName) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        if (showOverlayOnImage) Color.Transparent else backgroundColor.copy(alpha = 0.5f)
                    )
                    .padding(horizontal = 10.dp, vertical = 8.dp)
            ) {
                Text(
                    text = item.name,
                    fontSize = 14.sp,
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
 * Category list with horizontal scrolling (2 items per column)
 */
@Composable
fun CategoryListDouble(
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
    itemWidth: Dp = 150.dp,
    itemHeight: Dp = 200.dp,
    horizontalSpacing: Dp = 16.dp,
    verticalSpacing: Dp = 12.dp,
    horizontalPadding: Dp = 16.dp,
    verticalPadding: Dp = 12.dp
) {
    require(items.isNotEmpty()) { "Items list cannot be empty" }

    // Split items into pairs for 2 stacked per column
    val itemPairs = items.chunked(2)

    LazyRow(
        modifier = modifier
            .fillMaxWidth()
            .background(backgroundColor)
            .padding(horizontal = horizontalPadding, vertical = verticalPadding),
        horizontalArrangement = Arrangement.spacedBy(horizontalSpacing)
    ) {
        items(itemPairs) { pair ->
            Column(
                verticalArrangement = Arrangement.spacedBy(verticalSpacing),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                pair.forEach { item ->
                    if (customListItem != null) {
                        customListItem(item) { onItemClick(item) }
                    } else {
                        CategoryListItemDouble(
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
}

/**
 * Category list with heading and horizontal scroll
 */
@Composable
fun CategoryListDoubleWithHeading(
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
    itemWidth: Dp = 150.dp,
    itemHeight: Dp = 200.dp,
    horizontalSpacing: Dp = 16.dp,
    verticalSpacing: Dp = 12.dp,
    horizontalPadding: Dp = 16.dp,
    verticalPadding: Dp = 12.dp,
    headingBottomPadding: Dp = 12.dp
) {
    require(items.isNotEmpty()) { "Items list cannot be empty" }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(backgroundColor)
    ) {
        heading?.let {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = horizontalPadding)
                    .padding(top = verticalPadding, bottom = headingBottomPadding)
            ) {
                Text(
                    text = heading,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = textColor
                )
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

        CategoryListDouble(
            items = items,
            onItemClick = onItemClick,
            modifier = Modifier,
            backgroundColor = backgroundColor,
            itemBackgroundColor = itemBackgroundColor,
            textColor = textColor,
            showOverlayOnImage = showOverlayOnImage,
            overlayBackground = overlayBackground,
            overlayTextColor = overlayTextColor,
            showItemName = showItemName,
            customListItem = customListItem,
            itemWidth = itemWidth,
            itemHeight = itemHeight,
            horizontalSpacing = horizontalSpacing,
            verticalSpacing = verticalSpacing,
            horizontalPadding = horizontalPadding,
            verticalPadding = verticalPadding
        )
    }
}

/**
 * Example list and preview
 */
val roomCategoriesDouble = listOf(
    CategoryItem(0, "Kitchen", R.drawable.ic_kitchen, "View products"),
    CategoryItem(1, "Bathroom", R.drawable.ic_bathroom, "View products"),
    CategoryItem(2, "Bedroom", R.drawable.ic_bedroom, "View products"),
    CategoryItem(3, "Living Room", R.drawable.ic_living_room, "View products"),
    CategoryItem(4, "Dining Room", R.drawable.ic_kitchen, "View products"),
    CategoryItem(5, "Home Office", R.drawable.ic_bedroom, "View products"),
    CategoryItem(6, "Garden", R.drawable.ic_kitchen, "View products"),
    CategoryItem(7, "Garage", R.drawable.ic_bathroom, "View products")
)

@androidx.compose.ui.tooling.preview.Preview(showBackground = true)
@Composable
fun CategoryListDoublePreview() {
    MaterialTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            CategoryListDoubleWithHeading(
                heading = "Shop by Room",
                subtitle = "Scroll to explore",
                items = roomCategoriesDouble,
                onItemClick = { item -> println("Clicked ${item.name}") },
                showOverlayOnImage = true,
                showItemName = true,
                itemWidth = 150.dp,
                itemHeight = 180.dp,
                horizontalSpacing = 12.dp,
                verticalSpacing = 12.dp,
                backgroundColor = Color.LightGray
            )
        }
    }
}
