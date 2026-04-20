package com.example.qrakon.components.restaurants

import DisclaimerFood
import ViewCartButton
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.qrakon.MainActivity
import com.example.qrakon.R
import androidx.navigation.NavHostController
import com.example.hufko.components.restaurants.RestaurantItemDetails
import com.example.hufko.components.restaurants.RestaurantItemDetails2
import com.example.qrakon.components.fashion.searchfashion.SearchFashion
import com.example.qrakon.components.homescreen.FoodScreen
import com.example.qrakon.components.restaurants.RestaurantItemDetails3
import com.example.qrakon.components.searchbar.SearchBar
import com.example.qrakon.ui.theme.customColors
import kotlin.collections.chunked
import kotlin.collections.forEach

/**
 * Food item data class with all optional fields
 */
data class FoodItemDoubleF(
    val id: Int? = null,
    val imageRes: List<Int>? = null,
    val title: String? = null,
    val price: String? = null,
    val originalPrice: String? = null,
    val restaurantName: String? = null,
    val rating: String? = null,
    val deliveryTime: String? = null,
    val distance: String? = null,
    val discount: String? = null,
    val discountAmount: String? = null,
    val address: String? = null,
    val calories: String? = null,
    val protein: String? = null,
    val isHighProtein: Boolean? = null,
    val category: String? = null,
    val isWishlisted: Boolean? = false,
    val description: String? = null,
    val quantity: String? = null,
    val infoIcon: Int? = null,
    val highlyReordered: String? = null,
    val reorderedQuantity: String? = null,
    val bestSeller: Boolean? = false,
    val toppicks: Boolean? = false,
    val freedelivery: Boolean? = false,
    val recommended: Boolean? = false,
    val combo: Boolean? = false,
    val moredetailsbutton: Boolean? = false,
    val salad: Boolean? = false,
    val above259: Boolean? = false,
    val recommendedWithOutProtein: Boolean? = false,
    val crazy: Boolean? = false,
    val bigValue : Boolean? = false,
    val superSaver : Boolean? = false,
)

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RestaurantDetails(
    onBackClick: () -> Unit,
    navController: NavHostController,
    restaurantItem: TopRatedRestaurantItem? = null,
    category: String = "all"
) {
    var searchQuery by remember { mutableStateOf("") }
    var showDropdownMenu by remember { mutableStateOf(false) }

    // ✅ USE THE CENTRALIZED DATA from RestaurantData
    val allFoodItems = RestaurantData.allFoodItems

    // Filter food items based on category
    val foodItems = remember(category, allFoodItems) {
        if (category == "all" || category.isEmpty()) {
            allFoodItems
        } else {
            allFoodItems.filter { it.category == category }
        }
    }

    // Filter items by their flags
    val topPicksItems = remember(foodItems) { foodItems.filter { it.toppicks == true } }
    val freeDeliveryItems = remember(foodItems) { foodItems.filter { it.freedelivery == true  &&
            it.above259 != true} }
    val freeDeliveryItemsAbove259 = remember(foodItems) { foodItems.filter { it.freedelivery == true &&
            it.above259 == true} }
    val recommendedItems = remember(foodItems) { foodItems.filter { it.recommended == true } }
    val comboItems = remember(foodItems) { foodItems.filter { it.combo == true } }
    val moredetailsbuttonItems = remember(foodItems) { foodItems.filter { it.moredetailsbutton == true } }
    val saladItems = remember(foodItems) { foodItems.filter { it.salad == true } }
    val recommendedWithOutProteinItems = remember(foodItems) { foodItems.filter { it.recommendedWithOutProtein == true } }
    val crazyItems = remember(foodItems) { foodItems.filter { it.crazy == true } }
    val bigValueItems = remember(foodItems) { foodItems.filter { it.bigValue == true } }
    val superSaverItems = remember(foodItems) { foodItems.filter { it.superSaver == true } }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.customColors.white),
        bottomBar = {
//            if (cartItemCount > 0) {
                ViewCartButton(
//                    itemCount = cartItemCount,
                    onViewCartClick = {
                        // Navigate to CheckOutFood
                        // For example with Compose Navigation:
                        navController.navigate("checkout_food")
                        // Or with Activity navigation:
                        // context.startActivity(Intent(context, CheckOutFoodActivity::class.java))
                    }
                )
//            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.customColors.white)
                .padding(paddingValues)
        ) {
            // Item 1: Top Bar
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.customColors.blackHeader)
                        .padding(start = 12.dp, end = 12.dp, top = 5.dp, bottom = 0.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_back),
                            contentDescription = "Back",
                            tint = MaterialTheme.customColors.white,
                            modifier = Modifier.size(28.dp)
                        )
                    }
                    if (restaurantItem?.premium == "premium") {
                        Icon(
                            painter = painterResource(id = R.drawable.premium),
                            contentDescription = "Back",
                            tint = Color.Unspecified,
                            modifier = Modifier.height(35.dp)
                        )
                    }
                    else {
                        Icon(
                            painter = painterResource(id = R.drawable.ultra_premium),
                            contentDescription = "Back",
                            tint = Color.Unspecified,
                            modifier = Modifier.height(35.dp)
                        )
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    Button(
                        onClick = { },
                        modifier = Modifier
                            .height(32.dp)
                            .padding(start = 8.dp)
                            .widthIn(min = 90.dp),
                        shape = MaterialTheme.shapes.extraLarge,
                        contentPadding = PaddingValues(horizontal = 8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.customColors.blackHeader,
                            contentColor = MaterialTheme.customColors.white
                        ),
                        border = BorderStroke(2.dp, MaterialTheme.customColors.white)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_people),
                            contentDescription = "Group Order Icon",
                            modifier = Modifier.size(24.dp)
                        )
                        Text(
                            text = "Group Order",
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.customColors.white,
                            fontSize = 17.sp
                        )
                    }

                    Box(modifier = Modifier.size(42.dp)) {
                        IconButton(onClick = { showDropdownMenu = !showDropdownMenu }) {
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_more_vert_24),
                                contentDescription = "More options",
                                tint = MaterialTheme.customColors.white,
                                modifier = Modifier.size(28.dp)
                            )
                        }

                        DropdownMenu(
                            expanded = showDropdownMenu,
                            onDismissRequest = { showDropdownMenu = false },
                            modifier = Modifier.background(MaterialTheme.customColors.white)
                        ) {
                            DropdownMenuItem(
                                text = { Text("Share", fontSize = 14.sp) },
                                onClick = { showDropdownMenu = false },
                                leadingIcon = {
                                    Icon(
                                        painter = painterResource(id = R.drawable.outline_share_24),
                                        contentDescription = null,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("Save to favorites", fontSize = 14.sp) },
                                onClick = { showDropdownMenu = false },
                                leadingIcon = {
                                    Icon(
                                        painter = painterResource(id = R.drawable.outline_favorite_24),
                                        contentDescription = null,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            )
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        "Report",
                                        fontSize = 14.sp,
                                        color = MaterialTheme.colorScheme.error
                                    )
                                },
                                onClick = { showDropdownMenu = false },
                                leadingIcon = {
                                    Icon(
                                        painter = painterResource(id = R.drawable.outline_report_24),
                                        contentDescription = null,
                                        modifier = Modifier.size(20.dp),
                                        tint = MaterialTheme.colorScheme.error
                                    )
                                }
                            )
                        }
                    }
                }
            }

            // Item 2: Restaurant Content
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(
                            RoundedCornerShape(
                                topStart = 0.dp,
                                topEnd = 0.dp,
                                bottomStart = 30.dp,
                                bottomEnd = 30.dp
                            )
                        )
                ) {
                    if (restaurantItem != null) {
                        RestaurantCard(
                            item = restaurantItem,  // Pass single item, not a list
                            onItemClick = { item ->
                                // Navigate to restaurant details
                                navController.navigate("restaurant_details/${item.id}/${item.category}")
                            },
                            onInfoIconClick = { item ->
                                // Navigate to restaurant info page
                                item.restaurantName?.let {
                                    navController.navigate("restaurant_info/$it")
                                }
                            },
                            onScheduleLaterClick = { item ->
                                // Handle schedule later click
                                // You can show a toast, log, or perform any other action
                                // The bottom sheet will automatically open from within RestaurantCard
                                println("Schedule later clicked for ${item.restaurantName}")

                                // Optional: You can also track analytics or perform additional logic
                                // trackEvent("schedule_later_clicked", mapOf("restaurant_id" to item.id))
                            }
                        )
                    } else {
                        // Handle case when restaurantItem is null - maybe show a placeholder or don't render
                        Box(modifier = Modifier.fillMaxWidth()) {
                            Text(
                                text = "No restaurant data available",
                                modifier = Modifier.padding(16.dp),
                                color = Color.Gray
                            )
                        }
                    }
                }
            }

            // Item 3: Sticky Header - Search Bar
            stickyHeader {
                Surface(
                    color = MaterialTheme.customColors.header,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.customColors.white)
                            .padding(top = 8.dp, bottom = 8.dp, start = 12.dp, end = 12.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
//                            .weight(0.85f)
                                .clip(RoundedCornerShape(8.dp))
                                .background(MaterialTheme.customColors.white)
                        ) {
                            SearchBarRestaurant(
                                query = searchQuery,
                                placeholder = "Search for dishes",
                                backgroundColor = Color(0xFFE5E7E6),
//                            backgroundColor = MaterialTheme.customColors.spacerColor,
                                onQueryChange = { searchQuery = it },
                                qrIconColorMike = MaterialTheme.customColors.orangeLight, // Changed to onPrimary for better contrast
                                onSearch = { query -> println("Search performed: $query") }
                            )
//                        SearchBar(
//                            query = searchQuery,
//                            placeholder = "Search for dishes",
//                            backgroundColor = MaterialTheme.customColors.spacerColor,
//                            onQueryChange = { searchQuery = it },
//                            qrIconColor = Color(0xFFE5E7E6), // Changed to onPrimary for better contrast
//                            onSearch = { query -> println("Search performed: $query") }
//                        )
                        }

//                    Surface(
//                        shape = RoundedCornerShape(8.dp),
//                        color = MaterialTheme.customColors.skyBlue,
//                        modifier = Modifier
//                            .weight(0.15f)
//                            .size(width = 45.dp, height = 45.dp)
//                    ) {
//                        Row(
//                            horizontalArrangement = Arrangement.Center,
//                            verticalAlignment = Alignment.CenterVertically,
//                            modifier = Modifier.fillMaxSize()
//                        ) {
//                            Image(
//                                painter = painterResource(id = R.drawable.ic_people),
//                                contentDescription = "Group Order Icon",
//                                modifier = Modifier.size(30.dp),
//                                colorFilter = ColorFilter.tint(MaterialTheme.customColors.darkAccent2)
//                            )
//                        }
//                    }
                    }
                }
                val restFilters = FilterConfig(
                    filters = listOf(
                        FilterChip(
                            "filters",
                            "Filters",
                            FilterType.FILTER_DROPDOWN,
                            R.drawable.ic_filter,
                            R.drawable.outline_keyboard_arrow_down_24
                        ),
                        FilterChip("veg", "Veg", FilterType.WITH_LEFT_ICON, R.drawable.ic_veg_rest),
                        FilterChip("egg", "Egg", FilterType.WITH_LEFT_ICON, R.drawable.ic_egg_rest),
                        FilterChip(
                            "non_veg",
                            "Non-Veg",
                            FilterType.WITH_LEFT_ICON,
                            R.drawable.ic_non_veg_rest
                        ),
                        FilterChip(
                            "eat_right",
                            "EatRight",
                            FilterType.WITH_LEFT_ICON,
                            R.drawable.ic_eat_right_rest
                        ),
                        FilterChip("rating_4+", "Rating 4.0+", FilterType.TEXT_ONLY),
                        FilterChip(
                            "highly_reordered",
                            "Highly Reordered",
                            FilterType.WITH_LEFT_ICON,
                            R.drawable.ic_highly_reordered_rest
                        ),
                        FilterChip(
                            "spicy",
                            "Spicy",
                            FilterType.WITH_LEFT_ICON,
                            R.drawable.ic_spicy_rest
                        ),
                        FilterChip(
                            "kids_choice",
                            "Kid's choice",
                            FilterType.WITH_LEFT_ICON,
                            R.drawable.ic_kids_choice_rest
                        ),
                        FilterChip("buy_1_get_1", "Buy 1 Get 1", FilterType.TEXT_ONLY),
                        FilterChip("50_off", "50% Off", FilterType.TEXT_ONLY),
                        FilterChip(
                            "schedule",
                            "Schedule",
                            FilterType.SORT_DROPDOWN,
                            rightIcon = R.drawable.outline_keyboard_arrow_down_24
                        ),
                    ),
                    rows = 1,
                    chipHeight = 35,
                    cornerRadius = 8,
                    chipPadding = 8
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.customColors.white)
                        .padding(top = 0.dp, bottom = 0.dp, start = 0.dp, end = 0.dp)
                ) {
                    FilterButtonFood(
                        filterConfig = restFilters,
                        onFilterClick = { println("Filter clicked: ${it.text}") },
                        onSortClick = { println("Sort clicked") }
                    )
                }
            }

            // Divider
            item {
                Divider(
                    color = Color.LightGray,
                    thickness = 1.dp,
                    modifier = Modifier.padding(vertical = 0.dp)
                )
            }

