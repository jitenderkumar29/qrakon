package com.example.qrakon.components.restaurants

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.qrakon.R
import com.example.qrakon.ui.theme.customColors

@Composable
fun AddRestItemPizza(
    mainItemName: String = "Pizza Theatre Special Pizza",
    mainItemDescription: String = "tomatoes and olives with extra cheese.",
    mainItemIconRes: Int? = null,
    regularPrice: Int = 289,
    mediumPrice: Int = 479,
    largePrice: Int = 579,
    onDismiss: () -> Unit = {},
    onAddToCart: (selectedSize: String, quantity: Int, note: String, totalPrice: Int) -> Unit = { _, _, _, _ -> }
) {
    var selectedSize by remember { mutableStateOf("Regular") }
    var quantity by remember { mutableStateOf(1) }
    var note by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable(onClick = onDismiss)
    ) {

        // Main Content Container
        Column(
            modifier = Modifier
                .fillMaxSize()
                .clickable(enabled = false, onClick = {})
        ) {

            // 🔥 CLOSE BUTTON - Top Center
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 5.dp, bottom = 5.dp),
                contentAlignment = Alignment.Center
            ) {
                IconButton(
                    onClick = onDismiss,
                    modifier = Modifier
                        .size(40.dp)
                        .background(
                            color = Color.Black.copy(alpha = 0.6f),
                            shape = CircleShape
                        )
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close",
                        tint = Color.White
                    )
                }
            }

            // Scrollable Content
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
                    .background(MaterialTheme.customColors.background2)
            ) {
                // Header
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        // Dynamic Icon Box with Rounded Corners
                        Box(
                            modifier = Modifier
                                .size(50.dp)
                                .clip(RoundedCornerShape(12.dp))  // 👈 Clip the box to rounded corners
                                .background(Color.LightGray, RoundedCornerShape(12.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            if (mainItemIconRes != null) {
                                Image(
                                    painter = painterResource(mainItemIconRes),
                                    contentDescription = mainItemName,
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .clip(RoundedCornerShape(12.dp)),
                                    contentScale = ContentScale.FillBounds,
                                    colorFilter = null // Remove any tint to show original colors
                                )
                            } else {
                                // Default placeholder icon
                                Icon(
                                    painter = painterResource(R.drawable.outline_photo_camera_24),
                                    contentDescription = "Food item",
                                    tint = Color.Gray,
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(8.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        Column {
                            // Dynamic Title
                            Text(
                                text = mainItemName,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )
                            // Dynamic Description
                            Text(
                                text = mainItemDescription,
                                fontSize = 12.sp,
                                color = Color.Gray
                            )
                        }
                    }
                }

                // Size Selection Card
                item {
                    Column(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .background(Color.White, RoundedCornerShape(16.dp))
                            .padding(16.dp)
                    ) {

                        Text("Size", fontWeight = FontWeight.Bold)
                        Text(
                            "Required • Select any 1 option",
                            fontSize = 12.sp,
                            color = Color.Gray
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        SizeOption("Regular [7 inches]", "₹$regularPrice", selectedSize) {
                            selectedSize = "Regular"
                        }

                        SizeOption("Medium [9 inches]", "₹$mediumPrice", selectedSize) {
                            selectedSize = "Medium"
                        }

                        SizeOption("Large [12 inches]", "₹$largePrice", selectedSize) {
                            selectedSize = "Large"
                        }
                    }
                }

                // Cooking Request Card
                item {
                    Column(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .padding(top = 16.dp)
                            .background(Color.White, RoundedCornerShape(16.dp))
                            .padding(16.dp)
                    ) {

                        Text("Add a cooking request (optional)", fontWeight = FontWeight.Bold)

                        Spacer(modifier = Modifier.height(8.dp))

                        OutlinedTextField(
                            value = note,
                            onValueChange = { note = it },
                            placeholder = { Text("e.g. Don't make it too spicy") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(100.dp),
                            maxLines = 4
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Chip("No chilli") { note = if (note.contains("No chilli")) note.replace("No chilli", "").trim() else "$note No chilli".trim() }
                            Chip("No onion or garlic") { note = if (note.contains("No onion or garlic")) note.replace("No onion or garlic", "").trim() else "$note No onion or garlic".trim() }
                            Chip("No mushrooms") { note = if (note.contains("No mushrooms")) note.replace("No mushrooms", "").trim() else "$note No mushrooms".trim() }
                        }
                    }
                }

                // Bottom padding
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }

            // Sticky Bottom Bar
            BottomBarPizza(
                quantity = quantity,
                onIncrease = { quantity++ },
                onDecrease = { if (quantity > 1) quantity-- },
                totalPrice = calculateTotalPrice(selectedSize, quantity, regularPrice, mediumPrice, largePrice),
                onAddClick = {
                    onAddToCart(selectedSize, quantity, note, calculateTotalPrice(selectedSize, quantity, regularPrice, mediumPrice, largePrice))
                    onDismiss()
                }
            )
        }
    }
}

// Helper function to calculate total price
private fun calculateTotalPrice(selectedSize: String, quantity: Int, regularPrice: Int, mediumPrice: Int, largePrice: Int): Int {
    val basePrice = when (selectedSize) {
        "Regular" -> regularPrice
        "Medium" -> mediumPrice
        "Large" -> largePrice
        else -> regularPrice
    }
    return basePrice * quantity
}

// -------- Size Option --------
@Composable
fun SizeOption(
    title: String,
    price: String,
    selected: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(title)
            Text(price, fontWeight = FontWeight.Bold)
        }

        RadioButton(
            selected = selected in title,
            onClick = onClick,
            colors = RadioButtonDefaults.colors(selectedColor = Color(0xFFE53935))
        )
    }
}

// -------- Custom Chip --------
@Composable
fun Chip(text: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(Color(0xFFF1F1F1))
            .clickable { onClick() }
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Text(text, fontSize = 12.sp, color = Color.DarkGray)
    }
}

// -------- Bottom Bar --------
@Composable
fun BottomBarPizza(
    quantity: Int,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit,
    totalPrice: Int,
    onAddClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        // 🔢 QUANTITY SELECTOR
        Row(
            modifier = Modifier
                .border(1.dp, Color.LightGray, RoundedCornerShape(8.dp))
                .height(40.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            TextButton(onClick = onDecrease) {
                Text("-", fontSize = 18.sp, color = Color.Black)
            }

            Text(
                text = "$quantity",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 8.dp)
            )

            TextButton(onClick = onIncrease) {
                Text("+", fontSize = 18.sp, color = Color.Black)
            }
        }

        Spacer(modifier = Modifier.width(12.dp))

        // 🟢 ADD BUTTON
        Button(
            onClick = onAddClick,
            modifier = Modifier
                .weight(1f)
                .height(40.dp),
            shape = RoundedCornerShape(5.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.customColors.success
            )
        ) {
            Text(
                text = "Add Item | ₹$totalPrice",
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
}

// -------- Preview --------
@Preview(showBackground = true, widthDp = 360, heightDp = 800)
@Composable
fun AddRestItemPizzaPreview() {
    AddRestItemPizza(
        mainItemName = "Margherita Pizza",
        mainItemDescription = "Fresh tomatoes, mozzarella, and basil on a crispy crust.",
        mainItemIconRes = R.drawable.restaurant_image_pizzas_food_items_1,
        regularPrice = 289,
        mediumPrice = 479,
        largePrice = 579,
        onAddToCart = { size, quantity, note, totalPrice ->
            println("Added to cart: Size: $size, Quantity: $quantity, Note: $note, Total: ₹$totalPrice")
        }
    )
}