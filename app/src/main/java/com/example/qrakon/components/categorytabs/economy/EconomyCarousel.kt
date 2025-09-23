package com.example.qrakon.components.categorytabs.economy

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
fun EconomyCarousel(
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
            CategoryItem(0, "Luxury Saree", R.drawable.ic_luxury_saree),
            CategoryItem(1, "Women Ethnic", R.drawable.ic_women_ethnic),
            CategoryItem(2, "Women Western", R.drawable.ic_women_western),
            CategoryItem(3, "Men", R.drawable.ic_men),
            CategoryItem(4, "Kids", R.drawable.ic_kids),
            CategoryItem(5, "Home & Kitchen", R.drawable.ic_home_kitchen),
            CategoryItem(6, "Beauty & Health", R.drawable.ic_beauty_health),
            CategoryItem(7, "Jewellery & Accessories", R.drawable.ic_jewellery_accessories),
            CategoryItem(8, "Bags & Footwear", R.drawable.ic_bags_footwear),
            CategoryItem(9, "Electronics", R.drawable.ic_electronics),
            CategoryItem(10, "Sports & Fitness", R.drawable.ic_sports_fitness),
            CategoryItem(11, "Car & Motorbike", R.drawable.ic_car_motorbike),
            CategoryItem(12, "Office Supplies & Stationery", R.drawable.ic_office_supplies_stationery),
            CategoryItem(13, "Pet Supplies", R.drawable.ic_pet_supplies),
            CategoryItem(14, "Food & Drinks", R.drawable.ic_food_drinks),
            CategoryItem(15, "Musical Instruments", R.drawable.ic_musical_instruments),
            CategoryItem(16, "Books", R.drawable.ic_books)
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