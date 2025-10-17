package com.example.qrakon.components.fashion.fashiontab

import InfoCategoriesF
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.qrakon.R
import com.example.qrakon.ui.theme.customColors

@Composable
fun CategoriesFTab(
    onCategorySelected: (FashionCategoryTabPage) -> Unit = {}
) {
    var selectedTabIndex by remember { mutableIntStateOf(0) }

    val tabs = listOf(
        "Women" to FashionCategoryTabPage.Women,
        "Men" to FashionCategoryTabPage.Men,
        "Kids" to FashionCategoryTabPage.Kids,
        "Home" to FashionCategoryTabPage.Home
    )

    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.customColors.skyBlue),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            tabs.forEachIndexed { index, (title, page) ->
                val isSelected = selectedTabIndex == index

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 4.dp)
//                        .padding(horizontal = 4.dp, vertical = 6.dp)
                        .clickable {
                            selectedTabIndex = index
                            onCategorySelected(page)
                        },
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // ✅ Extract theme colors *before* drawBehind
                    // Extract colors in composable scope
                    MaterialTheme.customColors.skyBlue
                    if (isSelected) MaterialTheme.customColors.gray else Color.Transparent
                    if (isSelected) MaterialTheme.customColors.onPrimaryContainer else Color.Transparent

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(
                                RoundedCornerShape(
                                    topStart = 15.dp,
                                    topEnd = 15.dp
                                )
                            ) // Rounded top corners
//                            .background(backgroundColor) // Background respects clip
                            .drawBehind {
                                if (isSelected) 2.dp.toPx() else 1.dp.toPx()
                                size.width
                                size.height

//                                // Top border (optional)
//                                drawLine(
//                                    color = borderColor,
//                                    start = androidx.compose.ui.geometry.Offset(0f, strokeWidth / 2),
//                                    end = androidx.compose.ui.geometry.Offset(width, strokeWidth / 2),
//                                    strokeWidth = strokeWidth
//                                )
//
//                                // Left border
//                                drawLine(
//                                    color = borderColor,
//                                    start = androidx.compose.ui.geometry.Offset(strokeWidth / 2, 0f),
//                                    end = androidx.compose.ui.geometry.Offset(strokeWidth / 2, height),
//                                    strokeWidth = strokeWidth
//                                )
//
//                                // Right border
//                                drawLine(
//                                    color = borderColor,
//                                    start = androidx.compose.ui.geometry.Offset(width - strokeWidth / 2, 0f),
//                                    end = androidx.compose.ui.geometry.Offset(width - strokeWidth / 2, height),
//                                    strokeWidth = strokeWidth
//                                )
//
//                                // Bottom border (active highlight)
//                                drawLine(
//                                    color = bottomLineColor,
//                                    start = androidx.compose.ui.geometry.Offset(0f, height - strokeWidth / 2),
//                                    end = androidx.compose.ui.geometry.Offset(width, height - strokeWidth / 2),
//                                    strokeWidth = strokeWidth
//                                )
                            }
                            .padding(vertical = 8.dp),
                        contentAlignment = Alignment.Center
                    )

                    {
                        Text(
                            text = title,
                            fontSize = 15.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                            color = if (isSelected)
                                MaterialTheme.customColors.onPrimaryContainer
                            else
                                MaterialTheme.customColors.black,
                            textAlign = TextAlign.Center,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }

                    // ✅ Bottom border line (active indicator)
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(3.dp)
                            .background(
                                if (isSelected)
                                    MaterialTheme.customColors.onPrimaryContainer
                                else
                                    Color.Transparent
                            )
                    )
                }
            }
        }

        // ✅ Show tab content
        Box(modifier = Modifier.fillMaxWidth()) {
            when (selectedTabIndex) {
                0 -> WomenCategoriesFashionPage(onTabSelected = { println("Women: $it") })
                1 -> MenCategoriesFashionPage(onTabSelected = { println("Men: $it") })
                2 -> KidsCategoriesFashionPage(onTabSelected = { println("Kids: $it") })
                3 -> HomeCategoriesFashionPage(onTabSelected = { println("Home: $it") })
            }
        }
    }
}


