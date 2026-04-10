package com.example.qrakon.components.restaurants

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
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import androidx.compose.foundation.clickable
import androidx.compose.ui.zIndex
import com.example.qrakon.R
import com.example.qrakon.ui.theme.customColors

// Data classes for offer items
data class OfferItem(
    val discountText: String,
    val couponCode: String,
    val imageResId: Int
)

// Sample data - replace with your actual images
val offerItems = listOf(
    OfferItem("Flat 50% off", "NO CODE REQUIRED | ON SELECT ITEM...", R.drawable.ic_offer_rest_1),
    OfferItem("DEAL OF DAY", "Items at ₹329 | ON SELECT ITEMS", R.drawable.ic_offer_rest_2),
    OfferItem("Flat £150 off", "USE AXISREWARDS | ABOVE £500", R.drawable.ic_offer_rest_3),
    OfferItem("VISA", "10% off upto ₹75 | USE VISAPLATINUMDC | ABOVE ₹300", R.drawable.ic_offer_rest_4),
    OfferItem("Flat ₹125 off", "USE IDFCDC125 | ABOVE ₹499", R.drawable.ic_offer_rest_5)
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RestaurantCard(
    items: List<TopRatedRestaurantItem>,
    modifier: Modifier = Modifier,
    onItemClick: (TopRatedRestaurantItem) -> Unit = {}
) {
    var restaurantIndex by remember { mutableIntStateOf(0) }

    // Auto-slide every 3 seconds
    LaunchedEffect(Unit) {
        while (items.isNotEmpty()) {
            delay(3000)
            restaurantIndex = (restaurantIndex + 1) % items.size
        }
    }

    if (items.isEmpty()) return

    val currentItem = items[restaurantIndex]
    var paddingTopCard = 0.dp
    if (currentItem.acceptingOrders != null) {
        if (currentItem.acceptingOrders == true) {
            paddingTopCard = 55.dp
        } else if (currentItem.acceptingOrders == false) {
            paddingTopCard = 55.dp
        }
    } else {
        paddingTopCard = 5.dp
    }
    // Box for overlapping effect (ribbon/sticker style)
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.customColors.blackHeader)
    ) {
        // Card first (so it sits behind the banner)
        Card(
            modifier = modifier
                .fillMaxWidth()
                .clickable { onItemClick(currentItem) }
                .padding(top = paddingTopCard, bottom = 15.dp, start = 12.dp, end = 12.dp), // Top padding creates space for banner overlap
            shape = RoundedCornerShape(30.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
//            Spacer(modifier = Modifier.height(10.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp)
                    .background(MaterialTheme.customColors.background),
            ) {
                if (currentItem.acceptingOrders != null) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        if (currentItem.acceptingOrders == true) {
                            Text(
                                text = currentItem.acceptingOrdersMsg ?: "",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.customColors.greenTitle
                            )
                        } else if (currentItem.acceptingOrders == false) {
                            Text(
                                text = currentItem.acceptingOrdersMsg ?: "",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.customColors.buttonRed
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    HorizontalDivider(
                        color = Color.LightGray,
                        thickness = 0.5.dp
                    )
                Spacer(modifier = Modifier.height(10.dp))
                }

                // Restaurant Seal

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_high_protein),
                        contentDescription = "icon",
                        modifier = Modifier.size(15.dp),
                    )
                    Spacer(modifier = Modifier.width(3.dp))
                    Text(
                        text = "High Protein",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.customColors.darkAccent2
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Image(
                        painter = painterResource(id = R.drawable.hufko_seal),
                        contentDescription = "icon",
                        modifier = Modifier.size(15.dp),
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Text(
                        text = "Hufko Seal",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.customColors.darkAccent2
                    )
                }

                // Restaurant Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Left side: Restaurant Name + Info Icon (grouped together)
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = currentItem.restaurantName ?: "",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Image(
                            painter = painterResource(id = R.drawable.info_exclamation_mark_icon),
                            contentDescription = "info icon",
                            modifier = Modifier.size(18.dp),
                        )
                    }

                    // Right side: Rating Chip
                    Surface(
                        shape = RoundedCornerShape(20.dp),
                        color = MaterialTheme.customColors.success,
                        modifier = Modifier.size(width = 65.dp, height = 30.dp)
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 4.dp)
                        ) {
                            Text(
                                text = formatRating(currentItem.rating),
                                fontSize = 17.sp,
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.width(2.dp))
                            Text(
                                text = "★",
                                fontSize = 18.sp,
                                color = Color.White,
                                modifier = Modifier.padding(bottom = 5.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))

                // Distance & Address
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Left side: Location info
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(5.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.baseline_location_pin_24),
                            contentDescription = "icon",
                            modifier = Modifier.size(18.dp),
                            colorFilter = ColorFilter.tint(MaterialTheme.customColors.success)
                        )
                        Text(
                            text = currentItem.distance ?: "",
                            fontSize = 14.sp,
                            color = Color.DarkGray,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "•",
                            fontSize = 20.sp,
                            color = Color.DarkGray,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = currentItem.address ?: "",
                            fontSize = 14.sp,
                            color = Color.DarkGray,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            fontWeight = FontWeight.Bold
                        )
                        // Down arrow icon
                        Box(
                            modifier = Modifier
                                .offset(x = (-5).dp) // Pull icon left
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.outline_arrow_drop_down_24),
                                contentDescription = "Dropdown arrow",
                                modifier = Modifier.size(30.dp),
                                tint = Color.DarkGray
                            )
                        }
                    }

                    // Right side: Ratings
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "${getRandomRatings()}K+ ratings",
                            fontSize = 11.sp,
                            color = Color.DarkGray
                        )
                    }
                }

                // Delivery Time & Distance
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.outline_flash_on_24),
                        contentDescription = "icon",
                        modifier = Modifier.size(18.dp)
                            .padding(top = 3.dp),
                        colorFilter = ColorFilter.tint(MaterialTheme.customColors.success)
                    )
                    Text(
                        text = currentItem.deliveryTime ?: "",
                        fontSize = 14.sp,
                        color = Color.DarkGray,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "•",
                        fontSize = 20.sp,
                        color = Color.DarkGray,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Schedule for later",
                        fontSize = 14.sp,
                        color = Color.DarkGray,
                        fontWeight = FontWeight.Bold
                    )
                    // Down arrow icon
                    Icon(
                        painter = painterResource(id = R.drawable.outline_keyboard_arrow_down_24),
                        contentDescription = "Dropdown arrow",
                        modifier = Modifier.size(25.dp)
                            .padding(top = 1.dp),
                        tint = Color.DarkGray
                    )
                }

                Spacer(modifier = Modifier.height(5.dp))

                // Divider
                HorizontalDivider(
                    color = Color.LightGray,
                    thickness = 0.5.dp
                )

                Spacer(modifier = Modifier.height(5.dp))

                // Discount / Offer Section - Fixed Version
                OfferSection(
                    offerItems = offerItems,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        // Banner on top - overlapping the card's top edge (ribbon/sticker effect)
        // Only show the Row if acceptingOrders has a value (true or false)
        if (currentItem.acceptingOrders != null) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.TopCenter)
                    .zIndex(1f),
                horizontalArrangement = Arrangement.Center
            ) {
                if (currentItem.acceptingOrders == true) {
                    Image(
                        painter = painterResource(id = R.drawable.accepting_orders),
                        contentDescription = "accepting orders banner",
                        modifier = Modifier
                            .height(70.dp)
                            .width(170.dp)
                            .offset(y = 2.dp)
                    )
                } else { // false case
                    Image(
                        painter = painterResource(id = R.drawable.not_accepting_orders),
                        contentDescription = "not accepting orders banner",
                        modifier = Modifier
                            .height(70.dp)
                            .width(170.dp)
                            .offset(y = 2.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun OfferSection(
    offerItems: List<OfferItem>,
    modifier: Modifier = Modifier,
    autoRotate: Boolean = true,
    autoRotateIntervalMs: Long = 3000
) {
    var offerIndex by remember { mutableIntStateOf(0) }

    // Auto-rotation effect
    if (autoRotate && offerItems.isNotEmpty()) {
        LaunchedEffect(Unit) {
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
    ) {
        // Single AnimatedContent for the whole section
        AnimatedContent(
            targetState = offerIndex,
            transitionSpec = {
                slideInHorizontally(
                    animationSpec = tween(500, easing = FastOutSlowInEasing),
                    initialOffsetX = { fullWidth -> fullWidth } // From right
                ) togetherWith slideOutHorizontally(
                    animationSpec = tween(500, easing = FastOutSlowInEasing),
                    targetOffsetX = { fullWidth -> -fullWidth } // To left
                )
            },
            label = "offerTransition"
        ) { index ->
            Row(
                modifier = Modifier.padding(start = 0.dp, top = 0.dp, end = 0.dp, bottom = 0.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Left side: Image
                Image(
                    painter = painterResource(id = offerItems[index].imageResId),
                    contentDescription = "Offer Image",
                    modifier = Modifier
                        .size(48.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )

                // Middle: Text content
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = offerItems[index].discountText,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.customColors.black
                    )
                    Text(
                        text = offerItems[index].couponCode,
                        fontSize = 15.sp,
                        color = MaterialTheme.customColors.gray,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                // Right side: Page indicator and dots container
                Column(
                    modifier = Modifier.wrapContentWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    // Page number text
                    Text(
                        text = "${index + 1}/${offerItems.size}",
                        fontSize = 18.sp,
                        color = MaterialTheme.customColors.orangeButton,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 6.dp)
                    )

                    // Dot indicators with smooth transition
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        offerItems.forEachIndexed { dotIndex, _ ->
                            val isActive = dotIndex == index
                            val animatedSize by animateDpAsState(
                                targetValue = if (isActive) 8.dp else 6.dp,
                                animationSpec = tween(300, easing = FastOutSlowInEasing),
                                label = "dotSize"
                            )
                            val animatedColor by animateColorAsState(
                                targetValue = if (isActive)
                                    MaterialTheme.customColors.orangeButton
                                else
                                    Color.LightGray,
                                animationSpec = tween(300, easing = FastOutSlowInEasing),
                                label = "dotColor"
                            )

                            Box(
                                modifier = Modifier
                                    .padding(horizontal = 2.dp)
                                    .size(animatedSize)
                                    .background(
                                        color = animatedColor,
                                        shape = CircleShape
                                    )
                            )
                        }
                    }
                }
            }
        }
    }
}

// Helper function to safely format rating
internal fun formatRating(rating: Any?): String {
    return when (rating) {
        null -> "0.0"
        is String -> rating
        is Double -> String.format("%.1f", rating)
        is Float -> String.format("%.1f", rating)
        is Int -> rating.toString()
        else -> rating.toString()
    }
}

// Helper functions
fun getRandomRatings(): String {
    val ratings = listOf(298, 79, 299, 250)
    return ratings.random().toString()
}

private fun getDiscountText(item: TopRatedRestaurantItem): String {
    val price = item.price ?: "0"
    val discountAmount = item.discountAmount ?: "₹0"

    return when {
        item.discount == "ITEMS" -> "Items at ₹$price"
        item.discount?.contains("%") == true -> "${item.discount} off"
        else -> "Flat $discountAmount off"
    }
}

private fun getCouponCode(item: TopRatedRestaurantItem): String {
    val restaurantName = item.restaurantName ?: ""

    return when {
        item.discount == "ITEMS" -> "ON SELECT ITEMS"
        restaurantName == "Hunger Cure" -> "USE AXISREWARDS | ABOVE ₹500"
        restaurantName == "Amiche Pizza" -> "USE VISAPLATINUMIDC | ABOVE ₹300"
        else -> "USE CODE: ${restaurantName.uppercase().replace(" ", "")}"
    }
}