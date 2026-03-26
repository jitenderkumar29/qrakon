package com.example.qrakon.components.GroceryHome

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.qrakon.R
import com.example.qrakon.components.categorytabs.CarouselFashionOne
import com.example.qrakon.components.categorytabs.Category
import com.example.qrakon.components.categorytabs.CategoryProducts
import com.example.qrakon.components.homescreen.BannerBackgroundManager
import com.example.qrakon.components.homescreen.BannerFood
import com.example.qrakon.components.homescreen.BannerItem
import com.example.qrakon.components.homescreen.BannerPadding
import com.example.qrakon.components.homescreen.BannerPortrait
import com.example.qrakon.components.homescreen.CategoryItem
import com.example.qrakon.components.homescreen.CategoryItemBgImg
import com.example.qrakon.components.homescreen.CategoryListBgImg
import com.example.qrakon.components.homescreen.CategoryListSimple
import com.example.qrakon.components.homescreen.DotPosition
import com.example.qrakon.components.homescreen.DynamicSpacing
import com.example.qrakon.components.homescreen.OverlayPosition
import com.example.qrakon.ui.theme.customColors

// Sealed class for different grocery category pages - UPDATED with new categories
sealed class GroceryCategoryPage(val title: String, val iconRes: Int) {
    // New categories from the list
    object All : GroceryCategoryPage("All", R.drawable.all)
    object Fresh : GroceryCategoryPage("Fresh", R.drawable.fresh_1)
    object Festival : GroceryCategoryPage("Festival", R.drawable.festival)
    object Summer : GroceryCategoryPage("Summer", R.drawable.summer)
    object Ramadan : GroceryCategoryPage("Ramadan", R.drawable.ramadan)
    object Organic : GroceryCategoryPage("Organic", R.drawable.organic)
    object Health : GroceryCategoryPage("Health", R.drawable.health_1)
    object Gifting : GroceryCategoryPage("Gifting", R.drawable.gifting)
    object Toys : GroceryCategoryPage("Toys", R.drawable.toys)
    object Kids : GroceryCategoryPage("Kids", R.drawable.kids)
    object Beauty : GroceryCategoryPage("Beauty", R.drawable.beauty_1)
    object Pets : GroceryCategoryPage("Pets", R.drawable.pets)
    object Decor : GroceryCategoryPage("Decor", R.drawable.decor)
    object Imported : GroceryCategoryPage("Imported", R.drawable.imported)
}

// Data class for grocery items
data class GroceryItem(
    val id: Int,
    val name: String,
    val description: String? = null,
    val imageRes: Int,
    val price: String,
    val weight: String,
    val isVeg: Boolean = true,
    val category: String = "All"
)

@Composable
fun GroceryTabs(
    navController: NavHostController? = null,
    selectedTabIndex: Int,
    onCategorySelected: (GroceryCategoryPage) -> Unit = {},
    onTabIndexChanged: (Int) -> Unit = {}
) {
    // Get current value
    var currentSelectedIndex by rememberSaveable {
        mutableIntStateOf(
            navController?.currentBackStackEntry?.savedStateHandle?.get<Int>("currentSelectedIndex")
                ?: selectedTabIndex
        )
    }

    // Save changes back to savedStateHandle
    LaunchedEffect(currentSelectedIndex) {
        navController?.currentBackStackEntry?.savedStateHandle?.set(
            "currentSelectedIndex",
            currentSelectedIndex
        )
    }

    Log.d("GroceryTabs", "currentSelectedIndex in GroceryTabs: $currentSelectedIndex")

    val savedGroceryTabIndex = remember(navController?.currentBackStackEntry) {
        navController?.currentBackStackEntry?.savedStateHandle?.get<Int>("groceryTabIndex")
            ?: 0
    }

    // Update when parent state changes
    LaunchedEffect(selectedTabIndex) {
        currentSelectedIndex = selectedTabIndex
    }

    // All grocery category pages from the new list (all tabs displayed)
    val allCategoryPages = listOf(
        GroceryCategoryPage.All,
        GroceryCategoryPage.Fresh,
        GroceryCategoryPage.Festival,
        GroceryCategoryPage.Summer,
        GroceryCategoryPage.Ramadan,
        GroceryCategoryPage.Organic,
        GroceryCategoryPage.Health,
        GroceryCategoryPage.Gifting,
        GroceryCategoryPage.Toys,
        GroceryCategoryPage.Kids,
        GroceryCategoryPage.Beauty,
        GroceryCategoryPage.Pets,
        GroceryCategoryPage.Decor,
        GroceryCategoryPage.Imported
    )

    Column(
        modifier = Modifier.run {
            fillMaxWidth()
                .background(Color(0xFFFFECC9))
        }
    ) {
        ScrollableTabRow(
            selectedTabIndex = currentSelectedIndex,
            containerColor = Color.Transparent,
            contentColor = MaterialTheme.customColors.black,
            edgePadding = 0.dp,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[currentSelectedIndex]),
                    height = 5.dp,
                    color = MaterialTheme.customColors.footer
                )
            }
        ) {
            allCategoryPages.forEachIndexed { index, categoryPage ->
                val isSelected = currentSelectedIndex == index

                Tab(
                    selected = isSelected,
                    onClick = {
                        currentSelectedIndex = index
                        onTabIndexChanged(index)
                        onCategorySelected(categoryPage)
                    },
                    modifier = Modifier
                        .padding(horizontal = 0.dp)
                        .background(Color.Transparent)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(vertical = 2.dp, horizontal = 0.dp)
                    ) {
                        // Use a custom ImageWithFallback composable
                        ImageWithFallback(
                            iconRes = categoryPage.iconRes,
                            title = categoryPage.title,
                            isSelected = isSelected,
                            modifier = Modifier
                                .width(45.dp)
                                .height(45.dp)
                        )

                        Text(
                            text = categoryPage.title,
                            fontSize = 12.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                            color = if (isSelected) {
                                MaterialTheme.customColors.footer
                            } else {
                                MaterialTheme.customColors.black
                            },
                            maxLines = 2,
                            textAlign = TextAlign.Center,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.padding(top = 2.dp)
                        )
                    }
                }
            }
        }

        // Show content for each tab
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFFEDF6FF),
                            Color(0xFFFDFEFF)
                        )
                    )
                )
        ) {
            // Get the actual category based on currentSelectedIndex
            val actualCategory = allCategoryPages.getOrNull(currentSelectedIndex)

            // Show content based on the actual category
            when (actualCategory) {
                GroceryCategoryPage.All -> AllCategoryPage()
                GroceryCategoryPage.Fresh -> FreshCategoryPage()
                GroceryCategoryPage.Festival -> FestivalCategoryPage()
                GroceryCategoryPage.Summer -> SummerCategoryPage()
                GroceryCategoryPage.Ramadan -> RamadanCategoryPage()
                GroceryCategoryPage.Organic -> OrganicCategoryPage()
                GroceryCategoryPage.Health -> HealthCategoryPage()
                GroceryCategoryPage.Gifting -> GiftingCategoryPage()
                GroceryCategoryPage.Toys -> ToysCategoryPage()
                GroceryCategoryPage.Kids -> KidsCategoryPage()
                GroceryCategoryPage.Beauty -> BeautyCategoryPage()
                GroceryCategoryPage.Pets -> PetsCategoryPage()
                GroceryCategoryPage.Decor -> DecorCategoryPage()
                GroceryCategoryPage.Imported -> ImportedCategoryPage()
                null -> {
                    // Show empty state for invalid index
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Select a category",
                            color = MaterialTheme.customColors.gray,
                            fontSize = 16.sp
                        )
                    }
                }
            }
        }
    }
}

