package com.example.qrakon.components.fashion.fashiontab

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.qrakon.R

@Composable
fun SubCategoryFList(
    title: String = "Shop by Category",
    categories: List<Pair<String, Int>> = emptyList(),
    showBrandsSection: Boolean = true,
    brandsImageRes: Int? = R.drawable.ic_shop_stylish_brands,
    brandsTitle: String = "Shop Stylish Brands",
    onCategoryClick: (String) -> Unit = {},
    onBrandsClick: () -> Unit = {}
) {
    val context = LocalContext.current

    val validCategories = remember(categories) {
        categories.filter { (_, resId) ->
            isResourceAvailable(context, resId)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Text(
            text = title,
            style = TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black
            ),
            modifier = Modifier.padding(bottom = 20.dp)
        )

        validCategories.forEachIndexed { index, (name, imageRes) ->
            Column(modifier = Modifier.fillMaxWidth()) {
                BasicCategoryItem(
                    imageRes = imageRes,
                    text = name,
                    onClick = { onCategoryClick(name) }
                )
                // Add border line after each item
                Spacer(modifier = Modifier.height(8.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(Color.LightGray.copy(alpha = 0.6f))
                )
            }

            // Add spacing between items except for the last one
            if (index != validCategories.lastIndex) {
                Spacer(modifier = Modifier.height(8.dp))
            }
        }

        if (showBrandsSection && brandsImageRes != null) {
            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = brandsTitle,
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black
                ),
                modifier = Modifier.padding(bottom = 10.dp)
            )

            Image(
                painter = painterResource(id = brandsImageRes),
                contentDescription = brandsTitle,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clickable { onBrandsClick() }
                    .background(Color.LightGray),
                contentScale = ContentScale.FillBounds
            )
        }
    }
}

@Composable
fun BasicCategoryItem(
    imageRes: Int,
    text: String,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(70.dp)
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = text,
                modifier = Modifier
                    .height(70.dp)
                    .width(60.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .background(Color.LightGray),
                contentScale = ContentScale.FillBounds
            )

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = text,
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.Black
                )
            )
        }

        Icon(
            imageVector = Icons.Default.KeyboardArrowRight,
            contentDescription = "Go to $text",
            tint = Color.Gray,
            modifier = Modifier.size(35.dp)
        )
    }
}

private fun isResourceAvailable(context: android.content.Context, resId: Int): Boolean {
    return try {
        context.resources.getResourceName(resId)
        true
    } catch (e: Exception) {
        false
    }
}

//
//// Usage example for Men's category
//@Composable
//fun MenCategoryList() {
//    val menCategories = listOf(
//        "Topwear" to R.drawable.ic_topwear,
//        "Bottomwear" to R.drawable.ic_bottomwear,
//        "Footwear" to R.drawable.ic_footwear_men,
//        "Innerwear" to R.drawable.ic_innerwear,
//        "Sports & Active" to R.drawable.ic_sports_active,
//        "Watches" to R.drawable.ic_watches_men,
//        "Accessories" to R.drawable.ic_accessories_men,
//    )
//
//    SubCategoryFList(
//        title = "Men's Categories",
//        categories = menCategories,
//        showBrandsSection = true,
//        brandsImageRes = R.drawable.ic_men_brands,
//        brandsTitle = "Popular Men's Brands",
//        onCategoryClick = { categoryName ->
//            // Handle category click
//        },
//        onBrandsClick = {
//            // Handle brands click
//        }
//    )
//}
//
//// Usage example without brands section
//@Composable
//fun SimpleCategoryList() {
//    val simpleCategories = listOf(
//        "Category 1" to R.drawable.ic_category1,
//        "Category 2" to R.drawable.ic_category2,
//    )
//
//    SubCategoryFList(
//        title = "Categories",
//        categories = simpleCategories,
//        showBrandsSection = false,
//        onCategoryClick = { categoryName ->
//            // Handle category click
//        }
//    )
//}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SubCategoryFListPreview() {
    val previewCategories = listOf(
        "Indianwear" to R.drawable.ic_indianwear,
        "Westernwear" to R.drawable.ic_westernwear,
        "Footwear" to R.drawable.ic_footwear,
    )

    SubCategoryFList(
        title = "Shop by Category",
        categories = previewCategories,
        showBrandsSection = true,
        brandsImageRes = R.drawable.ic_shop_stylish_brands,
        onCategoryClick = { },
        onBrandsClick = { }
    )
}
//
//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun SubCategoryFListNoBrandsPreview() {
//    val previewCategories = listOf(
//        "Category 1" to R.drawable.ic_category1,
//        "Category 2" to R.drawable.ic_category2,
//    )
//
//    SubCategoryFList(
//        title = "Categories",
//        categories = previewCategories,
//        showBrandsSection = false,
//        onCategoryClick = { }
//    )
//}