//            North Indian Category
            if (restaurantItem?.category == "northindian") {
                // ==================== TOP PICKS SECTION ====================
                if (topPicksItems.isNotEmpty()) {
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(MaterialTheme.customColors.white)
                                .padding(12.dp)
                        ) {
                            Text(
                                text = "Top Picks",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.customColors.black
                                ),
                                maxLines = 1,
                                modifier = Modifier.fillMaxWidth().padding(start = 12.dp)
                            )
                            Spacer(modifier = Modifier.height(15.dp))
                            RestaurantItemDetails3(
                                items = topPicksItems,
                                onAddClick = { item ->
                                    println("Added: ${item.title}")
                                }
                            )
                        }
                    }
                    // Divider
                    item {
                        Divider(
                            color = Color.LightGray,
                            thickness = 1.dp,
                            modifier = Modifier.padding(vertical = 0.dp)
                        )
                    }
                }

                // ==================== FREE DELIVERY SECTION (Grid) ====================
                if (freeDeliveryItems.isNotEmpty()) {
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(MaterialTheme.customColors.white)
                                .padding(12.dp)
                        ) {
                            Text(
                                text = "Free Delivery",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.customColors.black
                                ),
                                maxLines = 1,
                                modifier = Modifier.fillMaxWidth().padding(start = 12.dp)
                            )
                            Spacer(modifier = Modifier.height(15.dp))
                            val rows = freeDeliveryItems.chunked(2)
                            rows.forEach { rowItems ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(bottom = 12.dp),
                                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                                ) {
                                    rowItems.forEach { item ->
                                        RestaurantItemDetails2(
                                            item = item,
                                            modifier = Modifier.weight(1f),
                                            onAddClick = { println("Added: ${item.title}") }
                                        )
                                    }
                                    if (rowItems.size == 1) {
                                        Spacer(modifier = Modifier.weight(1f))
                                    }
                                }
                            }
                        }
                    }
                    // Divider
                    item {
                        Divider(
                            color = Color.LightGray,
                            thickness = 1.dp,
                            modifier = Modifier.padding(vertical = 0.dp)
                        )
                    }
                }

                // ==================== RECOMMENDED WITHOUT PROTEIN SECTION (Grid) ====================
                if (recommendedWithOutProteinItems.isNotEmpty()) {
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(MaterialTheme.customColors.white)
                                .padding(12.dp)
                        ) {
                            Text(
                                text = "Recommended (20)",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.customColors.black
                                ),
                                maxLines = 1,
                                modifier = Modifier.fillMaxWidth().padding(start = 12.dp)
                            )
                            Spacer(modifier = Modifier.height(15.dp))
                            val rows = recommendedWithOutProteinItems.chunked(2)
                            rows.forEach { rowItems ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(bottom = 12.dp),
                                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                                ) {
                                    rowItems.forEach { item ->
                                        RestaurantItemDetails2(
                                            item = item,
                                            modifier = Modifier.weight(1f),
                                            onAddClick = { println("Added: ${item.title}") }
                                        )
                                    }
                                    if (rowItems.size == 1) {
                                        Spacer(modifier = Modifier.weight(1f))
                                    }
                                }
                            }
                        }
                    }
                    // Divider
                    item {
                        Divider(
                            color = Color.LightGray,
                            thickness = 1.dp,
                            modifier = Modifier.padding(vertical = 0.dp)
                        )
                    }
                }

                // ==================== COMBO SECTION (Multiple Images) ====================
                if (comboItems.isNotEmpty()) {
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(MaterialTheme.customColors.white)
                        ) {
                            Spacer(modifier = Modifier.height(15.dp))
                            Text(
                                text = "Combo For You",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.customColors.black
                                ),
                                maxLines = 1,
                                modifier = Modifier.fillMaxWidth().padding(start = 12.dp)
                            )
                            Spacer(modifier = Modifier.height(15.dp))
                            comboItems.forEach { item ->
                                RestaurantItemDetails(
                                    item = item,
                                    showMultipleImages = true,
                                    onAddClick = { println("Added: ${item.title}") }
                                )
                            }
                        }
                    }
                }

                // ==================== CRAZY SECTION (Single Image) ====================
                if (crazyItems.isNotEmpty()) {
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(MaterialTheme.customColors.white)
                        ) {
                            Spacer(modifier = Modifier.height(15.dp))
                            Text(
                                text = "Main Course",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.customColors.black
                                ),
                                maxLines = 1,
                                modifier = Modifier.fillMaxWidth().padding(start = 12.dp)
                            )
                            Spacer(modifier = Modifier.height(15.dp))
                            crazyItems.forEach { item ->
                                RestaurantItemDetails(
                                    item = item,
                                    showMultipleImages = false,
                                    onAddClick = { println("Added: ${item.title}") }
                                )
                            }
                        }
                    }
                }

                // ==================== RECOMMENDED SECTION (Single Image) ====================
                if (recommendedItems.isNotEmpty()) {
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(MaterialTheme.customColors.white)
                        ) {
                            Spacer(modifier = Modifier.height(15.dp))
                            Text(
                                text = "Breads",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.customColors.black
                                ),
                                maxLines = 1,
                                modifier = Modifier.fillMaxWidth().padding(start = 12.dp)
                            )
                            Spacer(modifier = Modifier.height(15.dp))
                            recommendedItems.forEach { item ->
                                RestaurantItemDetails(
                                    item = item,
                                    showMultipleImages = false,
                                    onAddClick = { println("Added: ${item.title}") }
                                )
                            }
                        }
                    }
                }

                // ==================== BIG VALUE / Accompaniments SECTION (Single Image) ====================
                if (bigValueItems.isNotEmpty()) {
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(MaterialTheme.customColors.white)
                        ) {
                            Spacer(modifier = Modifier.height(15.dp))
                            Text(
                                text = "Accompaniments",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.customColors.black
                                ),
                                maxLines = 1,
                                modifier = Modifier.fillMaxWidth().padding(start = 12.dp)
                            )
                            Spacer(modifier = Modifier.height(15.dp))
                            bigValueItems.forEach { item ->
                                RestaurantItemDetails(
                                    item = item,
                                    showMultipleImages = false,
                                    onAddClick = { println("Added: ${item.title}") }
                                )
                            }
                        }
                    }
                }

                // ==================== SUPER SAVER SECTION (Single Image) ====================
                if (superSaverItems.isNotEmpty()) {
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(MaterialTheme.customColors.white)
                        ) {
                            Text(
                                text = "Rice",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.customColors.black
                                ),
                                maxLines = 1,
                                modifier = Modifier.fillMaxWidth().padding(start = 12.dp)
                            )
                            Spacer(modifier = Modifier.height(15.dp))

                            // This will display ALL items
                            superSaverItems.forEach { item ->
                                RestaurantItemDetails(
                                    item = item,
                                    showMultipleImages = false,
                                    onAddClick = { println("Added: ${item.title}") }
                                )
                            }
                        }
                    }
                }



                // ==================== FREE DELIVERY ITEMS STARTING AT 289 SECTION (Grid) ====================
                if (freeDeliveryItemsAbove259.isNotEmpty()) {
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(MaterialTheme.customColors.white)
                                .padding(12.dp)
                        ) {
                            Text(
                                text = "Items starting at 259 (18)",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.customColors.black
                                ),
                                maxLines = 1,
                                modifier = Modifier.fillMaxWidth().padding(start = 12.dp)
                            )
                            Spacer(modifier = Modifier.height(15.dp))
                            val rows = freeDeliveryItemsAbove259.chunked(2)
                            rows.forEach { rowItems ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(bottom = 12.dp),
                                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                                ) {
                                    rowItems.forEach { item ->
                                        RestaurantItemDetails2(
                                            item = item,
                                            modifier = Modifier.weight(1f),
                                            onAddClick = { println("Added: ${item.title}") }
                                        )
                                    }
                                    if (rowItems.size == 1) {
                                        Spacer(modifier = Modifier.weight(1f))
                                    }
                                }
                            }
                        }
                    }
                    // Divider
                    item {
                        Divider(
                            color = Color.LightGray,
                            thickness = 1.dp,
                            modifier = Modifier.padding(vertical = 0.dp)
                        )
                    }
                }

                // ==================== MORE DETAILS BUTTON SECTION (Single Image) ====================
                if (moredetailsbuttonItems.isNotEmpty()) {
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(MaterialTheme.customColors.white)
                        ) {
                            Text(
                                text = "Big Party Meal For You",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.customColors.black
                                ),
                                maxLines = 1,
                                modifier = Modifier.fillMaxWidth().padding(start = 12.dp)
                            )
                            Spacer(modifier = Modifier.height(15.dp))
                            moredetailsbuttonItems.forEach { item ->
                                RestaurantItemDetails(
                                    item = item,
                                    showMultipleImages = false,
                                    onAddClick = { println("Added: ${item.title}") }
                                )
                            }
                        }
                    }
                }

                // ==================== MORE DETAILS BUTTON SECTION (Single Images) ====================
                if (saladItems.isNotEmpty()) {
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(MaterialTheme.customColors.white)
                                .padding(vertical = 12.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Text(
                                text = "Special Combo For You",
//                        text = "Salad For You",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.customColors.black
                                ),
                                maxLines = 1,
                                modifier = Modifier.fillMaxWidth().padding(start = 12.dp)
                            )
                            saladItems.forEach { item ->
                                RestaurantItemDetails(
                                    item = item,
                                    showMultipleImages = false,
                                    onAddClick = { println("Added: ${item.title}") }
                                )
                            }
                        }
                    }
                }

                // Empty state when no items found
                if (foodItems.isEmpty()) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(32.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_no_data),
                                    contentDescription = "No items found",
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .size(250.dp),
                                    tint = Color.Unspecified
                                )
                                Text(
                                    text = "No items available",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = Color.Gray
                                )
                                Text(
                                    text = "Try changing your filters or check back later",
                                    fontSize = 14.sp,
                                    color = Color.LightGray,
                                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                                )
                            }
                        }
                    }
                }
            }
