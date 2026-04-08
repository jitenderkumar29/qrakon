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
    val address: String? = null,
    val calories: String? = null,
    val protein: String? = null,
    val isHighProtein: Boolean? = null,
    val category: String? = null,
    val isWishlisted: Boolean? = null,
) : Parcelable

/**
 * Individual Top Rated Restaurant card
 */
@Composable
fun TopRatedRestaurantCard(
    restaurantItem: TopRatedRestaurantItem,
    onClick: () -> Unit,
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
                contentDescription = restaurantItem.title ?: "Restaurant",
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
                        // Discount Box
//                        restaurantItem.discount?.let { discount ->
//                            if (discount.isNotEmpty()) {
//                                Box(
//                                    modifier = Modifier
//                                        .background(Color(0x7746322B), RoundedCornerShape(6.dp))
//                                        .padding(horizontal = 2.dp, vertical = 1.dp)
//                                ) {
//                                    Text(
//                                        text = "$discount",
//                                        fontSize = 10.sp,
//                                        fontWeight = FontWeight.ExtraBold,
//                                        color = Color.White
//                                    )
//                                }
//                            }
//                        }

                        // Add some spacing between discount and price
                        Spacer(modifier = Modifier.height(1.dp))

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
            // Restaurant title
            restaurantItem.title?.let { title ->
                Text(
                    text = title,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.fillMaxWidth(),
                    lineHeight = 13.sp,
                    style = TextStyle(
                        platformStyle = PlatformTextStyle(
                            includeFontPadding = false
                        )
                    )
                )
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
                        fontSize = 10.sp,
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
                    Spacer(modifier = Modifier.width(4.dp))

                    Text(
                        text = "•",
                        fontSize = 10.sp,
                        color = Color.Black
                    )

                    Spacer(modifier = Modifier.width(4.dp))

                    Text(
                        text = deliveryTime,
                        fontSize = 10.sp,
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
            restaurantItem.category?.let { category ->
                Text(
                    text = category,
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

/**
 * Top Rated Restaurants with heading
 */
@Composable
fun TopRatedRestaurants(
    heading: String? = null,
    subtitle: String? = null,
    restaurantItems: List<TopRatedRestaurantItem>,
    onItemClick: (TopRatedRestaurantItem) -> Unit,
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
 * Sample data for Top Rated Restaurants
 */
val sampleTopRatedRestaurants = listOf(
    TopRatedRestaurantItem(
        id = 1,
        imageRes = R.drawable.restaurant_1,
        title = "Shree Jee Restaurant",
        rating = "4.1",
        deliveryTime = "45-50 mins",
        distance = "7.3 km",
        discount = "60",
        discountAmount = "up to ₹120",
        address = "Delhi",
        category = "North Indian"
    ),
    TopRatedRestaurantItem(
        id = 2,
        imageRes = R.drawable.restaurant_1,
        title = "Amiche Pizza",
        rating = "4.3",
        deliveryTime = "60-65 mins",
        category = "Italian"
    ),
    TopRatedRestaurantItem(
        id = 3,
        imageRes = R.drawable.restaurant_1,
        title = "Spice Garden",
        rating = "4.0",
        deliveryTime = "30-35 mins",
        discount = "40",
        category = "Multi-cuisine"
    ),
    TopRatedRestaurantItem(
        id = 4,
        imageRes = R.drawable.restaurant_1,
        title = "South Indian Delight",
        rating = "4.5",
        deliveryTime = "20-25 mins",
        discount = "20",
        category = "South Indian"
    ),
    TopRatedRestaurantItem(
        id = 5,
        imageRes = R.drawable.restaurant_1,
        title = "Biryani House",
        rating = "4.2",
        deliveryTime = "35-40 mins",
        category = "Mughlai"
    ),
    TopRatedRestaurantItem(
        id = 6,
        imageRes = R.drawable.restaurant_1,
        title = "Fast Food Center",
        rating = "4.0",
        deliveryTime = "15-20 mins",
        category = "Fast Food"
    ),
    TopRatedRestaurantItem(
        id = 7,
        imageRes = R.drawable.restaurant_1,
        title = "Chinese Corner",
        rating = "4.4",
        deliveryTime = "25-30 mins",
        discount = "30",
        category = "Chinese"
    )
)

@Preview(showBackground = true)
@Composable
fun TopRatedRestaurantCardPreview() {
    MaterialTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Preview with custom image height (larger image)
            TopRatedRestaurantCard(
                restaurantItem = sampleTopRatedRestaurants[0],
                onClick = { println("Clicked ${sampleTopRatedRestaurants[0].title}") },
                cardWidth = 180.dp,
                cardHeight = 260.dp,
                imageHeight = 200.dp
            )

            // Preview with smaller image height
            TopRatedRestaurantCard(
                restaurantItem = sampleTopRatedRestaurants[1],
                onClick = { println("Clicked ${sampleTopRatedRestaurants[1].title}") },
                cardWidth = 140.dp,
                cardHeight = 200.dp,
                imageHeight = 120.dp
            )

            // Preview with custom image width (square image)
            TopRatedRestaurantCard(
                restaurantItem = sampleTopRatedRestaurants[2],
                onClick = { println("Clicked ${sampleTopRatedRestaurants[2].title}") },
                cardWidth = 160.dp,
                cardHeight = 240.dp,
                imageWidth = 140.dp,
                imageHeight = 140.dp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TopRatedRestaurantsPreview() {
    MaterialTheme {
        TopRatedRestaurants(
            heading = "Top Rated Restaurants",
            subtitle = "Based on ratings and reviews",
            restaurantItems = sampleTopRatedRestaurants,
            onItemClick = { restaurant -> println("Clicked ${restaurant.title}") },
            backgroundColor = Color.White
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TopRatedRestaurantsCustomImageSizePreview() {
    MaterialTheme {
        TopRatedRestaurants(
            heading = "Featured Restaurants",
            subtitle = "With custom image sizes",
            restaurantItems = sampleTopRatedRestaurants.take(3),
            onItemClick = { restaurant -> println("Clicked ${restaurant.title}") },
            backgroundColor = Color.White,
            cardWidth = 180.dp,
            cardHeight = 280.dp,
            imageHeight = 200.dp,
            imageSizeFraction = 0.7f,
            imageCornerRadius = 16.dp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TopRatedRestaurantsSquareImagesPreview() {
    MaterialTheme {
        TopRatedRestaurants(
            heading = "Square Layout",
            restaurantItems = sampleTopRatedRestaurants.take(4),
            onItemClick = { restaurant -> println("Clicked ${restaurant.title}") },
            backgroundColor = Color.White,
            cardWidth = 150.dp,
            cardHeight = 220.dp,
            imageWidth = 150.dp,
            imageHeight = 150.dp,
            imageCornerRadius = 8.dp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TopRatedRestaurantDynamicCornersPreview() {
    MaterialTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Full width image (rounded top corners only)
            TopRatedRestaurantCard(
                restaurantItem = sampleTopRatedRestaurants[0],
                onClick = { println("Clicked ${sampleTopRatedRestaurants[0].title}") },
                cardWidth = 180.dp,
                cardHeight = 260.dp,
                imageWidth = 180.dp,
                imageHeight = 180.dp,
                imageCornerRadius = 16.dp
            )

            // Narrower image (all corners rounded)
            TopRatedRestaurantCard(
                restaurantItem = sampleTopRatedRestaurants[1],
                onClick = { println("Clicked ${sampleTopRatedRestaurants[1].title}") },
                cardWidth = 180.dp,
                cardHeight = 260.dp,
                imageWidth = 160.dp,
                imageHeight = 180.dp,
                imageCornerRadius = 8.dp
            )

            // Square image with custom corner radius
            TopRatedRestaurantCard(
                restaurantItem = sampleTopRatedRestaurants[2],
                onClick = { println("Clicked ${sampleTopRatedRestaurants[2].title}") },
                cardWidth = 160.dp,
                cardHeight = 240.dp,
                imageWidth = 160.dp,
                imageHeight = 160.dp,
                imageCornerRadius = 20.dp
            )
        }
    }
}

val completeRestaurantItems = listOf(
    TopRatedRestaurantItem(
        id = 1,
        imageRes = R.drawable.ic_top_rated_food_4,
        title = "Peppers Pizza",
        price = "199",
        restaurantName = "Momo Express",
        rating = "4.2",
        deliveryTime = "25-30 mins",
        distance = "2.5 km",
        discount = "30%",
        discountAmount = "₹20",
        address = "Pacific Jasola",
        category = "pizza"
    ),
    TopRatedRestaurantItem(
        id = 2,
        imageRes = R.drawable.ic_top_rated_food_1,
        title = "Burger King",
        price = "180",
        restaurantName = "Hunger Cure",
        rating = "4.1",
        deliveryTime = "45-50 mins",
        distance = "7.3 km",
        discount = "ITEMS",
        discountAmount = "₹20",
        address = "Pacific Jasola",
        category = "burger"
    ),
    TopRatedRestaurantItem(
        id = 3,
        imageRes = R.drawable.ic_top_rated_food_3,
        title = "Big Bowl",
        price = "150",
        restaurantName = "Spice Garden",
        rating = "4.0",
        deliveryTime = "30-35 mins",
        distance = "3.8 km",
        discount = "20%",
        discountAmount = "₹20",
        address = "Faridabad Sector-15",
        category = "bigbowl"
    ),
    TopRatedRestaurantItem(
        id = 4,
        imageRes = R.drawable.ic_top_rated_food_2,
        title = "Bakingo",
        price = "220",
        restaurantName = "Amiche Pizza",
        rating = "4.3",
        deliveryTime = "60-65 mins",
        distance = "5.2 km",
        discount = "30%",
        discountAmount = "₹20",
        address = "City Center",
        category = "fastfood"
    ),
    TopRatedRestaurantItem(
        id = 5,
        imageRes = R.drawable.ic_top_rated_food_5,
        title = "Zaika Food",
        price = "199",
        restaurantName = "Pizza Heaven",
        rating = "4.2",
        deliveryTime = "25-30 mins",
        distance = "2.5 km",
        discount = "20%",
        discountAmount = "₹20",
        address = "City Center",
        category = "fastfood"
    ),
    TopRatedRestaurantItem(
        id = 6,
        imageRes = R.drawable.ic_top_rated_food_6,
        title = "Havmor Ice Cream",
        price = "199",
        restaurantName = "Pizza Corner",
        rating = "4.2",
        deliveryTime = "25-30 mins",
        distance = "2.5 km",
        discount = "30%",
        discountAmount = "₹20",
        address = "Faridabad Sector-15",
        category = "icecream"
    )
)