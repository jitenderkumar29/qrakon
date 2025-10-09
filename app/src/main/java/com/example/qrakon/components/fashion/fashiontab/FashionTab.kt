package com.example.qrakon.components.fashion.fashiontab

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Surface
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
import com.example.qrakon.components.homescreen.CategoryProducts
import com.example.qrakon.R
import com.example.qrakon.ui.theme.customColors
import androidx.compose.ui.text.style.TextAlign
import com.example.qrakon.components.categorytabs.CarouselFashionOne
import com.example.qrakon.components.categorytabs.Category
import com.example.qrakon.components.categorytabs.WomenFashionCarousel
import com.example.qrakon.components.categorytabs.womenFashionCategories
import com.example.qrakon.components.homescreen.LocationSelectionButton

// Sealed class for fashion category pages
sealed class FashionCategoryPage {
    object Women : FashionCategoryPage()
    object Men : FashionCategoryPage()
    object Kids : FashionCategoryPage()
    object Home : FashionCategoryPage()
    object Categories : FashionCategoryPage()
}


@Composable
fun FashionTab(
    onCategorySelected: (FashionCategoryPage) -> Unit = {}
) {
    var selectedTabIndex by remember { mutableIntStateOf(0) }

    val tabs = listOf(
        "Women" to null,
        "Men" to null,
        "Kids" to null,
        "Home" to null,
        "" to R.drawable.ic_category_1
    )

    Column(modifier = Modifier.fillMaxWidth()) {
        // Use Row for fixed width tabs
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.customColors.skyBlue),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            tabs.forEachIndexed { index, (title, iconRes) ->
                val isSelected = selectedTabIndex == index

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clickable {
                            selectedTabIndex = index
                            val page = when (index) {
                                0 -> FashionCategoryPage.Women
                                1 -> FashionCategoryPage.Men
                                2 -> FashionCategoryPage.Kids
                                3 -> FashionCategoryPage.Home
                                4 -> FashionCategoryPage.Categories
                                else -> FashionCategoryPage.Women
                            }
                            onCategorySelected(page)
                        }
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Show icon only for Categories tab
                        if (index == 4 && iconRes != null) {
                            Icon(
                                painter = painterResource(id = iconRes),
                                contentDescription = "Categories",
                                modifier = Modifier.size(20.dp),
                                tint = if (isSelected) {
                                    MaterialTheme.customColors.onPrimaryContainer
                                } else {
                                    MaterialTheme.customColors.black
                                }
                            )
                        } else {
                            // Show text for other tabs
                            Text(
                                text = title,
                                fontSize = 15.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                color = if (isSelected) {
                                    MaterialTheme.customColors.onPrimaryContainer
                                } else {
                                    MaterialTheme.customColors.black
                                },
                                maxLines = 1,
                                textAlign = TextAlign.Center,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }

                    // Indicator at the bottom that spans the full tab width
                    if (isSelected) {
                        Spacer(
                            modifier = Modifier
                                .height(5.dp)
                                .fillMaxWidth()
                                .align(Alignment.BottomCenter)
                                .background(
                                    color = MaterialTheme.customColors.onPrimaryContainer,
                                    shape = RoundedCornerShape(topStart = 2.dp, topEnd = 2.dp)
                                )
                        )
                    }
                }
            }
        }

        // Show content for each tab
        when (selectedTabIndex) {
            0 -> WomenFashionPage(
                onTabSelected = { categoryName ->
                    // Handle subcategory selection for Women tab
                    println("Women subcategory selected: $categoryName")
                    // You can add navigation, filtering, or state updates here
                }
            )
            1 -> MenFashionPage()
            2 -> KidsFashionPage()
            3 -> HomeFashionPage()
            4 -> CategoriesFashionPage()
            else -> WomenFashionPage(
                onTabSelected = { categoryName ->
                    println("Women subcategory selected: $categoryName")
                }
            )
        }
    }
}

