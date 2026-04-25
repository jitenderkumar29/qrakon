package com.example.qrakon.components.restaurants

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.qrakon.R
import com.example.qrakon.ui.theme.customColors
import kotlin.Boolean
import kotlin.String
import kotlin.collections.take
import kotlin.let
import kotlin.text.isNotEmpty

/**
 * Food item data class for Top Rated Restaurants
 */
@Parcelize
data class TopRatedRestaurantItem(
    val id: Int? = null,
    val imageRes: Int? = null,
    val title: String? = null,
    val price: String? = null,
    val restaurantName: String? = null,
    val rating: String? = null,
    val deliveryTime: String? = null,
    val distance: String? = null,
    val discount: String? = null,
    val discountAmount: String? = null,
    val outlet: String? = null,
    val address: String? = null,
    val calories: String? = null,
    val protein: String? = null,
    val isHighProtein: Boolean? = null,
    val category: String? = null,
    val premium: String? = null,
    val isWishlisted: Boolean? = null,
    val acceptingOrders: Boolean? = null,
    val acceptingOrdersMsg: String? = null,
) : Parcelable


/**
 * Top Rated Restaurants with heading
 */
@Composable
fun TopRatedRestaurants(
    heading: String? = null,
    subtitle: String? = null,
    restaurantItems: List<TopRatedRestaurantItem>,
    onItemClick: (TopRatedRestaurantItem) -> Unit,
    onInfoIconClick: (TopRatedRestaurantItem) -> Unit = {}, // Add this parameter
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.customColors.white,
    cardWidth: Dp = 160.dp,
    cardHeight: Dp = 240.dp,
    imageHeight: Dp? = null,
    imageWidth: Dp? = null,
    imageSizeFraction: Float = 0.75f,
    imageCornerRadius: Dp = 12.dp,
    spacing: Dp = 12.dp,
    horizontalPadding: Dp = 16.dp,
    verticalPadding: Dp = 16.dp,
    headingBottomPadding: Dp = 12.dp
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(backgroundColor)
    ) {
        if (heading != null || subtitle != null) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = horizontalPadding)
                    .padding(top = verticalPadding, bottom = headingBottomPadding)
            ) {
                heading?.let {
                    Text(
                        text = heading,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }
                subtitle?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }
        }

        TopRatedRestaurantsList(
            restaurantItems = restaurantItems,
            onItemClick = onItemClick,
            onInfoIconClick = onInfoIconClick, // Pass the callback
            backgroundColor = backgroundColor,
            cardWidth = cardWidth,
            cardHeight = cardHeight,
            imageHeight = imageHeight,
            imageWidth = imageWidth,
            imageSizeFraction = imageSizeFraction,
            imageCornerRadius = imageCornerRadius,
            spacing = spacing,
            horizontalPadding = horizontalPadding
        )
        Spacer(modifier = Modifier.height(10.dp))
    }
}

/**
 * Individual Top Rated Restaurant card
 */

