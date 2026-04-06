package com.example.qrakon.components.restaurants

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
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
import com.example.qrakon.components.restaurants.RestaurantItemDetails3
import com.example.qrakon.ui.theme.customColors
import kotlin.collections.chunked
import kotlin.collections.forEach
import com.example.qrakon.components.restaurants.FilterConfig
import com.example.qrakon.components.restaurants.FilterChip
import com.example.qrakon.components.restaurants.FilterType
import com.example.qrakon.components.restaurants.FilterButtonFood
import com.example.qrakon.components.restaurants.SearchBarRestaurant


/**
 * Food item data class with all optional fields
 */
data class FoodItemDoubleF(
    val id: Int? = null,
    val imageRes: List<Int>? = null, // 👈 changed
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
    val bestSeller:  Boolean? = false,
)
@Composable
fun RestaurantDetails(
    onBackClick: () -> Unit,
    navController: NavHostController,
    restaurantItem: TopRatedRestaurantItem? = null
) {
    var searchQuery by remember { mutableStateOf("") }
    var showDropdownMenu by remember { mutableStateOf(false) }
    val foodItems = listOf(
        FoodItemDoubleF(
            id = 1,
            imageRes = listOf(
                R.drawable.restaurant_image_pizzas_food_items_1,
                R.drawable.restaurant_image_pizzas_food_items_2
            ),
            title = "Chicken Pizza",
            price = "275",
            originalPrice = "375",
            restaurantName = "Burger Hub",
            rating = "4.3",
            deliveryTime = "20-25 mins",
            distance = "1.8 km",
            discount = "10% OFF",
            discountAmount = "₹25 OFF",
            address = "Food Street, City Center",
            calories = "450 kcal",
            protein = "22g",
            isHighProtein = true,
            category = "Fast Food",
            isWishlisted = false,
            description = "Juicy chicken burger with fresh lettuce, tomato and crispy fries",
            quantity = "1",
            infoIcon = R.drawable.ic_veg_rest,
            highlyReordered = "70",
            reorderedQuantity = "150+ orders",
            bestSeller = true,
        ),
        FoodItemDoubleF(
            id = 2,
            imageRes = listOf(
                R.drawable.restaurant_image_pizzas_food_items_2,
                R.drawable.restaurant_image_pizzas_food_items_3
            ),
            title = "Chilli Garlic Pizza",
            price = "363",
            originalPrice = "563",
            restaurantName = "Momo Express",
            rating = "4.5",
            deliveryTime = "25-30 mins",
            distance = "2.2 km",
            discount = "15% OFF",
            discountAmount = "₹40 OFF",
            address = "Downtown Food Plaza",
            calories = "380 kcal",
            protein = "14g",
            isHighProtein = false,
            category = "Snacks",
            isWishlisted = true,
            description = "Spicy chilli garlic momos tossed with flavorful sauces and herbs",
            quantity = "1",
            infoIcon = R.drawable.ic_veg_rest,
            highlyReordered = "65",
            reorderedQuantity = "200+ orders",
            bestSeller = false,
        ),
        FoodItemDoubleF(
            id = 3,
            imageRes = listOf(
                R.drawable.restaurant_image_pizzas_food_items_3,
                R.drawable.restaurant_image_pizzas_food_items_4
            ),
            title = "Ultimate Cheese Pizza",
            price = "399",
            originalPrice = "499",
            restaurantName = "Pizza Heaven",
            rating = "4.3",
            deliveryTime = "25-30 mins",
            distance = "2.5 km",
            discount = "",
            discountAmount = "",
            address = "Food Court, Mall Road",
            calories = "780 kcal",
            protein = "24g",
            isHighProtein = false,
            category = "Pizza",
            isWishlisted = false,
            description = "Classic margherita with extra cheese and basil",
            quantity = "1",
            infoIcon = R.drawable.ic_veg_rest,
            highlyReordered = "",
            reorderedQuantity = "",
            bestSeller = false,
        ),

        // Image 2: Corn Pizza with Deal
        FoodItemDoubleF(
            id = 4,
            imageRes = listOf(
                R.drawable.restaurant_image_pizzas_food_items_4,
                R.drawable.restaurant_image_pizzas_food_items_5
            ),
            title = "Corn Pizza",
            price = "139",
            originalPrice = "239",
            restaurantName = "Pizza Corner",
            rating = "3.8",
            deliveryTime = "20-25 mins",
            distance = "1.5 km",
            discount = "",
            discountAmount = "",
            address = "Street Food Market",
            calories = "620 kcal",
            protein = "16g",
            isHighProtein = false,
            category = "Pizza",
            isWishlisted = false,
            description = "Sweet corn pizza with creamy sauce",
            quantity = "1",
            infoIcon = R.drawable.ic_veg_rest,
            highlyReordered = "Highly reordered",
            reorderedQuantity = "",
            bestSeller = true,
        ),

        // Image 3: Flotzz Pizzas
        FoodItemDoubleF(
            id = 5,
            imageRes = listOf(
                R.drawable.restaurant_image_pizzas_food_items_5,
                R.drawable.restaurant_image_pizzas_food_items_6
            ),
            title = "Ultimate Flotzz Pizza",
            price = "399",
            originalPrice = "499",
            restaurantName = "Flotzz Pizza",
            rating = "4.2",
            deliveryTime = "30-35 mins",
            distance = "3.0 km",
            discount = "",
            discountAmount = "",
            address = "Food Plaza, Sector 5",
            calories = "850 kcal",
            protein = "32g",
            isHighProtein = true,
            category = "Pizza",
            isWishlisted = false,
            description = "Loaded with cheese and toppings",
            quantity = "1",
            infoIcon = R.drawable.ic_non_veg_rest,
            highlyReordered = "",
            reorderedQuantity = "500+ orders",
            bestSeller = true,
        ),

        FoodItemDoubleF(
            id = 6,
            imageRes = listOf(
                R.drawable.restaurant_image_pizzas_food_items_6,
                R.drawable.restaurant_image_pizzas_food_items_1
            ),
            title = "Tandoori Chicken Pizza",
            price = "399",
            originalPrice = "499",
            restaurantName = "Flotzz Pizza",
            rating = "4.5",
            deliveryTime = "30-35 mins",
            distance = "3.0 km",
            discount = "",
            discountAmount = "",
            address = "Food Plaza, Sector 5",
            calories = "890 kcal",
            protein = "38g",
            isHighProtein = true,
            category = "Pizza",
            isWishlisted = true,
            description = "Tandoori chicken with spicy sauce",
            quantity = "1",
            infoIcon = R.drawable.ic_non_veg_rest,
            highlyReordered = "",
            reorderedQuantity = "500+ orders",
            bestSeller = false,
        )
    )
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.customColors.background)
    ) {
        // Item 1: Top Bar
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.customColors.blackHeader)
                    .padding(start = 12.dp, end = 12.dp, top = 5.dp, bottom = 5.dp),
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
                            text = { Text("Report", fontSize = 14.sp, color = MaterialTheme.colorScheme.error) },
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
                    .clip(RoundedCornerShape(
                        topStart = 0.dp,
                        topEnd = 0.dp,
                        bottomStart = 30.dp,
                        bottomEnd = 30.dp
                    ))
            ) {
                if (restaurantItem != null) {
                    RestaurantCard(
                        items = listOf(restaurantItem),
                        onItemClick = { println("Clicked: ${it.restaurantName}") }
                    )
                } else {
                    RestaurantCard(
                        items = completeRestaurantItems,
                        onItemClick = { println("Clicked: ${it.restaurantName}") }
                    )
                }
            }
        }

        // Item 3: Sticky Header - Search Bar
        stickyHeader {
            Surface(color = MaterialTheme.customColors.header, modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.customColors.background)
                        .padding(top = 8.dp, bottom = 8.dp, start = 12.dp, end = 12.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp), // Gap between elements
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // SearchBar - 85% width
                    Box(
                        modifier = Modifier
                            .weight(0.85f) // 85% of available width
                            .clip(RoundedCornerShape(8.dp))
                            .background(MaterialTheme.customColors.background)
                    ) {
                        SearchBarRestaurant(
                            query = searchQuery,
                            backgroundColor = MaterialTheme.customColors.background,
                            placeholder = "Search for dishes",
                            onQueryChange = { searchQuery = it },
                            onSearch = { println("Search performed: $it") },
                            qrIconColorMike = MaterialTheme.customColors.orange
                        )
                    }

                    // Image Button - 15% width
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = MaterialTheme.customColors.skyBlue,
                        modifier = Modifier
                            .weight(0.15f) // 15% of available width
                            .size(width = 45.dp, height = 45.dp) // Fixed size within weight
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_people),
                                contentDescription = "Group Order Icon",
                                modifier = Modifier.size(30.dp),
                                colorFilter = ColorFilter.tint(MaterialTheme.customColors.darkAccent2)
                            )
                        }
                    }
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
                chipHeight = 35,  // Set chip height to 30dp
                cornerRadius = 8,   // Set corner radius to 8dp
                chipPadding = 8   // Set corner radius to 8dp
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.customColors.background)
                    .padding(top = 0.dp, bottom = 0.dp, start = 0.dp, end = 0.dp)
                    .offset(y = (-5).dp)
            ) {
                FilterButtonFood(
                    filterConfig = restFilters,
                    onFilterClick = { println("Filter clicked: ${it.text}") },
                    onSortClick = { println("Sort clicked") }
                )
            }
        }

        // Item 4: Filter Button
