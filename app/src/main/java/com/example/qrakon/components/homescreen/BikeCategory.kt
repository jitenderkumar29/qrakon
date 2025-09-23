package com.example.qrakon.components.homescreen

import androidx.compose.foundation.border

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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

data class BikeCategory(
    val id: Int,
    val name: String,
    val iconRes: Int,
    val price: String // Added price property
)

@Composable
fun BikeCategoryHeader(modifier: Modifier = Modifier) {
    val bikeCategories = remember {
        listOf(
            BikeCategory(1, "Mountain Bike", R.drawable.bike_mountain, "₹70,348"),
            BikeCategory(2, "Road Bike", R.drawable.bike_road, "₹1,15,000"),
            BikeCategory(3, "Hybrid Bike", R.drawable.bike_hybrid, "₹2,60,000"),
            BikeCategory(4, "Electric Bike", R.drawable.bike_electric, "₹2,45,000"),
            BikeCategory(5, "Kids Bike", R.drawable.bike_kids, "₹95,000"),
            BikeCategory(6, "BMX Bike", R.drawable.bike_bmx, "₹78,500"),
            BikeCategory(7, "Folding Bike", R.drawable.bike_folding, "₹70,348"),
            BikeCategory(8, "Cruiser Bike", R.drawable.bike_cruiser, "₹1,15,000"),
//            BikeCategory(3,"Road Bike", R.drawable.bike_road, "$459"),
//            BikeCategory(3, "Hybrid Bike", R.drawable.bike_hybrid, "$349"),
//            BikeCategory(4, "Electric Bike", R.drawable.bike_electric, "$899"),
//            BikeCategory(5, "Kids Bike", R.drawable.bike_kids, "$199"),
//            BikeCategory(6, "BMX Bike", R.drawable.bike_bmx, "$279"),
//            BikeCategory(7, "Folding Bike", R.drawable.bike_folding, "$429"),
//            BikeCategory(8, "Cruiser Bike", R.drawable.bike_cruiser, "$379")
        )
    }

    val selectedCategory = remember { mutableStateOf(bikeCategories.first()) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 12.dp, vertical = 8.dp)
    ) {
        Text(
            text = "Starting ₹70,348 | Set off on your next great ride",
            fontSize = 16.sp,
            textAlign = TextAlign.Start,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.fillMaxWidth(),

        )

        Spacer(modifier = Modifier.height(6.dp))

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(bikeCategories.size) { index ->
                val category = bikeCategories[index]
                BikeCategoryItem(
                    category = category,
                    isSelected = category == selectedCategory.value,
                    onClick = { selectedCategory.value = category }
                )
            }
        }
    }
}

@Composable
fun BikeCategoryItem(
    category: BikeCategory,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor =  Color(0xFFF5F5F5)
    val borderColor =  Color.Transparent
//    val backgroundColor = if (isSelected) Color(0xFFE3F2FD) else Color(0xFFF5F5F5)
//    val borderColor = if (isSelected) Color(0xFF1976D2) else Color.Transparent

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

        // Bike Name
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
fun BikeCategoryPreview() {
    BikeCategoryHeader()
}