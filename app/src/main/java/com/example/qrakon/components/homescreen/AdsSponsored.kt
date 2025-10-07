
package com.example.qrakon.components.homescreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import com.example.qrakon.R

@Composable
fun AdsSponsored(
    modifier: Modifier = Modifier,
    adImages: List<Int>,
    autoSlideDelayMillis: Long = 5000L,
    onAdClick: (Int) -> Unit = {}
) {
    if (adImages.isEmpty()) return

    var currentIndex by remember { mutableStateOf(0) }

    // 🔄 Auto-slide logic
    LaunchedEffect(adImages) {
        while (true) {
            delay(autoSlideDelayMillis)
            currentIndex = (currentIndex + 1) % adImages.size
        }
    }

    val cardShape = RoundedCornerShape(0.dp)

    Card(
        shape = cardShape,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = modifier
            .fillMaxWidth()
            .clip(cardShape) // ✅ Ensure content follows rounded corners
            .clickable { onAdClick(currentIndex) }
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
                    .height(320.dp)
                    .clip(cardShape), // ✅ Clip image to rounded corners
                contentScale = ContentScale.FillBounds
//                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(2.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 6.dp),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Sponsored",
                    fontSize = 12.sp,
                    color = Color.Black,
                    modifier = Modifier
                        .background(Color.White)
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                )

                Icon(
                    imageVector = Icons.Filled.Info,
                    contentDescription = "Info Icon",
                    tint = Color.Gray,
                    modifier = Modifier
                        .padding(end = 4.dp)
                        .size(16.dp)
                )
            }

            Spacer(modifier = Modifier.height(2.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AdsSponsoredPreview() {
    AdsSponsored(
        adImages = listOf(
            R.drawable.ads_sponsored_2
        ),
        onAdClick = { index ->
            println("Clicked ad index: $index")
        }
    )
}
