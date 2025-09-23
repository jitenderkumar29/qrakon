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

data class FurnitureCategory(
    val id: Int,
    val name: String,
    val iconRes: Int,
    val price: String
)

@Composable
fun FurnitureCategory(modifier: Modifier = Modifier) {
    val furnitureCategories = remember {
        listOf(
            FurnitureCategory(1, "Sofas", R.drawable.ic_sofa, "₹15,999"),
            FurnitureCategory(2, "Chairs", R.drawable.ic_chair, "₹4,999"),
            FurnitureCategory(3, "Tables", R.drawable.ic_table, "₹8,499"),
            FurnitureCategory(4, "Beds", R.drawable.ic_bed, "₹22,999"),
            FurnitureCategory(5, "Wardrobes", R.drawable.ic_wardrobe, "₹18,999"),
            FurnitureCategory(6, "Desks", R.drawable.ic_desk, "₹12,999"),
            FurnitureCategory(7, "Shelves", R.drawable.ic_shelf, "₹6,499"),
            FurnitureCategory(8, "Cabinets", R.drawable.ic_cabinet, "₹9,999"),
            FurnitureCategory(9, "Dining Sets", R.drawable.ic_dining_set, "₹35,999")
        )
    }

    val selectedCategory = remember { mutableStateOf(furnitureCategories.first()) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 12.dp, vertical = 8.dp)
    ) {
        Text(
            text = "Starting ₹4,999 | Premium furniture for your home",
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
            items(furnitureCategories.size) { index ->
                val category = furnitureCategories[index]
                FurnitureCategoryItem(
                    category = category,
                    isSelected = category == selectedCategory.value,
                    onClick = { selectedCategory.value = category }
                )
            }
        }
    }
}

@Composable
fun FurnitureCategoryItem(
    category: FurnitureCategory,
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

        // Furniture Name
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
fun FurnitureCategoryPreview() {
    FurnitureCategory()
}