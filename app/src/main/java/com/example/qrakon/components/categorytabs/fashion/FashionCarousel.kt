package com.example.qrakon.components.categorytabs.fashion

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
fun FashionCarousel(
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
            CategoryItem(0, "New Arrivals", R.drawable.ic_new_arrivals),
            CategoryItem(1, "Men", R.drawable.ic_men),
            CategoryItem(2, "Women", R.drawable.ic_women),
            CategoryItem(3, "Kids", R.drawable.ic_kids),
            CategoryItem(4, "Home", R.drawable.ic_home),
            CategoryItem(5, "Genz", R.drawable.ic_genz),
            CategoryItem(6, "Sportswear", R.drawable.ic_sportswear),
            CategoryItem(7, "Footwear", R.drawable.ic_footwear_fashion),
            CategoryItem(8, "Accessories", R.drawable.ic_accessories),
            CategoryItem(9, "Winter Wear", R.drawable.ic_winter_wear),
            CategoryItem(10, "Activewear", R.drawable.ic_activewear_fashion),
            CategoryItem(11, "Luxury", R.drawable.ic_luxury),
            CategoryItem(12, "Ethnic Wear", R.drawable.ic_ethnic_wear),
            CategoryItem(13, "Western Wear", R.drawable.ic_western_wear),
            CategoryItem(14, "Bags & Luggage", R.drawable.ic_bags_luggage),
            CategoryItem(15, "All Brands", R.drawable.ic_all_brands)
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
