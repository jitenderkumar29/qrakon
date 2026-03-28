package com.example.qrakon.components.GroceryHome

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.qrakon.R
import com.example.qrakon.ui.theme.customColors
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.ui.unit.times
import kotlin.math.ceil

data class GroceryItemCategory(
    val id: Int,
    val title: String,
    val iconRes: Int = R.drawable.ic_category_placeholder
)

@Composable
fun ListItemsGrid(
    navController: NavHostController? = null,
    onBackClick: () -> Unit = {},
    onItemClick: (GroceryItemCategory) -> Unit = {},
    name: String = "Groceries & food",
    items: List<GroceryItemCategory> = defaultGroceryItems,
    selectedItemId: Int? = null,
    modifier: Modifier = Modifier,
    columns: Int = 4,
    horizontalSpacing: Dp = 8.dp,
    verticalSpacing: Dp = 12.dp,
    itemHeight: Dp = 120.dp,
    imageSize: Dp = 50.dp,
    containerSize: Dp = 75.dp,
    backgroundColor: Color = MaterialTheme.customColors.white,
    showBrowseText: Boolean = true,
    browseText: String = "Browse Categories"
) {
    val rowCount = ceil(items.size / columns.toFloat()).toInt().coerceAtLeast(1)
    val gridHeight = (rowCount * itemHeight) + ((rowCount - 1) * verticalSpacing) + 16.dp

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(backgroundColor)
    ) {
        if (showBrowseText) {
            Text(
                text = browseText,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.customColors.black,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(backgroundColor)
                    .padding(horizontal = 12.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(columns),
            modifier = Modifier
                .fillMaxWidth()
                .height(gridHeight),
            userScrollEnabled = false,
            contentPadding = PaddingValues(bottom = 8.dp),
            verticalArrangement = Arrangement.spacedBy(verticalSpacing),
            horizontalArrangement = Arrangement.spacedBy(horizontalSpacing)
        ) {
            items(items) { item ->
                GroceryGridItem(
                    item = item,
                    isSelected = item.id == selectedItemId,
                    onClick = { onItemClick(item) },
                    itemHeight = itemHeight,
                    imageSize = imageSize,
                    containerSize = containerSize
                )
            }
        }
    }
}

@Composable
fun GroceryGridItem(
    item: GroceryItemCategory,
    isSelected: Boolean = false,
    onClick: () -> Unit = {},
    itemHeight: Dp = 120.dp,
    imageSize: Dp = 50.dp,
    containerSize: Dp = 75.dp
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .fillMaxWidth()
            .height(itemHeight)
            .clickable { onClick() }
    ) {
        Box(
            modifier = Modifier
                .size(containerSize),
//                .clip(CircleShape)
//                .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = item.iconRes),
                contentDescription = item.title,
                modifier = Modifier.size(imageSize)
            )
        }

        // Title - Using your logic
        if (item.title.isNotEmpty()) {
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = item.title,
                fontSize = 12.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                color = if (isSelected) MaterialTheme.customColors.primary
                else MaterialTheme.customColors.black,
                textAlign = TextAlign.Center,
                lineHeight = 14.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(28.dp)
            )
        }

//        Spacer(modifier = Modifier.height(6.dp))
//        Text(
//            text = item.title,
//            fontSize = 12.sp,
//            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
//            color = if (isSelected) MaterialTheme.customColors.primary
//            else MaterialTheme.customColors.black,
//            textAlign = TextAlign.Center,
//            lineHeight = 14.sp,
//            maxLines = 2,
////            overflow = TextOverflow.Ellipsis,
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(28.dp)
//        )
    }
}

val defaultGroceryItems = listOf(
    GroceryItemCategory(1, "Fresh vegetables", R.drawable.ic_vegetables),
    GroceryItemCategory(2, "Fresh fruits", R.drawable.ic_fruits),
    GroceryItemCategory(3, "Atta, rice & grains", R.drawable.ic_grains_qrakon),
    GroceryItemCategory(4, "Dal & pulses", R.drawable.ic_dal),
    GroceryItemCategory(5, "Oil & ghee", R.drawable.ic_oil),
    GroceryItemCategory(6, "Masala, sugar & spices", R.drawable.ic_spices_grocery),
    GroceryItemCategory(7, "Milk & dairy", R.drawable.ic_dairy),
    GroceryItemCategory(8, "Breads & bakery", R.drawable.ic_bakery),
    GroceryItemCategory(9, "Cereals & breakfast", R.drawable.ic_cereals),
    GroceryItemCategory(10, "Tea, coffee & drink mixes", R.drawable.ic_tea_coffee),
    GroceryItemCategory(11, "Juices & cold drinks", R.drawable.ic_juices),
    GroceryItemCategory(12, "Sauces & spreads", R.drawable.ic_sauces),
    GroceryItemCategory(13, "Dry fruits & seeds", R.drawable.ic_dry_fruits),
    GroceryItemCategory(14, "Noodles & pasta", R.drawable.ic_noodles),
    GroceryItemCategory(15, "Chips & biscuits", R.drawable.ic_chips),
    GroceryItemCategory(16, "Chocolates & ice cream", R.drawable.ic_chocolates)
)