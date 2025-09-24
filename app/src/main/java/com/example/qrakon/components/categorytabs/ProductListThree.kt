package com.example.qrakon.components.categorytabs

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.qrakon.R

data class Product(
    val id: Int,
    val name: String,
    val model: String,
    val price: String,
    val originalPrice: String? = null,
    val discount: String? = null,
    val isSpecialOffer: Boolean = false,
    val imageRes: Int? = null // Add image resource ID
)

@Composable
fun ProductListThree(
    products: List<Product>,
    onProductClick: (Product) -> Unit = {},
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(products.size) { index ->
            ProductCard(
                product = products[index],
                onClick = { onProductClick(products[index]) }
            )
        }
    }
}

@Composable
fun ProductCard(
    product: Product,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Product Image
            if (product.imageRes != null) {
                Image(
                    painter = painterResource(id = product.imageRes),
                    contentDescription = "${product.name} ${product.model}",
                    modifier = Modifier
                        .size(80.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.LightGray.copy(alpha = 0.2f)),
                    contentScale = ContentScale.Fit
                )
                Spacer(modifier = Modifier.width(16.dp))
            } else {
                // Placeholder for when no image is available
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.LightGray.copy(alpha = 0.2f)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No Image",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
            }

            // Product Details
            Column(
                modifier = Modifier.weight(1f)
            ) {
                // Product Name and Model
                Text(
                    text = product.name,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    ),
                    color = Color.Black
                )

                Text(
                    text = product.model,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontSize = 14.sp
                    ),
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Price Section
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column {
                        if (product.isSpecialOffer) {
                            // Special offer styling for moto g96
                            Text(
                                text = product.price,
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp,
                                    color = Color(0xFFE53935) // Red color for special offer
                                )
                            )
                        } else {
                            // Regular pricing
                            Text(
                                text = product.price,
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp,
                                    color = Color(0xFF1E88E5) // Blue color for regular price
                                )
                            )
                        }

                        product.originalPrice?.let { originalPrice ->
                            Text(
                                text = originalPrice,
                                style = MaterialTheme.typography.bodySmall.copy(
                                    fontSize = 12.sp,
                                    textDecoration = TextDecoration.LineThrough,
                                    color = Color.Gray
                                )
                            )
                        }
                    }

                    // Discount badge or Special offer indicator
                    if (product.discount != null) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(4.dp))
                                .background(Color(0xFFFF7043))
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = product.discount,
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 10.sp,
                                    color = Color.White
                                )
                            )
                        }
                    } else if (product.isSpecialOffer) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(4.dp))
                                .background(Color(0xFF4CAF50))
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = "Just",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 10.sp,
                                    color = Color.White
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}

// Preview function
@Preview(showBackground = true)
@Composable
fun ProductListThreePreview() {
    // Note: In preview, we use null for imageRes since we don't have actual resources
    val sampleProducts = listOf(
        Product(
            id = 1,
            name = "OPPO",
            model = "K13x 5G",
            price = "From ₹9,499*",
            originalPrice = "₹12,999",
            discount = "27% off",
            imageRes = null // Replace with actual resource ID in your app
        ),
        Product(
            id = 2,
            name = "vivo",
            model = "T4x 5G",
            price = "From ₹12,249*",
            originalPrice = "₹15,999",
            discount = "23% off",
            imageRes = null // Replace with actual resource ID in your app
        ),
        Product(
            id = 3,
            name = "moto",
            model = "g96 (8GB)",
            price = "₹14,999*",
            isSpecialOffer = true,
            imageRes = null // Replace with actual resource ID in your app
        )
    )

    MaterialTheme {
        ProductListThree(products = sampleProducts)
    }
}

// Usage example in your app with actual images
@Composable
fun ProductScreen() {
    val products = listOf(
        Product(
            id = 1,
            name = "OPPO",
            model = "K13x 5G",
            price = "From ₹9,499*",
            originalPrice = "₹12,999",
            discount = "27% off",
            imageRes = R.drawable.oppo_k13x // Replace with your actual drawable resource
        ),
        Product(
            id = 2,
            name = "vivo",
            model = "T4x 5G",
            price = "From ₹12,249*",
            originalPrice = "₹15,999",
            discount = "23% off",
            imageRes = R.drawable.vivo_t4x // Replace with your actual drawable resource
        ),
        Product(
            id = 3,
            name = "moto",
            model = "g96 (8GB)",
            price = "₹14,999*",
            isSpecialOffer = true,
            imageRes = R.drawable.moto_g96 // Replace with your actual drawable resource
        )
    )

    ProductListThree(
        products = products,
        onProductClick = { product ->
            // Handle product click - navigate to detail screen or show dialog
            println("Clicked on: ${product.name} ${product.model}")
        }
    )
}