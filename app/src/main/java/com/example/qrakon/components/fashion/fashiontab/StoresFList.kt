package com.example.qrakon.components.fashion.fashiontab

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.qrakon.R

@Composable
fun StoresFList(
    title: String = "Shop by Category",
    stores: List<StoreItem> = emptyList(),
    onStoreClick: (String) -> Unit = {},
    showDivider: Boolean = true
) {
    val context = LocalContext.current

    val validStores = remember(stores) {
        stores.filter { store ->
            isResourceAvailable(context, store.imageRes)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Text(
            text = title,
            style = TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black
            ),
            modifier = Modifier.padding(bottom = 20.dp)
        )

        validStores.forEachIndexed { index, store ->
            Column(modifier = Modifier.fillMaxWidth()) {
                StoreItem(
                    imageRes = store.imageRes,
                    title = store.name,
                    description = store.description,
                    onClick = { onStoreClick(store.name) }
                )

                if (showDivider) {
                    // Add border line after each item
                    Spacer(modifier = Modifier.height(8.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(Color.LightGray.copy(alpha = 0.6f))
                    )
                }
            }

            // Add spacing between items except for the last one
            if (index != validStores.lastIndex) {
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun StoreItem(
    imageRes: Int,
    title: String,
    description: String,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(90.dp)
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = title,
                modifier = Modifier
                    .height(80.dp)
                    .width(75.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .background(Color.LightGray),
                contentScale = ContentScale.FillBounds
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = 8.dp)
            ) {
                Text(
                    text = title,
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Black
                    )
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = description,
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color.Gray
                    ),
                    maxLines = 2,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            Icon(
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = "Go to $title",
                tint = Color.Gray,
                modifier = Modifier.size(35.dp)
            )
        }

//        Icon(
//            imageVector = Icons.Default.KeyboardArrowRight,
//            contentDescription = "Go to $title",
//            tint = Color.Gray,
//            modifier = Modifier.size(35.dp)
//        )
    }
}

data class StoreItem(
    val name: String,
    val imageRes: Int,
    val description: String
)

private fun isResourceAvailable(context: Context, resId: Int): Boolean {
    return try {
        context.resources.getResourceName(resId)
        true
    } catch (e: Exception) {
        false
    }
}

// Usage example for Women's stores
@Composable
fun WomenCategoryStores() {
    val womenStores = listOf(
        StoreItem(
            name = "Global Store",
            imageRes = R.drawable.ic_global_store,
            description = "Influencer approved global brands handpicked for you"
        ),
        StoreItem(
            name = "Hidden Gems",
            imageRes = R.drawable.ic_hidden_gems,
            description = "Our selection of 300+ most sought after homegrown labels"
        ),
        StoreItem(
            name = "Luxe Edit",
            imageRes = R.drawable.ic_luxe_edit,
            description = "Our collection of 250+ designers rooted in traditional sophistication"
        ),
        StoreItem(
            name = "Genz",
            imageRes = R.drawable.ic_genz,
            description = "30,000+ curated Gen-Z approved styles to slay in"
        ),
        StoreItem(
            name = "House of Nykaa",
            imageRes = R.drawable.ic_house_of_nykaa,
            description = "Unique brands and curated styles; Made for you, by us"
        ),
        StoreItem(
            name = "Revolve",
            imageRes = R.drawable.ic_revolve,
            description = "Hottest celebrity styles from LA exclusively on Nykaa Fashion"
        )
    )

    StoresFList(
        title = "Shop by Category",
        stores = womenStores,
        onStoreClick = { storeName ->
            // Handle store click
            when (storeName) {
                "Global Store" -> { /* Navigate to Global Store */ }
                "Hidden Gems" -> { /* Navigate to Hidden Gems */ }
                "Luxe Edit" -> { /* Navigate to Luxe Edit */ }
                "Genz" -> { /* Navigate to Genz */ }
                "House of Nykaa" -> { /* Navigate to House of Nykaa */ }
                "Revolve" -> { /* Navigate to Revolve */ }
            }
        }
    )
}

//// Usage example for Men's stores
//@Composable
//fun MenCategoryStores() {
//    val menStores = listOf(
//        StoreItem(
//            name = "Premium Brands",
//            imageRes = R.drawable.ic_premium_brands,
//            description = "Top international and domestic brands for men"
//        ),
//        StoreItem(
//            name = "Street Style",
//            imageRes = R.drawable.ic_street_style,
//            description = "Urban and contemporary streetwear collections"
//        ),
//        StoreItem(
//            name = "Athleisure",
//            imageRes = R.drawable.ic_athleisure,
//            description = "Sporty and comfortable wear for active lifestyle"
//        ),
//        StoreItem(
//            name = "Formal Wear",
//            imageRes = R.drawable.ic_formal_wear,
//            description = "Sophisticated office and formal attire"
//        )
//    )
//
//    StoresFList(
//        title = "Men's Stores",
//        stores = menStores,
//        onStoreClick = { storeName ->
//            // Handle men's store click
//        }
//    )
//}

//// Usage example without dividers
//@Composable
//fun SimpleStoresList() {
//    val simpleStores = listOf(
//        StoreItem(
//            name = "Store 1",
//            imageRes = R.drawable.ic_store1,
//            description = "Description for store 1"
//        ),
//        StoreItem(
//            name = "Store 2",
//            imageRes = R.drawable.ic_store2,
//            description = "Description for store 2"
//        )
//    )
//
//    StoresFList(
//        title = "Featured Stores",
//        stores = simpleStores,
//        showDivider = false,
//        onStoreClick = { storeName ->
//            // Handle store click
//        }
//    )
//}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun StoresFListPreview() {
    val previewStores = listOf(
        StoreItem(
            name = "Global Store",
            imageRes = R.drawable.ic_global_store,
            description = "Influencer approved global brands handpicked for you"
        ),
        StoreItem(
            name = "Hidden Gems",
            imageRes = R.drawable.ic_hidden_gems,
            description = "Our selection of 300+ most sought after homegrown labels"
        ),
        StoreItem(
            name = "Luxe Edit",
            imageRes = R.drawable.ic_luxe_edit,
            description = "Our collection of 250+ designers rooted in traditional sophistication"
        )
    )

    StoresFList(
        title = "Shop by Category",
        stores = previewStores,
        onStoreClick = { }
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun StoresFListNoDividersPreview() {
    val previewStores = listOf(
        StoreItem(
            name = "Store 1",
            imageRes = R.drawable.ic_global_store,
            description = "Description for store 1"
        ),
        StoreItem(
            name = "Store 2",
            imageRes = R.drawable.ic_hidden_gems,
            description = "Description for store 2"
        )
    )

    StoresFList(
        title = "Featured Stores",
        stores = previewStores,
        showDivider = false,
        onStoreClick = { }
    )
}