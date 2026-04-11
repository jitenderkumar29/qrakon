package com.example.qrakon.components.location

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.qrakon.ui.theme.customColors
import com.example.qrakon.R

// Address Options Popup Component - Opens at bottom with icons, arrows and close button

// Address Options Popup Component - Opens at bottom with icons, arrows and close button
@Composable
fun AddressOptionsPopUp(
    isVisible: Boolean,
    onDismiss: () -> Unit,
    onEditAddress: () -> Unit = {},
    onDeleteAddress: () -> Unit = {}
) {
    if (!isVisible) return

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
            decorFitsSystemWindows = false
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f))
                .clickable(onClick = onDismiss),
            contentAlignment = Alignment.BottomCenter
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                // Close button positioned above the card (top-center)
                IconButton(
                    onClick = onDismiss,
                    modifier = Modifier
                        .size(40.dp)
                        .background(
                            color = Color.Black.copy(0.6f),
                            shape = RoundedCornerShape(50.dp)
                        )
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Bottom popup with rounded top corners
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
//                        .padding(start = 20.dp, end = 20.dp, top = 0.dp, bottom = 20.dp)
                        .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.customColors.background2
                    ),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 8.dp
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.customColors.background2)
                    ) {
                        // Title Section - "Address options"
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp, vertical = 16.dp)
                        ) {
                            Text(
                                text = "Address options",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color.Black,
                                letterSpacing = 0.15.sp
                            )
                        }

                        // Edit Address Option with icon and arrow
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight()
                                .padding(start = 20.dp, end = 20.dp, top = 0.dp, bottom = 35.dp)
                                .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp, bottomStart = 20.dp, bottomEnd = 20.dp)),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.customColors.white
                            ),
                            elevation = CardDefaults.cardElevation(
                                defaultElevation = 8.dp
                            )
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        onEditAddress()
                                        onDismiss()
                                    }
                                    .padding(horizontal = 20.dp, vertical = 16.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Edit,
                                        contentDescription = "Edit",
                                        tint = MaterialTheme.customColors.black,
                                        modifier = Modifier.size(24.dp)
                                    )
                                    Spacer(modifier = Modifier.width(16.dp))
                                    Text(
                                        text = "Edit Address",
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Normal,
                                        color = MaterialTheme.customColors.black,
                                        letterSpacing = 0.5.sp
                                    )
                                }

                                // Arrow icon
                                Icon(
                                    painter = painterResource(id = R.drawable.outline_arrow_forward_ios_24),
                                    contentDescription = "Arrow",
                                    tint = MaterialTheme.customColors.black,
                                    modifier = Modifier.size(18.dp)
                                )
                            }

                            // Divider
                            Divider(
                                color = Color(0xFFEEEEEE),
                                thickness = 1.dp,
                                modifier = Modifier.padding(horizontal = 20.dp)
                            )

                            // Delete Address Option with icon and arrow
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        onDeleteAddress()
                                        onDismiss()
                                    }
                                    .padding(horizontal = 20.dp, vertical = 16.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = "Delete",
                                        tint = Color(0xFFE53935),
                                        modifier = Modifier.size(24.dp)
                                    )
                                    Spacer(modifier = Modifier.width(16.dp))
                                    Text(
                                        text = "Delete Address",
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Normal,
                                        color = Color(0xFFE53935),
                                        letterSpacing = 0.5.sp
                                    )
                                }

                                // Arrow icon
                                Icon(
                                    painter = painterResource(id = R.drawable.outline_arrow_forward_ios_24),
                                    contentDescription = "Arrow",
                                    tint = Color(0xFFE53935),
                                    modifier = Modifier.size(18.dp)
                                )
                            }

//                            Spacer(modifier = Modifier.height(16.dp))
                        }
                    }
                }
            }
        }
    }
}