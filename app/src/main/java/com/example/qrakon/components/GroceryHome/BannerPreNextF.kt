package com.example.qrakon.components.GroceryHome

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlin.math.absoluteValue
import com.example.qrakon.R
import com.example.qrakon.ui.theme.customColors
import kotlin.collections.isNotEmpty
import kotlin.let
import kotlin.math.absoluteValue

@Composable
fun BannerPreNextF(
    foodItems: List<FoodItemBannerPreNextF>,
    onItemClick: (FoodItemBannerPreNextF) -> Unit,
    modifier: Modifier = Modifier,
    height: Dp = 260.dp,
    roundedCornerShape: Dp = 12.dp,
    contentScale: ContentScale = ContentScale.Crop,
    autoScrollDelay: Long = 5000,
    autoScrollEnabled: Boolean = true,
    backgroundColor1: Color = Color.Transparent, // Top color
    backgroundColor2: Color = Color.Black.copy(alpha = 0.9f), // Bottom color
    gradientStartY: Float = 0f,
    gradientEndY: Float = Float.POSITIVE_INFINITY,
    gradientColors: List<Color>? = null // Optional: Custom gradient colors
) {
    require(foodItems.isNotEmpty()) { "Food items list cannot be empty" }

    val actualCount = foodItems.size
    val infiniteCount = Int.MAX_VALUE
    val startPage = infiniteCount / 2 - ((infiniteCount / 2) % actualCount)

    val pagerState = rememberPagerState(
        pageCount = { infiniteCount },
        initialPage = startPage
    )

    // AutoScroll
    LaunchedEffect(autoScrollEnabled) {
        if (!autoScrollEnabled) return@LaunchedEffect

        while (true) {
            delay(autoScrollDelay)
            pagerState.animateScrollToPage(pagerState.currentPage + 1)
        }
    }

    val shape = RoundedCornerShape(roundedCornerShape)
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp

    // Calculate widths: 80% for current, 10% peeking for previous/next
    val currentWidth = screenWidth * 0.80f
    val peekWidth = screenWidth * 0.10f

    // Define gradient colors for the section background
    val gradientBrush = if (gradientColors != null) {
        Brush.verticalGradient(
            colors = gradientColors,
            startY = gradientStartY,
            endY = gradientEndY
        )
    } else {
        Brush.verticalGradient(
            colors = listOf(
                backgroundColor1,
                backgroundColor1,
                backgroundColor1,
                lerpColor(backgroundColor1, backgroundColor2, 0.3f),
                lerpColor(backgroundColor1, backgroundColor2, 0.6f),
                backgroundColor2
            ),
            startY = gradientStartY,
            endY = gradientEndY
        )
    }

    // Apply gradient to the entire container
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(height)
            .background(gradientBrush) // Gradient applied here to the section
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = peekWidth),
            pageSpacing = 0.dp
        ) { page ->
            val realIndex = page % actualCount
            val foodItem = foodItems[realIndex]

            // Calculate the scale factor based on position
            val pageOffset = ((pagerState.currentPage - page) + pagerState.currentPageOffsetFraction).absoluteValue

            // Current item gets full width, others are scaled down
            val scale = if (pageOffset < 0.5f) 1f else 0.9f

            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(currentWidth)
                    .graphicsLayer {
                        scaleX = scale
                        scaleY = scale
                        alpha = if (pageOffset < 0.5f) 1f else 0.8f
                    }
                    .clip(shape)
                    .clickable { onItemClick(foodItem) }
                    .shadow(elevation = 4.dp, shape = shape)
            ) {
                // Background Image
                if (foodItem.imageRes != null) {
                    Image(
                        painter = painterResource(id = foodItem.imageRes),
                        contentDescription = foodItem.title ?: "Food Item",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = contentScale
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(MaterialTheme.colorScheme.surfaceVariant)
                    )
                }

                // REMOVED: Gradient overlay on image since we now have gradient on section

                // Content Overlay
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(12.dp),
                    verticalArrangement = Arrangement.Bottom
                ) {
                    // First Row: Title and Price
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Bottom
                    ) {
                        Text(
                            text = foodItem.title ?: "",
                            style = MaterialTheme.typography.titleSmall.copy(
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            ),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.weight(1f)
                        )

                        Spacer(modifier = Modifier.width(4.dp))

                        Text(
                            text = "₹${foodItem.price ?: ""}",
                            style = MaterialTheme.typography.titleMedium.copy(
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    // Second Row: Restaurant Name
                    Text(
                        text = foodItem.restaurantName ?: "",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = Color.White.copy(alpha = 0.9f),
                            fontSize = 13.sp
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Third Row: Rating, Delivery Time, and Add Button
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Left side: Rating and Delivery Time
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .background(
                                        color = MaterialTheme.customColors.success,
                                        shape = RoundedCornerShape(20.dp)
                                    )
                                    .padding(start = 6.dp, end = 6.dp, top = 1.dp, bottom = 1.dp)
                            ){
                                Text(
                                    text = foodItem.rating ?: "",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = Color.White,
                                        fontSize = 13.sp
                                    ),
                                    modifier = Modifier.padding(start = 2.dp)
                                )
                                Spacer(modifier = Modifier.width(2.dp))
                                Text(
                                    text = "★",
                                    color = Color.White,
                                    fontSize = 13.sp
                                )
                            }

                            // Divider
                            Text(
                                text = "|",
                                color = Color.White.copy(alpha = 0.6f),
                                fontSize = 20.sp,
                                modifier = Modifier.padding(horizontal = 4.dp)
                            )

                            // Delivery Time
                            Text(
                                text = foodItem.deliveryTime ?: "",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = Color.White,
                                    fontSize = 12.sp
                                )
                            )
                        }

                        // Right side: ADD Button
                        OutlinedButton(
                            onClick = { onItemClick(foodItem) },
                            colors = ButtonDefaults.outlinedButtonColors(
                                containerColor = Color.White,
                                contentColor = Color.Red
                            ),
                            shape = RoundedCornerShape(8.dp),
                            border = BorderStroke(1.dp, Color.Red),
                            modifier = Modifier.height(28.dp),
                            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 0.dp)
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "ADD",
                                    style = MaterialTheme.typography.labelMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 12.sp,
                                        color = Color.Red
                                    )
                                )
                                Text(
                                    text = "+",
                                    style = MaterialTheme.typography.labelMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 16.sp,
                                        color = Color.Red
                                    ),
                                    modifier = Modifier.padding(start = 6.dp)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Nutrition Info Row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        // Left side
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            if (foodItem.isHighProtein == true) {
                                Text(
                                    text = "High Protein",
                                    style = MaterialTheme.typography.labelMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 12.sp,
                                        color = MaterialTheme.customColors.yellowish
                                    )
                                )
                            }
                        }

                        // Right side
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = "${foodItem.calories ?: "0"} kcal",
                                style = MaterialTheme.typography.labelMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp,
                                    color = MaterialTheme.customColors.yellowish
                                )
                            )
                            Text(
                                text = "${foodItem.protein ?: "0"}g protein",
                                style = MaterialTheme.typography.labelMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp,
                                    color = MaterialTheme.customColors.yellowish
                                )
                            )
                        }
                    }
                }

                // Discount Badge (Top Right)
                foodItem.discount?.let { discount ->
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(8.dp)
                            .background(
                                color = MaterialTheme.colorScheme.primary,
                                shape = RoundedCornerShape(4.dp)
                            )
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = discount,
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 11.sp
                            )
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun NutritionBadge(
    label: String,
    backgroundColor: Color,
    textColor: Color,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(6.dp))
            .background(backgroundColor)
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 10.sp,
                color = textColor
            )
        )
    }
}

