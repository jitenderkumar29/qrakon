package com.example.qrakon.components.homescreen

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.qrakon.components.searchbar.SearchBar
import com.example.qrakon.ui.theme.customColors

@Composable
fun HomeScreenHeader(
    selectedLocation: String,
    onLocationClick: () -> Unit,
    onSearch: (String) -> Unit,
    onQRCodeScan: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
//            .background(MaterialTheme.colorScheme.background)
//            .padding(horizontal = 12.dp)
    ) {
        // Location Button
        LocationSelectionButton(
            selectedLocation = selectedLocation,
            onLocationClick = onLocationClick,
            modifier = Modifier
                .fillMaxWidth()
        )

        // Search Section with Reduced Gap Above
        var query by remember { mutableStateOf("") }

        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 6.dp, bottom = 12.dp)
        ) {
            SearchBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 8.dp),
                query = query,
                onQueryChange = { query = it },
                onSearch = { onSearch(query) },
                onQRCodeScan = onQRCodeScan
            )
        }
    }
}
//
//@Composable
//fun LocationSelectionButton(
//    selectedLocation: String,
//    onLocationClick: () -> Unit,
//    modifier: Modifier = Modifier
//) {
//    Card(
//        onClick = onLocationClick,
//        modifier = modifier
//            .background(
//                brush = Brush.verticalGradient(
//                    colors = listOf(
//                        Color(0xFF923839),                // top
//                        Color(0xFF903E3F),                // top
//                    )
//                ),
////                shape = RoundedCornerShape(12.dp) // ✅ ensures gradient matches card
//            ),
////        shape = RoundedCornerShape(12.dp), // ✅ keep same shape
//        colors = CardDefaults.cardColors(
//            containerColor = Color.Transparent // ✅ allow gradient to show
//        ),
////        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
//    ) {
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(horizontal = 12.dp),
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            Icon(
//                imageVector = Icons.Default.LocationOn,
//                contentDescription = "Location",
//                tint = MaterialTheme.customColors.white
//            )
//            Spacer(modifier = Modifier.width(4.dp))
//            Row(
//                modifier = Modifier.weight(1f),
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                Text(
//                    text = "Deliver to",
//                    style = MaterialTheme.typography.bodyLarge,
//                    fontWeight = FontWeight.Medium,
//                    fontSize = 14.sp,
//                    color = MaterialTheme.customColors.white
//                )
//                Spacer(modifier = Modifier.width(4.dp))
//                Text(
//                    text = selectedLocation,
//                    style = MaterialTheme.typography.bodyLarge,
//                    fontWeight = FontWeight.Medium,
//                    fontSize = 14.sp,
//                    color = MaterialTheme.customColors.white
//                )
//                Icon(
//                    imageVector = Icons.Default.KeyboardArrowDown,
//                    contentDescription = "Change location",
//                    tint = MaterialTheme.customColors.white
//                )
//            }
//
//            Button(
//                onClick = { /* Handle Join Ultra action */ },
//                modifier = Modifier
//                    .height(32.dp)
//                    .padding(start = 8.dp)
//                    .widthIn(min = 90.dp),
//                shape = MaterialTheme.shapes.extraLarge,
//                contentPadding = PaddingValues(horizontal = 8.dp),
//                colors = ButtonDefaults.buttonColors(
//                    containerColor = MaterialTheme.customColors.white,
//                    contentColor = MaterialTheme.customColors.black
//                )
//            ) {
//                Text(
//                    text = "Join Ultra",
//                    style = MaterialTheme.typography.labelSmall,
//                    fontWeight = FontWeight.Bold,
//                    fontSize = 13.sp
//                )
//            }
//        }
//    }
//}



@Composable
fun LocationSelectionButton(
    selectedLocation: String,
    onLocationClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = onLocationClick,
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.customColors.darkAccent
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 12.dp, end = 12.dp, top = 0.dp, bottom = 0.dp),
//                .padding(horizontal = 12.dp, vertical = 0.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.LocationOn,
                contentDescription = "Location",
                tint = MaterialTheme.customColors.white // ✅ Icon color white
            )
            Spacer(modifier = Modifier.width(4.dp))
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Deliver to",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp,
                    color = MaterialTheme.customColors.white // ✅ Text color white
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = selectedLocation,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp,
                    color = MaterialTheme.customColors.white // ✅ Text color white
                )
                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = "Change location",
                    tint = MaterialTheme.customColors.white // ✅ Arrow icon color white
                )
            }

            Button(
                onClick = { /* Handle Join Prime action */ },
                modifier = Modifier
                    .height(32.dp)
                    .padding(start = 8.dp)
                    .widthIn(min = 90.dp),
                shape = MaterialTheme.shapes.extraLarge,
                contentPadding = PaddingValues(horizontal = 8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.customColors.white, // ✅ Button background white
                    contentColor = MaterialTheme.customColors.black     // ✅ Button text black
                )
            ) {
                Text(
                    text = "Join Ultra",
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp
                )
            }
        }
    }

}