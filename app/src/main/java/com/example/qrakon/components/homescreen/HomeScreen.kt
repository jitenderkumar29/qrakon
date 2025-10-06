package com.example.qrakon.components.homescreen

import LocationAddress
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.qrakon.components.searchbar.SearchBar
import com.example.qrakon.ui.theme.customColors
import com.example.qrakon.components.categorytabs.CategoryTabs


@Composable
fun HomeScreen() {
    var searchQuery by remember { mutableStateOf("") }
    var showLocationDialog by remember { mutableStateOf(false) }
    var selectedLocation by remember { mutableStateOf("Dhruv 110044") }

    val lazyListState = rememberLazyListState()

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            state = lazyListState,
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            // 🟢 Category Header FIRST (scrolls away with content)
//            item {
//                Surface(
//                    color = MaterialTheme.customColors.lightAccent,
//                    modifier = Modifier.fillMaxWidth()
//                ) {
//                    CategoryHeader(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                    )
////                    CategoryHeader()
////                    CategoryHeader(
////                        modifier = Modifier.padding(vertical = 0.dp)
////                    )
//                }
//            }

            // 🔵 Sticky Search Bar (pinned on scroll)
            stickyHeader {
                Surface(
                    color = MaterialTheme.customColors.lightAccent,
//                    color = Color.Transparent,
                    modifier = Modifier.fillMaxWidth()
//                        .background(
//                            brush = Brush.verticalGradient(
//                                colors = listOf(
//                                    Color(0xFF8B2B2D),
//                                    Color(0xFF923839)
//                                )
//                            )
//                        )
                ) {
//                    Column {
                        // ✅ Status bar spacer
//                        Spacer(
//                            modifier = Modifier
//                                .windowInsetsTopHeight(WindowInsets.statusBars)
//                                .background(MaterialTheme.customColors.lightAccent)
//                        )

                        // 🔍 Sticky Search Bar
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 12.dp, vertical = 0.dp)
                        ) {
                            SearchBar(
                                query = searchQuery,
                                onQueryChange = { searchQuery = it },
                                onSearch = { query -> println("Search performed: $query") }
                            )
                        }
//                    }
                }
            }

            // 🟢 Location Section
            item {
                Surface(
                    color = MaterialTheme.customColors.darkAccent,
//                    color = Color.Transparent,
                    modifier = Modifier.fillMaxWidth()
//                    .background(
//                        brush = Brush.verticalGradient(
//                            colors = listOf(
//                                Color(0xFF903E3F),
//                                MaterialTheme.customColors.darkAccent
//                            )
//                        )
//                    )
                ) {
                    LocationSelectionButton(
                        selectedLocation = selectedLocation,
                        onLocationClick = { showLocationDialog = true }
                    )
                }
            }

            // 🟢 Category Tabs Items
            item {
                CategoryTabs()
            }

//            item {
//                MoreCategoryPage()
//            }

            // 🟢 Content Items
//            item {
//                Spacer(
//                    modifier = Modifier
//                        .height(0.20.dp)
//                        .fillMaxWidth()
//                        .background(MaterialTheme.customColors.spacerColor)
//                )
//            }
//            item {
//                BannerHome(
//                    onJoinPrimeClick = { println("Prime button clicked!") },
//                    onDealBoxClick = { println("Deal section clicked!") }
//                )
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
//                QrakonPayScreen()
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
//                AdsSponsored(
//                    onAdClick = {
//                        // Handle ad click here, e.g., navigate to product details
//                        println("Ad clicked!")
//                    }
//                )
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
//                CategoryProducts(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .wrapContentHeight()
//                )
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
//            item { BikeCategoryHeader() }
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
//            item { ElectronicCategory() }
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
//            item { FurnitureCategory() }
//
//            item {
//                Spacer(
//                    modifier = Modifier
//                        .height(2.dp)
//                        .fillMaxWidth()
//                        .background(MaterialTheme.customColors.spacerColor)
//                )
//            }
//            item { ClothsCategory() }
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
