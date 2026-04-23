package com.example.qrakon.components.restaurants

import androidx.annotation.DrawableRes
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.zIndex
import com.example.qrakon.R
import com.example.qrakon.components.location.LocationAddress
import com.example.qrakon.ui.theme.customColors


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckOutFood(
    onBackClick: () -> Unit = {},
    onConfirmOrder: () -> Unit = {},
    onPaymentClick: () -> Unit = {},
    restaurantName: String = "Pizza Wings",
    initialRestaurantAddress: String = "" // Changed parameter name to avoid confusion
) {
    var showLocationDialog by remember { mutableStateOf(false) }
    var selectedLocation by remember { mutableStateOf("") }

    // Make restaurantAddress a mutable state that can be updated
    var restaurantAddress by remember { mutableStateOf(initialRestaurantAddress) }

    Scaffold(
        topBar = {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shadowElevation = 4.dp
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            brush = Brush.horizontalGradient(
                                colors = listOf(
                                    Color.White,
                                    Color.White,
                                )
                            )
                        )
                ) {
                    // Back button and more options row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = { onBackClick() }) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.Black)
                        }

                        // Restaurant Name and Address
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .padding(horizontal = 2.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = restaurantName,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                            Row(
//                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                // Make the entire row clickable to open location dialog
                                modifier = Modifier.fillMaxWidth().clickable {
                                    showLocationDialog = true
                                }
                            ) {
                                Icon(Icons.Default.Home, contentDescription = "Home", tint = Color.Black, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(text = if (restaurantAddress.isNotBlank()) "Selected |" else "Add |", fontSize = 13.sp, color = Color.Black, fontWeight = FontWeight.Bold)
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = if (restaurantAddress.isNotBlank()) restaurantAddress else "Select delivery address",
                                    fontSize = 13.sp,
                                    color = if (restaurantAddress.isNotBlank()) Color.Black else Color.DarkGray,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    modifier = Modifier.weight(1f)
                                )
                                Icon(Icons.Default.KeyboardArrowDown, contentDescription = "Dropdown", tint = Color.DarkGray, modifier = Modifier.size(28.dp))
                            }
                        }

                        IconButton(onClick = {}) {
                            Icon(Icons.Default.MoreVert, contentDescription = "More", tint = Color.Black)
                        }
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    // Saved Info Card - Only show if address is selected
//                    if (restaurantAddress.isNotBlank()) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 12.dp, end = 12.dp, top = 0.dp, bottom = 8.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(
                                    brush = Brush.horizontalGradient(
                                        colors = listOf(Color(0xFFDDFBEF), Color(0xFFDDFBEF))
                                    )
                                )
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp, vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(text = "224 Saved with this order",
                                    fontSize = 14.sp,
                                    color = MaterialTheme.customColors.greenTitle,
                                    fontWeight = FontWeight.Bold
                                )
//                                Spacer(modifier = Modifier.width(4.dp))
//                                Image(
//                                    painter = painterResource(R.drawable.ic_99_store),
//                                    contentDescription = "Offer Image",
//                                    modifier = Modifier
//                                        .height(20.dp)
//                                        .width(45.dp),
//                                    contentScale = ContentScale.FillBounds
//                                )
                            }
                        }
//                    }
                }
            }
        },
        containerColor = Color(0xFFF5F5F5),
        bottomBar = {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shadowElevation = 8.dp,
                color = Color.White
            ) {
                if (restaurantAddress.isNotBlank()) {
                    PaymentButtonF(
                        amount = "₹1710",
                        paymentMethod = "Credit card",
                        cardLast4 = "6247",
                        onClick = onPaymentClick
                    )
                } else {
                    AddressAddCheckout(
                        onClick = {
                            showLocationDialog = true
                        }
                    )
                }
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(paddingValues),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Only show order items if address is selected
//            if (restaurantAddress.isNotBlank()) {
                // Order Items Section
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {
                        Column(modifier = Modifier.padding(6.dp)) {
                            MembershipExpiredCard(onRenewClick = onConfirmOrder)
                            OrderItemsList()
                        }
                    }
                }

                // Complete Your Meal Section
                item {
                    CompleteYourMealSection()
                }

                // Savings Corner Section
                item {
                    SavingsCornerCard(onAddClick = onConfirmOrder, onRenewClick = onConfirmOrder)
                }

                // Delivery Type Section
                item {
                    DeliveryTypeCard()
                }

                // Payment Summary Section
                item {
                    PaymentSummaryCard()
                }

                // Cancellation policy Section
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp, vertical = 6.dp),
                    ) {
                        Text(
                            text = "Cancellation policy:",
                            fontSize = 14.sp,
                            color = Color.DarkGray,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Please double-check your order and address details. Orders are non-refundable once placed.",
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                    }
                }

                // Add bottom padding to avoid overlap with bottom bar
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                }
//            } else {
                // Show empty state or welcome message when no address is selected
//                item {
//                    Box(
//                        modifier = Modifier
//                            .fillMaxSize()
//                            .height(400.dp),
//                        contentAlignment = Alignment.Center
//                    ) {
//                        Column(
//                            horizontalAlignment = Alignment.CenterHorizontally,
//                            verticalArrangement = Arrangement.spacedBy(16.dp)
//                        ) {
//                            Icon(
//                                imageVector = Icons.Default.LocationOn,
//                                contentDescription = "Location",
//                                tint = Color(0xFFFF5A00),
//                                modifier = Modifier.size(64.dp)
//                            )
//                            Text(
//                                text = "Add delivery address to continue",
//                                fontSize = 16.sp,
//                                color = Color.Gray,
//                                fontWeight = FontWeight.Medium
//                            )
//                            Text(
//                                text = "Please select or add an address above",
//                                fontSize = 14.sp,
//                                color = Color.LightGray
//                            )
//                        }
//                    }
//                }
//            }
        }
    }

    // Location Dialog
    if (showLocationDialog) {
        var showAddressMap by remember { mutableStateOf(false) }

        Dialog(
            onDismissRequest = { showLocationDialog = false },
            properties = DialogProperties(usePlatformDefaultWidth = false)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
            ) {
                LocationAddress(
                    onBackClick = { showLocationDialog = false },
                    onLocationSelected = { location ->
                        selectedLocation = location
                        restaurantAddress = location // ✅ Update with selected address
                        showLocationDialog = false
                    },
                    onUseCurrentLocation = {
                        // ✅ Fix: Use a meaningful current location string instead of empty string
                        val currentLocation = "Current Location: Dhruv, Chennai-110044"
                        selectedLocation = currentLocation
                        restaurantAddress = currentLocation // ✅ Update with current location
                        showLocationDialog = false
                    },
                    navigateToAddAddress = {
                        showAddressMap = true
                    }
                )
            }
        }
    }
}

