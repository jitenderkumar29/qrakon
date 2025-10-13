package com.example.qrakon.components.fashion.fashiontab

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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

//
//@Composable
//fun WomenCategoriesFashionPage(onTabSelected: (String) -> Unit) {
//    Column(Modifier.fillMaxWidth()) {
//        InfoCategoriesF()
//        WomenCategoryList()
//    }
//}

@Composable
fun WomenCategoryList() {
    val context = LocalContext.current

    val validCategories = remember {
        listOf(
            "Indianwear" to R.drawable.ic_bathroom_accessories_home_kids_fashion_two,
            "Westernwear" to R.drawable.ic_bathroom_accessories_home_kids_fashion_two,
            "Footwear" to R.drawable.ic_bathroom_accessories_home_kids_fashion_two,
            "Lingerie" to R.drawable.ic_bathroom_accessories_home_kids_fashion_two,
            "Bags" to R.drawable.ic_bathroom_accessories_home_kids_fashion_two,
        ).filter { (_, resId) ->
            isResourceAvailable(context, resId)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
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

        validCategories.forEach { (name, imageRes) ->
            BasicCategoryItem(
                imageRes = imageRes,
                text = name
            )
        }
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
                    .size(60.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .background(Color.LightGray),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = text,
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.Black
                )
            )
        }

        Icon(
            imageVector = Icons.Default.KeyboardArrowRight,
            contentDescription = "Go to $text",
            tint = Color.Gray,
            modifier = Modifier.size(18.dp)
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