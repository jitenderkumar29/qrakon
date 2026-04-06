package com.example.hufko.components.restaurants

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.qrakon.R
import com.example.qrakon.components.restaurants.FoodItemDoubleF
import com.example.qrakon.components.restaurants.formatRating
import com.example.qrakon.components.restaurants.getRandomRatings
import com.example.qrakon.ui.theme.customColors

@Composable
fun RestaurantItemDetails(
    item: FoodItemDoubleF,
    showMultipleImages: Boolean = true, // 👈 control behavior
    onAddClick: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(2.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {

            /** ================= LEFT SIDE ================= **/
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp),
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {

                item.infoIcon?.let {
                    Icon(
                        painter = painterResource(id = it),
                        contentDescription = "Veg",
                        tint = Color.Unspecified,
                        modifier = Modifier.size(16.dp)
                    )
                }

                Text(
                    text = item.title ?: "",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    maxLines = 2
                )

                // Progress
                Row(verticalAlignment = Alignment.CenterVertically) {

                    val progress = (item.highlyReordered?.toFloatOrNull() ?: 0f) / 100f

                    Box(
                        modifier = Modifier
                            .width(50.dp)
                            .height(6.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(MaterialTheme.customColors.spacerColor)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxHeight()
                                .fillMaxWidth(progress)
                                .background(MaterialTheme.customColors.success)
                        )
                    }

                    Spacer(modifier = Modifier.width(6.dp))

                    Text(
                        text = "Highly reordered",
                        fontSize = 11.sp,
                        color = Color.Gray
                    )
                }

                // Price Row
                Row(verticalAlignment = Alignment.CenterVertically) {

                    Text(
                        text = "₹${item.price}",
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp
                    )

                    Spacer(modifier = Modifier.width(4.dp))

                    Image(
                        painter = painterResource(R.drawable.discount_badge),
                        contentDescription = "",
                        modifier = Modifier.size(12.dp),
                        contentScale = ContentScale.FillBounds
                    )

                    Spacer(modifier = Modifier.width(4.dp))

                    Text(
                        text = item.discount ?: "",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.DarkGray
                    )

                    Spacer(modifier = Modifier.width(4.dp))

                    Text(
                        text = "USE HUFKO COUPON",
                        fontSize = 11.sp,
                        color = Color.DarkGray,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                // Rating
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = Color.Transparent
                ) {
                    Box(
                        modifier = Modifier.background(
                            brush = Brush.horizontalGradient(
                                colors = listOf(
                                    MaterialTheme.customColors.success.copy(alpha = 0.2f),
                                    MaterialTheme.customColors.success.copy(alpha = 0.1f)
                                )
                            ),
                            shape = RoundedCornerShape(12.dp)
                        )
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("★", fontSize = 12.sp, color = MaterialTheme.customColors.success)
                            Spacer(modifier = Modifier.width(2.dp))
                            Text(
                                text = formatRating(item.rating),
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.customColors.success
                            )
                            Spacer(modifier = Modifier.width(2.dp))
                            Text(
                                text = "(${getRandomRatings()})",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.customColors.success
                            )
                        }
                    }
                }

                // Protein + Calories
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "${item.protein}",
                        fontSize = 12.sp,
                        color = MaterialTheme.customColors.orangeVivid
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Text(
                        text = "Protein • ${item.calories}",
                        fontSize = 12.sp,
                        color = MaterialTheme.customColors.orangeVivid
                    )
                }

                Text(
                    text = item.description ?: "",
                    fontSize = 12.sp,
                    color = Color.Gray,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                // Save & Share
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    IconButton(onClick = { }) {
                        Icon(
                            painter = painterResource(id = R.drawable.outline_bookmark_24),
                            contentDescription = "Save",
                            tint = Color.Gray
                        )
                    }
                    IconButton(onClick = { }) {
                        Icon(
                            painter = painterResource(id = R.drawable.outline_share_24),
                            contentDescription = "Share",
                            tint = Color.Gray
                        )
                    }
                }
            }

            /** ================= RIGHT SIDE ================= **/
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(170.dp)
                ) {

                    val images = item.imageRes ?: emptyList()

                    when {
                        // 🔥 DOUBLE IMAGE
                        images.size >= 2 && showMultipleImages -> {
                            Row(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(RoundedCornerShape(12.dp))
                            ) {
                                Image(
                                    painter = painterResource(images[0]),
                                    contentDescription = "",
                                    modifier = Modifier
                                        .weight(1f)
                                        .fillMaxHeight()
                                        .padding(end = 2.dp)
                                        .clip(
                                            RoundedCornerShape(
                                                topStart = 12.dp,
                                                bottomStart = 12.dp
                                            )
                                        ),
                                    contentScale = ContentScale.Crop
                                )

                                Image(
                                    painter = painterResource(images[1]),
                                    contentDescription = "",
                                    modifier = Modifier
                                        .weight(1f)
                                        .fillMaxHeight()
                                        .padding(start = 2.dp)
                                        .clip(
                                            RoundedCornerShape(
                                                topEnd = 12.dp,
                                                bottomEnd = 12.dp
                                            )
                                        ),
                                    contentScale = ContentScale.Crop
                                )
                            }
                        }

                        // ✅ SINGLE IMAGE
                        images.isNotEmpty() -> {
                            Image(
                                painter = painterResource(images[0]),
                                contentDescription = "",
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(RoundedCornerShape(12.dp)),
                                contentScale = ContentScale.Crop
                            )
                        }

                        // ❌ NO IMAGE
                        else -> {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(MaterialTheme.customColors.background),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.outline_photo_camera_24),
                                    contentDescription = "No image",
                                    tint = Color.Gray
                                )
                            }
                        }
                    }

                    // ADD BUTTON
                    Button(
                        onClick = onAddClick,
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .offset(y = 20.dp),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White,
                            contentColor = MaterialTheme.customColors.success
                        ),
                        border = BorderStroke(1.dp, MaterialTheme.customColors.success),
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 2.dp),
                        elevation = ButtonDefaults.buttonElevation(2.dp)
                    ) {
                        Text("ADD +", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "Customisable",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
        }
    }
}