//            Burger Category
            else if (restaurantItem?.category == "burger"){
                // ==================== TOP PICKS SECTION ====================
                if (topPicksItems.isNotEmpty()) {
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(MaterialTheme.customColors.white)
                                .padding(12.dp)
                        ) {
                            Text(
                                text = "Top Picks",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.customColors.black
                                ),
                                maxLines = 1,
                                modifier = Modifier.fillMaxWidth().padding(start = 12.dp)
                            )
                            Spacer(modifier = Modifier.height(15.dp))
                            RestaurantItemDetails3(
                                items = topPicksItems,
                                onAddClick = { item ->
                                    println("Added: ${item.title}")
                                }
                            )
                        }
                    }
                    // Divider
                    item {
                        Divider(
                            color = Color.LightGray,
                            thickness = 1.dp,
                            modifier = Modifier.padding(vertical = 0.dp)
                        )
                    }
                }

                // ==================== FREE DELIVERY SECTION (Grid) ====================
                if (freeDeliveryItems.isNotEmpty()) {
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(MaterialTheme.customColors.white)
                                .padding(12.dp)
                        ) {
                            Text(
                                text = "Free Delivery",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.customColors.black
                                ),
                                maxLines = 1,
                                modifier = Modifier.fillMaxWidth().padding(start = 12.dp)
                            )
                            Spacer(modifier = Modifier.height(15.dp))
                            val rows = freeDeliveryItems.chunked(2)
                            rows.forEach { rowItems ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(bottom = 12.dp),
                                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                                ) {
                                    rowItems.forEach { item ->
                                        RestaurantItemDetails2(
                                            item = item,
                                            modifier = Modifier.weight(1f),
                                            onAddClick = { println("Added: ${item.title}") }
                                        )
                                    }
                                    if (rowItems.size == 1) {
                                        Spacer(modifier = Modifier.weight(1f))
                                    }
                                }
                            }
                        }
                    }
                    // Divider
                    item {
                        Divider(
                            color = Color.LightGray,
                            thickness = 1.dp,
                            modifier = Modifier.padding(vertical = 0.dp)
                        )
                    }
                }
                // ==================== RECOMMENDED WITHOUT PROTEIN SECTION (Grid) ====================
                if (recommendedWithOutProteinItems.isNotEmpty()) {
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(MaterialTheme.customColors.white)
                                .padding(12.dp)
                        ) {
                            Text(
                                text = "Recommended (20)",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.customColors.black
                                ),
                                maxLines = 1,
                                modifier = Modifier.fillMaxWidth().padding(start = 12.dp)
                            )
                            Spacer(modifier = Modifier.height(15.dp))
                            val rows = recommendedWithOutProteinItems.chunked(2)
                            rows.forEach { rowItems ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(bottom = 12.dp),
                                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                                ) {
                                    rowItems.forEach { item ->
                                        RestaurantItemDetails2(
                                            item = item,
                                            modifier = Modifier.weight(1f),
                                            onAddClick = { println("Added: ${item.title}") }
                                        )
                                    }
                                    if (rowItems.size == 1) {
                                        Spacer(modifier = Modifier.weight(1f))
                                    }
                                }
                            }
                        }
                    }
                    // Divider
                    item {
                        Divider(
                            color = Color.LightGray,
                            thickness = 1.dp,
                            modifier = Modifier.padding(vertical = 0.dp)
                        )
                    }
                }

                // ==================== FREE DELIVERY ITEMS STARTING AT 289 SECTION (Grid) ====================
                if (freeDeliveryItemsAbove259.isNotEmpty()) {
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(MaterialTheme.customColors.white)
                                .padding(12.dp)
                        ) {
                            Text(
                                text = "Items starting at 259 (18)",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.customColors.black
                                ),
                                maxLines = 1,
                                modifier = Modifier.fillMaxWidth().padding(start = 12.dp)
                            )
                            Spacer(modifier = Modifier.height(15.dp))
                            val rows = freeDeliveryItemsAbove259.chunked(2)
                            rows.forEach { rowItems ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(bottom = 12.dp),
                                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                                ) {
                                    rowItems.forEach { item ->
                                        RestaurantItemDetails2(
                                            item = item,
                                            modifier = Modifier.weight(1f),
                                            onAddClick = { println("Added: ${item.title}") }
                                        )
                                    }
                                    if (rowItems.size == 1) {
                                        Spacer(modifier = Modifier.weight(1f))
                                    }
                                }
                            }
                        }
                    }
                    // Divider
                    item {
                        Divider(
                            color = Color.LightGray,
                            thickness = 1.dp,
                            modifier = Modifier.padding(vertical = 0.dp)
                        )
                    }
                }

                // ==================== RECOMMENDED SECTION (Single Image) ====================
                if (recommendedItems.isNotEmpty()) {
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(MaterialTheme.customColors.white)
                        ) {
                            Spacer(modifier = Modifier.height(15.dp))
                            Text(
                                text = "Recommended For You",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.customColors.black
                                ),
                                maxLines = 1,
                                modifier = Modifier.fillMaxWidth().padding(start = 12.dp)
                            )
                            Spacer(modifier = Modifier.height(15.dp))
                            recommendedItems.forEach { item ->
                                RestaurantItemDetails(
                                    item = item,
                                    showMultipleImages = false,
                                    onAddClick = { println("Added: ${item.title}") }
                                )
                            }
                        }
                    }
                }
                // ==================== CRAZY SECTION (Single Image) ====================
                if (crazyItems.isNotEmpty()) {
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(MaterialTheme.customColors.white)
                        ) {
                            Spacer(modifier = Modifier.height(15.dp))
                            Text(
                                text = "Crazy Burger Box",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.customColors.black
                                ),
                                maxLines = 1,
                                modifier = Modifier.fillMaxWidth().padding(start = 12.dp)
                            )
                            Spacer(modifier = Modifier.height(15.dp))
                            crazyItems.forEach { item ->
                                RestaurantItemDetails(
                                    item = item,
                                    showMultipleImages = false,
                                    onAddClick = { println("Added: ${item.title}") }
                                )
                            }
                        }
                    }
                }
                // ==================== BIG VALUE SECTION (Single Image) ====================
                if (bigValueItems.isNotEmpty()) {
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(MaterialTheme.customColors.white)
                        ) {
                            Text(
                                text = "Big Value Burger",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.customColors.black
                                ),
                                maxLines = 1,
                                modifier = Modifier.fillMaxWidth().padding(start = 12.dp)
                            )
                            Spacer(modifier = Modifier.height(15.dp))
                            bigValueItems.forEach { item ->
                                RestaurantItemDetails(
                                    item = item,
                                    showMultipleImages = false,
                                    onAddClick = { println("Added: ${item.title}") }
                                )
                            }
                        }
                    }
                }
                // ==================== SUPER SAVER SECTION (Single Image) ====================
                if (superSaverItems.isNotEmpty()) {
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(MaterialTheme.customColors.white)
                        ) {
                            Text(
                                text = "Super Saver Burger",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.customColors.black
                                ),
                                maxLines = 1,
                                modifier = Modifier.fillMaxWidth().padding(start = 12.dp)
                            )
                            Spacer(modifier = Modifier.height(15.dp))

                            // This will display ALL items
                            superSaverItems.forEach { item ->
                                RestaurantItemDetails(
                                    item = item,
                                    showMultipleImages = false,
                                    onAddClick = { println("Added: ${item.title}") }
                                )
                            }
                        }
                    }
                }

                // ==================== COMBO SECTION (Multiple Images) ====================
                if (comboItems.isNotEmpty()) {
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(MaterialTheme.customColors.white)
                        ) {
                            Text(
                                text = "Combo For You",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.customColors.black
                                ),
                                maxLines = 1,
                                modifier = Modifier.fillMaxWidth().padding(start = 12.dp)
                            )
                            Spacer(modifier = Modifier.height(15.dp))
                            comboItems.forEach { item ->
                                RestaurantItemDetails(
                                    item = item,
                                    showMultipleImages = true,
                                    onAddClick = { println("Added: ${item.title}") }
                                )
                            }
                        }
                    }
                }
                // ==================== MORE DETAILS BUTTON SECTION (Single Image) ====================
                if (moredetailsbuttonItems.isNotEmpty()) {
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(MaterialTheme.customColors.white)
                        ) {
                            Text(
                                text = "Big Party Meal For You",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.customColors.black
                                ),
                                maxLines = 1,
                                modifier = Modifier.fillMaxWidth().padding(start = 12.dp)
                            )
                            Spacer(modifier = Modifier.height(15.dp))
                            moredetailsbuttonItems.forEach { item ->
                                RestaurantItemDetails(
                                    item = item,
                                    showMultipleImages = false,
                                    onAddClick = { println("Added: ${item.title}") }
                                )
                            }
                        }
                    }
                }

                // ==================== MORE DETAILS BUTTON SECTION (Single Images) ====================
                if (saladItems.isNotEmpty()) {
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(MaterialTheme.customColors.white)
                                .padding(vertical = 12.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Text(
                                text = "Special Combo For You",
//                        text = "Salad For You",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.customColors.black
                                ),
                                maxLines = 1,
                                modifier = Modifier.fillMaxWidth().padding(start = 12.dp)
                            )
                            saladItems.forEach { item ->
                                RestaurantItemDetails(
                                    item = item,
                                    showMultipleImages = false,
                                    onAddClick = { println("Added: ${item.title}") }
                                )
                            }
                        }
                    }
                }

                // Empty state when no items found
                if (foodItems.isEmpty()) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(32.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_no_data),
                                    contentDescription = "No items found",
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .size(250.dp),
                                    tint = Color.Unspecified
                                )
                                Text(
                                    text = "No items available",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = Color.Gray
                                )
                                Text(
                                    text = "Try changing your filters or check back later",
                                    fontSize = 14.sp,
                                    color = Color.LightGray,
                                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                                )
                            }
                        }
                    }
                }
            }
