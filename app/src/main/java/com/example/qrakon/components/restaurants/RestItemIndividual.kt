package com.example.qrakon.components.restaurants

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import com.example.qrakon.R
import com.example.qrakon.ui.theme.customColors

@Composable
fun RestItemIndividual(
    modifier: Modifier = Modifier,
    item: FoodItemDoubleF,
    onAddClick: () -> Unit = {},
    onCustomiseClick: () -> Unit = {},
    onDismiss: () -> Unit = {}
) {
    // Wrap in Box to avoid ComposableFunction1 issues
    Box(
        modifier = modifier
            .fillMaxWidth()
//            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            // Close Button - Above Card
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp)
                    .offset(y = (5).dp),
                contentAlignment = Alignment.Center
            ) {
                IconButton(
                    onClick = onDismiss,
                    modifier = Modifier
                        .size(40.dp)
                        .background(
                            color = Color.Black.copy(alpha = 0.6f),
                            shape = RoundedCornerShape(50)
                        )
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close",
                        tint = Color.White
                    )
                }
            }

            // Card Content
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(4.dp, RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)),
                shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    ImageSlider(
                        images = item.imageRes ?: emptyList(),
                        title = item.title ?: "",
                        isHighProtein = item.isHighProtein ?: false,
                        bestSeller = item.bestSeller ?: false,
                        combo = item.combo ?: false
                    )

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painter = painterResource(
                                    id = item.infoIcon ?: R.drawable.ic_non_veg_rest
                                ),
                                contentDescription = "",
                                tint = Color.Unspecified,
                                modifier = Modifier.size(14.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.Top
                        ) {
                            Text(
                                text = item.title ?: "",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black,
                                maxLines = 2,
                                lineHeight = 24.sp,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier.weight(1f)
                            )

                            Spacer(modifier = Modifier.width(20.dp))

                            Column(
                                horizontalAlignment = Alignment.End
                            ) {
                                Button(
                                    onClick = onAddClick,
                                    shape = RoundedCornerShape(10.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color.White,
                                        contentColor = MaterialTheme.customColors.success
                                    ),
                                    border = BorderStroke(1.dp, MaterialTheme.customColors.success),
                                    contentPadding = PaddingValues(horizontal = 30.dp, vertical = 2.dp),
                                    elevation = ButtonDefaults.buttonElevation(2.dp)
                                ) {
                                    Text("ADD", fontWeight = FontWeight.Bold, fontSize = 15.sp)
                                }

                                Spacer(modifier = Modifier.height(0.dp))

                                Text(
                                    text = "Customisable",
                                    fontSize = 12.sp,
                                    color = Color.Gray
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(2.dp))

                        Row(
                            verticalAlignment = Alignment.Bottom,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = "₹${item.price ?: "0"}",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = item.description ?: "",
                            fontSize = 12.sp,
                            color = Color.Gray,
//                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )

                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun ImageSlider(
    images: List<Int>,
    title: String,
    isHighProtein: Boolean = false,
    bestSeller: Boolean = false,
    combo: Boolean = false
) {
    val coroutineScope = rememberCoroutineScope()
    var currentIndex by remember { mutableIntStateOf(0) }

    LaunchedEffect(images) {
        if (images.size > 1) {
            coroutineScope.launch {
                while (true) {
                    delay(3000)
                    currentIndex = (currentIndex + 1) % images.size
                }
            }
        }
    }

    // Wrap in Box to avoid ComposableFunction1 issues
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
    ) {
        if (images.isNotEmpty()) {
            Image(
                painter = painterResource(id = images[currentIndex]),
                contentDescription = title,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)),
                contentScale = ContentScale.Crop
            )
        }

        if (isHighProtein) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(12.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.high_protein_badge),
                    contentDescription = "High Protein Badge",
                    modifier = Modifier.size(width = 50.dp, height = 70.dp),
                    contentScale = ContentScale.FillBounds
                )
            }
        }

        if (images.size > 1) {
            Row(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                images.indices.forEach { index ->
                    Box(
                        modifier = Modifier
                            .size(6.dp)
                            .clip(RoundedCornerShape(3.dp))
                            .background(
                                color = if (index == currentIndex)
                                    Color.White
                                else
                                    Color.White.copy(alpha = 0.5f)
                            )
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RestItemIndividualPreview() {
    val sampleItem = FoodItemDoubleF(
        id = 1,
        imageRes = listOf(
            R.drawable.restaurant_image_special_chicken_hyderabadi_boneless_biryani_85,
            R.drawable.restaurant_image_chicken_65_hyderabadi_biryani_1kg_86
        ),
        title = "Mughlai Grilled Chicken Rice Bowl with Omelette",
        price = "359",
        originalPrice = "459",
        restaurantName = "Mughlai Delight",
        rating = "4.5",
        deliveryTime = "30-35 mins",
        distance = "2.5 km",
        discount = "",
        discountAmount = "₹100 OFF",
        address = "Downtown, Mumbai",
        calories = "590KCal",
        protein = "24gm",
        isHighProtein = true,
        category = "rice bowl",
        isWishlisted = false,
        description = "Delicious spicy gravy with chicken, rice, salad & omelette.",
        quantity = "1",
        infoIcon = R.drawable.ic_non_veg_rest,
        highlyReordered = "Highly reordered",
        reorderedQuantity = "420",
        bestSeller = true,
        combo = true
    )

    RestItemIndividual(
        item = sampleItem,
        onDismiss = {}
    )
}