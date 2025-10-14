package com.example.qrakon.components.fashion.fashiontab

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
fun WomenCategoryList() {
    val context = LocalContext.current

    val validCategories = remember {
        listOf(
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
        ).filter { (_, resId) ->
            isResourceAvailable(context, resId)
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

        validCategories.forEachIndexed { index, (name, imageRes) ->
            Column(modifier = Modifier.fillMaxWidth()) {
                BasicCategoryItem(
                    imageRes = imageRes,
                    text = name
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

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Shop Stylish Brands",
            style = TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black
            ),
            modifier = Modifier.padding(bottom = 10.dp)
        )
//        Row(verticalAlignment = Alignment.CenterVertically) {
        Image(
            painter = painterResource(id = R.drawable.ic_shop_stylish_brands),
            contentDescription = "Shop Stylish Brands",
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp) // fixed height, width fills parent
//                .clip(RoundedCornerShape(6.dp))
                .background(Color.LightGray),
            contentScale = ContentScale.FillBounds
        )



    }
}

@Composable
fun BasicCategoryItem(
    imageRes: Int,
    text: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(70.dp),
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

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun WomenCategoryListPreview() {
    WomenCategoryList()
}