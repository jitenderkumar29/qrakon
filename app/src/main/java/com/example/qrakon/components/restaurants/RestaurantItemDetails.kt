package com.example.hufko.components.restaurants

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.qrakon.R
import com.example.qrakon.components.restaurants.FoodItemDoubleF
import com.example.qrakon.components.restaurants.formatRating
import com.example.qrakon.components.restaurants.getRandomRatings
import com.example.qrakon.ui.theme.customColors

// Restaurant Item Details with all items per row(Top image with overlay content)
// and vertically scrollable
const val MAX_CHARS = 80  // Adjust based on your layout
@Composable
fun RestaurantItemDetails(
    item: FoodItemDoubleF,
    showMultipleImages: Boolean = true, // 👈 control behavior
    onAddClick: () -> Unit = {}
) {
    Column {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp),
            shape = RoundedCornerShape(0.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min),
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
                        maxLines = 3
                    )

                    // Progress
                    if (item.moredetailsbutton != true && item.salad  != true && item.crazy  != true) {

                        if (item.highlyReordered != null && !item.reorderedQuantity.isNullOrEmpty()) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                val maxOrders = 1000
                                val progress = (item.reorderedQuantity?.toFloatOrNull() ?: 0f) / maxOrders
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
                                            .fillMaxWidth(progress.coerceIn(0f, 1f))
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
                        }