// New composable to handle image loading with fallback - NO TRY-CATCH AROUND COMPOSABLES
@Composable
fun ImageWithFallback(
    iconRes: Int,
    title: String,
    isSelected: Boolean,
    modifier: Modifier = Modifier
) {
    // Use a state to track if we should show fallback
    var showFallback by remember(iconRes) { mutableStateOf(false) }
    val context = LocalContext.current

    // Check if resource exists - this is not a composable function call
    val resourceExists = try {
        context.resources.getResourceEntryName(iconRes)
        true
    } catch (e: Exception) {
        false
    }

    if (resourceExists && !showFallback) {
        // Image exists, show it
        Image(
            painter = painterResource(id = iconRes),
            contentDescription = title,
            modifier = modifier,
            contentScale = ContentScale.FillBounds
        )
    } else {
        // Image doesn't exist or failed, show fallback
        Box(
            modifier = Modifier
                .size(60.dp)
                .clip(CircleShape)
                .background(
                    if (isSelected)
                        MaterialTheme.customColors.footer.copy(alpha = 0.2f)
                    else
                        Color.LightGray.copy(alpha = 0.1f)
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = title.take(1).uppercase(),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = if (isSelected)
                    MaterialTheme.customColors.footer
                else
                    MaterialTheme.customColors.gray
            )
        }
    }
}


@SuppressLint("SuspiciousIndentation")
@Composable
fun AllCategoryPage() {
    val bannerBackgroundColor by remember { BannerBackgroundManager.backgroundColor }
    val allGroceryImages = listOf(
        BannerItem( imageRes = R.drawable.all_grocery_banner_10, backgroundColor = Color(0xFFFEFEFE) ),
        BannerItem( imageRes = R.drawable.all_grocery_banner_9, backgroundColor = Color(0xFFE4E5E9) ),
        BannerItem( imageRes = R.drawable.all_grocery_banner_1, backgroundColor = Color(0xFF533C2F)),
        BannerItem( imageRes = R.drawable.all_grocery_banner_3, backgroundColor = Color(0xFFEEEEEC) ),
        BannerItem( imageRes = R.drawable.fresh_grocery_banner5, backgroundColor = Color(0xFFD7D3C3)),
        BannerItem( imageRes = R.drawable.all_grocery_banner_4, backgroundColor = Color(0xFFEEEAE1) ),
        BannerItem( imageRes = R.drawable.all_grocery_banner_2, backgroundColor = Color(0xFFC7D3C2) ),
        BannerItem( imageRes = R.drawable.fresh_grocery_banner4, backgroundColor = Color(0xFFDEFBE9)),
        BannerItem( imageRes = R.drawable.fresh_grocery_banner1, backgroundColor = Color(0xFFC8C385)),
        BannerItem( imageRes = R.drawable.all_grocery_banner_5, backgroundColor = Color(0xFFFAFAFA) ),
        BannerItem( imageRes = R.drawable.all_grocery_banner_6, backgroundColor = Color(0xFFEBEBE9) ),
        BannerItem( imageRes = R.drawable.all_grocery_banner_7, backgroundColor = Color(0xFFFFEBB1) ),
        BannerItem( imageRes = R.drawable.all_grocery_banner_8, backgroundColor = Color(0xFFE0E0E0) ),
        )
    Spacer(
        modifier = Modifier.height(10.dp)
            .fillMaxWidth()
            .background(bannerBackgroundColor)
//                    .background(MaterialTheme.customColors.background)
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        bannerBackgroundColor,  // Directly use the Color object, don't wrap it
                        MaterialTheme.customColors.white
                    )
                )
            )
//                    .background(bannerBackgroundColor) // Use the global background color
    ) {
        BannerPortrait(
            items = allGroceryImages,
            onImageClick = { imageRes ->
                // Handle image click
                println("Clicked on image: $imageRes")
            },
            height = 200.dp,
            autoScrollDelay = 3000
        )
    }
    Spacer(
        modifier = Modifier.height(10.dp)
            .fillMaxWidth()
            .background(MaterialTheme.customColors.white)
    )

    // Housefull Sale
    val housefullSaleCategoriesSimple = listOf(
        CategoryItem(0, "", R.drawable.ic_housefull_sale_1, "View products"),
        CategoryItem(1, "", R.drawable.ic_housefull_sale_2, "View products"),
        CategoryItem(2, "", R.drawable.ic_housefull_sale_3, "View products"),
        CategoryItem(4, "", R.drawable.ic_housefull_sale_5, "View products"),
        CategoryItem(3, "", R.drawable.ic_housefull_sale_4, "View products"),
    )
