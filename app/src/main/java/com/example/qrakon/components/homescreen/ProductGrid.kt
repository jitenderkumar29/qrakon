package com.example.qrakon.components.homescreen

import android.annotation.SuppressLint
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.qrakon.ui.theme.customColors
import com.example.qrakon.R

data class GridItem(
    val id: String,
    val title: String,
    val description: String? = null,
    val icon: ImageVector? = null,
    val image: Painter? = null,
    val priceRange: String? = null,
    val category: String? = null,
    val backgroundColor: Color = Color.White,
    val textColor: Color = Color.Black,
    val onClick: () -> Unit = {}
)

@Composable
fun ProductGrid(
    modifier: Modifier = Modifier,
    items: List<GridItem>,
    onItemClick: (GridItem) -> Unit = {}
) {
    // Create multiple 2x2 grids for horizontal scrolling
    val gridChunks = items.chunked(4) // Each chunk represents one 2x2 grid

    Row(
        modifier = modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState())
            .padding(horizontal = 8.dp, vertical = 6.dp) // REMOVED vertical padding
            .background(MaterialTheme.customColors.background),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        gridChunks.forEach { chunk ->
            // Each chunk is a 2x2 grid
            Column(
                modifier = Modifier
                    .width(185.dp) // Fixed width for each 2x2 grid
                    .shadow(
                        elevation = 20.dp, // ⬅ Increase elevation for a stronger shadow
                        shape = RoundedCornerShape(8.dp),
                        ambientColor = Color.Black.copy(alpha = 0.5f), // Darker & more visible
                        spotColor = Color.Black.copy(alpha = 0.5f) // Balanced shadow all around
                    )
                    .border(
                        width = 2.dp,
                        color = MaterialTheme.customColors.lightGray,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(1.dp)
                    .background(color = MaterialTheme.customColors.white),
                verticalArrangement = Arrangement.spacedBy(1.dp)
            ) {
                // First row of 2x2 grid

                Row(
                    horizontalArrangement = Arrangement.spacedBy(2.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    chunk.take(2).forEach { item ->
                        GridItemCard(
                            item = item,
                            modifier = Modifier.weight(1f),
                            onClick = { onItemClick(item) }
                        )
                    }
                }

                // Second row of 2x2 grid (if items exist)
                if (chunk.size > 2) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(2.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        chunk.drop(2).take(2).forEach { item ->
                            GridItemCard(
                                item = item,
                                modifier = Modifier.weight(1f),
                                onClick = { onItemClick(item) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun QrakonPayScreen() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 0.dp) // REMOVED vertical padding
    ) {
        // Items arranged to match the image layout (2x2 grids)
        val gridItems = listOf(
            // First 2x2 grid (matches the image)
            GridItem(
                id = "pay",
                title = "Travel",
//                title = "Qrakon Pay",
                description = "Qrakon Pay",
                image = painterResource(id = R.drawable.ic_travel),
                onClick = { println("Qrakon Pay clicked") }
            ),
            GridItem(
                id = "send_money",
                title = "Send Money",
                description = "Send Money",
                image = painterResource(id = R.drawable.send_money),
                backgroundColor = Color(0xFFFFD700),
                textColor = Color.Black,
                onClick = { println("Scan Any QR clicked") }
            ),
            GridItem(
                id = "scan_any_qr",
                title = "Scan Any QR",
                image = painterResource(id = R.drawable.scan_any_qr),
                onClick = { println("Scan Any QR clicked") }
            ),
            GridItem(
                id = "recharge",
                title = "Recharge & Bills",
                image = painterResource(id = R.drawable.recharge_bills),
                onClick = { println("Recharge clicked") }
            ),

            // Second 2x2 grid (additional items)
            GridItem(
                id = "deals",
                title = "See all deals",
                description = "See all deals",
                image = painterResource(id = R.drawable.see_all_deals),
                onClick = { println("Deals clicked") }
            ),
            GridItem(
                id = "women",
                title = "Women",
                description = "Women",
                image = painterResource(id = R.drawable.women_grid),
                onClick = { println("Women clicked") }
            ),
            GridItem(
                id = "men",
                title = "From ₹149",
                description = "Men",
                image = painterResource(id = R.drawable.men_grid),
                onClick = { println("Men clicked") }
            ),
            GridItem(
                id = "kitchen",
                title = "Kitchen",
                description = "Kitchen",
                image = painterResource(id = R.drawable.kitchen_grid),
                backgroundColor = Color(0xFF00A8E8),
                textColor = Color.Black,
                onClick = { println("Kitchen clicked") }
            ),

            // Third 2x2 grid (repeats for scrolling)
            GridItem(
                id = "credit_card",
                title = "Credit Card",
                description = "credit_card",
                image = painterResource(id = R.drawable.credit_card),
                onClick = { println("Credit Card clicked") }
            ),
            GridItem(
                id = "metro",
                title = "Metro",
                description = "Metro",
                image = painterResource(id = R.drawable.metro_grid),
                backgroundColor = Color(0xFFFFD700),
                textColor = Color.Black,
                onClick = { println("Metro clicked") }
            ),
            GridItem(
                id = "gift_card",
                title = "Gift Card",
                image = painterResource(id = R.drawable.gift_card),
                onClick = { println("Gift Card clicked") }
            ),
            GridItem(
                id = "hospital",
                title = "Hospital",
                image = painterResource(id = R.drawable.hospital_grid),
                onClick = { println("Hospital clicked") }
            )
        )

        ProductGrid(
            items = gridItems,
            onItemClick = { item ->
                when (item.id) {
                    "pay", "pay2" -> { println("Navigate to Pay screen") }
                    "cashback", "cashback2" -> { println("Open QR Scanner") }
                    "send_money", "send_money2" -> { println("Open Send Money") }
                    "recharge", "recharge2" -> { println("Open Recharge & Bills") }
                    "deals" -> { println("Show all deals") }
                    "men" -> { println("Show Men's products") }
                    "women" -> { println("Show Women's products") }
                    "for_alex" -> { println("Show Alexa deals") }
                }
            }
        )

//        Text(
//            text = "More features coming soon!",
//            style = MaterialTheme.typography.bodyMedium,
//            color = Color.Gray,
//            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
//        )
    }
}

@Composable
fun GridItemCard(
    item: GridItem,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    // Get the animation progress from a composable function
    val animationProgress by rememberInfiniteTransition().animateFloat(
        initialValue = 0f,
        targetValue = 2f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    Column(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .clickable(onClick = onClick)
            .background(MaterialTheme.customColors.white)
//            .background(item.backgroundColor)
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Icon/Image section with shimmer animation
        Box(
            modifier = Modifier
                .size(75.dp) // Smaller size for 2x2 grid
//                .shimmerEffect(animationProgress) // Pass animation progress
//                .clip(RoundedCornerShape(8.dp))
//                .background(
//                    if (item.backgroundColor == Color.White)
//                        Color.LightGray.copy(alpha = 0.2f)
//                    else item.backgroundColor.copy(alpha = 0.8f)
//                ),
            ,
            contentAlignment = Alignment.Center
        ) {
            item.icon?.let {
                Icon(
                    imageVector = it,
                    contentDescription = item.title,
                    modifier = Modifier.size(80.dp),
                    tint = item.textColor
                )
            }
            item.image?.let { painter ->
                Image(
                    painter = painter,
                    contentDescription = item.title,
                    modifier = Modifier
                        .size(75.dp),
//                        .padding(2.dp),
                    contentScale = ContentScale.FillBounds,
                )
            }
        }

        // Title
        Text(
            text = item.title,
            color = item.textColor,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            maxLines = 1,
            modifier = Modifier.fillMaxWidth()
        )

        // Price range or category
        item.priceRange?.let { price ->
            Text(
                text = price,
                color = Color.Gray,
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                maxLines = 1,
                modifier = Modifier.fillMaxWidth()
            )
        }

        item.category?.let { category ->
            Text(
                text = category,
                color = Color.Gray,
                fontSize = 9.sp,
                textAlign = TextAlign.Center,
                maxLines = 1,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

// Shimmer effect modifier function (non-composable)
@SuppressLint("SuspiciousModifierThen")
fun Modifier.shimmerEffect(animationProgress: Float): Modifier = this.then(
    drawWithContent {
        /* First draw the actual content (image/icon) */
        drawContent()

        // Then draw the shimmer effect on top
        val shimmerColor = Color.White.copy(alpha = 0.5f)
        val x = size.width * (animationProgress * 2 - 1) // Moves from -100% to 100%

        drawRect(
            brush = Brush.linearGradient(
                colors = listOf(
                    Color.Transparent,
                    shimmerColor,
                    Color.Transparent
                ),
                start = Offset(x, 0f),
                end = Offset(x + size.width / 2, size.height)
            )
            // Removed BlendMode.SrcIn to allow content to show through
        )
    }
)

@Preview(showBackground = true)
@Composable
fun QrakonPayScreenPreview() {
    MaterialTheme {
        Surface {
            QrakonPayScreen()
        }
    }
}