//                        Row(verticalAlignment = Alignment.CenterVertically) {
//                            val progress = (item.highlyReordered?.toFloatOrNull() ?: 0f) / 100f
//                            Box(
//                                modifier = Modifier
//                                    .width(50.dp)
//                                    .height(6.dp)
//                                    .clip(RoundedCornerShape(10.dp))
//                                    .background(MaterialTheme.customColors.spacerColor)
//                            ) {
//                                Box(
//                                    modifier = Modifier
//                                        .fillMaxHeight()
//                                        .fillMaxWidth(progress)
//                                        .background(MaterialTheme.customColors.success)
//                                )
//                            }
//                            Spacer(modifier = Modifier.width(6.dp))
//                            Text(
//                                text = "Highly reordered",
//                                fontSize = 11.sp,
//                                color = Color.Gray
//                            )
//                        }
                    }

                    // Price
                    if (item.salad  == true ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row (
//                                verticalArrangement = Arrangement.spacedBy(0.dp)
                            ) {
                                // Original Price (Strikethrough)
                                Text(
                                    text = "₹${item.originalPrice ?: "0"}",
                                    fontSize = 14.sp,
                                    color = Color.Gray,
                                    textDecoration = TextDecoration.LineThrough
                                )
                                Spacer(modifier = Modifier.width(5.dp))
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
                                            color = Color(0xFFBDBDBD), // light gray border
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
                        }
                    }
                    // Price discount Row
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        if (item.moredetailsbutton != true  && item.salad  != true && item.crazy  != true) {
                            Text(
                                text = "₹${item.price}",
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp
                            )
                            if (!item.discount.isNullOrEmpty()) {
                                Spacer(modifier = Modifier.width(2.dp))
                                Image(
                                    painter = painterResource(R.drawable.discount_badge),
                                    contentDescription = "",
                                    modifier = Modifier.size(12.dp),
                                    contentScale = ContentScale.FillBounds
                                )
                                Spacer(modifier = Modifier.width(2.dp))
                                Text(
                                    text = item.discount ?: "",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.DarkGray
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "USE COUPON",
                                    fontSize = 10.sp,
                                    color = Color.DarkGray,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
//                            Spacer(modifier = Modifier.width(2.dp))
//                            Image(
//                                painter = painterResource(R.drawable.discount_badge),
//                                contentDescription = "",
//                                modifier = Modifier.size(12.dp),
//                                contentScale = ContentScale.FillBounds
//                            )
//                            Spacer(modifier = Modifier.width(2.dp))
//                            Text(
//                                text = item.discount ?: "",
//                                fontSize = 11.sp,
//                                fontWeight = FontWeight.Bold,
//                                color = Color.DarkGray
//                            )
//                            Spacer(modifier = Modifier.width(4.dp))
//                            Text(
//                                text = "USE COUPON",
//                                fontSize = 10.sp,
//                                color = Color.DarkGray,
//                                maxLines = 1,
//                                overflow = TextOverflow.Ellipsis
//                            )
                        }
                    }

                    if (item.crazy  == true) {
                        // Price and Button
                        Row(
                            modifier = Modifier.fillMaxWidth(),
//                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
//                            Column(
//                                verticalArrangement = Arrangement.spacedBy(0.dp)
//                            ) {
                    // Original Price (Strikethrough)
                    Text(
                        text = "₹${item.originalPrice ?: "0"}",
                        fontSize = 12.sp,
                        color = Color.Gray,
                        textDecoration = TextDecoration.LineThrough
                    )
                    Spacer(modifier = Modifier.width(5.dp))
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
                                color = Color(0xFFBDBDBD), // light gray border
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
                    Spacer(modifier = Modifier.height(5.dp))
//                        }
                }

                    // Rating
                    if (item.moredetailsbutton != true  && item.salad  != true) {
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
                                    Text(
                                        "★",
                                        fontSize = 12.sp,
                                        color = MaterialTheme.customColors.success
                                    )
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
                    }

                    // Protein + Calories
                    if (item.moredetailsbutton != true  && item.salad  != true && item.crazy  != true && item.bigValue != true && item.superSaver  != true) {
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
                    }

                    if (item.moredetailsbutton != true) {
                        val fullText = item.description ?: ""
                        val maxLength = 45

                        val displayText = if (fullText.length > maxLength) {
                            buildAnnotatedString {
                                append(fullText.take(maxLength))
                                pushStringAnnotation(tag = "more", annotation = "expand")
                                withStyle(SpanStyle(Color.Black)) {
                                    append("...more")
                                }
                                pop()
                            }
                        } else {
                            AnnotatedString(fullText)
                        }

                        Text(
                            text = displayText,  // ✅ Use displayText instead of item.description
                            fontSize = 12.sp,
                            color = Color.DarkGray,
                            maxLines = 2
                        )
                    }

//                    Row(verticalAlignment = Alignment.CenterVertically) {
//                        if (item.moredetailsbutton != true  && item.salad  != true) {
//                            Image(
//                                painter = painterResource(R.drawable.discount_badge),
//                                contentDescription = "",
//                                modifier = Modifier.size(12.dp),
//                                contentScale = ContentScale.FillBounds
//                            )
//                            Spacer(modifier = Modifier.width(4.dp))
//                            Text(
//                                text = item.discount ?: "",
//                                fontSize = 12.sp,
//                                fontWeight = FontWeight.Bold,
//                                color = Color.DarkGray
//                            )
//                            Spacer(modifier = Modifier.width(4.dp))
//                            Text(
//                                text = "USE HUFKO COUPON",
//                                fontSize = 11.sp,
//                                color = Color.DarkGray,
//                                maxLines = 1,
//                                overflow = TextOverflow.Ellipsis
//                            )
//                        }
//                    }



                    // Save & Share
                    if (item.moredetailsbutton != true  && item.salad  != true) {
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

                    if (item.moredetailsbutton == true) {
                        Row (
//                                verticalArrangement = Arrangement.spacedBy(0.dp)
                        ) {
                            // Original Price (Strikethrough)
                            Text(
                                text = "₹${item.originalPrice ?: "0"}",
                                fontSize = 14.sp,
                                color = Color.Gray,
                                textDecoration = TextDecoration.LineThrough
                            )
                            Spacer(modifier = Modifier.width(5.dp))
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
                                        color = Color(0xFFBDBDBD), // light gray border
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

                        Spacer(modifier = Modifier.height(10.dp))
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(MaterialTheme.customColors.white)
                                .border(
                                    width = 1.dp,
                                    color = MaterialTheme.customColors.gray,
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .clickable { /* Handle click */ }
                                .padding(horizontal = 14.dp, vertical = 6.dp)
                        ) {
                            Text(
                                text = "More Details",
                                fontSize = 12.sp,
                                color = MaterialTheme.customColors.darkGray,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }

                /** ================= RIGHT SIDE ================= **/
                Column(
                    modifier = Modifier
                        .weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(190.dp)
                    ) {
                        val images = item.imageRes ?: emptyList()
                        when {
                            // 🔥 DOUBLE IMAGE (Stacked vertically)
                            images.size >= 2 && showMultipleImages -> {
                                Column(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .clip(RoundedCornerShape(12.dp))
                                ) {
                                    Image(
                                        painter = painterResource(images[0]),
                                        contentDescription = "",
                                        modifier = Modifier
                                            .weight(1f)
                                            .fillMaxWidth()
                                            .clip(
                                                RoundedCornerShape(
                                                    topStart = 12.dp,
                                                    topEnd = 12.dp
                                                )
                                            ),
                                        contentScale = ContentScale.Crop
                                    )
                                    Spacer(modifier = Modifier.height(3.dp))
                                    Image(
                                        painter = painterResource(images[1]),
                                        contentDescription = "",
                                        modifier = Modifier
                                            .weight(1f)
                                            .fillMaxWidth()
                                            .clip(
                                                RoundedCornerShape(
                                                    bottomStart = 12.dp,
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

        // Divider after each item
        Divider(
            modifier = Modifier.padding(vertical = 8.dp),
            color = Color.Gray,
            thickness = 0.5.dp
        )
    }
}