@Composable
fun WomenCategoriesFashionPage(onTabSelected: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        InfoCategoriesF()
        val womenCategoriesF = listOf(
            "Indianwear" to R.drawable.ic_indianwear,
            "Westernwear" to R.drawable.ic_westernwear,
            "Footwear" to R.drawable.ic_footwear,
            "Lingerie" to R.drawable.ic_lingerie,
            "Bags" to R.drawable.ic_bags,
            "Jewellery" to R.drawable.ic_jewellery,
            "Active & Sports" to R.drawable.ic_active_sports,
            "Watches" to R.drawable.ic_watches,
            "Sleep & Lounge" to R.drawable.ic_sleep_lounge,
            "Accessories" to R.drawable.ic_accessories,
            "Maternity Wear" to R.drawable.ic_maternity_wear,
            "Tech Accessories" to R.drawable.ic_tech_accessories,
            "Sports & Fitness" to R.drawable.ic_sports_fitness,
        )

        SubCategoryFList(
            title = "Shop by Category",
            categories = womenCategoriesF,
            onCategoryClick = { categoryName ->
                // handle click
            }
        )

        BrandsSectionF(
            brandsTitle = "Shop Stylish Brands",
            brandsImageRes = R.drawable.ic_shop_stylish_brands,
            onBrandsClick = {
                // Handle brands click
            }
        )
//        SubCategoryFList(
//            title = "Shop by Category",
//            categories = womenCategoriesF,
//            showBrandsSection = true,
//            brandsImageRes = R.drawable.ic_shop_stylish_brands,
//            brandsTitle = "Shop Stylish Brands",
//            onCategoryClick = { categoryName ->
//                // Handle category click
//            },
//            onBrandsClick = {
//                // Handle brands click
//            }
//        )
        val womenStores = listOf(
            StoreItem(
                name = "Global Store",
                imageRes = R.drawable.ic_global_store,
                description = "Influencer approved global brands handpicked for you"
            ),
            StoreItem(
                name = "Hidden Gems",
                imageRes = R.drawable.ic_hidden_gems,
                description = "Our selection of 300+ most sought after homegrown labels"
            ),
            StoreItem(
                name = "Luxe Edit",
                imageRes = R.drawable.ic_luxe_edit,
                description = "Our collection of 250+ designers rooted in traditional sophistication"
            ),
            StoreItem(
                name = "Genz",
                imageRes = R.drawable.ic_genz,
                description = "30,000+ curated Gen-Z approved styles to slay in"
            ),
            StoreItem(
                name = "House of Nykaa",
                imageRes = R.drawable.ic_house_of_nykaa,
                description = "Unique brands and curated styles; Made for you, by us"
            ),
            StoreItem(
                name = "Revolve",
                imageRes = R.drawable.ic_revolve,
                description = "Hottest celebrity styles from LA exclusively on Nykaa Fashion"
            )
        )

        StoresFList(
            title = "Shop by Category",
            stores = womenStores,
            onStoreClick = { storeName ->
                // Handle store click
                when (storeName) {
                    "Global Store" -> { /* Navigate to Global Store */ }
                    "Hidden Gems" -> { /* Navigate to Hidden Gems */ }
                    "Luxe Edit" -> { /* Navigate to Luxe Edit */ }
                    "Genz" -> { /* Navigate to Genz */ }
                    "House of Nykaa" -> { /* Navigate to House of Nykaa */ }
                    "Revolve" -> { /* Navigate to Revolve */ }
                }
            }
        )

        val moreFromQrakon = listOf(
            StoreItem(
                name = "Style Advice",
                imageRes = R.drawable.ic_style_advice, // You'll need to create this drawable
                description = "Chat with a Nykaa Personal Stylist"
            ),
            StoreItem(
                name = "Retail Stores",
                imageRes = R.drawable.ic_retail_stores, // You'll need to create this drawable
                description = "Shop from our stores in your city"
            )
        )

        StoresFList(
            title = "More from Qrakon",
            stores = moreFromQrakon,
            onStoreClick = { storeName ->
                // Handle store click
                when (storeName) {
                    "Style Advice" -> {
                        /* Navigate to Style Advice/Chat with Stylist */
                    }
                    "Retail Stores" -> {
                        /* Navigate to Retail Stores locator */
                    }
                }
            }
        )
//        WomenCategoryStores()
    }
}