//        stickyHeader {
////            item {
//
////            }
//        }

        // Divider
        item {
            Divider(color = Color.LightGray, thickness = 1.dp, modifier = Modifier.padding(vertical = 0.dp))
        }

        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFF5F5F5))
                    .padding(12.dp)
            ) {
                Text(
                    text = "Top Picks",
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.customColors.black
                    ),
//            textAlign = TextAlign.Center,
                    maxLines = 1,
                    modifier = Modifier.fillMaxWidth().padding(start = 12.dp)
                )
                Spacer(modifier = Modifier.height(15.dp))
                RestaurantItemDetails3(
                    items = foodItems,
                    onAddClick = { item ->
                        println("Added: ${item.title}")
                    }
                )
            }
        }

        // Divider
        item {
            Divider(color = Color.LightGray, thickness = 1.dp, modifier = Modifier.padding(vertical = 0.dp))
        }

        // Item 5: Food Items Grid
        item {
            Spacer(modifier = Modifier.height(15.dp))
//            Row(
//                modifier = Modifier.fillMaxWidth()
//                    .padding(horizontal = 12.dp, vertical = 0.dp),
//                horizontalArrangement = Arrangement.SpaceBetween,
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                Image(
//                    painter = painterResource(R.drawable.ic_99_store),
//                    contentDescription = "Offer Image",
//                    modifier = Modifier
////                        .fillMaxWidth()
//                        .height(40.dp)
//                        .width(140.dp),
//                    contentScale = ContentScale.FillBounds
//                )
//
//                // Down arrow icon
//                Icon(
//                    painter = painterResource(id = R.drawable.outline_keyboard_arrow_down_24),
//                    contentDescription = "Dropdown arrow",
//                    modifier = Modifier.size(30.dp)
//                        .padding(top = 1.dp),
//                    tint = Color.Gray
//                )
//            }
            Surface(
                shape = RoundedCornerShape(20.dp),
                color = Color.Transparent, // Make Surface transparent
//                modifier = Modifier.size(width = 230.dp, height = 30.dp)
            ) {
                Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .padding(start = 12.dp, top = 5.dp, end = 12.dp, bottom = 5.dp)
                    ) {
                    Image(
                        painter = painterResource(R.drawable.ic_free_delivery_above_99),
                        contentDescription = "Offer Image",
                        modifier = Modifier
//                        .fillMaxWidth()
                            .height(40.dp)
                            .width(230.dp),
                        contentScale = ContentScale.FillBounds
                    )
                }
            }
        }


                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0xFFF5F5F5))
                            .padding(12.dp)
                    ) {
                        val rows = foodItems.chunked(2)
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

        // Item 6: Divider
        item {
            Divider(color = Color.LightGray, thickness = 1.dp, modifier = Modifier.padding(vertical = 0.dp))
        }

        // Item: List View Items
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFF5F5F5))
                    .padding(vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "Recommended For You",
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.customColors.black
                    ),
