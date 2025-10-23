package com.example.qrakon.components.fashion.categorydetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.example.qrakon.components.fashion.fashiontab.BannerFashion
import com.example.qrakon.components.fashion.fashiontab.CategoryItem
import com.example.qrakon.components.fashion.fashiontab.CategoryListDouble
import com.example.qrakon.components.fashion.fashiontab.CategoryListDoubleWithHeading
import com.example.qrakon.components.fashion.fashiontab.CategoryListSimple
import com.example.qrakon.components.fashion.fashiontab.CategoryListSimpleWithHeading
import com.example.qrakon.components.fashion.fashiontab.CustomCardCategoryListItem
import com.example.qrakon.components.fashion.fashiontab.CustomCategoryListItem
import com.example.qrakon.components.fashion.fashiontab.roomCategoriesDouble
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
                        modifier = Modifier.padding(bottom = 4.dp) // Reduced from 12.dp to 4.dp
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
                        Category(8, "MLI", R.drawable.mli_western)
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