//        Spacer(modifier = Modifier.height(10.dp))
    Image(
        painter = painterResource(R.drawable.ic_housefull_sale_header),
        contentDescription = "Banner",
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(
                min = 100.dp,
                max = 300.dp
            ), // Height between min and max, // 30% of screen height, // Sets height based on width and aspect ratio
        contentScale = ContentScale.FillBounds
    )
    CategoryListSimple(
        items = housefullSaleCategoriesSimple,
        onItemClick = { item -> println("Selected: ${item.name}") },
        itemWidth = 110.dp,
        itemHeight = 110.dp,
        horizontalSpacing = 12.dp,
//                        verticalPadding = 8.dp,
        horizontalPadding = 12.dp,
        backgroundColor = Color(0xFFFFC653)
    )
    Image(
        painter = painterResource(R.drawable.ic_housefull_sale_footer),
        contentDescription = "Banner",
        modifier = Modifier
            .fillMaxWidth()
            .height(20.dp), // Height between min and max, // 30% of screen height, // Sets height based on width and aspect ratio
        contentScale = ContentScale.FillBounds
    )

    // Feature this week
    val featureThisWeekCategoriesGrocery = listOf(
        CategoryItem(0, "", R.drawable.ic_grocery_newly_launched, "View products"),
        CategoryItem(1, "", R.drawable.ic_grocery_oral_health, "View products"),
        CategoryItem(2, "", R.drawable.ic_grocery_ugadi, "View products"),
        CategoryItem(3, "", R.drawable.ic_grocery_exam, "View products"),
        CategoryItem(4, "", R.drawable.ic_grocery_decathlon, "View products"),
        CategoryItem(5, "", R.drawable.ic_grocery_date_delight, "View products"),
        CategoryItem(6, "", R.drawable.ic_grocery_glow, "View products"),
        CategoryItem(7, "", R.drawable.ic_grocery_metro, "View products"),
        CategoryItem(8, "", R.drawable.ic_grocery_lifestyle, "View products"),
        CategoryItem(9, "", R.drawable.ic_grocery_summer, "View products"),
    )