//            Biryani Category
            else if (restaurantItem?.category == "biryani") {
                // ==================== TOP PICKS SECTION ====================
                if (topPicksItems.isNotEmpty()) {
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(MaterialTheme.customColors.white)
                                .padding(12.dp)
                        ) {
                            Text(
                                text = "Top Picks",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.customColors.black
                                ),
                                maxLines = 1,
                                modifier = Modifier.fillMaxWidth().padding(start = 12.dp)
                            )
                            Spacer(modifier = Modifier.height(15.dp))
                            RestaurantItemDetails3(
                                items = topPicksItems,
                                onAddClick = { item ->
                                    println("Added: ${item.title}")
                                }
                            )
                        }
                    }
                    // Divider
                    item {
                        Divider(
                            color = Color.LightGray,
                            thickness = 1.dp,
                            modifier = Modifier.padding(vertical = 0.dp)
                        )
                    }
                }

                // ==================== FREE DELIVERY SECTION (Grid) ====================
                if (freeDeliveryItems.isNotEmpty()) {
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(MaterialTheme.customColors.white)
                                .padding(12.dp)
                        ) {
                            Text(
                                text = "Free Delivery",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.customColors.black
                                ),
                                maxLines = 1,
                                modifier = Modifier.fillMaxWidth().padding(start = 12.dp)
                            )
                            Spacer(modifier = Modifier.height(15.dp))
                            val rows = freeDeliveryItems.chunked(2)
                            rows.forEach { rowItems ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(bottom = 12.dp),
                                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                                ) {
                                    rowItems.forEach { item ->
                                        RestaurantItemDetails2(
                                            item = item,
                                            modifier = Modifier.weight(1f),
                                            onAddClick = { println("Added: ${item.title}") }
                                        )
                                    }
                                    if (rowItems.size == 1) {
                                        Spacer(modifier = Modifier.weight(1f))
                                    }
                                }
                            }
                        }
                    }
                    // Divider
                    item {
                        Divider(
                            color = Color.LightGray,
                            thickness = 1.dp,
                            modifier = Modifier.padding(vertical = 0.dp)
                        )
                    }
                }
                // ==================== RECOMMENDED WITHOUT PROTEIN SECTION (Grid) ====================
                if (recommendedWithOutProteinItems.isNotEmpty()) {
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(MaterialTheme.customColors.white)
                                .padding(12.dp)
                        ) {
                            Text(
                                text = "Recommended (20)",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.customColors.black
                                ),
                                maxLines = 1,
                                modifier = Modifier.fillMaxWidth().padding(start = 12.dp)
                            )
                            Spacer(modifier = Modifier.height(15.dp))
                            val rows = recommendedWithOutProteinItems.chunked(2)
                            rows.forEach { rowItems ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(bottom = 12.dp),
                                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                                ) {
                                    rowItems.forEach { item ->
                                        RestaurantItemDetails2(
                                            item = item,
                                            modifier = Modifier.weight(1f),
                                            onAddClick = { println("Added: ${item.title}") }
                                        )
                                    }
                                    if (rowItems.size == 1) {
                                        Spacer(modifier = Modifier.weight(1f))
                                    }
                                }
                            }
                        }
                    }
                    // Divider
                    item {
                        Divider(
                            color = Color.LightGray,
                            thickness = 1.dp,
                            modifier = Modifier.padding(vertical = 0.dp)
                        )
                    }
                }

                // ==================== FREE DELIVERY ITEMS STARTING AT 289 SECTION (Grid) ====================
                if (freeDeliveryItemsAbove259.isNotEmpty()) {
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(MaterialTheme.customColors.white)
                                .padding(12.dp)
                        ) {
                            Text(
                                text = "Items starting at 259 (18)",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.customColors.black
                                ),
                                maxLines = 1,
                                modifier = Modifier.fillMaxWidth().padding(start = 12.dp)
                            )
                            Spacer(modifier = Modifier.height(15.dp))
                            val rows = freeDeliveryItemsAbove259.chunked(2)
                            rows.forEach { rowItems ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(bottom = 12.dp),
                                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                                ) {
                                    rowItems.forEach { item ->
                                        RestaurantItemDetails2(
                                            item = item,
                                            modifier = Modifier.weight(1f),
                                            onAddClick = { println("Added: ${item.title}") }
                                        )
                                    }
                                    if (rowItems.size == 1) {
                                        Spacer(modifier = Modifier.weight(1f))
                                    }
                                }
                            }
                        }
                    }
                    // Divider
                    item {
                        Divider(
                            color = Color.LightGray,
                            thickness = 1.dp,
                            modifier = Modifier.padding(vertical = 0.dp)
                        )
                    }
                }

                // ==================== RECOMMENDED SECTION (Single Image) ====================
                if (recommendedItems.isNotEmpty()) {
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(MaterialTheme.customColors.white)
                        ) {
                            Spacer(modifier = Modifier.height(15.dp))
                            Text(
                                text = "Recommended For You",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.customColors.black
                                ),
                                maxLines = 1,
                                modifier = Modifier.fillMaxWidth().padding(start = 12.dp)
                            )
                            Spacer(modifier = Modifier.height(15.dp))
                            recommendedItems.forEach { item ->
                                RestaurantItemDetails(
                                    item = item,
                                    showMultipleImages = false,
                                    onAddClick = { println("Added: ${item.title}") }
                                )
                            }
                        }
                    }
                }
                // ==================== CRAZY SECTION (Single Image) ====================
                if (crazyItems.isNotEmpty()) {
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(MaterialTheme.customColors.white)
                        ) {
                            Spacer(modifier = Modifier.height(15.dp))
                            Text(
                                text = "Crazy Biryani Box",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.customColors.black
                                ),
                                maxLines = 1,
                                modifier = Modifier.fillMaxWidth().padding(start = 12.dp)
                            )
                            Spacer(modifier = Modifier.height(15.dp))
                            crazyItems.forEach { item ->
                                RestaurantItemDetails(
                                    item = item,
                                    showMultipleImages = false,
                                    onAddClick = { println("Added: ${item.title}") }
                                )
                            }
                        }
                    }
                }
                // ==================== BIG VALUE SECTION (Single Image) ====================
                if (bigValueItems.isNotEmpty()) {
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(MaterialTheme.customColors.white)
                        ) {
                            Text(
                                text = "Big Value Biryani Bowl 500ml",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.customColors.black
                                ),
                                maxLines = 1,
                                modifier = Modifier.fillMaxWidth().padding(start = 12.dp)
                            )
                            Spacer(modifier = Modifier.height(15.dp))
                            bigValueItems.forEach { item ->
                                RestaurantItemDetails(
                                    item = item,
                                    showMultipleImages = false,
                                    onAddClick = { println("Added: ${item.title}") }
                                )
                            }
                        }
                    }
                }
                // ==================== SUPER SAVER SECTION (Single Image) ====================
                if (superSaverItems.isNotEmpty()) {
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(MaterialTheme.customColors.white)
                        ) {
                            Text(
                                text = "Super Saver Biryani Bowl 500ml",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.customColors.black
                                ),
                                maxLines = 1,
                                modifier = Modifier.fillMaxWidth().padding(start = 12.dp)
                            )
                            Spacer(modifier = Modifier.height(15.dp))

                            // This will display ALL items
                            superSaverItems.forEach { item ->
                                RestaurantItemDetails(
                                    item = item,
                                    showMultipleImages = false,
                                    onAddClick = { println("Added: ${item.title}") }
                                )
                            }
                        }
                    }
                }

                // ==================== COMBO SECTION (Multiple Images) ====================
                if (comboItems.isNotEmpty()) {
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(MaterialTheme.customColors.white)
                        ) {
                            Text(
                                text = "Combo For You",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.customColors.black
                                ),
                                maxLines = 1,
                                modifier = Modifier.fillMaxWidth().padding(start = 12.dp)
                            )
                            Spacer(modifier = Modifier.height(15.dp))
                            comboItems.forEach { item ->
                                RestaurantItemDetails(
                                    item = item,
                                    showMultipleImages = true,
                                    onAddClick = { println("Added: ${item.title}") }
                                )
                            }
                        }
                    }
                }
                // ==================== MORE DETAILS BUTTON SECTION (Single Image) ====================
                if (moredetailsbuttonItems.isNotEmpty()) {
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(MaterialTheme.customColors.white)
                        ) {
                            Text(
                                text = "Big Party Meal For You",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.customColors.black
                                ),
                                maxLines = 1,
                                modifier = Modifier.fillMaxWidth().padding(start = 12.dp)
                            )
                            Spacer(modifier = Modifier.height(15.dp))
                            moredetailsbuttonItems.forEach { item ->
                                RestaurantItemDetails(
                                    item = item,
                                    showMultipleImages = false,
                                    onAddClick = { println("Added: ${item.title}") }
                                )
                            }
                        }
                    }
                }

                // ==================== MORE DETAILS BUTTON SECTION (Single Images) ====================
                if (saladItems.isNotEmpty()) {
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(MaterialTheme.customColors.white)
                                .padding(vertical = 12.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Text(
                                text = "Special Combo For You",
//                        text = "Salad For You",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.customColors.black
                                ),
                                maxLines = 1,
                                modifier = Modifier.fillMaxWidth().padding(start = 12.dp)
                            )
                            saladItems.forEach { item ->
                                RestaurantItemDetails(
                                    item = item,
                                    showMultipleImages = false,
                                    onAddClick = { println("Added: ${item.title}") }
                                )
                            }
                        }
                    }
                }

                // Empty state when no items found
                if (foodItems.isEmpty()) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(32.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_no_data),
                                    contentDescription = "No items found",
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .size(250.dp),
                                    tint = Color.Unspecified
                                )
                                Text(
                                    text = "No items available",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = Color.Gray
                                )
                                Text(
                                    text = "Try changing your filters or check back later",
                                    fontSize = 14.sp,
                                    color = Color.LightGray,
                                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                                )
                            }
                        }
                    }
                }
            }

            //            Sanjha Chulha North Indian Category
            else if (restaurantItem?.category == "northindiansc") {
                // ==================== TOP PICKS SECTION ====================
                if (topPicksItems.isNotEmpty()) {
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(MaterialTheme.customColors.white)
                                .padding(12.dp)
                        ) {
                            Text(
                                text = "Top Picks",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.customColors.black
                                ),
                                maxLines = 1,
                                modifier = Modifier.fillMaxWidth().padding(start = 12.dp)
                            )
                            Spacer(modifier = Modifier.height(15.dp))
                            RestaurantItemDetails3(
                                items = topPicksItems,
                                onAddClick = { item ->
                                    println("Added: ${item.title}")
                                }
                            )
                        }
                    }
                    // Divider
                    item {
                        Divider(
                            color = Color.LightGray,
                            thickness = 1.dp,
                            modifier = Modifier.padding(vertical = 0.dp)
                        )
                    }
                }

                // ==================== FREE DELIVERY SECTION (Grid) ====================
                if (freeDeliveryItems.isNotEmpty()) {
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(MaterialTheme.customColors.white)
                                .padding(12.dp)
                        ) {
                            Text(
                                text = "Free Delivery",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.customColors.black
                                ),
                                maxLines = 1,
                                modifier = Modifier.fillMaxWidth().padding(start = 12.dp)
                            )
                            Spacer(modifier = Modifier.height(15.dp))
                            val rows = freeDeliveryItems.chunked(2)
                            rows.forEach { rowItems ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(bottom = 12.dp),
                                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                                ) {
                                    rowItems.forEach { item ->
                                        RestaurantItemDetails2(
                                            item = item,
                                            modifier = Modifier.weight(1f),
                                            onAddClick = { println("Added: ${item.title}") }
                                        )
                                    }
                                    if (rowItems.size == 1) {
                                        Spacer(modifier = Modifier.weight(1f))
                                    }
                                }
                            }
                        }
                    }
                    // Divider
                    item {
                        Divider(
                            color = Color.LightGray,
                            thickness = 1.dp,
                            modifier = Modifier.padding(vertical = 0.dp)
                        )
                    }
                }

                // ==================== CRAZY / Indian Main Course SECTION (Single Image) ====================
                if (crazyItems.isNotEmpty()) {
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(MaterialTheme.customColors.white)
                        ) {
                            Spacer(modifier = Modifier.height(15.dp))
                            Text(
                                text = "Indian Main Course",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.customColors.black
                                ),
                                maxLines = 1,
                                modifier = Modifier.fillMaxWidth().padding(start = 12.dp)
                            )
                            Spacer(modifier = Modifier.height(15.dp))
                            crazyItems.forEach { item ->
                                RestaurantItemDetails(
                                    item = item,
                                    showMultipleImages = false,
                                    onAddClick = { println("Added: ${item.title}") }
                                )
                            }
                        }
                    }
                }

                // ==================== RECOMMENDED / Indian Starters SECTION (Single Image) ====================
                if (recommendedItems.isNotEmpty()) {
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(MaterialTheme.customColors.white)
                        ) {
                            Spacer(modifier = Modifier.height(15.dp))
                            Text(
                                text = "Indian Starters",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.customColors.black
                                ),
                                maxLines = 1,
                                modifier = Modifier.fillMaxWidth().padding(start = 12.dp)
                            )
                            Spacer(modifier = Modifier.height(15.dp))
                            recommendedItems.forEach { item ->
                                RestaurantItemDetails(
                                    item = item,
                                    showMultipleImages = false,
                                    onAddClick = { println("Added: ${item.title}") }
                                )
                            }
                        }
                    }
                }

                // ==================== BIG VALUE / Sanjha Rolls & Momos SECTION (Single Image) ====================
                if (bigValueItems.isNotEmpty()) {
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(MaterialTheme.customColors.white)
                        ) {
                            Text(
                                text = "Sanjha Rolls & Momos",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.customColors.black
                                ),
                                maxLines = 1,
                                modifier = Modifier.fillMaxWidth().padding(start = 12.dp)
                            )
                            Spacer(modifier = Modifier.height(15.dp))
                            bigValueItems.forEach { item ->
                                RestaurantItemDetails(
                                    item = item,
                                    showMultipleImages = false,
                                    onAddClick = { println("Added: ${item.title}") }
                                )
                            }
                        }
                    }
                }

                // ==================== SUPER SAVER / Chinese Starters SECTION (Single Image) ====================
                if (superSaverItems.isNotEmpty()) {
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(MaterialTheme.customColors.white)
                        ) {
                            Text(
                                text = "Chinese Starters",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.customColors.black
                                ),
                                maxLines = 1,
                                modifier = Modifier.fillMaxWidth().padding(start = 12.dp)
                            )
                            Spacer(modifier = Modifier.height(15.dp))

                            // This will display ALL items
                            superSaverItems.forEach { item ->
                                RestaurantItemDetails(
                                    item = item,
                                    showMultipleImages = false,
                                    onAddClick = { println("Added: ${item.title}") }
                                )
                            }
                        }
                    }
                }

                // ==================== FREE DELIVERY / Tawa Special ITEMS STARTING AT 289 SECTION (Grid) ====================
                if (freeDeliveryItemsAbove259.isNotEmpty()) {
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(MaterialTheme.customColors.white)
                                .padding(12.dp)
                        ) {
                            Text(
                                text = "Tawa Special",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.customColors.black
                                ),
                                maxLines = 1,
                                modifier = Modifier.fillMaxWidth().padding(start = 12.dp)
                            )
                            Spacer(modifier = Modifier.height(15.dp))
                            val rows = freeDeliveryItemsAbove259.chunked(2)
                            rows.forEach { rowItems ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(bottom = 12.dp),
                                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                                ) {
                                    rowItems.forEach { item ->
                                        RestaurantItemDetails2(
                                            item = item,
                                            modifier = Modifier.weight(1f),
                                            onAddClick = { println("Added: ${item.title}") }
                                        )
                                    }
                                    if (rowItems.size == 1) {
                                        Spacer(modifier = Modifier.weight(1f))
                                    }
                                }
                            }
                        }
                    }
                    // Divider
                    item {
                        Divider(
                            color = Color.LightGray,
                            thickness = 1.dp,
                            modifier = Modifier.padding(vertical = 0.dp)
                        )
                    }
                }

                // ==================== COMBO / Indian Breads SECTION (Multiple Images) ====================
                if (comboItems.isNotEmpty()) {
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(MaterialTheme.customColors.white)
                        ) {
                            Spacer(modifier = Modifier.height(15.dp))
                            Text(
                                text = "Indian Breads",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.customColors.black
                                ),
                                maxLines = 1,
                                modifier = Modifier.fillMaxWidth().padding(start = 12.dp)
                            )
                            Spacer(modifier = Modifier.height(15.dp))
                            comboItems.forEach { item ->
                                RestaurantItemDetails(
                                    item = item,
                                    showMultipleImages = true,
                                    onAddClick = { println("Added: ${item.title}") }
                                )
                            }
                        }
                    }
                }

                // ==================== Fried Rice & Noodle / MORE DETAILS BUTTON SECTION (Single Images) ====================
                if (saladItems.isNotEmpty()) {
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(MaterialTheme.customColors.white)
                                .padding(vertical = 12.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Text(
                                text = "Fried Rice & Noodle",
//                        text = "Salad For You",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.customColors.black
                                ),
                                maxLines = 1,
                                modifier = Modifier.fillMaxWidth().padding(start = 12.dp)
                            )
                            saladItems.forEach { item ->
                                RestaurantItemDetails(
                                    item = item,
                                    showMultipleImages = false,
                                    onAddClick = { println("Added: ${item.title}") }
                                )
                            }
                        }
                    }
                }


                // ==================== RECOMMENDED WITHOUT PROTEIN SECTION (Grid) ====================
                if (recommendedWithOutProteinItems.isNotEmpty()) {
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(MaterialTheme.customColors.white)
                                .padding(12.dp)
                        ) {
                            Text(
                                text = "Recommended (20)",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.customColors.black
                                ),
                                maxLines = 1,
                                modifier = Modifier.fillMaxWidth().padding(start = 12.dp)
                            )
                            Spacer(modifier = Modifier.height(15.dp))
                            val rows = recommendedWithOutProteinItems.chunked(2)
                            rows.forEach { rowItems ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(bottom = 12.dp),
                                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                                ) {
                                    rowItems.forEach { item ->
                                        RestaurantItemDetails2(
                                            item = item,
                                            modifier = Modifier.weight(1f),
                                            onAddClick = { println("Added: ${item.title}") }
                                        )
                                    }
                                    if (rowItems.size == 1) {
                                        Spacer(modifier = Modifier.weight(1f))
                                    }
                                }
                            }
                        }
                    }
                    // Divider
                    item {
                        Divider(
                            color = Color.LightGray,
                            thickness = 1.dp,
                            modifier = Modifier.padding(vertical = 0.dp)
                        )
                    }
                }

