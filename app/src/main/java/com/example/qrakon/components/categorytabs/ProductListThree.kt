@file:OptIn(ExperimentalMaterial3Api::class)

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.qrakon.R
import com.example.qrakon.ui.theme.customColors

// ✅ Reusable Product Model
data class Product(
    val name: String,
    val price: String,
    val imageRes: Int
)

// ✅ Reusable ProductCard with optional fields
@Composable
fun ProductCard(
    product: Product,
    isSelected: Boolean,
    onProductClick: () -> Unit,
    modifier: Modifier = Modifier,
    showName: Boolean = true,
    showPrice: Boolean = true
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
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Image section
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 2.dp)
                    .height(120.dp)
                    .clip(RoundedCornerShape(12.dp)),
//                    .background(Color(0xFFF8F8F8)),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = product.imageRes),
                    contentDescription = product.name,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.FillBounds
                )
            }

            // Optional Name & Price
            if (showName || showPrice) {
//                Spacer(modifier = Modifier.height(4.dp))
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 4.dp)
                        .weight(1f),
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
                            modifier = Modifier.padding(bottom = if (showPrice) 4.dp else 0.dp)
                        )
                    }
                    if (showPrice) {
                        Text(
                            text = product.price,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFFE91E63),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}

// ✅ Reusable Product List with optional section title
@Composable
fun ProductList(
    products: List<Product>,
    modifier: Modifier = Modifier,
    sectionTitle: String? = null,
    showName: Boolean = true,
    showPrice: Boolean = true
) {
    var selectedProduct by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = modifier
            .fillMaxSize()
//            .background(Color(0xFFF5F5F5))
            .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Optional Section Title
        sectionTitle?.let {
            Text(
                text = it,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.customColors.black,
                modifier = Modifier.padding(bottom = 20.dp)
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            products.forEach { product ->
                ProductCard(
                    product = product,
                    isSelected = selectedProduct == product.name,
                    onProductClick = { selectedProduct = product.name },
                    modifier = Modifier
                        .weight(1f)
                        .height(200.dp),
                    showName = showName,
                    showPrice = showPrice
                )
            }
        }

        // Show selected product (optional)
        selectedProduct?.let { productName ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp),
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

//@Preview(showBackground = true)
//@Composable
//fun ProductListPreview() {
//    val sampleProducts = listOf(
//        Product("", "From ₹9,499*", R.drawable.oppo_k13x),
//        Product("", "From ₹12,249*", R.drawable.vivo_t4x),
//        Product("", "Just ₹14,999*", R.drawable.moto_g96)
//    )
//    MaterialTheme {
//        ProductList(
//            products = sampleProducts,
//            sectionTitle = "Featured Products",
//            showName = true,
//            showPrice = true
//        )
//    }
//}
