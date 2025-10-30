package com.example.qrakon.components.fashion.categorydetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.qrakon.R
import com.example.qrakon.components.categorytabs.CarouselFashionOne
import com.example.qrakon.components.categorytabs.Category
import com.example.qrakon.components.categorytabs.CategoryProducts
import com.example.qrakon.components.fashion.fashiontab.BannerFashion
import com.example.qrakon.components.fashion.fashiontab.CategoryItem
import com.example.qrakon.components.fashion.fashiontab.CategoryListDouble
import com.example.qrakon.components.fashion.fashiontab.CategoryListGrid
import com.example.qrakon.components.fashion.fashiontab.CategoryListSimple
import com.example.qrakon.components.fashion.fashiontab.ProductListGrid
import com.example.qrakon.ui.theme.customColors

@Composable
fun CategoryDetailScreen(
    categoryName: String?,
    onBackClick: () -> Unit,
    onTabSelected: (Category) -> Unit, // Added this parameter
    onBanner1Click: () -> Unit = {},
    onBanner2Click: () -> Unit = {},
    onBanner3Click: () -> Unit = {}
) {
    val name = categoryName ?: "Category"

    // Example data per category
    val categoryItems = when (name.lowercase()) {
        "ethnic" -> listOf(
            R.drawable.ic_fashion_ethnic to "Sarees",
        )
        "western" -> listOf(
            R.drawable.ic_fashion_western to "Dresses",
        )
        "fusion" -> listOf(
            R.drawable.ic_fashion_fusion to "Indo-Western Dresses",
        )
        "essentials" -> listOf(
            R.drawable.ic_fashion_essentials to "T-Shirts",
        )
        "beauty" -> listOf(
            R.drawable.ic_fashion_beauty to "Makeup",
        )
        "footwear" -> listOf(
            R.drawable.ic_fashion_footwear to "Flats",
        )
        "jewellery" -> listOf(
            R.drawable.ic_fashion_jewellery to "Earrings",
        )
        "home" -> listOf(
            R.drawable.ic_fashion_home to "Bedding",
        )
        "sportswear" -> listOf(
            R.drawable.ic_fashion_sportswear to "Active T-Shirts",
        )
        "girls" -> listOf(
            R.drawable.ic_fashion_girls to "Frocks",
        )
        else -> listOf(
            R.drawable.ic_view_all_home_tab to "Popular Styles",
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.customColors.white)
    ) {
        // Header Section with padding and background color
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.customColors.lightAccent)
                .padding(start = 12.dp, end = 12.dp, top = 12.dp, bottom = 12.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_back),
                    contentDescription = "Back",
                    modifier = Modifier
                        .size(28.dp)
                        .clickable { onBackClick() }
                )

                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = name,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.customColors.white
                )

                // Spacer to push the icons to the right
                Spacer(modifier = Modifier.weight(1f))

                // Category Icon
                Box(
                    modifier = Modifier
                        .size(35.dp)
                        .shadow(
                            elevation = 2.dp,
                            shape = CircleShape,
                            clip = true
                        )
                        .clip(CircleShape)
                        .background(Color.White)
                        .border(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.2f),
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_search_24),
                        contentDescription = "Categories",
                        tint = Color.Unspecified,
                        modifier = Modifier.size(30.dp)
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                // Wishlist Icon
                Box(
                    modifier = Modifier
                        .size(35.dp)
                        .shadow(
                            elevation = 2.dp,
                            shape = CircleShape,
                            clip = true
                        )
                        .clip(CircleShape)
                        .background(Color.White)
                        .border(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.2f),
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_wishlist_outline),
                        contentDescription = "Wishlist",
                        tint = MaterialTheme.colorScheme.scrim,
                        modifier = Modifier.size(25.dp)
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Box(
                    modifier = Modifier
                        .size(35.dp)
                        .shadow(
                            elevation = 2.dp,
                            shape = CircleShape,
                            clip = true
                        )
                        .clip(CircleShape)
                        .background(Color.White)
                        .border(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.2f),
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.outline_shopping_bag_24),
                        contentDescription = "Wishlist",
                        tint = MaterialTheme.colorScheme.scrim,
                        modifier = Modifier.size(25.dp)
                    )
                }
            }
        }

        // Content Section
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(4.dp) // Reduced from 12.dp to 4.dp
        ) {
            // Show banner only for ethnic category
            if (name.lowercase() == "ethnic") {
                item {
                    // Banner images for ethnic category
                    val bannerImages = listOf(
                        painterResource(id = R.drawable.ethnic_banner2),
                        painterResource(id = R.drawable.ethnic_banner1),
                        painterResource(id = R.drawable.ethnic_banner3),
                        painterResource(id = R.drawable.ethnic_banner4),
                        painterResource(id = R.drawable.ethnic_banner5),
                        painterResource(id = R.drawable.ethnic_banner6),
                        painterResource(id = R.drawable.ethnic_banner7),
                        painterResource(id = R.drawable.ethnic_banner8),
                        painterResource(id = R.drawable.ethnic_banner9),
                        painterResource(id = R.drawable.ethnic_banner10),
                        painterResource(id = R.drawable.ethnic_banner11),
                        painterResource(id = R.drawable.ethnic_banner12),
                    )
                    BannerFashion(
                        images = bannerImages,
                        onImageClick = { page ->
                            when (page) {
                                0 -> onBanner1Click()
                                1 -> onBanner2Click()
                                2 -> onBanner3Click()
                                // Add more cases if needed for other banners
                                else -> onBanner1Click() // Default fallback
                            }
                        },
                        autoScrollDelay = 2000,
                        height = 450.dp,
                        dotSize = 8.dp,
                        modifier = Modifier.padding(bottom = 4.dp) // Reduced from 12.dp to 4.dp
                    )
//                    Spacer(
//                        modifier = Modifier
//                            .height(2.dp)
//                            .fillMaxWidth()
//                            .background(MaterialTheme.customColors.spacerColor)
//                    )
                    val ethnicCategories = listOf(
                        Category(0, "Kurta Sets", R.drawable.kurta_sets_ethnic),
                        Category(1, "Sarees", R.drawable.sarees_ethnic),
                        Category(2, "Lehengas", R.drawable.lehengas_ethnic),
                        Category(3, "Dresses", R.drawable.ethnic_dresses_ethnic),
                        Category(4, "Co-Ords", R.drawable.co_ords_ethnic),
                        Category(5, "Indie", R.drawable.indie_work_ethnic),
                        Category(6, "Runway", R.drawable.runway_ethnic),
                        Category(7, "Designer", R.drawable.designer_ethnic),
                        Category(8, "Fusion", R.drawable.indie_fusion_ethnic)
                    )
                    CarouselFashionOne(
                        categories = ethnicCategories,
                        onTabSelected = { categoryName ->
                            println("hello")
//                            onTabSelected(categoryName) // Then call the original callback
                        },
                        modifier = Modifier,
                        backgroundColor = MaterialTheme.customColors.white,
                        itemWidth = 75,
                        itemHeight = 90,
                        horizontalSpacing = 8
                    )
                    // Festive Categories Simple
                    val festiveCategoriesSimple = listOf(
                        CategoryItem(0, "", R.drawable.ic_ethnic_1, ""),
                        CategoryItem(1, "", R.drawable.ic_ethnic_2, ""),
                        CategoryItem(2, "", R.drawable.ic_ethnic_3, ""),
                        CategoryItem(3, "", R.drawable.ic_ethnic_4, ""),
                        CategoryItem(4, "", R.drawable.ic_ethnic_5, ""),
                        CategoryItem(5, "", R.drawable.ic_ethnic_6, ""),
                        CategoryItem(6, "", R.drawable.ic_ethnic_7, ""),
                        CategoryItem(7, "", R.drawable.ic_ethnic_8, "")
                    )
//                    val items = roomCategoriesSimple
//                    Spacer(
//                        modifier = Modifier
//                            .height(2.dp)
//                            .fillMaxWidth()
//                            .background(MaterialTheme.customColors.spacerColor)
//                    )

                    Image(
                        painter = painterResource(R.drawable.ic_ethnic_header_festive),
                        contentDescription = "Banner",
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = 100.dp, max = 300.dp), // Height between min and max, // 30% of screen height, // Sets height based on width and aspect ratio
                        contentScale = ContentScale.FillBounds
                    )
                    CategoryListSimple(
                        items = festiveCategoriesSimple,
                        onItemClick = { item -> println("Selected: ${item.name}") },
                        itemWidth = 180.dp,
                        itemHeight = 220.dp,
                        horizontalSpacing = 12.dp,
//                        verticalPadding = 8.dp,
                        horizontalPadding = 12.dp,
                        backgroundColor = Color(0xFFFDFDF1)
//                        backgroundColor = Color(0xFFFFF8E1)
                    )

                    // Handpicked Categories Simple
                    val handpickedCategoriesSimple = listOf(
                        CategoryItem(0, "", R.drawable.ic_ethnic_handpicked_1, ""),
                        CategoryItem(1, "", R.drawable.ic_ethnic_handpicked_2, ""),
                        CategoryItem(2, "", R.drawable.ic_ethnic_handpicked_3, ""),
                        CategoryItem(3, "", R.drawable.ic_ethnic_handpicked_4, ""),
                        CategoryItem(4, "", R.drawable.ic_ethnic_handpicked_5, ""),
                        CategoryItem(5, "", R.drawable.ic_ethnic_handpicked_6, ""),
                        CategoryItem(6, "", R.drawable.ic_ethnic_handpicked_7, ""),
                        CategoryItem(7, "", R.drawable.ic_ethnic_handpicked_8, ""),
                        CategoryItem(8, "", R.drawable.ic_ethnic_handpicked_9, ""),
                        CategoryItem(9, "", R.drawable.ic_ethnic_handpicked_10, "")
                    )
                    Image(
                        painter = painterResource(R.drawable.ic_ethnic_header_handpicked),
                        contentDescription = "Banner",
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = 100.dp, max = 300.dp), // Height between min and max, // 30% of screen height, // Sets height based on width and aspect ratio
                        contentScale = ContentScale.FillBounds
                    )
                    CategoryListSimple(
                        items = handpickedCategoriesSimple,
                        onItemClick = { item -> println("Selected: ${item.name}") },
                        itemWidth = 180.dp,
                        itemHeight = 260.dp,
                        horizontalSpacing = 12.dp,
//                        verticalPadding = 8.dp,
                        horizontalPadding = 12.dp,
                        backgroundColor = Color(0xFFFFFFFF)
//                        backgroundColor = Color(0xFFFFF8E1)
                    )

                    // Just Categories Simple
                    val justCategoriesSimple = listOf(
                        CategoryItem(0, "", R.drawable.ic_ethnic_just_1, ""),
                        CategoryItem(1, "", R.drawable.ic_ethnic_just_2, ""),
                        CategoryItem(2, "", R.drawable.ic_ethnic_just_3, ""),
                        CategoryItem(3, "", R.drawable.ic_ethnic_just_4, ""),
                        CategoryItem(4, "", R.drawable.ic_ethnic_just_5, ""),
                        CategoryItem(5, "", R.drawable.ic_ethnic_just_6, ""),
                        CategoryItem(6, "", R.drawable.ic_ethnic_just_7, ""),
                        )
                    Image(
                        painter = painterResource(R.drawable.ic_ethnic_header_just),
                        contentDescription = "Banner",
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = 100.dp, max = 300.dp), // Height between min and max, // 30% of screen height, // Sets height based on width and aspect ratio
                        contentScale = ContentScale.FillBounds
                    )
                    CategoryListSimple(
                        items = justCategoriesSimple,
                        onItemClick = { item -> println("Selected: ${item.name}") },
                        itemWidth = 180.dp,
                        itemHeight = 270.dp,
                        horizontalSpacing = 12.dp,
//                        verticalPadding = 8.dp,
                        horizontalPadding = 12.dp,
                        backgroundColor = Color(0xFFFDFDF1)
                    )

                    // Reel Categories Simple
                    val reelCategoriesSimple = listOf(
                        CategoryItem(0, "", R.drawable.ic_ethnic_reel_1, ""),
                        CategoryItem(1, "", R.drawable.ic_ethnic_reel_2, ""),
                        CategoryItem(2, "", R.drawable.ic_ethnic_reel_3, ""),
                        CategoryItem(3, "", R.drawable.ic_ethnic_reel_4, ""),
                        CategoryItem(4, "", R.drawable.ic_ethnic_reel_5, ""),
                        CategoryItem(5, "", R.drawable.ic_ethnic_reel_6, ""),
                        CategoryItem(6, "", R.drawable.ic_ethnic_reel_7, ""),
                        CategoryItem(7, "", R.drawable.ic_ethnic_reel_8, ""),
                        CategoryItem(8, "", R.drawable.ic_ethnic_reel_9, ""),
                        CategoryItem(9, "", R.drawable.ic_ethnic_reel_10, ""),
                    )
                    Image(
                        painter = painterResource(R.drawable.ic_ethnic_header_reel),
                        contentDescription = "Banner",
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = 100.dp, max = 300.dp), // Height between min and max, // 30% of screen height, // Sets height based on width and aspect ratio
                        contentScale = ContentScale.FillBounds
                    )
                    CategoryListSimple(
                        items = reelCategoriesSimple,
                        onItemClick = { item -> println("Selected: ${item.name}") },
                        itemWidth = 180.dp,
                        itemHeight = 270.dp,
                        horizontalSpacing = 12.dp,
//                        verticalPadding = 8.dp,
                        horizontalPadding = 12.dp,
                        backgroundColor = Color(0xFFFDFDF1)
                    )

                    // Reel Categories Simple
                    val indieCategoriesSimple = listOf(
                        CategoryItem(0, "", R.drawable.ic_ethnic_indie_1, ""),
                        CategoryItem(1, "", R.drawable.ic_ethnic_indie_2, ""),
                        CategoryItem(2, "", R.drawable.ic_ethnic_indie_3, ""),
                        CategoryItem(3, "", R.drawable.ic_ethnic_indie_4, ""),
                         )
                    Image(
                        painter = painterResource(R.drawable.ic_ethnic_header_indie),
                        contentDescription = "Banner",
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = 100.dp, max = 300.dp), // Height between min and max, // 30% of screen height, // Sets height based on width and aspect ratio
                        contentScale = ContentScale.FillBounds
                    )
                    CategoryListSimple(
                        items = indieCategoriesSimple,
                        onItemClick = { item -> println("Selected: ${item.name}") },
                        itemWidth = 180.dp,
                        itemHeight = 270.dp,
                        horizontalSpacing = 12.dp,
//                        verticalPadding = 8.dp,
                        horizontalPadding = 12.dp,
                        backgroundColor = Color(0xFFFFFFFF)
//                        backgroundColor = Color(0xFFFDFDF1)
                    )
                    // Reel Categories Simple
                    val celebCategoriesSimple = listOf(
                        CategoryItem(0, "", R.drawable.ic_ethnic_celeb_1, ""),
                        CategoryItem(1, "", R.drawable.ic_ethnic_celeb_2, ""),
                        CategoryItem(2, "", R.drawable.ic_ethnic_celeb_3, ""),
                        CategoryItem(3, "", R.drawable.ic_ethnic_celeb_4, ""),
                         )
                    Image(
                        painter = painterResource(R.drawable.ic_ethnic_header_celeb),
                        contentDescription = "Banner",
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = 100.dp, max = 300.dp), // Height between min and max, // 30% of screen height, // Sets height based on width and aspect ratio
                        contentScale = ContentScale.FillBounds
                    )
                    CategoryListSimple(
                        items = celebCategoriesSimple,
                        onItemClick = { item -> println("Selected: ${item.name}") },
                        itemWidth = 180.dp,
                        itemHeight = 270.dp,
                        horizontalSpacing = 12.dp,
//                        verticalPadding = 8.dp,
                        horizontalPadding = 12.dp,
                        backgroundColor = Color(0xFFFDFDF1)
                    )
                    // Kurta Categories Simple
                    val kurtaCategoriesSimple = listOf(
                        CategoryItem(0, "", R.drawable.ic_ethnic_kurta_1, ""),
                        CategoryItem(1, "", R.drawable.ic_ethnic_kurta_2, ""),
                        CategoryItem(2, "", R.drawable.ic_ethnic_kurta_3, ""),
                        CategoryItem(3, "", R.drawable.ic_ethnic_kurta_4, ""),
                        CategoryItem(4, "", R.drawable.ic_ethnic_kurta_5, ""),
                        CategoryItem(5, "", R.drawable.ic_ethnic_kurta_6, ""),
                      )
                    Image(
                        painter = painterResource(R.drawable.ic_ethnic_header_kurta),
                        contentDescription = "Banner",
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = 100.dp, max = 300.dp), // Height between min and max, // 30% of screen height, // Sets height based on width and aspect ratio
                        contentScale = ContentScale.FillBounds
                    )
                    CategoryListDouble(
                        items = kurtaCategoriesSimple,
                        onItemClick = { item -> println("Selected: ${item.name}") },
                        showOverlayOnImage = false,
                        showItemName = false,
                        itemWidth = 160.dp,
                        itemHeight = 220.dp,
                        horizontalSpacing = 12.dp,
                        verticalPadding = 12.dp,
                        backgroundColor = Color(0xFFFBE0CD),
//                        showOverlayOnImage = false,
//                        overlayBackground = Color.Black.copy(alpha = 0.6f),

                    )
                    // Saree Categories Simple
                    val sareeCategoriesSimple = listOf(
                        CategoryItem(0, "", R.drawable.ic_ethnic_saree_1, ""),
                        CategoryItem(1, "", R.drawable.ic_ethnic_saree_2, ""),
                        CategoryItem(2, "", R.drawable.ic_ethnic_saree_3, ""),
                        CategoryItem(3, "", R.drawable.ic_ethnic_saree_4, ""),
                        CategoryItem(4, "", R.drawable.ic_ethnic_saree_5, ""),
                        CategoryItem(5, "", R.drawable.ic_ethnic_saree_6, ""),
                        CategoryItem(6, "", R.drawable.ic_ethnic_saree_7, ""),
                        CategoryItem(7, "", R.drawable.ic_ethnic_saree_8, ""),
                        CategoryItem(8, "", R.drawable.ic_ethnic_saree_9, ""),
                        CategoryItem(9, "", R.drawable.ic_ethnic_saree_10, ""),
                    )
                    Image(
                        painter = painterResource(R.drawable.ic_ethnic_header_saree),
                        contentDescription = "Banner",
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = 100.dp, max = 300.dp), // Height between min and max, // 30% of screen height, // Sets height based on width and aspect ratio
                        contentScale = ContentScale.FillBounds
                    )
                    CategoryListSimple(
                        items = sareeCategoriesSimple,
                        onItemClick = { item -> println("Selected: ${item.name}") },
                        itemWidth = 180.dp,
                        itemHeight = 270.dp,
                        horizontalSpacing = 12.dp,
//                        verticalPadding = 8.dp,
                        horizontalPadding = 12.dp,
                        backgroundColor = Color(0xFFFDFDF1)
                    )
                    // Luxe Categories Simple
                    val luxeCategoriesSimple = listOf(
                        CategoryItem(0, "", R.drawable.ic_ethnic_luxe_1, ""),
                        CategoryItem(1, "", R.drawable.ic_ethnic_luxe_2, ""),
                        CategoryItem(2, "", R.drawable.ic_ethnic_luxe_3, ""),
                        CategoryItem(3, "", R.drawable.ic_ethnic_luxe_4, ""),
                        CategoryItem(4, "", R.drawable.ic_ethnic_luxe_5, ""),
                        CategoryItem(5, "", R.drawable.ic_ethnic_luxe_6, ""),
                     )
                    Image(
                        painter = painterResource(R.drawable.ic_ethnic_header_luxe),
                        contentDescription = "Banner",
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = 100.dp, max = 300.dp), // Height between min and max, // 30% of screen height, // Sets height based on width and aspect ratio
                        contentScale = ContentScale.FillBounds
                    )
                    CategoryListSimple(
                        items = luxeCategoriesSimple,
                        onItemClick = { item -> println("Selected: ${item.name}") },
                        itemWidth = 200.dp,
                        itemHeight = 290.dp,
                        horizontalSpacing = 12.dp,
//                        verticalPadding = 8.dp,
                        horizontalPadding = 12.dp,
                        backgroundColor = Color(0xFFFDFDF1)
                    )
                    // Wedding Categories Simple
                    val weddingCategoriesSimple = listOf(
                        CategoryItem(0, "", R.drawable.ic_ethnic_wedding_1, ""),
                        CategoryItem(1, "", R.drawable.ic_ethnic_wedding_2, ""),
                        CategoryItem(2, "", R.drawable.ic_ethnic_wedding_3, ""),
                        CategoryItem(3, "", R.drawable.ic_ethnic_wedding_4, ""),
                     )
                    Image(
                        painter = painterResource(R.drawable.ic_ethnic_header_wedding),
                        contentDescription = "Banner",
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = 100.dp, max = 300.dp), // Height between min and max, // 30% of screen height, // Sets height based on width and aspect ratio
                        contentScale = ContentScale.FillBounds
                    )
                    CategoryListSimple(
                        items = weddingCategoriesSimple,
                        onItemClick = { item -> println("Selected: ${item.name}") },
                        itemWidth = 220.dp,
                        itemHeight = 290.dp,
                        horizontalSpacing = 12.dp,
//                        verticalPadding = 8.dp,
                        horizontalPadding = 12.dp,
                        backgroundColor = Color(0xFFF2E2D5)
                    )
                    // Rising Star Categories Simple
                    val risingCategoriesSimple = listOf(
                        CategoryItem(0, "", R.drawable.ic_ethnic_rising_1, ""),
                        CategoryItem(1, "", R.drawable.ic_ethnic_rising_2, ""),
                        CategoryItem(2, "", R.drawable.ic_ethnic_rising_3, ""),
                        CategoryItem(3, "", R.drawable.ic_ethnic_rising_4, ""),
                     )
                    Image(
                        painter = painterResource(R.drawable.ic_ethnic_header_rising),
                        contentDescription = "Banner",
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = 100.dp, max = 300.dp), // Height between min and max, // 30% of screen height, // Sets height based on width and aspect ratio
                        contentScale = ContentScale.FillBounds
                    )
                    CategoryListSimple(
                        items = risingCategoriesSimple,
                        onItemClick = { item -> println("Selected: ${item.name}") },
                        itemWidth = 220.dp,
                        itemHeight = 290.dp,
                        horizontalSpacing = 12.dp,
//                        verticalPadding = 8.dp,
                        horizontalPadding = 12.dp,
                        backgroundColor = Color(0xFFF2E2D5)
                    )
