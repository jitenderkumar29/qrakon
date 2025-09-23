package com.example.qrakon.components.categorytabs

import AllShopping
import ElectronicsShopping
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
import com.example.qrakon.components.categorytabs.beauty.BeautyCarousel
import com.example.qrakon.components.categorytabs.fashion.FashionCarousel
import com.example.qrakon.components.categorytabs.shopping.ShoppingCarousel
import com.example.qrakon.components.categorytabs.todaydeal.TodayDealCarousel
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
    object Shopping : CategoryPage()
    object Mobile : CategoryPage()
    object Fashion : CategoryPage()
    object Beauty : CategoryPage()
    object TodaysDeal : CategoryPage()
    object Economy : CategoryPage()
    object BrideGroom : CategoryPage()
    object AirportDutyFree : CategoryPage()
    object ElectricVehicles : CategoryPage()
    object Industry : CategoryPage()
    object Wholesale : CategoryPage()
    object Sell : CategoryPage()
//    object More : CategoryPage()
}

@Composable
fun CategoryTabs(
    onCategorySelected: (CategoryPage) -> Unit = {}
) {
    var selectedTabIndex by remember { mutableIntStateOf(0) }

    // Pair each tab title with its corresponding icon resource ID
    val tabs = listOf(
        "All" to R.drawable.outline_all_inbox_24,
        "Shopping" to R.drawable.outline_shopping_cart_24,
        "Mobile" to R.drawable.outline_mobile_24,
        "Fashion" to R.drawable.ic_fashion_24,
        "Beauty" to R.drawable.ic_beauty_24,
        "Today's deal" to R.drawable.ic_todays_deal,
        "Economy" to R.drawable.ic_economy,
        "Bride" to R.drawable.outline_person_24,
        "Jewellery" to R.drawable.ic_jewellery_24,
        "Airport" to R.drawable.outline_connecting_airports_24,
        "E-Vehicles" to R.drawable.outline_electric_bolt_24,
        "Industry" to R.drawable.ic_industry,
        "Wholesale" to R.drawable.ic_wholesale,
        "Sell" to R.drawable.outline_sell_24
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
                            1 -> CategoryPage.Shopping
                            2 -> CategoryPage.Mobile
                            3 -> CategoryPage.Fashion
                            4 -> CategoryPage.Beauty
                            5 -> CategoryPage.TodaysDeal
                            6 -> CategoryPage.Economy
                            7 -> CategoryPage.BrideGroom
                            8 -> CategoryPage.AirportDutyFree
                            9 -> CategoryPage.ElectricVehicles
                            10 -> CategoryPage.Industry
                            11 -> CategoryPage.Wholesale
                            12 -> CategoryPage.Sell
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

                        Spacer(modifier = Modifier.height(4.dp))

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
            1 -> ShoppingCategoryPage()
            2 -> MobileCategoryPage()
            3 -> FashionCategoryPage()
            4 -> BeautyCategoryPage()
            5 -> TodaysDealCategoryPage()
            6 -> EconomyCategoryPage()
            7 -> BrideGroomCategoryPage()
            8 -> AirportDutyFreeCategoryPage()
            9 -> ElectricVehiclesCategoryPage()
            10 -> IndustryCategoryPage()
            11 -> WholesaleCategoryPage()
            12 -> SellCategoryPage()
        }
    }
}

// Add these new category page composables
@Composable
fun TodaysDealCategoryPage() {
    var selectedCategory by remember { mutableStateOf("All") }
    Column(
        modifier = Modifier.fillMaxSize().padding(8.dp)
    ) {
        TodayDealCarousel(
            selectedCategory = selectedCategory,
            onCategoryClick = { category -> selectedCategory = category }
        )
        Spacer(modifier = Modifier.height(16.dp))
        when (selectedCategory) {
            "All" -> AllShopping(categoryName = selectedCategory)
            "Electronics" -> ElectronicsShopping(categoryName = selectedCategory)
            else -> AllShopping(categoryName = selectedCategory)
        }
    }
}