//        Spacer(modifier = Modifier.height(10.dp))
    Spacer(modifier = Modifier.height(10.dp))
    Text(
        text = "Feature This Week",
        style = MaterialTheme.typography.bodySmall.copy(
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.customColors.black
        ),
        maxLines = 1,
        modifier = Modifier.fillMaxWidth().padding(start = 12.dp)
    )
    CategoryListSimple(
        items = featureThisWeekCategoriesGrocery,
        onItemClick = { item -> println("Selected: ${item.name}") },
        itemWidth = 100.dp,
        itemHeight = 120.dp,
        horizontalSpacing = 12.dp,
//                        verticalPadding = 8.dp,
        horizontalPadding = 12.dp,
        backgroundColor = Color(0xFFFFFFFF)
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(12.dp)
    ) {

    }
}
@Composable
fun FestivalCategoryPage() {
        // gudi Sale
//                    Spacer(modifier = Modifier.height(20.dp))
        val gudiCategoriesSimple = listOf(
            CategoryItem(0, "", R.drawable.ic_gudi_sale_1, "View products"),
            CategoryItem(1, "", R.drawable.ic_gudi_sale_2, "View products"),
            CategoryItem(2, "", R.drawable.ic_gudi_sale_3, "View products"),
            CategoryItem(3, "", R.drawable.ic_gudi_sale_4, "View products"),
            CategoryItem(4, "", R.drawable.ic_gudi_sale_5, "View products"),
        )
//        Spacer(modifier = Modifier.height(10.dp))
        Image(
            painter = painterResource(R.drawable.ic_gudi),
            contentDescription = "Banner",
            modifier = Modifier
                .fillMaxWidth()
                .height(
                    200.dp
                ),
            contentScale = ContentScale.FillBounds
        )
        CategoryListSimple(
            items = gudiCategoriesSimple,
            onItemClick = { item -> println("Selected: ${item.name}") },
            itemWidth = 110.dp,
            itemHeight = 140.dp,
            horizontalSpacing = 12.dp,
//                        verticalPadding = 8.dp,
            horizontalPadding = 12.dp,
            backgroundColor = Color(0xFFE2FCE3)
        )

        val items = listOf(
            CategoryItemBgImg(
                id = 1,
                name = "",
                imageRes = R.drawable.ic_fest_1
            ),
            CategoryItemBgImg(
                id = 2,
                name = "",
                imageRes = R.drawable.ic_fest_2
            ),
            CategoryItemBgImg(
                id = 3,
                name = "",
                imageRes = R.drawable.ic_fest_3
            ),
            CategoryItemBgImg(
                id = 4,
                name = "",
                imageRes = R.drawable.ic_fest_4
            ),
            CategoryItemBgImg(
                id = 5,
                name = "",
                imageRes = R.drawable.ic_fest_5
            )
        )
        Spacer(modifier = Modifier.height(20.dp))
        CategoryListBgImg(
            backgroundImageRes = R.drawable.ic_fest_bg,
            items = items,
            onItemClick = { },
            backgroundImageHeight = 300.dp,
            listItemWidth = 120.dp,
            listItemHeight = 230.dp,
            overlayItemSize = 90.dp,
            overlayTextSize = 12.sp,
            backgroundOverlay = Color.Black.copy(alpha = 0.3f),
            overlayBottomPosition = OverlayPosition.pixels(250.dp),
            title = null,
            showHorizontalList = false,
            overlayItemsSpacing = DynamicSpacing.Fixed(15.dp), // Fixed 28dp between overlay items
            listItemsSpacing = DynamicSpacing.Fixed(12.dp) // Fixed 12dp between list items
        )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(12.dp)
    ) {
    }
}
// Sample content for each category page
@Composable
fun FreshCategoryPage() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(0.dp)
    ) {
        val freshEssentialsCategories = listOf(
            GroceryItemCategory(0, "Fresh Fruits", R.drawable.fresh_fruits),
            GroceryItemCategory(1, "Fresh Vegetables", R.drawable.fresh_veggies),
            GroceryItemCategory(2, "Cuts & Exotics", R.drawable.cuts_exotics),
            GroceryItemCategory(3, "Herbs & Spice Mix", R.drawable.herbs_spice_mix),
            GroceryItemCategory(4, "Dairy & Plant-Based", R.drawable.dairy_plant_based),
            GroceryItemCategory(5, "Meat, Eggs & Fish", R.drawable.meat_eggs_fish),
            GroceryItemCategory(6, "Healthy & Organic", R.drawable.healthy_organic),
            GroceryItemCategory(7, "Batters & Bread", R.drawable.batters_bread)
        )
        // In your screen composable
        Spacer(modifier = Modifier.height(10.dp))
        ListItemsGrid(
            browseText = "Snacks & Drinks",
            items = freshEssentialsCategories,
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
        BannerFood(
            images = listOf(
                painterResource(id = R.drawable.fresh_grocery_banner1),
                painterResource(id = R.drawable.fresh_grocery_banner2),
                painterResource(id = R.drawable.fresh_grocery_banner3),
                painterResource(id = R.drawable.fresh_grocery_banner4),
                painterResource(id = R.drawable.fresh_grocery_banner5),
                painterResource(id = R.drawable.fresh_grocery_banner6),
                painterResource(id = R.drawable.fresh_grocery_banner7),
                painterResource(id = R.drawable.fresh_grocery_banner8),
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
    }
}

@Composable
fun SummerCategoryPage() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(0.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.ic_summer_ready),
            contentDescription = "Banner",
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp), // Height between min and max, // 30% of screen height, // Sets height based on width and aspect ratio
            contentScale = ContentScale.FillBounds
        )

        // Carousel Double
        val summerReadyCategories = remember {
            listOf(
                Category(0, "", R.drawable.ic_icecream),
                Category(1, "", R.drawable.ic_cold_beverage),
                Category(2, "", R.drawable.ic_buttermilk),
                Category(3, "", R.drawable.ic_melon),
                Category(4, "", R.drawable.ic_glucose),
                Category(5, "", R.drawable.ic_sunscreen),
                Category(6, "", R.drawable.ic_seasonal_veggies),
                Category(7, "", R.drawable.ic_mixer),
                Category(8, "", R.drawable.ic_fan),
                Category(9, "", R.drawable.ic_kitchen_household)
            )
        }
        Spacer(modifier = Modifier.height(10.dp).fillMaxWidth())
        var selectedCategory by remember { mutableStateOf<Category?>(null) }
        var selectedSummerReadyCategory by remember { mutableStateOf<Category?>(null) }
        Column {
            CategoryProducts(
                categories = summerReadyCategories,
                onCategorySelected = { category ->
                    selectedCategory = category
                    // Handle category selection (navigation, filtering, etc.)
                    println("Selected category: ${category.name}")
                },
                modifier = Modifier.fillMaxWidth(),
                initialSelectedCategory = summerReadyCategories.first(),
                itemWidth = 85, // Custom width
                itemHeight = 95, // Custom height
                horizontalSpacing = 2, // Custom spacing
                verticalSpacing = 12,
                backgroundColor = MaterialTheme.customColors.white // Light blue background
            )
        }
        Spacer(modifier = Modifier.height(10.dp).fillMaxWidth())
        val coolSipsCategories = listOf(
            GroceryItemCategory(id = 1, title = "", iconRes = R.drawable.ic_category_aerated),
            GroceryItemCategory(id = 2, title = "", iconRes = R.drawable.ic_category_juices),
            GroceryItemCategory(id = 3, title = "", iconRes = R.drawable.ic_category_dairy),
            GroceryItemCategory(id = 4, title = "", iconRes = R.drawable.ic_category_sharbat),
            GroceryItemCategory(id = 5, title = "", iconRes = R.drawable.ic_category_sports_drink),
            GroceryItemCategory(id = 6, title = "", iconRes = R.drawable.ic_category_mixers),
        )
        // In your screen composable
//        Spacer(modifier = Modifier.height(10.dp))
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(12.dp)
        ) {
            ListItemsGrid(
                browseText = "Shop by Category",
                items = coolSipsCategories,
                onItemClick = { item ->
                    println("Clicked: ${item.title}")
                },
                columns = 3,
                horizontalSpacing = 5.dp,  // Increased spacing for better readability
                verticalSpacing = 0.dp,    // Increased vertical spacing
                itemHeight = 130.dp,        // Taller items to accommodate larger images
                imageSize = 115.dp,          // Larger image size
                containerSize = 120.dp,     // Larger container for images
                backgroundColor = MaterialTheme.customColors.background,
                onBackClick = {}
            )
        }
//        Spacer(modifier = Modifier.height(10.dp).fillMaxWidth())

    }
}

@Composable
fun RamadanCategoryPage() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(12.dp)
    ) {
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Ramadan Special",
            style = MaterialTheme.typography.bodySmall.copy(
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.customColors.black
            ),
            maxLines = 1,
            modifier = Modifier.fillMaxWidth().padding(start = 12.dp)
        )
        Text(
            text = "Dates, sweets & Ramadan essentials",
            fontSize = 16.sp,
            color = MaterialTheme.customColors.gray,
            modifier = Modifier.padding(start = 12.dp, top = 8.dp)
        )
    }
}

