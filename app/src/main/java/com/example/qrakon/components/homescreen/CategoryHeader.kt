package com.example.qrakon.components.homescreen

import DisclaimerFood
import ViewCartButton
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.qrakon.ui.theme.customColors
import com.example.qrakon.R
import com.example.qrakon.components.GroceryHome.GroceryHome
import com.example.qrakon.components.navigation.TabNavigationApp
import com.example.qrakon.components.restaurants.TopRatedRestaurants
import com.example.qrakon.components.restaurants.completeRestaurantItems

data class CategoryHeaderClass(
    val id: Int,
    val name: String,
    val iconRes: Int,
    val backgroundColor: Color,
    val selectedColor: Color
)

@Composable
fun CategoryHeader(
    modifier: Modifier = Modifier,
    selectedCategoryName: String = "Shopping", // Add this parameter
    onCategorySelected: (String) -> Unit = {}
) {
    val bannerBackgroundColor by remember { BannerBackgroundManager.backgroundColor }
    val categories = remember {
        listOf(
            CategoryHeaderClass(1, "Shopping", R.drawable.shopping, Color(0xFFD9C898), selectedColor = Color(0xFFD4AF44)),
            CategoryHeaderClass(16, "Grocery", R.drawable.grocery, Color(0xFF78CB7E), selectedColor = Color(0xFF56C65E)),
            CategoryHeaderClass(2, "Fashion", R.drawable.fashion_category, Color(0xFFCCF8CF), selectedColor = Color(0xFF2DC137)),
            CategoryHeaderClass(3, "Beauty", R.drawable.beauty_category, Color(0xFFD9A7A7), selectedColor = Color(0xFFCA5353)),
            CategoryHeaderClass(4, "Electric", R.drawable.electric_category, Color(0xFFB7EEBB), selectedColor = Color(0xFF31BC3B)),
            CategoryHeaderClass(5, "Deals", R.drawable.deal_category, Color(0xFFA6DF95), selectedColor = Color(0xFF64CD44)),
            CategoryHeaderClass(6, "Bridal", R.drawable.bride_category, Color(0xFFF0B7C0), selectedColor = Color(0xFFC6374D)),
            CategoryHeaderClass(7, "Jewellery", R.drawable.jewellery_category, Color(0xFFCEC7A5), selectedColor = Color(0xFFC3B364)),
            CategoryHeaderClass(8, "Economy", R.drawable.deal_economy, Color(0xFFF1EFDA), selectedColor = Color(0xFFDDD373)),
            CategoryHeaderClass(9, "Airport", R.drawable.airport_category, Color(0xFFAEE9F1), selectedColor = Color(0xFF30A8B8)),
            CategoryHeaderClass(10, "Industry", R.drawable.industry_category, Color(0xFFB0D0E6), selectedColor = Color(0xFF3182BA)),
            CategoryHeaderClass(11, "Wholesale", R.drawable.wholesale_category, Color(0xFFDFC396), selectedColor = Color(0xFFAE7B2B)),
            CategoryHeaderClass(12, "Sell", R.drawable.sell_category, Color(0xFFCEF0A7), selectedColor = Color(0xFF72B526)),
            CategoryHeaderClass(13, "Medical", R.drawable.medical, Color(0xFFDF8592), selectedColor = Color(0xFFB6253A)),
            CategoryHeaderClass(14, "Fresh", R.drawable.fresh, Color(0xFF95E69A), selectedColor = Color(0xFF24B12D)),
            CategoryHeaderClass(15, "Pay", R.drawable.pay, Color(0xFFCFC76D), selectedColor = Color(0xFFE7D532)),
            CategoryHeaderClass(16, "Food", R.drawable.food_qrakon, Color(0xFFD86F7E), selectedColor = Color(0xFFD93048))
        )
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(MaterialTheme.customColors.lightAccent)
    ) {
        // Fixed height for the LazyRow container to avoid infinite constraints
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(78.dp)
                .padding(horizontal = 12.dp, vertical = 8.dp)
        ) {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(categories.size) { index ->
                    val category = categories[index]
                    CategoryItemHeader(
                        category = category,
                        isSelected = category.name == selectedCategoryName, // Compare by name
                        onClick = {
                            onCategorySelected(category.name)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun CategoryItemHeader(
    category: CategoryHeaderClass,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(90.dp)
            .clickable(onClick = onClick)
            .clip(RoundedCornerShape(12.dp))
            .background(if (isSelected) category.selectedColor else category.backgroundColor)
    ) {
        Box(
            modifier = Modifier
                .height(40.dp)
                .fillMaxWidth()
                .padding(top = 2.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(if (isSelected) category.selectedColor else category.backgroundColor),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = category.iconRes),
                contentDescription = category.name,
                contentScale = ContentScale.Fit,
                modifier = Modifier.fillMaxSize()
            )
        }

        Text(
            text = category.name,
            fontSize = 15.sp,
            textAlign = TextAlign.Center,
            color = Color.Black,
            fontWeight = FontWeight.Medium,
            maxLines = 1,
            overflow = TextOverflow.Visible,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 0.dp)
        )
    }
}

// Main composable that handles category selection and displays appropriate content
@Composable
fun CategoryScreen(
    navController: NavHostController,
    onOpenFashion: () -> Unit
) {
    // Save selected category in rememberSaveable to persist across navigation
    val selectedCategory = rememberSaveable { mutableStateOf("Shopping") }
    val scrollOffset = remember { mutableStateOf(0f) }
    val isHeaderVisible = remember { mutableStateOf(true) }
    val density = LocalDensity.current

    // Add a key to track when we're on Fashion screen
    val isOnFashionScreen = remember { mutableStateOf(false) }

    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                val delta = available.y
                scrollOffset.value = (scrollOffset.value - delta).coerceAtLeast(0f)
                if (delta < 0f) isHeaderVisible.value = false
                if (delta > 0f || scrollOffset.value <= 0f) isHeaderVisible.value = true
                return Offset.Zero
            }
        }
    }

    Column(modifier = Modifier.fillMaxSize().background(MaterialTheme.customColors.white)) {
        AnimatedVisibility(
            visible = isHeaderVisible.value,
            enter = slideInVertically(
                initialOffsetY = { -it },
                animationSpec = tween(durationMillis = 200)
            ) + fadeIn(animationSpec = tween(durationMillis = 200)),
            exit = slideOutVertically(
                targetOffsetY = { -it },
                animationSpec = tween(durationMillis = 120)
            ) + fadeOut(animationSpec = tween(durationMillis = 120))
        ) {
            CategoryHeader(
                selectedCategoryName = selectedCategory.value, // Pass selected category
                onCategorySelected = { categoryName ->
                    selectedCategory.value = categoryName
                    if (categoryName == "Fashion") {
                        isOnFashionScreen.value = true
                        onOpenFashion() // 🚀 navigate to new screen
                    }
                }
            )
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .nestedScroll(nestedScrollConnection)
        ) {
            when (selectedCategory.value) {
                "Shopping" -> ShoppingScreen(navController = navController)
                "Grocery" -> GroceryScreen(navController = navController)
                "Beauty" -> BeautyScreen()
                "Economy" -> EconomyScreen()
                "Deals" -> DealsScreen()
                "Bridal" -> BrideScreen()
                "Jewellery" -> JewelleryScreen()
                "Airport" -> AirportScreen()
                "Electric" -> ElectricScreen()
                "Industry" -> IndustryScreen()
                "Wholesale" -> WholesaleScreen()
                "Sell" -> SellScreen()
                "Medical" -> MedicalScreen()
                "Fresh" -> FreshScreen()
                "Pay" -> PayScreen()
                "Food" -> FoodScreen(navController = navController)
            }
        }
    }

    // Effect to reset category when coming back from Fashion screen
    LaunchedEffect(Unit) {
        // Check if we're returning to this composable and were on Fashion
        snapshotFlow { isOnFashionScreen.value }
            .collect { wasOnFashion ->
                if (wasOnFashion) {
                    // Reset to Shopping when returning from Fashion
                    selectedCategory.value = "Shopping"
                    isOnFashionScreen.value = false
                }
            }
    }
}