//                    CategoryListSimpleWithHeading(
//                        heading = "",
////                        heading = "The Festive Edit",
//                        items = roomCategoriesSimple,
//                        onItemClick = { item -> println("Selected: ${item.name}") },
//                        customListItem = { item, onClick ->
//                            CustomCardCategoryListItem(
//                                item = item,
//                                onClick = onClick,
//                                itemWidth = 180.dp,
//                                itemHeight = 220.dp,
//                                imageSize = 220.dp,
//                                backgroundColor = Color(0xFFFFFFFF)
//                            )
//                        },
//                        backgroundColor = Color(0xFFFFFFFF),
//                        horizontalSpacing = 6.dp,
//                        verticalPadding = 6.dp,
//                        horizontalPadding = 6.dp,
//                    )

                    }
                }

//            Western
            if (name.lowercase() == "western") {
                item {
                    // Banner images for ethnic category
                    val bannerImages = listOf(
                        painterResource(id = R.drawable.western_banner1),
                        painterResource(id = R.drawable.western_banner2),
                        painterResource(id = R.drawable.western_banner3),
                        painterResource(id = R.drawable.western_banner4),
                        painterResource(id = R.drawable.western_banner5),
                        painterResource(id = R.drawable.western_banner6),
                        painterResource(id = R.drawable.western_banner7),
                        painterResource(id = R.drawable.western_banner8),
                        painterResource(id = R.drawable.western_banner9),
                    )
                    BannerFashion(
                        images = bannerImages,
                        onImageClick = { page ->
                            when (page) {
                                0 -> onBanner1Click()
                                1 -> onBanner2Click()
                                2 -> onBanner3Click()
                                // Add more cases if needed for other banners
                                else -> onBanner1Click() // Default fallback
                            }
                        },
                        autoScrollDelay = 2000,
                        height = 450.dp,
                        dotSize = 8.dp,
                        modifier = Modifier.padding(bottom = 8.dp) // Reduced from 12.dp to 4.dp
                    )
//                    Spacer(
//                        modifier = Modifier
//                            .height(2.dp)
//                            .fillMaxWidth()
//                            .background(MaterialTheme.customColors.spacerColor)
//                    )
                    val westernCategories = listOf(
                        Category(0, "Shirts", R.drawable.shirts_western),
                        Category(1, "Tops", R.drawable.tops_western),
                        Category(2, "Jeans", R.drawable.jeans_western),
                        Category(3, "L&L", R.drawable.l_and_l_western),
                        Category(4, "Tees", R.drawable.tees_western),
                        Category(5, "Dresses", R.drawable.dresses_western),
                        Category(6, "Jumpsuits", R.drawable.jumpsuits_western),
                        Category(7, "Rising Stars", R.drawable.rising_stars_western),
                        Category(8, "MLI", R.drawable.mli_western),
                        Category(9, "Newest Launches", R.drawable.newest_launches),
                        Category(10, "Dazzling Dresses", R.drawable.dazzling_dresses),
                        Category(11, "Trendy Tops", R.drawable.trendy_tops),
                        Category(12, "Versatile Bottoms", R.drawable.versatile_bottoms),
                    )
                    CarouselFashionOne(
                        categories = westernCategories,
                        onTabSelected = { categoryName ->
                            println("hello")
//                            onTabSelected(categoryName) // Then call the original callback
                        },
                        modifier = Modifier,
                        backgroundColor = MaterialTheme.customColors.white,
                        itemWidth = 75,
                        itemHeight = 90,
                        horizontalSpacing = 8
                    )
//
                    val bannerImages2 = listOf(
                        painterResource(id = R.drawable.western_banner2_1),
                        painterResource(id = R.drawable.western_banner2_2),
                        painterResource(id = R.drawable.western_banner2_3),
                        painterResource(id = R.drawable.western_banner2_4),
                        painterResource(id = R.drawable.western_banner2_5),
                        painterResource(id = R.drawable.western_banner2_6),
                        painterResource(id = R.drawable.western_banner2_7),
                        painterResource(id = R.drawable.western_banner2_8),
                        painterResource(id = R.drawable.western_banner2_9),
                    )
                    BannerFashion(
                        images = bannerImages2,
                        onImageClick = { page ->
                            when (page) {
                                0 -> onBanner1Click()
                                1 -> onBanner2Click()
                                2 -> onBanner3Click()
                                // Add more cases if needed for other banners
                                else -> onBanner1Click() // Default fallback
                            }
                        },
                        autoScrollDelay = 2000,
                        height = 450.dp,
                        dotSize = 8.dp,
                        modifier = Modifier.padding(bottom = 4.dp) // Reduced from 12.dp to 4.dp
                    )
                    // New Categories Simple
                    val newWesternCategoriesSimple = listOf(
                        CategoryItem(0, "", R.drawable.ic_new_western_1, ""),
                        CategoryItem(1, "", R.drawable.ic_new_western_2, ""),
                        CategoryItem(2, "", R.drawable.ic_new_western_3, ""),
                        CategoryItem(3, "", R.drawable.ic_new_western_4, ""),
                        CategoryItem(4, "", R.drawable.ic_new_western_5, ""),
                        CategoryItem(5, "", R.drawable.ic_new_western_6, ""),
                        CategoryItem(6, "", R.drawable.ic_new_western_7, ""),
                        CategoryItem(7, "", R.drawable.ic_new_western_8, ""),
                        CategoryItem(8, "", R.drawable.ic_new_western_9, ""),
                    )
                    Image(
                        painter = painterResource(R.drawable.ic_western_header_new),
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
                        items = newWesternCategoriesSimple,
                        onItemClick = { item -> println("Selected: ${item.name}") },
                        itemWidth = 180.dp,
                        itemHeight = 250.dp,
                        horizontalSpacing = 12.dp,
//                        verticalPadding = 8.dp,
                        horizontalPadding = 12.dp,
                        backgroundColor = Color(0xFFFDFDF1)
//                        backgroundColor = Color(0xFFFFF8E1)
                    )
                    // New Season Categories Simple
                    val newSeasonCategoriesSimple = listOf(
                        CategoryItem(0, "", R.drawable.ic_new_season_western_1, ""),
                        CategoryItem(1, "", R.drawable.ic_new_season_western_2, ""),
                        CategoryItem(2, "", R.drawable.ic_new_season_western_3, ""),
                        CategoryItem(3, "", R.drawable.ic_new_season_western_4, ""),
                        CategoryItem(4, "", R.drawable.ic_new_season_western_5, ""),
                        CategoryItem(5, "", R.drawable.ic_new_season_western_6, ""),
                        CategoryItem(6, "", R.drawable.ic_new_season_western_7, ""),
                        CategoryItem(7, "", R.drawable.ic_new_season_western_8, ""),
                        CategoryItem(8, "", R.drawable.ic_new_season_western_9, ""),
                    )
                    Image(
                        painter = painterResource(R.drawable.ic_western_header_new_season),
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
                        items = newSeasonCategoriesSimple,
                        onItemClick = { item -> println("Selected: ${item.name}") },
                        itemWidth = 180.dp,
                        itemHeight = 250.dp,
                        horizontalSpacing = 12.dp,
//                        verticalPadding = 8.dp,
                        horizontalPadding = 12.dp,
                        backgroundColor = Color(0xFFFDFDF1)
//                        backgroundColor = Color(0xFFFFF8E1)
                    )

                    // All Things Global Season Categories Simple
                    val globalCategoriesSimple = listOf(
                        CategoryItem(0, "", R.drawable.ic_new_season_global_1, ""),
                        CategoryItem(1, "", R.drawable.ic_new_season_global_2, ""),
                        CategoryItem(2, "", R.drawable.ic_new_season_global_3, ""),
                    )
                    Image(
                        painter = painterResource(R.drawable.ic_western_header_global),
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
                        items = globalCategoriesSimple,
                        onItemClick = { item -> println("Selected: ${item.name}") },
                        itemWidth = 180.dp,
                        itemHeight = 260.dp,
                        horizontalSpacing = 12.dp,
//                        verticalPadding = 8.dp,
                        horizontalPadding = 12.dp,
                        backgroundColor = Color(0xFFFDFDF1)
//                        backgroundColor = Color(0xFFFFF8E1)
                    )

                    // Hot on the Timeline Categories Simple
                    val timelineCategoriesSimple = listOf(
                        CategoryItem(0, "", R.drawable.ic_new_season_timeline_1, ""),
                        CategoryItem(1, "", R.drawable.ic_new_season_timeline_2, ""),
                        CategoryItem(2, "", R.drawable.ic_new_season_timeline_3, ""),
                        CategoryItem(3, "", R.drawable.ic_new_season_timeline_4, ""),
                        CategoryItem(4, "", R.drawable.ic_new_season_timeline_5, ""),
                        CategoryItem(5, "", R.drawable.ic_new_season_timeline_6, ""),
                        CategoryItem(6, "", R.drawable.ic_new_season_timeline_7, ""),
                        CategoryItem(7, "", R.drawable.ic_new_season_timeline_8, ""),
                        CategoryItem(8, "", R.drawable.ic_new_season_timeline_9, ""),
                        CategoryItem(9, "", R.drawable.ic_new_season_timeline_10, ""),
                         )
                    Image(
                        painter = painterResource(R.drawable.ic_western_header_timeline),
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
                        items = timelineCategoriesSimple,
                        onItemClick = { item -> println("Selected: ${item.name}") },
                        itemWidth = 220.dp,
                        itemHeight = 180.dp,
                        horizontalSpacing = 12.dp,
//                        verticalPadding = 8.dp,
                        horizontalPadding = 12.dp,
                        backgroundColor = Color(0xFFFFFFFF)
//                        backgroundColor = Color(0xFFFFF8E1)
                    )
                    // Premium Collections Categories Simple
                    val premiumCategoriesSimple = listOf(
                        CategoryItem(0, "", R.drawable.ic_new_season_premium_1, ""),
                        CategoryItem(1, "", R.drawable.ic_new_season_premium_2, ""),
                        CategoryItem(2, "", R.drawable.ic_new_season_premium_3, ""),
                        )
                    Image(
                        painter = painterResource(R.drawable.ic_western_header_premium),
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
                        items = premiumCategoriesSimple,
                        onItemClick = { item -> println("Selected: ${item.name}") },
                        itemWidth = 180.dp,
                        itemHeight = 250.dp,
                        horizontalSpacing = 12.dp,
//                        verticalPadding = 8.dp,
                        horizontalPadding = 12.dp,
                        backgroundColor = Color(0xFFFFFFFF)
//                        backgroundColor = Color(0xFFFFF8E1)
                    )
                    // The Fan Favourites Categories Simple
                    val fanCategoriesSimple = listOf(
                        CategoryItem(0, "", R.drawable.ic_new_season_fan_1, ""),
                        CategoryItem(1, "", R.drawable.ic_new_season_fan_2, ""),
                        CategoryItem(2, "", R.drawable.ic_new_season_fan_3, ""),
                        CategoryItem(3, "", R.drawable.ic_new_season_fan_4, ""),
                        CategoryItem(4, "", R.drawable.ic_new_season_fan_5, ""),
                        CategoryItem(5, "", R.drawable.ic_new_season_fan_6, ""),
                        CategoryItem(6, "", R.drawable.ic_new_season_fan_7, ""),
                        )
                    Image(
                        painter = painterResource(R.drawable.ic_western_header_fan),
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
                        items = fanCategoriesSimple,
                        onItemClick = { item -> println("Selected: ${item.name}") },
                        itemWidth = 180.dp,
                        itemHeight = 320.dp,
                        horizontalSpacing = 12.dp,
//                        verticalPadding = 8.dp,
                        horizontalPadding = 12.dp,
                        backgroundColor = Color(0xFFFFFFFF)
//                        backgroundColor = Color(0xFFFFF8E1)
                    )
                    // The Party Dressing Categories Simple
                    val partyCategoriesSimple = listOf(
                        CategoryItem(0, "", R.drawable.ic_new_season_party_1, ""),
                        CategoryItem(1, "", R.drawable.ic_new_season_party_2, ""),
                        CategoryItem(2, "", R.drawable.ic_new_season_party_3, ""),
                        CategoryItem(3, "", R.drawable.ic_new_season_party_4, ""),
                        CategoryItem(4, "", R.drawable.ic_new_season_party_5, ""),
                        CategoryItem(5, "", R.drawable.ic_new_season_party_6, ""),
                        CategoryItem(6, "", R.drawable.ic_new_season_party_7, ""),
                        CategoryItem(7, "", R.drawable.ic_new_season_party_8, ""),
                        CategoryItem(8, "", R.drawable.ic_new_season_party_9, ""),
                        CategoryItem(9, "", R.drawable.ic_new_season_party_10, ""),
                        CategoryItem(10, "", R.drawable.ic_new_season_party_11, ""),
                        )
                    Image(
                        painter = painterResource(R.drawable.ic_western_header_party),
                        contentDescription = "Banner",
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(
                                min = 100.dp,
                                max = 400.dp
                            ), // Height between min and max, // 30% of screen height, // Sets height based on width and aspect ratio
                        contentScale = ContentScale.FillBounds
                    )
                    CategoryListSimple(
                        items = partyCategoriesSimple,
                        onItemClick = { item -> println("Selected: ${item.name}") },
                        itemWidth = 180.dp,
                        itemHeight = 330.dp,
                        horizontalSpacing = 12.dp,
//                        verticalPadding = 8.dp,
                        horizontalPadding = 12.dp,
                        backgroundColor = Color(0xFFFFFFFF)
//                        backgroundColor = Color(0xFFFFF8E1)
                    )
                    // Gen Z Edit Categories Simple
                    val genzCategoriesSimple = listOf(
                        CategoryItem(0, "", R.drawable.ic_new_season_genz_1, ""),
                        CategoryItem(1, "", R.drawable.ic_new_season_genz_2, ""),
                        CategoryItem(2, "", R.drawable.ic_new_season_genz_3, ""),
                        CategoryItem(3, "", R.drawable.ic_new_season_genz_4, ""),
                        CategoryItem(4, "", R.drawable.ic_new_season_genz_5, ""),
                        CategoryItem(5, "", R.drawable.ic_new_season_genz_6, ""),
                        CategoryItem(6, "", R.drawable.ic_new_season_genz_7, ""),
                        CategoryItem(7, "", R.drawable.ic_new_season_genz_8, ""),
                        )
                    Image(
                        painter = painterResource(R.drawable.ic_western_header_genz),
                        contentDescription = "Banner",
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(
                                min = 100.dp,
                                max = 450.dp
                            ), // Height between min and max, // 30% of screen height, // Sets height based on width and aspect ratio
                        contentScale = ContentScale.FillBounds
                    )
                    CategoryListSimple(
                        items = genzCategoriesSimple,
                        onItemClick = { item -> println("Selected: ${item.name}") },
                        itemWidth = 180.dp,
                        itemHeight = 300.dp,
                        horizontalSpacing = 12.dp,
//                        verticalPadding = 8.dp,
                        horizontalPadding = 12.dp,
                        backgroundColor = Color(0xFFFFFFFF)
//                        backgroundColor = Color(0xFFFFF8E1)
                    )

//                    Global Store
                    Image(
                        painter = painterResource(R.drawable.ic_western_header_global_store),
                        contentDescription = "Banner",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(600.dp),
                        contentScale = ContentScale.FillBounds
                    )

                    // Shades of the Season Categories Simple
                    val shadesCategoriesSimple = listOf(
                        CategoryItem(0, "", R.drawable.ic_new_season_shades_1, ""),
                        CategoryItem(1, "", R.drawable.ic_new_season_shades_2, ""),
                        CategoryItem(2, "", R.drawable.ic_new_season_shades_3, ""),
                        CategoryItem(3, "", R.drawable.ic_new_season_shades_4, ""),
                        CategoryItem(4, "", R.drawable.ic_new_season_shades_5, ""),
                    )
                    Image(
                        painter = painterResource(R.drawable.ic_western_header_shades),
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
                        items = shadesCategoriesSimple,
                        onItemClick = { item -> println("Selected: ${item.name}") },
                        itemWidth = 220.dp,
                        itemHeight = 350.dp,
                        horizontalSpacing = 12.dp,
//                        verticalPadding = 8.dp,
                        horizontalPadding = 12.dp,
                        backgroundColor = Color(0xFFFFFFFF)
//                        backgroundColor = Color(0xFFFFF8E1)
                    )
                    // The Hot List Categories Simple
                    val hot_listCategoriesSimple = listOf(
                        CategoryItem(0, "", R.drawable.ic_new_season_hot_list_1, ""),
                        CategoryItem(1, "", R.drawable.ic_new_season_hot_list_2, ""),
                        CategoryItem(2, "", R.drawable.ic_new_season_hot_list_3, ""),
                        CategoryItem(3, "", R.drawable.ic_new_season_hot_list_4, ""),
                        CategoryItem(4, "", R.drawable.ic_new_season_hot_list_5, ""),
                        CategoryItem(4, "", R.drawable.ic_new_season_hot_list_6, ""),
                        CategoryItem(4, "", R.drawable.ic_new_season_hot_list_7, ""),
                    )
                    Image(
                        painter = painterResource(R.drawable.ic_western_header_hot_list),
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
                        items = hot_listCategoriesSimple,
                        onItemClick = { item -> println("Selected: ${item.name}") },
                        itemWidth = 180.dp,
                        itemHeight = 220.dp,
                        horizontalSpacing = 12.dp,
//                        verticalPadding = 8.dp,
                        horizontalPadding = 12.dp,
                        backgroundColor = Color(0xFF202E51)
//                        backgroundColor = Color(0xFFFFF8E1)
                    )

                    val sampleProducts = listOf(
                        ProductListGrid("Iki Chic", "Starting at ₹399", R.drawable.iki_chic),
                        ProductListGrid("Trendy Wear", "Starting at ₹1299", R.drawable.product2),
                        ProductListGrid("Summer Sera", "Starting at ₹750", R.drawable.product3),
                        ProductListGrid("Rebel Tee", "Starting at ₹999", R.drawable.product4),
                        ProductListGrid("Smart Casual", "Starting at ₹799", R.drawable.product5),
                        ProductListGrid("Daily Wear", "Starting at ₹400", R.drawable.product6),
                        ProductListGrid("Sera Classic", "Starting at ₹350", R.drawable.sera),
                        ProductListGrid("Stylish Top", "Starting at ₹499", R.drawable.product8),
                        ProductListGrid("Trendy Shirt", "Starting at ₹400", R.drawable.product9),
                        ProductListGrid("Dilinger Hoodie", "Starting at ₹599", R.drawable.dilinger),
                        ProductListGrid("Fnocks Jacket", "Starting at ₹399", R.drawable.fnocks),
                        ProductListGrid("Rebelmme Tee", "Starting at ₹599", R.drawable.therebelmme)
                    )

// Display CategoryListGrid showing **name only**
                    Image(
                        painter = painterResource(R.drawable.ic_western_header_list_grid),
                        contentDescription = "Banner",
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(
                                min = 100.dp,
                                max = 300.dp
                            ), // Height between min and max, // 30% of screen height, // Sets height based on width and aspect ratio
                        contentScale = ContentScale.FillBounds
                    )
                    CategoryListGrid(
                        products = sampleProducts,
                        columns = 3,
                        gridHeight = 945.dp, // fixed height to avoid crashes
                        showName = false,
                        showPrice = true,   // hide price
                        imageAspectRatio = 3f / 4.5f,
//                        defaultCardColor = Color(0xFFB3212E),
                        defaultCardColor = Color(0xFFF5F2ED),
                        onItemClick = { product ->
                            println("Clicked on ${product.name}")
                        }
                    )

//                    CategoryListGrid(
//                        products = sampleProducts,
//                        columns = 3,
//                        onItemClick = { product ->
//                            // ✅ Handle item click here (e.g., navigate or show toast)
//                            println("Clicked on ${product.name}")
//                        }
//                    )
//                    MaterialTheme {
//                    }
//                    CategoryListSimple(
//                        items = globalCategoriesSimple,
//                        onItemClick = { item -> println("Selected: ${item.name}") },
//                        itemWidth = 220.dp,
//                        itemHeight = 290.dp,
//                        horizontalSpacing = 12.dp,
////                        verticalPadding = 8.dp,
//                        horizontalPadding = 12.dp,
//                        backgroundColor = Color(0xFFF2E2D5)
//                    )
                }
            }

            if (name.lowercase() == "fusion") {
                item {
                    // Banner images for ethnic category
                    Image(
                        painter = painterResource(R.drawable.ic_fusion_header_new),
                        contentDescription = "Banner",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp), // Height between min and max, // 30% of screen height, // Sets height based on width and aspect ratio
                        contentScale = ContentScale.FillBounds
                    )
                    val bannerImages = listOf(
                        painterResource(id = R.drawable.fusion_banner1),
                        painterResource(id = R.drawable.fusion_banner2),
                        painterResource(id = R.drawable.fusion_banner3),
                        painterResource(id = R.drawable.fusion_banner4),
                        painterResource(id = R.drawable.fusion_banner5),
                        painterResource(id = R.drawable.fusion_banner6),
                        painterResource(id = R.drawable.fusion_banner7),
                        painterResource(id = R.drawable.fusion_banner8),
                        painterResource(id = R.drawable.fusion_banner9),
                    )
                    BannerFashion(
                        images = bannerImages,
                        onImageClick = { page ->
                            when (page) {
                                0 -> onBanner1Click()
                                1 -> onBanner2Click()
                                2 -> onBanner3Click()
                                // Add more cases if needed for other banners
                                else -> onBanner1Click() // Default fallback
                            }
                        },
                        autoScrollDelay = 2000,
                        height = 450.dp,
                        dotSize = 8.dp,
                        modifier = Modifier.padding(bottom = 8.dp) // Reduced from 12.dp to 4.dp
                    )
//                    Spacer(
//                        modifier = Modifier
//                            .height(2.dp)
//                            .fillMaxWidth()
//                            .background(MaterialTheme.customColors.spacerColor)
//                    )
                    val fusionCategories = listOf(
                        Category(0, "Top At", R.drawable.shirts_fusion),
                        Category(1, "What's New", R.drawable.tops_fusion),
                        Category(2, "Brands To Bag", R.drawable.jeans_fusion),
                        Category(3, "Fusion Deals", R.drawable.l_and_l_fusion),
                         )
                    CarouselFashionOne(
                        categories = fusionCategories,
                        onTabSelected = { categoryName ->
                            println("hello")
//                            onTabSelected(categoryName) // Then call the original callback
                        },
                        modifier = Modifier,
                        backgroundColor = MaterialTheme.customColors.white,
                        itemWidth = 75,
                        itemHeight = 90,
                        horizontalSpacing = 8
                    )

                    // Trendy Jackpot Simple
                    val trendyCategoriesSimple = listOf(
                        CategoryItem(0, "", R.drawable.ic_new_fusion_1, ""),
                        CategoryItem(1, "", R.drawable.ic_new_fusion_2, ""),
                        CategoryItem(2, "", R.drawable.ic_new_fusion_3, ""),
                        CategoryItem(3, "", R.drawable.ic_new_fusion_4, ""),
                        CategoryItem(4, "", R.drawable.ic_new_fusion_5, ""),
                        CategoryItem(5, "", R.drawable.ic_new_fusion_6, ""),
                        CategoryItem(6, "", R.drawable.ic_new_fusion_7, ""),
                        CategoryItem(7, "", R.drawable.ic_new_fusion_8, ""),
                        CategoryItem(8, "", R.drawable.ic_new_fusion_9, ""),
                    )
                    Image(
                        painter = painterResource(R.drawable.ic_fusion_header_trendy),
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
                        items = trendyCategoriesSimple,
                        onItemClick = { item -> println("Selected: ${item.name}") },
                        itemWidth = 200.dp,
                        itemHeight = 250.dp,
                        horizontalSpacing = 12.dp,
//                        verticalPadding = 8.dp,
                        horizontalPadding = 12.dp,
                        backgroundColor = Color(0xFFFDFDF1)
//                        backgroundColor = Color(0xFFFFF8E1)
                    )

                    // Brands in Spotlight Categories Simple
                    val brandsCategoriesSimple = listOf(
                        CategoryItem(0, "", R.drawable.ic_fusion_brands_1, ""),
                        CategoryItem(1, "", R.drawable.ic_fusion_brands_2, ""),
                        CategoryItem(2, "", R.drawable.ic_fusion_brands_3, ""),
                        CategoryItem(3, "", R.drawable.ic_fusion_brands_4, ""),
                        CategoryItem(4, "", R.drawable.ic_fusion_brands_5, ""),
                        CategoryItem(5, "", R.drawable.ic_fusion_brands_6, ""),
                    )
                    Image(
                        painter = painterResource(R.drawable.ic_fusion_header_brands),
                        contentDescription = "Banner",
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = 100.dp, max = 300.dp), // Height between min and max, // 30% of screen height, // Sets height based on width and aspect ratio
                        contentScale = ContentScale.FillBounds
                    )
                    CategoryListDouble(
                        items = brandsCategoriesSimple,
                        onItemClick = { item -> println("Selected: ${item.name}") },
                        showOverlayOnImage = false,
                        showItemName = false,
                        itemWidth = 160.dp,
                        itemHeight = 220.dp,
                        horizontalSpacing = 12.dp,
                        verticalPadding = 12.dp,
                        backgroundColor = Color(0xFFFFFFFF),
//                        showOverlayOnImage = false,
//                        overlayBackground = Color.Black.copy(alpha = 0.6f),

                    )

                    // Silhouette Story Simple
                    val silhouetteCategoriesSimple = listOf(
                        CategoryItem(0, "", R.drawable.ic_silhouette_fusion_1, ""),
                        CategoryItem(1, "", R.drawable.ic_silhouette_fusion_2, ""),
                        CategoryItem(2, "", R.drawable.ic_silhouette_fusion_3, ""),
                        CategoryItem(3, "", R.drawable.ic_silhouette_fusion_4, ""),
                        CategoryItem(4, "", R.drawable.ic_silhouette_fusion_5, ""),
                        CategoryItem(5, "", R.drawable.ic_silhouette_fusion_6, ""),
                    )
                    Image(
                        painter = painterResource(R.drawable.ic_silhouette_header_fusion),
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
                        items = silhouetteCategoriesSimple,
                        onItemClick = { item -> println("Selected: ${item.name}") },
                        itemWidth = 200.dp,
                        itemHeight = 250.dp,
                        horizontalSpacing = 12.dp,
//                        verticalPadding = 8.dp,
                        horizontalPadding = 12.dp,
                        backgroundColor = Color(0xFFFDFDF1)
//                        backgroundColor = Color(0xFFA54086)
                    )
                    val sampleProducts = listOf(
                        ProductListGrid("Iki Chic", "Starting at ₹399", R.drawable.ic_explore_more_fusion_1),
                        ProductListGrid("Trendy Wear", "Starting at ₹1299", R.drawable.ic_explore_more_fusion_2),
                        ProductListGrid("Summer Sera", "Starting at ₹750", R.drawable.ic_explore_more_fusion_3),
                        ProductListGrid("Rebel Tee", "Starting at ₹999", R.drawable.ic_explore_more_fusion_4),
                        ProductListGrid("Smart Casual", "Starting at ₹799", R.drawable.ic_explore_more_fusion_5),
                        ProductListGrid("Daily Wear", "Starting at ₹400", R.drawable.ic_explore_more_fusion_6),
                        ProductListGrid("Sera Classic", "Starting at ₹350", R.drawable.ic_explore_more_fusion_7),
                        ProductListGrid("Stylish Top", "Starting at ₹499", R.drawable.ic_explore_more_fusion_8),
                        ProductListGrid("Trendy Shirt", "Starting at ₹400", R.drawable.ic_explore_more_fusion_9),
                        ProductListGrid("Dilinger Hoodie", "Starting at ₹599", R.drawable.ic_explore_more_fusion_10),
                        ProductListGrid("Fnocks Jacket", "Starting at ₹399", R.drawable.ic_explore_more_fusion_11),
                        ProductListGrid("Rebelmme Tee", "Starting at ₹599", R.drawable.ic_explore_more_fusion_12)
                    )