@Composable
fun TopRatedRestaurantCard(
    restaurantItem: TopRatedRestaurantItem,
    onClick: () -> Unit,
    onInfoIconClick: () -> Unit = {}, // Add this parameter
    onWishlistClick: (Int?) -> Unit = {},
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.customColors.white,
    cardWidth: Dp = 160.dp,
    cardHeight: Dp = 240.dp,
    imageHeight: Dp? = null,
    imageWidth: Dp? = null,
    imageSizeFraction: Float = 0.75f,
    imageCornerRadius: Dp = 12.dp,
    defaultImageRes: Int = R.drawable.restaurant_1
) {
    var isWishlisted by remember { mutableStateOf(restaurantItem.isWishlisted ?: false) }

    // Calculate image height if not provided
    val calculatedImageHeight = imageHeight ?: (cardHeight * imageSizeFraction)

    // Calculate image width (full width if not specified)
    val calculatedImageWidth = imageWidth ?: cardWidth

    // Calculate dynamic corner radius for image
    val imageCornerShape = if (calculatedImageWidth < cardWidth) {
        // If image is narrower than card, apply all corners
        RoundedCornerShape(imageCornerRadius)
    } else {
        // If image is full width, apply only top corners
        RoundedCornerShape(
            topStart = imageCornerRadius,
            topEnd = imageCornerRadius,
            bottomStart = imageCornerRadius,
            bottomEnd = imageCornerRadius
        )
    }

    Column(
        modifier = modifier
            .width(cardWidth)
            .height(cardHeight)
            .clip(RoundedCornerShape(12.dp))
            .clickable(onClick = onClick)
            .background(backgroundColor)
    ) {
        // Image section with overlay
        Box(
            modifier = Modifier
                .width(calculatedImageWidth)
                .height(calculatedImageHeight)
        ) {
            // Restaurant image
            Image(
                painter = painterResource(id = restaurantItem.imageRes ?: defaultImageRes),
                contentDescription = restaurantItem.restaurantName ?: "Restaurant",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(imageCornerShape)
            )

            // Overlay content
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(4.dp)
            ) {
                // Wishlist button at top-right
                Box(
                    modifier = Modifier.align(Alignment.TopEnd)
                ) {
                    IconButton(
                        onClick = {
                            isWishlisted = !isWishlisted
                            restaurantItem.id?.let { onWishlistClick(it) }
                        },
                        modifier = Modifier.size(24.dp)
                    ) {
                        Icon(
                            imageVector = if (isWishlisted) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = "Wishlist",
                            tint = if (isWishlisted) Color.Red else Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(start = 1.dp, bottom = 1.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .wrapContentSize()
                    ) {
                        // Price Box
                        restaurantItem.price?.let { price ->
                            if (price.isNotEmpty()) {
                                Box(
                                    modifier = Modifier
                                        .background(Color(0x7746322B), RoundedCornerShape(6.dp))
                                        .padding(horizontal = 2.dp, vertical = 1.dp)
                                ) {
                                    Text(
                                        text = "AT ₹$price",
                                        fontSize = 15.sp,
                                        fontWeight = FontWeight.ExtraBold,
                                        color = Color.White
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        // Content section below image
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 1.dp)
        ) {
            // Restaurant title with info icon
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                restaurantItem.restaurantName?.let { restaurantName ->
                    Text(
                        text = restaurantName,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f),
                        lineHeight = 13.sp,
                        style = TextStyle(
                            platformStyle = PlatformTextStyle(
                                includeFontPadding = false
                            )
                        )
                    )
                }

                // Info icon
//                Icon(
//                    painter = painterResource(id = R.drawable.info_exclamation_mark_icon),
//                    contentDescription = "Info",
//                    modifier = Modifier
//                        .size(14.dp)
//                        .clickable { onInfoIconClick() },
//                    tint = Color.Gray
//                )
            }

            // Rating + delivery time
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                restaurantItem.rating?.let { rating ->
                    Image(
                        painter = painterResource(id = R.drawable.ic_star_food),
                        contentDescription = null,
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier
                            .size(10.dp)
                    )

                    Spacer(modifier = Modifier.width(3.dp))

                    Text(
                        text = rating,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black,
                        lineHeight = 10.sp,
                        style = TextStyle(
                            platformStyle = PlatformTextStyle(
                                includeFontPadding = false
                            )
                        )
                    )
                }

                restaurantItem.deliveryTime?.let { deliveryTime ->
                    Spacer(modifier = Modifier.width(2.dp))

                    Text(
                        text = "•",
                        fontSize = 12.sp,
                        color = Color.Black
                    )

                    Spacer(modifier = Modifier.width(2.dp))

                    Text(
                        text = deliveryTime,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black,
                        lineHeight = 10.sp,
                        style = TextStyle(
                            platformStyle = PlatformTextStyle(
                                includeFontPadding = false
                            )
                        )
                    )
                }
            }

            // Category
            restaurantItem.outlet?.let { outlet ->
                Text(
                    text = outlet,
                    fontSize = 13.sp,
                    color = Color.Black,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    lineHeight = 12.sp,
                    style = TextStyle(
                        platformStyle = PlatformTextStyle(
                            includeFontPadding = false
                        )
                    )
                )
            }
        }
    }
}

/**
 * Horizontal scrolling list of Top Rated Restaurants (SINGLE ROW)
 */
@Composable
fun TopRatedRestaurantsList(
    restaurantItems: List<TopRatedRestaurantItem>,
    onItemClick: (TopRatedRestaurantItem) -> Unit,
    onInfoIconClick: (TopRatedRestaurantItem) -> Unit = {}, // Add this parameter
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.customColors.white,
    cardWidth: Dp = 160.dp,
    cardHeight: Dp = 240.dp,
    imageHeight: Dp? = null,
    imageWidth: Dp? = null,
    imageSizeFraction: Float = 0.75f,
    imageCornerRadius: Dp = 12.dp,
    spacing: Dp = 12.dp,
    horizontalPadding: Dp = 16.dp
) {
    if (restaurantItems.isEmpty()) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .background(backgroundColor)
                .padding(horizontal = horizontalPadding)
        ) {
            Text(
                text = "No restaurants available",
                color = Color.Gray,
                modifier = Modifier.padding(16.dp)
            )
        }
        return
    }

    LazyRow(
        modifier = modifier
            .fillMaxWidth()
            .background(backgroundColor)
            .padding(horizontal = horizontalPadding),
        horizontalArrangement = Arrangement.spacedBy(spacing)
    ) {
        items(restaurantItems) { restaurantItem ->
            TopRatedRestaurantCard(
                restaurantItem = restaurantItem,
                onClick = { onItemClick(restaurantItem) },
                onInfoIconClick = { onInfoIconClick(restaurantItem) }, // Pass the item
                cardWidth = cardWidth,
                cardHeight = cardHeight,
                imageHeight = imageHeight,
                imageWidth = imageWidth,
                imageSizeFraction = imageSizeFraction,
                imageCornerRadius = imageCornerRadius
            )
        }
    }
}

val completeRestaurantItems = listOf(
    TopRatedRestaurantItem(
        id = 1,
        imageRes = R.drawable.ic_kitchen_exotica_badarpur,
        title = "North Indian",
        price = "399",
        restaurantName = "Kitchen Exotica",
        rating = "5.0",
        deliveryTime = "25-30 mins",
        distance = "2.5 km",
        discount = "20% OFF",
        discountAmount = "₹60",
        outlet = "Badarpur",
        address = "Badarpur",
        category = "kitchen_exotica_badarpur",
        premium = "premium",
//        acceptingOrders = true,
    ),
    TopRatedRestaurantItem(
        id = 2,
        imageRes = R.drawable.ic_burger_king,
        title = "Burger King",
        price = "180",
        restaurantName = "Burger King",
        rating = "5.0",
        deliveryTime = "45-50 mins",
        distance = "7.3 km",
        discount = "ITEMS",
        discountAmount = "₹20",
        outlet = "Pacific Jasola",
        address = "Pacific Jasola",
        category = "burger",
        premium = "ultrapremium",
//        acceptingOrders = true,
//        acceptingOrdersMsg = "Hey early bird! Restaurant opens in 1 mins, but why wait? Place your order right away!",
    ),
    TopRatedRestaurantItem(
        id = 3,
        imageRes = R.drawable.ic_top_rated_food_bikkgane_biryani,
        title = "Chicken Biryani",
        price = "186",
        restaurantName = "Bikkgane Biryani",
        rating = "5.0",
        deliveryTime = "35-40 mins",
        distance = "3.0 km",
        discount = "70%",
        discountAmount = "₹130",
        outlet = "Greater Kailash New",
        address = "Greater Kailash New",
        category = "biryani",
        premium = "premium",
//        acceptingOrders = true,
//        acceptingOrdersMsg = "Hey early bird! Restaurant opens in 2 mins, but why wait? Place your order right away!",
    ),
    TopRatedRestaurantItem(
        id = 4,
        imageRes = R.drawable.ic_sanjha_chulha,
        title = "North Indian Combo",
        price = "229",
        restaurantName = "Sanjha Chulha",
        rating = "5.0",
        deliveryTime = "55-65 mins",
        distance = "3.5 km",
        discount = "20%",
        discountAmount = "₹40",
        outlet = "Sector 46",
        address = "Sector 46",
        category = "northindiansc",
        premium = "ultrapremium",
//        acceptingOrders = true,
//        acceptingOrdersMsg = ""
    ),
    TopRatedRestaurantItem(
        id = 5,
        imageRes = R.drawable.ic_top_rated_food_dana_choga,
        title = "Dana Choga Special",
        price = "299",
        restaurantName = "Dana Choga",
        rating = "5.0",
        deliveryTime = "45-50 mins",
        distance = "5.5 km",
        discount = "",
        discountAmount = "",
        outlet = "Jasola",
        address = "Jasola",
        category = "northindian",
        premium = "ultrapremium",
        acceptingOrders = false,
        acceptingOrdersMsg = "Uh-oh! Outlet is not accepting orders at the moment. They should be back by 11:30 AM",

        ),
    TopRatedRestaurantItem(
        id = 6,
        imageRes = R.drawable.ic_anupam_sweets_restaurant,
        title = "North Indian",
        price = "299",
        restaurantName = "Anupam Sweets and Restaurant",
        rating = "4.2",
        deliveryTime = "55-65 mins",
        distance = "2.5 km",
        discount = "₹20 OFF",
        discountAmount = "₹20",
        outlet = "Sec 34",
        address = "Sec 34",
        category = "anupam_sweets_restaurant",
        premium = "premium",
    ),
    TopRatedRestaurantItem(
        id = 7,
        imageRes = R.drawable.ic_mcdonalds,
        title = "Fast Food",
        price = "180",
        restaurantName = "Mcdonalds",
        rating = "5.0",
        deliveryTime = "45-50 mins",
        distance = "7.3 km",
        discount = "ITEMS",
        discountAmount = "₹20",
        outlet = "Sec 35",
        address = "Sec 35",
        category = "mcdonalds",
        premium = "ultrapremium",
    ),
    TopRatedRestaurantItem(
        id = 8,
        imageRes = R.drawable.ic_biriyani_by_kilo,
        title = "Best in Biryani gourmet",
        price = "249",
        restaurantName = "Biryani By Kilo",
        rating = "0.0",
        deliveryTime = "50-60 mins",
        distance = "3.2 km",
        discount = "₹20 OFF",
        discountAmount = "₹20",
        outlet = "Jasola",
        address = "Jasola",
        category = "biriyani_by_kilo",
        premium = "premium",
        acceptingOrders = false,
        acceptingOrdersMsg = "",
    ),
    TopRatedRestaurantItem(
        id = 9,
        imageRes = R.drawable.ic_carcoal_eats,
        title = "Biryani & Beyond",
        price = "",
        restaurantName = "Charcoal Eats",
        rating = "4.3",
        deliveryTime = "40-45 mins",
        distance = "",
        discount = "₹20 OFF",
        discountAmount = "₹20",
        outlet = "Suraj Kund Road",
        address = "Suraj Kund Road",
        category = "charcoal_eats",
        premium = "ultrapremium",
        acceptingOrders = true,
        acceptingOrdersMsg = "",
    ),
    TopRatedRestaurantItem(
        id = 10,
        imageRes = R.drawable.ic_pizza_wings,
        title = "Peppers Pizza",
        price = "199",
        restaurantName = "PIZA WINGS-OK",
        rating = "5.0",
        deliveryTime = "25-30 mins",
        distance = "2.5 km",
        discount = "30%",
        discountAmount = "₹20",
        outlet = "Sector 37",
        address = "Sector 37",
        category = "pizza",
        acceptingOrders = true,
        premium = "premium",
        acceptingOrdersMsg = "Hey early bird! Restaurant opens in 14 mins, but why wait? Place your order right away!",
    ),
    TopRatedRestaurantItem(
        id = 11,
        imageRes = R.drawable.ic_top_rated_food_kfc,
        title = "Chicken Bucket",
        price = "350",
        restaurantName = "KFC",
        rating = "5.0",
        deliveryTime = "40-45 mins",
        distance = "2.5 km",
        discount = "50%",
        discountAmount = "₹350",
        outlet = "Sector-18, Noida",
        address = "Sector-18, Noida",
        category = "kfcburger",
        premium = "ultrapremium",
        acceptingOrders = false,
        acceptingOrdersMsg = "Uh-oh! Outlet is not accepting orders at the moment. They should be back by 11:30 AM",
    ),
    TopRatedRestaurantItem(
        id = 12,
        imageRes = R.drawable.ic_top_rated_food_curry_queen,
        title = "Curry Combo",
        price = "279",
        restaurantName = "Curry Queen",
        rating = "5.0",
        deliveryTime = "30-35 mins",
        distance = "4.2 km",
        discount = "₹80 OFF",
        discountAmount = "₹80",
        outlet = "Dhaka, Bangladesh",
        address = "Dhaka, Bangladesh",
        category = "bangladeshi",
        premium = "premium",
        acceptingOrders = false,
        acceptingOrdersMsg = "Uh-oh! Outlet is not accepting orders at the moment. They should be back by 11:30 AM",
    ),
    TopRatedRestaurantItem(
        id = 13,
        imageRes = R.drawable.ic_top_rated_food_subway,
        title = "Sub Sandwich",
        price = "200",
        restaurantName = "SUBWAY",
        rating = "5.0",
        deliveryTime = "40-45 mins",
        distance = "2.0 km",
        discount = "40%",
        discountAmount = "₹80",
        outlet = "Connaught Place",
        address = "Connaught Place",
        category = "sandwich",
        premium = "ultrapremium",
        acceptingOrders = false,
        acceptingOrdersMsg = "Uh-oh! Outlet is not accepting orders at the moment. They should be back by 11:30 AM",
    ),



    TopRatedRestaurantItem(
        id = 14,
        imageRes = R.drawable.ic_kitchen_exotica,
        title = "North Indian",
        price = "299",
        restaurantName = "Kitchen Exotica",
        rating = "5.0",
        deliveryTime = "40-45 mins",
        distance = "2.5 km",
        discount = "20% OFF",
        discountAmount = "₹60",
        outlet = "Greenfields",
        address = "Greenfields",
        category = "northindian",
        premium = "premium",
//        acceptingOrders = true,
    ),
    TopRatedRestaurantItem(
        id = 15,
        imageRes = R.drawable.ic_spice_garden,
        title = "Big Bowl",
        price = "150",
        restaurantName = "Spice Garden",
        rating = "5.0",
        deliveryTime = "30-35 mins",
        distance = "3.8 km",
        discount = "20%",
        discountAmount = "₹20",
        outlet = "Faridabad Sector-15",
        address = "Faridabad Sector-15",
        category = "bigbowl",
        premium = "premium",
        acceptingOrdersMsg = "Uh-oh! Outlet is not accepting orders at the moment. They should be back by 11:30 AM",
    ),
    TopRatedRestaurantItem(
        id = 16,
        imageRes = R.drawable.ic_amiche_pizza,
        title = "Bakingo",
        price = "220",
        restaurantName = "Amiche Pizza",
        rating = "5.0",
        deliveryTime = "60-65 mins",
        distance = "5.2 km",
        discount = "30%",
        discountAmount = "₹20",
        outlet = "City Center",
        address = "City Center",
        category = "fastfood",
        premium = "premium",
        acceptingOrders = false,
        acceptingOrdersMsg = "Uh-oh! Outlet is not accepting orders at the moment. They should be back by 11:30 AM",
    ),
    TopRatedRestaurantItem(
        id = 17,
        imageRes = R.drawable.ic_pizza_heaven,
        title = "Zaika Food",
        price = "199",
        restaurantName = "Pizza Heaven",
        rating = "5.0",
        deliveryTime = "25-30 mins",
        distance = "2.5 km",
        discount = "20%",
        discountAmount = "₹20",
        outlet = "City Center",
        address = "City Center",
        category = "fastfood",
        premium = "ultrapremium",
        acceptingOrders = false,
        acceptingOrdersMsg = "Uh-oh! Outlet is not accepting orders at the moment. They should be back by 11:30 AM",
    ),
    TopRatedRestaurantItem(
        id = 18,
        imageRes = R.drawable.ic_pizza_corner,
        title = "Havmor Ice Cream",
        price = "199",
        restaurantName = "Pizza Corner",
        rating = "5.0",
        deliveryTime = "25-30 mins",
        distance = "2.5 km",
        discount = "30%",
        discountAmount = "₹20",
        outlet = "Faridabad Sector-15",
        address = "Faridabad Sector-15",
        category = "icecream",
        premium = "premium",
        acceptingOrders = false,
        acceptingOrdersMsg = "Uh-oh! Outlet is not accepting orders at the moment. They should be back by 11:30 AM",
    ),



















)