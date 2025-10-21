package com.example.qrakon.components.fashion.fashiontab

import KidsFilter
import Product
import ProductList
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import com.example.qrakon.R
import com.example.qrakon.ui.theme.customColors
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import com.example.qrakon.components.categorytabs.CarouselFashionOne
import com.example.qrakon.components.categorytabs.Category
import com.example.qrakon.components.categorytabs.CategoryProducts

// Sealed class for fashion category pages
sealed class FashionCategoryPage {
    object Women : FashionCategoryPage()
    object Men : FashionCategoryPage()
    object Kids : FashionCategoryPage()
    object Home : FashionCategoryPage()
    object Luxe : FashionCategoryPage()
    object Brands : FashionCategoryPage()
    object Categories : FashionCategoryPage()
}

@Composable
fun FashionTab(
    navController: NavController,
    onCategorySelected: (FashionCategoryPage) -> Unit = {},
    onOpenFashionCategory: () -> Unit = {}
) {
    var selectedTabIndex by remember { mutableIntStateOf(0) }

    val tabs = listOf(
        "Women" to null,
        "Men" to null,
        "Kids" to null,
        "Home" to null,
        "Luxe" to null,
        "Brands" to null,
//        "" to R.drawable.ic_category_image
    )

    Column(modifier = Modifier.fillMaxWidth()) {
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
                                4 -> FashionCategoryPage.Luxe
                                5 -> FashionCategoryPage.Brands
                                6 -> FashionCategoryPage.Categories
                                else -> FashionCategoryPage.Women
                            }

                            if (index == 6) {
                                onOpenFashionCategory()
                            } else {
                                onCategorySelected(page)
                            }
                        }
                ) {
                    // Content + bottom border are wrapped together
                    Box(
                        modifier = Modifier
//                            .weight(1f)
                            .clickable {
                                selectedTabIndex = index
                                val page = when (index) {
                                    0 -> FashionCategoryPage.Women
                                    1 -> FashionCategoryPage.Men
                                    2 -> FashionCategoryPage.Kids
                                    3 -> FashionCategoryPage.Home
                                    4 -> FashionCategoryPage.Luxe
                                    5 -> FashionCategoryPage.Brands
                                    6 -> FashionCategoryPage.Categories
                                    else -> FashionCategoryPage.Women
                                }

                                if (index == 6) {
                                    onOpenFashionCategory()
                                } else {
                                    onCategorySelected(page)
                                }
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            if (index == 6 && iconRes != null) {
                                Icon(
                                    painter = painterResource(id = iconRes),
                                    contentDescription = "Categories",
                                    modifier = Modifier.size(25.dp),
                                    tint = Color.Unspecified
                                )
                            } else {
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

                        // Bottom border (always visible)
//                        Box(
//                            modifier = Modifier
//                                .align(Alignment.BottomCenter)
//                                .fillMaxWidth()
//                                .height(1.dp)
//                                .background(MaterialTheme.customColors.spacerColor)
//                        )

                        // Active indicator (exactly above border, no gap)
                        if (isSelected) {
                            Box(
                                modifier = Modifier
                                    .align(Alignment.BottomCenter)
                                    .fillMaxWidth()
                                    .height(3.dp)
                                    .background(MaterialTheme.customColors.onPrimaryContainer)
                            )
                        }
                    }

                }
            }
        }

        // Show content for each tab
        when (selectedTabIndex) {
            0 -> WomenFashionPage(onTabSelected = { categoryName ->
                navController.navigate("categoryDetail/$categoryName")
            })
            1 -> MenFashionPage(onTabSelected = { println("Men subcategory: $it") })
            2 -> KidsFashionPage(onTabSelected = { println("Kids subcategory: $it") })
            3 -> HomeFashionPage(onTabSelected = { println("Home subcategory: $it") })
            4 -> LuxeFashionPage(onTabSelected = { println("Luxe subcategory: $it") })
            5 -> BrandsFashionPage(onTabSelected = { println("Brands subcategory: $it") })
            else -> WomenFashionPage(onTabSelected = { println("Women subcategory: $it") })
        }
    }
}


