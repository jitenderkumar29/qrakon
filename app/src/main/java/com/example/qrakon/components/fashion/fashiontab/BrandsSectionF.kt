package com.example.qrakon.components.fashion.fashiontab

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.qrakon.R

@Composable
fun BrandsSectionF(
    brandsTitle: String = "Shop Stylish Brands",
    brandsImageRes: Int = R.drawable.ic_shop_stylish_brands,
    onBrandsClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
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