@Composable
fun WomenFashionPage( onTabSelected: (String) -> Unit,
                      modifier: Modifier = Modifier) {
    var selectedLocation by remember { mutableStateOf("Dhruv 110044") }
    var showLocationDialog by remember { mutableStateOf(false) }

    // Women's Fashion Categories
    val womenFashionCategories = listOf(
        Category(0, "Western", R.drawable.ic_fashion_western),
        Category(1, "Ethnic", R.drawable.ic_fashion_ethnic),
        Category(2, "Fusion", R.drawable.ic_fashion_fusion),
        Category(3, "Essentials", R.drawable.ic_fashion_essentials),
        Category(4, "Beauty", R.drawable.ic_fashion_beauty),
        Category(5, "Footwear", R.drawable.ic_fashion_footwear),
        Category(6, "Ad-Ons", R.drawable.ic_fashion_ad_ons),
        Category(7, "Jewellery", R.drawable.ic_fashion_jewellery),
        Category(8, "Home", R.drawable.ic_fashion_home),
        Category(9, "Sportswear", R.drawable.ic_fashion_sportswear),
        Category(10, "Girls", R.drawable.ic_fashion_girls),
        Category(11, "View All", R.drawable.ic_view_all_home_tab),
    )
    CarouselFashionOne(
        categories = womenFashionCategories,
        onTabSelected = onTabSelected,
        modifier = modifier,
        backgroundColor = MaterialTheme.customColors.imageBgColor1,
        itemWidth = 75,
        itemHeight = 75,
        horizontalSpacing = 8
    )
    val bannerImages = listOf(
        painterResource(id = R.drawable.women_fashion_banner1),
        painterResource(id = R.drawable.women_fashion_banner2),
        painterResource(id = R.drawable.women_fashion_banner3),
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        BannerFashion(
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


//            Surface(
//                color = MaterialTheme.customColors.darkAccent,
//                modifier = Modifier.fillMaxWidth()
//            ) {
//                Column(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(start = 0.dp, end = 0.dp, top = 0.dp, bottom = 0.dp)
//                ) {
//                    LocationSelectionButton(
//                        selectedLocation = selectedLocation,
//                        onLocationClick = { showLocationDialog = true }
//                    )
//                }
//            }

//
//        Spacer(
//            modifier = Modifier.height(2.dp)
//                .fillMaxWidth()
//                .background(MaterialTheme.customColors.spacerColor)
//        )
//
//        val womenCategories = remember {
//            listOf(
//                Category(0, "Dresses", R.drawable.ic_dresses),
//                Category(1, "Tops", R.drawable.ic_tops),
//                Category(2, "Jeans", R.drawable.ic_jeans),
//                Category(3, "Ethnic Wear", R.drawable.ic_ethnic_wear),
//                Category(4, "Footwear", R.drawable.ic_footwear),
//                Category(5, "Bags", R.drawable.ic_bags),
//                Category(6, "Jewellery", R.drawable.ic_jewellery),
//                Category(7, "Lingerie", R.drawable.ic_lingerie),
//                Category(8, "Activewear", R.drawable.ic_activewear),
//                Category(9, "Winter Wear", R.drawable.ic_winter_wear),
//                Category(10, "Beauty", R.drawable.ic_beauty),
//                Category(11, "View All", R.drawable.ic_view_all_home_tab),
//            )
//        }
//
//        var selectedCategory by remember { mutableStateOf<Category?>(null) }
//
//        Column {
//            CategoryProducts(
//                categories = womenCategories,
//                onCategorySelected = { category ->
//                    selectedCategory = category
//                    println("Selected category: ${category.name}")
//                },
//                modifier = Modifier.fillMaxWidth(),
//                initialSelectedCategory = womenCategories.first(),
//                itemWidth = 75,
//                itemHeight = 65,
//                horizontalSpacing = 8,
//                verticalSpacing = 8,
//                backgroundColor = MaterialTheme.customColors.white
//            )
//
//            Spacer(
//                modifier = Modifier.height(2.dp)
//                    .fillMaxWidth()
//                    .background(MaterialTheme.customColors.spacerColor)
//            )
//
//            AdsSponsored(
//                adImages = listOf(
//                    R.drawable.ads_women_fashion_1,
//                    R.drawable.ads_women_fashion_2,
//                ),
//                onAdClick = { index ->
//                    println("Clicked ad index: $index")
//                }
//            )
//
//            val womenProducts = listOf(
//                Product("Summer Dresses", "From ₹799", R.drawable.women_dress_product),
//                Product("Designer Sarees", "Up to 50% Off", R.drawable.women_saree_product),
//                Product("Handbags", "Min. 40% Off", R.drawable.women_bag_product)
//            )
//
//            Spacer(
//                modifier = Modifier.height(2.dp)
//                    .fillMaxWidth()
//                    .background(MaterialTheme.customColors.spacerColor)
//            )
//
//            ProductList(
//                products = womenProducts,
//                showName = true,
//                showPrice = true
//            )
//        }
//        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun MenFashionPage() {
//    val bannerImages = listOf(
//        painterResource(id = R.drawable.men_fashion_banner1),
//        painterResource(id = R.drawable.men_fashion_banner2),
//        painterResource(id = R.drawable.men_fashion_banner3),
//    )
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//    ) {
//        BannerHome(
//            images = bannerImages,
//            onImageClick = { page ->
//                when (page) {
//                    0 -> onBanner1Click()
//                    1 -> onBanner2Click()
//                    2 -> onBanner3Click()
//                }
//            },
//            autoScrollDelay = 2000,
//            height = 270.dp,
//            dotSize = 8.dp,
//            modifier = Modifier.padding(bottom = 0.dp)
//        )
//
//        Spacer(
//            modifier = Modifier.height(2.dp)
//                .fillMaxWidth()
//                .background(MaterialTheme.customColors.spacerColor)
//        )
//
//        val menCategories = remember {
//            listOf(
//                Category(0, "Shirts", R.drawable.ic_shirts),
//                Category(1, "T-Shirts", R.drawable.ic_tshirts),
//                Category(2, "Jeans", R.drawable.ic_jeans_men),
//                Category(3, "Formal Wear", R.drawable.ic_formal_wear),
//                Category(4, "Footwear", R.drawable.ic_footwear_men),
//                Category(5, "Watches", R.drawable.ic_watches),
//                Category(6, "Accessories", R.drawable.ic_accessories_men),
//                Category(7, "Sportswear", R.drawable.ic_sportswear),
//                Category(8, "Winter Wear", R.drawable.ic_winter_wear_men),
//                Category(9, "Grooming", R.drawable.ic_grooming),
//                Category(10, "Sunglasses", R.drawable.ic_sunglasses),
//                Category(11, "View All", R.drawable.ic_view_all_home_tab),
//            )
//        }
//
//        var selectedCategory by remember { mutableStateOf<Category?>(null) }
//
//        Column {
//            CategoryProducts(
//                categories = menCategories,
//                onCategorySelected = { category ->
//                    selectedCategory = category
//                    println("Selected category: ${category.name}")
//                },
//                modifier = Modifier.fillMaxWidth(),
//                initialSelectedCategory = menCategories.first(),
//                itemWidth = 75,
//                itemHeight = 65,
//                horizontalSpacing = 8,
//                verticalSpacing = 8,
//                backgroundColor = MaterialTheme.customColors.white
//            )
//
//            Spacer(
//                modifier = Modifier.height(2.dp)
//                    .fillMaxWidth()
//                    .background(MaterialTheme.customColors.spacerColor)
//            )
//
//            AdsSponsored(
//                adImages = listOf(
//                    R.drawable.ads_men_fashion_1,
//                    R.drawable.ads_men_fashion_2,
//                ),
//                onAdClick = { index ->
//                    println("Clicked ad index: $index")
//                }
//            )
//
//            val menProducts = listOf(
//                Product("Casual Shirts", "From ₹599", R.drawable.men_shirt_product),
//                Product("Sneakers", "Up to 60% Off", R.drawable.men_shoes_product),
//                Product("Watches", "Min. 30% Off", R.drawable.men_watch_product)
//            )
//
//            Spacer(
//                modifier = Modifier.height(2.dp)
//                    .fillMaxWidth()
//                    .background(MaterialTheme.customColors.spacerColor)
//            )
//
//            ProductList(
//                products = menProducts,
//                showName = true,
//                showPrice = true
//            )
//        }
//        Spacer(modifier = Modifier.height(16.dp))
//    }
}

@Composable
fun KidsFashionPage() {
//    val bannerImages = listOf(
//        painterResource(id = R.drawable.kids_fashion_banner1),
//        painterResource(id = R.drawable.kids_fashion_banner2),
//        painterResource(id = R.drawable.kids_fashion_banner3),
//    )
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//    ) {
//        BannerHome(
//            images = bannerImages,
//            onImageClick = { page ->
//                when (page) {
//                    0 -> onBanner1Click()
//                    1 -> onBanner2Click()
//                    2 -> onBanner3Click()
//                }
//            },
//            autoScrollDelay = 2000,
//            height = 270.dp,
//            dotSize = 8.dp,
//            modifier = Modifier.padding(bottom = 0.dp)
//        )
//
//        Spacer(
//            modifier = Modifier.height(2.dp)
//                .fillMaxWidth()
//                .background(MaterialTheme.customColors.spacerColor)
//        )
//
//        val kidsCategories = remember {
//            listOf(
//                Category(0, "Boys Wear", R.drawable.ic_boys_wear),
//                Category(1, "Girls Wear", R.drawable.ic_girls_wear),
//                Category(2, "Infants", R.drawable.ic_infants),
//                Category(3, "Footwear", R.drawable.ic_footwear_kids),
//                Category(4, "Toys", R.drawable.ic_toys),
//                Category(5, "Accessories", R.drawable.ic_accessories_kids),
//                Category(6, "School Wear", R.drawable.ic_school_wear),
//                Category(7, "Party Wear", R.drawable.ic_party_wear),
//                Category(8, "Winter Wear", R.drawable.ic_winter_wear_kids),
//                Category(9, "Diapers", R.drawable.ic_diapers),
//                Category(10, "Backpacks", R.drawable.ic_backpacks),
//                Category(11, "View All", R.drawable.ic_view_all_home_tab),
//            )
//        }
//
//        var selectedCategory by remember { mutableStateOf<Category?>(null) }
//
//        Column {
//            CategoryProducts(
//                categories = kidsCategories,
//                onCategorySelected = { category ->
//                    selectedCategory = category
//                    println("Selected category: ${category.name}")
//                },
//                modifier = Modifier.fillMaxWidth(),
//                initialSelectedCategory = kidsCategories.first(),
//                itemWidth = 75,
//                itemHeight = 65,
//                horizontalSpacing = 8,
//                verticalSpacing = 8,
//                backgroundColor = MaterialTheme.customColors.white
//            )
//
//            Spacer(
//                modifier = Modifier.height(2.dp)
//                    .fillMaxWidth()
//                    .background(MaterialTheme.customColors.spacerColor)
//            )
//
//            AdsSponsored(
//                adImages = listOf(
//                    R.drawable.ads_kids_fashion_1,
//                    R.drawable.ads_kids_fashion_2,
//                ),
//                onAdClick = { index ->
//                    println("Clicked ad index: $index")
//                }
//            )
//
//            val kidsProducts = listOf(
//                Product("Kids T-Shirts", "From ₹299", R.drawable.kids_tshirt_product),
//                Product("School Shoes", "Up to 50% Off", R.drawable.kids_shoes_product),
//                Product("Toys Set", "Min. 40% Off", R.drawable.kids_toys_product)
//            )
//
//            Spacer(
//                modifier = Modifier.height(2.dp)
//                    .fillMaxWidth()
//                    .background(MaterialTheme.customColors.spacerColor)
//            )
//
//            ProductList(
//                products = kidsProducts,
//                showName = true,
//                showPrice = true
//            )
//        }
//        Spacer(modifier = Modifier.height(16.dp))
//    }
}

@Composable
fun HomeFashionPage() {
//    val bannerImages = listOf(
//        painterResource(id = R.drawable.home_fashion_banner1),
//        painterResource(id = R.drawable.home_fashion_banner2),
//        painterResource(id = R.drawable.home_fashion_banner3),
//    )
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//    ) {
//        BannerHome(
//            images = bannerImages,
//            onImageClick = { page ->
//                when (page) {
//                    0 -> onBanner1Click()
//                    1 -> onBanner2Click()
//                    2 -> onBanner3Click()
//                }
//            },
//            autoScrollDelay = 2000,
//            height = 270.dp,
//            dotSize = 8.dp,
//            modifier = Modifier.padding(bottom = 0.dp)
//        )
//
//        Spacer(
//            modifier = Modifier.height(2.dp)
//                .fillMaxWidth()
//                .background(MaterialTheme.customColors.spacerColor)
//        )
//
//        val homeCategories = remember {
//            listOf(
//                Category(0, "Bedding", R.drawable.ic_bedding),
//                Category(1, "Decor", R.drawable.ic_decor),
//                Category(2, "Kitchen", R.drawable.ic_kitchen),
//                Category(3, "Bath", R.drawable.ic_bath),
//                Category(4, "Furniture", R.drawable.ic_furniture),
//                Category(5, "Lighting", R.drawable.ic_lighting),
//                Category(6, "Storage", R.drawable.ic_storage),
//                Category(7, "Garden", R.drawable.ic_garden),
//                Category(8, "Curtains", R.drawable.ic_curtains),
//                Category(9, "Rugs", R.drawable.ic_rugs),
//                Category(10, "Wall Art", R.drawable.ic_wall_art),
//                Category(11, "View All", R.drawable.ic_view_all_home_tab),
//            )
//        }
//
//        var selectedCategory by remember { mutableStateOf<Category?>(null) }
//
//        Column {
//            CategoryProducts(
//                categories = homeCategories,
//                onCategorySelected = { category ->
//                    selectedCategory = category
//                    println("Selected category: ${category.name}")
//                },
//                modifier = Modifier.fillMaxWidth(),
//                initialSelectedCategory = homeCategories.first(),
//                itemWidth = 75,
//                itemHeight = 65,
//                horizontalSpacing = 8,
//                verticalSpacing = 8,
//                backgroundColor = MaterialTheme.customColors.white
//            )
//
//            Spacer(
//                modifier = Modifier.height(2.dp)
//                    .fillMaxWidth()
//                    .background(MaterialTheme.customColors.spacerColor)
//            )
//
//            AdsSponsored(
//                adImages = listOf(
//                    R.drawable.ads_home_fashion_1,
//                    R.drawable.ads_home_fashion_2,
//                ),
//                onAdClick = { index ->
//                    println("Clicked ad index: $index")
//                }
//            )
//
//            val homeProducts = listOf(
//                Product("Bed Sheets", "From ₹999", R.drawable.home_bedsheet_product),
//                Product("Kitchen Set", "Up to 50% Off", R.drawable.home_kitchen_product),
//                Product("Wall Decor", "Min. 30% Off", R.drawable.home_decor_product)
//            )
//
//            Spacer(
//                modifier = Modifier.height(2.dp)
//                    .fillMaxWidth()
//                    .background(MaterialTheme.customColors.spacerColor)
//            )
//
//            ProductList(
//                products = homeProducts,
//                showName = true,
//                showPrice = true
//            )
//        }
//        Spacer(modifier = Modifier.height(16.dp))
//    }
}

@Composable
fun CategoriesFashionPage() {
//    val bannerImages = listOf(
//        painterResource(id = R.drawable.categories_fashion_banner1),
//        painterResource(id = R.drawable.categories_fashion_banner2),
//        painterResource(id = R.drawable.categories_fashion_banner3),
//    )
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//    ) {
//        BannerHome(
//            images = bannerImages,
//            onImageClick = { page ->
//                when (page) {
//                    0 -> onBanner1Click()
//                    1 -> onBanner2Click()
//                    2 -> onBanner3Click()
//                }
//            },
//            autoScrollDelay = 2000,
//            height = 270.dp,
//            dotSize = 8.dp,
//            modifier = Modifier.padding(bottom = 0.dp)
//        )
//
//        Spacer(
//            modifier = Modifier.height(2.dp)
//                .fillMaxWidth()
//                .background(MaterialTheme.customColors.spacerColor)
//        )
//
//        val allCategories = remember {
//            listOf(
//                Category(0, "All Women", R.drawable.ic_women_fashion),
//                Category(1, "All Men", R.drawable.ic_men_fashion),
//                Category(2, "All Kids", R.drawable.ic_kids_fashion),
//                Category(3, "All Home", R.drawable.ic_home_fashion),
//                Category(4, "Brands", R.drawable.ic_brands),
//                Category(5, "New Arrivals", R.drawable.ic_new_arrivals),
//                Category(6, "Sale", R.drawable.ic_sale),
//                Category(7, "Luxury", R.drawable.ic_luxury),
//                Category(8, "Sustainable", R.drawable.ic_sustainable),
//                Category(9, "Accessories", R.drawable.ic_accessories_all),
//                Category(10, "Beauty", R.drawable.ic_beauty_all),
//                Category(11, "View All", R.drawable.ic_view_all_home_tab),
//            )
//        }
//
//        var selectedCategory by remember { mutableStateOf<Category?>(null) }
//
//        Column {
//            CategoryProducts(
//                categories = allCategories,
//                onCategorySelected = { category ->
//                    selectedCategory = category
//                    println("Selected category: ${category.name}")
//                },
//                modifier = Modifier.fillMaxWidth(),
//                initialSelectedCategory = allCategories.first(),
//                itemWidth = 75,
//                itemHeight = 65,
//                horizontalSpacing = 8,
//                verticalSpacing = 8,
//                backgroundColor = MaterialTheme.customColors.white
//            )
//
//            Spacer(
//                modifier = Modifier.height(2.dp)
//                    .fillMaxWidth()
//                    .background(MaterialTheme.customColors.spacerColor)
//            )
//
//            AdsSponsored(
//                adImages = listOf(
//                    R.drawable.ads_categories_fashion_1,
//                    R.drawable.ads_categories_fashion_2,
//                ),
//                onAdClick = { index ->
//                    println("Clicked ad index: $index")
//                }
//            )
//
//            val categoryProducts = listOf(
//                Product("All Collections", "Shop Now", R.drawable.categories_all_product),
//                Product("Trending Now", "Hot Picks", R.drawable.categories_trending_product),
//                Product("Best Sellers", "Top Rated", R.drawable.categories_bestseller_product)
//            )
//
//            Spacer(
//                modifier = Modifier.height(2.dp)
//                    .fillMaxWidth()
//                    .background(MaterialTheme.customColors.spacerColor)
//            )
//
//            ProductList(
//                products = categoryProducts,
//                showName = true,
//                showPrice = true
//            )
//        }
//        Spacer(modifier = Modifier.height(16.dp))
//    }
}

@Composable
fun FashionMainScreen(navController: NavHostController) {
    var currentPage by remember { mutableIntStateOf(0) }
    Column(modifier = Modifier.fillMaxWidth()) {
        FashionTab(
            onCategorySelected = { fashionCategoryPage ->
                currentPage = when (fashionCategoryPage) {
                    is FashionCategoryPage.Women -> 0
                    is FashionCategoryPage.Men -> 1
                    is FashionCategoryPage.Kids -> 2
                    is FashionCategoryPage.Home -> 3
                    is FashionCategoryPage.Categories -> 4
                }
            }
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