@Composable
fun WomenFashionPage(
    onTabSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {

    // Women's Fashion Categories
    val womenFashionCategories = listOf(
        Category(0, "Ethnic", R.drawable.ic_fashion_ethnic),
        Category(1, "Western", R.drawable.ic_fashion_western),
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
//    CarouselFashionOne(
//        categories = womenFashionCategories,
//        onTabSelected = { categoryName ->
//            navController.navigate("categoryDetail/$categoryName")
//        },
//        modifier = modifier,
//        backgroundColor = MaterialTheme.customColors.imageBgColor1,
//        itemWidth = 75,
//        itemHeight = 75,
//        horizontalSpacing = 8
//    )

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
        painterResource(id = R.drawable.women_fashion_banner6),
        painterResource(id = R.drawable.women_fashion_banner7),
        painterResource(id = R.drawable.women_fashion_banner2),
        painterResource(id = R.drawable.women_fashion_banner3),
        painterResource(id = R.drawable.women_fashion_banner4),
        painterResource(id = R.drawable.women_fashion_banner5),
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
            height = 500.dp,
            dotSize = 8.dp,
            modifier = Modifier.padding(bottom = 0.dp)
        )

//
        Spacer(
            modifier = Modifier
                .height(2.dp)
                .fillMaxWidth()
                .background(MaterialTheme.customColors.spacerColor)
        )
//
        val womenFashionCategoriesTwo = remember {
            listOf(
                Category(0, "Explore", R.drawable.ic_explore_women_fashion_two),
                Category(1, "Kurta Sets", R.drawable.ic_kurta_sets_women_fashion_two),
                Category(2, "Jeans", R.drawable.ic_jeans_women_fashion_two),
                Category(3, "Ethnic Dresses", R.drawable.ic_ethnic_dresses_women_fashion_two),
                Category(4, "Lingerie", R.drawable.ic_lingerie_women_fashion_two),
                Category(5, "Western Workwear", R.drawable.ic_western_workwear_women_fashion_two),
                Category(6, "Kurtas", R.drawable.ic_kurtas_women_fashion_two),
                Category(7, "Co-Ords", R.drawable.ic_co_ords_women_fashion_two),
                Category(8, "T-Shirts", R.drawable.ic_t_shirts_women_fashion_two),
                Category(9, "Dresses", R.drawable.ic_dresses_women_fashion_two),
                Category(10, "Tops & Shirts", R.drawable.ic_tops_shirts_women_fashion_two),
                Category(11, "Sarees", R.drawable.ic_sarees_women_fashion_two),
                Category(12, "Curve Collection", R.drawable.ic_curve_collection_women_fashion_two),
                Category(13, "Trousers", R.drawable.ic_trousers_women_fashion_two),
                Category(14, "Watches", R.drawable.ic_watches_women_fashion_two),
                Category(15, "Flats & Heels", R.drawable.ic_flats_heels_women_fashion_two),
                Category(16, "Handbags", R.drawable.ic_handbags_women_fashion_two),
                Category(17, "Smart Watches", R.drawable.ic_smart_watches_women_fashion_two),
                Category(18, "Explore", R.drawable.ic_explore_women_fashion_two2),
                Category(19, "Casuals Shoes", R.drawable.ic_casuals_shoes_women_fashion_two),
                Category(20, "Flip-Flops", R.drawable.ic_flip_flops_women_fashion_two),
                Category(21, "Sports Shoes", R.drawable.ic_sports_shoes_women_fashion_two),
                Category(22, "Sunglasses", R.drawable.ic_sunglasses_women_fashion_two),
                Category(23, "Makeup", R.drawable.makeup_women_fashion_two),
                Category(24, "Blankets & Quilts", R.drawable.ic_blankets_quilts_women_fashion_two),
                Category(25, "Fragrances", R.drawable.ic_fragrances_women_fashion_two),
                Category(
                    26,
                    "Salon & Appliances",
                    R.drawable.ic_salon_appliances_women_fashion_two
                ),
                Category(27, "Bedsheets", R.drawable.ic_bedsheets_women_fashion_two),
//                Category(28, "View All", R.drawable.ic_view_all_home_tab),
            )
        }
//
        var selectedCategory by remember { mutableStateOf<Category?>(null) }

        CategoryProducts(
            categories = womenFashionCategoriesTwo,
            onCategorySelected = { category ->
                selectedCategory = category
                println("Selected category: ${category.name}")
            },
            modifier = Modifier.fillMaxWidth(),
            initialSelectedCategory = womenFashionCategoriesTwo.first(),
            itemWidth = 75,
            itemHeight = 65,
            horizontalSpacing = 8,
            verticalSpacing = 8,
            backgroundColor = MaterialTheme.customColors.white
        )

        // Usage example:

        val productsListScroll = listOf(
            ProductListScroll(
                "U.S. Polo Assn.",
                "Preppy Outfits\nMin 30% off\nExtra 10% off*",
                R.drawable.us_polo_preppy_outfits
            ),
            ProductListScroll(
                "New Balance | Asics",
                "Athlete's Faves\nMin 30% off",
                R.drawable.newbalance_asics_athletes_faves
            ),
            ProductListScroll(
                "Rare Rabbit",
                "Refined Staples\nMin 50% off\nExtra 10-15% off*",
                R.drawable.rare_rabbit_mens_refined
            ),
            ProductListScroll(
                "Libas",
                "Festive Ethnics\nMin 40-70% off\nExtra 10% off*",
                R.drawable.libas_festive_ethnics
            ),
            ProductListScroll(
                "Truffle Collection",
                "Stylish Pairs\nUp to 60% off",
                R.drawable.truffle_carlton_stylish_pairs
            ),
            ProductListScroll(
                "Alo | AllSaints",
                "Hot Global Styles\nUp to 70% off\nExtra 10% off",
                R.drawable.alo_allsaints_hot_global
            ),
        )

        // In your Composable function:
        // Usage with different background colors:
        ProductListScroll(
            products = productsListScroll,
            sectionTitle = "Rush Hour Deals",
            showName = true,
            showPrice = true,
            backgroundColor = MaterialTheme.customColors.imageBgColor1 // Light gray background
        )
//        ProductListScroll(
//            products = productsListScroll,
//            sectionTitle = "Featured Products",
//            showName = true,
//            showPrice = true
//        )


//        val myProducts = listOf(
//            Product("U.S. Polo Assn.", "Preppy Outfits\\nMin 30% off\\nExtra 10% off*", R.drawable.us_polo_preppy_outfits),
//            Product("New Balance | Asics", "Athlete's Faves\\nMin 30% off", R.drawable.newbalance_asics_athletes_faves),
//            Product("Rare Rabbit", "Men's Refined Staples\\nMin 50% off\\nExtra 10-15% off*", R.drawable.rare_rabbit_mens_refined),
//            Product("Libas", "Festive Ethnics\\nMin 40-70% off\\nExtra 10% off*", R.drawable.libas_festive_ethnics),
//            Product("Truffle Collection | Carlton London", "Stylish Pairs\\nUp to 60% off", R.drawable.truffle_carlton_stylish_pairs),
//            Product("Alo | AllSaints", "Hot Global Styles\\nUp to 70% off\\nExtra 10% off", R.drawable.alo_allsaints_hot_global),
//        )
//
//        Spacer(
//            modifier = Modifier.height(2.dp)
//                .fillMaxWidth()
//                .background(MaterialTheme.customColors.spacerColor)
//        )
//
//        // Call the reusable component
//        ProductList(
//            products = myProducts,
////                sectionTitle = "Featured Products", // Optional
//            showName = true,  // Show name under image
//            showPrice = true  // Show price under image
//        )
    }
}

