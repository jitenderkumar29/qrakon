package com.example.qrakon.components.location

import androidx.compose.foundation.background
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationAddress(
    onBackClick: () -> Unit = {},
    onLocationSelected: (String) -> Unit = {},
    onUseCurrentLocation: () -> Unit = {},
    onAddAddress: () -> Unit = {},
    onImportBlinkit: () -> Unit = {}
) {

    // ✅ Your Address List
    val addressList = listOf(
        "Dhruv 110044, Chennai 600001",
        "201002 Main Street, Noida",
        "201001 Office Road, Ghaziabad"
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
                    containerColor = MaterialTheme.customColors.background2   // ✅ HEADER BG
                )
            )
        }
    ) { padding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp, vertical = 0.dp),
//                .background(MaterialTheme.customColors.background2)
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            // 🔍 Search Bar
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
                            color = Color(0xFF666666),  // Dark enough to be clearly visible
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

            // 📦 Action Box
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
                            onClick = onUseCurrentLocation
                        )

                        Divider()

                        ActionItem(
                            icon = Icons.Default.Add,
                            title = "Add Address",
                            onClick = onAddAddress
                        )

                        Divider()

                        ActionItem(
                            icon = Icons.Default.LocationOn,
                            title = "Import addresses from Blinkit",
                            onClick = onImportBlinkit
                        )
                    }
                }
            }

            // 🏠 Saved Addresses
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
                    address = "F106 gali no 1 Dhruv 110044, Chennai 600001",
                    phone = "+91-9267958302"
                )
            }

            // 📍 Dynamic Address List (IMPORTANT)
//            item {
//                Text(
//                    text = "AVAILABLE LOCATIONS",
//                    style = MaterialTheme.typography.labelSmall,
//                    color = Color.Gray,
//                    fontSize = 15.sp
//                )
//            }

//            items(addressList) { address ->
//
//                AddressOptionItem(
//                    address = address,
//                    onClick = {
//                        onLocationSelected(address)
//                    }
//                )
//            }
//
//            // 📍 Nearby
//            item {
//                Text(
//                    text = "NEARBY LOCATIONS",
//                    style = MaterialTheme.typography.labelSmall,
//                    color = Color.Gray,
//                    fontSize = 15.sp
//                )
//            }
//
//            item {
//                NearbyItem(
//                    "SK Payal Public School",
//                    "Budh Vihar Road, Badarpur Extension"
//                )
//            }
//
//            item {
//                NearbyItem(
//                    "RED Carmine",
//                    "Prakash Petrol Pump, Badarpur"
//                )
//            }
//
//            item {
//                NearbyItem(
//                    "International Institute of Hotel Management",
//                    "Mathura Road"
//                )
//            }
        }
    }
}


// ✅ Action Row
@Composable
fun ActionItem(
    icon: ImageVector,
    title: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(14.dp)
            .background(MaterialTheme.customColors.white),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween  // Space between content and arrow
    ) {
        // Left side: Icon and Text
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

        // Right side: Arrow icon
        Icon(
            painter = painterResource(id = R.drawable.outline_arrow_forward_ios_24),  // Replace with your image resource
            contentDescription = "Navigate",
            tint = MaterialTheme.customColors.black,
            modifier = Modifier.size(18.dp)
        )
    }
}


// ✅ Address Item (Selectable)
@Composable
fun AddressOptionItem(
    address: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(2.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.customColors.white
        ),
        onClick = onClick   // ✅ clickable card
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.LocationOn,
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
}


// ✅ Nearby Item
@Composable
fun NearbyItem(
    title: String,
    address: String,
    onClick: () -> Unit = {}   // ✅ added click support
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(2.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.customColors.white
        ),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(
                imageVector = Icons.Default.LocationOn,
                contentDescription = null,
                tint = Color.Gray
            )

            Spacer(Modifier.width(10.dp))

            Column {
                Text(
                    text = title,
                    fontWeight = FontWeight.Medium
                )

                Text(
                    text = address,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            }
        }
    }
}


// ✅ Saved Address Card
@Composable
fun SavedAddressCard(
    title: String,
    address: String,
    phone: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),  // 👈 Takes full width
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(2.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.customColors.white
        )
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Default.LocationOn,
                    contentDescription = null,
                    tint = MaterialTheme.customColors.buttonRed  // Optional: match your theme
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    title,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    fontSize = 15.sp
                )
            }

            Spacer(Modifier.height(6.dp))

            Text(
                address,
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray,
                fontSize = 15.sp
            )

            Spacer(Modifier.height(4.dp))

            Text(
                "Phone number: $phone",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )

            Spacer(Modifier.height(10.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                Text(
                    "Edit",
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.clickable { /* Handle edit */ }
                )
                Text(
                    "Delete",
                    color = Color.Red,
                    modifier = Modifier.clickable { /* Handle delete */ }
                )
            }
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