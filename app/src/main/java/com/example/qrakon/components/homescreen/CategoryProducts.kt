package com.example.qrakon.components.homescreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.example.qrakon.R
import com.example.qrakon.ui.theme.customColors

data class Category(
    val id: Int,
    val name: String,
    val iconRes: Int
)

@Composable
fun CategoryProducts(modifier: Modifier = Modifier) {
    val categories = remember {
        listOf(
            Category(1, "Early Deals", R.drawable.great_indian_festival),
            Category(2, "Shop Live", R.drawable.shop_live),
            Category(3, "Electronics", R.drawable.electronics),
            Category(4, "Mobiles", R.drawable.mobiles),
            Category(5, "Ultra", R.drawable.prime),
            Category(6, "Rewards", R.drawable.rewards),
            Category(7, "Movies", R.drawable.movies),
            Category(8, "Furniture", R.drawable.furniture),
            Category(9, "Kids & Toys", R.drawable.kids_toys),
            Category(10, "Every day", R.drawable.every_day),
            Category(11, "Appliances", R.drawable.appliances),
            Category(12, "Fashion", R.drawable.fashion),
            Category(13, "Deals", R.drawable.deals),
            Category(14, "Home", R.drawable.home),
            Category(15, "Travel", R.drawable.travel),
            Category(16, "Gift Card", R.drawable.gift_card),
            Category(17, "Beauty", R.drawable.beauty),
            Category(18, "Business", R.drawable.for_business),
            Category(19, "Books", R.drawable.books),
            Category(20, "Insurance", R.drawable.insurance),
            Category(21, "Sports", R.drawable.sports),
            Category(22, "Alexa", R.drawable.alexa),
            Category(23, "Bazaar", R.drawable.bazar),
            Category(24, "Groceries", R.drawable.groceries),
            Category(25, "MX Player", R.drawable.mx_player),
            Category(26, "Health", R.drawable.health),
        )
    }

    val selectedCategory = remember { mutableStateOf(categories.first()) }

    // ✅ Split categories into two rows for the horizontal scroll
    val halfSize = (categories.size + 1) / 2
    val firstRow = categories.take(halfSize)
    val secondRow = categories.drop(halfSize)

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        items(firstRow.size) { columnIndex ->
            Column(
                verticalArrangement = Arrangement.spacedBy(6.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Top Row Item
                CategoryItem(
                    category = firstRow[columnIndex],
                    isSelected = firstRow[columnIndex] == selectedCategory.value,
                    onClick = { selectedCategory.value = firstRow[columnIndex] }
                )

                // Bottom Row Item (only if available)
                if (columnIndex < secondRow.size) {
                    CategoryItem(
                        category = secondRow[columnIndex],
                        isSelected = secondRow[columnIndex] == selectedCategory.value,
                        onClick = { selectedCategory.value = secondRow[columnIndex] }
                    )
                }
            }
        }
    }
}

@Composable
fun CategoryItem(
    category: Category,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(70 .dp)
            .clickable(onClick = onClick)
    ) {
        Box(
            modifier = Modifier
                .height(60.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(MaterialTheme.customColors.imageBgColor1),
//            .background(
//                    if (isSelected) Color(0xFFE65100).copy(alpha = 0.08f)
//                    else Color(0xFFF5F5F5)
//                ),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = category.iconRes),
                contentDescription = category.name,
                contentScale = ContentScale.Fit,
                modifier = Modifier.fillMaxSize()
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = category.name,
            fontSize = 12.sp,
            textAlign = TextAlign.Center,
            color = Color.Black,
            fontWeight = FontWeight.Medium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.fillMaxWidth()
        )
    }
}
