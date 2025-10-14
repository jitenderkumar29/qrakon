package com.example.qrakon.components.fashion.fashiontab

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
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
fun WomenCategoryStores() {
    val context = LocalContext.current

    val validCategories = remember {
        listOf(
            CategoryItem(
                name = "Global Store",
                imageRes = R.drawable.ic_global_store,
                description = "Influencer approved global brands handpicked for you"
            ),
            CategoryItem(
                name = "Hidden Gems",
                imageRes = R.drawable.ic_hidden_gems,
                description = "Our selection of 300+ most sought after homegrown labels"
            ),
            CategoryItem(
                name = "Luxe Edit",
                imageRes = R.drawable.ic_luxe_edit,
                description = "Our collection of 250+ designers rooted in traditional sophistication"
            ),
            CategoryItem(
                name = "Genz",
                imageRes = R.drawable.ic_genz,
                description = "30,000+ curated Gen-Z approved styles to slay in"
            ),
            CategoryItem(
                name = "House of Nykaa",
                imageRes = R.drawable.ic_house_of_nykaa,
                description = "Unique brands and curated styles; Made for you, by us"
            ),
            CategoryItem(
                name = "Revolve",
                imageRes = R.drawable.ic_revolve,
                description = "Hottest celebrity styles from LA exclusively on Nykaa Fashion"
            )
        ).filter { category ->
            isResourceAvailable(context, category.imageRes)
        }
    }


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Text(
            text = "Shop by Category",
            style = TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black
            ),
            modifier = Modifier.padding(bottom = 20.dp)
        )

        validCategories.forEachIndexed { index, category ->
            Column(modifier = Modifier.fillMaxWidth()) {
                CategoryStoreItem(
                    imageRes = category.imageRes,
                    title = category.name,
                    description = category.description
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

//        Spacer(modifier = Modifier.height(20.dp))
//
//        Text(
//            text = "Shop Stylish Brands",
//            style = TextStyle(
//                fontSize = 18.sp,
//                fontWeight = FontWeight.SemiBold,
//                color = Color.Black
//            ),
//            modifier = Modifier.padding(bottom = 10.dp)
//        )
//
//        Image(
//            painter = painterResource(id = R.drawable.ic_shop_stylish_brands),
//            contentDescription = "Shop Stylish Brands",
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(200.dp)
//                .background(Color.LightGray),
//            contentScale = ContentScale.FillBounds
//        )
    }
}

@Composable
fun CategoryStoreItem(
    imageRes: Int,
    title: String,
    description: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(90.dp), // Increased height to accommodate description
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = title,
                modifier = Modifier
                    .height(80.dp)
                    .width(75.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .background(Color.LightGray),
                contentScale = ContentScale.FillBounds
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = 8.dp)
            ) {
                Text(
                    text = title,
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Black
                    )
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = description,
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color.Gray
                    ),
                    maxLines = 2,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            Icon(
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = "Go to $title",
                tint = Color.Gray,
                modifier = Modifier.size(35.dp)
            )
        }

//        Icon(
//            imageVector = Icons.Default.KeyboardArrowRight,
//            contentDescription = "Go to $title",
//            tint = Color.Black,
//            modifier = Modifier.size(35.dp)
//        )
    }
}

data class CategoryItem(
    val name: String,
    val imageRes: Int,
    val description: String
)

private fun isResourceAvailable(context: Context, resId: Int): Boolean {
    return try {
        context.resources.getResourceName(resId)
        true
    } catch (e: Exception) {
        false
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun WomenCategoryStoresPreview() {
    WomenCategoryStores()
}