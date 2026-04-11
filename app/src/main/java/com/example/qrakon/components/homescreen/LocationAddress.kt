package com.example.qrakon.components.location

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.qrakon.ui.theme.customColors
import com.example.qrakon.R
import com.example.qrakon.components.homescreen.AddressMap

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationAddress(
    onBackClick: () -> Unit = {},
    onLocationSelected: (String) -> Unit = {},
    onUseCurrentLocation: () -> Unit = {},
    onImportBlinkit: () -> Unit = {},
    navigateToAddAddress: () -> Unit = {}
) {
    var showAddressMap by remember { mutableStateOf(false) }

    // If showAddressMap is true, show AddressMap instead
    if (showAddressMap) {
        AddressMap(
            onBackClick = { showAddressMap = false },
            onSaveAddress = {
                // Save address logic
                showAddressMap = false
            },
            onUseCurrentLocation = onUseCurrentLocation
        )
    } else {
        // Show the main location screen
        val addressList = listOf(
            "Dhruv, Chennai, 110044",
            "Main Street, Noida, 201002",
            "Office Road, Ghaziabad, 201001"
        )

        var searchQuery by remember { mutableStateOf("") }

        Scaffold(
            containerColor = MaterialTheme.customColors.background2,
            topBar = {
                TopAppBar(
                    title = { Text("Select a location") },
                    navigationIcon = {
                        IconButton(onClick = onBackClick) {
                            Icon(Icons.Default.ArrowBack, contentDescription = null)
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.customColors.background2
                    )
                )
            }
        ) { padding ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(horizontal = 16.dp, vertical = 0.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Search Bar
                item {
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        placeholder = {
                            Text(
                                "Search for area, street name...",
                                color = Color(0xFF666666),
                                fontSize = 14.sp
                            )
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Search",
                                tint = MaterialTheme.customColors.buttonRed
                            )
                        },
                        shape = RoundedCornerShape(12.dp),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            disabledContainerColor = Color.White,
                            focusedBorderColor = Color.Transparent,
                            unfocusedBorderColor = Color.Transparent,
                            cursorColor = MaterialTheme.customColors.buttonRed,
                            focusedPlaceholderColor = Color(0xFF666666),
                            unfocusedPlaceholderColor = Color(0xFF666666)
                        )
                    )
                }

                // Action Box
                item {
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        elevation = CardDefaults.cardElevation(2.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.customColors.white
                        ),
                    ) {
                        Column {
                            ActionItem(
                                icon = Icons.Default.LocationOn,
                                title = "Use current location",
                                address = "Dhruv, Chennai-110044",
                                onClick = onUseCurrentLocation
                            )

                            Divider()

                            ActionItem(
                                icon = Icons.Default.Add,
                                title = "Add Address",
                                onClick = { showAddressMap = true }
                            )

                            Divider()

                            ActionItem(
                                icon = Icons.Default.LocationOn,
                                title = "Import addresses from Hufko",
                                onClick = onImportBlinkit
                            )
                        }
                    }
                }

                // Saved Addresses
                item {
                    Text(
                        text = "SAVED ADDRESSES",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.Gray,
                        fontSize = 15.sp
                    )
                }

                item {
                    SavedAddressCard(
                        title = "Home",
                        address = "F106 gali no 1 Dhruv Block-F, Badarpur Extension, New Delhi-110044",
                        phone = "+91-9267958302",
                        onEditClick = { /* Handle edit address */ },
                        onDeleteClick = { /* Handle delete address */ },
                        onShareClick = { /* Handle share address */ }
                    )
                }

                // Dynamic Address List
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        elevation = CardDefaults.cardElevation(2.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.customColors.white
                        )
                    ) {
                        Column {
                            Text(
                                text = "AVAILABLE LOCATIONS",
                                style = MaterialTheme.typography.labelSmall,
                                color = Color.Gray,
                                fontSize = 15.sp,
                                modifier = Modifier.padding(14.dp)
                            )

                            addressList.forEachIndexed { index, address ->
                                AddressOptionItem(
                                    address = address,
                                    onClick = { onLocationSelected(address) }
                                )

                                if (index != addressList.lastIndex) {
                                    Divider(
                                        color = Color.LightGray.copy(alpha = 0.5f),
                                        thickness = 0.5.dp,
                                        modifier = Modifier.padding(horizontal = 14.dp)
                                    )
                                }
                            }
                        }
                    }
                }

                // Nearby Locations
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        elevation = CardDefaults.cardElevation(2.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.customColors.white
                        )
                    ) {
                        Column {
                            NearbyItem(
                                "SK Payal Public School",
                                "Budh Vihar Road, Badarpur Extension"
                            )
                            Divider(
                                color = Color.LightGray.copy(alpha = 0.5f),
                                thickness = 0.5.dp,
                                modifier = Modifier.padding(horizontal = 14.dp)
                            )
                            NearbyItem(
                                "RED Carmine",
                                "Prakash Petrol Pump, Badarpur"
                            )
                            Divider(
                                color = Color.LightGray.copy(alpha = 0.5f),
                                thickness = 0.5.dp,
                                modifier = Modifier.padding(horizontal = 14.dp)
                            )
                            NearbyItem(
                                "International Institute of Hotel Management",
                                "Mathura Road"
                            )
                        }
                    }
                }

                // Powered by Google
                item {
                    PoweredByGoogle(
                        modifier = Modifier.padding(vertical = 16.dp)
                    )
                }
            }
        }
    }
}