@Composable
fun OrganicCategoryPage() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(0.dp)
    ) {
//                Spacer(modifier = Modifier.height(10.dp))
        BannerFood(
            images = listOf(
                painterResource(id = R.drawable.organic_grocery_banner1),
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
            dotPosition = DotPosition.NONE,
            overlayGradient = true, // Adds gradient for better visibility
            selectedDotColor = Color.White,
            padding = BannerPadding.all(8.dp),
            modifier = Modifier
                .fillMaxWidth()
        )

            val shopByCategory = listOf(
                GroceryItemCategory(id = 1, title = "Dals & pulses", iconRes = R.drawable.ic_category_dals),
                GroceryItemCategory(id = 2, title = "Flours", iconRes = R.drawable.ic_category_flours),
                GroceryItemCategory(id = 3, title = "Rice & rice products", iconRes = R.drawable.ic_category_rice),
                GroceryItemCategory(id = 4, title = "Dry fruits", iconRes = R.drawable.ic_category_dryfruits),
                GroceryItemCategory(id = 5, title = "Ghee & oils", iconRes = R.drawable.ic_category_ghee),
                GroceryItemCategory(id = 6, title = "Fruits & veggies", iconRes = R.drawable.ic_category_fruits),
                GroceryItemCategory(id = 7, title = "Masalas & spices", iconRes = R.drawable.ic_category_masala),
                GroceryItemCategory(id = 8, title = "Millet flours", iconRes = R.drawable.ic_category_millet),
                GroceryItemCategory(id = 9, title = "Cereals & breakfast", iconRes = R.drawable.ic_cereals)
            )
            // In your screen composable
//            Spacer(modifier = Modifier.height(10.dp))
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(12.dp)
        ) {
            ListItemsGrid(
                browseText = "Shop by Category",
                items = shopByCategory,
                onItemClick = { item ->
                    println("Clicked: ${item.title}")
                },
                columns = 3,
                horizontalSpacing = 5.dp,  // Increased spacing for better readability
                verticalSpacing = 5.dp,    // Increased vertical spacing
                itemHeight = 150.dp,        // Taller items to accommodate larger images
                imageSize = 115.dp,          // Larger image size
                containerSize = 120.dp,     // Larger container for images
                backgroundColor = MaterialTheme.customColors.background,
                onBackClick = {}
            )
        }
    }
}

@Composable
fun HealthCategoryPage(  // Accept both parameters
                        modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(0.dp)
    ) {
        // Shop By Health Filters Categories
        val shopByFilters = listOf(
            Category(0, "Shop by goal", R.drawable.ic_shop_by_goal),
            Category(1, "Shop by diet", R.drawable.ic_shop_by_diet),
            Category(2, "Shop by lifestyle", R.drawable.ic_shop_by_lifestyle),
            Category(3, "Shop by allergen", R.drawable.ic_shop_by_allergen),
            Category(4, "Shop by health", R.drawable.ic_shop_by_health),
        )
        CarouselFashionOne(
            categories = shopByFilters,
            onTabSelected = { categoryName, categoryId ->
                println("hello")
                // If you need to call the original callback that only takes name:
                // onTabSelected(categoryName)
            },
            modifier = Modifier,
            backgroundColor = MaterialTheme.customColors.white,
            itemWidth = 85,
            itemHeight = 85,
            horizontalSpacing = 1
        )

        val lifestyleOptions = listOf(
            GroceryItemCategory(id = 1, title = "Athlete's choice", iconRes = R.drawable.ic_lifestyle_athlete),
            GroceryItemCategory(id = 2, title = "Weight management", iconRes = R.drawable.ic_lifestyle_weight),
            GroceryItemCategory(id = 3, title = "Ayurveda", iconRes = R.drawable.ic_lifestyle_ayurveda),
            GroceryItemCategory(id = 4, title = "Vegan & plant-based", iconRes = R.drawable.ic_lifestyle_vegan),
            GroceryItemCategory(id = 5, title = "Fresh & organic", iconRes = R.drawable.ic_lifestyle_organic),
        )
        // In your screen composable
//            Spacer(modifier = Modifier.height(10.dp))
        Image(
            painter = painterResource(R.drawable.ic_life_style),
            contentDescription = "Banner",
            modifier = Modifier
                .fillMaxWidth()
                .height(75.dp), // Height between min and max, // 30% of screen height, // Sets height based on width and aspect ratio
            contentScale = ContentScale.FillBounds
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(vertical = 5.dp, horizontal = 12.dp )
        ) {
            ListItemsGrid(
                browseText = "",
                showBrowseText = false,
                items = lifestyleOptions,
                onItemClick = { item ->
                    println("Clicked: ${item.title}")
                },
                columns = 3,
                horizontalSpacing = 5.dp,  // Increased spacing for better readability
                verticalSpacing = 5.dp,    // Increased vertical spacing
                itemHeight = 150.dp,        // Taller items to accommodate larger images
                imageSize = 115.dp,          // Larger image size
                containerSize = 120.dp,     // Larger container for images
                backgroundColor = MaterialTheme.customColors.background,
                onBackClick = {}
            )
        }

        val dietPreferenceOptions = listOf(
            GroceryItemCategory(id = 1, title = "Diabetic-friendly", iconRes = R.drawable.ic_diet_diabetic),
            GroceryItemCategory(id = 2, title = "High-protein", iconRes = R.drawable.ic_diet_high_protein),
            GroceryItemCategory(id = 3, title = "Keto-friendly", iconRes = R.drawable.ic_diet_keto),
        )
        // In your screen composable
//            Spacer(modifier = Modifier.height(10.dp))
        Image(
            painter = painterResource(R.drawable.ic_diet_preference),
            contentDescription = "Banner",
            modifier = Modifier
                .fillMaxWidth()
                .height(75.dp), // Height between min and max, // 30% of screen height, // Sets height based on width and aspect ratio
            contentScale = ContentScale.FillBounds
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(vertical = 5.dp, horizontal = 12.dp)
        ) {
            ListItemsGrid(
                browseText = "",
                showBrowseText = false,
                items = dietPreferenceOptions,
                onItemClick = { item ->
                    println("Clicked: ${item.title}")
                },
                columns = 3,
                horizontalSpacing = 5.dp,  // Increased spacing for better readability
                verticalSpacing = 5.dp,    // Increased vertical spacing
                itemHeight = 150.dp,        // Taller items to accommodate larger images
                imageSize = 115.dp,          // Larger image size
                containerSize = 120.dp,     // Larger container for images
                backgroundColor = MaterialTheme.customColors.background,
                onBackClick = {}
            )
        }

        // Feature this week
        val shopByHealthGoals = listOf(
            CategoryItem(0, "", R.drawable.ic_health_goal_heart, "View products"),
            CategoryItem(1, "", R.drawable.ic_health_goal_gut, "View products"),
            CategoryItem(2, "", R.drawable.ic_health_goal_muscle, "View products"),
            CategoryItem(3, "", R.drawable.ic_health_goal_metabolic, "View products"),
            CategoryItem(4, "", R.drawable.ic_health_goal_weight, "View products"),
            CategoryItem(5, "", R.drawable.ic_health_goal_immunity, "View products"),
        )
//        Spacer(modifier = Modifier.height(10.dp))
//        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Shop by Health Goal",
            style = MaterialTheme.typography.bodySmall.copy(
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.customColors.black
            ),
            maxLines = 1,
            modifier = Modifier.fillMaxWidth().padding(start = 12.dp)
        )
        CategoryListSimple(
            items = shopByHealthGoals,
            onItemClick = { item -> println("Selected: ${item.name}") },
            itemWidth = 100.dp,
            itemHeight = 120.dp,
            horizontalSpacing = 12.dp,
//                        verticalPadding = 8.dp,
            horizontalPadding = 12.dp,
            backgroundColor = Color(0xFFFFFFFF)
        )
    }
}

