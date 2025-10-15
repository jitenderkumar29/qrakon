package com.example.qrakon.components.categorytabs

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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
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
    object FourWheeler : CategoryPage()

    object Gemstone : CategoryPage()
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
        "Four Wheeler" to R.drawable.ic_four_wheeler,
        "Gemstone" to R.drawable.ic_gemstone,
    )

    Column(modifier = Modifier.fillMaxWidth()) {
        ScrollableTabRow(
            selectedTabIndex = selectedTabIndex,
            containerColor = MaterialTheme.customColors.skyBlue,
            contentColor = MaterialTheme.customColors.black,
            edgePadding = 0.dp,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                    height = 5.dp,
//                    color = Color.Blue
                    color = MaterialTheme.customColors.onPrimaryContainer
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
                            13 -> CategoryPage.FourWheeler
                            14 -> CategoryPage.Gemstone
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
                                MaterialTheme.customColors.onPrimaryContainer // Different color when selected
//                                Color.Blue
                            } else {
                                MaterialTheme.customColors.black
                            }
                        )

//                        Spacer(modifier = Modifier.height(2.dp))

                        Text(
                            text = title,
                            fontSize = 12.sp, // Slightly smaller font for better fit
                            fontWeight = if (selectedTabIndex == index) FontWeight.Bold else FontWeight.Medium,
                            color = if (selectedTabIndex == index) {
                                MaterialTheme.customColors.onPrimaryContainer
//                                Color.Blue
                            } else {
                                MaterialTheme.customColors.black
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
            13 -> FourWheelerCategoryPage()
            14 -> GemstoneCategoryPage()
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
                Category(17, "View All", R.drawable.ic_view_all_home_tab),

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

            Spacer(
                modifier = Modifier.height(2.dp)
                    .fillMaxWidth()
                    .background(MaterialTheme.customColors.spacerColor)
            )

            AdsSponsored(
                adImages = listOf(
                    R.drawable.ads_sponsored_electronics_1,
                    R.drawable.ads_sponsored_electronics_2,
                    R.drawable.ads_sponsored_electronics_3,
                    R.drawable.ads_sponsored_electronics_4
                ),
                onAdClick = { index ->
                    println("Clicked ad index: $index")
                }
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
//            WomenCategoryList()
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
//        painterResource(id = R.drawable.appliances_banner3),
        painterResource(id = R.drawable.appliances_banner4),
//        painterResource(id = R.drawable.appliances_banner5),

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
        val appliancesCategories = remember {
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
                categories = appliancesCategories,
                onCategorySelected = { category ->
                    selectedCategory = category
                    // Handle category selection (navigation, filtering, etc.)
                    println("Selected category: ${category.name}")
                },
                modifier = Modifier.fillMaxWidth(),
                initialSelectedCategory = appliancesCategories.first(),
                itemWidth = 75, // Custom width
                itemHeight = 65, // Custom height
                horizontalSpacing = 8, // Custom spacing
                verticalSpacing = 8,
                backgroundColor = MaterialTheme.customColors.white // Light blue background
            )

            Spacer(
                modifier = Modifier.height(2.dp)
                    .fillMaxWidth()
                    .background(MaterialTheme.customColors.spacerColor)
            )

            AdsSponsored(
                adImages = listOf(
                    R.drawable.appliances_banner3,
                    R.drawable.appliances_banner5,

                ),
                onAdClick = { index ->
                    println("Clicked ad index: $index")
                }
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
//        painterResource(id = R.drawable.home_category_banner4),
//        painterResource(id = R.drawable.home_category_banner5),
//        painterResource(id = R.drawable.home_category_banner6),
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
        val homeCategories = remember {
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
                categories = homeCategories,
                onCategorySelected = { category ->
                    selectedCategory = category
                    // Handle category selection (navigation, filtering, etc.)
                    println("Selected category: ${category.name}")
                },
                modifier = Modifier.fillMaxWidth(),
                initialSelectedCategory = homeCategories.first(),
                itemWidth = 75, // Custom width
                itemHeight = 65, // Custom height
                horizontalSpacing = 8, // Custom spacing
                verticalSpacing = 8,
                backgroundColor = MaterialTheme.customColors.white // Light blue background
            )

            Spacer(
                modifier = Modifier.height(2.dp)
                    .fillMaxWidth()
                    .background(MaterialTheme.customColors.spacerColor)
            )

            AdsSponsored(
                adImages = listOf(
                    R.drawable.home_category_banner4,
                    R.drawable.home_category_banner5,
                    R.drawable.home_category_banner6,
//                    R.drawable.ads_spoonsored_home_1,
//                    R.drawable.ads_sponsored_home_2,

                    ),
                onAdClick = { index ->
                    println("Clicked ad index: $index")
                }
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
        val foodhealthCategories = remember {
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
                categories = foodhealthCategories,
                onCategorySelected = { category ->
                    selectedCategory = category
                    // Handle category selection (navigation, filtering, etc.)
                    println("Selected category: ${category.name}")
                },
                modifier = Modifier.fillMaxWidth(),
                initialSelectedCategory = foodhealthCategories.first(),
                itemWidth = 75, // Custom width
                itemHeight = 65, // Custom height
                horizontalSpacing = 8, // Custom spacing
                verticalSpacing = 8,
                backgroundColor = MaterialTheme.customColors.white // Light blue background
            )

            Spacer(
                modifier = Modifier.height(2.dp)
                    .fillMaxWidth()
                    .background(MaterialTheme.customColors.spacerColor)
            )

            AdsSponsored(
                adImages = listOf(

                    R.drawable.ads_spoonsored_foodhealth_1,
                    R.drawable.ads_spoonsored_foodhealth_2,

                ),
                onAdClick = { index ->
                    println("Clicked ad index: $index")
                }
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
        val toysKidsCategories = remember {
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
                categories = toysKidsCategories,
                onCategorySelected = { category ->
                    selectedCategory = category
                    // Handle category selection (navigation, filtering, etc.)
                    println("Selected category: ${category.name}")
                },
                modifier = Modifier.fillMaxWidth(),
                initialSelectedCategory = toysKidsCategories.first(),
                itemWidth = 75, // Custom width
                itemHeight = 65, // Custom height
                horizontalSpacing = 8, // Custom spacing
                verticalSpacing = 8,
                backgroundColor = MaterialTheme.customColors.white // Light blue background
            )

            Spacer(
                modifier = Modifier.height(2.dp)
                    .fillMaxWidth()
                    .background(MaterialTheme.customColors.spacerColor)
            )

            AdsSponsored(
                adImages = listOf(
                    R.drawable.ads_spoonsored_toyskids_1,
                    R.drawable.ads_spoonsored_toyskids_2,
                    R.drawable.ads_spoonsored_toyskids_3,

                    ),
                onAdClick = { index ->
                    println("Clicked ad index: $index")
                }
            )

            val myProducts = listOf(
                Product("Essentials", "Upto 90% Off", R.drawable.essentials),
                Product("Scooters & ride-ons", "From ₹549", R.drawable.scooters_ride_ons),
                Product("Walker", "From ₹715", R.drawable.walker)
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
fun AutoAccessoriesCategoryPage() {
    var selectedCategory = remember { mutableStateOf("All Deals") }
    val bannerImages = listOf(
        painterResource(id = R.drawable.auto_accessories_banner1),
//        painterResource(id = R.drawable.auto_accessories_banner2),
        painterResource(id = R.drawable.auto_accessories_banner3),
//        painterResource(id = R.drawable.auto_accessories_banner4),
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
        val autoAccessoriesCategories = remember {
            listOf(
                Category(0, "Dashcams", R.drawable.ic_dashcams_auto_acce),
                Category(1, "Riding gear", R.drawable.ic_riding_gear_auto_acce),
                Category(2, "Cleaner", R.drawable.ic_cleaner_auto_acce),
                Category(3, "Tyres", R.drawable.ic_tyres_auto_acce),
                Category(4, "Batteries", R.drawable.ic_batteries_auto_acce),
                Category(5, "Music sys", R.drawable.ic_music_sys_auto_acce),
                Category(6, "Engine oils", R.drawable.ic_engine_oils_auto_acce),
                Category(7, "Subwoofers", R.drawable.ic_subwoofers_auto_acce),
                Category(8, "Helmets", R.drawable.ic_helmets_hair_auto_acce),
                Category(9, "Car washer", R.drawable.ic_car_washer_auto_acce),
                Category(10, "Body covers", R.drawable.ic_body_covers_auto_acce),
                Category(11, "Styling", R.drawable.ic_styling_auto_acce),
                Category(12, "Car mats", R.drawable.ic_car_mats_auto_acce),
                Category(13, "Lights", R.drawable.ic_lights_auto_acce),
                Category(14, "Type air pump", R.drawable.ic_type_air_pump_auto_acce),
                Category(15, "View All", R.drawable.ic_view_all_home_tab),

                )
        }

        var selectedCategory by remember { mutableStateOf<Category?>(null) }
        var selectedMobileCategory by remember { mutableStateOf<Category?>(null) }
        Column {
            CategoryProducts(
                categories = autoAccessoriesCategories,
                onCategorySelected = { category ->
                    selectedCategory = category
                    // Handle category selection (navigation, filtering, etc.)
                    println("Selected category: ${category.name}")
                },
                modifier = Modifier.fillMaxWidth(),
                initialSelectedCategory = autoAccessoriesCategories.first(),
                itemWidth = 75, // Custom width
                itemHeight = 65, // Custom height
                horizontalSpacing = 8, // Custom spacing
                verticalSpacing = 8,
                backgroundColor = MaterialTheme.customColors.white // Light blue background
            )

            Spacer(
                modifier = Modifier.height(2.dp)
                    .fillMaxWidth()
                    .background(MaterialTheme.customColors.spacerColor)
            )

            AdsSponsored(
                adImages = listOf(
                    R.drawable.auto_accessories_banner2,
                    R.drawable.auto_accessories_banner4,
//                    R.drawable.ads_spoonsored_autoacce_3,
                    ),
                onAdClick = { index ->
                    println("Clicked ad index: $index")
                }
            )

            val myProducts = listOf(
                Product("SMK", "Flat 28% Off", R.drawable.smk),
                Product("Redtiger", "40-60% Off", R.drawable.redtiger),
                Product("Steelbird", "Flat 30% Off", R.drawable.steelbird)
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
fun SportsCategoryPage() {
    var selectedCategory = remember { mutableStateOf("All Deals") }
    val bannerImages = listOf(
        painterResource(id = R.drawable.sports_banner1),
//        painterResource(id = R.drawable.sports_banner2),
        painterResource(id = R.drawable.sports_banner3),
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
        val sportsCategories = remember {
            listOf(
                Category(0, "Badminton", R.drawable.ic_badminton_sports_tab),
                Category(1, "Cycles", R.drawable.ic_cycles_sports_tab),
                Category(2, "Gym Combo", R.drawable.ic_gym_combo_sports_tab),
                Category(3, "Fitness", R.drawable.ic_fitness_sports_tab),
                Category(4, "Ball Sports", R.drawable.ic_ball_sports_sports_tab),
                Category(5, "Camping", R.drawable.ic_camping_sports_tab),
                Category(6, "Yoga", R.drawable.ic_yoga_sports_tab),
                Category(7, "Protein", R.drawable.ic_protein_sports_tab),
                Category(8, "Treadmills", R.drawable.ic_treadmills_sports_tab),
                Category(9, "Excercise", R.drawable.ic_excercise_sports_tab),
                Category(10, "Cricket", R.drawable.ic_cricket_sports_tab),
                Category(11, "Indoor sports", R.drawable.ic_indoor_sports_sports_tab),
                Category(12, "Kids Cycles", R.drawable.ic_kids_cycles_sports_tab),
                Category(13, "Kids favs", R.drawable.ic_kids_favs_sports_tab),
                Category(14, "Electric cycles", R.drawable.ic_electric_cycles_sports_tab),
                Category(15, "View All", R.drawable.ic_view_all_home_tab),

                )
        }

        var selectedCategory by remember { mutableStateOf<Category?>(null) }
        var selectedMobileCategory by remember { mutableStateOf<Category?>(null) }
        Column {
            CategoryProducts(
                categories = sportsCategories,
                onCategorySelected = { category ->
                    selectedCategory = category
                    // Handle category selection (navigation, filtering, etc.)
                    println("Selected category: ${category.name}")
                },
                modifier = Modifier.fillMaxWidth(),
                initialSelectedCategory = sportsCategories.first(),
                itemWidth = 75, // Custom width
                itemHeight = 65, // Custom height
                horizontalSpacing = 8, // Custom spacing
                verticalSpacing = 8,
                backgroundColor = MaterialTheme.customColors.white // Light blue background
            )

            Spacer(
                modifier = Modifier.height(2.dp)
                    .fillMaxWidth()
                    .background(MaterialTheme.customColors.spacerColor)
            )

            AdsSponsored(
                adImages = listOf(
                    R.drawable.ads_spoonsored_sports_1,
                    R.drawable.sports_banner2,
                ),
                onAdClick = { index ->
                    println("Clicked ad index: $index")
                }
            )

            val myProducts = listOf(
                Product("Home Gym Combo", "From ₹1699", R.drawable.home_gym_combo),
                Product("Adult cycle", "Min 40% Off", R.drawable.adult_cycle),
                Product("Treadmill", "Up to 70% Off", R.drawable.treadmill)
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
fun FurnitureCategoryPage() {
    var selectedCategory = remember { mutableStateOf("All Deals") }
    val bannerImages = listOf(
        painterResource(id = R.drawable.furniture_banner1),
//        painterResource(id = R.drawable.furniture_banner2),
        painterResource(id = R.drawable.furniture_banner3),
        painterResource(id = R.drawable.furniture_banner4),
//        painterResource(id = R.drawable.furniture_banner5),
        painterResource(id = R.drawable.furniture_banner6),
        painterResource(id = R.drawable.furniture_banner7),
        painterResource(id = R.drawable.furniture_banner8),
        painterResource(id = R.drawable.furniture_banner9),
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
        val furnitureCategories = remember {
            listOf(
                Category(0, "Mattresses", R.drawable.ic_mattresses_furniture_tab),
                Category(1, "Beds", R.drawable.ic_beds_furniture_tab),
                Category(2, "Sofas", R.drawable.ic_sofas_furniture_tab),
                Category(3, "Laptop tables", R.drawable.ic_laptop_tables_furniture_tab),
                Category(4, "Coffee tables", R.drawable.ic_coffee_tables_furniture_tab),
                Category(5, "Sofa beds", R.drawable.ic_sofa_beds_furniture_tab),
                Category(6, "TV units", R.drawable.ic_tv_units_furniture_tab),
                Category(7, "Bath Fittings", R.drawable.ic_bath_fittings_furniture_tab),
                Category(8, "Kid's furniture", R.drawable.ic_kids_furniture_furniture_tab),
                Category(9, "Office chairs", R.drawable.ic_office_chairs_furniture_tab),
                Category(10, "Wardrobes", R.drawable.ic_wardrobes_furniture_tab),
                Category(11, "Dining sets", R.drawable.ic_dining_sets_furniture_tab),
                Category(12, "Collapsibles", R.drawable.ic_collapsibles_furniture_tab),
                Category(13, "Hammock", R.drawable.ic_hammock_furniture_tab),
                Category(14, "Shoe racks", R.drawable.ic_shoe_racks_furniture_tab),
                Category(15, "Pooja Mandir", R.drawable.ic_pooja_mandir_furniture_tab),
                Category(16, "Inflatables", R.drawable.ic_inflatables_furniture_tab),
                Category(17, "View all", R.drawable.ic_view_all_furniture_tab),
//                Category(18, "Top25 deals", R.drawable.ic_top25_deals_furniture_tab),
            )
        }

        var selectedCategory by remember { mutableStateOf<Category?>(null) }
        var selectedMobileCategory by remember { mutableStateOf<Category?>(null) }
        Column {
            CategoryProducts(
                categories = furnitureCategories,
                onCategorySelected = { category ->
                    selectedCategory = category
                    // Handle category selection (navigation, filtering, etc.)
                    println("Selected category: ${category.name}")
                },
                modifier = Modifier.fillMaxWidth(),
                initialSelectedCategory = furnitureCategories.first(),
                itemWidth = 75, // Custom width
                itemHeight = 65, // Custom height
                horizontalSpacing = 8, // Custom spacing
                verticalSpacing = 8,
                backgroundColor = MaterialTheme.customColors.white // Light blue background
            )

            Spacer(
                modifier = Modifier.height(2.dp)
                    .fillMaxWidth()
                    .background(MaterialTheme.customColors.spacerColor)
            )

            AdsSponsored(
                adImages = listOf(
                    R.drawable.furniture_banner2,
                    R.drawable.furniture_banner5,
                ),
                onAdClick = { index ->
                    println("Clicked ad index: $index")
                }
            )

            val myProducts = listOf(
                Product("Bedroom furniture", "From ₹5,999", R.drawable.bedroom_furniture),
                Product("Wardrobes", "From ₹5,499", R.drawable.wardrobes),
                Product("Office chairs", "From ₹2,699", R.drawable.office_chairs)
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
fun TwoWheelersCategoryPage() {
    var selectedCategory = remember { mutableStateOf("All Deals") }
    val bannerImages = listOf(
        painterResource(id = R.drawable.two_wheelers_banner1),
        painterResource(id = R.drawable.two_wheelers_banner2),
        painterResource(id = R.drawable.two_wheelers_banner3),
        painterResource(id = R.drawable.two_wheelers_banner4),
        painterResource(id = R.drawable.two_wheelers_banner5),
        painterResource(id = R.drawable.two_wheelers_banner6),

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
        val twowheelersCategories = remember {
            listOf(
                Category(0, "Upcoming", R.drawable.ic_upcoming_two_wheelers_tab),
                Category(1, "New Launch", R.drawable.ic_new_launch_two_wheelers_tab),
//                Category(0, "Popular Brands", R.drawable.ic_popular_brands_two_wheelers_tab),
                Category(2, "Hero", R.drawable.ic_hero_two_wheelers_tab),
                Category(3, "Honda", R.drawable.ic_honda_two_wheelers_tab),
                Category(4, "Yamaha", R.drawable.ic_yamaha_two_wheelers_tab),
                Category(5, "TVS", R.drawable.ic_tvs_two_wheelers_tab),
                Category(6, "Bajaj", R.drawable.ic_bajaj_two_wheelers_tab),
                Category(7, "Suzuki", R.drawable.ic_suzuki_two_wheelers_tab),
                Category(8, "Royal Enfield", R.drawable.ic_royal_enfield_two_wheelers_tab),
                Category(9, "KTM", R.drawable.ic_ktm_two_wheelers_tab),
                Category(10, "Jawa", R.drawable.ic_jawa_two_wheelers_tab),
                Category(11, "Triumph", R.drawable.ic_triumph_two_wheelers_tab),
                Category(12, "Harley-Davidson", R.drawable.ic_harley_davidson_two_wheelers_tab),
                Category(13, "Aprilia", R.drawable.ic_aprilia_two_wheelers_tab),
                Category(14, "BMW", R.drawable.ic_bmw_two_wheelers_tab),
                Category(15, "View all", R.drawable.ic_view_all_furniture_tab),

                )
        }

        var selectedCategory by remember { mutableStateOf<Category?>(null) }
        var selectedMobileCategory by remember { mutableStateOf<Category?>(null) }
        Column {
            CategoryProducts(
                categories = twowheelersCategories,
                onCategorySelected = { category ->
                    selectedCategory = category
                    // Handle category selection (navigation, filtering, etc.)
                    println("Selected category: ${category.name}")
                },
                modifier = Modifier.fillMaxWidth(),
                initialSelectedCategory = twowheelersCategories.first(),
                itemWidth = 75, // Custom width
                itemHeight = 65, // Custom height
                horizontalSpacing = 8, // Custom spacing
                verticalSpacing = 8,
                backgroundColor = MaterialTheme.customColors.white // Light blue background
            )

            Spacer(
                modifier = Modifier.height(2.dp)
                    .fillMaxWidth()
                    .background(MaterialTheme.customColors.spacerColor)
            )

            AdsSponsored(
                adImages = listOf(
                    R.drawable.ads_spoonsored_two_wheeler_1,
                    R.drawable.ads_spoonsored_two_wheeler_2,
                    R.drawable.ads_spoonsored_two_wheeler_3,
                    R.drawable.ads_spoonsored_two_wheeler_4,
                    R.drawable.ads_spoonsored_two_wheeler_5,
                ),
                onAdClick = { index ->
                    println("Clicked ad index: $index")
                }
            )

            val myProducts = listOf(
                Product("Ampere", "Up to 95km* range", R.drawable.ampere_product),
                Product("Ather", "115km* range", R.drawable.ather_product),
                Product("OLA", "Up to 242km* range", R.drawable.ola_product)
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
fun BooksCategoryPage() {
    var selectedCategory = remember { mutableStateOf("All Deals") }
    val bannerImages = listOf(
        painterResource(id = R.drawable.books_banner1),
        painterResource(id = R.drawable.books_banner2),
        painterResource(id = R.drawable.books_banner3),
//        painterResource(id = R.drawable.books_banner4),
//        painterResource(id = R.drawable.books_banner5),

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
        val booksCategories = remember {
            listOf(
//                Category(0, "Guitar", R.drawable.ic_guitar_books_tab),
//                Category(1, "Microphones", R.drawable.ic_microphones_books_tab),
//                Category(2, "Keyboards", R.drawable.ic_keyboards_books_tab),
//                Category(3, "Cajons", R.drawable.ic_cajons_books_tab),
//                Category(4, "Instruments", R.drawable.ic_instruments_books_tab),
//                Category(5, "Amplifiers", R.drawable.ic_amplifiers_books_tab),
                Category(0, "Boxsets", R.drawable.ic_boxsets_books_tab),
                Category(1, "Children's", R.drawable.ic_childrens_books_tab),
                Category(2, "Comics", R.drawable.ic_comics_books_tab),
                Category(3, "Fiction", R.drawable.ic_fiction_books_tab),
                Category(4, "Exam Prep", R.drawable.ic_exam_prep_books_tab),
                Category(5, "School Books", R.drawable.ic_school_books_books_tab),
                Category(6, "Non-Fiction", R.drawable.ic_non_fiction_books_tab),
                Category(7, "Biographies", R.drawable.ic_biographies_books_tab),
                Category(8, "Science & Tech", R.drawable.ic_science_tech_books_tab),
                Category(9, "History", R.drawable.ic_history_books_tab),
                Category(10, "Romance", R.drawable.ic_romance_books_tab),
                Category(11, "Self Help", R.drawable.ic_self_help_books_tab))
        }

        var selectedCategory by remember { mutableStateOf<Category?>(null) }
        var selectedMobileCategory by remember { mutableStateOf<Category?>(null) }
        Column {
            CategoryProducts(
                categories = booksCategories,
                onCategorySelected = { category ->
                    selectedCategory = category
                    // Handle category selection (navigation, filtering, etc.)
                    println("Selected category: ${category.name}")
                },
                modifier = Modifier.fillMaxWidth(),
                initialSelectedCategory = booksCategories.first(),
                itemWidth = 75, // Custom width
                itemHeight = 65, // Custom height
                horizontalSpacing = 8, // Custom spacing
                verticalSpacing = 8,
                backgroundColor = MaterialTheme.customColors.white // Light blue background
            )

            Spacer(
                modifier = Modifier.height(2.dp)
                    .fillMaxWidth()
                    .background(MaterialTheme.customColors.spacerColor)
            )

            AdsSponsored(
                adImages = listOf(
                    R.drawable.ads_spoonsored_books_1,
                    R.drawable.ads_spoonsored_books_2,
                ),
                onAdClick = { index ->
                    println("Clicked ad index: $index")
                }
            )

            val myProducts = listOf(
                Product("English", "Up to 20% Off", R.drawable.english_product),
                Product("Hindi", "Up to 25% Off", R.drawable.hindi_product),
                Product("Tamil", "Up to 30% Off", R.drawable.tamil_product)
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
fun MusicalCategoryPage() {
    var selectedCategory = remember { mutableStateOf("All Deals") }
    val bannerImages = listOf(
        painterResource(id = R.drawable.books_banner4),
        painterResource(id = R.drawable.books_banner5),

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
        val musicalCategories = remember {
            listOf(
                Category(0, "Guitar", R.drawable.ic_guitar_books_tab),
                Category(1, "Microphones", R.drawable.ic_microphones_books_tab),
                Category(2, "Keyboards", R.drawable.ic_keyboards_books_tab),
                Category(3, "Cajons", R.drawable.ic_cajons_books_tab),
                Category(4, "Instruments", R.drawable.ic_instruments_books_tab),
                Category(5, "Amplifiers", R.drawable.ic_amplifiers_books_tab),
                Category(6, "Drums", R.drawable.ic_drums_books_tab),
                Category(7, "Violins", R.drawable.ic_violins_books_tab),
                Category(8, "Studio Gear", R.drawable.ic_studio_gear_books_tab),
                Category(9, "DJ Equipment", R.drawable.ic_dj_equipment_books_tab),
                Category(10, "Ukuleles", R.drawable.ic_ukulele_books_tab),
                Category(11, "Accessories", R.drawable.ic_accessories_books_tab)
            )
        }

        var selectedCategory by remember { mutableStateOf<Category?>(null) }
        var selectedMobileCategory by remember { mutableStateOf<Category?>(null) }
        Column {
            CategoryProducts(
                categories = musicalCategories,
                onCategorySelected = { category ->
                    selectedCategory = category
                    // Handle category selection (navigation, filtering, etc.)
                    println("Selected category: ${category.name}")
                },
                modifier = Modifier.fillMaxWidth(),
                initialSelectedCategory = musicalCategories.first(),
                itemWidth = 75, // Custom width
                itemHeight = 65, // Custom height
                horizontalSpacing = 8, // Custom spacing
                verticalSpacing = 8,
                backgroundColor = MaterialTheme.customColors.white // Light blue background
            )

            Spacer(
                modifier = Modifier.height(2.dp)
                    .fillMaxWidth()
                    .background(MaterialTheme.customColors.spacerColor)
            )

            AdsSponsored(
                adImages = listOf(
                    R.drawable.ads_spoonsored_musical_1,
                    R.drawable.ads_spoonsored_musical_2,
                ),
                onAdClick = { index ->
                    println("Clicked ad index: $index")
                }
            )

            val myProducts = listOf(
                Product("Acoustic guitars", "From ₹1,299", R.drawable.acoustic_guitars_product),
                Product("Microphones", "Up to 60% Off", R.drawable.microphones_product),
                Product("Cajons", "Up to 50% Off", R.drawable.cajons_product)
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
fun FourWheelerCategoryPage() {
    var selectedCategory = remember { mutableStateOf("All Deals") }
    val bannerImages = listOf(
        painterResource(id = R.drawable.four_wheeler_banner1),
        painterResource(id = R.drawable.four_wheeler_banner2),
        painterResource(id = R.drawable.four_wheeler_banner3),

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
        val fourwheelersCategories = remember {
            listOf(
                Category(0, "Maruti", R.drawable.ic_maruti_four_wheelers_tab),
                Category(1, "Tata", R.drawable.ic_tata_four_wheelers_tab),
                Category(2, "Kia", R.drawable.ic_kia_four_wheelers_tab),
                Category(3, "Toyota", R.drawable.ic_toyota_four_wheelers_tab),
                Category(4, "Hyundai", R.drawable.ic_hyundai_four_wheelers_tab),
                Category(5, "Mahindra", R.drawable.ic_mahindra_four_wheelers_tab),
                Category(6, "Haima", R.drawable.ic_haima_four_wheelers_tab),
                Category(7, "Haval", R.drawable.ic_haval_four_wheelers_tab),
                Category(8, "Koenigsegg", R.drawable.ic_koenigsegg_four_wheelers_tab),
                Category(9, "Leapmotor", R.drawable.ic_leapmotor_four_wheelers_tab),
//                Category(10, "Xiaomi", R.drawable.ic_xiaomi_four_wheelers_tab),
                Category(10, "Abarth", R.drawable.ic_abarth_four_wheelers_tab),
                Category(11, "BMW", R.drawable.ic_bmw_four_wheelers_tab),
                Category(12, "Austin", R.drawable.ic_austin_four_wheelers_tab),
                Category(13, "Cadillac", R.drawable.ic_cadillac_four_wheelers_tab),
                Category(14, "Caterham", R.drawable.ic_caterham_four_wheelers_tab),
                Category(15, "Chevrolet", R.drawable.ic_chevrolet_four_wheelers_tab),
                Category(16, "Audi", R.drawable.ic_audi_four_wheelers_tab),
                Category(17, "Rolls-Royce", R.drawable.ic_rolls_royce_four_wheelers_tab),
            )
        }

        var selectedCategory by remember { mutableStateOf<Category?>(null) }
        var selectedMobileCategory by remember { mutableStateOf<Category?>(null) }
        Column {
            CategoryProducts(
                categories = fourwheelersCategories,
                onCategorySelected = { category ->
                    selectedCategory = category
                    // Handle category selection (navigation, filtering, etc.)
                    println("Selected category: ${category.name}")
                },
                modifier = Modifier.fillMaxWidth(),
                initialSelectedCategory = fourwheelersCategories.first(),
                itemWidth = 75, // Custom width
                itemHeight = 65, // Custom height
                horizontalSpacing = 8, // Custom spacing
                verticalSpacing = 8,
                backgroundColor = MaterialTheme.customColors.imageBgColor1 // Light blue background
            )

            Spacer(
                modifier = Modifier.height(2.dp)
                    .fillMaxWidth()
                    .background(MaterialTheme.customColors.spacerColor)
            )

            AdsSponsored(
                adImages = listOf(
                    R.drawable.ads_spoonsored_four_wheeler_1,
                    R.drawable.ads_spoonsored_four_wheeler_2,
                ),
                onAdClick = { index ->
                    println("Clicked ad index: $index")
                }
            )

            val myProducts = listOf(
                Product("Maruti FRONX", "₹6.85-11.98 Lakh*", R.drawable.maruti_fronx_product),
                Product("Tata Punch", "₹5.50-9.30 Lakh*", R.drawable.tata_punch_product),
                Product("Tata Nexon", "₹7.32-14.05 Lakh*", R.drawable.tata_nexon_product)
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
fun GemstoneCategoryPage() {
    var selectedCategory = remember { mutableStateOf("All Deals") }
    val bannerImages = listOf(
        painterResource(id = R.drawable.gemstone_banner1),
        painterResource(id = R.drawable.gemstone_banner2),
        painterResource(id = R.drawable.gemstone_banner3),

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
        val fourwheelersCategories = remember {
            listOf(
                Category(0, "Gemstones", R.drawable.ic_gemstones_gemstone_tab),
                Category(1, "Rudraksh", R.drawable.ic_rudraksh_gemstone_tab),
                Category(2, "Bracelets", R.drawable.ic_bracelets_gemstone_tab),
                Category(3, "Mala", R.drawable.ic_mala_gemstone_tab),
                Category(4, "Crystal Products", R.drawable.ic_crystal_products_gemstone_tab),
                Category(5, "Customized Jewellery", R.drawable.ic_customized_jewellery_gemstone_tab),
                Category(6, "Gemstone Rings", R.drawable.ic_gemstone_rings_gemstone_tab),
                Category(7, "New Arrivals", R.drawable.ic_new_arrivals_gemstone_tab),
                Category(8, "Gem Recommendation", R.drawable.ic_gem_recommendation_gemstone_tab),
                Category(9, "View all", R.drawable.ic_view_all_furniture_tab),

//                Category(9, "Remedies", R.drawable.ic_remedies_gemstone_tab),

                 )
        }

        var selectedCategory by remember { mutableStateOf<Category?>(null) }
        var selectedMobileCategory by remember { mutableStateOf<Category?>(null) }
        Column {
            CategoryProducts(
                categories = fourwheelersCategories,
                onCategorySelected = { category ->
                    selectedCategory = category
                    // Handle category selection (navigation, filtering, etc.)
                    println("Selected category: ${category.name}")
                },
                modifier = Modifier.fillMaxWidth(),
                initialSelectedCategory = fourwheelersCategories.first(),
                itemWidth = 75, // Custom width
                itemHeight = 65, // Custom height
                horizontalSpacing = 8, // Custom spacing
                verticalSpacing = 8,
                backgroundColor = MaterialTheme.customColors.imageBgColor1 // Light blue background
            )

            Spacer(
                modifier = Modifier.height(2.dp)
                    .fillMaxWidth()
                    .background(MaterialTheme.customColors.spacerColor)
            )

            AdsSponsored(
                adImages = listOf(
                    R.drawable.ads_spoonsored_gemstone_1,
                    R.drawable.ads_spoonsored_gemstone_2,
                ),
                onAdClick = { index ->
                    println("Clicked ad index: $index")
                }
            )

            val myProducts = listOf(
                Product("Yellow Sapphire/Pukhraj", "Up to 20% Off", R.drawable.yellow_sapphire_pukhraj),
                Product("Blue Sapphire/Neelam", "Up to 20% Off", R.drawable.blue_sapphir_neelam),
                Product("Ruby/Manik", "Up to 20% Off", R.drawable.ruby_manik)
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

        AdsSponsored(
            adImages = listOf(
//                R.drawable.ads_sponsored_1,
                R.drawable.ads_sponsored_2,
//                R.drawable.ads_sponsored_3,
                R.drawable.ads_sponsored_4,
                R.drawable.ads_sponsored_5,
                R.drawable.ads_sponsored_6
            ),
            onAdClick = { index ->
                println("Clicked ad index: $index")
            }
        )

//        AdsSponsored(onAdClick = { println("Ad clicked!") })
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

            Spacer(
                modifier = Modifier.height(2.dp)
                    .fillMaxWidth()
                    .background(MaterialTheme.customColors.spacerColor)
            )

            AdsSponsored(
                adImages = listOf(
                    R.drawable.ads_sponsored_mobile_1,
                    R.drawable.ads_sponsored_mobile_2,
//                    R.drawable.ads_sponsored_mobile_3
                ),
                onAdClick = { index ->
                    println("Clicked ad index: $index")
                }
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
                    is CategoryPage.FourWheeler -> 13
                    is CategoryPage.Gemstone -> 14
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