// ✅ Action Row
@Composable
fun ActionItem(
    icon: ImageVector,
    title: String,
    address: String? = null,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .background(MaterialTheme.customColors.white)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 14.dp, end = 14.dp, bottom = 0.dp, top = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    icon,
                    contentDescription = null,
                    tint = MaterialTheme.customColors.buttonRed
                )
                Spacer(Modifier.width(12.dp))
                Text(
                    title,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.customColors.buttonRed
                )
            }

            Icon(
                painter = painterResource(id = R.drawable.outline_arrow_forward_ios_24),
                contentDescription = "Navigate",
                tint = MaterialTheme.customColors.black,
                modifier = Modifier.size(17.dp)
            )
        }

        if (!address.isNullOrEmpty()) {
            Text(
                text = address,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray,
                fontSize = 14.sp,
                modifier = Modifier.padding(start = 52.dp, end = 0.dp, bottom = 14.dp, top = 0.dp),
            )
        } else {
            Spacer(modifier = Modifier.height(14.dp))
        }
    }
}



// Updated Saved Address Card
@Composable
fun SavedAddressCard(
    title: String,
    address: String,
    phone: String,
    modifier: Modifier = Modifier,
    onEditClick: () -> Unit = {},
    onDeleteClick: () -> Unit = {},
    onShareClick: () -> Unit = {}
) {
    var showAddressOptionsPopUp by remember { mutableStateOf(false) }

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(2.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.customColors.white
        )
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            // Row with icon on left, text content on right
            Row(
                verticalAlignment = Alignment.Top
            ) {
                // Left side: Icon
                Icon(
                    painter = painterResource(id = R.drawable.outline_home_24),
                    contentDescription = null,
                    tint = MaterialTheme.customColors.gray,
                    modifier = Modifier.size(24.dp)
                )

                Spacer(modifier = Modifier.width(12.dp))

                // Right side: Title and Address
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        title,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        fontSize = 16.sp
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        address,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray,
                        fontSize = 14.sp,
                        lineHeight = 20.sp
                    )
                    // Phone number
                    Text(
                        "Phone number: $phone",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray,
                        fontSize = 14.sp,
                        lineHeight = 20.sp
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    // Right side: More options (three dots) and Share icon

                    Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                        // Three dots menu icon - opens AddressOptionsPopUp
                        Icon(
                            painter = painterResource(id = R.drawable.ic_more_horizontal),
                            contentDescription = "More options",
                            tint = MaterialTheme.customColors.buttonRed,
                            modifier = Modifier
                                .size(30.dp)
                                .border(
                                    width = 1.dp,
                                    color = MaterialTheme.customColors.lightGray,
                                    shape = RoundedCornerShape(20.dp)
                                )
                                .padding(4.dp)
                                .clickable { showAddressOptionsPopUp = true }
                        )
                        // Share icon
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = "Share",
                            tint = MaterialTheme.customColors.buttonRed,
                            modifier = Modifier
                                .size(30.dp)
                                .border(
                                    width = 1.dp,
                                    color = MaterialTheme.customColors.lightGray,
                                    shape = RoundedCornerShape(20.dp)
                                )
                                .padding(4.dp)
                                .clickable {
                                    onShareClick()
                                }
                        )
                    }
                }
            }
        }
    }

    // Address Options Popup - Opens at bottom with icons, arrows and close button
    AddressOptionsPopUp(
        isVisible = showAddressOptionsPopUp,
        onDismiss = { showAddressOptionsPopUp = false },
        onEditAddress = onEditClick,
        onDeleteAddress = onDeleteClick
    )
}

// ✅ Address Item (Selectable)
@Composable
fun AddressOptionItem(
    address: String,
    onClick: () -> Unit,
    isLastItem: Boolean = false
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_clock),
            contentDescription = null,
            tint = Color.Gray
        )

        Spacer(modifier = Modifier.width(10.dp))

        Text(
            text = address,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium
        )
    }
}

// ✅ Nearby Item
@Composable
fun NearbyItem(
    title: String,
    address: String,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_clock),
            contentDescription = null,
            tint = Color.Gray
        )

        Spacer(modifier = Modifier.width(10.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = title,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = address,
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray,
                fontSize = 12.sp
            )
        }
    }
}

@Composable
fun PoweredByGoogle(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Powered by ",
            color = Color.DarkGray,
            fontSize = 15.sp
        )
        Row {
            Text("G", color = Color(0xFF4285F4), fontSize = 15.sp, fontWeight = FontWeight.Bold)
            Text("o", color = Color(0xFFEA4335), fontSize = 15.sp, fontWeight = FontWeight.Bold)
            Text("o", color = Color(0xFFFBBC05), fontSize = 15.sp, fontWeight = FontWeight.Bold)
            Text("g", color = Color(0xFF4285F4), fontSize = 15.sp, fontWeight = FontWeight.Bold)
            Text("l", color = Color(0xFF34A853), fontSize = 15.sp, fontWeight = FontWeight.Bold)
            Text("e", color = Color(0xFFEA4335), fontSize = 15.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LocationAddressPreview() {
    MaterialTheme {
        LocationAddress()
    }
}