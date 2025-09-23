package com.example.qrakon.components.homescreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.example.qrakon.R

data class ClothsCategory(
    val id: Int,
    val name: String,
    val iconRes: Int,
    val price: String
)

@Composable
fun ClothsCategory(modifier: Modifier = Modifier) {
    val clothsCategories = remember {
        listOf(
            ClothsCategory(1, "T-Shirts", R.drawable.ic_tshirt, "₹499"),
            ClothsCategory(2, "Jeans", R.drawable.ic_jeans, "₹1,299"),
            ClothsCategory(3, "Dresses", R.drawable.ic_dress, "₹1,999"),
            ClothsCategory(4, "Shirts", R.drawable.ic_shirt, "₹899"),
            ClothsCategory(5, "Sweaters", R.drawable.ic_sweater, "₹1,499"),
            ClothsCategory(6, "Jackets", R.drawable.ic_jacket, "₹2,499"),
            ClothsCategory(7, "Skirts", R.drawable.ic_skirt, "₹999"),
            ClothsCategory(8, "Activewear", R.drawable.ic_activewear, "₹1,199"),
            ClothsCategory(9, "Formal Wear", R.drawable.ic_formal_wear, "₹3,999")
        )
    }

    val selectedCategory = remember { mutableStateOf(clothsCategories.first()) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 12.dp, vertical = 8.dp)
    ) {
        Text(
            text = "Starting ₹499 | Latest fashion trends at great prices",
            fontSize = 16.sp,
            textAlign = TextAlign.Start,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(6.dp))

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(clothsCategories.size) { index ->
                val category = clothsCategories[index]
                ClothsCategoryItem(
                    category = category,
                    isSelected = category == selectedCategory.value,
                    onClick = { selectedCategory.value = category }
                )
            }
        }
    }
}

@Composable
fun ClothsCategoryItem(
    category: ClothsCategory,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor = Color(0xFFF5F5F5)
    val borderColor = Color.Transparent

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(100.dp)
            .clickable(onClick = onClick)
            .clip(RoundedCornerShape(12.dp))
            .background(backgroundColor)
            .border(
                width = 2.dp,
                color = borderColor,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(8.dp)
    ) {
        // Image Container
        Box(
            modifier = Modifier
                .size(80.dp)
                .clip(RoundedCornerShape(5.dp))
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = category.iconRes),
                contentDescription = category.name,
                contentScale = ContentScale.Fit,
                modifier = Modifier.size(70.dp)
            )
        }

        Spacer(modifier = Modifier.height(6.dp))

        // Clothing Name
        Text(
            text = category.name,
            fontSize = 12.sp,
            textAlign = TextAlign.Center,
            color = Color.Black,
            fontWeight = FontWeight.Medium,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(2.dp))

        // Price Text
        Text(
            text = category.price,
            fontSize = 14.sp,
            textAlign = TextAlign.Center,
            color = Color(0xFF1976D2),
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

// Preview function (optional)
@Composable
fun ClothsCategoryPreview() {
    ClothsCategory()
}