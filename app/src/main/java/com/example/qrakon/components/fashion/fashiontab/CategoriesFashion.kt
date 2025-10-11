package com.example.qrakon.components.fashion

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.unit.sp
import com.example.qrakon.R
import com.example.qrakon.ui.theme.customColors

@Composable
fun CategoriesFashionPage(onBackClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // 🔹 Top bar with back button
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.customColors.lightAccent)
                .padding(start = 12.dp, end = 12.dp, top = 2.dp, bottom = 0.dp),
//                .padding(vertical = 4.dp, horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBackClick) {
                Image(
                    painter = painterResource(id = R.drawable.ic_back), // your back icon
                    contentDescription = "Back",
                    modifier = Modifier
                        .size(28.dp)
                        .background(MaterialTheme.customColors.lightAccent)
//                        .clickable { onBack() }
                )
//                Icon(
//                    painter = painterResource(id = R.drawable.ic_back),
//                    contentDescription = "Back",
//                    tint = Color.White
//                )
            }

            Text(
                text = "Categories",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.customColors.white
            )
        }

        // 🔹 Categories List
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            item {
                Text(
                    text = "All Categories",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }
//
//            items(getFashionCategories()) { category ->
//                CategoryItem(category = category)
//            }
        }
    }
}

//@Composable
//fun CategoryItem(category: String) {
//    Box(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(vertical = 8.dp)
//            .background(Color.White)
//            .clickable {
//                // Handle category item click
//                println("Category clicked: $category")
//            }
//    ) {
//        Text(
//            text = "• $category",
//            fontSize = 16.sp,
//            fontWeight = FontWeight.Medium,
//            color = Color.Black,
//            modifier = Modifier.padding(vertical = 12.dp, horizontal = 8.dp)
//        )
//    }
//}
//
//private fun getFashionCategories(): List<String> {
//    return listOf(
//        "Fashion Accessories",
//        "Footwear",
//        "Jewelry",
//        "Watches",
//        "Bags & Wallets",
//        "Clothing",
//        "Eyewear",
//        "Belts",
//        "Hats & Caps",
//        "Scarves & Shawls",
//        "Gloves",
//        "Sunglasses",
//        "Wallets & Cardholders",
//        "Backpacks",
//        "Luggage & Travel"
//    )
//}