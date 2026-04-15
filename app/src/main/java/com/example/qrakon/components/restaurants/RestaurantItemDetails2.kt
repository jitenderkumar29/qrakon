package com.example.hufko.components.restaurants

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.example.qrakon.components.restaurants.AddOnCategory
import com.example.qrakon.components.restaurants.AddOnItem
import com.example.qrakon.components.restaurants.AddRestItemPizza
import com.example.qrakon.components.restaurants.AddRestItemPopUp
import com.example.qrakon.components.restaurants.formatRating
import com.example.qrakon.components.restaurants.getRandomRatings
import com.example.qrakon.components.restaurants.FoodItemDoubleF
import com.example.qrakon.components.restaurants.RestItemIndividual
import com.example.qrakon.components.restaurants.SelectionType
import com.example.qrakon.ui.theme.customColors

// Restaurant Item Details with two items per row
// and vertically scrollable
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RestaurantItemDetails2(
    item: FoodItemDoubleF,
    modifier: Modifier = Modifier,
    onAddClick: () -> Unit = {},
    getAddOnCategories: (FoodItemDoubleF) -> List<AddOnCategory> = { emptyList() }
) {
    var showBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()
    var showAddRestItemPopUp by remember { mutableStateOf(false) }
    var showAddRestItemPizza by remember { mutableStateOf(false) }

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(5.dp),
//        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        if (item.recommendedWithOutProtein == true) {
            // Image
            Box(
                modifier = Modifier
//                    .fillMaxWidth()
                    .height(170.dp)  // Increased from 120 to 180
                    .width(170.dp)   // Increased from 120 to 180
                    .clip(RoundedCornerShape(12.dp))
                    .background(MaterialTheme.customColors.background)
                    .clickable { showBottomSheet = true }
            ) {
                val imageList = item.imageRes ?: emptyList()
                if (imageList.isNotEmpty()) {
                    Image(
                        painter = painterResource(id = imageList[0]), // 👈 ALWAYS FIRST IMAGE
                        contentDescription = item.title ?: "Food item",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.FillBounds
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(MaterialTheme.customColors.background),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.outline_photo_camera_24),
                            contentDescription = "No image",
                            tint = Color.Gray,
                            modifier = Modifier.size(48.dp)  // Larger icon for empty state
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            //  Info Icon
//            Row(verticalAlignment = Alignment.CenterVertically) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // If they're in a Row, remove spacing
                Row(
                    horizontalArrangement = Arrangement.spacedBy(0.dp), // Remove spacing
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    item.infoIcon?.let {
                        Icon(
                            painter = painterResource(id = it),
                            contentDescription = "",
                            tint = Color.Unspecified,
                            modifier = Modifier.size(14.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(2.dp))
                    if (item.bestSeller == true) {
                        Image(
                            painter = painterResource(id = R.drawable.best_seller),
                            contentDescription = "Food item",
                            modifier = Modifier
                                .size(width = 65.dp, height = 15.dp),
                            contentScale = ContentScale.FillBounds
                        )
                    }
                }

                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = Color.Transparent,
                    modifier = Modifier.wrapContentSize()  // Dynamic width & height
                ) {
                    Box(
                        modifier = Modifier
                            .background(
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
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(
                                start = 6.dp,
                                top = 0.dp,
                                end = 6.dp,
                                bottom = 0.dp
                            )
                        ) {
                            Text(
                                text = "★",
                                fontSize = 12.sp,
                                color = MaterialTheme.customColors.success,
                                modifier = Modifier.padding(bottom = 2.dp)
                            )
                            Spacer(modifier = Modifier.width(2.dp))
                            Text(
                                text = formatRating(item.rating),
                                fontSize = 12.sp,
                                color = MaterialTheme.customColors.success,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.width(2.dp))
                            Text(
                                text = "(${getRandomRatings()})",
                                fontSize = 12.sp,
                                color = MaterialTheme.customColors.success,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(5.dp))
            // Title
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = item.title ?: "Untitled",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )
            }
//            Protein
//            Row(
//                verticalAlignment = Alignment.CenterVertically,
//                modifier = Modifier.fillMaxWidth()
//            ) {
//                Text(
//                    text = item.protein ?: "Untitled",
//                    fontSize = 12.sp,
//                    fontWeight = FontWeight.Medium,
//                    color = MaterialTheme.customColors.orangeVivid, // Primary text color
//                    maxLines = 2,
//                    overflow = TextOverflow.Ellipsis,
////                    modifier = Modifier.weight(1f)
//                )
//                Spacer(modifier = Modifier.width(1.dp))
//                Text(
//                    text = "Protein",
//                    fontSize = 12.sp,
//                    fontWeight = FontWeight.Medium,
//                    color = MaterialTheme.customColors.orangeVivid, // Secondary/Label color
//                    maxLines = 2,
//                    overflow = TextOverflow.Ellipsis,
////                    modifier = Modifier.weight(1f)
//                )
//                Spacer(modifier = Modifier.width(2.dp))
//
//                Text(
//                    text = "•",
//                    fontSize = 20.sp,
//                    color = MaterialTheme.customColors.orangeVivid // Separator color
//                )
//                Spacer(modifier = Modifier.width(2.dp))
//                Text(
//                    text = item.calories ?: "Untitled",
//                    fontSize = 12.sp,
//                    fontWeight = FontWeight.Medium,
//                    color = MaterialTheme.customColors.orangeVivid, // Primary text color
//                    maxLines = 2,
//                    overflow = TextOverflow.Ellipsis,
////                    modifier = Modifier.weight(1f)
//                )
//            }

            Spacer(modifier = Modifier.height(2.dp))

            // Price and Button
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(0.dp)
                ) {
                    Text(
                        text = "₹${item.price ?: "0"}",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    // Original Price (Strikethrough)
//                    Text(
//                        text = "₹${item.originalPrice ?: "0"}",
//                        fontSize = 12.sp,
//                        color = Color.Gray,
//                        textDecoration = TextDecoration.LineThrough
//                    )
                    // Discounted Price (Box)
//                    Box(
//                        modifier = Modifier
//                            .shadow(
//                                elevation = 4.dp,
//                                shape = RoundedCornerShape(4.dp),
//                                clip = false // important for natural shadow
//                            )
//                            .background(
//                                color = MaterialTheme.customColors.yellowButton,
//                                shape = RoundedCornerShape(4.dp)
//                            )
//                            .border(
//                                width = 1.dp,
//                                color = Color(0xFFBDBDBD), // light gray border
//                                shape = RoundedCornerShape(4.dp)
//                            )
//                            .padding(horizontal = 5.dp, vertical = 1.dp)
//                    ) {
//                        Text(
//                            text = "₹${item.price ?: "0"}",
//                            fontSize = 14.sp,
//                            fontWeight = FontWeight.Bold,
//                            color = Color.Black
//                        )
//                    }
                }

                OutlinedButton(
                    onClick = {
                        if (item.category == "pizza") {
                            showAddRestItemPizza = true
                        } else {
                            showAddRestItemPopUp = true
                        }  // 👈 Open AddRestItemPopUp instead
                    },
//                    onClick = onAddClick,
                    modifier = Modifier
                        .height(32.dp)
                        .width(70.dp),
                    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 0.dp),
                    shape = RoundedCornerShape(6.dp),
                    border = BorderStroke(1.dp, color = MaterialTheme.customColors.success),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.customColors.success)
                ) {
                    Text(
                        "ADD",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.customColors.success
                    )
                }
            }
        }
        else {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(2.dp)
            ) {
                // Image
                Box(
                    modifier = Modifier
//                        .fillMaxWidth()
                        .height(170.dp)  // Increased from 120 to 180
                        .width(170.dp)   // Increased from 120 to 180
                        .clip(RoundedCornerShape(12.dp))
                        .background(MaterialTheme.customColors.background)
                        .clickable { showBottomSheet = true }
                ) {
                    val imageList = item.imageRes ?: emptyList()

                    if (imageList.isNotEmpty()) {
                        Image(
                            painter = painterResource(id = imageList[0]), // 👈 ALWAYS FIRST IMAGE
                            contentDescription = item.title ?: "Food item",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.FillBounds
                        )
                    } else {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(MaterialTheme.customColors.background),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.outline_photo_camera_24),
                                contentDescription = "No image",
                                tint = Color.Gray,
                                modifier = Modifier.size(48.dp)  // Larger icon for empty state
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                //  Info Icon
//            Row(verticalAlignment = Alignment.CenterVertically) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // If they're in a Row, remove spacing
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(0.dp), // Remove spacing
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        item.infoIcon?.let {
                            Icon(
                                painter = painterResource(id = it),
                                contentDescription = "",
                                tint = Color.Unspecified,
                                modifier = Modifier.size(14.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(2.dp))
                        if (item.bestSeller == true) {
                            Image(
                                painter = painterResource(id = R.drawable.best_seller),
                                contentDescription = "Food item",
                                modifier = Modifier
                                    .size(width = 65.dp, height = 15.dp),
                                contentScale = ContentScale.FillBounds
                            )
                        }
                    }

                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = Color.Transparent,
                        modifier = Modifier.wrapContentSize()  // Dynamic width & height
                    ) {
                        Box(
                            modifier = Modifier
                                .background(
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
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(
                                    start = 6.dp,
                                    top = 0.dp,
                                    end = 6.dp,
                                    bottom = 0.dp
                                )
                            ) {
                                Text(
                                    text = "★",
                                    fontSize = 12.sp,
                                    color = MaterialTheme.customColors.success,
                                    modifier = Modifier.padding(bottom = 2.dp)
                                )
                                Spacer(modifier = Modifier.width(2.dp))
                                Text(
                                    text = formatRating(item.rating),
                                    fontSize = 12.sp,
                                    color = MaterialTheme.customColors.success,
                                    fontWeight = FontWeight.Bold
                                )
                                Spacer(modifier = Modifier.width(2.dp))
                                Text(
                                    text = "(${getRandomRatings()})",
                                    fontSize = 12.sp,
                                    color = MaterialTheme.customColors.success,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }

//                    Surface(
//                        shape = RoundedCornerShape(20.dp),
//                        color = MaterialTheme.customColors.success.copy(alpha = 0.1f),
//                        modifier = Modifier.size(width = 65.dp, height = 25.dp)
//                    ) {
//                        Row(
//                            horizontalArrangement = Arrangement.Center,
//                            verticalAlignment = Alignment.CenterVertically,
//                            modifier = Modifier.padding(start = 2.dp, top = 0.dp, end = 2.dp, bottom = 0.dp)
//                        ) {
//                            Text(
//                                text = "★",
//                                fontSize = 12.sp,
//                                color = MaterialTheme.customColors.success,
//                                modifier = Modifier.padding(bottom = 5.dp)
//                            )
//                            Spacer(modifier = Modifier.width(1.dp))
//                            Text(
//                                text = formatRating(item.rating),
//                                fontSize = 12.sp,
//                                color = MaterialTheme.customColors.success,
//                                fontWeight = FontWeight.Bold
//                            )
//                            Spacer(modifier = Modifier.width(1.dp))
//                            Text(
//                                text = "(${getRandomRatings()})",
//                                fontSize = 12.sp,
//                                color = MaterialTheme.customColors.success,
//                                fontWeight = FontWeight.Bold
//                            )
//                        }
//                    }
                }
                Spacer(modifier = Modifier.height(5.dp))
                // Title
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = item.title ?: "Untitled",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f)
                    )
                }
//            Protein
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = item.protein ?: "Untitled",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.customColors.orangeVivid, // Primary text color
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
//                    modifier = Modifier.weight(1f)
                    )
                    Spacer(modifier = Modifier.width(1.dp))
                    Text(
                        text = "Protein",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.customColors.orangeVivid, // Secondary/Label color
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
//                    modifier = Modifier.weight(1f)
                    )
                    Spacer(modifier = Modifier.width(2.dp))

                    Text(
                        text = "•",
                        fontSize = 20.sp,
                        color = MaterialTheme.customColors.orangeVivid // Separator color
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Text(
                        text = item.calories ?: "Untitled",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.customColors.orangeVivid, // Primary text color
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
//                    modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(2.dp))

                // Price and Button
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(0.dp)
                    ) {
                        // Original Price (Strikethrough)
                        Text(
                            text = "₹${item.originalPrice ?: "0"}",
                            fontSize = 12.sp,
                            color = Color.Gray,
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

                    OutlinedButton(
                        onClick = {
                            if (item.category == "pizza") {
                                showAddRestItemPizza = true
                            } else {
                                showAddRestItemPopUp = true
                            }  // 👈 Open AddRestItemPopUp instead
                        },
//                        onClick = onAddClick,
                        modifier = Modifier
                            .height(32.dp)
                            .width(70.dp),
                        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 0.dp),
                        shape = RoundedCornerShape(6.dp),
                        border = BorderStroke(1.dp, color = MaterialTheme.customColors.success),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.customColors.success)
                    ) {
                        Text(
                            "ADD",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.customColors.success
                        )
                    }
                }
            }
        }
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

