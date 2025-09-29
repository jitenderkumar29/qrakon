package com.example.qrakon.components.categorytabs

import AllShopping
import ElectronicsShopping
import Product
import ProductList
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.qrakon.components.categorytabs.beauty.BeautyCarousel
import com.example.qrakon.components.categorytabs.fashion.FashionCarousel
import com.example.qrakon.components.categorytabs.shopping.ShoppingCarousel
import com.example.qrakon.components.homescreen.AdsSponsored
import com.example.qrakon.components.homescreen.BannerHome
import com.example.qrakon.components.homescreen.BikeCategoryHeader
import com.example.qrakon.components.homescreen.CategoryProducts
import com.example.qrakon.components.homescreen.ClothsCategory
import com.example.qrakon.components.homescreen.ElectronicCategory
import com.example.qrakon.components.homescreen.FurnitureCategory
import com.example.qrakon.R
import com.example.qrakon.ui.theme.customColors
import androidx.compose.ui.text.style.TextAlign
import com.example.qrakon.components.categorytabs.mobile.MobileCarousel

// Sealed class for different category pages
sealed class CategoryPage {
    object All : CategoryPage()
    object Mobile : CategoryPage()
    object Electronics : CategoryPage()
    object Appliances : CategoryPage()
    object Home : CategoryPage()
    object FoodHealth : CategoryPage()
    object ToysKids : CategoryPage()
    object AutoAccessories : CategoryPage()
    object Sports : CategoryPage()
    object Furniture : CategoryPage()
    object TwoWheelers : CategoryPage()
    object Books : CategoryPage()
    object Musical : CategoryPage()
    object Ultra : CategoryPage()
}

@Composable
fun CategoryTabs(
    onCategorySelected: (CategoryPage) -> Unit = {}
) {
    var selectedTabIndex by remember { mutableIntStateOf(0) }

    // Pair each tab title with its corresponding icon resource ID
    val tabs = listOf(
        "All" to R.drawable.ic_category_all_tab,
        "Mobile" to R.drawable.ic_mobile,
        "Electronics" to R.drawable.ic_electronics_tab,
        "Appliances" to R.drawable.ic_appliances,
        "Home" to R.drawable.ic_home_tab,
        "Food & Health" to R.drawable.ic_food_health_tab,
        "Toys & kids" to R.drawable.ic_toys_kids,
        "Auto Acce" to R.drawable.ic_auto_acce,
        "Sports" to R.drawable.ic_sports_tab,
        "Furniture" to R.drawable.ic_furniture_tab,
        "2 wheelers" to R.drawable.ic_two_wheelers,
        "Books" to R.drawable.ic_books_tab,
        "Musical" to R.drawable.ic_musical,
        "Ultra" to R.drawable.ic_ultra,
    )

    Column(modifier = Modifier.fillMaxWidth()) {
        ScrollableTabRow(
            selectedTabIndex = selectedTabIndex,
            containerColor = MaterialTheme.customColors.orange,
            contentColor = MaterialTheme.customColors.white,
            edgePadding = 0.dp,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                    height = 5.dp,
                    color = MaterialTheme.customColors.white
                )
            }
        ) {
            tabs.forEachIndexed { index, (title, iconRes) ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = {
                        selectedTabIndex = index
                        val page = when (index) {
                            0 -> CategoryPage.All
                            1 -> CategoryPage.Mobile
                            2 -> CategoryPage.Electronics
                            3 -> CategoryPage.Appliances
                            4 -> CategoryPage.Home
                            5 -> CategoryPage.FoodHealth
                            6 -> CategoryPage.ToysKids
                            7 -> CategoryPage.AutoAccessories
                            8 -> CategoryPage.Sports
                            9 -> CategoryPage.Furniture
                            10 -> CategoryPage.TwoWheelers
                            11 -> CategoryPage.Books
                            12 -> CategoryPage.Musical
                            13 -> CategoryPage.Ultra
                            else -> CategoryPage.All
                        }
                        onCategorySelected(page)
                    },
                    modifier = Modifier.padding(horizontal = 4.dp)
                ) {
                    // Column layout for icon on top and text at bottom
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(vertical = 4.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = iconRes),
                            contentDescription = title,
                            modifier = Modifier.size(
                                if (selectedTabIndex == index) {
                                    24.dp // Larger size when selected
                                } else {
                                    20.dp // Normal size
                                }
                            ),
                            tint = if (selectedTabIndex == index) {
                                MaterialTheme.customColors.white // Different color when selected
                            } else {
                                MaterialTheme.customColors.white
                            }
                        )

//                        Spacer(modifier = Modifier.height(2.dp))

                        Text(
                            text = title,
                            fontSize = 12.sp, // Slightly smaller font for better fit
                            fontWeight = if (selectedTabIndex == index) FontWeight.Bold else FontWeight.Medium,
                            color = if (selectedTabIndex == index) {
                                MaterialTheme.customColors.white
                            } else {
                                MaterialTheme.customColors.white
                            },
                            maxLines = 2, // Allow 2 lines for longer text
                            textAlign = TextAlign.Center,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
        }

        // Show content for each tab
        when (selectedTabIndex) {
            0 -> AllCategoryPage()
            1 -> MobileCategoryPage()
            2 -> ElectronicsCategoryPage()
            3 -> AppliancesCategoryPage()
            4 -> HomeCategoryPage()
            5 -> FoodHealthCategoryPage()
            6 -> ToysKidsCategoryPage()
            7 -> AutoAccessoriesCategoryPage()
            8 -> SportsCategoryPage()
            9 -> FurnitureCategoryPage()
            10 -> TwoWheelersCategoryPage()
            11 -> BooksCategoryPage()
            12 -> MusicalCategoryPage()
            13 -> UltraCategoryPage()
            else -> AllCategoryPage()
        }
    }
}

