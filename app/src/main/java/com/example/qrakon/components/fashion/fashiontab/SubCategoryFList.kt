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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.qrakon.R

@Composable
fun SubCategoryFList(
    title: String = "Shop by Category",
    categories: List<Pair<String, Int>> = emptyList(),
    onCategoryClick: (String) -> Unit = {}
) {
    val context = LocalContext.current

    val validCategories = remember(categories) {
        categories.filter { (_, resId) -> isResourceAvailable(context, resId) }
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

                Spacer(modifier = Modifier.height(8.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(Color.LightGray.copy(alpha = 0.6f))
                )
            }

            if (index != validCategories.lastIndex) {
                Spacer(modifier = Modifier.height(8.dp))
            }
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