// Display CategoryListGrid showing **name only**
                    Image(
                        painter = painterResource(R.drawable.ic_explore_more_header_list_grid_fusion),
                        contentDescription = "Banner",
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(
                                min = 100.dp,
                                max = 300.dp
                            ), // Height between min and max, // 30% of screen height, // Sets height based on width and aspect ratio
                        contentScale = ContentScale.FillBounds
                    )
                    CategoryListGrid(
                        products = sampleProducts,
                        columns = 3,
                        gridHeight = 945.dp, // fixed height to avoid crashes
                        showName = false,
                        showPrice = true,   // hide price
                        imageAspectRatio = 3f / 4.5f,
//                        defaultCardColor = Color(0xFFB3212E),
                        defaultCardColor = Color(0xFFF5F2ED),
                        onItemClick = { product ->
                            println("Clicked on ${product.name}")
                        }
                    )
                }
            }

            if (name.lowercase() == "essentials") {
                item {
                    // Banner images for Essentials category
                    val bannerImages = listOf(
                        painterResource(id = R.drawable.essentials_banner1),
                        painterResource(id = R.drawable.essentials_banner2),
                        painterResource(id = R.drawable.essentials_banner8),
                        painterResource(id = R.drawable.essentials_banner3),
                        painterResource(id = R.drawable.essentials_banner5),
                        painterResource(id = R.drawable.essentials_banner11),
                        painterResource(id = R.drawable.essentials_banner7),
                        painterResource(id = R.drawable.essentials_banner10),
                        painterResource(id = R.drawable.essentials_banner4),
                        painterResource(id = R.drawable.essentials_banner6),
                        painterResource(id = R.drawable.essentials_banner9),
                        painterResource(id = R.drawable.essentials_banner12),
                        painterResource(id = R.drawable.essentials_banner13),
                    )
                    BannerFashion(
                        images = bannerImages,
                        onImageClick = { page ->
                            when (page) {
                                0 -> onBanner1Click()
                                1 -> onBanner2Click()
                                2 -> onBanner3Click()
                                // Add more cases if needed for other banners
                                else -> onBanner1Click() // Default fallback
                            }
                        },
                        autoScrollDelay = 2000,
                        height = 500.dp,
//                        dotSize = 8.dp,
                        modifier = Modifier.padding(bottom = 8.dp) // Reduced from 12.dp to 4.dp
                    )

                    val essentialsCategories = listOf(
                        Category(0, "Bras", R.drawable.bras_essentials),
                        Category(1, "Panties", R.drawable.panties_essentials),
                        Category(2, "Night Suits", R.drawable.night_suits_essentials),
                        Category(3, "Shapewear", R.drawable.shapewear_essentials),
                        Category(4, "Stockings", R.drawable.stockings_essentials),
                        Category(5, "Thermals", R.drawable.thermals_essentials),
                        Category(6, "Maternity Wear", R.drawable.maternity_wear_essentials),
                        Category(7, "Swimwear", R.drawable.swimwear_essentials),
                        Category(8, "Babydolls", R.drawable.babydolls_essentials),
                        Category(9, "Lingerie Accessories", R.drawable.lingerie_accessories_essentials),
                        Category(10, "Saree Shapewear", R.drawable.saree_shapewear_essentials)
                    )
                    CarouselFashionOne(
                        categories = essentialsCategories,
                        onTabSelected = { categoryName ->
                            println("hello")  
//                            onTabSelected(categoryName) // Then call the original callback
                        },
                        modifier = Modifier,
                        backgroundColor = MaterialTheme.customColors.white,
                        itemWidth = 75,
                        itemHeight = 90,
                        horizontalSpacing = 8
                    )

                    // Banner images for Brands in Focus category
                    Image(
                        painter = painterResource(R.drawable.ic_brands_header_fusion),
                        contentDescription = "Banner",
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(
                                min = 100.dp,
                                max = 300.dp
                            ), // Height between min and max, // 30% of screen height, // Sets height based on width and aspect ratio
                        contentScale = ContentScale.FillBounds
                    )
                    val bannerImagesBrands = listOf(
                        painterResource(id = R.drawable.brands_banner1),
                        painterResource(id = R.drawable.brands_banner2),
                        painterResource(id = R.drawable.brands_banner3),
                        painterResource(id = R.drawable.brands_banner4),
                        painterResource(id = R.drawable.brands_banner5),
                        painterResource(id = R.drawable.brands_banner6),
                        painterResource(id = R.drawable.brands_banner7),
                        painterResource(id = R.drawable.brands_banner8),
                    )
                    BannerFashion(
                        images = bannerImagesBrands,
                        onImageClick = { page ->
                            when (page) {
                                0 -> onBanner1Click()
                                1 -> onBanner2Click()
                                2 -> onBanner3Click()
                                // Add more cases if needed for other banners
                                else -> onBanner1Click() // Default fallback
                            }
                        },
                        autoScrollDelay = 2000,
                        height = 200.dp,
                        dotSize = 0.dp,
                        modifier = Modifier.padding(bottom = 0.dp) // Reduced from 12.dp to 4.dp
                    )

                    // Deals So Irresistible Simple
                    val sampleProducts = listOf(
                        ProductListGrid("Iki Chic", "Starting at ₹399", R.drawable.ic_deals_essentials_1),
                        ProductListGrid("Trendy Wear", "Starting at ₹1299", R.drawable.ic_deals_essentials_2),
                        ProductListGrid("Summer Sera", "Starting at ₹750", R.drawable.ic_deals_essentials_3),
                        ProductListGrid("Rebel Tee", "Starting at ₹999", R.drawable.ic_deals_essentials_4),
                        ProductListGrid("Smart Casual", "Starting at ₹799", R.drawable.ic_deals_essentials_5),
                        ProductListGrid("Daily Wear", "Starting at ₹400", R.drawable.ic_deals_essentials_6),
                    )

                    // Display CategoryListGrid showing **name only**
                    Image(
                        painter = painterResource(R.drawable.ic_fusion_header_deals),
                        contentDescription = "Banner",
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(
                                min = 100.dp,
                                max = 300.dp
                            ), // Height between min and max, // 30% of screen height, // Sets height based on width and aspect ratio
                        contentScale = ContentScale.FillBounds
                    )

                    CategoryListGrid(
                        products = sampleProducts,
                        columns = 3,
                        gridHeight = 472.dp, // fixed height to avoid crashes
                        showName = false,
                        showPrice = false,   // hide price
                        imageAspectRatio = 3f / 5f,
//                        defaultCardColor = Color(0xFFB3212E),
                        defaultCardColor = Color(0xFFFFFFFF),
                        onItemClick = { product ->
                            println("Clicked on ${product.name}")
                        }
                    )

                    // Cloude Like Comfort Simple
                    val cloudeCategoriesSimple = listOf(
                        CategoryItem(0, "", R.drawable.ic_cloude_fusion_1, ""),
                        CategoryItem(1, "", R.drawable.ic_cloude_fusion_2, ""),
                        CategoryItem(2, "", R.drawable.ic_cloude_fusion_3, ""),
                        CategoryItem(3, "", R.drawable.ic_cloude_fusion_4, ""),
                        CategoryItem(4, "", R.drawable.ic_cloude_fusion_5, ""),
                        CategoryItem(5, "", R.drawable.ic_cloude_fusion_6, ""),
                    )
                    Image(
                        painter = painterResource(R.drawable.ic_cloude_header_fusion),
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
                        items = cloudeCategoriesSimple,
                        onItemClick = { item -> println("Selected: ${item.name}") },
                        itemWidth = 200.dp,
                        itemHeight = 250.dp,
                        horizontalSpacing = 12.dp,
//                        verticalPadding = 8.dp,
                        horizontalPadding = 12.dp,
                        backgroundColor = Color(0xFFFDFDF1)
//                        backgroundColor = Color(0xFFA54086)
                    )

                    // Lingerie trends Simple
                    val lingerieCategoriesSimple1 = listOf(
                        CategoryItem(0, "", R.drawable.ic_lingerie_fusion_1, ""),
                        CategoryItem(1, "", R.drawable.ic_lingerie_fusion_2, ""),
                        CategoryItem(2, "", R.drawable.ic_lingerie_fusion_3, ""),
                        CategoryItem(3, "", R.drawable.ic_lingerie_fusion_4, ""),
                    )
                    Image(
                        painter = painterResource(R.drawable.ic_lingerie_header_fusion),
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
                        items = lingerieCategoriesSimple1,
                        onItemClick = { item -> println("Selected: ${item.name}") },
                        itemWidth = 250.dp,
                        itemHeight = 350.dp,
                        horizontalSpacing = 12.dp,
//                        verticalPadding = 8.dp,
                        horizontalPadding = 12.dp,
                        backgroundColor = Color(0xFFFFFFFF)
                    )

                    // Spice Up Your  Wardrobe Simple
                    val spiceProducts = listOf(
                        ProductListGrid("Iki Chic", "Starting at ₹399", R.drawable.ic_spice_fusion_1),
                        ProductListGrid("Trendy Wear", "Starting at ₹1299", R.drawable.ic_spice_fusion_2),
                        ProductListGrid("Summer Sera", "Starting at ₹750", R.drawable.ic_spice_fusion_3),
                        ProductListGrid("Rebel Tee", "Starting at ₹999", R.drawable.ic_spice_fusion_4),
                        ProductListGrid("Smart Casual", "Starting at ₹799", R.drawable.ic_spice_fusion_5),
                        ProductListGrid("Daily Wear", "Starting at ₹400", R.drawable.ic_spice_fusion_6),
                    )

                    Image(
                        painter = painterResource(R.drawable.ic_fusion_header_spice),
                        contentDescription = "Banner",
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(
                                min = 100.dp,
                                max = 300.dp
                            ), // Height between min and max, // 30% of screen height, // Sets height based on width and aspect ratio
                        contentScale = ContentScale.FillBounds
                    )

                    CategoryListGrid(
                        products = spiceProducts,
                        columns = 2,
                        gridHeight = 820.dp, // fixed height to avoid crashes
                        showName = false,
                        showPrice = false,   // hide price
                        imageAspectRatio = 3f / 4f,
                        defaultCardColor = Color(0xFFFFFFFF),
                        onItemClick = { product ->
                            println("Clicked on ${product.name}")
                        }
                    )

                    // Lingerie For Every Occasion Simple
                    val lingerieCategoriesSimple = listOf(
                        CategoryItem(0, "", R.drawable.ic_lingerie_occasion_fusion_1, ""),
                        CategoryItem(1, "", R.drawable.ic_lingerie_occasion_fusion_2, ""),
                        CategoryItem(2, "", R.drawable.ic_lingerie_occasion_fusion_3, ""),
                        CategoryItem(3, "", R.drawable.ic_lingerie_occasion_fusion_4, ""),
                    )
                    Image(
                        painter = painterResource(R.drawable.ic_lingerie_occasion_header_fusion),
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
                        items = lingerieCategoriesSimple,
                        onItemClick = { item -> println("Selected: ${item.name}") },
                        itemWidth = 200.dp,
                        itemHeight = 280.dp,
                        horizontalSpacing = 12.dp,
//                        verticalPadding = 8.dp,
                        horizontalPadding = 12.dp,
                        backgroundColor = Color(0xFFFFF0F5)
                    )

                    // Brands We love Simple
                    val brandsLoveProducts = listOf(
                        ProductListGrid("", "Up to 70% off", R.drawable.ic_brands_love_fusion_1),
                        ProductListGrid("", "Up to 60% off", R.drawable.ic_brands_love_fusion_2),
                        ProductListGrid("", "Up to 70% off", R.drawable.ic_brands_love_fusion_3),
                        ProductListGrid("", "Up to 40% off", R.drawable.ic_brands_love_fusion_4),
                        ProductListGrid("", "Up to 35% off", R.drawable.ic_brands_love_fusion_5),
                        ProductListGrid("", "Up to 60% off", R.drawable.ic_brands_love_fusion_6),
                        ProductListGrid("", "Min 20% off", R.drawable.ic_brands_love_fusion_7),
                        ProductListGrid("", "Starting at ₹219", R.drawable.ic_brands_love_fusion_8),
                        ProductListGrid("", "Up to 40% off", R.drawable.ic_brands_love_fusion_9),
                    )

                    // Display CategoryListGrid showing **name only**
                    Image(
                        painter = painterResource(R.drawable.ic_fusion_header_brands_love),
                        contentDescription = "Banner",
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(
                                min = 100.dp,
                                max = 300.dp
                            ), // Height between min and max, // 30% of screen height, // Sets height based on width and aspect ratio
                        contentScale = ContentScale.FillBounds
                    )

                    CategoryListGrid(
                        products = brandsLoveProducts,
                        columns = 3,
                        gridHeight = 630.dp, // fixed height to avoid crashes
                        showName = false,
                        showPrice = true,   // hide price
                        imageAspectRatio = 3f / 4f,
                        defaultCardColor = Color(0xFFE1F6F1),
                        onItemClick = { product ->
                            println("Clicked on ${product.name}")
                        }
                    )

                    // Bold & Beautiful Simple
                    val boldCategoriesSimple = listOf(
                        CategoryItem(0, "", R.drawable.ic_bold_beautiful_fusion_1, ""),
                        CategoryItem(1, "", R.drawable.ic_bold_beautiful_fusion_2, ""),
                        CategoryItem(2, "", R.drawable.ic_bold_beautiful_fusion_3, ""),
                        CategoryItem(3, "", R.drawable.ic_bold_beautiful_fusion_4, ""),
                    )
                    Image(
                        painter = painterResource(R.drawable.ic_bold_beautiful_header_fusion),
                        contentDescription = "Banner",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(350.dp), // Height between min and max, // 30% of screen height, // Sets height based on width and aspect ratio
                        contentScale = ContentScale.FillBounds
                    )
                    CategoryListSimple(
                        items = boldCategoriesSimple,
                        onItemClick = { item -> println("Selected: ${item.name}") },
                        itemWidth = 200.dp,
                        itemHeight = 200.dp,
                        horizontalSpacing = 12.dp,
//                        verticalPadding = 8.dp,
                        horizontalPadding = 12.dp,
                        backgroundColor = Color(0xFFCBEFFF)
//                        backgroundColor = Color(0xFFA54086)
                    )

                    // Sleepwear Brands We Love Simple
                    val sleepwearProducts = listOf(
                        ProductListGrid("", "Up to 70% off", R.drawable.ic_sleepwear_fusion_1),
                        ProductListGrid("", "Up to 60% off", R.drawable.ic_sleepwear_fusion_2),
                        ProductListGrid("", "Up to 70% off", R.drawable.ic_sleepwear_fusion_3),
                        ProductListGrid("", "Up to 40% off", R.drawable.ic_sleepwear_fusion_4),
                    )

                    // Display CategoryListGrid showing **name only**
                    Image(
                        painter = painterResource(R.drawable.ic_fusion_header_sleepwear),
                        contentDescription = "Banner",
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(
                                min = 100.dp,
                                max = 300.dp
                            ), // Height between min and max, // 30% of screen height, // Sets height based on width and aspect ratio
                        contentScale = ContentScale.FillBounds
                    )

                    CategoryListGrid(
                        products = sleepwearProducts,
                        columns = 2,
                        gridHeight = 550.dp, // fixed height to avoid crashes
                        showName = false,
                        showPrice = false,   // hide price
                        imageAspectRatio = 3f / 4f,
                        defaultCardColor = Color(0xFFFFFFFF),
                        onItemClick = { product ->
                            println("Clicked on ${product.name}")
                        }
                    )
                }
            }