@Composable
fun GiftingCategoryPage() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(0.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.ic_gifting),
            contentDescription = "Banner",
            modifier = Modifier
                .fillMaxWidth()
                .height(65.dp), // Height between min and max, // 30% of screen height, // Sets height based on width and aspect ratio
            contentScale = ContentScale.FillBounds
        )
        val giftCategories = listOf(
            GroceryItemCategory(id = 1, title = "", iconRes = R.drawable.ic_gift_chocolates),
            GroceryItemCategory(id = 2, title = "", iconRes = R.drawable.ic_gift_dry_fruits),
            GroceryItemCategory(id = 3, title = "", iconRes = R.drawable.ic_gift_indian_sweets),
            GroceryItemCategory(id = 4, title = "", iconRes = R.drawable.ic_gift_cups_mugs),
            GroceryItemCategory(id = 5, title = "", iconRes = R.drawable.ic_gift_toys_games),
            GroceryItemCategory(id = 6, title = "", iconRes = R.drawable.ic_gift_beauty_fashion),
            GroceryItemCategory(id = 7, title = "", iconRes = R.drawable.ic_gift_books),
            GroceryItemCategory(id = 8, title = "", iconRes = R.drawable.ic_gift_stationery),
            GroceryItemCategory(id = 9, title = "", iconRes = R.drawable.ic_gift_home_living),
            GroceryItemCategory(id = 10, title = "", iconRes = R.drawable.ic_gift_gadgets),
            GroceryItemCategory(id = 11, title = "", iconRes = R.drawable.ic_gift_home_appliances),
        )
        // In your screen composable
//        Spacer(modifier = Modifier.height(10.dp))
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(vertical = 0.dp, horizontal = 12.dp )
        ) {
            ListItemsGrid(
                browseText = "",
                showBrowseText = false,
                items = giftCategories,
                onItemClick = { item ->
                    println("Clicked: ${item.title}")
                },
                columns = 4,
                horizontalSpacing = 8.dp,
                verticalSpacing = 1.dp,      // Increased from 1.dp for better spacing
                itemHeight = 100.dp,          // Increased from 120.dp for larger items
                imageSize = 90.dp,            // Increased from 100.dp? Actually reduced to fit 4 columns
                containerSize = 90.dp,        // Added container size for image
                backgroundColor = MaterialTheme.customColors.background,
                onBackClick = {}
            )
        }

        Image(
            painter = painterResource(R.drawable.ic_gifts_for_her),
            contentDescription = "Banner",
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp), // Height between min and max, // 30% of screen height, // Sets height based on width and aspect ratio
            contentScale = ContentScale.FillBounds
        )
        val giftsForHerCategories = listOf(
            GroceryItemCategory(id = 1, title = "", iconRes = R.drawable.ic_gift_perfumes),
            GroceryItemCategory(id = 2, title = "", iconRes = R.drawable.ic_gift_sets)
        )// In your screen composable
//        Spacer(modifier = Modifier.height(10.dp))
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(vertical = 2.dp, horizontal = 12.dp )
        ) {
            ListItemsGrid(
                browseText = "",
                showBrowseText = false,
                items = giftsForHerCategories,
                onItemClick = { item ->
                    println("Clicked: ${item.title}")
                },
                columns = 2,
                horizontalSpacing = 8.dp,
                verticalSpacing = 2.dp,  // Increased from 1.dp for better spacing
                itemHeight = 190.dp,      // Increased height for better proportion
                imageSize = 180.dp,       // Larger image size (was 190.dp)
                containerSize = 180.dp,   // Container for image (slightly larger than image)
                backgroundColor = MaterialTheme.customColors.background,
                onBackClick = {}
            )

            Image(
                painter = painterResource(R.drawable.ic_gifts_for_him),
                contentDescription = "Banner",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp), // Height between min and max, // 30% of screen height, // Sets height based on width and aspect ratio
                contentScale = ContentScale.FillBounds
            )
            val giftsForHimCategories = listOf(
                GroceryItemCategory(id = 1, title = "", iconRes = R.drawable.ic_category_audio),
                GroceryItemCategory(id = 2, title = "", iconRes = R.drawable.ic_category_speakers),
                GroceryItemCategory(id = 3, title = "", iconRes = R.drawable.ic_category_grooming)
            )
            // In your screen composable
//        Spacer(modifier = Modifier.height(10.dp))
            ListItemsGrid(
                browseText = "",
                showBrowseText = false,
                items = giftsForHimCategories,
                onItemClick = { item ->
                    println("Clicked: ${item.title}")
                },
                columns = 3,
                horizontalSpacing = 5.dp,  // Increased spacing for better readability
                verticalSpacing = 0.dp,    // Increased vertical spacing
                itemHeight = 120.dp,        // Taller items to accommodate larger images
                imageSize = 115.dp,          // Larger image size
                containerSize = 120.dp,     // Larger container for images
                backgroundColor = MaterialTheme.customColors.background,
                onBackClick = {}
            )

            val toysGamesCraftsCategories = listOf(
                CategoryItem(0, "", R.drawable.ic_toys_board_games, "View products"),
                CategoryItem(1, "", R.drawable.ic_toys_cars, "View products"),
                CategoryItem(2, "", R.drawable.ic_toys_soft_toys, "View products"),
                CategoryItem(3, "", R.drawable.ic_toys_arts_crafts, "View products")
            )
