package com.example.qrakon.components.homescreen

import LocationAddress
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import com.example.qrakon.components.fashion.fashiontab.FashionTab
import com.example.qrakon.components.fashion.searchfashion.SearchFashion
import com.example.qrakon.components.searchbar.SearchBar
import com.example.qrakon.ui.theme.customColors

@Composable
fun HomeFashionScreen(navController: NavHostController) {
    var searchQuery by remember { mutableStateOf("") }
    var showLocationDialog by remember { mutableStateOf(false) }
    var selectedLocation by remember { mutableStateOf("Dhruv 110044") }

    val lazyListState = rememberLazyListState()

    // Handle category click
    val onCategoryClick: () -> Unit = {
        navController.navigate("fashion_categories")
    }

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            state = lazyListState,
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            // 🔵 Sticky Search Bar (pinned on scroll)
            stickyHeader {
                Surface(
                    color = MaterialTheme.customColors.lightAccent,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.customColors.lightAccent)
                            .padding(start = 12.dp, end = 12.dp, top = 8.dp, bottom = 0.dp)
//                            .padding(horizontal = 12.dp, vertical = 8.dp)
                    ) {
                        SearchFashion(
                            query = searchQuery,
                            onQueryChange = { searchQuery = it },
                            onSearch = { query ->
                                println("Searching for fashion: $query")
                                // Handle fashion search
                            },
                            onQRCodeScan = {
                                println("QR Code scan for fashion")
                                // Handle QR code scanning for fashion
                            },
                            onCategoryClick = onCategoryClick // Use the local function
                        )
                    }
                }
            }

            // 🟢 Location Section
            item {
                Surface(
                    color = MaterialTheme.customColors.darkAccent,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 0.dp, end = 0.dp, top = 0.dp, bottom = 0.dp)
                    ) {
                        LocationSelectionButton(
                            selectedLocation = selectedLocation,
                            onLocationClick = { showLocationDialog = true }
                        )
                    }
                }
            }

            // 🟢 Fashion Content Items
            item {
                FashionTab(
                    onCategorySelected = { categoryPage ->
                        // Handle category selection if needed
                        println("Category selected: $categoryPage")
                    },
                    onOpenFashionCategory = {
                        // Navigate to categories page when category icon is clicked
                        navController.navigate("fashion_categories")
                    }
                )
//                FashionTab()
            }

//            item {
//                Spacer(
//                    modifier = Modifier
//                        .height(2.dp)
//                        .fillMaxWidth()
//                        .background(MaterialTheme.customColors.spacerColor)
//                )
//            }
//
//            item {
//                FashionTrendingSection()
//            }
//
//            item {
//                Spacer(
//                    modifier = Modifier
//                        .height(2.dp)
//                        .fillMaxWidth()
//                        .background(MaterialTheme.customColors.spacerColor)
//                )
//            }
//
//            item {
//                FashionCategories()
//            }
//
//            item {
//                Spacer(
//                    modifier = Modifier
//                        .height(2.dp)
//                        .fillMaxWidth()
//                        .background(MaterialTheme.customColors.spacerColor)
//                )
//            }
//
//            item {
//                FashionCollections()
//            }
//
//            item {
//                Spacer(
//                    modifier = Modifier
//                        .height(2.dp)
//                        .fillMaxWidth()
//                        .background(MaterialTheme.customColors.spacerColor)
//                )
//            }
//
//            item {
//                FashionDeals()
//            }
        }
    }

    // 🟢 Location Dialog
    if (showLocationDialog) {
        Dialog(onDismissRequest = { showLocationDialog = false }) {
            LocationAddress(
                onBackClick = { showLocationDialog = false },
                onLocationSelected = { location ->
                    selectedLocation = location
                    showLocationDialog = false
                },
                onUseCurrentLocation = {
                    selectedLocation = "Current Location"
                    showLocationDialog = false
                }
            )
        }
    }
}

@Composable
fun FashionWelcomeSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "👗 Fashion Hub",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.customColors.primary,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Discover the latest trends and styles",
            fontSize = 16.sp,
            color = MaterialTheme.customColors.black,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun FashionTrendingSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "🔥 Trending Now",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.customColors.black
        )
        Spacer(modifier = Modifier.height(12.dp))
        // Add trending fashion items here
        Text(
            text = "• Summer Collection 2024\n• Sustainable Fashion\n• Street Style Essentials\n• Luxury Brands",
            fontSize = 14.sp,
            color = MaterialTheme.customColors.black,
            lineHeight = 20.sp
        )
    }
}

@Composable
fun FashionCategories() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "📁 Categories",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.customColors.black
        )
        Spacer(modifier = Modifier.height(12.dp))
        // Add fashion categories here
        Text(
            text = "• Women's Wear\n• Men's Fashion\n• Kids Collection\n• Accessories\n• Footwear\n• Bags & Luggage",
            fontSize = 14.sp,
            color = MaterialTheme.customColors.black,
            lineHeight = 20.sp
        )
    }
}

@Composable
fun FashionCollections() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "🎨 New Collections",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.customColors.black
        )
        Spacer(modifier = Modifier.height(12.dp))
        // Add fashion collections here
        Text(
            text = "• Bohemian Summer\n• Urban Chic\n• Classic Elegance\n• Sporty Casual",
            fontSize = 14.sp,
            color = MaterialTheme.customColors.black,
            lineHeight = 20.sp
        )
    }
}

@Composable
fun FashionDeals() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "💫 Special Offers",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.customColors.black
        )
        Spacer(modifier = Modifier.height(12.dp))
        // Add fashion deals here
        Text(
            text = "• 50% Off on Summer Dresses\n• Buy 2 Get 1 Free on Accessories\n• Free Shipping on orders above ₹999\n• New Customer Discount: 20% OFF",
            fontSize = 14.sp,
            color = MaterialTheme.customColors.black,
            lineHeight = 20.sp
        )
    }
}