@Composable
fun EconomyCategoryPage() {
    Column(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Economy Category",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = "Explore the latest Economy Category trends",
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
//    var selectedCategory by remember { mutableStateOf("All") }
//    Column(
//        modifier = Modifier.fillMaxSize().padding(8.dp)
//    ) {
//        EconomyCarousel(
//            selectedCategory = selectedCategory,
//            onCategoryClick = { category -> selectedCategory = category }
//        )
//        Spacer(modifier = Modifier.height(16.dp))
//        when (selectedCategory) {
//            "All" -> AllShopping(categoryName = selectedCategory)
//            "Electronics" -> ElectronicsShopping(categoryName = selectedCategory)
//            else -> AllShopping(categoryName = selectedCategory)
//        }
//    }
}

@Composable
fun BrideGroomCategoryPage() {
    Column(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Bride & Groom",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = "Everything for your special day",
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}

@Composable
fun AirportDutyFreeCategoryPage() {
    Column(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Airport Duty Free",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = "Tax-free shopping for travelers",
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}

@Composable
fun ElectricVehiclesCategoryPage() {
    Column(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Electric Vehicles",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = "Explore eco-friendly transportation options",
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}

@Composable
fun IndustryCategoryPage() {
    Column(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Industry",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = "Industrial equipment and supplies",
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}

@Composable
fun WholesaleCategoryPage() {
    Column(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Wholesale",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = "Bulk purchasing at wholesale prices",
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}

@Composable
fun SellCategoryPage() {
    Column(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Sell",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = "List your products for sale",
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}

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
            painterResource(id = R.drawable.banner_home1),
            painterResource(id = R.drawable.banner_home2),
            painterResource(id = R.drawable.banner_home3),
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
            height = 250.dp,
            dotSize = 8.dp
        )

//        BannerHome(
//            onJoinPrimeClick = { println("Prime button clicked!") },
//            onDealBoxClick = { println("Deal section clicked!") }
//        )
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

//        QrakonPayScreen()
//        Spacer(
//            modifier = Modifier.height(2.dp)
//                .fillMaxWidth()
//                .background(MaterialTheme.customColors.spacerColor)
//        )
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
        painterResource(id = R.drawable.shopping_banner1),
        painterResource(id = R.drawable.shopping_banner2),
        painterResource(id = R.drawable.shopping_banner3),
        painterResource(id = R.drawable.shopping_banner4),
        painterResource(id = R.drawable.shopping_banner5),
        painterResource(id = R.drawable.shopping_banner6),
        painterResource(id = R.drawable.shopping_banner7),
        painterResource(id = R.drawable.shopping_banner8),
        painterResource(id = R.drawable.shopping_banner9),
    )


    Column(
        modifier = Modifier.fillMaxSize().padding(0.dp)
    ) {
//        ShoppingCarousel(
//            selectedCategory = selectedCategory,
//            onCategoryClick = { category -> selectedCategory = category }
//        )
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
            height = 250.dp,            dotSize = 8.dp,
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
    Column(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Mobile Category",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = "Explore the latest Mobile Category trends",
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}

@Composable
fun MoreCategoryPageTemp() {
    Column(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "More Category",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = "Explore the latest More Category trends and styles",
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}

@Composable
fun FashionCategoryPage() {
    var selectedCategory by remember { mutableStateOf("All") }
    Column(
        modifier = Modifier.fillMaxSize().padding(8.dp)
    ) {
        FashionCarousel(
            selectedCategory = selectedCategory,
            onCategoryClick = { category -> selectedCategory = category }
        )
        Spacer(modifier = Modifier.height(16.dp))
        when (selectedCategory) {
            "All" -> AllShopping(categoryName = selectedCategory)
            "Electronics" -> ElectronicsShopping(categoryName = selectedCategory)
            else -> AllShopping(categoryName = selectedCategory)
        }
    }
}

@Composable
fun BeautyCategoryPage() {
    var selectedCategory by remember { mutableStateOf("All") }
    Column(
        modifier = Modifier.fillMaxSize().padding(8.dp)
    ) {
        BeautyCarousel(
            selectedCategory = selectedCategory,
            onCategoryClick = { category -> selectedCategory = category }
        )
        Spacer(modifier = Modifier.height(16.dp))
        when (selectedCategory) {
            "All" -> AllShopping(categoryName = selectedCategory)
            "Electronics" -> ElectronicsShopping(categoryName = selectedCategory)
            else -> AllShopping(categoryName = selectedCategory)
        }
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
                    is CategoryPage.Shopping -> 1
                    is CategoryPage.Mobile -> 2
                    is CategoryPage.Fashion -> 3
                    is CategoryPage.Beauty -> 4
                    is CategoryPage.TodaysDeal -> 5
                    is CategoryPage.Economy -> 6
                    is CategoryPage.BrideGroom -> 7
                    is CategoryPage.AirportDutyFree -> 8
                    is CategoryPage.ElectricVehicles -> 9
                    is CategoryPage.Industry -> 10
                    is CategoryPage.Wholesale -> 11
                    is CategoryPage.Sell -> 12
//                    is CategoryPage.More -> 12
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