//                // ==================== FREE DELIVERY ITEMS STARTING AT 289 SECTION (Grid) ====================
//                if (freeDeliveryItemsAbove259.isNotEmpty()) {
//                    item {
//                        Column(
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .background(MaterialTheme.customColors.white)
//                                .padding(12.dp)
//                        ) {
//                            Text(
//                                text = "Items starting at 259",
//                                style = MaterialTheme.typography.bodySmall.copy(
//                                    fontSize = 20.sp,
//                                    fontWeight = FontWeight.Bold,
//                                    color = MaterialTheme.customColors.black
//                                ),
//                                maxLines = 1,
//                                modifier = Modifier.fillMaxWidth().padding(start = 12.dp)
//                            )
//                            Spacer(modifier = Modifier.height(15.dp))
//                            val rows = freeDeliveryItemsAbove259.chunked(2)
//                            rows.forEach { rowItems ->
//                                Row(
//                                    modifier = Modifier
//                                        .fillMaxWidth()
//                                        .padding(bottom = 12.dp),
//                                    horizontalArrangement = Arrangement.spacedBy(12.dp)
//                                ) {
//                                    rowItems.forEach { item ->
//                                        RestaurantItemDetails2(
//                                            item = item,
//                                            modifier = Modifier.weight(1f),
//                                            onAddClick = { println("Added: ${item.title}") }
//                                        )
//                                    }
//                                    if (rowItems.size == 1) {
//                                        Spacer(modifier = Modifier.weight(1f))
//                                    }
//                                }
//                            }
//                        }
//                    }
//                    // Divider
//                    item {
//                        Divider(
//                            color = Color.LightGray,
//                            thickness = 1.dp,
//                            modifier = Modifier.padding(vertical = 0.dp)
//                        )
//                    }
//                }