@Composable
fun MenFashionPage(
    onTabSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val menFashionCategories = listOf(
        Category(0, "Casual", R.drawable.ic_casual_men),
        Category(1, "Ethnic", R.drawable.ic_ethnic_men),
        Category(2, "Footwear", R.drawable.ic_footwear_men),
        Category(3, "Sports", R.drawable.ic_sports_men),
        Category(4, "Essentials", R.drawable.ic_essentials_men),
        Category(5, "Ad-Ons", R.drawable.ic_ad_ons_men),
        Category(6, "Grooming", R.drawable.ic_grooming_men),
        Category(7, "Boys", R.drawable.ic_boys_men),
    )
    CarouselFashionOne(
        categories = menFashionCategories,
        onTabSelected = onTabSelected,
        modifier = modifier,
        backgroundColor = MaterialTheme.customColors.imageBgColor1,
        itemWidth = 75,
        itemHeight = 75,
        horizontalSpacing = 8
    )
    val bannerImages = listOf(
        painterResource(id = R.drawable.men_fashion_banner1),
        painterResource(id = R.drawable.men_fashion_banner2),
        painterResource(id = R.drawable.men_fashion_banner3),
        painterResource(id = R.drawable.men_fashion_banner4),
        painterResource(id = R.drawable.men_fashion_banner5),
        painterResource(id = R.drawable.men_fashion_banner6),
        painterResource(id = R.drawable.men_fashion_banner7),
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
            height = 500.dp,
            dotSize = 8.dp,
            modifier = Modifier.padding(bottom = 0.dp)
        )

        Spacer(
            modifier = Modifier
                .height(2.dp)
                .fillMaxWidth()
                .background(MaterialTheme.customColors.spacerColor)
        )
//
        val menFashionCategoriesTwo = remember {
            listOf(
                Category(0, "Explore", R.drawable.ic_explore_men_fashion_two),
                Category(1, "Shirts", R.drawable.ic_shirts_men_fashion_two),
                Category(2, "Jeans", R.drawable.ic_jeans_men_fashion_two),
                Category(3, "Gen-Z Drip", R.drawable.ic_gen_z_drip_men_fashion_two),
                Category(4, "Formal Shirts", R.drawable.ic_formal_shirts_men_fashion_two),
                Category(5, "T-Shirts", R.drawable.ic_t_shirts_men_fashion_two),
                Category(6, "Track Pants", R.drawable.ic_track_pants_men_fashion_two),
                Category(7, "Trousers", R.drawable.ic_trousers_men_fashion_two),
                Category(8, "Shorts", R.drawable.ic_shorts_men_fashion_two),
                Category(9, "Blazers & Suits", R.drawable.ic_blazers_suits_men_fashion_two),
                Category(10, "Ethnic  Sets", R.drawable.ic_ethnic_sets_men_fashion_two),
                Category(11, "Kurtas", R.drawable.ic_kurtas_men_fashion_two),
                Category(12, "Kids", R.drawable.ic_kids_men_fashion_two),
                Category(13, "Headphones", R.drawable.ic_headphones_men_fashion_two),
                Category(14, "Watches", R.drawable.ic_watches_men_fashion_two),
                Category(15, "Sunglasses", R.drawable.ic_sunglasses_men_fashion_two),
                Category(16, "Sports Shoes", R.drawable.ic_sports_shoes_men_fashion_two),
                Category(17, "Flip-Flops", R.drawable.ic_flip_flops_men_fashion_two),
                Category(18, "Explore", R.drawable.ic_explore_men_fashion_two2),
                Category(19, "Formal Shoes", R.drawable.ic_formal_shoes_men_fashion_two),
                Category(20, "Perfume & Deos", R.drawable.ic_perfume_deos_men_fashion_two),
                Category(21, "Wallets", R.drawable.ic_wallets_men_fashion_two),
                Category(22, "Belts", R.drawable.ic_belts_men_fashion_two),
                Category(23, "Smart Watches", R.drawable.ic_smart_watches_men_fashion_two),
                Category(24, "Backpacks", R.drawable.ic_backpacks_men_fashion_two),
                Category(25, "Casual Shoes", R.drawable.ic_casual_shoes_men_fashion_two),
                Category(26, "Personal Care", R.drawable.ic_personal_care_men_fashion_two),
                Category(27, "View All", R.drawable.ic_view_all_home_tab),

                )
        }
//
        var selectedCategory by remember { mutableStateOf<Category?>(null) }

        CategoryProducts(
            categories = menFashionCategoriesTwo,
            onCategorySelected = { category ->
                selectedCategory = category
                println("Selected category: ${category.name}")
            },
            modifier = Modifier.fillMaxWidth(),
            initialSelectedCategory = menFashionCategoriesTwo.first(),
            itemWidth = 75,
            itemHeight = 65,
            horizontalSpacing = 8,
            verticalSpacing = 8,
            backgroundColor = MaterialTheme.customColors.white
        )

        val productsListScroll = listOf(
            ProductListScroll(
                "U.S. Polo Assn.",
                "Min 30% off\nExtra 10% off*",
                R.drawable.us_polo_preppy_outfits_min_30_extra_10
            ),
            ProductListScroll(
                "Puma",
                "Min 50% off\nExtra 10% off*",
                R.drawable.newbalance_asics_athletes_faves_min_30
            ),
            ProductListScroll(
                "Tommy Hilfiger",
                "Min 40% off\nExtra 10% off*",
                R.drawable.tommy_hilfiger_faves_min_30
            ),
            ProductListScroll(
                "Rare Rabbit",
                "Min 50% off\nExtra 10-15% off*",
                R.drawable.rare_rabbit_refined_staples_min_50_extra_15
            ),
            ProductListScroll(
                "Calvin Klein",
                "Min 40% off\nExtra 10% off*",
                R.drawable.calvin_klein_premium_min_40
            ),
            ProductListScroll(
                "Adidas",
                "Min 50% off\nExtra 10% off*",
                R.drawable.adidas_sportswear_min_50
            ),
            ProductListScroll(
                "GAP",
                "Min 40% off\nExtra 10% off*",
                R.drawable.gap_casual_wear_min_40
            ),
            ProductListScroll(
                "KISAH",
                "Min 40% off\nExtra 10% off*",
                R.drawable.kisah_ethnic_min_40
            ),
            ProductListScroll(
                "SNITCH",
                "Up to 50% off\nExtra 10% off*",
                R.drawable.snitch_urban_up_to_50
            ),
            ProductListScroll(
                "Asics",
                "Min 40% off\nExtra 10% off*",
                R.drawable.asics_athletic_min_40
            )
        )

        // In your Composable function:
        // Usage with different background colors:
        ProductListScroll(
            products = productsListScroll,
            sectionTitle = "Rush Hour Deals",
            showName = true,
            showPrice = true,
            backgroundColor = Color(0xFFF8E8AE)
            // Light gray background
//            backgroundColor = MaterialTheme.customColors.imageBgColor1 // Light gray background
        )
    }
}

