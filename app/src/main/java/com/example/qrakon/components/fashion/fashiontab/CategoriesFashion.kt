package com.example.qrakon.components.fashion.fashiontab

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.qrakon.R
import com.example.qrakon.components.fashion.searchfashion.SearchCategoriesF
import com.example.qrakon.ui.theme.customColors
import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import com.example.qrakon.components.fashion.fashiontab.CategoriesFTab

@Composable
fun CategoriesFashionPage(
    onBackClick: () -> Unit,
    navController: NavHostController // Add navController parameter
) {
    var searchQuery by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // 🔹 Top bar with back button and search
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.customColors.lightAccent)
                .padding(start = 12.dp, end = 12.dp, top = 5.dp, bottom = 5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBackClick) {
                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .shadow(
                            elevation = 3.dp,
                            shape = CircleShape,
                            clip = true
                        )
                        .clip(CircleShape)
                        .background(MaterialTheme.customColors.white)
                        .border(
                            width = 1.5.dp,
                            color = MaterialTheme.colorScheme.primary,
                            shape = CircleShape
                        ),
//                        .clickable { onCategoryClick() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_close_24),
                        contentDescription = "Categories",
                        tint = MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier.size(24.dp)
                    )
                }
//                Image(
//                    painter = painterResource(id = R.drawable.ic_back),
//                    contentDescription = "Back",
//                    modifier = Modifier.size(28.dp)
//                )
            }



            // 🔹 SearchCategoriesF component
            SearchCategoriesF(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp), // Add some spacing between back button and search
                query = searchQuery,
                onQueryChange = { newQuery ->
                    searchQuery = newQuery
                },
                onSearch = { query ->
                    // Handle search in categories
                    filterCategories(query)
                },
                onQRCodeScan = {
                    // Navigate to QR scanner or handle QR code
                    navController.navigate("qr_scanner")
                },
                onCategoryClick = {
                    // This is already the categories page, so you might want to:
                    // 1. Show a sub-categories menu
                    // 2. Refresh categories
                    // 3. Or do nothing since we're already on categories page
                    showSubCategoriesMenu()
                },
                placeholder = "Search categories..."
            )
        }

        // 🔹 Categories List
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
//                .padding(16.dp)
        ) {
            item {
                CategoriesFTab()

//                Text(
//                    text = "All Categories",
//                    fontSize = 18.sp,
//                    fontWeight = FontWeight.Bold,
//                    color = MaterialTheme.colorScheme.primary,
//                    modifier = Modifier.padding(bottom = 16.dp)
//                )
            }

            // Example categories list
//            items(getFashionCategories()) { category ->
//                CategoryItem(category = category, onCategoryClick = {
//                    // Handle individual category item click
//                    navigateToCategoryDetails(category, navController)
//                })
//            }
        }
    }
}

// Helper functions
private fun filterCategories(query: String) {
    // Implement your category filtering logic here
    println("Filtering categories with: $query")
}

private fun showSubCategoriesMenu() {
    // Implement sub-categories menu logic
    println("Showing sub-categories menu")
}

private fun navigateToCategoryDetails(category: String, navController: NavHostController) {
    // Navigate to category details screen
    navController.navigate("category_details/$category")
}

// Category Item Composable
@Composable
fun CategoryItem(category: String, onCategoryClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .background(Color.White)
            .clickable(onClick = onCategoryClick)
    ) {
        Text(
            text = "• $category",
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Black,
            modifier = Modifier.padding(vertical = 12.dp, horizontal = 8.dp)
        )
    }
}

// Sample categories data
private fun getFashionCategories(): List<String> {
    return listOf(
        "Fashion Accessories",
        "Footwear",
        "Jewelry",
        "Watches",
        "Bags & Wallets",
        "Clothing",
        "Eyewear",
        "Belts",
        "Hats & Caps",
        "Scarves & Shawls",
        "Gloves",
        "Sunglasses",
        "Wallets & Cardholders",
        "Backpacks",
        "Luggage & Travel"
    )
}

// Don't forget to import clickable
