package com.example.qrakon.components.fashion.fashiontab

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.qrakon.R

// 🔹 Data model with optional background color
data class ProductListGrid(
    val name: String,
    val price: String,
    val imageRes: Int,
    val backgroundColor: Color? = null // Optional background color
)

// 🔹 Safe & reusable CategoryListGrid
@Composable
fun CategoryListGrid(
    products: List<ProductListGrid>,
    columns: Int = 3,
    modifier: Modifier = Modifier,
    gridHeight: Dp? = null, // optional height for nested usage
    showName: Boolean = true,
    showPrice: Boolean = true,
    defaultCardColor: Color = MaterialTheme.colorScheme.surface, // Default card color
    onItemClick: (ProductListGrid) -> Unit = {}
) {
    val safeModifier = if (gridHeight != null) {
        modifier
            .fillMaxWidth()
            .height(gridHeight)
    } else {
        modifier
            .fillMaxWidth()
            .heightIn(min = 200.dp, max = 1800.dp)
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(columns),
        modifier = safeModifier.padding(0.dp).background(defaultCardColor),
        contentPadding = PaddingValues(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(products) { product ->
            CategoryGridItem(
                product = product,
                showName = showName,
                showPrice = showPrice,
                defaultCardColor = defaultCardColor,
                onClick = { onItemClick(product) }
            )
        }
    }
}

// 🔹 Single item card with optional name, price, and dynamic background
@Composable
fun CategoryGridItem(
    product: ProductListGrid,
    showName: Boolean = true,
    showPrice: Boolean = true,
    defaultCardColor: Color = MaterialTheme.colorScheme.surface,
    onClick: () -> Unit
) {
    // Use product's background color if provided, otherwise use default
    val cardColor = product.backgroundColor ?: defaultCardColor

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(0.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = cardColor)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier.padding(0.dp)
        ) {
            Image(
                painter = painterResource(id = product.imageRes),
                contentDescription = product.name,
                modifier = Modifier
                    .height(190.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(0.dp)),
                contentScale = ContentScale.FillBounds
//                contentScale = ContentScale.Crop
            )

            if (showName || showPrice) {
                Spacer(modifier = Modifier.height(6.dp))
            }

            if (showName && product.name.isNotEmpty()) {
                Text(
                    text = product.name,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            if (showPrice && product.price.isNotEmpty()) {
                Text(
                    text = product.price,
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            if (showName || showPrice) {
                Spacer(modifier = Modifier.height(6.dp))
            }
//            Spacer(modifier = Modifier.height(6.dp))
        }
    }
}

// Usage examples:
@Composable
fun ExampleUsage() {
    val sampleProducts = listOf(
        ProductListGrid(
            name = "Product 1",
            price = "$29.99",
            imageRes = R.drawable.iki_chic,
            backgroundColor = Color(0xFFFFF8E1) // Light amber
        ),
        ProductListGrid(
            name = "Product 2",
            price = "$39.99",
            imageRes = R.drawable.product2,
            backgroundColor = Color(0xFFE8F5E8) // Light green
        ),
        ProductListGrid(
            name = "Product 3",
            price = "$49.99",
            imageRes = R.drawable.product3,
            backgroundColor = Color(0xFFE3F2FD) // Light blue
        ),
        ProductListGrid(
            name = "Product 4",
            price = "$19.99",
            imageRes = R.drawable.product4
            // No background color - will use default
        )
    )

    // Example 1: With dynamic background colors from products
    CategoryListGrid(
        products = sampleProducts,
        columns = 3,
        gridHeight = 945.dp,
        showName = false,
        showPrice = true,
        onItemClick = { product ->
            println("Clicked on ${product.name}")
        }
    )

    // Example 2: With custom default background color for all cards
    CategoryListGrid(
        products = sampleProducts,
        columns = 3,
        gridHeight = 945.dp,
        showName = true,
        showPrice = true,
        defaultCardColor = Color(0xFFF5F5F5), // Light gray default
        onItemClick = { product ->
            println("Clicked on ${product.name}")
        }
    )

    // Example 3: Using MaterialTheme colors as default
    CategoryListGrid(
        products = sampleProducts,
        columns = 3,
        defaultCardColor = MaterialTheme.colorScheme.background,
        onItemClick = { product ->
            println("Clicked on ${product.name}")
        }
    )
}