package com.example.qrakon.components.fashion

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.qrakon.R
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import com.example.qrakon.components.fashion.navigationFashion.TabNavigationFashion
import com.example.qrakon.ui.theme.customColors

@Composable
fun FashionScreen(
    onBack: () -> Unit,
    navController: NavHostController
) {
    var searchQuery by remember { mutableStateOf("") }
    val onCategoryClick: () -> Unit = {
        navController.navigate("fashion_categories")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.customColors.lightAccent)
    ) {
        // Back Button Row
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 12.dp, end = 12.dp, top = 2.dp, bottom = 0.dp)
                .background(MaterialTheme.customColors.lightAccent)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_back),
                contentDescription = "Back",
                modifier = Modifier
                    .size(28.dp)
                    .background(MaterialTheme.customColors.lightAccent)
                    .clickable { onBack() }
            )

            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Fashion",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.customColors.white
            )

            // Spacer to push the icons to the right
            Spacer(modifier = Modifier.weight(1f))

            // Category Icon
            Box(
                modifier = Modifier
                    .size(35.dp)
                    .shadow(
                        elevation = 2.dp,
                        shape = CircleShape,
                        clip = true
                    )
                    .clip(CircleShape)
                    .background(MaterialTheme.customColors.lightAccent)
//                    .background(Color.White)
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.2f),
                        shape = CircleShape
                    )
              .clickable { onCategoryClick() },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_category_image),
//                    painter = painterResource(id = R.drawable.ic_category_1),
                    contentDescription = "Categories",
                    tint = Color.Unspecified,
//                    tint = MaterialTheme.colorScheme.scrim,
                    modifier = Modifier.size(30.dp)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            // Wishlist Icon
            Box(
                modifier = Modifier
                    .size(35.dp)
                    .shadow(
                        elevation = 2.dp,
                        shape = CircleShape,
                        clip = true
                    )
                    .clip(CircleShape)
                    .background(MaterialTheme.customColors.black)
//                    .background(Color.Black)
                    .border(
                        width = 2.dp,
                        color = MaterialTheme.customColors.white,
//                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.2f),
                        shape = CircleShape
                    ),
//              .clickable { onQRCodeScan() },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_wishlist_outline),
                    contentDescription = "Wishlist",
                    tint = MaterialTheme.customColors.white,
//                    tint = MaterialTheme.colorScheme.scrim,
                    modifier = Modifier.size(22.dp)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            // Amount Field with overlapping coin image
            Box(
                contentAlignment = Alignment.CenterEnd
            ) {
                // Circular amount field
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .border(
                            width = 2.dp,
//                            width = 5.dp,
                            color = MaterialTheme.customColors.white,
//                            color = MaterialTheme.customColors.spacerColor,
                            shape = CircleShape
                        )
                        .background(MaterialTheme.customColors.black, CircleShape)
//                        .background(MaterialTheme.customColors.white, CircleShape)
                        .padding(horizontal = 12.dp, vertical = 2.dp)
                ) {
                    Row(verticalAlignment = Alignment.Bottom) {
                        Text(
                            text = "₹",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.customColors.white
//                            color = MaterialTheme.customColors.black
                        )
                        Spacer(modifier = Modifier.width(0.dp))
                        Text(
                            text = "0",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.customColors.white
//                            color = MaterialTheme.customColors.black
                        )
                    }
                    // Add extra space for the overlapping image
                    Spacer(modifier = Modifier.width(12.dp))
                }

                // Coin image placed outside the bordered container but overlapping it
                Image(
                    painter = painterResource(id = R.drawable.ic_coin),
                    contentDescription = "Amount",
                    modifier = Modifier
                        .size(30.dp)
                        .offset(x = 5.dp)
                        .zIndex(1f)
                )
            }
        }
        TabNavigationFashion(navController = navController)
    }
}