//            Beauty
            if (name.lowercase() == "beauty") {
                item {
                    // Banner images for Essentials category
                    val bannerImages = listOf(
                        painterResource(id = R.drawable.beauty_banner1),
                        painterResource(id = R.drawable.beauty_banner2),
                        painterResource(id = R.drawable.beauty_banner8),
                        painterResource(id = R.drawable.beauty_banner3),
                        painterResource(id = R.drawable.beauty_banner5),
                        painterResource(id = R.drawable.beauty_banner11),
                        painterResource(id = R.drawable.beauty_banner7),
                        painterResource(id = R.drawable.beauty_banner10),
                        painterResource(id = R.drawable.beauty_banner4),
                        painterResource(id = R.drawable.beauty_banner6),
                        painterResource(id = R.drawable.beauty_banner9),
                        painterResource(id = R.drawable.beauty_banner12),
                        painterResource(id = R.drawable.beauty_banner13),
                        painterResource(id = R.drawable.beauty_banner14),
                    )
                    BannerFashion(
                        images = bannerImages,
                        onImageClick = { page ->
                            when (page) {
                                0 -> onBanner1Click()
                                1 -> onBanner2Click()
                                2 -> onBanner3Click()
                                // Add more cases if needed for other banners
                                else -> onBanner1Click() // Default fallback
                            }
                        },
                        autoScrollDelay = 2000,
                        height = 400.dp,
//                        dotSize = 8.dp,
                        modifier = Modifier.padding(bottom = 8.dp) // Reduced from 12.dp to 4.dp
                    )

                    // Beauty Products List
                    val beautyCategoriesList = remember {
                        listOf(
                            Category(0, "Fem Care", R.drawable.ic_fem_care_beauty),
                            Category(1, "Makeup", R.drawable.ic_makeup_beauty),
                            Category(2, "Skincare", R.drawable.ic_skincare_beauty),
                            Category(3, "Haircare", R.drawable.ic_haircare_beauty),
                            Category(4, "Fragrance", R.drawable.ic_fragrance_beauty),
                            Category(5, "Rainkissed Beauty", R.drawable.ic_rainkissed_beauty),
                            Category(6, "Baby Care", R.drawable.ic_baby_care_beauty),
                            Category(7, "Explore", R.drawable.ic_explore_beauty),
                            Category(8, "Wellness", R.drawable.ic_wellness_beauty),
                            Category(9, "Dermacosmetics", R.drawable.ic_dermacosmetics_beauty),
                            Category(10, "Luxe", R.drawable.ic_luxe_beauty),
                            Category(11, "K-Glow Studio", R.drawable.ic_k_glow_studio_beauty),
                            Category(12, "Bath & Body", R.drawable.ic_bath_body_beauty),
                            Category(13, "Straightener", R.drawable.ic_straightener_beauty)
                        )
                    }
//
                    var selectedCategory by remember { mutableStateOf<Category?>(null) }

                    CategoryProducts(
                        categories = beautyCategoriesList,
                        onCategorySelected = { category ->
                            selectedCategory = category
                            println("Selected category: ${category.name}")
                        },
                        modifier = Modifier.fillMaxWidth(),
                        initialSelectedCategory = beautyCategoriesList.first(),
                        itemWidth = 75,
                        itemHeight = 85,
                        horizontalSpacing = 8,
                        verticalSpacing = 8,
                        backgroundColor = MaterialTheme.customColors.white
                    )

                    // deals You Can't Miss Categories Simple
                    val brandsCategoriesSimple = listOf(
                        CategoryItem(0, "", R.drawable.ic_deals_beauty_brands_1, ""),
                        CategoryItem(1, "", R.drawable.ic_deals_beauty_brands_2, ""),
                        CategoryItem(2, "", R.drawable.ic_deals_beauty_brands_3, ""),
                        CategoryItem(3, "", R.drawable.ic_deals_beauty_brands_4, ""),
                        CategoryItem(4, "", R.drawable.ic_deals_beauty_brands_5, ""),
                        CategoryItem(5, "", R.drawable.ic_deals_beauty_brands_6, ""),
                        CategoryItem(5, "", R.drawable.ic_deals_beauty_brands_7, ""),
                        CategoryItem(5, "", R.drawable.ic_deals_beauty_brands_8, ""),
                        CategoryItem(5, "", R.drawable.ic_deals_beauty_brands_9, ""),
                        CategoryItem(5, "", R.drawable.ic_deals_beauty_brands_10, ""),
                        CategoryItem(5, "", R.drawable.ic_deals_beauty_brands_11, ""),
                        CategoryItem(5, "", R.drawable.ic_deals_beauty_brands_12, ""),
                        CategoryItem(5, "", R.drawable.ic_deals_beauty_brands_13, ""),
                        CategoryItem(5, "", R.drawable.ic_deals_beauty_brands_14, ""),
                        CategoryItem(5, "", R.drawable.ic_deals_beauty_brands_15, ""),
                        CategoryItem(5, "", R.drawable.ic_deals_beauty_brands_16, ""),
                        CategoryItem(5, "", R.drawable.ic_deals_beauty_brands_17, ""),
                        CategoryItem(5, "", R.drawable.ic_deals_beauty_brands_18, ""),
                        CategoryItem(5, "", R.drawable.ic_deals_beauty_brands_19, ""),
                        CategoryItem(5, "", R.drawable.ic_deals_beauty_brands_20, ""),
                        CategoryItem(5, "", R.drawable.ic_deals_beauty_brands_21, ""),
                        CategoryItem(5, "", R.drawable.ic_deals_beauty_brands_22, ""),
                        CategoryItem(5, "", R.drawable.ic_deals_beauty_brands_23, ""),
                        CategoryItem(5, "", R.drawable.ic_deals_beauty_brands_24, ""),
                        CategoryItem(5, "", R.drawable.ic_deals_beauty_brands_25, ""),
                        CategoryItem(5, "", R.drawable.ic_deals_beauty_brands_26, ""),
                        CategoryItem(5, "", R.drawable.ic_deals_beauty_brands_27, ""),
                        CategoryItem(5, "", R.drawable.ic_deals_beauty_brands_28, ""),
                        CategoryItem(5, "", R.drawable.ic_deals_beauty_brands_29, ""),
                        CategoryItem(5, "", R.drawable.ic_deals_beauty_brands_30, ""),
                        CategoryItem(5, "", R.drawable.ic_deals_beauty_brands_31, ""),
                        CategoryItem(5, "", R.drawable.ic_deals_beauty_brands_32, ""),
                        CategoryItem(5, "", R.drawable.ic_deals_beauty_brands_33, ""),
                        CategoryItem(5, "", R.drawable.ic_deals_beauty_brands_34, ""),
                        CategoryItem(5, "", R.drawable.ic_deals_beauty_brands_35, ""),
                        CategoryItem(5, "", R.drawable.ic_deals_beauty_brands_36, ""),
                        CategoryItem(5, "", R.drawable.ic_deals_beauty_brands_37, ""),
                        CategoryItem(5, "", R.drawable.ic_deals_beauty_brands_38, ""),
                        CategoryItem(5, "", R.drawable.ic_deals_beauty_brands_39, ""),
                    )
                    Image(
                        painter = painterResource(R.drawable.ic_deals_beauty_header_brands),
                        contentDescription = "Banner",
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = 100.dp, max = 300.dp), // Height between min and max, // 30% of screen height, // Sets height based on width and aspect ratio
                        contentScale = ContentScale.FillBounds
                    )
                    CategoryListDouble(
                        items = brandsCategoriesSimple,
                        onItemClick = { item -> println("Selected: ${item.name}") },
                        showOverlayOnImage = false,
                        showItemName = false,
                        itemWidth = 160.dp,
                        itemHeight = 220.dp,
                        horizontalSpacing = 12.dp,
                        verticalPadding = 12.dp,
                        backgroundColor = Color(0xFFFFFFFF),
//                        showOverlayOnImage = false,
//                        overlayBackground = Color.Black.copy(alpha = 0.6f),

                    )

                }
            }

            if (name.lowercase() == "footwear") {
                item {
                    // Banner images for Essentials category
                    val bannerImages = listOf(
                        painterResource(id = R.drawable.footwear_banner1),
                        painterResource(id = R.drawable.footwear_banner2),
                        painterResource(id = R.drawable.footwear_banner3),
                        painterResource(id = R.drawable.footwear_banner4),
                        painterResource(id = R.drawable.footwear_banner5),
                        painterResource(id = R.drawable.footwear_banner6),
                    )
                    BannerFashion(
                        images = bannerImages,
                        onImageClick = { page ->
                            when (page) {
                                0 -> onBanner1Click()
                                1 -> onBanner2Click()
                                2 -> onBanner3Click()
                                // Add more cases if needed for other banners
                                else -> onBanner1Click() // Default fallback
                            }
                        },
                        autoScrollDelay = 2000,
                        height = 400.dp,
//                        dotSize = 8.dp,
                        modifier = Modifier.padding(bottom = 8.dp) // Reduced from 12.dp to 4.dp
                    )

                    // Footwear Categories List
                    val footwearCategoriesList = remember {
                        listOf(
                            Category(0, "Heels", R.drawable.ic_heels_footwear),
                            Category(1, "Flats", R.drawable.ic_flats_footwear),
                            Category(2, "Sandals", R.drawable.ic_sandals_footwear),
                            Category(3, "Sneakers", R.drawable.ic_sneakers_footwear),
                            Category(4, "Sports Shoes", R.drawable.ic_sports_shoes_footwear),
                            Category(5, "Flip Flops", R.drawable.ic_flip_flops_footwear),
                            Category(6, "Ethnic", R.drawable.ic_ethnic_footwear),
                            Category(7, "Casual Shoes", R.drawable.ic_casual_shoes_footwear),
                            Category(8, "Boots", R.drawable.ic_boots_footwear),
                            Category(9, "Formal Shoes", R.drawable.ic_formal_shoes_footwear),
                            Category(10, "Shop All", R.drawable.ic_shop_all_footwear)
                        )
                    }
//
                    var selectedCategory by remember { mutableStateOf<Category?>(null) }

                    CategoryProducts(
                        categories = footwearCategoriesList,
                        onCategorySelected = { category ->
                            selectedCategory = category
                            println("Selected category: ${category.name}")
                        },
                        modifier = Modifier.fillMaxWidth(),
                        initialSelectedCategory = footwearCategoriesList.first(),
                        itemWidth = 75,
                        itemHeight = 75,
                        horizontalSpacing = 8,
                        verticalSpacing = 8,
                        backgroundColor = MaterialTheme.customColors.white
                    )
                }
            }



                    // Category items list
//            items(categoryItems) { (imageRes, title) ->
//                Row(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .background(MaterialTheme.colorScheme.surfaceVariant)
//                        .padding(12.dp)
//                        .clickable {
//                            // Handle item click - you can navigate to a product list screen
//                            // or perform other actions here
//                        },
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    Image(
//                        painter = painterResource(id = imageRes),
//                        contentDescription = title,
//                        modifier = Modifier
//                            .size(60.dp)
//                            .padding(end = 12.dp)
//                    )
//                    Text(
//                        text = title,
//                        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium),
//                        color = MaterialTheme.colorScheme.onSurface
//                    )
//                }
//            }
        }
    }
}