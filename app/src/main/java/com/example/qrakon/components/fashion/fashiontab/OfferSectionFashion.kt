package com.example.qrakon.components.fashion.fashiontab

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import com.example.qrakon.R
import com.example.qrakon.components.restaurants.OfferItem
import com.example.qrakon.ui.theme.customColors

// ✅ Local data (renamed to avoid conflict)
val fashionOfferItems = listOf(
    OfferItem("Flat 50% off", "NO CODE REQUIRED | ON SELECT ITEM...", R.drawable.ic_offer_rest_1),
    OfferItem("DEAL OF DAY", "Items at ₹329 | ON SELECT ITEMS", R.drawable.ic_offer_rest_2),
    OfferItem("Flat ₹150 off", "USE AXISREWARDS | ABOVE ₹500", R.drawable.ic_offer_rest_3),
    OfferItem("VISA", "10% off upto ₹75 | USE VISAPLATINUMDC | ABOVE ₹300", R.drawable.ic_offer_rest_4),
    OfferItem("Flat ₹125 off", "USE IDFCDC125 | ABOVE ₹499", R.drawable.ic_offer_rest_5)
)

@Composable
fun OfferSectionFashion(
    offerItems: List<OfferItem>,
    modifier: Modifier = Modifier,
    autoRotate: Boolean = true,
    autoRotateIntervalMs: Long = 3000
) {
    var offerIndex by remember { mutableIntStateOf(0) }

    // ✅ Auto-rotation tied to data
    if (autoRotate && offerItems.isNotEmpty()) {
        LaunchedEffect(offerItems) {
            while (true) {
                delay(autoRotateIntervalMs)
                offerIndex = (offerIndex + 1) % offerItems.size
            }
        }
    }

    if (offerItems.isEmpty()) return

    Surface(
        shape = RoundedCornerShape(8.dp),
        color = MaterialTheme.customColors.background,
        modifier = modifier
//            .border(
//                width = 1.dp,
//                color = MaterialTheme.customColors.lightGray, // or any Color like Color.Gray
//                shape = RoundedCornerShape(20.dp)
//            )
//            .padding(top = 5.dp, bottom = 5.dp, start = 5.dp, end = 5.dp),
    ) {
        AnimatedContent(
            targetState = offerIndex,
            transitionSpec = {
                slideInHorizontally(
                    animationSpec = tween(500, easing = FastOutSlowInEasing),
                    initialOffsetX = { it }
                ) togetherWith slideOutHorizontally(
                    animationSpec = tween(500, easing = FastOutSlowInEasing),
                    targetOffsetX = { -it }
                )
            },
            label = "offerTransition"
        ) { index ->

            // ✅ Safe index
            val safeIndex = index % offerItems.size
            val item = offerItems[safeIndex]

            Row(
                modifier = Modifier.padding(0.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = item.imageResId),
                    contentDescription = "Offer Image",
                    modifier = Modifier
                        .size(48.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )

                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = item.discountText,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.customColors.black
                    )
                    Text(
                        text = item.couponCode,
                        fontSize = 13.sp,
                        color = MaterialTheme.customColors.gray,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Column(
                    modifier = Modifier.wrapContentWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "${safeIndex + 1}/${offerItems.size}",
                        fontSize = 14.sp,
                        color = MaterialTheme.customColors.orangeButton,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 6.dp)
                    )

                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        offerItems.forEachIndexed { dotIndex, _ ->
                            val isActive = dotIndex == safeIndex

                            val animatedSize by animateDpAsState(
                                targetValue = if (isActive) 8.dp else 6.dp,
                                animationSpec = tween(300),
                                label = "dotSize"
                            )

                            val animatedColor by animateColorAsState(
                                targetValue = if (isActive)
                                    MaterialTheme.customColors.orangeButton
                                else
                                    Color.LightGray,
                                animationSpec = tween(300),
                                label = "dotColor"
                            )

                            Box(
                                modifier = Modifier
                                    .padding(horizontal = 2.dp)
                                    .size(animatedSize)
                                    .background(animatedColor, CircleShape)
                            )
                        }
                    }
                }
            }
        }
    }
}

// ==================== Preview ====================

@Composable
fun PreviewOfferSectionFashion() {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = Color(0xFFF5F5F5)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OfferSectionFashion(
                offerItems = fashionOfferItems,
                modifier = Modifier.fillMaxWidth(),
                autoRotate = true
            )
        }
    }
}