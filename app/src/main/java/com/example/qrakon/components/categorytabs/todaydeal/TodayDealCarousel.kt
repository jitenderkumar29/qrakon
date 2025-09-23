package com.example.qrakon.components.categorytabs.todaydeal

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.qrakon.R
import com.example.qrakon.ui.theme.customColors

@Composable
fun TodayDealCarousel(
    selectedCategory: String,
    onCategoryClick: (String) -> Unit
) {
    data class CategoryItem(
        val id: Int,
        val name: String,
        val imageResId: Int
    )

    val categories = remember {
        listOf(
            CategoryItem(0, "All Deals", R.drawable.ic_all_deals),
            CategoryItem(1, "Electronics", R.drawable.ic_electronics),
            CategoryItem(2, "Home & Kitchen", R.drawable.ic_home_kitchen),
            CategoryItem(3, "Fashion", R.drawable.ic_fashion),
            CategoryItem(4, "Beauty", R.drawable.ic_beauty),
            CategoryItem(5, "Toys & Games", R.drawable.ic_toys_games),
            CategoryItem(6, "Sports", R.drawable.ic_sports),
            CategoryItem(7, "Grocery", R.drawable.ic_grocery),
            CategoryItem(8, "Health", R.drawable.ic_health),
            CategoryItem(9, "Books", R.drawable.ic_books),
            CategoryItem(10, "Automotive", R.drawable.ic_automotive),
            CategoryItem(11, "Pet Supplies", R.drawable.ic_pet_supplies),
            CategoryItem(12, "Office", R.drawable.ic_office),
            CategoryItem(13, "Baby", R.drawable.ic_baby),
            CategoryItem(14, "Garden", R.drawable.ic_garden),
            CategoryItem(15, "Travel", R.drawable.ic_travel)
        )
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        categories.forEach { category ->
            CategoryCard(
                name = category.name,
                imageResId = category.imageResId,
                isSelected = selectedCategory == category.name,
                modifier = Modifier.width(80.dp),
                onClick = { onCategoryClick(category.name) }
            )
        }
    }
}

@Composable
fun CategoryCard(
    name: String,
    imageResId: Int,
    isSelected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier
            .clickable { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .height(80.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(MaterialTheme.customColors.imageBgColor1)
                .padding(4.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = imageResId),
                contentDescription = name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(80.dp)
                    .clip(MaterialTheme.shapes.medium)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = name,
            style = MaterialTheme.typography.bodySmall.copy(
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                color = if (isSelected) MaterialTheme.colorScheme.primary
                else MaterialTheme.colorScheme.onSurfaceVariant
            ),
            textAlign = TextAlign.Center,
            maxLines = 2,
            modifier = Modifier.fillMaxWidth()
        )
    }
}