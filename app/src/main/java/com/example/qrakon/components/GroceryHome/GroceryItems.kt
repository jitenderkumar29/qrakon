package com.example.qrakon.components.GroceryHome

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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

// -------------------- DATA --------------------

data class GroceryItemTwo(
    val id: Int,
    val discount: String,
    val title: String,
    val weight: String,
    val discountedPrice: String,
    val originalPrice: String,
    val imageRes: Int,
    var quantity: Int = 0
)

// -------------------- UI --------------------

@Composable
fun GroceryItemsHorizontal(
    items: List<GroceryItemTwo>,
    onQuantityChange: (Int, Int) -> Unit,
    modifier: Modifier = Modifier,
    rows: Int = 2 // Dynamic rows parameter with default 2
) {
    LazyHorizontalGrid(
        rows = GridCells.Fixed(rows), // Dynamic rows
        modifier = modifier
            .fillMaxWidth()
            .height((rows * 225).dp) // Dynamic height based on rows
            .padding(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(items) { item ->
            GroceryItemCard(
                item = item,
                onQuantityChange = { newQuantity ->
                    onQuantityChange(item.id, newQuantity)
                }
            )
        }
    }
}

@Composable
fun GroceryItemCard(
    item: GroceryItemTwo,
    onQuantityChange: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    // Local state for quantity
    var quantity by remember { mutableIntStateOf(item.quantity) }

    Card(
        modifier = modifier.width(110.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(5.dp)
            ) {
                // Image + Discount
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .clip(RoundedCornerShape(8.dp))
                ) {
                    Image(
                        painter = painterResource(id = item.imageRes),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxSize()
                            .width(50.dp)
                            .padding(vertical = 2.dp, horizontal = 25.dp),
                        contentScale = ContentScale.FillBounds
                    )

                    Text(
                        text = item.discount,
                        color = Color.White,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .background(Color(0xFFFF6B6B), RoundedCornerShape(4.dp))
                            .padding(horizontal = 4.dp, vertical = 2.dp)
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                // Title
                Text(
                    text = item.title,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    lineHeight = 14.sp,
                    color = MaterialTheme.customColors.black
                )

                // Weight
                Text(
                    text = item.weight,
                    fontSize = 11.sp,
                    color = MaterialTheme.customColors.gray
                )

                // Price Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = item.discountedPrice,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.customColors.black
                    )

                    if (item.originalPrice.isNotEmpty()) {
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = item.originalPrice,
                            fontSize = 11.sp,
                            color = MaterialTheme.customColors.gray,
                            textDecoration = TextDecoration.LineThrough
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(5.dp))
            // Fixed Quantity button - now properly positioned within card boundaries
            QuantityButton(
                quantity = quantity,
                onQuantityChange = { newQuantity ->
                    quantity = newQuantity
                    onQuantityChange(newQuantity)
                },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = 0.dp, bottom = 0.dp) // Add padding instead of offset
            )
        }
    }
}

