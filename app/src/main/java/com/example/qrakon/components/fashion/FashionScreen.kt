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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.example.qrakon.components.fashion.navigationFashion.TabNavigationFashion
import com.example.qrakon.ui.theme.customColors

@Composable
fun FashionScreen(onBack: () -> Unit) {
    var searchQuery by remember { mutableStateOf("") }

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
                .padding(start = 12.dp, end = 12.dp, top = 4.dp, bottom = 4.dp)
                .background(MaterialTheme.customColors.lightAccent)

        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_back), // your back icon
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

            // Spacer to push the amount field to the right
            Spacer(modifier = Modifier.weight(1f))

            // Use Box container to properly layer the image above the border
            Box(
                contentAlignment = Alignment.CenterEnd
            ) {
                // Circular amount field without the image
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .border(
                            width = 5.dp,
                            color = MaterialTheme.customColors.spacerColor,
                            shape = CircleShape
                        )
                        .background(MaterialTheme.customColors.white, CircleShape)
                        .padding(horizontal = 16.dp, vertical = 4.dp)
                ) {
                    Row(verticalAlignment = Alignment.Bottom) {
                        Text(
                            text = "₹",
                            fontSize = 24.sp, // ₹ symbol size
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.customColors.black
                        )
                        Spacer(modifier = Modifier.width(0.dp))
                        Text(
                            text = "0",
                            fontSize = 20.sp, // amount text size
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.customColors.black
                        )
                    }

//                    Text(
//                        text = "₹ 0", // Replace with your actual amount
//                        fontSize = 20.sp,
//                        fontWeight = FontWeight.Medium,
//                        color = MaterialTheme.customColors.black
//                    )
                    // Add extra space for the overlapping image
                    Spacer(modifier = Modifier.width(10.dp))
                }

                // Image placed outside the bordered container but overlapping it
                Image(
                    painter = painterResource(id = R.drawable.ic_coin),
                    contentDescription = "Amount",
                    modifier = Modifier
                        .size(30.dp)
                        .offset(x = 5.dp) // Shift 5.dp towards right
                        .zIndex(1f) // Ensure image displays over the border
                )
            }
        }
        TabNavigationFashion()
    }
}