//        Spacer(modifier = Modifier.height(10.dp))
            Image(
                painter = painterResource(R.drawable.ic_toys_games_crafts),
                contentDescription = "Banner",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp), // Height between min and max, // 30% of screen height, // Sets height based on width and aspect ratio
                contentScale = ContentScale.FillBounds
            )
            CategoryListSimple(
                items = toysGamesCraftsCategories,
                onItemClick = { item -> println("Selected: ${item.name}") },
                itemWidth = 100.dp,
                itemHeight = 120.dp,
                horizontalSpacing = 12.dp,
//                        verticalPadding = 8.dp,
                horizontalPadding = 12.dp,
                backgroundColor = Color(0xFFFFFFFF)
            )
        }
    }
}

@Composable
fun ToysCategoryPage() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(0.dp)
    ) {
        val shopByCategoryToys = listOf(
            GroceryItemCategory(id = 1, title = "Baby & toddler toys", iconRes = R.drawable.ic_toys_baby_toddler),
            GroceryItemCategory(id = 2, title = "Soft toys", iconRes = R.drawable.ic_toys_soft_toys_1),
            GroceryItemCategory(id = 3, title = "Dolls & playsets", iconRes = R.drawable.ic_toys_dolls_playsets),
            GroceryItemCategory(id = 4, title = "Vehicles & action toys", iconRes = R.drawable.ic_toys_vehicles_action),
            GroceryItemCategory(id = 5, title = "Board games", iconRes = R.drawable.ic_toys_board_games_1),
            GroceryItemCategory(id = 6, title = "Blocks & puzzles", iconRes = R.drawable.ic_toys_blocks_puzzles),
            GroceryItemCategory(id = 7, title = "Educational toys", iconRes = R.drawable.ic_toys_educational),
            GroceryItemCategory(id = 8, title = "Hobby & craft sets", iconRes = R.drawable.ic_toys_hobby_craft),
            GroceryItemCategory(id = 9, title = "Activity toys", iconRes = R.drawable.ic_toys_activity),
            GroceryItemCategory(id = 10, title = "Indoor games", iconRes = R.drawable.ic_toys_indoor_games),
            GroceryItemCategory(id = 11, title = "Sports equipments", iconRes = R.drawable.ic_toys_sports_equipment),
            GroceryItemCategory(id = 12, title = "Workout essentials", iconRes = R.drawable.ic_toys_workout_essentials)
        )// In your screen composable
//        Spacer(modifier = Modifier.height(10.dp))
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(vertical = 0.dp, horizontal = 12.dp )
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        ListItemsGrid(
            browseText = "Shop By Category",
            items = shopByCategoryToys,
            onItemClick = { item ->
                println("Clicked: ${item.title}")
            },
            columns = 4,
            horizontalSpacing = 8.dp,
            verticalSpacing = 1.dp,      // Increased from 1.dp for better spacing
            itemHeight = 120.dp,          // Increased from 120.dp for larger items
            imageSize = 90.dp,            // Increased from 100.dp? Actually reduced to fit 4 columns
            containerSize = 90.dp,        // Added container size for image
            backgroundColor = MaterialTheme.customColors.background,
            onBackClick = {}
        )
    }

        val shopByAgeGender = listOf(
            GroceryItemCategory(id = 1, title = "For babies", iconRes = R.drawable.ic_toys_for_babies),
            GroceryItemCategory(id = 2, title = "For girls", iconRes = R.drawable.ic_toys_for_girls),
            GroceryItemCategory(id = 3, title = "For boys", iconRes = R.drawable.ic_toys_for_boys),
            GroceryItemCategory(id = 4, title = "For teens", iconRes = R.drawable.ic_toys_for_teens)
        )
        Image(
            painter = painterResource(R.drawable.ic_shop_by_age_gender),
            contentDescription = "Banner",
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp), // Height between min and max, // 30% of screen height, // Sets height based on width and aspect ratio
            contentScale = ContentScale.FillBounds
        )
        ListItemsGrid(
            browseText = "",
            showBrowseText = false,
            items = shopByAgeGender,
            onItemClick = { item ->
                println("Clicked: ${item.title}")
            },
            columns = 4,
            horizontalSpacing = 8.dp,
            verticalSpacing = 10.dp,      // Increased from 1.dp for better spacing
            itemHeight = 110.dp,          // Increased from 120.dp for larger items
            imageSize = 90.dp,            // Increased from 100.dp? Actually reduced to fit 4 columns
            containerSize = 90.dp,        // Added container size for image
            backgroundColor = Color(0xFFDCF2FF),
            onBackClick = {}
        )

        val toyBrands = listOf(
            GroceryItemCategory(id = 1, title = "Funskool", iconRes = R.drawable.ic_brand_funskool),
            GroceryItemCategory(id = 2, title = "Mattel", iconRes = R.drawable.ic_brand_mattel),
            GroceryItemCategory(id = 3, title = "PlayShifu", iconRes = R.drawable.ic_brand_playshifu),
            GroceryItemCategory(id = 4, title = "Wembley", iconRes = R.drawable.ic_brand_wembley),
            GroceryItemCategory(id = 5, title = "Hasbro", iconRes = R.drawable.ic_brand_hasbro),
            GroceryItemCategory(id = 6, title = "Toyshine", iconRes = R.drawable.ic_brand_toyshine)
        )
        // In your screen composable
        Spacer(modifier = Modifier.height(10.dp))
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(vertical = 0.dp, horizontal = 12.dp )
        ) {
            ListItemsGrid(
                browseText = "Discover toys from your favourite brands",
                items = toyBrands,
                onItemClick = { item ->
                    println("Clicked: ${item.title}")
                },
                columns = 3,
                horizontalSpacing = 5.dp,  // Increased spacing for better readability
                verticalSpacing = 0.dp,    // Increased vertical spacing
                itemHeight = 150.dp,        // Taller items to accommodate larger images
                imageSize = 115.dp,          // Larger image size
                containerSize = 120.dp,     // Larger container for images
                backgroundColor = MaterialTheme.customColors.background,
                onBackClick = {}
            )
        }
        // In your screen composable
        Spacer(modifier = Modifier.height(10.dp))
        val sportsAndFitness = listOf(
            GroceryItemCategory(id = 1, title = "Swimming", iconRes = R.drawable.ic_sports_swimming),
            GroceryItemCategory(id = 2, title = "Cricket", iconRes = R.drawable.ic_sports_cricket),
            GroceryItemCategory(id = 3, title = "Badminton", iconRes = R.drawable.ic_sports_badminton),
            GroceryItemCategory(id = 4, title = "Fitness, health & nutrition", iconRes = R.drawable.ic_fitness_health_nutrition),
            GroceryItemCategory(id = 5, title = "Yoga", iconRes = R.drawable.ic_fitness_yoga),
            GroceryItemCategory(id = 6, title = "Gym essentials", iconRes = R.drawable.ic_fitness_gym_essentials)
        )
            Image(
                painter = painterResource(R.drawable.ic_sports_and_fitness),
                contentDescription = "Banner",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp), // Height between min and max, // 30% of screen height, // Sets height based on width and aspect ratio
                contentScale = ContentScale.FillBounds
            )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(vertical = 0.dp, horizontal = 12.dp )
        ) {
            ListItemsGrid(
                browseText = "",
                showBrowseText = false,
                items = sportsAndFitness,
                onItemClick = { item ->
                    println("Clicked: ${item.title}")
                },
                columns = 3,
                horizontalSpacing = 5.dp,  // Increased spacing for better readability
                verticalSpacing = 0.dp,    // Increased vertical spacing
                itemHeight = 150.dp,        // Taller items to accommodate larger images
                imageSize = 115.dp,          // Larger image size
                containerSize = 120.dp,     // Larger container for images
                backgroundColor = MaterialTheme.customColors.background,
                onBackClick = {}
            )

            val shopByPrice = listOf(
                GroceryItemCategory(id = 1, title = "Under Rs.199", iconRes = R.drawable.ic_price_under_199),
                GroceryItemCategory(id = 2, title = "Under Rs.499", iconRes = R.drawable.ic_price_under_499),
                GroceryItemCategory(id = 3, title = "Rs.500 - 1000", iconRes = R.drawable.ic_price_500_1000),
                GroceryItemCategory(id = 4, title = "Above Rs.1000", iconRes = R.drawable.ic_price_above_1000)
            )
            ListItemsGrid(
                browseText = "Shop by price",
//                showBrowseText = false,
                items = shopByPrice,
                onItemClick = { item ->
                    println("Clicked: ${item.title}")
                },
                columns = 4,
                horizontalSpacing = 8.dp,
                verticalSpacing = 10.dp,      // Increased from 1.dp for better spacing
                itemHeight = 110.dp,          // Increased from 120.dp for larger items
                imageSize = 90.dp,            // Increased from 100.dp? Actually reduced to fit 4 columns
                containerSize = 90.dp,        // Added container size for image
                backgroundColor = Color(0xFFDCF2FF),
                onBackClick = {}
            )
        }
    }
}