// Helper function to interpolate between two colors
fun lerpColor(start: Color, end: Color, fraction: Float): Color {
    return Color(
        lerp(start.red, end.red, fraction),
        lerp(start.green, end.green, fraction),
        lerp(start.blue, end.blue, fraction),
        lerp(start.alpha, end.alpha, fraction)
    )
}

// Linear interpolation helper
fun lerp(a: Float, b: Float, fraction: Float): Float = (1 - fraction) * a + fraction * b

// Data class remains the same
data class FoodItemBannerPreNextF(
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
    val isHighProtein: Boolean? = null
)

// Alternative version if you want the gradient behind the pager items
@Composable
fun BannerPreNextFWithSectionBackground(
    foodItems: List<FoodItemBannerPreNextF>,
    onItemClick: (FoodItemBannerPreNextF) -> Unit,
    modifier: Modifier = Modifier,
    height: Dp = 260.dp,
    roundedCornerShape: Dp = 12.dp,
    contentScale: ContentScale = ContentScale.Crop,
    autoScrollDelay: Long = 5000,
    autoScrollEnabled: Boolean = true,
    backgroundColor1: Color = Color.Transparent,
    backgroundColor2: Color = Color.Black.copy(alpha = 0.9f),
    gradientStartY: Float = 0f,
    gradientEndY: Float = Float.POSITIVE_INFINITY,
    gradientColors: List<Color>? = null
) {
    require(foodItems.isNotEmpty()) { "Food items list cannot be empty" }

    val actualCount = foodItems.size
    val infiniteCount = Int.MAX_VALUE
    val startPage = infiniteCount / 2 - ((infiniteCount / 2) % actualCount)

    val pagerState = rememberPagerState(
        pageCount = { infiniteCount },
        initialPage = startPage
    )

    // AutoScroll
    LaunchedEffect(autoScrollEnabled) {
        if (!autoScrollEnabled) return@LaunchedEffect

        while (true) {
            delay(autoScrollDelay)
            pagerState.animateScrollToPage(pagerState.currentPage + 1)
        }
    }

    val shape = RoundedCornerShape(roundedCornerShape)
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp

    // Calculate widths: 80% for current, 10% peeking for previous/next
    val currentWidth = screenWidth * 0.80f
    val peekWidth = screenWidth * 0.10f

    // Define gradient colors for the section background
    val gradientBrush = if (gradientColors != null) {
        Brush.verticalGradient(
            colors = gradientColors,
            startY = gradientStartY,
            endY = gradientEndY
        )
    } else {
        Brush.verticalGradient(
            colors = listOf(
                backgroundColor1,
                backgroundColor1,
                backgroundColor1,
                lerpColor(backgroundColor1, backgroundColor2, 0.3f),
                lerpColor(backgroundColor1, backgroundColor2, 0.6f),
                backgroundColor2
            ),
            startY = gradientStartY,
            endY = gradientEndY
        )
    }

    // Main container with gradient background
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(height)
            .background(gradientBrush)
    ) {
        // The pager with its items
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = peekWidth),
            pageSpacing = 8.dp
        ) { page ->
            val realIndex = page % actualCount
            val foodItem = foodItems[realIndex]

            val pageOffset = ((pagerState.currentPage - page) + pagerState.currentPageOffsetFraction).absoluteValue
            val scale = if (pageOffset < 0.5f) 1f else 0.9f

            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(currentWidth)
                    .graphicsLayer {
                        scaleX = scale
                        scaleY = scale
                        alpha = if (pageOffset < 0.5f) 1f else 0.8f
                    }
                    .clip(shape)
                    .clickable { onItemClick(foodItem) }
                    .shadow(elevation = 4.dp, shape = shape)
            ) {
                // Image without any gradient overlay
                if (foodItem.imageRes != null) {
                    Image(
                        painter = painterResource(id = foodItem.imageRes),
                        contentDescription = foodItem.title ?: "Food Item",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = contentScale
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(MaterialTheme.colorScheme.surfaceVariant)
                    )
                }

                // Simple overlay for text readability (optional, can be removed if you want pure image)
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    Color.Transparent,
                                    Color.Transparent,
                                    Color.Black.copy(alpha = 0.3f),
                                    Color.Black.copy(alpha = 0.6f)
                                )
                            )
                        )
                )

                // Content (same as before)
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(12.dp),
                    verticalArrangement = Arrangement.Bottom
                ) {
                    // ... (content rows remain the same)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Bottom
                    ) {
                        Text(
                            text = foodItem.title ?: "",
                            style = MaterialTheme.typography.titleSmall.copy(
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            ),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.weight(1f)
                        )

                        Spacer(modifier = Modifier.width(4.dp))

                        Text(
                            text = "₹${foodItem.price ?: ""}",
                            style = MaterialTheme.typography.titleMedium.copy(
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = foodItem.restaurantName ?: "",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = Color.White.copy(alpha = 0.9f),
                            fontSize = 13.sp
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    // ... (rest of the content)
                }

                // Discount Badge
                foodItem.discount?.let { discount ->
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(8.dp)
                            .background(
                                color = MaterialTheme.colorScheme.primary,
                                shape = RoundedCornerShape(4.dp)
                            )
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = discount,
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 11.sp
                            )
                        )
                    }
                }
            }
        }
    }
}