@Composable
fun MembershipExpiredCard(
    onRenewClick: () -> Unit = {}
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFDEFF0))
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                // Membership expired row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "PRIME",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = MaterialTheme.customColors.buttonRed,
                        maxLines = 1
                    )
//                    Icon(
//                        painter = painterResource(id = R.drawable.ic_prime),
//                        contentDescription = "One",
//                        tint = Color.Unspecified,
//                        modifier = Modifier.height(15.dp)
//                            .width(35.dp)
//                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "membership expired!",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.customColors.buttonRed,
                        maxLines = 1
                    )
                }

                // Gradient line
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 150.dp)
                        .height(2.dp)
                        .background(
                            brush = Brush.horizontalGradient(
                                colors = listOf(
                                    MaterialTheme.customColors.buttonRed.copy(alpha = 0.5f),
                                    Color(0xFFFFF3E0)
                                )
                            )
                        )
                ) {}

                // Savings and renew row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "You saved ₹594 with last plan! Renew for 3 months",
                        fontSize = 12.sp,
                        color = MaterialTheme.customColors.black,
                        modifier = Modifier.weight(1f),
                        maxLines = 2
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.End
                    ) {
                        // Renew Button
                        Box(
                            modifier = Modifier
                                .height(36.dp)
                                .border(1.dp, MaterialTheme.customColors.gray, RoundedCornerShape(8.dp))
                                .background(MaterialTheme.customColors.white, RoundedCornerShape(8.dp))
                                .padding(horizontal = 12.dp)
                                .clickable { onRenewClick()  },
                            contentAlignment = Alignment.Center
                        ) {
                            Text("Renew", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.customColors.success)
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("₹1", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                    }
                }
            }
        }
    }
}

@Composable
fun OrderItemsList() {
    var pizzaQuantity by remember { mutableStateOf(1) }
    var twistiesQuantity by remember { mutableStateOf(1) }
    var burgerQuantity by remember { mutableStateOf(1) }
    var friesQuantity by remember { mutableStateOf(1) }
    var drinkQuantity by remember { mutableStateOf(1) }

    Column(modifier = Modifier.padding(12.dp)) {
        // Item 1: Spicy Twisties
        OrderItemWithQuantity(
            name = "Spicy Twisties 4pc",
            originalPrice = "₹45",
            discountedPrice = "₹25",
            quantity = twistiesQuantity,
            onQuantityChange = { twistiesQuantity = it }
        )

        HorizontalDivider(modifier = Modifier
            .padding(vertical = 8.dp),
                color = MaterialTheme.customColors.veryLightGray
        )

        // Item 2: Cheese Margherita Cheese Burst Pizza
        OrderItemWithQuantityAndCustomization(
            name = "Cheese Margherita Pizza",
            originalPrice = "₹599",
            discountedPrice = "₹439",
            finalPrice = "₹399",
            subtitle = "Cheese Burst Medium, Medium ...",
            quantity = pizzaQuantity,
            onQuantityChange = { pizzaQuantity = it },
            showDropdown = true,
            onDropdownClick = { }
        )

        HorizontalDivider(modifier = Modifier
            .padding(vertical = 8.dp),
            color = MaterialTheme.customColors.veryLightGray
        )

        // Item 3: Classic Chicken Burger
        OrderItemWithQuantity(
            name = "Classic Chicken Burger",
            originalPrice = "₹299",
            discountedPrice = "₹199",
            quantity = burgerQuantity,
            onQuantityChange = { burgerQuantity = it }
        )

        HorizontalDivider(modifier = Modifier
            .padding(vertical = 8.dp),
            color = MaterialTheme.customColors.veryLightGray
        )

        // Item 4: French Fries with Dip
        OrderItemWithQuantityAndCustomization(
            name = "French Fries with Dip",
            originalPrice = "₹149",
            discountedPrice = "₹99",
            finalPrice = "₹89",
            subtitle = "Extra Dip: Tomato Ketchup",
            quantity = friesQuantity,
            onQuantityChange = { friesQuantity = it },
            showDropdown = true,
            onDropdownClick = { }
        )

        HorizontalDivider(modifier = Modifier
            .padding(vertical = 8.dp),
            color = MaterialTheme.customColors.veryLightGray
        )

        // Item 5: Cold Drink (Pepsi/Coke)
        OrderItemWithQuantity(
            name = "Cold Drink (Pepsi/Coke)",
            originalPrice = "₹60",
            discountedPrice = "₹40",
            quantity = drinkQuantity,
            onQuantityChange = { drinkQuantity = it }
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Add Items Row
        AddItemsRow()
    }
}


@Composable
fun OrderItemWithQuantity(
    name: String,
    originalPrice: String,
    discountedPrice: String,
    quantity: Int,
    onQuantityChange: (Int) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Name on left
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(1f)
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_veg_rest),
                contentDescription = "Veg",
                tint = Color.Unspecified,
                modifier = Modifier.size(14.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = name,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )
        }

        // Quantity controls and prices on right
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            // Quantity controls button
            Row(
                modifier = Modifier
                    .border(1.dp, Color.Gray, RoundedCornerShape(4.dp))
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(
                    text = "-",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Gray,
                    modifier = Modifier.clickable {
                        if (quantity > 1) onQuantityChange(quantity - 1)
                    }
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = quantity.toString(),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "+",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Gray,
                    modifier = Modifier.clickable {
                        onQuantityChange(quantity + 1)
                    }
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            // Prices column with fixed width
            Column(
                horizontalAlignment = Alignment.End,
                modifier = Modifier.width(35.dp)
            ) {
                Text(
                    text = originalPrice,
                    fontSize = 12.sp,
                    color = Color.Gray,
                    textDecoration = TextDecoration.LineThrough
                )
                Text(
                    text = discountedPrice,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }
        }
    }
}

