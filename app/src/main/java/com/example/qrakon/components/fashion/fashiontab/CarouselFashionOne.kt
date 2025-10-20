package com.example.qrakon.components.categorytabs

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.qrakon.ui.theme.customColors
import com.example.qrakon.R

/**
 * Reusable component for displaying fashion categories in a single row horizontal scroll layout
 * with tab navigation functionality
 *
 * @param categories List of categories to display
 * @param onTabSelected Callback function when a category tab is selected
 * @param modifier Modifier for the component
 * @param itemWidth Width of each category item
 * @param itemHeight Height of each category item
 * @param horizontalSpacing Spacing between items
 * @param horizontalPadding Horizontal padding for the entire component
 * @param verticalPadding Vertical padding for the entire component
 * @param backgroundColor Background color for the category items
 * @param textColor Text color for category names
 * @param showItemCount Whether to show the number of items in the carousel (for debugging)
 */

data class Category(
    val id: Int,
    val name: String,
    val iconRes: Int
)

@Composable
fun CarouselFashionOne(
    categories: List<Category>,
    onTabSelected: (String) -> Unit,
    modifier: Modifier = Modifier,
    itemWidth: Int = 75,
    itemHeight: Int = 75,
    horizontalSpacing: Int = 8,
    horizontalPadding: Int = 12,
    verticalPadding: Int = 6,
    backgroundColor: Color = MaterialTheme.customColors.imageBgColor1,
    textColor: Color = Color.Black,
    showItemCount: Boolean = false
) {
    require(categories.isNotEmpty()) { "Categories list cannot be empty" }

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(horizontalSpacing.dp),
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = horizontalPadding.dp, vertical = verticalPadding.dp)
    ) {
        if (showItemCount) {
            item {
                Text(
                    text = "Items: ${categories.size}",
                    fontSize = 10.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(end = 8.dp)
                )
            }
        }

        items(categories.size) { index ->
            FashionCategoryItem(
                category = categories[index],
                onClick = {
                    onTabSelected(categories[index].name)
                },
                itemWidth = itemWidth,
                itemHeight = itemHeight,
                backgroundColor = backgroundColor,
                textColor = textColor
            )
        }
    }
}

/**
 * Individual fashion category item component for single row layout
 *
 * @param category The category to display
 * @param onClick Callback when the category is clicked
 * @param itemWidth Width of the category item
 * @param itemHeight Height of the category item
 * @param backgroundColor Background color for the category item
 * @param textColor Text color for category name
 */