//            textAlign = TextAlign.Center,
                    maxLines = 1,
                    modifier = Modifier.fillMaxWidth().padding(start = 12.dp)
                )
                foodItems.forEach { item ->
                    RestaurantItemDetails(
                        item = item,
                        showMultipleImages = false,
                        onAddClick = { println("Added: ${item.title}") }
                    )
                }
            }
        }
        // Item: List View Items
//        item {
//            Column(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .background(Color(0xFFF5F5F5))
//                    .padding(vertical = 12.dp),
//                verticalArrangement = Arrangement.spacedBy(12.dp)
//            ) {
//                Text(
//                    text = "Recommended For You",
//                    style = MaterialTheme.typography.bodySmall.copy(
//                        fontSize = 20.sp,
//                        fontWeight = FontWeight.Bold,
//                        color = MaterialTheme.customColors.black
//                    ),
////            textAlign = TextAlign.Center,
//                    maxLines = 1,
//                    modifier = Modifier.fillMaxWidth().padding(start = 12.dp)
//                )
//                foodItems.forEach { item ->
//                    RestaurantItemDetails(
//                        item = item,
//                        showMultipleImages = true,
//                        onAddClick = { println("Added: ${item.title}") }
//                    )
//                }
//            }
//        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewRestaurantDetails() {
    MaterialTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            RestaurantDetails(
                onBackClick = {},
                navController = NavHostController(MainActivity()),
                restaurantItem = null
            )
        }
    }
}