@Composable
fun KidsCategoryPage() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(12.dp)
    ) {
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Kids",
            style = MaterialTheme.typography.bodySmall.copy(
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.customColors.black
            ),
            maxLines = 1,
            modifier = Modifier.fillMaxWidth().padding(start = 12.dp)
        )
        Text(
            text = "Kids clothing, accessories & more",
            fontSize = 16.sp,
            color = MaterialTheme.customColors.gray,
            modifier = Modifier.padding(start = 12.dp, top = 8.dp)
        )
    }
}

@Composable
fun BeautyCategoryPage() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(12.dp)
    ) {
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Beauty",
            style = MaterialTheme.typography.bodySmall.copy(
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.customColors.black
            ),
            maxLines = 1,
            modifier = Modifier.fillMaxWidth().padding(start = 12.dp)
        )
        Text(
            text = "Cosmetics, skincare & personal care",
            fontSize = 16.sp,
            color = MaterialTheme.customColors.gray,
            modifier = Modifier.padding(start = 12.dp, top = 8.dp)
        )
    }
}

@Composable
fun PetsCategoryPage() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(12.dp)
    ) {
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Pets",
            style = MaterialTheme.typography.bodySmall.copy(
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.customColors.black
            ),
            maxLines = 1,
            modifier = Modifier.fillMaxWidth().padding(start = 12.dp)
        )
        Text(
            text = "Pet food, accessories & care products",
            fontSize = 16.sp,
            color = MaterialTheme.customColors.gray,
            modifier = Modifier.padding(start = 12.dp, top = 8.dp)
        )
    }
}

@Composable
fun DecorCategoryPage() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(12.dp)
    ) {
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Home Decor",
            style = MaterialTheme.typography.bodySmall.copy(
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.customColors.black
            ),
            maxLines = 1,
            modifier = Modifier.fillMaxWidth().padding(start = 12.dp)
        )
        Text(
            text = "Home decoration & furnishings",
            fontSize = 16.sp,
            color = MaterialTheme.customColors.gray,
            modifier = Modifier.padding(start = 12.dp, top = 8.dp)
        )
    }
}

@Composable
fun ImportedCategoryPage() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(12.dp)
    ) {
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Imported Products",
            style = MaterialTheme.typography.bodySmall.copy(
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.customColors.black
            ),
            maxLines = 1,
            modifier = Modifier.fillMaxWidth().padding(start = 12.dp)
        )
        Text(
            text = "International brands & imported goods",
            fontSize = 16.sp,
            color = MaterialTheme.customColors.gray,
            modifier = Modifier.padding(start = 12.dp, top = 8.dp)
        )
    }
}


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