@Composable
fun MenCategoriesFashionPage(onTabSelected: (String) -> Unit) {

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        InfoCategoriesF()

        val menCategoriesF = listOf(
            "Topwear" to R.drawable.ic_topwear_men_scf,
            "Bottomwear" to R.drawable.ic_bottomwear_men_scf,
            "Ethnicwear" to R.drawable.ic_ethnicwear_men_scf,
            "Footwear" to R.drawable.ic_footwear_men_scf,
            "Active & Sports" to R.drawable.ic_active_sports_men_scf,
            "Accessories" to R.drawable.ic_accessories_men_scf,
            "Innerwear & Sleepwear" to R.drawable.ic_innerwear_sleepwear_men_scf,
            "Watches" to R.drawable.ic_watches_men_scf,
            "Bags & Backpacks" to R.drawable.ic_bags_backpacks_men_scf,
            "Tech Accessories" to R.drawable.ic_tech_accessories_men_scf,
            "Sports & Fitness" to R.drawable.ic_sports_fitness_men_scf
        )

        SubCategoryFList(
            title = "Shop by Category",
            categories = menCategoriesF,
            onCategoryClick = { categoryName ->
                // handle click
            }
        )

        BrandsSectionF(
            brandsTitle = "Shop Stylish Brands",
            brandsImageRes = R.drawable.ic_men_categories_f_stylish_brands,
            onBrandsClick = {
                // Handle brands click
            }
        )
//        SubCategoryFList(
//            title = "Shop by Category",
//            categories = menCategoriesF,
//            showBrandsSection = true,
//            brandsImageRes = R.drawable.ic_men_categories_f_stylish_brands,
//            brandsTitle = "Shop Stylish Brands",
//            onCategoryClick = { categoryName ->
//                // Handle category click
//            },
//            onBrandsClick = {
//                // Handle brands click
//            }
//        )

            val menStores = listOf(
                StoreItem(
                    name = "Global Store",
                    imageRes = R.drawable.ic_global_store_men,
                    description = "Influencer approved global brands handpicked for you"
                ),
                StoreItem(
                    name = "Hidden Gems",
                    imageRes = R.drawable.ic_hidden_gems_men,
                    description = "Our selection of 300+ most sought after homegrown labels"
                ),
                StoreItem(
                    name = "Luxe Edit",
                    imageRes = R.drawable.ic_luxe_edit_men,
                    description = "Our collection of 250+ Indian designers rooted in traditional sophistication"
                ),
                StoreItem(
                    name = "Genz",
                    imageRes = R.drawable.ic_genz_men,
                    description = "30,000+ Gen-Z approved styles to slay in"
                )
            )

            StoresFList(
                title = "Curated Style Stores",
                stores = menStores,
                onStoreClick = { storeName ->
                    // Handle store click
                    when (storeName) {
                        "Global Store" -> { /* Navigate to Global Store */ }
                        "Hidden Gems" -> { /* Navigate to Hidden Gems */ }
                        "Luxe Edit" -> { /* Navigate to Luxe Edit */ }
                        "Genz" -> { /* Navigate to Genz */ }
                    }
                }
            )
    }
}