@Composable
fun KidsFashionPage(
    onTabSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val kidsFashionCategories = listOf(
        Category(0, "Girls", R.drawable.ic_girls_kids),
        Category(1, "Boys", R.drawable.ic_boys_kids),
        Category(2, "Infants", R.drawable.ic_infants_kids),
        Category(3, "Teens", R.drawable.ic_teens_kids),
        Category(4, "Ad-Ons", R.drawable.ic_ad_ons_kids),
    )
    CarouselFashionOne(
        categories = kidsFashionCategories,
        onTabSelected = onTabSelected,
        modifier = modifier,
        backgroundColor = MaterialTheme.customColors.imageBgColor1,
        itemWidth = 75,
        itemHeight = 75,
        horizontalSpacing = 8
    )

    // With selection handling
    KidsFilter { selectedRange ->
        println("Selected age range: $selectedRange")
        // Handle the selection - update state, filter data, etc.
    }

    val bannerImages = listOf(
        painterResource(id = R.drawable.kids_fashion_banner1),
        painterResource(id = R.drawable.kids_fashion_banner2),
        painterResource(id = R.drawable.kids_fashion_banner3),
        painterResource(id = R.drawable.kids_fashion_banner4),
        painterResource(id = R.drawable.kids_fashion_banner5),
        painterResource(id = R.drawable.kids_fashion_banner6),
        painterResource(id = R.drawable.kids_fashion_banner7),
        painterResource(id = R.drawable.kids_fashion_banner8),
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
            height = 500.dp,
            dotSize = 8.dp,
            modifier = Modifier.padding(bottom = 0.dp)
        )


        Spacer(
            modifier = Modifier
                .height(2.dp)
                .fillMaxWidth()
                .background(MaterialTheme.customColors.spacerColor)
        )
//
        val kidsFashionCategoriesTwo = remember {
            listOf(
                Category(0, "Dresses", R.drawable.ic_dresses_kids_fashion_two),
                Category(1, "Shorts", R.drawable.ic_shorts_kids_fashion_two),
                Category(2, "Watches", R.drawable.ic_watches_kids_fashion_two),
                Category(3, "Backpacks", R.drawable.ic_backpacks_kids_fashion_two),
                Category(4, "Accessories", R.drawable.ic_accessories_kids_fashion_two),
                Category(5, "Troleys", R.drawable.ic_troleys_kids_fashion_two),
                Category(6, "Hair Accessories", R.drawable.ic_hair_accessories_kids_fashion_two),
                Category(7, "Casual Shoes", R.drawable.ic_casual_shoes_kids_fashion_two),
                Category(8, "Sunglasses", R.drawable.ic_sunglasses_kids_fashion_two),
                Category(9, "Innerwear", R.drawable.ic_innerwear_kids_fashion_two),
                Category(10, "Value Packs", R.drawable.ic_value_packs_kids_fashion_two),
                Category(11, "Night Suits", R.drawable.ic_night_suits_kids_fashion_two),
                Category(12, "Tops", R.drawable.ic_tops_kids_fashion_two),
                Category(13, "Tops wear", R.drawable.ic_tops_wear_kids_fashion_two),
                Category(14, "Ethnic Wear", R.drawable.ic_ethnic_wear_kids_fashion_two),
                Category(15, "Bottom wear", R.drawable.ic_bottom_wear_kids_fashion_two),
                Category(16, "Dresses wear", R.drawable.ic_dresses_wear_kids_fashion_two),
                Category(17, "Innerwear Kids", R.drawable.ic_innerwear_kids_kids_fashion_two),
                Category(18, "Footwear", R.drawable.ic_footwear_kids_fashion_two),
                Category(19, "Shorts", R.drawable.ic_shorts_deos_kids_fashion_two),
//                Category(20, ""View All", R.drawable.ic_view_all_home_tab),

            )
        }
        var selectedCategory by remember { mutableStateOf<Category?>(null) }

        CategoryProducts(
            categories = kidsFashionCategoriesTwo,
            onCategorySelected = { category ->
                selectedCategory = category
                println("Selected category: ${category.name}")
            },
            modifier = Modifier.fillMaxWidth(),
            initialSelectedCategory = kidsFashionCategoriesTwo.first(),
            itemWidth = 75,
            itemHeight = 65,
            horizontalSpacing = 8,
            verticalSpacing = 8,
            backgroundColor = MaterialTheme.customColors.white
        )

        val productsListScroll = listOf(
            ProductListScroll(
                name = "Saiwai Suits & Sets",
                price = "Up to 70% off",
                imageRes = R.drawable.saiwai_suits_sets_kids
            ),
            ProductListScroll(
                name = "Boss Kurta Set",
                price = "Up to 70% off",
                imageRes = R.drawable.boss_kurta_set_kids
            ),
            ProductListScroll(
                name = "Lehenga Set",
                price = "Up to 70% off",
                imageRes = R.drawable.lehenga_set_kids
            ),
            ProductListScroll(
                name = "Sherwani & Ethnic Jackets",
                price = "Up to 60% off",
                imageRes = R.drawable.sherwani_jackets_kids
            ),
            ProductListScroll(
                name = "Fusionwear",
                price = "Up to 60% off",
                imageRes = R.drawable.fusionwear_kids
            ),
            ProductListScroll(
                name = "South Indian Trends",
                price = "Up to 60% off",
                imageRes = R.drawable.south_indian_trends_kids
            ),
            ProductListScroll(
                name = "Ethnic Dresses",
                price = "Up to 60% off",
                imageRes = R.drawable.ethnic_dresses_kids
            ),
            ProductListScroll(
                name = "Ethnic Footwear",
                price = "Up to 55% off",
                imageRes = R.drawable.ethnic_footwear_kids
            ),
            ProductListScroll(
                name = "Pre-draped Saree",
                price = "Up to 60% off",
                imageRes = R.drawable.predraped_saree_kids
            )
        )

        // In your Composable function:
        // Usage with different background colors:
        ProductListScroll(
            products = productsListScroll,
            sectionTitle = "Rush Hour Deals",
            showName = true,
            showPrice = true,
            backgroundColor = Color(0xFFF8E8AE)
            // Light gray background
//            backgroundColor = MaterialTheme.customColors.imageBgColor1 // Light gray background
        )
    }
}