@Composable
fun OrderItemWithQuantityAndCustomization(
    name: String,
    originalPrice: String,
    discountedPrice: String,
    finalPrice: String,
    subtitle: String,
    quantity: Int,
    onQuantityChange: (Int) -> Unit,
    showDropdown: Boolean = false,
    onDropdownClick: (() -> Unit)? = null
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Left side - Name and Subtitle
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
//                modifier = Modifier.weight(1f)
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_non_veg_rest),
                    contentDescription = "Non-Veg",
                    tint = Color.Unspecified,
                    modifier = Modifier
                        .size(14.dp)
//                        .align(Alignment.Top)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = name,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(modifier = Modifier.height(2.dp))
            // Subtitle with dropdown
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = subtitle,
                    fontSize = 12.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = Color.Gray,
                    modifier = Modifier.weight(1f)
                )
                if (showDropdown) {
                    Icon(
                        Icons.Default.KeyboardArrowDown,
                        contentDescription = "Dropdown",
                        tint = Color.Gray,
                        modifier = Modifier
                            .size(16.dp)
                            .clickable { onDropdownClick?.invoke() }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.width(12.dp))

        // Right side - Quantity controls and Prices
        Row(
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.End
        ) {
            // Quantity controls button
            Row(
                modifier = Modifier
                    .border(1.dp, Color.Gray, RoundedCornerShape(4.dp))
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(
                    text = "-",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Gray,
                    modifier = Modifier.clickable {
                        if (quantity > 1) onQuantityChange(quantity - 1)
                    }
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = quantity.toString(),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "+",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Gray,
                    modifier = Modifier.clickable {
                        onQuantityChange(quantity + 1)
                    }
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            // Prices column with fixed width
            Column(
                horizontalAlignment = Alignment.End,
                modifier = Modifier.width(35.dp)
            ) {
                Text(
                    text = originalPrice,
                    fontSize = 12.sp,
                    color = Color.Gray,
                    textDecoration = TextDecoration.LineThrough,
                    maxLines = 1
                )
                Text(
                    text = discountedPrice,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    maxLines = 1
                )
            }
        }
    }
}

@Composable
fun ActionChip(
    text: String,
    isPrimary: Boolean = false,
    imageVector: ImageVector? = null,
    @DrawableRes iconRes: Int? = null,
    onClick: () -> Unit
) {
    val borderColor =  Color.LightGray
    val contentColor = Color.DarkGray
//    val borderColor = if (isPrimary) Color(0xFFE53935) else Color.LightGray
//    val contentColor = if (isPrimary) Color(0xFFE53935) else Color.Gray

    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .border(1.dp, borderColor, RoundedCornerShape(8.dp))
            .clickable { onClick() }
            .padding(horizontal = 14.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        // Vector Icon
        if (imageVector != null) {
            Icon(
                imageVector = imageVector,
                contentDescription = text,
                tint = contentColor,
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
        }

        // Drawable Icon
        if (iconRes != null) {
            Icon(
                painter = painterResource(id = iconRes),
                contentDescription = text,
                tint = contentColor,
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
        }

        Text(
            text = text,
            fontSize = 13.sp,
            color = contentColor,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun AddItemsRow() {

    LazyRow(
        modifier = Modifier
            .fillMaxWidth(),
//            .padding(horizontal = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        item {
            ActionChip(
                text = "Add Items",
                isPrimary = true,
                imageVector = Icons.Default.Add,
                onClick = { }
            )
        }

        item {
            ActionChip(
                text = "Cooking requests",
                iconRes = R.drawable.outline_ink_marker_24,
                onClick = { }
            )
        }

        item {
            ActionChip(
                text = "Cutlery Notes",
                onClick = { }
            )
        }
    }
}

// Data class for items
data class CompleteYourMealItemData(
    val id: Int,
    val name: String,
    val originalPrice: String,
    val price: String,
    val imageRes: Int? = null,
    val isVeg: Boolean = true
)

// Sample data with images
val popularItemsList = listOf(
    CompleteYourMealItemData(0, "Jumbo Paneer...", "₹69", "₹159", R.drawable.restaurant_image_paneer_do_pyaaza),
    CompleteYourMealItemData(1, "Spicy Twists...", "₹45", "₹45", R.drawable.pizza_spicy_twisties_2pc),
    CompleteYourMealItemData(2, "Spicy Twists 2...", "", "₹29", R.drawable.pizza_spicy_twisties_4pc),
    CompleteYourMealItemData(3, "Garlic Breadstick...", "₹159", "₹159", R.drawable.pizza_stuffed_paneer_garlic_bread)
)

val beveragesItemsList = listOf(
    CompleteYourMealItemData(4, "Spicy Twists...", "₹45", "₹45", R.drawable.pizza_spicy_twisties_2pc),
    CompleteYourMealItemData(5, "Garlic Breadstick...", "₹159", "₹159", R.drawable.pizza_stuffed_paneer_garlic_bread),
    CompleteYourMealItemData(6, "Garlic Breadstick...", "₹179", "₹179", R.drawable.restaurant_garlic_breadsticks),
    CompleteYourMealItemData(7, "Jalapeño ...", "₹259", "₹259", R.drawable.restaurant_image_stuffed_aloo_sanjha)
)

val dessertsItemsList = listOf(
    CompleteYourMealItemData(8, "Fusion Chocolava", "", "₹129", R.drawable.restaurant_image_pizza_achari_paneer_twist),
    CompleteYourMealItemData(9, "2 Choco Lava...", "", "₹258", R.drawable.restaurant_image_paneer_do_pyaaza),
    CompleteYourMealItemData(10, "Snookie - Rs Jar.", "", "₹159", R.drawable.restaurant_image_pizzas_food_items_farmers_paradise_cheese_burst)
)

val sidesItemsList = listOf(
    CompleteYourMealItemData(11, "Spicy Twists...", "₹45", "₹45", R.drawable.pizza_spicy_twisties_2pc),
    CompleteYourMealItemData(12, "Garlic Breadstick...", "₹159", "₹159", R.drawable.pizza_stuffed_paneer_garlic_bread),
    CompleteYourMealItemData(13, "Garlic Breadstick...", "₹179", "₹179", R.drawable.restaurant_garlic_breadsticks),
    CompleteYourMealItemData(14, "Jalapeño ...", "₹259", "₹259", R.drawable.restaurant_image_stuffed_aloo_sanjha)
)


@Composable
fun CompleteYourMealSection() {

    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("Popular", "Beverages", "Desserts", "Sides")

    // Track added items
    val addedItems = remember { mutableStateListOf<Int>() }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {

            // Title
            Text(
                text = "COMPLETE YOUR MEAL",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Rounded Tab Container
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(30.dp),
                color = Color(0xFFF5F5F5)
            ) {

                ScrollableTabRow(
                    selectedTabIndex = selectedTab,
                    containerColor = Color.Transparent,
                    edgePadding = 0.dp,
                    divider = {},
                    indicator = { tabPositions ->
                        if (selectedTab < tabPositions.size) {
                            val currentTab = tabPositions[selectedTab]
                            Box(
                                modifier = Modifier
                                    .tabIndicatorOffset(currentTab)
                                    .width(currentTab.width)
                                    .fillMaxHeight()
//                                    .padding(2.dp)
                                    .background(
                                        color = Color.Black,
                                        shape = RoundedCornerShape(30.dp)
                                    )
                                    .zIndex(0f) // Place indicator behind content
                            )
                        }
                    }
                ) {

                    tabs.forEachIndexed { index, title ->

                        Tab(
                            selected = selectedTab == index,
                            onClick = { selectedTab = index },
                            modifier = Modifier
                                .padding(horizontal = 0.dp, vertical = 0.dp)
                                .zIndex(1f), // Place tab content above indicator
                            text = {
                                Text(
                                    text = title,
                                    fontSize = 12.sp,
                                    fontWeight = if (selectedTab == index)
                                        FontWeight.Bold else FontWeight.Medium,
                                    color = if (selectedTab == index)
                                        Color.White else Color.Black,
                                    modifier = Modifier.padding(
                                        horizontal = 0.dp,
                                        vertical = 0.dp
                                    )
                                )
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Tab Content
            when (selectedTab) {
                0 -> PopularItems(addedItems)
                1 -> BeveragesItems(addedItems)
                2 -> DessertsItems(addedItems)
                3 -> SidesItems(addedItems)
            }
        }
    }
}

@Composable
fun CompleteYourMealHorizontalList(
    items: List<CompleteYourMealItemData>,
    addedItems: MutableList<Int>
) {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 0.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(items) { item ->

            val isAdded = addedItems.contains(item.id)

            CompleteYourMealItem(
                data = item,
                isAdded = isAdded,
                onAddClick = {
                    if (isAdded) {
                        addedItems.remove(item.id)
                    } else {
                        addedItems.add(item.id)
                    }
                }
            )
        }
    }
}
@Composable
fun PopularItems(addedItems: MutableList<Int>) {
    CompleteYourMealHorizontalList(popularItemsList, addedItems)
}

@Composable
fun BeveragesItems(addedItems: MutableList<Int>) {
    CompleteYourMealHorizontalList(beveragesItemsList, addedItems)
}

@Composable
fun DessertsItems(addedItems: MutableList<Int>) {
    CompleteYourMealHorizontalList(dessertsItemsList, addedItems)
}

@Composable
fun SidesItems(addedItems: MutableList<Int>) {
    CompleteYourMealHorizontalList(sidesItemsList, addedItems)
}



@Composable
fun CompleteYourMealItem(
    data: CompleteYourMealItemData,
    isAdded: Boolean = false,
    onAddClick: () -> Unit = {}
) {
    var added by remember { mutableStateOf(isAdded) }

    Column(
        modifier = Modifier
            .width(75.dp)
            .background(Color(0xFFF5F5F5), RoundedCornerShape(12.dp))
//            .padding(8.dp)
    ) {

        // Image with overlay button
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(75.dp)
                .clip(RoundedCornerShape(10.dp))
        ) {
            Image(
                painter = painterResource(id = data.imageRes!!),
                contentDescription = data.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            // Add button (top-right)
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(4.dp)
                    .size(26.dp)
                    .background(Color.White, CircleShape)
                    .clickable {
                        added = !added
                        onAddClick()
                    },
                contentAlignment = Alignment.Center
            ) {
                if (added) {
                    Icon(
                        Icons.Default.Check,
                        contentDescription = "Added",
                        tint = Color(0xFF4CAF50),
                        modifier = Modifier.size(16.dp)
                    )
                } else {
                    Text(
                        text = "+",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        // Title with icon (correct wrapping behavior)
        Box {
            Text(
                text = data.name,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                lineHeight = 16.sp,
                modifier = Modifier.padding(start = 18.dp) // space reserved for icon
            )

            Icon(
                painter = painterResource(
                    if (data.isVeg) R.drawable.ic_veg_rest
                    else R.drawable.ic_non_veg_rest
                ),
                contentDescription = if (data.isVeg) "Veg" else "Non-Veg",
                tint = Color.Unspecified,
                modifier = Modifier
                    .size(12.dp)
                    .align(Alignment.TopStart)
            )
        }

        Spacer(modifier = Modifier.height(2.dp))

        // Price
        Text(
            text = data.price,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
    }
}

@Composable
fun SavingsCornerCard(onAddClick: () -> Unit = {},
                      onRenewClick: () -> Unit = {}) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(0.dp)) {
            // Header with icon
            Row(
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 0.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "SAVINGS CORNER",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Apply Coupon row
            Row(modifier = Modifier.padding(horizontal = 16.dp)) {
                SavingsRow(
                    text = "Apply Coupon",
                    leftIconType = LeftIconType.COUPON,
                    rightIconType = RightIconType.CHEVRON
                )
            }

            HorizontalDivider(modifier = Modifier
                .padding(vertical = 8.dp),
                color = MaterialTheme.customColors.veryLightGray
            )

            // ₹180 saved row
            Row(modifier = Modifier.padding(horizontal = 16.dp)) {
                SavingsRow(
                    text = "₹180 saved with Items starting ₹25",
                    leftIconType = LeftIconType.COUPON,
                    rightIconType = RightIconType.APPLIED_TEXT
                )
            }

            HorizontalDivider(modifier = Modifier
                .padding(vertical = 8.dp),
                color = MaterialTheme.customColors.veryLightGray
            )

            // ₹20 extra off row
            Row(modifier = Modifier.padding(horizontal = 16.dp)) {
                SavingsRow(
                    text = "₹20 extra off above coupons",
                    leftIconType = LeftIconType.COUPON,
                    rightIconType = RightIconType.APPLIED_TEXT
                )
            }

            HorizontalDivider(modifier = Modifier
                .padding(vertical = 8.dp),
                color = MaterialTheme.customColors.veryLightGray
            )

            // ₹24 saved row
            Row(modifier = Modifier.padding(horizontal = 16.dp)) {
                SavingsRow(
                    text = "₹24 saved with free delivery",
                    leftIconType = LeftIconType.DELIVERY,
                    rightIconType = RightIconType.APPLIED_TEXT
                )
            }

            HorizontalDivider(modifier = Modifier
                .padding(vertical = 8.dp),
                color = MaterialTheme.customColors.veryLightGray
            )

            // Savings summary row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.one_badge),
                        contentDescription = null,
                        tint = Color.Unspecified,
                        modifier = Modifier.size(18.dp)
                    )
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = "You saved ₹594 with the last plan.",
                            fontSize = 14.sp,
                            color = Color.DarkGray
                        )
                        Text(
                            text = "Renew now!",
                            fontSize = 14.sp,
                            color = Color.DarkGray
                        )
                    }
                }

                Box(
                    modifier = Modifier
//                        .height(35.dp)
                        .border(
                            width = 1.dp,
                            color = MaterialTheme.customColors.orangeButton,
                            shape = RoundedCornerShape(12.dp)
                        )
                        .clip(RoundedCornerShape(12.dp))
                        .background(
                            color = Color.White,
                            shape = RoundedCornerShape(12.dp)
                        )
                        .clickable { onAddClick() }
                        .padding(horizontal = 16.dp, vertical = 6.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Add",
                        fontSize = 15.sp,
                        color = MaterialTheme.customColors.orangeButton
                    )
                }
            }

            // Renew row
            Row(
                modifier = Modifier
                    .padding(start = 45.dp, end = 16.dp, top = 0.dp, bottom = 8.dp)
                    .clickable { onRenewClick() },
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Renew One at ₹1 for 3 months",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
                Icon(
                    painter = painterResource(id = R.drawable.outline_arrow_forward_ios_24),
                    contentDescription = "View Cart",
                    tint = Color.Gray,
                    modifier = Modifier.size(12.dp)
                )
            }

        }
    }
}

// Define enums for left icon types
enum class LeftIconType {
    COUPON,
    DELIVERY,
    ONE,
    DEFAULT
}

// Define enums for right icon types
enum class RightIconType {
    CHEVRON,
    APPLIED_TEXT,
    NONE
}

@Composable
fun SavingsRow(
    text: String,
    leftIconType: LeftIconType = LeftIconType.DEFAULT,
    rightIconType: RightIconType = RightIconType.NONE
) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Left side with icon and text
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Left icon based on type
            when (leftIconType) {
                LeftIconType.COUPON -> {
                    Icon(
                        painter = painterResource(id = R.drawable.discount_badge_1),
                        contentDescription = "Coupon icon",
                        tint = Color.Unspecified,
                        modifier = Modifier.size(18.dp)
                    )
                }
                LeftIconType.DELIVERY -> {
                    Icon(
                        painter = painterResource(id = R.drawable.delivery_badge),
                        contentDescription = "Applied icon",
                        tint = Color.Unspecified,
                        modifier = Modifier.size(18.dp)
                    )
                }
                LeftIconType.ONE -> {
                    Icon(
                        painter = painterResource(id = R.drawable.one_badge),
                        contentDescription = "One badge icon",
                        tint = Color.Unspecified,
                        modifier = Modifier.size(18.dp)
                    )
                }
                LeftIconType.DEFAULT -> {
                    Icon(
                        Icons.Default.Savings,
                        contentDescription = "Savings icon",
                        tint = Color.Unspecified,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }

            Text(text, fontSize = 14.sp)
        }

        // Right side content based on type
        when (rightIconType) {
            RightIconType.CHEVRON -> {
                Icon(
                    Icons.Default.ChevronRight,
                    contentDescription = "Chevron right icon",
                    tint = Color.Black,
                    modifier = Modifier.size(25.dp)
                )
            }
            RightIconType.APPLIED_TEXT -> {
                Text(
                    "✓ Applied",
                    fontSize = 12.sp,
                    color = Color(0xFF388E3C),
                    fontWeight = FontWeight.Medium
                )
            }
            RightIconType.NONE -> {
                // No right content
            }
        }
    }
}


@Composable
fun DeliveryTypeCard() {
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("Delivery Type", "Tip", "Instructions")

    // State for selected delivery option
    var selectedDeliveryOption by remember { mutableStateOf(0) }

    // State for selected tip amount
    var selectedTip by remember { mutableStateOf(1) } // 0: ₹20, 1: ₹30, 2: ₹50
    var customTipAmount by remember { mutableStateOf("") }
    var showCustomTipInput by remember { mutableStateOf(false) }

    // State for instructions
    var directions by remember { mutableStateOf(false) } // Add this state
    var leaveAtDoor by remember { mutableStateOf(false) }
    var avoidRinging by remember { mutableStateOf(false) }
    var leaveWithSecurity by remember { mutableStateOf(false) }
    var avoidCalling by remember { mutableStateOf(false) }
    var directionsToReach by remember { mutableStateOf("") }


    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
//                .padding(16.dp)
        ) {
            // Tab Row
            Surface(
                modifier = Modifier.fillMaxWidth()
                    .padding(start = 12.dp, end = 12.dp, top = 12.dp, bottom = 0.dp),
                shape = RoundedCornerShape(30.dp),
                color = Color(0xFFF5F5F5)
            ) {
                ScrollableTabRow(
                    selectedTabIndex = selectedTab,
                    containerColor = Color.Transparent,
                    edgePadding = 0.dp,
                    divider = {},
                    indicator = { tabPositions ->
                        if (selectedTab < tabPositions.size) {
                            val currentTab = tabPositions[selectedTab]
                            Box(
                                modifier = Modifier
                                    .tabIndicatorOffset(currentTab)
                                    .width(currentTab.width)
                                    .fillMaxHeight()
                                    .background(
                                        color = Color.Black,
                                        shape = RoundedCornerShape(30.dp)
                                    )
                                    .zIndex(0f)
                            )
                        }
                    }
                ) {
                    tabs.forEachIndexed { index, title ->
                        Tab(
                            selected = selectedTab == index,
                            onClick = { selectedTab = index },
                            modifier = Modifier
                                .zIndex(1f),
                            text = {
                                Text(
                                    text = title,
                                    fontSize = 13.sp,
                                    fontWeight = if (selectedTab == index)
                                        FontWeight.Bold else FontWeight.Medium,
                                    color = if (selectedTab == index)
                                        Color.White else Color.Black,
                                    modifier = Modifier.padding(horizontal = 5.dp, vertical = 0.dp)
                                )
                            }
                        )
                    }
                }
            }

//            Spacer(modifier = Modifier.height(16.dp))

            // Tab Content
            when (selectedTab) {
                0 -> DeliveryTypeContent(
                    selectedOption = selectedDeliveryOption,
                    onOptionSelected = { selectedDeliveryOption = it }
                )
                1 -> TipContent(
                    selectedTip = selectedTip,
                    onTipSelected = { selectedTip = it },
                    customTipAmount = customTipAmount,
                    onCustomTipChange = { customTipAmount = it },
                    showCustomTipInput = showCustomTipInput,
                    onShowCustomTipInput = { showCustomTipInput = it }
                )
                2 -> InstructionsContent(
                    directions = directions,  // Add this
                    onDirectionsChange = { directions = it },  // Add this
                    leaveAtDoor = leaveAtDoor,
                    onLeaveAtDoorChange = { leaveAtDoor = it },
                    avoidRinging = avoidRinging,
                    onAvoidRingingChange = { avoidRinging = it },
                    leaveWithSecurity = leaveWithSecurity,
                    onLeaveWithSecurityChange = { leaveWithSecurity = it },
                    avoidCalling = avoidCalling,
                    onAvoidCallingChange = { avoidCalling = it },
                    directionsToReach = directionsToReach,
                    onDirectionsToReachChange = { directionsToReach = it }
                )
            }
        }
    }
}

@Composable
fun DeliveryTypeContent(
    selectedOption: Int,
    onOptionSelected: (Int) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()){
        // Express Option
        DeliveryOptionItem(
            title = "Express-4",
            price = "₹29",
            discountedPrice = "₹19",
            time = "20–25 mins",
            subtitle = "Fastest delivery, directly to you!",
            isSelected = selectedOption == 0,
            onClick = { onOptionSelected(0) }
        )
        HorizontalDivider(modifier = Modifier
            .padding(vertical = 0.dp),
            color = MaterialTheme.customColors.veryLightGray
        )
        // Standard Option
        DeliveryOptionItem(
            title = "Standard",
            price = null,
            discountedPrice = null,
            time = "25–30 mins",
            subtitle = "Minimal order grouping",
            isSelected = selectedOption == 1,
            onClick = { onOptionSelected(1) }
        )
        HorizontalDivider(modifier = Modifier
            .padding(vertical = 0.dp),
            color = MaterialTheme.customColors.veryLightGray
        )
        // Eco Saver Option
        DeliveryOptionItem(
            title = "Eco Saver",
            price = null,
            discountedPrice = null,
            time = "30–40 mins",
            subtitle = "Lesser CO2 by order grouping",
            isSelected = selectedOption == 2,
            onClick = { onOptionSelected(2) }
        )
    }
}

@Composable
fun DeliveryOptionItem(
    title: String,
    price: String?,
    discountedPrice: String?,
    time: String,
    subtitle: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
//            .background(
//                if (isSelected) Color(0xFFE3F2FD) else Color.Transparent
//            )
            .clickable { onClick() }
            .padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = isSelected,
                onClick = onClick,
                colors = RadioButtonDefaults.colors(
                    selectedColor = MaterialTheme.customColors.buttonRed
                )
            )
            Spacer(modifier = Modifier.width(4.dp))
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = title,
                        fontSize = 14.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                        color = if (isSelected) MaterialTheme.customColors.buttonRed else Color.Black
                    )
                    if (price != null) {
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = price,
                            fontSize = 12.sp,
                            color = Color.Gray,
                            textDecoration = TextDecoration.LineThrough
                        )
                    }
                    if (discountedPrice != null) {
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = discountedPrice,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.customColors.buttonRed
                        )
                    }
                }
                Text(
                    text = subtitle,
                    fontSize = 11.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(top = 0.dp)
                )
            }
        }

        Text(
            text = time,
            fontSize = 12.sp,
            fontWeight = if (isSelected) FontWeight.Medium else FontWeight.Normal,
            color = if (isSelected) MaterialTheme.customColors.buttonRed else Color.Gray,
            textAlign = TextAlign.End
        )
    }
}

