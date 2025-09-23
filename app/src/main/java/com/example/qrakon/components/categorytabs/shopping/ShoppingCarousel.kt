package com.example.qrakon.components.categorytabs.shopping

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
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
import com.example.qrakon.components.homescreen.BannerHome
import com.example.qrakon.components.homescreen.CategoryProducts

@Composable
fun ShoppingCarousel(
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
            CategoryItem(0, "All", R.drawable.ic_category_all),
            CategoryItem(1, "Electronics", R.drawable.ic_electronics),
            CategoryItem(2, "Mobile Phones", R.drawable.ic_mobile_phones),
            CategoryItem(3, "Laptops & Computers", R.drawable.ic_laptops),
            CategoryItem(4, "TVs & Home Theater", R.drawable.ic_tv),
            CategoryItem(5, "Audio & Headphones", R.drawable.ic_headphones),
            CategoryItem(6, "Cameras & Photography", R.drawable.ic_camera),
            CategoryItem(7, "Smart Home", R.drawable.ic_smart_home),
            CategoryItem(8, "Wearable Technology", R.drawable.ic_wearable),
            CategoryItem(9, "Gaming", R.drawable.ic_gaming),
            CategoryItem(10, "Fashion", R.drawable.fashion),
            CategoryItem(11, "Men's Clothing", R.drawable.ic_mens_clothing),
            CategoryItem(12, "Women's Clothing", R.drawable.ic_womens_clothing),
            CategoryItem(13, "Kids & Baby", R.drawable.ic_kids),
            CategoryItem(14, "Shoes", R.drawable.ic_shoes),
            CategoryItem(15, "Watches", R.drawable.ic_watches),
            CategoryItem(16, "Jewelry", R.drawable.ic_jewelry),
            CategoryItem(17, "Bags & Luggage", R.drawable.ic_bags),
            CategoryItem(18, "Home & Kitchen", R.drawable.ic_home),
            CategoryItem(19, "Furniture", R.drawable.ic_furniture),
            CategoryItem(20, "Bedding & Bath", R.drawable.ic_bedding),
            CategoryItem(21, "Home Decor", R.drawable.ic_decor),
            CategoryItem(22, "Kitchen Appliances", R.drawable.ic_kitchen),
            CategoryItem(23, "Cookware", R.drawable.ic_cookware),
            CategoryItem(24, "Home Improvement", R.drawable.ic_improvement),
            CategoryItem(25, "Garden & Outdoor", R.drawable.ic_garden)
        )
    }

    val bannerImages = listOf(
        painterResource(id = R.drawable.banner_home4),
        painterResource(id = R.drawable.banner_home5),
        painterResource(id = R.drawable.banner_home6),
        painterResource(id = R.drawable.banner_home7),
        painterResource(id = R.drawable.banner_home8),
    )

    Column {
        // Category carousel (always visible)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
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

        Spacer(
            modifier = Modifier.height(2.dp)
                .fillMaxWidth()
                .background(MaterialTheme.customColors.spacerColor)
        )

        // Show BannerHome only when "All" category is selected
        if (selectedCategory == "All") {
            BannerHome(
                images = bannerImages,
                onImageClick = { page ->
                    when (page) {
                        0 -> onBanner1Click()
                        1 -> onBanner2Click()
                        2 -> onBanner3Click()
                    }
                },
                autoScrollDelay = 2000,
                height = 300.dp,
                dotSize = 12.dp,
                modifier = Modifier.padding(bottom = 0.dp)
            )

            Spacer(
                modifier = Modifier.height(2.dp)
                    .fillMaxWidth()
                    .background(MaterialTheme.customColors.spacerColor)
            )
            CategoryProducts(modifier = Modifier.fillMaxWidth().wrapContentHeight())
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
                .height(70.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(
                    if (isSelected) MaterialTheme.customColors.orange
                    else MaterialTheme.customColors.imageBgColor1
                )
                .padding(4.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = imageResId),
                contentDescription = name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(60.dp)
                    .clip(MaterialTheme.shapes.medium)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = name,
            style = MaterialTheme.typography.bodySmall.copy(
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                color = if (isSelected) MaterialTheme.customColors.primary
                else MaterialTheme.colorScheme.onSurfaceVariant
            ),
            textAlign = TextAlign.Center,
            maxLines = 2,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

// Example click handlers for banners
private fun onBanner1Click() {
    // Handle banner 1 click
}

private fun onBanner2Click() {
    // Handle banner 2 click
}

private fun onBanner3Click() {
    // Handle banner 3 click
}