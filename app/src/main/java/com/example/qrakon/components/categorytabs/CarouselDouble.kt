package com.example.qrakon.components.categorytabs

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.qrakon.ui.theme.customColors

/**
 * Reusable component for displaying categories in a two-row horizontal scroll layout
 *
 * @param categories List of categories to display
 * @param onCategorySelected Callback function when a category is selected
 * @param modifier Modifier for the component
 * @param initialSelectedCategory Initially selected category (defaults to first category)
 * @param itemWidth Width of each category item
 * @param itemHeight Height of each category item
 * @param horizontalSpacing Spacing between columns
 * @param verticalSpacing Spacing between rows
 * @param horizontalPadding Horizontal padding for the entire component
 * @param verticalPadding Vertical padding for the entire component
 * @param backgroundColor Background color for the category items
 */

//data class Category(
//    val id: Int,
//    val name: String,
//    val iconRes: Int
//)

@Composable
fun CategoryProducts(
    categories: List<Category>,
    onCategorySelected: (Category) -> Unit,
    modifier: Modifier = Modifier,
    initialSelectedCategory: Category? = null,
    itemWidth: Int = 70,
    itemHeight: Int = 60,
    horizontalSpacing: Int = 16,
    verticalSpacing: Int = 6,
    horizontalPadding: Int = 12,
    verticalPadding: Int = 6,
    backgroundColor: Color = MaterialTheme.customColors.imageBgColor1 // Default background color
) {
    require(categories.isNotEmpty()) { "Categories list cannot be empty" }

    var selectedCategory by remember {
        mutableStateOf(initialSelectedCategory ?: categories.first())
    }

    // Split categories into two rows for the horizontal scroll
    val halfSize = (categories.size + 1) / 2
    val firstRow = categories.take(halfSize)
    val secondRow = categories.drop(halfSize)

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(horizontalSpacing.dp),
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = horizontalPadding.dp, vertical = verticalPadding.dp)
    ) {
        items(firstRow.size) { columnIndex ->
            Column(
                verticalArrangement = Arrangement.spacedBy(verticalSpacing.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Top Row Item
                CategoryItem(
                    category = firstRow[columnIndex],
                    isSelected = firstRow[columnIndex] == selectedCategory,
                    onClick = {
                        selectedCategory = firstRow[columnIndex]
                        onCategorySelected(firstRow[columnIndex])
                    },
                    itemWidth = itemWidth,
                    itemHeight = itemHeight,
                    backgroundColor = backgroundColor
                )

                // Bottom Row Item (only if available)
                if (columnIndex < secondRow.size) {
                    CategoryItem(
                        category = secondRow[columnIndex],
                        isSelected = secondRow[columnIndex] == selectedCategory,
                        onClick = {
                            selectedCategory = secondRow[columnIndex]
                            onCategorySelected(secondRow[columnIndex])
                        },
                        itemWidth = itemWidth,
                        itemHeight = itemHeight,
                        backgroundColor = backgroundColor
                    )
                }
            }
        }
    }
}

/**
 * Individual category item component
 *
 * @param category The category to display
 * @param isSelected Whether this category is currently selected
 * @param onClick Callback when the category is clicked
 * @param itemWidth Width of the category item
 * @param itemHeight Height of the category item
 * @param backgroundColor Background color for the category item
 */
@Composable
fun CategoryItem(
    category: Category,
    isSelected: Boolean,
    onClick: () -> Unit,
    itemWidth: Int = 70,
    itemHeight: Int = 60,
    backgroundColor: Color = MaterialTheme.customColors.imageBgColor1
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(itemWidth.dp)
            .clickable(onClick = onClick)
    ) {
        Box(
            modifier = Modifier
                .height(itemHeight.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(backgroundColor), // Use the passed backgroundColor
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = category.iconRes),
                contentDescription = category.name,
                contentScale = ContentScale.Fit,
                modifier = Modifier.fillMaxSize()
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = category.name,
            fontSize = 12.sp,
            textAlign = TextAlign.Center,
            color = Color.Black,
            fontWeight = FontWeight.Medium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.fillMaxWidth()
        )
    }
}