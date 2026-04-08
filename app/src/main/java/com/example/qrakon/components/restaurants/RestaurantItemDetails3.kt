package com.example.qrakon.components.restaurants

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.qrakon.R
import com.example.qrakon.components.restaurants.FoodItemDoubleF
import com.example.qrakon.ui.theme.customColors

// Restaurant Item Details with one items per row(Left side content and right side image)
// and Horizontally scrollable
@Composable
fun RestaurantItemDetails3(
    items: List<FoodItemDoubleF>,
    modifier: Modifier = Modifier,
    onAddClick: (FoodItemDoubleF) -> Unit = {}
) {
    val scrollState = rememberScrollState()

    Row(
        modifier = modifier
            .fillMaxWidth()
            .horizontalScroll(scrollState),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items.forEach { item ->
            RestaurantItemCard3(
                item = item,
                onAddClick = { onAddClick(item) }
            )

        }
    }
}

@Composable
fun RestaurantItemCard3(
    item: FoodItemDoubleF,
    onAddClick: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .width(240.dp)
            .height(200.dp),
        shape = RoundedCornerShape(5.dp),
//        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            // Background Image
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(16.dp))
                    .background(MaterialTheme.customColors.background)
            ) {
                val imageList = item.imageRes ?: emptyList()

                if (imageList.isNotEmpty()) {
                    Image(
                        painter = painterResource(id = imageList[0]), // 👈 ONLY FIRST IMAGE
                        contentDescription = item.title ?: "Food item",
                        modifier = Modifier
                            .fillMaxSize()
                            .width(230.dp)
                            .height(230.dp),
                        contentScale = ContentScale.FillBounds
                    )
                } else {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.outline_photo_camera_24),
                            contentDescription = "No image",
                            tint = Color.Gray
                        )
                    }
                }

                // Dark Overlay only on bottom half (50% of image)
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    Color.Black.copy(alpha = 0.2f)
                                ),
                                startY = 0.5f,  // Start exactly at middle
                                endY = 1f
                            )
                        )
                )
            }

            // Content Overlay
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp),
                verticalArrangement = Arrangement.Bottom
            ) {
                // Top row with Info Icon and Best Seller
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Info Icon
                    item.infoIcon?.let {
                        Icon(
                            painter = painterResource(id = it),
                            contentDescription = "",
                            tint = Color.Unspecified,
                            modifier = Modifier.size(16.dp)
                        )
                    }

                    // Best Seller Badge
//                    if (item.bestSeller == true) {
//                        Image(
//                            painter = painterResource(id = R.drawable.best_seller),
//                            contentDescription = "Best Seller",
//                            modifier = Modifier
//                                .size(width = 50.dp, height = 15.dp),
//                            contentScale = ContentScale.FillBounds
//                        )
//                    }
                }
                Spacer(modifier = Modifier.height(6.dp))
                // Bottom section with Title and Price
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // Title
                    Text(
                        text = item.title ?: "Untitled",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        lineHeight = 18.sp
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    // Price and ADD Button Row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Bottom
                    ) {
                        // Price Column
                        Column(
                            verticalArrangement = Arrangement.spacedBy(0.dp)
                        ) {
                            // Original Price (Strikethrough)
                            Text(
                                text = "₹${item.originalPrice ?: "0"}",
                                fontSize = 12.sp,
                                color = Color.DarkGray,
                                textDecoration = TextDecoration.LineThrough
                            )
                            // Discounted Price (Box)
                            Box(
                                modifier = Modifier
                                    .shadow(
                                        elevation = 4.dp,
                                        shape = RoundedCornerShape(4.dp),
                                        clip = false // important for natural shadow
                                    )
                                    .background(
                                        color = MaterialTheme.customColors.yellowButton,
                                        shape = RoundedCornerShape(4.dp)
                                    )
                                    .border(
                                        width = 1.dp,
                                        color = MaterialTheme.customColors.yellowButton, // light gray border
                                        shape = RoundedCornerShape(4.dp)
                                    )
                                    .padding(horizontal = 5.dp, vertical = 1.dp)
                            ) {
                                Text(
                                    text = "₹${item.price ?: "0"}",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Black
                                )
                            }
                        }


                        // ADD Button
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            border = BorderStroke(1.dp, color = MaterialTheme.customColors.white),
                            color = MaterialTheme.customColors.white,
                            modifier = Modifier
                                .height(30.dp)
                                .width(60.dp)
                        ) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "ADD",
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.customColors.success,
                                    modifier = Modifier.clickable { onAddClick() }
                                )
                            }
                        }

                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewRestaurantItemDetails3() {
    val sampleItems = listOf(
        FoodItemDoubleF(
            id = 1,
            imageRes = listOf(
                R.drawable.restaurant_image_pizzas_food_items_1,
                R.drawable.restaurant_image_pizzas_food_items_2
            ),
            title = "Teriyaki Spicy Paneer Burger",
            price = "199",
            originalPrice = "279",
            restaurantName = "Burger King",
            rating = "4.5",
            deliveryTime = "25-30 mins",
            distance = "2.5 km",
            discount = "20% OFF",
            discountAmount = "₹80",
            description = "A delicious spicy paneer burger with teriyaki sauce",
            quantity = "20",
            infoIcon = R.drawable.ic_veg_rest,
            highlyReordered = "icon_name",
            reorderedQuantity = "icon_name",
            address = "Food Court, Mall",
            calories = "450 kcal",
            protein = "18g",
            bestSeller = true
        ),
        FoodItemDoubleF(
            id = 2,
            imageRes = listOf(
                R.drawable.restaurant_image_pizzas_food_items_2,
                R.drawable.restaurant_image_pizzas_food_items_3
            ),
            title = "Paneer Tikka Roll",
            price = "199",
            originalPrice = "279",
            restaurantName = "Tandoori Junction",
            rating = "4.6",
            deliveryTime = "25-30 mins",
            distance = "2.5 km",
            discount = "15% OFF",
            discountAmount = "₹33",
            description = "Delicious paneer tikka roll",
            quantity = "15",
            infoIcon = R.drawable.ic_veg_rest,
            highlyReordered = "icon_name",
            reorderedQuantity = "icon_name",
            address = "Food Court, Mall",
            calories = "380 kcal",
            protein = "15g",
            bestSeller = false
        ),
        FoodItemDoubleF(
            id = 3,
            imageRes = listOf(
                R.drawable.restaurant_image_pizzas_food_items_3,
                R.drawable.restaurant_image_pizzas_food_items_4
            ),
            title = "Margherita Pizza",
            price = "199",
            originalPrice = "279",
            restaurantName = "Pizza House",
            rating = "4.7",
            deliveryTime = "30-35 mins",
            distance = "3.0 km",
            discount = "20% OFF",
            discountAmount = "₹90 OFF",
            address = "Pizza Street",
            calories = "680 kcal",
            protein = "25g",
            isHighProtein = false,
            category = "Pizza",
            isWishlisted = false,
            description = "Classic Margherita pizza with fresh mozzarella and basil",
            quantity = "1",
            infoIcon = R.drawable.ic_veg_rest,
            highlyReordered = "30",
            reorderedQuantity = "300+ orders",
            bestSeller = true
        ),
        FoodItemDoubleF(
            id = 4,
            imageRes = listOf(
                R.drawable.restaurant_image_pizzas_food_items_4,
                R.drawable.restaurant_image_pizzas_food_items_5
            ),
            title = "Veggie Supreme Pizza",
            price = "199",
            originalPrice = "279",
            restaurantName = "Pizza House",
            rating = "4.4",
            deliveryTime = "30-35 mins",
            distance = "3.0 km",
            discount = "20% OFF",
            discountAmount = "₹90 OFF",
            address = "Pizza Street",
            calories = "580 kcal",
            protein = "20g",
            isHighProtein = false,
            category = "Pizza",
            isWishlisted = false,
            description = "Loaded with fresh vegetables",
            quantity = "1",
            infoIcon = R.drawable.ic_veg_rest,
            highlyReordered = "30",
            reorderedQuantity = "300+ orders",
            bestSeller = false
        )
    )
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.customColors.background)
            .padding(12.dp)
    ) {
        // Section Title
        Text(
            text = "Popular Items",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.customColors.black,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        // Horizontal Scrolling Items
        RestaurantItemDetails3(
            items = sampleItems,
            onAddClick = { item ->
                println("Added: ${item.title}")
            }
        )
    }
}