@Composable
fun KidsCategoriesFashionPage(onTabSelected: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        InfoCategoriesF()


        val infantCategories = listOf(
            ListItemSquare(0, "Shop All", R.drawable.ic_kids_view_all_home_tab),
            ListItemSquare(1, "Infant Clothing", R.drawable.ic_infants_kids2, ""),
            ListItemSquare(2, "Nursery", R.drawable.ic_nursery_kids, ""),
            ListItemSquare(3, "Feeding & Meal", R.drawable.ic_feeding_n_meal_time_kids, ""),
            ListItemSquare(4, "Diapering", R.drawable.ic_diapering_kids, ""),
            ListItemSquare(5, "Baby Gear", R.drawable.ic_baby_gear_kids, ""),
            ListItemSquare(6, "Baby Toys", R.drawable.ic_baby_first_toys_kids, ""),
            ListItemSquare(7, "Bath Time", R.drawable.ic_bath_time_kids, ""),
            ListItemSquare(8, "Gift sets", R.drawable.ic_gift_sets_kids, ""),
        )


        ListItemSquareSection(
            heading = "Shop for Infants",
            subheading = "Westernwear, Indianwear & much more",
            items = infantCategories,
            onItemClick = { item ->
                println("Selected: ${item.name}")
            },
//            modifier = modifier
        )

        val girlsCategories = listOf(
            ListItemSquare(0, "Shop All", R.drawable.ic_girls_shop_all, ""),
            ListItemSquare(1, "Toddler", R.drawable.ic_girls_toddler, "2–4 years"),
            ListItemSquare(2, "Child", R.drawable.ic_girls_child, "4–8 years"),
            ListItemSquare(3, "Tween", R.drawable.ic_girls_tween, "8–12 years"),
            ListItemSquare(4, "Teen", R.drawable.ic_girls_teen, "12–16 years"),
        )


        ListItemSquareSection(
            heading = "Shop for Girls",
            subheading = "Westernwear, Indianwear & much more",
            items = girlsCategories,
            onItemClick = { item ->
                println("Selected: ${item.name}")
            }
        )


        // ✅ Merged Boys Categories (Unique + Ordered)
        val boysCategories = listOf(
            ListItemSquare(0, "Shop All", R.drawable.ic_boys_shop_all),
            ListItemSquare(1, "Toddler", R.drawable.ic_boys_toddler, "2-4 years"),
            ListItemSquare(2, "Child", R.drawable.ic_boys_child, "4-8 years"),
            ListItemSquare(3, "Tween", R.drawable.ic_boys_tween, "8-12 years"),
            ListItemSquare(4, "Teen", R.drawable.ic_boys_teen, "12-16 years")
        )

// ✅ Single Section Call
        ListItemSquareSection(
            heading = "Shop for Boys",
            subheading = "Westernwear, Indianwear & much more",
            items = boysCategories,
            onItemClick = { item ->
                println("Selected: ${item.name}")
            }
        )


        val kidsCategoriesF = listOf(
            "Infant" to R.drawable.ic_infant_kids,
            "Westernwear" to R.drawable.ic_westernwear_kids,
            "Indianwear" to R.drawable.ic_indianwear_kids,
            "Winterwear" to R.drawable.ic_winterwear_kids,
            "Footwear" to R.drawable.ic_footwear_kids,
            "Sports & Athleisure" to R.drawable.ic_sports_athleisure_kids,
            "Kids Accessories" to R.drawable.ic_kids_accessories,
            "Toys & Playtime" to R.drawable.ic_toys_playtime_kids,
            "Home" to R.drawable.ic_home_kids,
            "Sports & Fitness" to R.drawable.ic_sports_fitness_kids,
            "School Must Haves" to R.drawable.ic_school_must_haves_kids,
            "Innerwear" to R.drawable.ic_innerwear_kids,
            "Sleepwear" to R.drawable.ic_sleepwear_kids
        )

        SubCategoryFList(
            title = "Shop by Category",
            categories = kidsCategoriesF,
            onCategoryClick = { categoryName ->
                // handle click
            }
        )

        BrandsSectionF(
            brandsTitle = "Shop Stylish Brands",
            brandsImageRes = R.drawable.ic_kids_categories_f_stylish_brands,
            onBrandsClick = {
                // Handle brands click
            }
        )

//        SubCategoryFList(
//            title = "Shop by Category",
//            categories = kidsCategoriesF,
//            showBrandsSection = true,
//            brandsImageRes = R.drawable.ic_kids_categories_f_stylish_brands, // Changed to kids variant
//            brandsTitle = "Shop Stylish Brands",
//            onCategoryClick = { categoryName ->
//                // Handle category click
//                println("Selected category: $categoryName")
//            },
//            onBrandsClick = {
//                // Handle brands click
//                println("Brands section clicked")
//            }
//        )

        val curatedStores = listOf(
            StoreItem(
                name = "Global Store",
                imageRes = R.drawable.ic_global_store_kids,
                description = "Influencer approved global brands handpicked for you"
            ),
            StoreItem(
                name = "Hidden Gems",
                imageRes = R.drawable.ic_hidden_gems_kids,
                description = "Our selection of 300+ most sought after homegrown labels"
            ),
            StoreItem(
                name = "Luxe Edit",
                imageRes = R.drawable.ic_luxe_edit_kids,
                description = "Our collection of 250+ Indian designers rooted in traditional sophistication"
            )
        )

        StoresFList(
            title = "Curated Style Stores",
            stores = curatedStores,
            onStoreClick = { storeName ->
                // Handle store click
                when (storeName) {
                    "Global Store" -> { /* Navigate to Global Store */ }
                    "Hidden Gems" -> { /* Navigate to Hidden Gems */ }
                    "Luxe Edit" -> { /* Navigate to Luxe Edit */ }
                }
                println("Selected store: $storeName")
            }
        )
    }
}