@Composable
fun TipContent(
    selectedTip: Int,
    onTipSelected: (Int) -> Unit,
    customTipAmount: String,
    onCustomTipChange: (String) -> Unit,
    showCustomTipInput: Boolean,
    onShowCustomTipInput: (Boolean) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        // Description Row with Text and Image
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Day & night, our delivery partners bring your favourite meals. Thank them with a tip.",
                fontSize = 13.sp,
                color = Color.Gray,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(12.dp))

            Image(
                painter = painterResource(id = R.drawable.ic_tip_hand),
                contentDescription = "Thank delivery partner",
                modifier = Modifier
                    .size(68.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Tip Amount Options
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            TipAmountChip(
                amount = "₹20",
                isSelected = selectedTip == 0,
                onClick = {
                    onTipSelected(0)
                    onShowCustomTipInput(false)
                },
                modifier = Modifier.weight(1f)
            )

            TipAmountChip(
                amount = "₹30",
                isSelected = selectedTip == 1,
                onClick = {
                    onTipSelected(1)
                    onShowCustomTipInput(false)
                },
                modifier = Modifier.weight(1f)
            )

            TipAmountChip(
                amount = "₹50",
                isSelected = selectedTip == 2,
                onClick = {
                    onTipSelected(2)
                    onShowCustomTipInput(false)
                },
                modifier = Modifier.weight(1f)
            )

            TipAmountChip(
                amount = "Other",
                isSelected = showCustomTipInput,
                onClick = {
                    onShowCustomTipInput(!showCustomTipInput)
                    onTipSelected(-1)
                },
                modifier = Modifier.weight(1f)
            )
        }

        // Custom Tip Input
        if (showCustomTipInput) {
            Spacer(modifier = Modifier.height(12.dp))
            OutlinedTextField(
                value = customTipAmount,
                onValueChange = onCustomTipChange,
                placeholder = { Text("Enter amount") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                singleLine = true,
                prefix = { Text("₹") }
            )
        }
    }
}