//                // ==================== COMBO / Indian Breads SECTION (Multiple Images) ====================
//                if (comboItems.isNotEmpty()) {
//                    item {
//                        Column(
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .background(MaterialTheme.customColors.white)
//                        ) {
//                            Text(
//                                text = "Indian Breads",
//                                style = MaterialTheme.typography.bodySmall.copy(
//                                    fontSize = 20.sp,
//                                    fontWeight = FontWeight.Bold,
//                                    color = MaterialTheme.customColors.black
//                                ),
//                                maxLines = 1,
//                                modifier = Modifier.fillMaxWidth().padding(start = 12.dp)
//                            )
//                            Spacer(modifier = Modifier.height(15.dp))
//                            comboItems.forEach { item ->
//                                RestaurantItemDetails(
//                                    item = item,
//                                    showMultipleImages = false,
//                                    onAddClick = { println("Added: ${item.title}") }
//                                )
//                            }
//                        }
//                    }
//                }




                // ==================== MORE DETAILS BUTTON SECTION (Single Image) ====================
                if (moredetailsbuttonItems.isNotEmpty()) {
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(MaterialTheme.customColors.white)
                        ) {
                            Text(
                                text = "Big Party Meal For You",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.customColors.black
                                ),
                                maxLines = 1,
                                modifier = Modifier.fillMaxWidth().padding(start = 12.dp)
                            )
                            Spacer(modifier = Modifier.height(15.dp))
                            moredetailsbuttonItems.forEach { item ->
                                RestaurantItemDetails(
                                    item = item,
                                    showMultipleImages = false,
                                    onAddClick = { println("Added: ${item.title}") }
                                )
                            }
                        }
                    }
                }


                // Empty state when no items found
                if (foodItems.isEmpty()) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(32.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_no_data),
                                    contentDescription = "No items found",
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .size(250.dp),
                                    tint = Color.Unspecified
                                )
                                Text(
                                    text = "No items available",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = Color.Gray
                                )
                                Text(
                                    text = "Try changing your filters or check back later",
                                    fontSize = 14.sp,
                                    color = Color.LightGray,
                                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                                )
                            }
                        }
                    }
                }
            }