@Composable
fun HomeFashionPage(
    onTabSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val kidsFashionCategories = listOf(
        Category(0, "Decor", R.drawable.ic_decor_home_fashion),
        Category(1, "Bedding", R.drawable.ic_bedding_home_fashion),
        Category(2, "Kitchen", R.drawable.ic_kitchen_dining_home_fashion),
        Category(3, "Gifts", R.drawable.ic_thoughtful_gifts_home_fashion),
        Category(4, "Appliances", R.drawable.ic_appliances_home_fashion),
        Category(5, "Bath", R.drawable.ic_bath_home_fashion),
        Category(6, "Storage", R.drawable.ic_storage_home_fashion),
        Category(7, "Furniture", R.drawable.ic_accent_furniture_home_fashion),
        Category(8, "Cushions", R.drawable.ic_cushions_home_fashion),
        Category(9, "Global Store", R.drawable.ic_global_store_home_fashion),
        Category(10, "Gems", R.drawable.ic_hidden_gems_home_fashion),
        Category(11, "Luxe", R.drawable.ic_luxe_home_fashion),
    )
    CarouselFashionOne(
        categories = kidsFashionCategories,
        onTabSelected = onTabSelected,
        modifier = modifier,
        backgroundColor = MaterialTheme.customColors.imageBgColor1,
        itemWidth = 75,
        itemHeight = 75,
        horizontalSpacing = 8
    )

    // With selection handling
//    KidsFilter { selectedRange ->
//        println("Selected age range: $selectedRange")
//        // Handle the selection - update state, filter data, etc.
//    }

    val bannerImages = listOf(
        painterResource(id = R.drawable.home_fashion_banner1),
        painterResource(id = R.drawable.home_fashion_banner2),
        painterResource(id = R.drawable.home_fashion_banner3),
        painterResource(id = R.drawable.home_fashion_banner4),
        painterResource(id = R.drawable.home_fashion_banner5),
        painterResource(id = R.drawable.home_fashion_banner6),
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
            height = 470.dp,
            dotSize = 8.dp,
            modifier = Modifier.padding(bottom = 0.dp)
        )

        val homeFashionCategoriesTwo = remember {
            listOf(
                Category(0, "Dinnerware", R.drawable.ic_dinnerware_home_fashion_two),
                Category(1, "Cookware", R.drawable.ic_cookware_home_fashion_two),
                Category(2, "Cups & Mugs", R.drawable.ic_cups_mugs_home_fashion_two),
                Category(3, "Appliances", R.drawable.ic_appliances_home_fashion_two),
                Category(4, "Water Bottles", R.drawable.ic_water_bottles_home_fashion_two),
                Category(5, "Storage", R.drawable.ic_storage_home_fashion_two),
                Category(6, "Curtain", R.drawable.ic_curtain_accessories_home_fashion_two),
                Category(7, "Showpieces", R.drawable.ic_showpieces_shoes_home_fashion_two),
                Category(8, "Sofa Cover", R.drawable.ic_sofa_cover_home_fashion_two),
                Category(9, "Wall Art", R.drawable.ic_wall_art_home_fashion_two),
                Category(10, "Clocks", R.drawable.ic_clocks_home_fashion_two),
                Category(11, "Plants", R.drawable.ic_plants_home_fashion_two),
                Category(12, "BedSheets", R.drawable.ic_bed_sheets_home_fashion_two),
                Category(13, "Blankets", R.drawable.ic_blankets_home_fashion_two),
                Category(14, "Cushion", R.drawable.ic_cushion_home_fashion_two),
                Category(15, "Towels", R.drawable.ic_towels_home_fashion_two),
                Category(16, "Bath Robes", R.drawable.ic_bath_robes_home_fashion_two),
                Category(
                    17,
                    "Bathroom Accessories",
                    R.drawable.ic_bathroom_accessories_home_kids_fashion_two
                ),
                //Category(20, ""View All", R.drawable.ic_view_all_home_tab),
            )
        }
//
        var selectedCategory by remember { mutableStateOf<Category?>(null) }

        CategoryProducts(
            categories = homeFashionCategoriesTwo,
            onCategorySelected = { category ->
                selectedCategory = category
                println("Selected category: ${category.name}")
            },
            modifier = Modifier.fillMaxWidth(),
            initialSelectedCategory = homeFashionCategoriesTwo.first(),
            itemWidth = 75,
            itemHeight = 65,
            horizontalSpacing = 8,
            verticalSpacing = 8,
            backgroundColor = MaterialTheme.customColors.white
        )

        val productsListScroll = listOf(
            ProductListScroll(
                name = "Stunning Cutlery",
                price = "Up to 55% off",
                imageRes = R.drawable.stunning_cutlery_sets_kids
            ),
            ProductListScroll(
                name = "Comfy Decor Picks",
                price = "Up to 80% off",
                imageRes = R.drawable.comfy_decor_picks_set_kids
            ),
            ProductListScroll(
                name = "Accent Furniture",
                price = "Up to 60% off",
                imageRes = R.drawable.accent_furniture_set_kids
            ),
            ProductListScroll(
                name = "Home Texttiles",
                price = "Up to 70% off",
                imageRes = R.drawable.home_texttiles_set_kids
            ),
            ProductListScroll(
                name = "Rustic Bedding",
                price = "Up to 50% off",
                imageRes = R.drawable.rustic_bedding_set_kids
            ),
            ProductListScroll(
                name = "Classy Barware",
                price = "Up to 50% off",
                imageRes = R.drawable.classy_barware_set_kids
            ),
            ProductListScroll(
                name = "Homeware",
                price = "Up to 60% off",
                imageRes = R.drawable.homeware_set_kids
            ),
            ProductListScroll(
                name = "Standout Decor",
                price = "Up to 10% off",
                imageRes = R.drawable.standout_decor_set_kids
            ),
            ProductListScroll(
                name = "Stylish Battles",
                price = "Min 45% off",
                imageRes = R.drawable.stylish_battles_set_kids
            )
        )

        // In your Composable function:
        // Usage with different background colors:
        ProductListScroll(
            products = productsListScroll,
            sectionTitle = "Rush Hour Deals",
            showName = true,
            showPrice = true,
            backgroundColor = Color(0xFFF8E8AE)
            // Light gray background
//            backgroundColor = MaterialTheme.customColors.imageBgColor1 // Light gray background
        )
    }
}