// Example usage with different gradient options:
@Composable
fun ExampleUsagePreNextF() {
    val foodItems = listOf(
        FoodItemBannerPreNextF(
            id = 1,
            imageRes = R.drawable.sitaram_chole_bhature,
            title = "Special Chole Bhature",
            price = "120",
            restaurantName = "Sitaram Diwan Chand",
            rating = "4.8",
            deliveryTime = "15-20 mins",
            calories = "450",
            protein = "15",
            isHighProtein = true,
            discount = "20%"
        )
    )

    // Usage 1: Gradient applied to section background
    BannerPreNextF(
        foodItems = foodItems,
        onItemClick = {},
        backgroundColor1 = Color(0xFF6A1B9A).copy(alpha = 0.3f), // Light Purple
        backgroundColor2 = Color(0xFF4A148C).copy(alpha = 0.8f) // Dark Purple
    )

    // Usage 2: Blue gradient section
    BannerPreNextF(
        foodItems = foodItems,
        onItemClick = {},
        backgroundColor1 = Color(0xFF1976D2).copy(alpha = 0.3f), // Light Blue
        backgroundColor2 = Color(0xFF0D47A1).copy(alpha = 0.8f) // Dark Blue
    )

    // Usage 3: Custom gradient colors for section
    BannerPreNextF(
        foodItems = foodItems,
        onItemClick = {},
        gradientColors = listOf(
            Color(0xFFFF9800).copy(alpha = 0.2f), // Light Orange
            Color(0xFFFF9800).copy(alpha = 0.4f), // Medium Orange
            Color(0xFFF57C00).copy(alpha = 0.6f), // Orange
            Color(0xFFE65100).copy(alpha = 0.8f)  // Dark Orange
        )
    )
}