@Preview(showBackground = true)
@Composable
fun PreviewRestaurantItemDetails2() {
    val sampleItems = listOf(
        FoodItemDoubleF(
            id = 1,
            imageRes = listOf(
                R.drawable.restaurant_image_pizzas_food_items_1,
                R.drawable.restaurant_image_pizzas_food_items_2
            ),
            title = "Tandoori Soya Chaap Roll",
            price = "189",
            restaurantName = "Tandoori Junction",
            rating = "4.5",
            deliveryTime = "25-30 mins",
            distance = "2.5 km",
            discount = "20% OFF",
            discountAmount = "₹20",
            description = "A delicious snack made of soya chaap",
            quantity = "20",
            infoIcon = R.drawable.ic_veg_rest,
            highlyReordered = "icon_name",
            reorderedQuantity = "icon_name",
            address = "Food Court, Mall"
        ),
        FoodItemDoubleF(
            id = 2,
            imageRes = listOf(
                R.drawable.restaurant_image_pizzas_food_items_2,
                R.drawable.restaurant_image_pizzas_food_items_3
            ),
            title = "Paneer Tikka Roll",
            price = "220",
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
            address = "Food Court, Mall"
        ),
        FoodItemDoubleF(
            id = 3,
            imageRes = listOf(
                R.drawable.restaurant_image_pizzas_food_items_3,
                R.drawable.restaurant_image_pizzas_food_items_4
            ),
            title = "Margherita Pizza",
            price = "450",
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
        )
    )
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.customColors.background)
            .padding(12.dp)
    ) {
        // Display items in 2-column grid
        val rows = sampleItems.chunked(2)
        rows.forEach { rowItems ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                rowItems.forEach { item ->
                    RestaurantItemDetails2(
                        item = item,
                        modifier = Modifier.weight(1f),
                        onAddClick = { println("Added: ${item.title}") }
                    )
                }
                // Fill empty space if odd number of items
                if (rowItems.size == 1) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}