package com.example.qrakon.components.fashion.categorydetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.qrakon.R

@Composable
fun CategoryDetailScreen(
    categoryName: String?,
    onBackClick: () -> Unit
) {
    val name = categoryName ?: "Category"

    // Example data per category
    val categoryItems = when (name.lowercase()) {
        "ethnic" -> listOf(
            R.drawable.ic_fashion_ethnic to "Sarees",
            R.drawable.ic_fashion_ethnic to "Lehengas",
            R.drawable.ic_fashion_ethnic to "Kurtas & Sets"
        )
        "western" -> listOf(
            R.drawable.ic_fashion_western to "Dresses",
            R.drawable.ic_fashion_western to "Tops & Shirts",
            R.drawable.ic_fashion_western to "Jeans & Trousers"
        )
        "fusion" -> listOf(
            R.drawable.ic_fashion_fusion to "Indo-Western Dresses",
            R.drawable.ic_fashion_fusion to "Bohemian Styles",
            R.drawable.ic_fashion_fusion to "Tunics"
        )
        "essentials" -> listOf(
            R.drawable.ic_fashion_essentials to "T-Shirts",
            R.drawable.ic_fashion_essentials to "Casual Bottoms",
            R.drawable.ic_fashion_essentials to "Sleepwear"
        )
        "beauty" -> listOf(
            R.drawable.ic_fashion_beauty to "Makeup",
            R.drawable.ic_fashion_beauty to "Skincare",
            R.drawable.ic_fashion_beauty to "Fragrances"
        )
        "footwear" -> listOf(
            R.drawable.ic_fashion_footwear to "Flats",
            R.drawable.ic_fashion_footwear to "Heels",
            R.drawable.ic_fashion_footwear to "Sneakers"
        )
        "jewellery" -> listOf(
            R.drawable.ic_fashion_jewellery to "Earrings",
            R.drawable.ic_fashion_jewellery to "Necklaces",
            R.drawable.ic_fashion_jewellery to "Bangles"
        )
        "home" -> listOf(
            R.drawable.ic_fashion_home to "Bedding",
            R.drawable.ic_fashion_home to "Curtains",
            R.drawable.ic_fashion_home to "Decor"
        )
        "sportswear" -> listOf(
            R.drawable.ic_fashion_sportswear to "Active T-Shirts",
            R.drawable.ic_fashion_sportswear to "Leggings",
            R.drawable.ic_fashion_sportswear to "Track Jackets"
        )
        "girls" -> listOf(
            R.drawable.ic_fashion_girls to "Frocks",
            R.drawable.ic_fashion_girls to "Tops",
            R.drawable.ic_fashion_girls to "Denims"
        )
        else -> listOf(
            R.drawable.ic_view_all_home_tab to "Popular Styles",
            R.drawable.ic_view_all_home_tab to "Top Brands",
            R.drawable.ic_view_all_home_tab to "Shop All Categories"
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {
        // 🔙 Back + Title Bar
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onBackClick) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_back),
                    contentDescription = "Back",
                    tint = Color.Black
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = name,
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                color = Color.Black
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Category-specific list
        LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            items(categoryItems) { (imageRes, title) ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = imageRes),
                        contentDescription = title,
                        modifier = Modifier
                            .size(60.dp)
                            .padding(end = 12.dp)
                    )
                    Text(
                        text = title,
                        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
    }
}