@Composable
fun QuantityButton(
    quantity: Int,
    onQuantityChange: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    if (quantity == 0) {
        // Show only + button when quantity is 0
        Box(
            modifier = modifier
                .border(
                    width = 1.dp,
                    color = MaterialTheme.customColors.success,
                    shape = RoundedCornerShape(16.dp)
                )
                .background(
                    color = MaterialTheme.customColors.success,
                    shape = RoundedCornerShape(16.dp)
                )
                .clickable {
                    onQuantityChange(1)
                }
                .padding(horizontal = 12.dp, vertical = 4.dp)
        ) {
            Text(
                text = "+",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.customColors.white
            )
        }
    } else {
        // Show - quantity + buttons when quantity >= 1
        Box(
            modifier = modifier
                .border(
                    width = 1.dp,
                    color = MaterialTheme.customColors.success,
                    shape = RoundedCornerShape(16.dp)
                )
                .background(
                    color = MaterialTheme.customColors.success,
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(horizontal = 4.dp, vertical = 2.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                // Minus button
                Text(
                    text = "-",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.customColors.white,
                    modifier = Modifier
                        .clickable {
                            onQuantityChange(quantity - 1)
                        }
                        .padding(horizontal = 4.dp)
                )

                // Quantity
                Text(
                    text = quantity.toString(),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.customColors.white,
                    modifier = Modifier.padding(horizontal = 2.dp)
                )

                // Plus button
                Text(
                    text = "+",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.customColors.white,
                    modifier = Modifier
                        .clickable {
                            onQuantityChange(quantity + 1)
                        }
                        .padding(horizontal = 4.dp)
                )
            }
        }
    }
}

// Preview with different row counts
@Preview(showBackground = true)
@Composable
fun PreviewGroceryItemsHorizontal() {
    val sampleItems = listOf(
        GroceryItemTwo(
            id = 1,
            discount = "12% OFF",
            title = "Fortune Sunlite Sunflower Oil",
            weight = "800 g",
            discountedPrice = "₹163",
            originalPrice = "₹185",
            imageRes = R.drawable.fortune_sunlite_sunflower_oil,
            quantity = 0
        ),
        GroceryItemTwo(
            id = 2,
            discount = "20% OFF",
            title = "Fortune Premium Kachi Ghani Pure Mustard Oil",
            weight = "1 L",
            discountedPrice = "₹168",
            originalPrice = "₹210",
            imageRes = R.drawable.fortune_premium_kachi_ghani_mustard_oil,
            quantity = 0
        ),
        GroceryItemTwo(
            id = 3,
            discount = "18% OFF",
            title = "Fortune Premium Kachi Ghani Pure Mustard Oil",
            weight = "1 L",
            discountedPrice = "₹176",
            originalPrice = "₹215",
            imageRes = R.drawable.fortune_premium_kachi_ghani_mustard_oil_1,
            quantity = 0
        ),
        GroceryItemTwo(
            id = 4,
            discount = "22% OFF",
            title = "Exo Anti-Bacterial Diswash Bar",
            weight = "Pack of 4, 120 g",
            discountedPrice = "₹30",
            originalPrice = "",
            imageRes = R.drawable.exo_antibacterial_dishwash_bar,
            quantity = 0
        )
    )

    Column {
        // 2 rows (default)
        Text("2 Rows", fontSize = 18.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(8.dp))
        GroceryItemsHorizontal(
            items = sampleItems,
            onQuantityChange = { itemId, newQuantity ->
                println("Item $itemId quantity changed to $newQuantity")
            },
            rows = 2
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 1 row
        Text("1 Row", fontSize = 18.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(8.dp))
        GroceryItemsHorizontal(
            items = sampleItems.take(3),
            onQuantityChange = { itemId, newQuantity ->
                println("Item $itemId quantity changed to $newQuantity")
            },
            rows = 1
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 3 rows
        Text("3 Rows", fontSize = 18.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(8.dp))
        GroceryItemsHorizontal(
            items = sampleItems + sampleItems, // Duplicate to have more items
            onQuantityChange = { itemId, newQuantity ->
                println("Item $itemId quantity changed to $newQuantity")
            },
            rows = 3
        )
    }
}

// Example usage in your app
@Composable
fun GrocerySection(
    title: String,
    items: List<GroceryItemTwo>,
    rows: Int = 2
) {
    Column {
        Text(
            text = title,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )

        GroceryItemsHorizontal(
            items = items,
            onQuantityChange = { itemId, newQuantity ->
                // Handle quantity change
            },
            rows = rows
        )
    }
}

// Usage
//val sampleItems = listOf(
//    GroceryItemTwo(
//        id = 1,
//        discount = "12% OFF",
//        title = "Fortune Sunlite Sunflower Oil",
//        weight = "800 g",
//        discountedPrice = "₹163",
//        originalPrice = "₹185",
//        imageRes = R.drawable.fortune_sunlite_sunflower_oil,
//        quantity = 0
//    ),
//    GroceryItemTwo(
//        id = 2,
//        discount = "20% OFF",
//        title = "Fortune Premium Kachi Ghani Pure Mustard Oil",
//        weight = "1 L",
//        discountedPrice = "₹168",
//        originalPrice = "₹210",
//        imageRes = R.drawable.fortune_premium_kachi_ghani_mustard_oil,
//        quantity = 0
//    ),
//    GroceryItemTwo(
//        id = 3,
//        discount = "18% OFF",
//        title = "Fortune Premium Kachi Ghani Pure Mustard Oil",
//        weight = "1 L",
//        discountedPrice = "₹176",
//        originalPrice = "₹215",
//        imageRes = R.drawable.fortune_premium_kachi_ghani_mustard_oil_1,
//        quantity = 0
//    ),
//    GroceryItemTwo(
//        id = 4,
//        discount = "22% OFF",
//        title = "Exo Anti-Bacterial Diswash Bar",
//        weight = "Pack of 4, 120 g",
//        discountedPrice = "₹30",
//        originalPrice = "",
//        imageRes = R.drawable.exo_antibacterial_dishwash_bar,
//        quantity = 0
//    ),
//    GroceryItemTwo(
//        id = 5,
//        discount = "22% OFF",
//        title = "Parle Platina Hide & Seek Cookies",
//        weight = "200 g",
//        discountedPrice = "₹42",
//        originalPrice = "₹54",
//        imageRes = R.drawable.parle_platina_hide_seek_cookies,
//        quantity = 0
//    ),
//    GroceryItemTwo(
//        id = 6,
//        discount = "12% OFF",
//        title = "Fortune Sunlite Sunflower Oil Jar",
//        weight = "4.35 kg",
//        discountedPrice = "₹929",
//        originalPrice = "₹1,055",
//        imageRes = R.drawable.fortune_sunlite_sunflower_oil_jar_1,
//        quantity = 0
//    ),
//    GroceryItemTwo(
//        id = 7,
//        discount = "18% OFF",
//        title = "Maggi 2-Minute Masala Instant Noodles",
//        weight = "Pack of 12, 70 g",
//        discountedPrice = "₹148",
//        originalPrice = "₹180",
//        imageRes = R.drawable.maggi_masala_instant_noodles_pack,
//        quantity = 0
//    ),
//    GroceryItemTwo(
//        id = 8,
//        discount = "34% OFF",
//        title = "Tata Sampann Toor Dal/Arhar Dal",
//        weight = "1 kg",
//        discountedPrice = "₹162",
//        originalPrice = "₹244",
//        imageRes = R.drawable.tata_sampann_toor_dal_1,
//        quantity = 0
//    ),
//    GroceryItemTwo(
//        id = 9,
//        discount = "27% OFF",
//        title = "Vedaka Raw Peanuts",
//        weight = "1 kg",
//        discountedPrice = "₹219",
//        originalPrice = "₹300",
//        imageRes = R.drawable.vedaka_raw_peanuts,
//        quantity = 0
//    ),
//    GroceryItemTwo(
//        id = 10,
//        discount = "18% OFF",
//        title = "Saffola Active Multi-Source Oil",
//        weight = "850 g",
//        discountedPrice = "₹153",
//        originalPrice = "₹187",
//        imageRes = R.drawable.saffola_active_multisource_oil,
//        quantity = 0
//    ),
//)
//MaterialTheme {
//    GroceryItemsHorizontal(items = sampleItems,
//        onQuantityChange = { itemId, newQuantity ->
//            // Handle quantity change (e.g., update cart, etc.)
//            println("Item $itemId quantity changed to $newQuantity")
//        },
//        rows = 2)
//}