@Composable
fun TipAmountChip(
    amount: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(
                if (isSelected) Color.White else Color.White
            )
            .border(
                width = 1.dp,
                color = if (isSelected) MaterialTheme.customColors.orangeButton else Color(0xFFE0E0E0),
                shape = RoundedCornerShape(12.dp)
            )
            .clickable { onClick() }
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 6.dp, vertical = 10.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = amount,
                fontSize = 14.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.SemiBold,
                color = if (isSelected) MaterialTheme.customColors.orangeButton else Color(0xFF212121)
            )
        }
    }
}


@Composable
fun InstructionsContent(
    directions: Boolean,
    onDirectionsChange: (Boolean) -> Unit, // Added this parameter
    leaveAtDoor: Boolean,
    onLeaveAtDoorChange: (Boolean) -> Unit,
    avoidRinging: Boolean,
    onAvoidRingingChange: (Boolean) -> Unit,
    leaveWithSecurity: Boolean,
    onLeaveWithSecurityChange: (Boolean) -> Unit,
    avoidCalling: Boolean,
    onAvoidCallingChange: (Boolean) -> Unit,
    directionsToReach: String,
    onDirectionsToReachChange: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Delivery Instructions Section
//        Text(
//            text = "Delivery Instructions",
//            fontSize = 14.sp,
//            fontWeight = FontWeight.Bold,
//            color = Color.Black
//        )

        // Horizontally scrollable instruction cards
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            InstructionCard(
                iconRes = R.drawable.ic_directions,
                title = "Directions",
                subtitle = "to reach",
                isSelected = directions,
                onClick = { onDirectionsChange(!directions) } // Fixed: now uses correct callback
            )

            InstructionCard(
                iconRes = R.drawable.ic_avoid_call,
                title = "Avoid",
                subtitle = "calling",
                isSelected = avoidCalling,
                onClick = { onAvoidCallingChange(!avoidCalling) }
            )

            InstructionCard(
                iconRes = R.drawable.ic_leave_door,
                title = "Leave at",
                subtitle = "the door",
                isSelected = leaveAtDoor,
                onClick = { onLeaveAtDoorChange(!leaveAtDoor) }
            )

            InstructionCard(
                iconRes = R.drawable.ic_bell,
                title = "Avoid",
                subtitle = "ringing bell",
                isSelected = avoidRinging,
                onClick = { onAvoidRingingChange(!avoidRinging) }
            )

            InstructionCard(
                iconRes = R.drawable.ic_security,
                title = "Leave with",
                subtitle = "security",
                isSelected = leaveWithSecurity,
                onClick = { onLeaveWithSecurityChange(!leaveWithSecurity) }
            )
        }

        // Optional: Show text field only when Directions is selected