@Composable
fun FashionCategoryItem(
    category: Category,
    onClick: () -> Unit,
    itemWidth: Int = 70,
    itemHeight: Int = 60,
    backgroundColor: Color = MaterialTheme.customColors.imageBgColor1,
    textColor: Color = Color.Black
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(itemWidth.dp)
            .clickable(onClick = onClick)
    ) {
        Box(
            modifier = Modifier
                .height(itemHeight.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(backgroundColor),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = category.iconRes),
                contentDescription = category.name,
                contentScale = ContentScale.FillBounds, // use Crop for better corner clipping
//                contentScale = ContentScale.Crop, // use Crop for better corner clipping
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
                    .clip(RoundedCornerShape(8.dp))    // ⬅ Rounded corners
            )

//            Image(
//                painter = painterResource(id = category.iconRes),
//                contentDescription = category.name,
//                contentScale = ContentScale.Fit,
//                modifier = Modifier
//                    .fillMaxSize()
//                    .padding(8.dp)
//            )
        }

        Spacer(modifier = Modifier.height(2.dp))

        Text(
            text = category.name,
            fontSize = 12.sp,
            textAlign = TextAlign.Center,
            color = textColor,
            fontWeight = FontWeight.Medium,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            lineHeight = 14.sp,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

/**
 * Predefined category lists for different fashion sections
 */

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

// Men's Fashion Categories
//val menFashionCategories = listOf(
//    Category(0, "Shirts", R.drawable.ic_shirts),
//    Category(1, "T-Shirts", R.drawable.ic_tshirts),
//    Category(2, "Jeans", R.drawable.ic_jeans_men),
//    Category(3, "Formal Wear", R.drawable.ic_formal_wear),
//    Category(4, "Footwear", R.drawable.ic_footwear_men),
//    Category(5, "Watches", R.drawable.ic_watches),
//    Category(6, "Accessories", R.drawable.ic_accessories_men),
//    Category(7, "Sportswear", R.drawable.ic_sportswear),
//    Category(8, "Winter Wear", R.drawable.ic_winter_wear_men),
//    Category(9, "Grooming", R.drawable.ic_grooming),
//    Category(10, "Sunglasses", R.drawable.ic_sunglasses),
//    Category(11, "View All", R.drawable.ic_view_all_home_tab),
//)

// Kids Fashion Categories
//val kidsFashionCategories = listOf(
//    Category(0, "Boys Wear", R.drawable.ic_boys_wear),
//    Category(1, "Girls Wear", R.drawable.ic_girls_wear),
//    Category(2, "Infants", R.drawable.ic_infants),
//    Category(3, "Footwear", R.drawable.ic_footwear_kids),
//    Category(4, "Toys", R.drawable.ic_toys),
//    Category(5, "Accessories", R.drawable.ic_accessories_kids),
//    Category(6, "School Wear", R.drawable.ic_school_wear),
//    Category(7, "Party Wear", R.drawable.ic_party_wear),
//    Category(8, "Winter Wear", R.drawable.ic_winter_wear_kids),
//    Category(9, "Diapers", R.drawable.ic_diapers),
//    Category(10, "Backpacks", R.drawable.ic_backpacks),
//    Category(11, "View All", R.drawable.ic_view_all_home_tab),
//)

// Home & Living Categories
//val homeFashionCategories = listOf(
//    Category(0, "Bedding", R.drawable.ic_bedding),
//    Category(1, "Decor", R.drawable.ic_decor),
//    Category(2, "Kitchen", R.drawable.ic_kitchen),
//    Category(3, "Bath", R.drawable.ic_bath),
//    Category(4, "Furniture", R.drawable.ic_furniture),
//    Category(5, "Lighting", R.drawable.ic_lighting),
//    Category(6, "Storage", R.drawable.ic_storage),
//    Category(7, "Garden", R.drawable.ic_garden),
//    Category(8, "Curtains", R.drawable.ic_curtains),
//    Category(9, "Rugs", R.drawable.ic_rugs),
//    Category(10, "Wall Art", R.drawable.ic_wall_art),
//    Category(11, "View All", R.drawable.ic_view_all_home_tab),
//)

// All Categories Overview
//val allFashionCategories = listOf(
//    Category(0, "Women", R.drawable.ic_women_fashion),
//    Category(1, "Men", R.drawable.ic_men_fashion),
//    Category(2, "Kids", R.drawable.ic_kids_fashion),
//    Category(3, "Home", R.drawable.ic_home_fashion),
//    Category(4, "Beauty", R.drawable.ic_beauty),
//    Category(5, "Accessories", R.drawable.ic_accessories_all),
//    Category(6, "Sale", R.drawable.ic_sale),
//    Category(7, "New Arrivals", R.drawable.ic_new_arrivals),
//    Category(8, "Brands", R.drawable.ic_brands),
//    Category(9, "Luxury", R.drawable.ic_luxury),
//    Category(10, "Sustainable", R.drawable.ic_sustainable),
//    Category(11, "View All", R.drawable.ic_view_all_home_tab),
//)

/**
 * Usage examples and convenience composables for different fashion sections
 */

@Composable
fun WomenFashionCarousel(
    onTabSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    CarouselFashionOne(
        categories = womenFashionCategories,
        onTabSelected = onTabSelected,
        modifier = modifier,
        backgroundColor = MaterialTheme.customColors.white,
        itemWidth = 75,
        itemHeight = 65,
        horizontalSpacing = 12
    )
}

//@Composable
//fun MenFashionCarousel(
//    onTabSelected: (String) -> Unit,
//    modifier: Modifier = Modifier
//) {
//    CarouselFashionOne(
//        categories = menFashionCategories,
//        onTabSelected = onTabSelected,
//        modifier = modifier,
//        backgroundColor = MaterialTheme.customColors.white,
//        itemWidth = 75,
//        itemHeight = 65,
//        horizontalSpacing = 12
//    )
//}

//@Composable
//fun KidsFashionCarousel(
//    onTabSelected: (String) -> Unit,
//    modifier: Modifier = Modifier
//) {
//    CarouselFashionOne(
//        categories = kidsFashionCategories,
//        onTabSelected = onTabSelected,
//        modifier = modifier,
//        backgroundColor = MaterialTheme.customColors.white,
//        itemWidth = 75,
//        itemHeight = 65,
//        horizontalSpacing = 12
//    )
//}
//
//@Composable
//fun HomeFashionCarousel(
//    onTabSelected: (String) -> Unit,
//    modifier: Modifier = Modifier
//) {
//    CarouselFashionOne(
//        categories = homeFashionCategories,
//        onTabSelected = onTabSelected,
//        modifier = modifier,
//        backgroundColor = MaterialTheme.customColors.white,
//        itemWidth = 75,
//        itemHeight = 65,
//        horizontalSpacing = 12
//    )
//}
//
//@Composable
//fun AllCategoriesCarousel(
//    onTabSelected: (String) -> Unit,
//    modifier: Modifier = Modifier
//) {
//    CarouselFashionOne(
//        categories = allFashionCategories,
//        onTabSelected = onTabSelected,
//        modifier = modifier,
//        backgroundColor = MaterialTheme.customColors.white,
//        itemWidth = 75,
//        itemHeight = 65,
//        horizontalSpacing = 12
//    )
//}

/**
 * Preview function for testing
 */
@androidx.compose.ui.tooling.preview.Preview(showBackground = true)
@Composable
fun CarouselFashionOnePreview() {
    MaterialTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp)
        ) {
            Text(
                text = "Women's Fashion",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            WomenFashionCarousel(
                onTabSelected = { categoryName ->
                    println("Selected: $categoryName")
                }
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Men's Fashion",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )

//            MenFashionCarousel(
//                onTabSelected = { categoryName ->
//                    println("Selected: $categoryName")
//                }
//            )
        }
    }
}