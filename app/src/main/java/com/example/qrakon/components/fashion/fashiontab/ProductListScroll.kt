package com.example.qrakon.components.fashion.fashiontab

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.qrakon.ui.theme.customColors

// ✅ Reusable ProductListScroll Model
data class ProductListScroll(
    val name: String,
    val price: String,
    val imageRes: Int
)

// ✅ Reusable ProductCard with optional fields
@Composable
fun ProductCard(
    product: ProductListScroll,
    isSelected: Boolean,
    onProductClick: () -> Unit,
    modifier: Modifier = Modifier,
    showName: Boolean = true,
    showPrice: Boolean = true,
    backgroundColor: Color = Color.White // Default background color
) {
    val cardElevation = if (isSelected) 12.dp else 6.dp

    Card(
        modifier = modifier.clickable { onProductClick() },
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = cardElevation),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            // Image section
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .background(backgroundColor)
                    .clip(RoundedCornerShape(0.dp)),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = product.imageRes),
                    contentDescription = product.name,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.FillBounds
                )
            }

            // Optional Name & Price with proper spacing and background color
            if (showName || showPrice) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .background(
                            color = backgroundColor,
                            shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp)
                        )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp)
                            .padding(vertical = 8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        if (showName) {
                            Text(
                                text = product.name,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF333333),
                                textAlign = TextAlign.Center,
                                maxLines = 2,
                                modifier = Modifier.padding(bottom = if (showPrice) 8.dp else 0.dp)
                            )
                        }
                        if (showPrice) {
                            Text(
                                text = product.price,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color(0xFFE91E63),
                                textAlign = TextAlign.Center,
                                lineHeight = 16.sp
                            )
                        }
                    }
                }
            }
        }
    }
}

// ✅ Horizontal Scrollable ProductListScroll List
@Composable
fun ProductListScroll(
    products: List<ProductListScroll>,
    modifier: Modifier = Modifier,
    sectionTitle: String? = null,
    showName: Boolean = true,
    showPrice: Boolean = true,
    backgroundColor: Color = Color.White // New backgroundColor parameter with default value
) {
    var selectedProduct by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(12.dp)
    ) {
        // Optional Section Title
        sectionTitle?.let {
            Text(
                text = it,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.customColors.black,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }

        // Horizontal Scrollable Row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            products.forEach { product ->
                ProductCard(
                    product = product,
                    isSelected = selectedProduct == product.name,
                    onProductClick = { selectedProduct = product.name },
                    modifier = Modifier
                        .width(160.dp)
                        .height(320.dp),
                    showName = showName,
                    showPrice = showPrice,
                    backgroundColor = backgroundColor // Pass the background color to ProductCard
                )
            }
        }

        // Show selected product (optional)
        selectedProduct?.let { productName ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Text(
                    text = "Selected: $productName",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF333333),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )
            }
        }
    }
}

// Usage examples:
/*
val myProducts = listOf(
    ProductListScroll("U.S. Polo Assn.", "Preppy Outfits\nMin 30% off\nExtra 10% off*", R.drawable.us_polo_preppy_outfits),
    ProductListScroll("New Balance | Asics", "Athlete's Faves\nMin 30% off", R.drawable.newbalance_asics_athletes_faves),
    ProductListScroll("Rare Rabbit", "Men's Refined Staples\nMin 50% off\nExtra 10-15% off*", R.drawable.rare_rabbit_mens_refined),
    ProductListScroll("Libas", "Festive Ethnics\nMin 40-70% off\nExtra 10% off*", R.drawable.libas_festive_ethnics),
    ProductListScroll("Truffle Collection | Carlton London", "Stylish Pairs\nUp to 60% off", R.drawable.truffle_carlton_stylish_pairs),
    ProductListScroll("Alo | AllSaints", "Hot Global Styles\nUp to 70% off\nExtra 10% off", R.drawable.alo_allsaints_hot_global),
)

// Usage with different background colors:
ProductListScroll(
    products = myProducts,
    sectionTitle = "Featured Products",
    showName = true,
    showPrice = true,
    backgroundColor = Color(0xFFF8F8F8) // Light gray background
)

// Or with other colors:
ProductListScroll(
    products = myProducts,
    sectionTitle = "Featured Products",
    backgroundColor = Color(0xFFFFF8E1) // Light amber background
)

// Or use default white background:
ProductListScroll(
    products = myProducts,
    sectionTitle = "Featured Products"
)
*/