//        if (directions) {
//            OutlinedTextField(
//                value = directionsToReach,
//                onValueChange = onDirectionsToReachChange,
//                placeholder = { Text("Add directions for delivery partner...") },
//                modifier = Modifier.fillMaxWidth(),
//                shape = RoundedCornerShape(8.dp),
//                minLines = 2,
//                maxLines = 4,
//                colors = OutlinedTextFieldDefaults.colors(
//                    focusedBorderColor = Color(0xFF1565C0),
//                    unfocusedBorderColor = Color(0xFFE0E0E0)
//                )
//            )
//        }
    }
}

@Composable
fun InstructionCard(
    iconRes: Int,
    title: String,
    subtitle: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .width(100.dp)
            .height(90.dp)
            .clip(RoundedCornerShape(12.dp))
            .clickable { onClick() },
        color = if (isSelected) Color(0xFFFFECE5) else Color.White,
        shape = RoundedCornerShape(12.dp),
        border = if (isSelected) BorderStroke(1.dp, MaterialTheme.customColors.orangeButton.copy(0.5f)) else  BorderStroke(1.dp, Color.Gray)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
//            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Icon at top
            Icon(
                painter = painterResource(id = iconRes),
                contentDescription = title,
                tint =  if (isSelected) MaterialTheme.customColors.orangeButton.copy(0.5f) else Color.Unspecified,
                modifier = Modifier.size(24.dp)
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Text in two lines
            Text(
                text = title,
                fontSize = 12.sp,
                fontWeight = if (isSelected) FontWeight.Medium else FontWeight.Normal,
                color = if (isSelected) Color.Black else Color.Black,
                textAlign = TextAlign.Center
            )

            Text(
                text = subtitle,
                fontSize = 12.sp,
                fontWeight = if (isSelected) FontWeight.Medium else FontWeight.Normal,
                color = if (isSelected) Color.Black else Color.Black,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun PaymentSummaryCard() {

    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize() // smooth expand/collapse
            .clickable { expanded = !expanded },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {

        Column(modifier = Modifier
//            .padding(16.dp)
        ) {

            // 🔹 Top Row (Always Visible)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Left side: Receipt Icon + Payment Details
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(
                        imageVector = Icons.Default.Receipt,
                        contentDescription = null,
                        tint = Color(0xFF2E7D32),
                        modifier = Modifier.size(20.dp)
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    // Payment Details
                    Column {
                        Row {
                            Text(
                                text = "To Pay",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "₹995.99",
                                fontSize = 14.sp,
                                color = Color.Gray,
                                textDecoration = TextDecoration.LineThrough
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "₹751",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Text(
                            text = "₹244.99 saved on the total!",
                            fontSize = 12.sp,
                            color = Color(0xFF2E7D32)
                        )
                    }
                }

                // Right side: Expand/Collapse Icon
                Icon(
                    imageVector = if (expanded) Icons.Default.KeyboardArrowUp
                    else Icons.Default.KeyboardArrowDown,
                    contentDescription = if (expanded) "Collapse" else "Expand",
                    modifier = Modifier.size(24.dp)
                )
            }

            // 🔽 Expandable Section
            if (expanded) {
                HorizontalDivider(modifier = Modifier
                    .padding(vertical = 0.dp),
                    color = MaterialTheme.customColors.veryLightGray
                )
                Column(modifier = Modifier.padding(16.dp)) {
                    PaymentRow(
                        "Item Total",
                        "₹572",
                        "₹352.01",
                        valueColor = MaterialTheme.customColors.success,
                        labelColor = Color.Black
                    )

                    PaymentRow(
                        label = "Delivery Fee | 60.0 kms",
                        value = "₹103.00",
                        valueColor = Color.Black,
                        labelColor = Color.Black,
                        showDottedUnderline = true,
                        makeItFree = true
                    )

                    Text(
                        text = "This fee fairly goes to our delivery partners for delivering your food",
                        fontSize = 11.sp,
                        color = Color.Gray,
                        modifier = Modifier.padding(start = 4.dp, bottom = 0.dp)
                    )

                    PaymentRow(
                        label = "Extra discount for you",
                        value = "-₹25.00",
                        valueColor = MaterialTheme.customColors.success,
                        labelColor = Color.Black,
                        showDottedUnderline = true
                    )
                    PaymentRow(
                        label = "Item Discount",
                        value = "-₹49.17",
                        valueColor = MaterialTheme.customColors.success,
                        labelColor = Color.Black,
                        showDottedUnderline = true
                    )

                    // Dashed line (dashes)
                    DashedLine(
                        dashLength = 12f,
                        gapLength = 8f,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )

                    PaymentRow(
                        label = "Delivery Tip",
                        value = "Add tip",
                        valueColor = MaterialTheme.customColors.buttonRed,
                        labelColor = Color.Black,
                    )

                    PaymentRow(
                        label = "Cancellation Fee",
                        value = "₹244",
                        valueColor = Color.Black,
                        labelColor = Color.Black,
                    )

                    PaymentRow(
                        label = "GST & Other Charges",
                        value = "₹76.72",
                        valueColor = Color.Black,
                        labelColor = Color.Black,
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Dashed line (dashes)
                    DashedLine(
                        dashLength = 12f,
                        gapLength = 8f,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    PaymentRow(
                        label = "To Pay",
                        value = "₹751",
                        isBold = true,
                        labelColor = Color.Black,
                        valueColor = Color.Black,
                    )
                }

            }
        }
    }
}

@Composable
fun DashedLine(
    color: Color = Color.LightGray,
    dashLength: Float = 20f,
    gapLength: Float = 12f,
    strokeWidth: Dp = 1.dp,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(strokeWidth * 2)
            .drawBehind {
                val y = size.height / 2
                drawLine(
                    color = color,
                    start = Offset(0f, y),
                    end = Offset(size.width, y),
                    strokeWidth = strokeWidth.toPx(),
                    pathEffect = PathEffect.dashPathEffect(
                        floatArrayOf(dashLength, gapLength), 0f
                    )
                )
            }
    )
}

@Composable
fun PaymentRow(
    label: String,
    value: String,
    strikeValue: String? = null,
    labelColor: Color = Color.DarkGray,
    valueColor: Color = Color.Black,
    isBold: Boolean = false,
    showDottedUnderline: Boolean = false, // 👈 control when to show
    makeItFree: Boolean = false // 👈 control when to show
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .weight(0.7f)
        ) {
            Text(
                text = label,
                fontSize = 14.sp,
                color = labelColor,
                modifier = if (showDottedUnderline) {
                    Modifier.drawBehind {
                        val strokeWidth = 2f
                        val y = size.height + 4f

                        drawLine(
                            color = Color.LightGray,
                            start = Offset(0f, y),
                            end = Offset(size.width, y),
                            strokeWidth = strokeWidth,
                            pathEffect = PathEffect.dashPathEffect(
                                floatArrayOf(6f, 6f), 0f // dotted effect
                            )
                        )
                    }
                } else Modifier
            )
        }

        Row(
            modifier = Modifier.weight(0.3f),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            strikeValue?.let {
                Text(
                    text = it,
                    fontSize = 14.sp,
                    color = Color.Gray,
                    textDecoration = TextDecoration.LineThrough
                )
                Spacer(modifier = Modifier.width(6.dp))
            }
        Column(
            horizontalAlignment = Alignment.End   // Aligns the entire Column to the right
        ) {
            Text(
                text = value,
                fontSize = 14.sp,
                fontWeight = if (isBold) FontWeight.Bold else FontWeight.Normal,
                color = valueColor
            )
            if (makeItFree) {
                Text(
                    text = "Make it FREE",
                    fontSize = 14.sp,
                    fontWeight = if (isBold) FontWeight.Bold else FontWeight.Normal,
                    color = MaterialTheme.customColors.buttonRed
                )
            }
        }
        }
    }
}

@Composable
fun PaymentButtonF(
    amount: String = "₹1710",
    paymentMethod: String = "Credit card",
    cardLast4: String = "6247",
    onClick: () -> Unit = {}
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        tonalElevation = 6.dp,
        shadowElevation = 6.dp,
        color = Color.White
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp)
                .padding(bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Left Section (Payment Info)
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .border(1.dp, Color.LightGray, RoundedCornerShape(4.dp))
                            .background(Color.White)
                            .padding(horizontal = 6.dp, vertical = 6.dp)
                    ) {
                        Image(
                            painter = painterResource(R.drawable.ic_visa),
                            contentDescription = "VISA",
                            modifier = Modifier
                                .height(10.dp)
                                .width(30.dp),
                            contentScale = ContentScale.FillBounds
                        )
                    }

                    Spacer(modifier = Modifier.width(6.dp))

                    Text(
                        text = "PAY USING",
                        fontSize = 14.sp,
                        color = Color.Gray,
                        fontWeight = FontWeight.Medium
                    )

                    Icon(
                        painter = painterResource(R.drawable.outline_arrow_drop_up_24),
                        contentDescription = null,
                        tint = Color.Gray,
                        modifier = Modifier.size(40.dp)
                    )
                }

                Text(
                    text = "$paymentMethod  ••  $cardLast4",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black
                )
            }

            Spacer(modifier = Modifier.width(15.dp))

            // Pay Button
            Button(
                onClick = onClick,
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.customColors.greenButton
                ),
                contentPadding = PaddingValues(horizontal = 50.dp, vertical = 20.dp)
            ) {
                Text(
                    text = "Pay $amount",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
    }
}

@Composable
fun AddressAddCheckout(
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF5F5F5))
            .padding(16.dp)
            .padding(bottom = 16.dp),
    ) {
        Row(
            verticalAlignment = Alignment.Top,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                imageVector = Icons.Default.Navigation,
                contentDescription = "Location",
                tint = MaterialTheme.customColors.orangeButton,
                modifier = Modifier
                    .size(30.dp)
                    .padding(top = 4.dp)
                    .rotate(45f)
            )

            Spacer(modifier = Modifier.width(10.dp))

            Text(
                text = "Where would you like us to deliver this order?",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF333333),
                lineHeight = 24.sp
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = onClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFFF5A00)
            ),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
        ) {
            Text(
                text = "Add or Select address",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}




@Preview(showBackground = true)
@Composable
fun CheckOutFoodPreview() {
    MaterialTheme {
        CheckOutFood()
    }
}