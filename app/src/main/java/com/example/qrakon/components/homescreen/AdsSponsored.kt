package com.example.qrakon.components.homescreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.qrakon.R
import kotlinx.coroutines.delay

@Composable
fun AdsSponsored(
    onAdClick: () -> Unit = {}
) {
    // List of images (add as many as you want)
    val adImages = listOf(
        R.drawable.ads_sponsored_1,
        R.drawable.ads_sponsored_2, // Add more images to drawable
        R.drawable.ads_sponsored_3
    )

    var currentIndex by remember { mutableStateOf(0) }

    // Auto-change image every 3 seconds
    LaunchedEffect(Unit) {
        while (true) {
            delay(5000) // 3 seconds
            currentIndex = (currentIndex + 1) % adImages.size
        }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onAdClick()
            }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
        ) {
            Image(
                painter = painterResource(id = adImages[currentIndex]),
                contentDescription = "Sponsored Ad",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(320.dp),
                contentScale = ContentScale.FillBounds
            )

            Spacer(modifier = Modifier.height(2.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 0.dp),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Sponsored",
                    fontSize = 12.sp,
                    color = Color.Black,
                    modifier = Modifier
                        .background(Color.White)
                        .padding(horizontal = 6.dp)
                )

                Icon(
                    imageVector = Icons.Filled.Info,
                    contentDescription = "Info Icon",
                    tint = Color.Gray,
                    modifier = Modifier.size(16.dp)
                )
            }

            Spacer(modifier = Modifier.height(2.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AdsSponsoredPreview() {
    AdsSponsored()
}