// Main composable that handles category selection and displays appropriate content
//@Composable
//fun CategoryScreen(
//    navController: NavHostController,
//    onOpenFashion: () -> Unit
//) {
//    // Save selected category in rememberSaveable to persist across navigation
//    val selectedCategory = rememberSaveable { mutableStateOf("Shopping") }
//    val scrollOffset = remember { mutableStateOf(0f) }
//    val isHeaderVisible = remember { mutableStateOf(true) }
//    val density = LocalDensity.current
//    val nestedScrollConnection = remember {
//        object : NestedScrollConnection {
//            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
//                val delta = available.y
//                scrollOffset.value = (scrollOffset.value - delta).coerceAtLeast(0f)
//                if (delta < 0f) isHeaderVisible.value = false
//                if (delta > 0f || scrollOffset.value <= 0f) isHeaderVisible.value = true
//                return Offset.Zero
//            }
//        }
//    }
//
//    Column(modifier = Modifier.fillMaxSize().background(MaterialTheme.customColors.white)) {
//        AnimatedVisibility(
//            visible = isHeaderVisible.value,
//            enter = slideInVertically(
//                initialOffsetY = { -it },
//                animationSpec = tween(durationMillis = 200)
//            ) + fadeIn(animationSpec = tween(durationMillis = 200)),
//            exit = slideOutVertically(
//                targetOffsetY = { -it },
//                animationSpec = tween(durationMillis = 120)
//            ) + fadeOut(animationSpec = tween(durationMillis = 120))
//        ) {
//            CategoryHeader(
//                selectedCategoryName = selectedCategory.value, // Pass selected category
//                onCategorySelected = { categoryName ->
//                    selectedCategory.value = categoryName
//                    if (categoryName == "Fashion") {
//                        onOpenFashion() // 🚀 navigate to new screen
//                    }
//                }
//            )
//        }
//
//        Box(
//            modifier = Modifier
//                .fillMaxSize()
//                .nestedScroll(nestedScrollConnection)
//        ) {
//            when (selectedCategory.value) {
//                "Shopping" -> ShoppingScreen(navController = navController)
//                "Grocery" -> GroceryScreen(navController = navController)
//                "Beauty" -> BeautyScreen()
//                "Economy" -> EconomyScreen()
//                "Deals" -> DealsScreen()
//                "Bridal" -> BrideScreen()
//                "Jewellery" -> JewelleryScreen()
//                "Airport" -> AirportScreen()
//                "Electric" -> ElectricScreen()
//                "Industry" -> IndustryScreen()
//                "Wholesale" -> WholesaleScreen()
//                "Sell" -> SellScreen()
//                "Medical" -> MedicalScreen()
//                "Fresh" -> FreshScreen()
//                "Pay" -> PayScreen()
//                "Food" -> FoodScreen(navController = navController)
//            }
//        }
//    }
//}


