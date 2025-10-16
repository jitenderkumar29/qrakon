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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.qrakon.ui.theme.customColors
import com.example.qrakon.R

/**
 * Component for displaying a section with heading, subheading and square list items
 * Similar to the image showing infant categories
 *
 * @param heading The main heading/title of the section
 * @param subheading The descriptive text below the heading
 * @param items List of square items to display
 * @param onItemClick Callback when an item is clicked
 * @param modifier Modifier for the component
 * @param backgroundColor Background color for the entire section
 * @param itemBackgroundColor Background color for individual items
 * @param textColor Text color for content
 */

data class ListItemSquare(
    val id: Int,
    val name: String,
    val iconRes: Int,
    val subName: String? = null // Optional sub-name
)

@Composable
fun ListItemSquareSection(
    heading: String,
    subheading: String,
    items: List<ListItemSquare>,
    onItemClick: (ListItemSquare) -> Unit,
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.customColors.white,
    itemBackgroundColor: Color = MaterialTheme.customColors.white,
    textColor: Color = Color.Black
) {
    require(items.isNotEmpty()) { "Items list cannot be empty" }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(backgroundColor)
            .padding(horizontal = 16.dp, vertical = 2.dp)
    ) {
        // Heading
        Text(
            text = heading,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = textColor,
            modifier = Modifier.padding(bottom = 4.dp)
        )

        // Subheading
        Text(
            text = subheading,
            style = MaterialTheme.typography.bodyMedium,
            color = textColor.copy(alpha = 0.7f),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Horizontal scrollable list of square items
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(items) { item ->
                SquareListItem(
                    item = item,
                    onClick = { onItemClick(item) },
                    backgroundColor = itemBackgroundColor,
                    textColor = textColor
                )
            }
        }
    }
}

/**
 * Individual square list item component
 *
 * @param item The item to display
 * @param onClick Callback when the item is clicked
 * @param backgroundColor Background color for the item
 * @param textColor Text color for item name
 */
@Composable
fun SquareListItem(
    item: ListItemSquare,
    onClick: () -> Unit,
    backgroundColor: Color = MaterialTheme.customColors.white,
    textColor: Color = Color.Black
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(90.dp)
            .clickable(onClick = onClick)
    ) {
        // Square container for image
        Box(
            modifier = Modifier
                .size(90.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(backgroundColor),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = item.iconRes),
                contentDescription = item.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(0.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Item name
        Text(
            text = item.name,
            fontSize = 12.sp,
            textAlign = TextAlign.Center,
            color = textColor,
            fontWeight = FontWeight.Medium,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            lineHeight = 14.sp,
            modifier = Modifier.fillMaxWidth()
        )

        // Optional sub-name
        item.subName?.let { subName ->
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = subName,
                fontSize = 11.sp,
                textAlign = TextAlign.Center,
                color = textColor.copy(alpha = 0.6f),
                fontWeight = FontWeight.Normal,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                lineHeight = 12.sp,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

/**
 * Predefined infant categories as shown in the image with optional sub-names
 */
val infantCategories = listOf(
    ListItemSquare(0, "Shop All", R.drawable.ic_kids_view_all_home_tab),
    ListItemSquare(1, "Infant Clothing", R.drawable.ic_infants_kids2, "0-24 Months"),
    ListItemSquare(2, "Nursery", R.drawable.ic_nursery_kids, "Furniture & Decor"),
    ListItemSquare(3, "Feeding & Meal Time", R.drawable.ic_feeding_n_meal_time_kids, "Bottles & Utensils"),
    ListItemSquare(4, "Diapering", R.drawable.ic_diapering_kids, "Diapers & Wipes"),
    ListItemSquare(5, "Baby Gear", R.drawable.ic_baby_gear_kids, "Strollers & Carriers"),
    ListItemSquare(6, "Baby First Toys", R.drawable.ic_baby_first_toys_kids, "0-12 Months"),
    ListItemSquare(7, "Bath Time", R.drawable.ic_bath_time_kids, "Bath Essentials"),
    ListItemSquare(8, "Gift sets", R.drawable.ic_gift_sets_kids, "Curated Packages"),
)

/**
 * Convenience composable for infant categories section
 */
@Composable
fun InfantCategoriesSection(
    onItemClick: (ListItemSquare) -> Unit,
    modifier: Modifier = Modifier
) {
    ListItemSquareSection(
        heading = "Shop for Infants",
        subheading = "Westernwear, Indianwear & much more",
        items = infantCategories,
        onItemClick = onItemClick,
        modifier = modifier
    )
}

/**
 * Preview function for testing
 */
@androidx.compose.ui.tooling.preview.Preview(showBackground = true)
@Composable
fun ListItemSquareSectionPreview() {
    MaterialTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            InfantCategoriesSection(
                onItemClick = { item ->
                    println("Selected: ${item.name}")
                }
            )
        }
    }
}