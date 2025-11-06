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

data class Category(
    val id: Int,
    val name: String,
    val iconRes: Int
)

@Composable
fun CarouselFashionOne(
    categories: List<Category>,
    onTabSelected: (String, Int) -> Unit,
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
                    onTabSelected(categories[index].name, categories[index].id)
                },
                itemWidth = itemWidth,
                itemHeight = itemHeight,
                backgroundColor = backgroundColor,
                textColor = textColor
            )
        }
    }
}

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
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
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

@Composable
fun WomenFashionCarousel(
    onTabSelected: (String, Int) -> Unit,
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

@Composable
fun MenFashionCarousel(
    onTabSelected: (String, Int) -> Unit,
    modifier: Modifier = Modifier
) {
    CarouselFashionOne(
        categories = menFashionCategories,
        onTabSelected = onTabSelected,
        modifier = modifier,
        backgroundColor = MaterialTheme.customColors.white,
        itemWidth = 75,
        itemHeight = 65,
        horizontalSpacing = 12
    )
}

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
                onTabSelected = { categoryName, categoryId ->
                    println("Selected: $categoryName with ID: $categoryId")
                }
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Men's Fashion",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            MenFashionCarousel(
                onTabSelected = { categoryName, categoryId ->
                    println("Selected: $categoryName with ID: $categoryId")
                }
            )
        }
    }
}