//
//@Composable
//fun CategoryScreen() {
//    val selectedCategory = remember { mutableStateOf("Shopping") }
//
//    // Accumulated scroll offset (px). Keeping >= 0 so we can force header visible at top.
//    val scrollOffset = remember { mutableStateOf(0f) }
//    val isHeaderVisible = remember { mutableStateOf(true) }
//
//    val density = LocalDensity.current
//    val hideThresholdPx = with(density) { 50.dp.toPx() } // still useful if you ever want to add smooth hide
//
//    val nestedScrollConnection = remember {
//        object : NestedScrollConnection {
//            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
//                val delta = available.y
//
//                // Track scroll offset so we know when we're at top
//                scrollOffset.value = (scrollOffset.value - delta).coerceAtLeast(0f)
//
//                when {
//                    delta < 0f -> {
//                        // User is scrolling UP (swipe up) → hide header immediately
//                        isHeaderVisible.value = false
//                    }
//                    delta > 0f -> {
//                        // User is scrolling DOWN (swipe down) → show header immediately
//                        isHeaderVisible.value = true
//                    }
//                }
//
//                // Force header visible when at very top
//                if (scrollOffset.value <= 0f) {
//                    isHeaderVisible.value = true
//                }
//
//                return Offset.Zero
//            }
//        }
//    }
//
//    Column(modifier = Modifier.fillMaxSize().background(MaterialTheme.customColors.white)) {
//        AnimatedVisibility(
//            visible = isHeaderVisible.value,
//            enter = slideInVertically(
//                initialOffsetY = { -it },
//                animationSpec = tween(durationMillis = 200) // faster show
//            ) + fadeIn(animationSpec = tween(durationMillis = 200)),
//            exit = slideOutVertically(
//                targetOffsetY = { -it },
//                animationSpec = tween(durationMillis = 120) // very fast hide
//            ) + fadeOut(animationSpec = tween(durationMillis = 120))
//        ) {
//            CategoryHeader(
//                onCategorySelected = { categoryName ->
//                    selectedCategory.value = categoryName
//                }
//            )
//        }
//
//        Box(
//            modifier = Modifier
//                .fillMaxSize()
//                .nestedScroll(nestedScrollConnection)
//        ) {
//            when (selectedCategory.value) {
//                "Shopping" -> ShoppingScreen()
//                "Fashion" -> FashionScreen()
//                "Beauty" -> BeautyScreen()
//                "Economy" -> EconomyScreen()
//                "Deals" -> DealsScreen()
//                "Bridal" -> BrideScreen()
//                "Jewellery" -> JewelleryScreen()
////                "Groom" -> GroomScreen()
//                "Airport" -> AirportScreen()
//                "Electric" -> ElectricScreen()
//                "Industry" -> IndustryScreen()
//                "Wholesale" -> WholesaleScreen()
//                "Sell" -> SellScreen()
//                "Medical" -> MedicalScreen()
//                "Fresh" -> FreshScreen()
//                "Pay" -> PayScreen()
//                else -> ShoppingScreen()
//            }
//        }
//    }
//}