@Composable
fun LuxeFashionPage(
    onTabSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val luxeFashionCategories = listOf(
        Category(0, "Men", R.drawable.ic_luxe_men_fashion), // Using existing icon as placeholder
        Category(1, "Women", R.drawable.ic_luxe_women_fashion),
        Category(2, "Watches", R.drawable.ic_luxe_watches_fashion),
        Category(3, "Fragrances", R.drawable.ic_luxe_fragrances_fashion),
        Category(4, "Handbags", R.drawable.ic_luxe_handbags_fashion),
        Category(5, "Beauty", R.drawable.ic_luxe_beauty_fashion),
        Category(6, "Footwear", R.drawable.ic_luxe_footwear_fashion),
        Category(7, "MLI", R.drawable.ic_luxe_mli_fashion),
        Category(8, "Jewellery", R.drawable.ic_luxe_jewellery_fashion),
        Category(9, "Eyewear", R.drawable.ic_luxe_eyewear_fashion),
        Category(10, "Kids", R.drawable.ic_luxe_kids_fashion),
        Category(10, "Accessories", R.drawable.ic_luxe_accessories_fashion),
        Category(11, "A-Z Brands", R.drawable.ic_luxe_a_z_brands_fashion),
    )
    CarouselFashionOne(
        categories = luxeFashionCategories,
        onTabSelected = onTabSelected,
        modifier = modifier,
        backgroundColor = MaterialTheme.customColors.imageBgColor1,
        itemWidth = 75,
        itemHeight = 75,
        horizontalSpacing = 8
    )

    val bannerImages = listOf(
        painterResource(id = R.drawable.luxe_fashion_banner1), // Using existing banner as placeholder
        painterResource(id = R.drawable.luxe_fashion_banner2),
        painterResource(id = R.drawable.luxe_fashion_banner3),
        painterResource(id = R.drawable.luxe_fashion_banner4),
        painterResource(id = R.drawable.luxe_fashion_banner5),
        painterResource(id = R.drawable.luxe_fashion_banner6),
        painterResource(id = R.drawable.luxe_fashion_banner7),
        painterResource(id = R.drawable.luxe_fashion_banner8),
        painterResource(id = R.drawable.luxe_fashion_banner9),
        painterResource(id = R.drawable.luxe_fashion_banner10),
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
            height = 470.dp,
            dotSize = 8.dp,
            modifier = Modifier.padding(bottom = 0.dp)
        )

        val luxeFashionCategoriesTwo = remember {
            listOf(
                Category(0, "Fragrances", R.drawable.ic_fragrances_fashion_luxe_two),
                Category(1, "Lipstick", R.drawable.ic_lipstick_fashion_luxe_two),
                Category(2, "Handbags", R.drawable.ic_handbags_fashion_luxe_two),
                Category(3, "Footwear", R.drawable.ic_footwear_fashion_luxe_two),
                Category(4, "Eyewear", R.drawable.ic_eyewear_fashion_luxe_two),
                Category(5, "Skincare", R.drawable.ic_skincare_fashion_luxe_two),
                Category(6, "Haircare", R.drawable.ic_haircare_fashion_luxe_two),
                Category(7, "Watches", R.drawable.ic_watches_fashion_luxe_two),
                Category(8, "Makeup", R.drawable.ic_makeup_fashion_luxe_two),
                Category(9, "Shop All", R.drawable.ic_view_all_home_tab),
//                Category(9, "Shop All", R.drawable.ic_shop_all_fashion_luxe_two),
            )
        }

        var selectedCategory by remember { mutableStateOf<Category?>(null) }

        CategoryProducts(
            categories = luxeFashionCategoriesTwo,
            onCategorySelected = { category ->
                selectedCategory = category
                println("Selected category: ${category.name}")
            },
            modifier = Modifier.fillMaxWidth(),
            initialSelectedCategory = luxeFashionCategoriesTwo.first(),
            itemWidth = 75,
            itemHeight = 65,
            horizontalSpacing = 8,
            verticalSpacing = 8,
            backgroundColor = MaterialTheme.customColors.white
        )
    }
}

