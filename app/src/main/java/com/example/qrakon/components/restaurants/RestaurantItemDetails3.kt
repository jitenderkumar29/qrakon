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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
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
import com.example.qrakon.ui.theme.customColors

// Restaurant Item Details with one items per row(Left side content and right side image)
// and Horizontally scrollable
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RestaurantItemDetails3(
    items: List<FoodItemDoubleF>,
    modifier: Modifier = Modifier,
    onAddClick: (FoodItemDoubleF) -> Unit = {},
    getAddOnCategories: (FoodItemDoubleF) -> List<AddOnCategory> = { emptyList() }
) {
    // State hoisted to parent level
    var showBottomSheet by remember { mutableStateOf(false) }
    var selectedItemForBottomSheet by remember { mutableStateOf<FoodItemDoubleF?>(null) }
    var showAddRestItemPopUp by remember { mutableStateOf(false) }
    var selectedItemForAddPopup by remember { mutableStateOf<FoodItemDoubleF?>(null) }
    var showAddRestItemPizza by remember { mutableStateOf(false) }
    var selectedItemForPizza by remember { mutableStateOf<FoodItemDoubleF?>(null) }

    val sheetState = rememberModalBottomSheetState()
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
                onImageClick = {
                    selectedItemForBottomSheet = item
                    showBottomSheet = true
                },
                onAddClick = {
                    if (item.category == "pizza") {
                        selectedItemForPizza = item
                        showAddRestItemPizza = true
                    } else {
                        selectedItemForAddPopup = item
                        showAddRestItemPopUp = true
                    }
                }
            )
        }
    }

    // Bottom Sheet for Image Preview
    if (showBottomSheet && selectedItemForBottomSheet != null) {
        ModalBottomSheet(
            onDismissRequest = {
                showBottomSheet = false
                selectedItemForBottomSheet = null
            },
            containerColor = Color.Transparent,
            shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
            dragHandle = {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .width(0.dp)
                            .height(0.dp)
                            .background(
                                color = Color.Transparent,
                                shape = RoundedCornerShape(2.dp)
                            )
                    )
                }
            }
        ) {
            RestItemIndividual(
                item = selectedItemForBottomSheet!!,
                onAddClick = {
                    onAddClick(selectedItemForBottomSheet!!)
                    showBottomSheet = false
                    selectedItemForBottomSheet = null
                },
                onCustomiseClick = {
                    showBottomSheet = false
                    selectedItemForBottomSheet = null
                },
                onDismiss = {
                    showBottomSheet = false
                    selectedItemForBottomSheet = null
                }
            )
        }
    }

    // AddRestItemPopUp Modal Bottom Sheet
    if (showAddRestItemPopUp && selectedItemForAddPopup != null) {
        ModalBottomSheet(
            onDismissRequest = {
                showAddRestItemPopUp = false
                selectedItemForAddPopup = null
            },
            containerColor = Color.Transparent,
            modifier = Modifier,
            shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
            dragHandle = {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .width(0.dp)
                            .height(0.dp)
                            .background(
                                color = Color.Transparent,
                                shape = RoundedCornerShape(2.dp)
                            )
                    )
                }
            }
        ) {
            val sampleCategories = getAddOnCategories(selectedItemForAddPopup!!)

            AddRestItemPopUp(
                mainItemName = selectedItemForAddPopup!!.title ?: "",
                mainItemPrice = selectedItemForAddPopup!!.price?.toIntOrNull() ?: 0,
                mainItemIconRes = selectedItemForAddPopup!!.imageRes?.firstOrNull(),
                restaurantName = selectedItemForAddPopup!!.restaurantName ?: "",
                deliveryTime = selectedItemForAddPopup!!.deliveryTime ?: "",
                location = selectedItemForAddPopup!!.address ?: "",
                rating = selectedItemForAddPopup!!.rating?.toDoubleOrNull() ?: 0.0,
                ratingCount = selectedItemForAddPopup!!.reorderedQuantity ?: "3.2K+",
                categories = if (sampleCategories.isNotEmpty()) sampleCategories else getSampleCategories(),
                onAddToCart = { totalPrice, selectedItems ->
                    println("Added to cart: ₹$totalPrice for ${selectedItemForAddPopup!!.title}")
                    onAddClick(selectedItemForAddPopup!!)
                    showAddRestItemPopUp = false
                    selectedItemForAddPopup = null
                },
                onDismiss = {
                    showAddRestItemPopUp = false
                    selectedItemForAddPopup = null
                }
            )
        }
    }

    // AddRestItemPizza Modal Bottom Sheet
    if (showAddRestItemPizza && selectedItemForPizza != null) {
        ModalBottomSheet(
            onDismissRequest = {
                showAddRestItemPizza = false
                selectedItemForPizza = null
            },
            containerColor = Color.Transparent,
            modifier = Modifier,
            shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
            dragHandle = {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .width(0.dp)
                            .height(0.dp)
                            .background(
                                color = Color.Transparent,
                                shape = RoundedCornerShape(2.dp)
                            )
                    )
                }
            }
        ) {
            AddRestItemPizza(
                mainItemName = selectedItemForPizza!!.title ?: "Pizza",
                mainItemDescription = selectedItemForPizza!!.description ?: "Delicious pizza",
                mainItemIconRes = selectedItemForPizza!!.imageRes?.firstOrNull(),
                regularPrice = selectedItemForPizza!!.price?.toIntOrNull() ?: 289,
                mediumPrice = 479,
                largePrice = 579,
                onDismiss = {
                    showAddRestItemPizza = false
                    selectedItemForPizza = null
                },
                onAddToCart = { size, quantity, note, totalPrice ->
                    println("Added to cart: Size: $size, Quantity: $quantity, Note: $note, Total: ₹$totalPrice for ${selectedItemForPizza!!.title}")
                    onAddClick(selectedItemForPizza!!)
                    showAddRestItemPizza = false
                    selectedItemForPizza = null
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RestaurantItemCard3(
    item: FoodItemDoubleF,
    onImageClick: () -> Unit = {},
    onAddClick: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .width(240.dp)
            .height(200.dp),
        shape = RoundedCornerShape(5.dp),
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
                    .clickable { onImageClick() }
            ) {
                val imageList = item.imageRes ?: emptyList()

                if (imageList.isNotEmpty()) {
                    Image(
                        painter = painterResource(id = imageList[0]),
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
//                Box(
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .background(
//                            Brush.verticalGradient(
//                                colors = listOf(
//                                    Color.Transparent,
//                                    Color.Black.copy(alpha = 0.2f)
//                                ),
//                                startY = 0.5f,
//                                endY = 1f
//                            )
//                        )
//                )
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
                                color = Color.White,
                                textDecoration = TextDecoration.LineThrough
                            )
                            // Discounted Price (Box)
                            Box(
                                modifier = Modifier
                                    .shadow(
                                        elevation = 4.dp,
                                        shape = RoundedCornerShape(4.dp),
                                        clip = false
                                    )
                                    .background(
                                        color = MaterialTheme.customColors.yellowButton,
                                        shape = RoundedCornerShape(4.dp)
                                    )
                                    .border(
                                        width = 1.dp,
                                        color = MaterialTheme.customColors.yellowButton,
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
                            onClick = onAddClick,
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
                                    color = MaterialTheme.customColors.success
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

// Helper function for sample categories
fun getSampleCategories(): List<AddOnCategory> {
    return listOf(
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
            bestSeller = true,
            category = "burger"
        ),
        FoodItemDoubleF(
            id = 2,
            imageRes = listOf(
                R.drawable.restaurant_image_pizzas_food_items_2,
                R.drawable.restaurant_image_pizzas_food_items_3
            ),
            title = "Margherita Pizza",
            price = "289",
            originalPrice = "479",
            restaurantName = "Pizza Hut",
            rating = "4.6",
            deliveryTime = "25-30 mins",
            distance = "2.5 km",
            discount = "15% OFF",
            discountAmount = "₹33",
            description = "Delicious margherita pizza",
            quantity = "15",
            infoIcon = R.drawable.ic_veg_rest,
            highlyReordered = "icon_name",
            reorderedQuantity = "icon_name",
            address = "Food Court, Mall",
            calories = "680 kcal",
            protein = "25g",
            bestSeller = false,
            category = "pizza"
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