// Separate screen composables for each category
@Composable
fun ShoppingScreen(navController: NavHostController) {
    TabNavigationApp(navController = navController)
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
////            .padding(16.dp)
//    ) {
////        Text(
////            text = "Shopping",
////            fontSize = 24.sp,
////            fontWeight = FontWeight.Bold,
////            color = MaterialTheme.customColors.black
////        )
////        Spacer(modifier = Modifier.height(16.dp))
//        TabNavigationApp() // Your full navigation component
//    }
}

@Composable
fun GroceryScreen(navController: NavHostController? = null) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.customColors.background)
    ) {
        GroceryHome(
            navController = navController,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
fun FashionScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Fashion",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.customColors.black
        )
        Spacer(modifier = Modifier.height(16.dp))
        // Add your fashion-specific content here
        Text("Latest fashion trends and collections!",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.customColors.black)
        // You can add more complex fashion content
    }
}

@Composable
fun BeautyScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Beauty",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.customColors.primary
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text("Beauty products and skincare essentials!")
        // Add beauty-specific content
    }
}

@Composable
fun EconomyScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Economy",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.customColors.primary
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text("Budget-friendly options and economic deals!")
    }
}

@Composable
fun DealsScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Deals",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.customColors.primary
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text("Special offers and limited-time deals!")
    }
}

@Composable
fun BrideScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Bride",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.customColors.primary
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text("Bridal collections and wedding planning!")
    }
}

@Composable
fun JewelleryScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Jewellery",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.customColors.primary
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text("Jewellery Screen!")
    }
}

@Composable
fun AirportScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Airport",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.customColors.primary
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text("Travel accessories and airport services!")
    }
}

@Composable
fun ElectricScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Electric",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.customColors.primary
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text("Electronics and electrical appliances!")
    }
}

@Composable
fun IndustryScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Industry",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.customColors.primary
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text("Industrial equipment and tools!")
    }
}

@Composable
fun WholesaleScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Wholesale",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.customColors.primary
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text("Wholesale products and bulk deals!")
    }
}

@Composable
fun SellScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Sell",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.customColors.primary
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text("Sell your products with ease!")
    }
}

@Composable
fun MedicalScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Medical",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.customColors.primary
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text("Medical supplies and healthcare products!")
    }
}

@Composable
fun FreshScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Fresh",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.customColors.primary
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text("Fresh produce and daily groceries!")
    }
}
@Composable
fun PayScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Payment",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.customColors.primary
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text("Pay Online!")
    }
}


@Composable
fun FoodScreen(navController: NavHostController? = null) {

    Scaffold(
        bottomBar = {
            ViewCartButton(
                itemCount = 1,
                onViewCartClick = {
                    // Navigate to CheckOutFood
                    // For example with Compose Navigation:
                    navController?.navigate("checkout_food")
                    // Or with Activity navigation:
                    // context.startActivity(Intent(context, CheckOutFoodActivity::class.java))
                }
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState()) // ✅ MAKE SCROLLABLE
        ) {

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Top rated restaurants near you",
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

            DisclaimerFood(
                onReportIssueClick = {}
            )

            // ✅ Extra space so last item not hidden behind bottom bar
//            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}