//            Pizza Category
            else if (restaurantItem?.category == "pizza") {
                // ==================== TOP PICKS SECTION ====================
                if (topPicksItems.isNotEmpty()) {
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(MaterialTheme.customColors.white)
                                .padding(12.dp)
                        ) {
                            Text(
                                text = "Top Picks",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.customColors.black
                                ),
                                maxLines = 1,
                                modifier = Modifier.fillMaxWidth().padding(start = 12.dp)
                            )
                            Spacer(modifier = Modifier.height(15.dp))
                            RestaurantItemDetails3(
                                items = topPicksItems,
                                onAddClick = { item ->
                                    println("Added: ${item.title}")
                                }
                            )
                        }
                    }
                    // Divider
                    item {
                        Divider(
                            color = Color.LightGray,
                            thickness = 1.dp,
                            modifier = Modifier.padding(vertical = 0.dp)
                        )
                    }
                }

                // ==================== FREE DELIVERY SECTION (Grid) ====================
                if (freeDeliveryItems.isNotEmpty()) {
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(MaterialTheme.customColors.white)
                                .padding(12.dp)
                        ) {
                            Text(
                                text = "Free Delivery",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.customColors.black
                                ),
                                maxLines = 1,
                                modifier = Modifier.fillMaxWidth().padding(start = 12.dp)
                            )
                            Spacer(modifier = Modifier.height(15.dp))
                            val rows = freeDeliveryItems.chunked(2)
                            rows.forEach { rowItems ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(bottom = 12.dp),
                                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                                ) {
                                    rowItems.forEach { item ->
                                        RestaurantItemDetails2(
                                            item = item,
                                            modifier = Modifier.weight(1f),
                                            onAddClick = { println("Added: ${item.title}") }
                                        )
                                    }
                                    if (rowItems.size == 1) {
                                        Spacer(modifier = Modifier.weight(1f))
                                    }
                                }
                            }
                        }
                    }
                    // Divider
                    item {
                        Divider(
                            color = Color.LightGray,
                            thickness = 1.dp,
                            modifier = Modifier.padding(vertical = 0.dp)
                        )
                    }
                }

                // ==================== RECOMMENDED WITHOUT PROTEIN SECTION (Grid) ====================
                if (recommendedWithOutProteinItems.isNotEmpty()) {
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(MaterialTheme.customColors.white)
                                .padding(12.dp)
                        ) {
                            Text(
                                text = "Recommended (20)",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.customColors.black
                                ),
                                maxLines = 1,
                                modifier = Modifier.fillMaxWidth().padding(start = 12.dp)
                            )
                            Spacer(modifier = Modifier.height(15.dp))
                            val rows = recommendedWithOutProteinItems.chunked(2)
                            rows.forEach { rowItems ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(bottom = 12.dp),
                                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                                ) {
                                    rowItems.forEach { item ->
                                        RestaurantItemDetails2(
                                            item = item,
                                            modifier = Modifier.weight(1f),
                                            onAddClick = { println("Added: ${item.title}") }
                                        )
                                    }
                                    if (rowItems.size == 1) {
                                        Spacer(modifier = Modifier.weight(1f))
                                    }
                                }
                            }
                        }
                    }
                    // Divider
                    item {
                        Divider(
                            color = Color.LightGray,
                            thickness = 1.dp,
                            modifier = Modifier.padding(vertical = 0.dp)
                        )
                    }
                }

                // ==================== FREE DELIVERY ITEMS STARTING AT 289 SECTION (Grid) ====================
                if (freeDeliveryItemsAbove259.isNotEmpty()) {
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(MaterialTheme.customColors.white)
                                .padding(12.dp)
                        ) {
                            Text(
                                text = "Items starting at 259 (18)",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.customColors.black
                                ),
                                maxLines = 1,
                                modifier = Modifier.fillMaxWidth().padding(start = 12.dp)
                            )
                            Spacer(modifier = Modifier.height(15.dp))
                            val rows = freeDeliveryItemsAbove259.chunked(2)
                            rows.forEach { rowItems ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(bottom = 12.dp),
                                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                                ) {
                                    rowItems.forEach { item ->
                                        RestaurantItemDetails2(
                                            item = item,
                                            modifier = Modifier.weight(1f),
                                            onAddClick = { println("Added: ${item.title}") }
                                        )
                                    }
                                    if (rowItems.size == 1) {
                                        Spacer(modifier = Modifier.weight(1f))
                                    }
                                }
                            }
                        }
                    }
                    // Divider
                    item {
                        Divider(
                            color = Color.LightGray,
                            thickness = 1.dp,
                            modifier = Modifier.padding(vertical = 0.dp)
                        )
                    }
                }

                // ==================== RECOMMENDED SECTION (Single Image) ====================
                if (recommendedItems.isNotEmpty()) {
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(MaterialTheme.customColors.white)
                        ) {
                            Spacer(modifier = Modifier.height(15.dp))
                            Text(
                                text = "Recommended For You",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.customColors.black
                                ),
                                maxLines = 1,
                                modifier = Modifier.fillMaxWidth().padding(start = 12.dp)
                            )
                            Spacer(modifier = Modifier.height(15.dp))
                            recommendedItems.forEach { item ->
                                RestaurantItemDetails(
                                    item = item,
                                    showMultipleImages = false,
                                    onAddClick = { println("Added: ${item.title}") }
                                )
                            }
                        }
                    }
                }

                // ==================== CRAZY / MAIN SECTION (Single Image) ====================
                if (crazyItems.isNotEmpty()) {
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(MaterialTheme.customColors.white)
                        ) {
                            Spacer(modifier = Modifier.height(15.dp))
                            Text(
                                text = "Crazy Pizza",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.customColors.black
                                ),
                                maxLines = 1,
                                modifier = Modifier.fillMaxWidth().padding(start = 12.dp)
                            )
                            Spacer(modifier = Modifier.height(15.dp))
                            crazyItems.forEach { item ->
                                RestaurantItemDetails(
                                    item = item,
                                    showMultipleImages = false,
                                    onAddClick = { println("Added: ${item.title}") }
                                )
                            }
                        }
                    }
                }

                // ==================== BIG VALUE SECTION (Single Image) ====================
                if (bigValueItems.isNotEmpty()) {
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(MaterialTheme.customColors.white)
                        ) {
                            Text(
                                text = "Big Value Pizza",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.customColors.black
                                ),
                                maxLines = 1,
                                modifier = Modifier.fillMaxWidth().padding(start = 12.dp)
                            )
                            Spacer(modifier = Modifier.height(15.dp))
                            bigValueItems.forEach { item ->
                                RestaurantItemDetails(
                                    item = item,
                                    showMultipleImages = false,
                                    onAddClick = { println("Added: ${item.title}") }
                                )
                            }
                        }
                    }
                }

                // ==================== SUPER SAVER SECTION (Single Image) ====================
                if (superSaverItems.isNotEmpty()) {
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(MaterialTheme.customColors.white)
                        ) {
                            Text(
                                text = "Super Saver Pizza",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.customColors.black
                                ),
                                maxLines = 1,
                                modifier = Modifier.fillMaxWidth().padding(start = 12.dp)
                            )
                            Spacer(modifier = Modifier.height(15.dp))

                            // This will display ALL items
                            superSaverItems.forEach { item ->
                                RestaurantItemDetails(
                                    item = item,
                                    showMultipleImages = false,
                                    onAddClick = { println("Added: ${item.title}") }
                                )
                            }
                        }
                    }
                }

                // ==================== COMBO SECTION (Multiple Images) ====================
                if (comboItems.isNotEmpty()) {
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(MaterialTheme.customColors.white)
                        ) {
                            Text(
                                text = "Combo For You",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.customColors.black
                                ),
                                maxLines = 1,
                                modifier = Modifier.fillMaxWidth().padding(start = 12.dp)
                            )
                            Spacer(modifier = Modifier.height(15.dp))
                            comboItems.forEach { item ->
                                RestaurantItemDetails(
                                    item = item,
                                    showMultipleImages = true,
                                    onAddClick = { println("Added: ${item.title}") }
                                )
                            }
                        }
                    }
                }

                // ==================== MORE DETAILS BUTTON SECTION (Single Image) ====================
                if (moredetailsbuttonItems.isNotEmpty()) {
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(MaterialTheme.customColors.white)
                        ) {
                            Text(
                                text = "Big Party Meal For You",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.customColors.black
                                ),
                                maxLines = 1,
                                modifier = Modifier.fillMaxWidth().padding(start = 12.dp)
                            )
                            Spacer(modifier = Modifier.height(15.dp))
                            moredetailsbuttonItems.forEach { item ->
                                RestaurantItemDetails(
                                    item = item,
                                    showMultipleImages = false,
                                    onAddClick = { println("Added: ${item.title}") }
                                )
                            }
                        }
                    }
                }

                // ==================== MORE DETAILS BUTTON SECTION (Single Images) ====================
                if (saladItems.isNotEmpty()) {
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(MaterialTheme.customColors.white)
                                .padding(vertical = 12.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Text(
                                text = "Special Slide For You",
//                        text = "Salad For You",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.customColors.black
                                ),
                                maxLines = 1,
                                modifier = Modifier.fillMaxWidth().padding(start = 12.dp)
                            )
                            saladItems.forEach { item ->
                                RestaurantItemDetails(
                                    item = item,
                                    showMultipleImages = false,
                                    onAddClick = { println("Added: ${item.title}") }
                                )
                            }
                        }
                    }
                }

                // Empty state when no items found
                if (foodItems.isEmpty()) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(32.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_no_data),
                                    contentDescription = "No items found",
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .size(250.dp),
                                    tint = Color.Unspecified
                                )
                                Text(
                                    text = "No items available",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = Color.Gray
                                )
                                Text(
                                    text = "Try changing your filters or check back later",
                                    fontSize = 14.sp,
                                    color = Color.LightGray,
                                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                                )
                            }
                        }
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "Try these featured restaurants!",
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.customColors.black
                    ),
                    maxLines = 1,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 12.dp)
                )

                Spacer(modifier = Modifier.height(20.dp))

                TopRatedRestaurants(
                    heading = null,
                    subtitle = null,
                    restaurantItems = completeRestaurantItems,
                    onItemClick = { foodItem ->
                        navController?.navigate("restaurant_details/${foodItem.id}/${foodItem.category}")
                    },
                    onInfoIconClick = { restaurantItem ->
                        restaurantItem.restaurantName?.let {
                            navController?.navigate("restaurant_info/$it")
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    backgroundColor = Color.White,
                    cardWidth = 110.dp,
                    cardHeight = 195.dp,
                    imageHeight = 130.dp,
                    imageCornerRadius = 15.dp,
                    spacing = 15.dp,
                    horizontalPadding = 12.dp,
                    verticalPadding = 0.dp,
                    headingBottomPadding = 0.dp
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Divider after each item
                Divider(
                    modifier = Modifier.padding(vertical = 8.dp),
                    color = Color.Gray,
                    thickness = 0.5.dp
                )

                DisclaimerFood(
                    restaurantName = restaurantItem?.restaurantName ?: "",
                    outlet = restaurantItem?.address ?: "",
//                    address = restaurantItem?.address ?: "",
                    fssaiLicense = "23318008000752",
                    onReportIssueClick = { /* Handle report click */ }
                )
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun PreviewRestaurantDetails() {
    MaterialTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            RestaurantDetails(
                onBackClick = {},
                navController = NavHostController(MainActivity()),
                restaurantItem = null,
                category = "all"
            )
        }
    }
}