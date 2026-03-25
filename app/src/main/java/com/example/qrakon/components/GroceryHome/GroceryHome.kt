package com.example.qrakon.components.GroceryHome

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import com.example.qrakon.R
import com.example.qrakon.components.homescreen.BannerFood
import com.example.qrakon.components.homescreen.BannerPadding
//import com.example.qrakon.components.homescreen.AddressSelection
//import com.example.qrakon.components.homescreen.BannerFood
//import com.example.qrakon.components.homescreen.BannerPadding
//import com.example.qrakon.components.homescreen.CashbackButton
import com.example.qrakon.components.homescreen.CategoryItem
import com.example.qrakon.components.homescreen.CategoryItemBgImg
import com.example.qrakon.components.homescreen.CategoryListBgImg
import com.example.qrakon.components.homescreen.CategoryListSimple
import com.example.qrakon.components.homescreen.DotPosition
import com.example.qrakon.components.homescreen.DynamicSpacing
import com.example.qrakon.components.homescreen.LocationSelectionButton
//import com.example.qrakon.components.homescreen.Location
import com.example.qrakon.components.homescreen.OverlayPosition
import com.example.qrakon.components.searchbar.SearchBar
import com.example.qrakon.ui.theme.customColors

@Composable
fun GroceryHome(
    navController: NavHostController?,
    modifier: Modifier = Modifier
) {
    var searchQuery by remember { mutableStateOf("") }
    var showLocationDialog by remember { mutableStateOf(false) }
    var selectedLocation by remember { mutableStateOf("Dhruv 110044") }

//    var selectedLocation by remember {
//        mutableStateOf(
//            Location(
//                house = "F 109/B",
//                street = "Block-F",
//                apartment = "5th floor, Gali no 1",
//                city = "Noida",
//                state = "UP",
//                postal = "10001",
//                country = "India"
//            )
//        )
//    }

    // Manage selected tab index
    var selectedTabIndex by remember { mutableIntStateOf(0) }
// Track selected category
    var selectedCategory by remember { mutableStateOf<GroceryCategoryPage?>(null) }

    // Listen for updates from CategoryTabsFList via SavedStateHandle
    LaunchedEffect(navController) {
        navController?.currentBackStackEntry
            ?.savedStateHandle
            ?.getStateFlow<Int?>("currentSelectedIndex", null)
            ?.collect { newIndex ->
                newIndex?.let { index ->
                    selectedTabIndex = index
                    navController.currentBackStackEntry
                        ?.savedStateHandle
                        ?.remove<Int>("currentSelectedIndex")
                }
            }
    }

    val lazyListState = rememberLazyListState()
    var isLocationVisible by remember { mutableStateOf(true) }

    // Track scroll position to hide/show location section
    LaunchedEffect(lazyListState.firstVisibleItemScrollOffset) {
        val scrollThreshold = 50
        isLocationVisible = lazyListState.firstVisibleItemScrollOffset < scrollThreshold
    }

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        LazyColumn(
            state = lazyListState,
            modifier = Modifier.fillMaxSize()
        ) {
            // 🟢 Location Section
            item {
                Surface(
                    color = MaterialTheme.customColors.darkAccent,
//                    color = bannerBackgroundColor,
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

            stickyHeader {
                Surface(
                    color = MaterialTheme.customColors.lightAccent,
//                    color = bannerBackgroundColor,
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
                            .padding(top = 0.dp, bottom = 5.dp, start = 12.dp, end = 12.dp
                            )
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
            // Location Section (hidden when scrolled)
//            item {
//                if (isLocationVisible) {
//                    Surface(
//                        color = MaterialTheme.customColors.header,
//                        modifier = Modifier.fillMaxWidth()
//                    ) {
//                        Row(
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .padding(horizontal = 12.dp, vertical = 0.dp)
//                        ) {
//                            // Location button - takes available space
//                            Box(
//                                modifier = Modifier
//                                    .weight(1f)
//                                    .padding(
//                                        top = 0.dp,
//                                        bottom = 4.dp,
//                                        end = 0.dp
//                                    )
//                                    .shadow(
//                                        elevation = 0.dp,
//                                        shape = RoundedCornerShape(8.dp),
//                                        clip = true
//                                    )
//                                    .background(
//                                        Color(0x56A7A7A7)
//                                    ),
//                            ) {
//                                LocationSelectionButton(
//                                    selectedLocation = selectedLocation,
//                                    onLocationClick = { showLocationDialog = true }
//                                )
//                            }
//
//                            // CashbackButton with dynamic width
////                            CashbackButton(
////                                amount = "1500",
////                                modifier = Modifier
////                                    .padding(
////                                        top = 0.dp,
////                                        bottom = 0.dp
////                                    )
////                                    .wrapContentSize()
////                            )
//                        }
//                    }
//                } else {
//                    Spacer(modifier = Modifier.height(0.dp))
//                }
//            }

            // Sticky Search Bar (always visible)
//            stickyHeader {
//                Surface(
//                    color = MaterialTheme.customColors.lightAccent,
//                    modifier = Modifier.fillMaxWidth()
//                ) {
//                    Row(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .background(MaterialTheme.customColors.header)
//                            .padding(horizontal = 12.dp, vertical = 0.dp)
//                    ) {
//                        Box(
//                            modifier = Modifier
//                                .weight(0.85f)
//                                .padding(
//                                    top = 4.dp,
//                                    bottom = 8.dp,
//                                    start = 0.dp,
//                                    end = 0.dp
//                                )
//                        ) {
//                            SearchBar(
//                                query = searchQuery,
//                                onQueryChange = { searchQuery = it },
//                                onSearch = { query -> println("Search performed: $query") }
//                            )
//                        }
////                        Box(
////                            modifier = Modifier
////                                .weight(0.15f)
////                                .padding(
////                                    top = 4.dp,
////                                    bottom = 8.dp,
////                                    start = 4.dp,
////                                    end = 0.dp
////                                )
////                        ) {
////                            VegNonVegButton()
////                        }
//                    }
//                }
//            }


            item {
                // Use
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White)
//                        .padding(12.dp)
                ) {
//                    Spacer(modifier = Modifier.height(10.dp))
//                    Text(
//                        text = "Recommended for you",
//                        style = MaterialTheme.typography.bodySmall.copy(
//                            fontSize = 20.sp,
//                            fontWeight = FontWeight.Bold,
//                            color = MaterialTheme.customColors.black
//                        ),
//                        maxLines = 1,
//                        modifier = Modifier.fillMaxWidth().padding(start = 12.dp)
//                    )
//                    Spacer(modifier = Modifier.height(10.dp))
                    GroceryTabs(
                        navController = navController,
                        selectedTabIndex = selectedTabIndex,
                        onCategorySelected = { category ->
                            selectedCategory = category
                            println("Selected category: ${category.title}")
                        },
                        onTabIndexChanged = { index ->
                            selectedTabIndex = index
                            println("Tab changed to index: $index")
                        }
                    )

                    // Banner Section
                    BannerFood(
                        images = listOf(
                            painterResource(id = R.drawable.all_grocery_banner1),
                            painterResource(id = R.drawable.all_grocery_banner2),
                            painterResource(id = R.drawable.all_grocery_banner3),
                            painterResource(id = R.drawable.all_grocery_banner4),
                            painterResource(id = R.drawable.all_grocery_banner5),
                        ),
                        onImageClick = { page ->
                            when (page) {
                                0 -> onBanner1Click()
                                1 -> onBanner2Click()
                                2 -> onBanner3Click()
                            }
                        },
                        autoScrollDelay = 2000,
                        height = 225.dp,
                        roundedCornerShape = 0.dp,
                        contentScale = ContentScale.FillBounds,
                        dotSize = 8.dp,
                        dotPadding = 4.dp,
                        dotPosition = DotPosition.BELOW_IMAGE,
                        overlayGradient = true, // Adds gradient for better visibility
                        selectedDotColor = Color.White,
                        padding = BannerPadding.all(8.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                    )

                    val defaultGroceryItems = listOf(
                        GroceryItemCategory(
                            id = 1,
                            title = "Fresh vegetables",
                            iconRes = R.drawable.ic_vegetables
                        ),
                        GroceryItemCategory(
                            id = 2,
                            title = "Fresh fruits",
                            iconRes = R.drawable.ic_fruits
                        ),
                        GroceryItemCategory(
                            id = 3,
                            title = "Atta, rice & grains",
                            iconRes = R.drawable.ic_grains
                        ),
                        GroceryItemCategory(
                            id = 4,
                            title = "Dal & pulses",
                            iconRes = R.drawable.ic_dal
                        ),
                        GroceryItemCategory(
                            id = 5,
                            title = "Oil & ghee",
                            iconRes = R.drawable.ic_oil
                        ),
                        GroceryItemCategory(
                            id = 6,
                            title = "Masala, sugar & spices",
                            iconRes = R.drawable.ic_spices_grocery
                        ),
                        GroceryItemCategory(
                            id = 7,
                            title = "Milk & dairy",
                            iconRes = R.drawable.ic_dairy
                        ),
                        GroceryItemCategory(
                            id = 8,
                            title = "Breads & bakery",
                            iconRes = R.drawable.ic_bakery
                        ),
                        GroceryItemCategory(
                            id = 9,
                            title = "Cereals & breakfast",
                            iconRes = R.drawable.ic_cereals
                        ),
                        GroceryItemCategory(
                            id = 10,
                            title = "Tea, coffee & drink mixes",
                            iconRes = R.drawable.ic_tea_coffee
                        ),
                        GroceryItemCategory(
                            id = 11,
                            title = "Juices & cold drinks",
                            iconRes = R.drawable.ic_juices
                        ),
                        GroceryItemCategory(
                            id = 12,
                            title = "Sauces & spreads",
                            iconRes = R.drawable.ic_sauces
                        ),
                        GroceryItemCategory(
                            id = 13,
                            title = "Dry fruits & seeds",
                            iconRes = R.drawable.ic_dry_fruits
                        ),
                        GroceryItemCategory(
                            id = 14,
                            title = "Noodles & pasta",
                            iconRes = R.drawable.ic_noodles
                        ),
                        GroceryItemCategory(
                            id = 15,
                            title = "Chips & biscuits",
                            iconRes = R.drawable.ic_chips
                        ),
                        GroceryItemCategory(
                            id = 16,
                            title = "Chocolates & ice cream",
                            iconRes = R.drawable.ic_chocolates
                        )
                    )
                    // In your screen composable
                    Spacer(modifier = Modifier.height(10.dp))
                    ListItemsGrid(
                        browseText = "Groceries & Food",
                        items = defaultGroceryItems,
                        onItemClick = { item ->
                            println("Clicked: ${item.title}")
                        },
                        columns = 4,
                        horizontalSpacing = 8.dp,
                        verticalSpacing = 1.dp,
                        itemHeight = 120.dp,
                        imageSize = 90.dp,
                        backgroundColor = MaterialTheme.customColors.background,
                        onBackClick = {}
                    )

                    val snackCategories = listOf(
                        GroceryItemCategory(1, "Chips & Namkeen", R.drawable.ic_category_chips),
                        GroceryItemCategory(2, "Sweets & Chocolates", R.drawable.ic_category_sweets),
                        GroceryItemCategory(3, "Drinks & Juices", R.drawable.ic_category_drinks),
                        GroceryItemCategory(4, "Tea, Coffee & Milk Drinks", R.drawable.ic_category_coffee),
                        GroceryItemCategory(5, "Instant Food", R.drawable.ic_category_instant),
                        GroceryItemCategory(6, "Sauces & Spreads", R.drawable.ic_category_sauces),
                        GroceryItemCategory(7, "Paan Corner", R.drawable.ic_category_paan),
                        GroceryItemCategory(8, "Ice Creams & More", R.drawable.ic_category_icecream)
                    )
                    // In your screen composable
                    Spacer(modifier = Modifier.height(10.dp))
                    ListItemsGrid(
                        browseText = "Snacks & Drinks",
                        items = snackCategories,
                        onItemClick = { item ->
                            println("Clicked: ${item.title}")
                        },
                        columns = 4,
                        horizontalSpacing = 8.dp,
                        verticalSpacing = 1.dp,
                        itemHeight = 120.dp,
                        imageSize = 90.dp,
                        backgroundColor = MaterialTheme.customColors.background,
                        onBackClick = {}
                    )

                    val defaultBeautyCareItems = listOf(
                        GroceryItemCategory(
                            id = 1,
                            title = "Bath & body",
                            iconRes = R.drawable.ic_bath_body
                        ),
                        GroceryItemCategory(
                            id = 2,
                            title = "Hair care",
                            iconRes = R.drawable.ic_hair_care
                        ),
                        GroceryItemCategory(
                            id = 3,
                            title = "Skin & face",
                            iconRes = R.drawable.ic_skin_face
                        ),
                        GroceryItemCategory(
                            id = 4,
                            title = "Deos & perfumes",
                            iconRes = R.drawable.ic_deos_perfumes
                        ),
                        GroceryItemCategory(
                            id = 5,
                            title = "Feminine hygiene",
                            iconRes = R.drawable.ic_feminine_hygiene
                        ),
                        GroceryItemCategory(
                            id = 6,
                            title = "Men's grooming",
                            iconRes = R.drawable.ic_mens_grooming
                        ),
                        GroceryItemCategory(
                            id = 7,
                            title = "Oral care",
                            iconRes = R.drawable.ic_oral_care
                        ),
                        GroceryItemCategory(
                            id = 8,
                            title = "Baby care",
                            iconRes = R.drawable.ic_baby_care
                        ),
                        GroceryItemCategory(
                            id = 9,
                            title = "Makeup & cosmetics",
                            iconRes = R.drawable.ic_makeup_cosmetics
                        ),
                        GroceryItemCategory(
                            id = 10,
                            title = "Pharma & wellness",
                            iconRes = R.drawable.ic_pharma_wellness
                        ),
                        GroceryItemCategory(
                            id = 11,
                            title = "Diet & nutrition",
                            iconRes = R.drawable.ic_diet_nutrition
                        ),
                        GroceryItemCategory(
                            id = 12,
                            title = "Pet care",
                            iconRes = R.drawable.ic_pet_care
                        )
                    )
                    // In your screen composable
                    Spacer(modifier = Modifier.height(10.dp))
                    ListItemsGrid(
                        browseText = "Beauty & Personal Care",
                        items = defaultBeautyCareItems,
                        onItemClick = { item ->
                            println("Clicked: ${item.title}")
                        },
                        columns = 4,
                        horizontalSpacing = 8.dp,
                        verticalSpacing = 1.dp,
                        itemHeight = 120.dp,
                        imageSize = 90.dp,
                        backgroundColor = MaterialTheme.customColors.background,
                        onBackClick = {}
                    )

                    val householdItems = listOf(
                        GroceryItemCategory(
                            id = 1,
                            title = "Laundry detergents",
                            iconRes = R.drawable.ic_laundry_detergent
                        ),
                        GroceryItemCategory(
                            id = 2,
                            title = "Household & toilet cleaners",
                            iconRes = R.drawable.ic_toilet_cleaner
                        ),
                        GroceryItemCategory(
                            id = 3,
                            title = "Cleaning supplies",
                            iconRes = R.drawable.ic_cleaning_supplies
                        ),
                        GroceryItemCategory(
                            id = 4,
                            title = "Home & kitchen",
                            iconRes = R.drawable.ic_home_kitchen
                        )
                    )
                    // In your screen composable
                    Spacer(modifier = Modifier.height(10.dp))
                    ListItemsGrid(
                        browseText = "Household essentials",
                        items = householdItems,
                        onItemClick = { item ->
                            println("Clicked: ${item.title}")
                        },
                        columns = 4,
                        horizontalSpacing = 8.dp,
                        verticalSpacing = 1.dp,
                        itemHeight = 120.dp,
                        imageSize = 90.dp,
                        backgroundColor = MaterialTheme.customColors.background,
                        onBackClick = {}
                    )

                    val storeCategories = listOf(
                        GroceryItemCategory(
                            id = 1,
                            title = "Local delights",
                            iconRes = R.drawable.ic_local_delights // Replace with actual icon
                        ),
                        GroceryItemCategory(
                            id = 2,
                            title = "Millets",
                            iconRes = R.drawable.ic_millets // Replace with actual icon
                        ),
                        GroceryItemCategory(
                            id = 3,
                            title = "Combos",
                            iconRes = R.drawable.ic_combos // Replace with actual icon
                        ),
                        GroceryItemCategory(
                            id = 4,
                            title = "Pooja essentials",
                            iconRes = R.drawable.ic_pooja_essentials // Replace with actual icon
                        ),
                        GroceryItemCategory(
                            id = 5,
                            title = "Premium & organic",
                            iconRes = R.drawable.ic_premium_organic // Replace with actual icon
                        ),
                        GroceryItemCategory(
                            id = 6,
                            title = "Beauty",
                            iconRes = R.drawable.ic_beauty // Replace with actual icon
                        ),
                        GroceryItemCategory(
                            id = 7,
                            title = "Pet care",
                            iconRes = R.drawable.ic_pet_care_1 // Replace with actual icon
                        ),
                        GroceryItemCategory(
                            id = 8,
                            title = "Brand of the week",
                            iconRes = R.drawable.ic_brand_week // Replace with actual icon
                        )
                    )
                    // In your screen composable
                    Spacer(modifier = Modifier.height(10.dp))
                    ListItemsGrid(
                        browseText = "Shop by store",
                        items = storeCategories,
                        onItemClick = { item ->
                            println("Clicked: ${item.title}")
                        },
                        columns = 4,
                        horizontalSpacing = 8.dp,
                        verticalSpacing = 1.dp,
                        itemHeight = 120.dp,
                        imageSize = 90.dp,
                        backgroundColor = MaterialTheme.customColors.background,
                        onBackClick = {}
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                }
            }
            // Additional content items would go here
            // Add your product listings, categories, etc.
        }
    }

    // Location Dialog
//    if (showLocationDialog) {
//        Dialog(onDismissRequest = { showLocationDialog = false }) {
//            AddressSelection(
//                onDismissRequest = { showLocationDialog = false },
//                onAddressSelected = { location ->
//                    selectedLocation = location
//                    showLocationDialog = false
//                },
//                onUseCurrentLocation = {
//                    selectedLocation = Location(
//                        house = "",
//                        street = "Current Location",
//                        apartment = "",
//                        city = "",
//                        state = "",
//                        postal = "",
//                        country = ""
//                    )
//                    showLocationDialog = false
//                },
//                onSearchByArea = {
//                    selectedLocation = Location(
//                        house = "",
//                        street = "Searched Area",
//                        apartment = "",
//                        city = "Noida",
//                        state = "UP",
//                        postal = "",
//                        country = "India"
//                    )
//                    showLocationDialog = false
//                },
//                onAddNewAddress = {
//                    showLocationDialog = false
//                }
//            )
//        }
//    }
}

// Helper function if you need to use different amounts
//@Composable
//fun CashbackButtonWrapper(
//    amount: String = "0",
//    modifier: Modifier = Modifier
//) {
//    CashbackButton(
//        amount = amount,
//        modifier = modifier
//    )
//}

private fun onBanner1Click() {
    // Handle banner 1 click
    println("Banner 1 clicked")
}

private fun onBanner2Click() {
    // Handle banner 2 click
    println("Banner 2 clicked")
}

private fun onBanner3Click() {
    // Handle banner 3 click
    println("Banner 3 clicked")
}