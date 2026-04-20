package com.example.qrakon.components.restaurants

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.example.qrakon.R
import com.example.qrakon.ui.theme.customColors


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckOutFood(
    onBackClick: () -> Unit = {},
    onConfirmOrder: () -> Unit = {},
    restaurantName: String = "Pizza Wings",
    restaurantAddress: String = "F109, Arnab House, 4th Floor, Block - F..."
) {
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
                                    Color(0xFFCFEAFF),
                                    Color(0xFFFDFEFF)
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
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(Icons.Default.Home, contentDescription = "Home", tint = Color.Black, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(text = "Home |", fontSize = 13.sp, color = Color.Black, fontWeight = FontWeight.Bold)
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = restaurantAddress,
                                    fontSize = 13.sp,
                                    color = Color.DarkGray,
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

                    // Saved Info Card
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 12.dp, end = 12.dp, top = 0.dp, bottom = 8.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(
                                brush = Brush.horizontalGradient(
                                    colors = listOf(Color(0xFF84C6FA), Color(0xFFFFFFFF))
                                )
                            )
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "224 Saved with store", fontSize = 12.sp, color = Color.Black)
                        }
                    }
                }
            }
        },
        containerColor = Color(0xFFF5F5F5)
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(paddingValues),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Order Items Section
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Column(modifier = Modifier.padding(6.dp)) {
                        // Membership Expired Card
                        MembershipExpiredCard(onRenewClick = onConfirmOrder)

                        // Order Items
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
//            item {
//                DeliveryTypeCard()
//            }
//
//            // Payment Summary Section
//            item {
//                PaymentSummaryCard(onPayClick = onConfirmOrder)
//            }
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
                    Icon(
                        painter = painterResource(id = R.drawable.icon_one),
                        contentDescription = "One",
                        tint = Color.Unspecified,
                        modifier = Modifier.height(15.dp)
                    )
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

    Column(modifier = Modifier.padding(12.dp)) {
        // Item 1: Spicy Twisties
        OrderItemWithQuantity(
            name = "Spicy Twisties 4pc",
            originalPrice = "₹45",
            discountedPrice = "₹25",
            quantity = twistiesQuantity,
            onQuantityChange = { twistiesQuantity = it }
        )

//        Divider(modifier = Modifier.padding(vertical = 8.dp))

        // Item 2: Cheese Margherita Cheese Burst Pizza
        OrderItemWithQuantityAndCustomization(
            name = "Cheese Margherita Cheese Burst Pizza",
            originalPrice = "₹599",
            discountedPrice = "₹439",
            finalPrice = "₹399",
            subtitle = "Cheese Burst Medium, Medium ...",
            quantity = pizzaQuantity,
            onQuantityChange = { pizzaQuantity = it },
            showDropdown = true,
            onDropdownClick = { }
        )

//        Divider(modifier = Modifier.padding(vertical = 8.dp))
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
                fontSize = 16.sp,
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
                    .padding(horizontal = 12.dp, vertical = 6.dp)
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
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = quantity.toString(),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.width(16.dp))
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
                modifier = Modifier.width(30.dp)
            ) {
                Text(
                    text = originalPrice,
                    fontSize = 12.sp,
                    color = Color.Gray,
                    textDecoration = TextDecoration.LineThrough
                )
                Text(
                    text = discountedPrice,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFE53935)
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
//                verticalAlignment = Alignment.CenterVertically,
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
                    fontSize = 16.sp,
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
                    .padding(horizontal = 12.dp, vertical = 6.dp)
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
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = quantity.toString(),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.width(16.dp))
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
                modifier = Modifier.width(30.dp)
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
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF388E3C),
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
            .width(90.dp)
            .background(Color(0xFFF5F5F5), RoundedCornerShape(12.dp))
//            .padding(8.dp)
    ) {

        // Image with overlay button
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(90.dp)
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
                fontSize = 13.sp,
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
                    .size(14.dp)
                    .align(Alignment.TopStart)
            )
        }

        Spacer(modifier = Modifier.height(2.dp))

        // Price
        Text(
            text = data.price,
            fontSize = 14.sp,
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

            Divider(modifier = Modifier.padding(vertical = 8.dp))

            // ₹180 saved row
            Row(modifier = Modifier.padding(horizontal = 16.dp)) {
                SavingsRow(
                    text = "₹180 saved with Items starting ₹25",
                    leftIconType = LeftIconType.COUPON,
                    rightIconType = RightIconType.APPLIED_TEXT
                )
            }

            Divider(modifier = Modifier.padding(vertical = 8.dp))

            // ₹20 extra off row
            Row(modifier = Modifier.padding(horizontal = 16.dp)) {
                SavingsRow(
                    text = "₹20 extra off above coupons",
                    leftIconType = LeftIconType.COUPON,
                    rightIconType = RightIconType.APPLIED_TEXT
                )
            }

            Divider(modifier = Modifier.padding(vertical = 8.dp))

            // ₹24 saved row
            Row(modifier = Modifier.padding(horizontal = 16.dp)) {
                SavingsRow(
                    text = "₹24 saved with free delivery",
                    leftIconType = LeftIconType.DELIVERY,
                    rightIconType = RightIconType.APPLIED_TEXT
                )
            }

            Divider(modifier = Modifier.padding(vertical = 8.dp))

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
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Delivery Type", fontWeight = FontWeight.Bold)
                Text("Tip", fontWeight = FontWeight.Bold)
                Text("Instructions", fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.height(12.dp))
            DeliveryOptionRow(title = "Express - ₹29 ₹19", time = "20-25 mins", isSelected = true)
            DeliveryOptionRow(title = "Standard", subtitle = "Minimal order grouping", time = "25-30 mins")
            DeliveryOptionRow(title = "Eco Saver", subtitle = "Lesser CO2 by order grouping", time = "30-40 mins")
        }
    }
}

@Composable
fun DeliveryOptionRow(title: String, subtitle: String = "", time: String, isSelected: Boolean = false) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(if (isSelected) Color(0xFFE3F2FD) else Color.Transparent)
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = title,
                fontSize = 14.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                color = if (isSelected) Color(0xFF1565C0) else Color.Black
            )
            if (subtitle.isNotEmpty()) {
                Text(subtitle, fontSize = 11.sp, color = Color.Gray)
            }
        }
        Text(time, fontSize = 12.sp, color = Color.Gray)
    }
}

@Composable
fun PaymentSummaryCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "To Pay", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Column(horizontalAlignment = Alignment.End) {
                    Text(text = "₹1356 ₹1132", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    Text(text = "₹224 saved on the total!", fontSize = 12.sp, color = Color(0xFF388E3C))
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "FREE Delivery on your order!", fontSize = 14.sp, fontWeight = FontWeight.Medium, color = Color(0xFF388E3C))
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "Cancellation policy:\nPlease double-check your order and address details. Orders are non-refundable once placed.",
                fontSize = 12.sp,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(16.dp))
            Divider()
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.CreditCard, contentDescription = null, modifier = Modifier.size(20.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Credit card - 6247", fontSize = 14.sp)
                }
                Button(
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE53935)),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Pay ₹1132", fontSize = 16.sp)
                }
            }
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