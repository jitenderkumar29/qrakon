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
            showBrandsSection = true,
            brandsImageRes = R.drawable.ic_shop_stylish_brands,
            brandsTitle = "Shop Stylish Brands",
            onCategoryClick = { categoryName ->
                // Handle category click
            },
            onBrandsClick = {
                // Handle brands click
            }
        )
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
            showBrandsSection = true,
            brandsImageRes = R.drawable.ic_men_categories_f_stylish_brands,
            brandsTitle = "Shop Stylish Brands",
            onCategoryClick = { categoryName ->
                // Handle category click
            },
            onBrandsClick = {
                // Handle brands click
            }
        )

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
    Box(Modifier.fillMaxWidth()) {
        Text("Kids Fashion Content")
    }
}

@Composable
fun HomeCategoriesFashionPage(onTabSelected: (String) -> Unit) {
    Box(Modifier.fillMaxWidth()) {
        Text("Home Fashion Content")
    }
}

sealed class FashionCategoryTabPage {
    object Women : FashionCategoryTabPage()
    object Men : FashionCategoryTabPage()
    object Kids : FashionCategoryTabPage()
    object Home : FashionCategoryTabPage()
}