@Composable
fun HomeCategoriesFashionPage(onTabSelected: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        InfoCategoriesF()

        val homeCategoriesF = listOf(
            "Shop All" to R.drawable.ic_shop_all_home,
            "Decor" to R.drawable.ic_decor_home,
            "Kitchen & Dining" to R.drawable.ic_kitchen_dining_home,
            "Bedding" to R.drawable.ic_bedding_home,
            "Bath" to R.drawable.ic_bath_home,
            "Storage" to R.drawable.ic_storage_home,
            "Appliances" to R.drawable.ic_appliances_home,
            "Floor Coverings" to R.drawable.ic_floor_coverings_home,
            "Accent Furniture" to R.drawable.ic_accent_furniture_home,
            "Home Essentials" to R.drawable.ic_home_essentials_home
        )

        SubCategoryFList(
            title = "Shop by Category",
            categories = homeCategoriesF,
            onCategoryClick = { categoryName ->
                // handle click
            }
        )

        val roomCategories = listOf(
            CategoryItem(0, "Kitchen", R.drawable.ic_kitchen, "View products"),
            CategoryItem(1, "Bathroom", R.drawable.ic_bathroom, "View products"),
            CategoryItem(2, "Bedroom", R.drawable.ic_bedroom, "View products"),
            CategoryItem(3, "Living Room", R.drawable.ic_living_room, "View products")
        )

        CategoryListOverlay(
            heading = "Shop by Room",
            subtitle = "View all categories", // Example with subtitle
            items = roomCategories,
            onItemClick = { item ->
                println("Selected: ${item.name}")
            },
            showOverlayOnImage = true,
        )


        BrandsSectionF(
            brandsTitle = "Shop Stylish Brands",
            brandsImageRes = R.drawable.ic_home_categories_f_stylish_brands,
            onBrandsClick = {
                // Handle brands click
            }
        )

//        SubCategoryFList(
//            title = "Shop by Category",
//            categories = homeCategoriesF,
//            showBrandsSection = true,
//            brandsImageRes = R.drawable.ic_home_categories_f_stylish_brands, // Changed to kids variant
//            brandsTitle = "Shop Stylish Brands",
//            onCategoryClick = { categoryName ->
//                // Handle category click
//                println("Selected category: $categoryName")
//            },
//            onBrandsClick = {
//                // Handle brands click
//                println("Brands section clicked")
//            }
//        )

        val curatedStores = listOf(
            StoreItem(
                name = "Hidden Gems",
                imageRes = R.drawable.ic_hidden_gems_home,
                description = "Our selection of 300+ most sought after homegrown labels"
            ),
            StoreItem(
                name = "Luxe Edit",
                imageRes = R.drawable.ic_luxe_edit_home,
                description = "Our collection of 250+ Indian designers rooted in traditional sophistication"
            )
        )

        StoresFList(
            title = "Curated Style Stores",
            stores = curatedStores,
            onStoreClick = { storeName ->
                // Handle store click
                when (storeName) {
                    "Hidden Gems" -> { /* Navigate to Hidden Gems */ }
                    "Luxe Edit" -> { /* Navigate to Luxe Edit */ }
                }
                println("Selected store: $storeName")
            }
        )
    }
}

sealed class FashionCategoryTabPage {
    object Women : FashionCategoryTabPage()
    object Men : FashionCategoryTabPage()
    object Kids : FashionCategoryTabPage()
    object Home : FashionCategoryTabPage()
}