@Composable
fun BrandsFashionPage(
    onTabSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val luxeFashionCategories = listOf(
        Category(
            0,
            "Men",
            R.drawable.ic_luxe_men_fashion_brands
        ), // Using existing icon as placeholder
        Category(1, "Women", R.drawable.ic_luxe_women_fashion_brands),
        Category(2, "Kids", R.drawable.ic_luxe_kids_fashion_brands),
        Category(3, "Home", R.drawable.ic_luxe_home_fashion_brands),
        Category(4, "Footwear", R.drawable.ic_luxe_footwear_fashion_brands),
    )
    CarouselFashionOne(
        categories = luxeFashionCategories,
        onTabSelected = onTabSelected,
        modifier = modifier,
        backgroundColor = MaterialTheme.customColors.imageBgColor1,
        itemWidth = 75,
        itemHeight = 75,
        horizontalSpacing = 8
    )

    val bannerImages = listOf(
        painterResource(id = R.drawable.brands_fashion_banner4),
        painterResource(id = R.drawable.brands_fashion_banner5),
        painterResource(id = R.drawable.brands_fashion_banner1), // Using existing banner as placeholder
        painterResource(id = R.drawable.brands_fashion_banner2),
        painterResource(id = R.drawable.brands_fashion_banner3)
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
            height = 470.dp,
            dotSize = 8.dp,
            modifier = Modifier.padding(bottom = 0.dp)
        )

        val brandsFashionCategoriesTwo = remember {
            listOf(
                // Ethnic & Fusion Wear
                Category(0, "Libas", R.drawable.ic_libas_fashion_brands_two),
                Category(1, "Lihas", R.drawable.ic_lihas_fashion_brands_two),
                Category(2, "Autumn Lane", R.drawable.ic_autumn_lane_fashion_brands_two),

                // GenZ & Youth Styles
                Category(3, "Cider", R.drawable.ic_cider_fashion_brands_two),
                Category(4, "Y Wenty Dresses", R.drawable.ic_y_wenty_dresses_fashion_brands_two),

                // American Classics
                Category(5, "US Polo Assn", R.drawable.ic_us_polo_assn_fashion_brands_two),
                Category(6, "Buck 1800", R.drawable.ic_buck_1800_fashion_brands_two),
                Category(7, "Tommy Hilfiger", R.drawable.ic_tommy_hilfiger_fashion_brands_two),

                // Minimalistic & Modern
                Category(8, "Calvin Klein", R.drawable.ic_calvin_klein_fashion_brands_two),

                // Formal & Business Wear
                Category(9, "Allen Solly", R.drawable.ic_allen_solly_fashion_brands_two),
                Category(10, "Van Heusen", R.drawable.ic_van_heusen_fashion_brands_two),

                // Denim & Casual
                Category(11, "Nurcay", R.drawable.ic_nurcay_fashion_brands_two),

                // Footwear & Accessories
                Category(12, "Aldo", R.drawable.ic_aldo_fashion_brands_two),
                Category(13, "Slacklets", R.drawable.ic_slacklets_fashion_brands_two),

                // Activewear & Performance
                Category(14, "Dumm", R.drawable.ic_dumm_fashion_brands_two),

                // Feminine Fashion & Lingerie
                Category(15, "Veromoda", R.drawable.ic_veromoda_fashion_brands_two),
                Category(16, "Bry/Nyraa", R.drawable.ic_bry_nyraa_fashion_brands_two),

                // Occasion Wear
                Category(17, "Forever New", R.drawable.ic_forever_new_fashion_brands_two)
            )
        }

        var selectedCategory by remember { mutableStateOf<Category?>(null) }

        CategoryProducts(
            categories = brandsFashionCategoriesTwo,
            onCategorySelected = { category ->
                selectedCategory = category
                println("Selected category: ${category.name}")
            },
            modifier = Modifier.fillMaxWidth(),
            initialSelectedCategory = brandsFashionCategoriesTwo.first(),
            itemWidth = 75,
            itemHeight = 65,
            horizontalSpacing = 8,
            verticalSpacing = 8,
            backgroundColor = MaterialTheme.customColors.white
        )
    }
}

@Composable
fun CategoriesFashionPage() {
//
}

@Composable
fun FashionMainScreen(navController: NavHostController) {
    var currentPage by remember { mutableIntStateOf(0) }

    Column(modifier = Modifier.fillMaxWidth()) {
        FashionTab(
            navController = navController, // ✅ Pass navController here
            onCategorySelected = { fashionCategoryPage ->
                currentPage = when (fashionCategoryPage) {
                    is FashionCategoryPage.Women -> 0
                    is FashionCategoryPage.Men -> 1
                    is FashionCategoryPage.Kids -> 2
                    is FashionCategoryPage.Home -> 3
                    is FashionCategoryPage.Luxe -> 4
                    is FashionCategoryPage.Brands -> 5
                    is FashionCategoryPage.Categories -> 6
                }
            },
            onOpenFashionCategory = {
                navController.navigate("fashion_categories")
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