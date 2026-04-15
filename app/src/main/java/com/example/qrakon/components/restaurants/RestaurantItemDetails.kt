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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.example.qrakon.components.restaurants.AddOnCategory
import com.example.qrakon.components.restaurants.AddOnItem
import com.example.qrakon.components.restaurants.AddRestItemPizza
import com.example.qrakon.components.restaurants.AddRestItemPopUp
import com.example.qrakon.components.restaurants.FoodItemDoubleF
import com.example.qrakon.components.restaurants.RestItemIndividual
import com.example.qrakon.components.restaurants.SelectionType
import com.example.qrakon.components.restaurants.formatRating
import com.example.qrakon.components.restaurants.getRandomRatings
import com.example.qrakon.ui.theme.customColors

// Restaurant Item Details with all items per row(Top image with overlay content)
// and vertically scrollable
const val MAX_CHARS = 80  // Adjust based on your layout
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RestaurantItemDetails(
    item: FoodItemDoubleF,
    showMultipleImages: Boolean = true, // 👈 control behavior
    onAddClick: () -> Unit = {},
    getAddOnCategories: (FoodItemDoubleF) -> List<AddOnCategory> = { emptyList() }
) {
    var showBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()
    var showAddRestItemPopUp by remember { mutableStateOf(false) }
    var showAddRestItemPizza by remember { mutableStateOf(false) }

//    var showBottomSheet by remember { mutableStateOf(false) }
//    val sheetState = rememberModalBottomSheetState()

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
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { showBottomSheet = true }
                        ) {
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
                        }

                        // ADD BUTTON
                        Button(
                            onClick = {
                                if (item.category == "pizza") {
                                    showAddRestItemPizza = true
                                } else {
                                    showAddRestItemPopUp = true
                                }
                            },
//                            onClick = onAddClick,
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

    // Bottom Sheet with RestItemIndividual
    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { showBottomSheet = false },
            containerColor = Color.Transparent,
//            containerColor = Color.White,
            shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
            dragHandle = {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp),
//                        .padding(12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .width(0.dp)
//                            .width(40.dp)
                            .height(0.dp)
//                            .height(4.dp)
                            .background(
                                color = Color.Transparent,
//                                color = Color.Gray.copy(alpha = 0.5f),
                                shape = RoundedCornerShape(2.dp)
                            )
                    )
                }
            }
        )
        {
            RestItemIndividual(
                item = item,
                onAddClick = {
                    onAddClick()
                    showBottomSheet = false
                },
                onCustomiseClick = {
                    showBottomSheet = false
                },
                onDismiss = {
                    showBottomSheet = false   // 👈 THIS IS THE FIX
                }
            )
        }
    }

    // AddRestItemPopUp Modal Bottom Sheet
    if (showAddRestItemPopUp) {
        ModalBottomSheet(
            onDismissRequest = { showAddRestItemPopUp = false },
            containerColor = Color.Transparent,
            modifier = Modifier,
//                .fillMaxHeight(0.9f), // 👈 Set height to 90% of screen
            shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
//            sheetState = sheetState,
        dragHandle = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp),
//                        .padding(12.dp),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .width(0.dp)
//                            .width(40.dp)
                        .height(0.dp)
//                            .height(4.dp)
                        .background(
                            color = Color.Transparent,
//                                color = Color.Gray.copy(alpha = 0.5f),
                            shape = RoundedCornerShape(2.dp)
                        )
                )
            }
        }
        ) {
            val sampleCategories = listOf(
                AddOnCategory(
                    title = "Add Extra Gravy",
                    selectionType = SelectionType.RADIO,
                    items = listOf(
                        AddOnItem(
                            id = 1,
                            name = "Gravy (80g)",
                            price = 49,
                            iconRes = R.drawable.ic_veg_rest
                        )
                    )
                ),
                AddOnCategory(
                    title = "Add a Starter",
                    subtitle = "Select upto 4",
                    selectLimit = "Select upto 4",
                    selectionType = SelectionType.CHECKBOX,
                    items = listOf(
                        AddOnItem(
                            id = 2,
                            name = "Cheesy Chicken Meatballs (3 pcs)",
                            price = 69,
                            iconRes = R.drawable.ic_non_veg_rest
                        ),
                        AddOnItem(
                            id = 3,
                            name = "French Fries (M)",
                            price = 99,
                            iconRes = R.drawable.ic_veg_rest
                        ),
                        AddOnItem(
                            id = 4,
                            name = "Falafel Nuggets with Mayo Dip (12 pcs)",
                            price = 109,
                            iconRes = R.drawable.ic_veg_rest
                        ),
                        AddOnItem(
                            id = 5,
                            name = "Chicken Tikki (5 pcs)",
                            price = 139,
                            iconRes = R.drawable.ic_non_veg_rest
                        )
                    )
                ),
                AddOnCategory(
                    title = "Add a Beverage",
                    subtitle = "Select upto 5",
                    selectLimit = "Select upto 5",
                    selectionType = SelectionType.CHECKBOX,
                    items = listOf(
                        AddOnItem(
                            id = 6,
                            name = "Masala Lemonade (200 ml)",
                            price = 59,
                            iconRes = R.drawable.ic_veg_rest
                        ),
                        AddOnItem(
                            id = 7,
                            name = "Jeera Masala Cola (250 ml)",
                            price = 59,
                            iconRes = R.drawable.ic_veg_rest
                        ),
                        AddOnItem(
                            id = 8,
                            name = "Coca-Cola Bottle (475 ml)",
                            price = 69,
                            iconRes = R.drawable.ic_veg_rest
                        ),
                        AddOnItem(
                            id = 9,
                            name = "Lemon Ice Tea (250 ml)",
                            price = 79,
                            iconRes = R.drawable.ic_veg_rest
                        )
                    )
                ),
                AddOnCategory(
                    title = "Add a Dessert",
                    subtitle = "Select upto 4",
                    selectLimit = "Select upto 4",
                    selectionType = SelectionType.CHECKBOX,
                    items = listOf(
                        AddOnItem(
                            id = 10,
                            name = "Gulab Jamun (1 pc)",
                            price = 29,
                            isBestSeller = true,
                            iconRes = R.drawable.ic_veg_rest
                        ),
                        AddOnItem(
                            id = 11,
                            name = "Gulab Jamun (2 pcs)",
                            price = 58,
                            isBestSeller = true,
                            iconRes = R.drawable.ic_veg_rest
                        ),
                        AddOnItem(
                            id = 12,
                            name = "Choco Chip Brownie",
                            price = 109,
                            iconRes = R.drawable.ic_veg_rest
                        ),
                        AddOnItem(
                            id = 13,
                            name = "Walnut Brownie",
                            price = 109,
                            iconRes = R.drawable.ic_veg_rest
                        ),
                        AddOnItem(
                            id = 14,
                            name = "Choco Lava Cake",
                            price = 0,
                            isUnavailable = true,
                            iconRes = R.drawable.ic_veg_rest
                        )
                    )
                )
            )
            AddRestItemPopUp(
                mainItemName = item.title ?: "",
                mainItemPrice = item.price?.toIntOrNull() ?: 0,
                mainItemIconRes = item.imageRes?.firstOrNull(),
                restaurantName = item.restaurantName ?: "",
                deliveryTime = item.deliveryTime ?: "",
                location = item.address ?: "",
                rating = item.rating?.toDoubleOrNull() ?: 0.0,
                ratingCount = item.reorderedQuantity ?: "3.2K+",
                categories = sampleCategories,
                onAddToCart = { totalPrice, selectedItems ->
                    println("Added to cart: ₹$totalPrice for ${item.title}")
                    onAddClick() // Call the original onAddClick if needed
                    showAddRestItemPopUp = false
                },
                onDismiss = {
                    showAddRestItemPopUp = false
                }
            )
        }
    }

    // AddRestItemPizza Modal Bottom Sheet
    if (showAddRestItemPizza) {
        ModalBottomSheet(
            onDismissRequest = { showAddRestItemPizza = false },
            containerColor = Color.Transparent,
            modifier = Modifier,
//                .fillMaxHeight(0.9f), // 👈 Set height to 90% of screen
            shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
//            sheetState = sheetState,
        dragHandle = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp),
//                        .padding(12.dp),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .width(0.dp)
//                            .width(40.dp)
                        .height(0.dp)
//                            .height(4.dp)
                        .background(
                            color = Color.Transparent,
//                                color = Color.Gray.copy(alpha = 0.5f),
                            shape = RoundedCornerShape(2.dp)
                        )
                )
            }
        }
        ) {
            AddRestItemPizza(
                mainItemName = item.title ?: "",
                mainItemDescription = item.description ?: "",
                mainItemIconRes = item.imageRes?.firstOrNull(),
                regularPrice = item.price?.toIntOrNull() ?: 289,
                mediumPrice = 479,
                largePrice = 579,
                onDismiss = {
                    showAddRestItemPizza = false
                },
                onAddToCart = { size, quantity, note, totalPrice ->
                    println("Added to cart: Size: $size, Quantity: $quantity, Note: $note, Total: ₹$totalPrice")
                    showAddRestItemPizza = false
                }
            )
        }
    }
}