// Add the missing category page composables
@Composable
fun ElectronicsCategoryPage() {
    var selectedCategory = remember { mutableStateOf("All Deals") }
    val bannerImages = listOf(
        painterResource(id = R.drawable.electronics_banner1),
        painterResource(id = R.drawable.electronics_banner2),
        painterResource(id = R.drawable.electronics_banner3),
        painterResource(id = R.drawable.electronics_banner4),
        painterResource(id = R.drawable.electronics_banner5),
        painterResource(id = R.drawable.electronics_banner6),
        painterResource(id = R.drawable.electronics_banner7),
        painterResource(id = R.drawable.electronics_banner8),
        painterResource(id = R.drawable.electronics_banner9),
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
//            .padding(16.dp)
    ) {
        BannerHome(
            images = bannerImages,
            onImageClick = { page ->
                when (page) {
                    0 -> onBanner1Click()
                    1 -> onBanner2Click()
                    2 -> onBanner3Click()
                }
            },
            autoScrollDelay = 2000,
            height = 270.dp,
            dotSize = 8.dp,
            modifier = Modifier.padding(bottom = 0.dp)
        )

        Spacer(
            modifier = Modifier.height(2.dp)
                .fillMaxWidth()
                .background(MaterialTheme.customColors.spacerColor)
        )
        // Carousel Double
        val electronicsCategories = remember {
            listOf(
                Category(0, "Laptops", R.drawable.ic_laptops_tab),
                Category(1, "2 Wheelers", R.drawable.ic_two_wheelers_tab),
                Category(2, "Wearables", R.drawable.ic_wearables_tab),
                Category(3, "ITPeripherals", R.drawable.ic_itperipherals_tab),
                Category(4, "Camera", R.drawable.ic_camera_tab),
                Category(5, "Accessories", R.drawable.ic_accessories_tab),
                Category(6, "Powerbanks", R.drawable.ic_powerbanks_tab),
                Category(7, "Smart Camera", R.drawable.ic_smart_camera),
                Category(8, "Gaming", R.drawable.ic_gaming_pc_tab),
                Category(9, "Headsets+", R.drawable.ic_headsets_tab),
                Category(10, "Tablet", R.drawable.ic_tablet_tab),
                Category(11, "Grooming", R.drawable.ic_grooming_tab),
                Category(12, "Chargers", R.drawable.ic_chargers_tab),
                Category(13, "Mobile case", R.drawable.ic_mobile_case_tab),
                Category(14, "Storage", R.drawable.ic_storage_tab),
                Category(15, "Gaming", R.drawable.ic_gaming_tab),
                Category(16, "Healthcare", R.drawable.ic_healthcare_tab),
            )
        }

        Spacer(
            modifier = Modifier.height(2.dp)
                .fillMaxWidth()
                .background(MaterialTheme.customColors.spacerColor)
        )

        var selectedCategory by remember { mutableStateOf<Category?>(null) }
        var selectedMobileCategory by remember { mutableStateOf<Category?>(null) }
        Column {
            CategoryProducts(
                categories = electronicsCategories,
                onCategorySelected = { category ->
                    selectedCategory = category
                    // Handle category selection (navigation, filtering, etc.)
                    println("Selected category: ${category.name}")
                },
                modifier = Modifier.fillMaxWidth(),
                initialSelectedCategory = electronicsCategories.first(),
                itemWidth = 75, // Custom width
                itemHeight = 65, // Custom height
                horizontalSpacing = 8, // Custom spacing
                verticalSpacing = 8,
                backgroundColor = MaterialTheme.customColors.white // Light blue background
            )

            val myProducts = listOf(
                Product("Motobook 60 OLED", "Starts ₹39,999*", R.drawable.motobook_60_oled),
                Product("HP Victus 3050", "Starts ₹54,949*", R.drawable.hp_victus_3050),
                Product("HP Price Drop", "Starts ₹29,999*", R.drawable.hp_price_drop)
            )

            Spacer(
                modifier = Modifier.height(2.dp)
                    .fillMaxWidth()
                    .background(MaterialTheme.customColors.spacerColor)
            )

            // Call the reusable component
            ProductList(
                products = myProducts,
//                sectionTitle = "Featured Products", // Optional
                showName = true,  // Show name under image
                showPrice = true  // Show price under image
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun AppliancesCategoryPage() {
    var selectedCategory = remember { mutableStateOf("All Deals") }
    val bannerImages = listOf(
        painterResource(id = R.drawable.appliances_banner1),
        painterResource(id = R.drawable.appliances_banner2),
        painterResource(id = R.drawable.appliances_banner3),
        painterResource(id = R.drawable.appliances_banner4),
        painterResource(id = R.drawable.appliances_banner5),

    )
    Column(
        modifier = Modifier
            .fillMaxSize()
//            .padding(16.dp)
    ) {
        BannerHome(
            images = bannerImages,
            onImageClick = { page ->
                when (page) {
                    0 -> onBanner1Click()
                    1 -> onBanner2Click()
                    2 -> onBanner3Click()
                }
            },
            autoScrollDelay = 2000,
            height = 270.dp,
            dotSize = 8.dp,
            modifier = Modifier.padding(bottom = 0.dp)
        )

        Spacer(
            modifier = Modifier.height(2.dp)
                .fillMaxWidth()
                .background(MaterialTheme.customColors.spacerColor)
        )
        // Carousel Double
        val electronicsCategories = remember {
            listOf(
                Category(0, "Television", R.drawable.ic_television_tab),
                Category(1, "Laundry", R.drawable.ic_two_laundry_tab),
                Category(2, "Fridge", R.drawable.ic_fridge_tab),
                Category(3, "Home Deals", R.drawable.ic_homedeals_tab),
                Category(4, "ACs", R.drawable.ic_acs_tab),
                Category(5, "FK Originals", R.drawable.ic_fk_originals_tab),
                Category(6, "Vacuum Cleaner", R.drawable.ic_vacuum_cleaner_tab),
                Category(7, "Kitchen Deals", R.drawable.ic_kitchen_deals_tab),
                Category(8, "Inverter & Battery", R.drawable.ic_inverter_battery_tab),
                Category(9, "Microwaves", R.drawable.ic_microwaves_tab),
                Category(10, "Dishwasher", R.drawable.ic_dishwasher_tab),
                Category(11, "Fans", R.drawable.ic_fans_tab),
                  )
        }

        var selectedCategory by remember { mutableStateOf<Category?>(null) }
        var selectedMobileCategory by remember { mutableStateOf<Category?>(null) }
        Column {
            CategoryProducts(
                categories = electronicsCategories,
                onCategorySelected = { category ->
                    selectedCategory = category
                    // Handle category selection (navigation, filtering, etc.)
                    println("Selected category: ${category.name}")
                },
                modifier = Modifier.fillMaxWidth(),
                initialSelectedCategory = electronicsCategories.first(),
                itemWidth = 75, // Custom width
                itemHeight = 65, // Custom height
                horizontalSpacing = 8, // Custom spacing
                verticalSpacing = 8,
                backgroundColor = MaterialTheme.customColors.white // Light blue background
            )

            val myProducts = listOf(
                Product("best Selling", "Extra ₹1,000 off", R.drawable.best_seelling),
                Product("Washers", "Extra ₹1,000 off", R.drawable.washers),
                Product("Air Conditioners", "Extra ₹1,500 off", R.drawable.air_conditioners)
            )

            Spacer(
                modifier = Modifier.height(2.dp)
                    .fillMaxWidth()
                    .background(MaterialTheme.customColors.spacerColor)
            )

            // Call the reusable component
            ProductList(
                products = myProducts,
//                sectionTitle = "Featured Products", // Optional
                showName = true,  // Show name under image
                showPrice = true  // Show price under image
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun HomeCategoryPage() {
    var selectedCategory = remember { mutableStateOf("All Deals") }
    val bannerImages = listOf(
        painterResource(id = R.drawable.home_category_banner1),
        painterResource(id = R.drawable.home_category_banner2),
        painterResource(id = R.drawable.home_category_banner3),
        painterResource(id = R.drawable.home_category_banner4),
        painterResource(id = R.drawable.home_category_banner5),
        painterResource(id = R.drawable.home_category_banner6),
        painterResource(id = R.drawable.home_category_banner7),
        painterResource(id = R.drawable.home_category_banner8),
        )
    Column(
        modifier = Modifier
            .fillMaxSize()
//            .padding(16.dp)
    ) {
        BannerHome(
            images = bannerImages,
            onImageClick = { page ->
                when (page) {
                    0 -> onBanner1Click()
                    1 -> onBanner2Click()
                    2 -> onBanner3Click()
                }
            },
            autoScrollDelay = 2000,
            height = 270.dp,
            dotSize = 8.dp,
            modifier = Modifier.padding(bottom = 0.dp)
        )

        Spacer(
            modifier = Modifier.height(2.dp)
                .fillMaxWidth()
                .background(MaterialTheme.customColors.spacerColor)
        )
        // Carousel Double
        val electronicsCategories = remember {
            listOf(
                Category(0, "Shop Now", R.drawable.ic_shop_now_home_tab),
                Category(1, "Containers", R.drawable.ic_containers_home_tab),
                Category(2, "Decor", R.drawable.ic_decor_home_tab),
                Category(3, "Drinkware", R.drawable.ic_drinkware_home_tab),
                Category(4, "Dining", R.drawable.ic_dining_home_tab),
                Category(5, "Bathroom", R.drawable.ic_bathroom_home_tab),
                Category(6, "Cleaning", R.drawable.ic_cleaning_home_tab),
                Category(7, "Floor Covers", R.drawable.ic_floor_covers_home_tab),
                Category(8, "Wall Decor", R.drawable.ic_wall_decor_home_tab),
                Category(9, "Bath Towels", R.drawable.ic_bath_towels_home_tab),
                Category(10, "Sofas", R.drawable.ic_sofas_home_tab),
                Category(11, "Cookware", R.drawable.ic_cookware_home_tab),
                Category(12, "Furnishing", R.drawable.ic_furnishing_home_tab),
                Category(13, "Hardware", R.drawable.ic_hardware_home_tab),
                Category(14, "Lighting", R.drawable.ic_lighting_home_tab),
                Category(15, "Bedsheets", R.drawable.ic_bedsheets_home_tab),
                Category(16, "Furniture", R.drawable.ic_furniture_home_tab),
                Category(17, "Blankets", R.drawable.ic_blankets_home_tab),
                Category(18, "Utilities", R.drawable.ic_utilities_home_tab),
                Category(19, "Pooja Needs", R.drawable.ic_pooja_needs_home_tab),
                Category(20, "Gardening", R.drawable.ic_gardening_home_tab),
                Category(21, "View All", R.drawable.ic_view_all_home_tab),
            )
        }

        var selectedCategory by remember { mutableStateOf<Category?>(null) }
        var selectedMobileCategory by remember { mutableStateOf<Category?>(null) }
        Column {
            CategoryProducts(
                categories = electronicsCategories,
                onCategorySelected = { category ->
                    selectedCategory = category
                    // Handle category selection (navigation, filtering, etc.)
                    println("Selected category: ${category.name}")
                },
                modifier = Modifier.fillMaxWidth(),
                initialSelectedCategory = electronicsCategories.first(),
                itemWidth = 75, // Custom width
                itemHeight = 65, // Custom height
                horizontalSpacing = 8, // Custom spacing
                verticalSpacing = 8,
                backgroundColor = MaterialTheme.customColors.white // Light blue background
            )

            val myProducts = listOf(
                Product("Plant stands", "From ₹99", R.drawable.plant_stands),
                Product("Laundry baskets", "From ₹100", R.drawable.laundry_baskets),
                Product("Prestige", "Up to 50% Off", R.drawable.prestige)
            )

            Spacer(
                modifier = Modifier.height(2.dp)
                    .fillMaxWidth()
                    .background(MaterialTheme.customColors.spacerColor)
            )

            // Call the reusable component
            ProductList(
                products = myProducts,
//                sectionTitle = "Featured Products", // Optional
                showName = true,  // Show name under image
                showPrice = true  // Show price under image
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun FoodHealthCategoryPage() {
    var selectedCategory = remember { mutableStateOf("All Deals") }
    val bannerImages = listOf(
        painterResource(id = R.drawable.food_health_banner1),
        painterResource(id = R.drawable.food_health_banner2),
        painterResource(id = R.drawable.food_health_banner3),
        painterResource(id = R.drawable.food_health_banner4),
        painterResource(id = R.drawable.food_health_banner5),
        painterResource(id = R.drawable.food_health_banner6),
        painterResource(id = R.drawable.food_health_banner7),
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
//            .padding(16.dp)
    ) {
        BannerHome(
            images = bannerImages,
            onImageClick = { page ->
                when (page) {
                    0 -> onBanner1Click()
                    1 -> onBanner2Click()
                    2 -> onBanner3Click()
                }
            },
            autoScrollDelay = 2000,
            height = 270.dp,
            dotSize = 8.dp,
            modifier = Modifier.padding(bottom = 0.dp)
        )

        Spacer(
            modifier = Modifier.height(2.dp)
                .fillMaxWidth()
                .background(MaterialTheme.customColors.spacerColor)
        )
        // Carousel Double
        val electronicsCategories = remember {
            listOf(
                Category(0, "Dry Fruits", R.drawable.ic_dry_fruits_food_health_tab),
                Category(1, "Oil & Ghee", R.drawable.ic_oil_ghee_food_health_tab),
                Category(2, "Spreads", R.drawable.ic_spreads_home_food_health_tab),
                Category(3, "Chokolates", R.drawable.ic_chokolates_food_health_tab),
                Category(4, "Hot Brews", R.drawable.ic_hot_brews_home_food_health_tab),
                Category(5, "Breakfast", R.drawable.ic_breakfast_home_food_health_tab),
                Category(6, "Sweets", R.drawable.ic_sweets_home_food_health_tab),
                Category(7, "Petfood", R.drawable.ic_petfood_home_food_health_tab),
                Category(8, "Nutrition", R.drawable.ic_nutrition_home_food_health_tab),
                Category(9, "Protein", R.drawable.ic_protein_home_food_health_tab),
                Category(10, "Vitamin", R.drawable.ic_vitamin_home_food_health_tab),
                Category(11, "Ayurveda", R.drawable.ic_ayurveda_home_food_health_tab),
                Category(12, "Energy", R.drawable.ic_energy_home_food_health_tab),
                Category(13, "Sexual", R.drawable.ic_sexual_home_food_health_tab),
                Category(14, "Medical", R.drawable.ic_medical_home_food_health_tab),
                Category(15, "Household", R.drawable.ic_household_food_health_tab),
                  )
        }

        var selectedCategory by remember { mutableStateOf<Category?>(null) }
        var selectedMobileCategory by remember { mutableStateOf<Category?>(null) }
        Column {
            CategoryProducts(
                categories = electronicsCategories,
                onCategorySelected = { category ->
                    selectedCategory = category
                    // Handle category selection (navigation, filtering, etc.)
                    println("Selected category: ${category.name}")
                },
                modifier = Modifier.fillMaxWidth(),
                initialSelectedCategory = electronicsCategories.first(),
                itemWidth = 75, // Custom width
                itemHeight = 65, // Custom height
                horizontalSpacing = 8, // Custom spacing
                verticalSpacing = 8,
                backgroundColor = MaterialTheme.customColors.white // Light blue background
            )

            val myProducts = listOf(
                Product("Edible Seeds", "Min. 25% Off", R.drawable.edible_seeds),
                Product("Vitamins", "Min. 30% Off", R.drawable.vitamins),
                Product("Dry Fruits", "Min. 40% Off", R.drawable.dry_fruits)
            )

            Spacer(
                modifier = Modifier.height(2.dp)
                    .fillMaxWidth()
                    .background(MaterialTheme.customColors.spacerColor)
            )

            // Call the reusable component
            ProductList(
                products = myProducts,
//                sectionTitle = "Featured Products", // Optional
                showName = true,  // Show name under image
                showPrice = true  // Show price under image
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun ToysKidsCategoryPage() {
    var selectedCategory = remember { mutableStateOf("All Deals") }
    val bannerImages = listOf(
        painterResource(id = R.drawable.toys_kids_banner1),
        painterResource(id = R.drawable.toys_kids_banner2),
        painterResource(id = R.drawable.toys_kids_banner3),
        painterResource(id = R.drawable.toys_kids_banner4),
        painterResource(id = R.drawable.toys_kids_banner5),
        painterResource(id = R.drawable.toys_kids_banner6),
        painterResource(id = R.drawable.toys_kids_banner7),
        painterResource(id = R.drawable.toys_kids_banner8),
        painterResource(id = R.drawable.toys_kids_banner9),
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
//            .padding(16.dp)
    ) {
        BannerHome(
            images = bannerImages,
            onImageClick = { page ->
                when (page) {
                    0 -> onBanner1Click()
                    1 -> onBanner2Click()
                    2 -> onBanner3Click()
                }
            },
            autoScrollDelay = 2000,
            height = 270.dp,
            dotSize = 8.dp,
            modifier = Modifier.padding(bottom = 0.dp)
        )

        Spacer(
            modifier = Modifier.height(2.dp)
                .fillMaxWidth()
                .background(MaterialTheme.customColors.spacerColor)
        )
        // Carousel Double
        val electronicsCategories = remember {
            listOf(
                Category(0, "Toys & games", R.drawable.ic_toys_games_toys_kids_tab),
                Category(1, "Diapers", R.drawable.ic_diapers_toys_kids_tab),
                Category(2, "Travel", R.drawable.ic_travel_toys_kids_tab),
                Category(3, "Bedding", R.drawable.ic_bedding_toys_kids_tab),
                Category(4, "Supplies", R.drawable.ic_supplies_toys_kids_tab),
                Category(5, "Top Brands", R.drawable.ic_top_brands_toys_kids_tab),
                Category(6, "Wipes", R.drawable.ic_wipes_toys_kids_tab),
                Category(7, "Gifting", R.drawable.ic_gifting_toys_kids_tab),
                Category(8, "Skin & hair", R.drawable.ic_skin_hair_toys_kids_tab),
                Category(9, "Stationery", R.drawable.ic_stationery_toys_kids_tab),
                Category(10, "Arts & Crafts", R.drawable.ic_arts_crafts_toys_kids_tab),
                Category(11, "STEM Toys", R.drawable.ic_stem_toys_toys_kids_tab),
                )
        }

        var selectedCategory by remember { mutableStateOf<Category?>(null) }
        var selectedMobileCategory by remember { mutableStateOf<Category?>(null) }
        Column {
            CategoryProducts(
                categories = electronicsCategories,
                onCategorySelected = { category ->
                    selectedCategory = category
                    // Handle category selection (navigation, filtering, etc.)
                    println("Selected category: ${category.name}")
                },
                modifier = Modifier.fillMaxWidth(),
                initialSelectedCategory = electronicsCategories.first(),
                itemWidth = 75, // Custom width
                itemHeight = 65, // Custom height
                horizontalSpacing = 8, // Custom spacing
                verticalSpacing = 8,
                backgroundColor = MaterialTheme.customColors.white // Light blue background
            )

//            val myProducts = listOf(
//                Product("Essentials", "Upto 90% Off", R.drawable.essentials),
//                Product("Scooters & ride-ons", "From ₹549", R.drawable.scooters_ride_ons),
//                Product("Walker", "From ₹715", R.drawable.walker)
//            )
//
//            Spacer(
//                modifier = Modifier.height(2.dp)
//                    .fillMaxWidth()
//                    .background(MaterialTheme.customColors.spacerColor)
//            )
//
//            // Call the reusable component
//            ProductList(
//                products = myProducts,
////                sectionTitle = "Featured Products", // Optional
//                showName = true,  // Show name under image
//                showPrice = true  // Show price under image
//            )
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun AutoAccessoriesCategoryPage() {
    Column(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Auto Accessories",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = "Car accessories and parts",
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}

@Composable
fun SportsCategoryPage() {
    Column(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Sports",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = "Sports equipment and gear",
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}

@Composable
fun FurnitureCategoryPage() {
    Column(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Furniture",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = "Home and office furniture",
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}

@Composable
fun TwoWheelersCategoryPage() {
    Column(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "2 Wheelers",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = "Bikes and scooters",
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}

@Composable
fun BooksCategoryPage() {
    Column(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Books",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = "Books and educational material",
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}

@Composable
fun MusicalCategoryPage() {
    Column(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Musical",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = "Musical instruments and equipment",
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}

@Composable
fun UltraCategoryPage() {
    Column(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Ultra",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = "Premium and luxury products",
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}

// Remove or comment out the old category pages that are no longer needed
/*
@Composable
fun TodaysDealCategoryPage() { ... }

@Composable
fun EconomyCategoryPage() { ... }

@Composable
fun BrideGroomCategoryPage() { ... }

@Composable
fun AirportDutyFreeCategoryPage() { ... }

@Composable
fun ElectricVehiclesCategoryPage() { ... }

@Composable
fun IndustryCategoryPage() { ... }

@Composable
fun WholesaleCategoryPage() { ... }

@Composable
fun SellCategoryPage() { ... }
*/

// Rest of the existing code remains the same...
@Composable
fun AllCategoryPage() {
    // Example click handlers (define these where needed)
    fun onJoinPrimeClick() {
        // Handle join prime click
    }

    fun onDealBoxClick() {
        // Handle deal box click
    }

    fun onSpecialOfferClick() {
        // Handle special offer click
    }
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        val bannerImages = listOf(
//            painterResource(id = R.drawable.banner_home1),
//            painterResource(id = R.drawable.banner_home2),
//            painterResource(id = R.drawable.banner_home3),
            painterResource(id = R.drawable.banner_home4),
            painterResource(id = R.drawable.banner_home5),
            painterResource(id = R.drawable.banner_home6),
            painterResource(id = R.drawable.banner_home7),
            painterResource(id = R.drawable.banner_home8),
            painterResource(id = R.drawable.banner_home9),
            painterResource(id = R.drawable.banner_home10),
            painterResource(id = R.drawable.banner_home11),
            painterResource(id = R.drawable.banner_home12),
            painterResource(id = R.drawable.banner_home13),
            painterResource(id = R.drawable.banner_home14),
            painterResource(id = R.drawable.banner_home15)
        )

        BannerHome(
            images = bannerImages,
            onImageClick = { page -> /* handle click */ },
            autoScrollDelay = 2000,
            height = 270.dp,
            dotSize = 8.dp
        )

        Spacer(
            modifier = Modifier.height(2.dp)
                .fillMaxWidth()
                .background(MaterialTheme.customColors.spacerColor)
        )

        CategoryProducts(modifier = Modifier.fillMaxWidth().wrapContentHeight())

        Spacer(
            modifier = Modifier.height(2.dp)
                .fillMaxWidth()
                .background(MaterialTheme.customColors.spacerColor)
        )

        AdsSponsored(onAdClick = { println("Ad clicked!") })
        Spacer(
            modifier = Modifier.height(2.dp)
                .fillMaxWidth()
                .background(MaterialTheme.customColors.spacerColor)
        )
        BikeCategoryHeader()
        Spacer(
            modifier = Modifier.height(2.dp)
                .fillMaxWidth()
                .background(MaterialTheme.customColors.spacerColor)
        )
        ElectronicCategory()
        Spacer(
            modifier = Modifier.height(2.dp)
                .fillMaxWidth()
                .background(MaterialTheme.customColors.spacerColor)
        )
        FurnitureCategory()
        Spacer(
            modifier = Modifier.height(2.dp)
                .fillMaxWidth()
                .background(MaterialTheme.customColors.spacerColor)
        )
        ClothsCategory()
    }
}

@Composable
fun ShoppingCategoryPage() {
    var selectedCategory by remember { mutableStateOf("All") }
    val bannerImages = listOf(
        painterResource(id = R.drawable.mobile_banner1),
        painterResource(id = R.drawable.shopping_banner2),
        painterResource(id = R.drawable.shopping_banner3),
        painterResource(id = R.drawable.shopping_banner4),
        painterResource(id = R.drawable.shopping_banner5),
        painterResource(id = R.drawable.shopping_banner6),
        painterResource(id = R.drawable.mobile_banner2),
        painterResource(id = R.drawable.shopping_banner8),
        painterResource(id = R.drawable.mobile_banner3),
    )

    Column(
        modifier = Modifier.fillMaxSize().padding(0.dp)
    ) {
        BannerHome(
            images = bannerImages,
            onImageClick = { page ->
                when (page) {
                    0 -> onBanner1Click()
                    1 -> onBanner2Click()
                    2 -> onBanner3Click()
                }
            },
            autoScrollDelay = 2000,
            height = 270.dp,
            dotSize = 8.dp,
            modifier = Modifier.padding(bottom = 0.dp)
        )

        Spacer(
            modifier = Modifier.height(2.dp)
                .fillMaxWidth()
                .background(MaterialTheme.customColors.spacerColor)
        )
        CategoryProducts(modifier = Modifier.fillMaxWidth().wrapContentHeight())

        Spacer(modifier = Modifier.height(16.dp))
        when (selectedCategory) {
            "All" -> AllShopping(categoryName = selectedCategory)
            "Electronics" -> ElectronicsShopping(categoryName = selectedCategory)
            else -> AllShopping(categoryName = selectedCategory)
        }
    }
}

@Composable
fun MobileCategoryPage() {
    // Track currently selected category
    var selectedCategory = remember { mutableStateOf("All Deals") }
    val bannerImages = listOf(
        painterResource(id = R.drawable.mobile_banner1),
        painterResource(id = R.drawable.mobile_banner2),
        painterResource(id = R.drawable.mobile_banner3),
        painterResource(id = R.drawable.banner_home5),
        painterResource(id = R.drawable.banner_home7),
        painterResource(id = R.drawable.banner_home8),
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
//            .padding(16.dp)
    ) {
        BannerHome(
            images = bannerImages,
            onImageClick = { page ->
                when (page) {
                    0 -> onBanner1Click()
                    1 -> onBanner2Click()
                    2 -> onBanner3Click()
                }
            },
            autoScrollDelay = 2000,
            height = 270.dp,
            dotSize = 8.dp,
            modifier = Modifier.padding(bottom = 0.dp)
        )

        Spacer(
            modifier = Modifier.height(2.dp)
                .fillMaxWidth()
                .background(MaterialTheme.customColors.spacerColor)
        )
        // Carousel Double
        val MobileCategories = remember {
            listOf(
                Category(0, "iphone", R.drawable.ic_iphone),
                Category(1, "motoriola", R.drawable.ic_motoriola),
                Category(2, "Samsung", R.drawable.ic_samsung),
                Category(3, "Vivo", R.drawable.ic_vivo),
                Category(4, "OPPO", R.drawable.ic_oppo),
                Category(5, "realme", R.drawable.ic_realme),
                Category(6, "Nothing", R.drawable.ic_nothing),
                Category(7, "POCO", R.drawable.ic_poco),
                Category(8, "Google", R.drawable.ic_google),
                Category(9, "AI+", R.drawable.ic_aiplus),
                Category(10, "Minutes", R.drawable.ic_minutes),
                Category(11, "Redmi", R.drawable.ic_redmi),
                Category(12, "Tecno", R.drawable.ic_tecno),
                Category(13, "Infinix", R.drawable.ic_infinix),
                Category(14, "Alcatel", R.drawable.ic_alcatel),
                Category(15, "Snapdragon", R.drawable.ic_snapdragon),
                Category(16, "Android", R.drawable.ic_android),
                Category(17, "Itel", R.drawable.ic_itel)
            )
        }

        Spacer(
            modifier = Modifier.height(2.dp)
                .fillMaxWidth()
                .background(MaterialTheme.customColors.spacerColor)
        )

        var selectedCategory by remember { mutableStateOf<Category?>(null) }
        var selectedMobileCategory by remember { mutableStateOf<Category?>(null) }
        Column {
            CategoryProducts(
                categories = MobileCategories,
                onCategorySelected = { category ->
                    selectedCategory = category
                    // Handle category selection (navigation, filtering, etc.)
                    println("Selected category: ${category.name}")
                },
                modifier = Modifier.fillMaxWidth(),
                initialSelectedCategory = MobileCategories.first(),
                itemWidth = 75, // Custom width
                itemHeight = 65, // Custom height
                horizontalSpacing = 8, // Custom spacing
                verticalSpacing = 8,
                backgroundColor = MaterialTheme.customColors.white // Light blue background
            )

            val myProducts = listOf(
                Product("OPPO K13x 5G", "From ₹9,499*", R.drawable.oppo_k13x),
                Product("vivo T4x 5G", "From ₹12,249*", R.drawable.vivo_t4x),
                Product("moto g96 (8GB)", "Just ₹14,999*", R.drawable.moto_g96)
            )
            Spacer(
                modifier = Modifier.height(2.dp)
                    .fillMaxWidth()
                    .background(MaterialTheme.customColors.spacerColor)
            )
            // Call the reusable component
            ProductList(
                products = myProducts,
//                sectionTitle = "Featured Products", // Optional
                showName = true,  // Show name under image
                showPrice = true  // Show price under image
            )
            // Display content based on selected category
//            selectedCategory?.let { category ->
//                Text(
//                    text = "Showing products for: ${category.name}",
//                    modifier = Modifier.padding(16.dp)
//                )
//            }
        }
//        MobileCarousel(
//            selectedCategory = selectedCategory.value,
//            onCategoryClick = { category ->
//                selectedCategory.value = category
//                // Handle category click, e.g., filter products
//            }
//        )

        Spacer(modifier = Modifier.height(16.dp))

//         (selectedCategory = selectedMobileCategory?.name ?: "All")
        // Example: Display selected category

//        Column(
//            modifier = Modifier.fillMaxWidth().padding(16.dp),
//            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.Center
//        ) {
//            Text(
//                text = "Selected Category: ${selectedCategory.value}",
//                style = MaterialTheme.typography.bodyMedium
//            )
//        }
    }
}


@Composable
fun MainScreen(navController: NavHostController) {
    var currentPage by remember { mutableIntStateOf(0) }
    Column(modifier = Modifier.fillMaxWidth()) {
        CategoryTabs(
            onCategorySelected = { categoryPage ->
                currentPage = when (categoryPage) {
                    is CategoryPage.All -> 0
                    is CategoryPage.Mobile -> 1
                    is CategoryPage.Electronics -> 2
                    is CategoryPage.Appliances -> 3
                    is CategoryPage.Home -> 4
                    is CategoryPage.FoodHealth -> 5
                    is CategoryPage.ToysKids -> 6
                    is CategoryPage.AutoAccessories -> 7
                    is CategoryPage.Sports -> 8
                    is CategoryPage.Furniture -> 9
                    is CategoryPage.TwoWheelers -> 10
                    is CategoryPage.Books -> 11
                    is CategoryPage.Musical -> 12
                    is CategoryPage.Ultra -> 13
                }
            }
        )
    }
}

private fun onBanner1Click() {
    // Handle banner 1 click
}

private fun onBanner2Click() {
    // Handle banner 2 click
}

private fun onBanner3Click() {
    // Handle banner 3 click
}