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

data class ElectronicCategory(
    val id: Int,
    val name: String,
    val iconRes: Int,
    val price: String
)

@Composable
fun ElectronicCategory(modifier: Modifier = Modifier) {
    val electronicCategories = remember {
        listOf(
            ElectronicCategory(1, "Smartphones", R.drawable.ic_smartphone, "₹15,999"),
            ElectronicCategory(2, "Laptops", R.drawable.ic_laptop, "₹45,999"),
//            ElectronicCategory(3, "Headphones", R.drawable.ic_headphones, "₹2,499"),
            ElectronicCategory(4, "Watches", R.drawable.ic_smartwatch, "₹8,999"),
            ElectronicCategory(5, "Tablets", R.drawable.ic_tablet, "₹25,999"),
//            ElectronicCategory(6, "Cameras", R.drawable.ic_camera, "₹32,999"),
//            ElectronicCategory(7, "Gaming", R.drawable.ic_gaming, "₹35,999"),
            ElectronicCategory(8, "Speakers", R.drawable.ic_speaker, "₹5,499"),
            ElectronicCategory(9, "Power Banks", R.drawable.ic_power_banks, "₹799")
        )
    }

    val selectedCategory = remember { mutableStateOf(electronicCategories.first()) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 12.dp, vertical = 8.dp)
    ) {
        Text(
            text = "Starting ₹2,499 | Latest electronics at great prices",
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
            items(electronicCategories.size) { index ->
                val category = electronicCategories[index]
                ElectronicCategoryItem(
                    category = category,
                    isSelected = category == selectedCategory.value,
                    onClick = { selectedCategory.value = category }
                )
            }
        }
    }
}

@Composable
fun ElectronicCategoryItem(
    category: ElectronicCategory,
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

        // Electronic Name
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
fun ElectronicCategoryPreview() {
    ElectronicCategory()
}