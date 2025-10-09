package com.example.qrakon.components.fashion

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import com.example.qrakon.components.fashion.navigationFashion.TabNavigationFashion
import com.example.qrakon.components.fashion.searchfashion.SearchFashion
import com.example.qrakon.components.navigation.TabNavigationApp
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
//                .padding(12.dp)
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
//            Column(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .background(MaterialTheme.customColors.lightAccent)
//                    .padding(start = 12.dp, end = 12.dp, top = 8.dp, bottom = 0.dp)
////                            .padding(horizontal = 12.dp, vertical = 8.dp)
//            ) {
//                SearchFashion(
//                    query = searchQuery,
//                    onQueryChange = { searchQuery = it },
//                    onSearch = { query ->
//                        println("Searching for fashion: $query")
//                        // Handle fashion search
//                    },
//                    onQRCodeScan = {
//                        println("QR Code scan for fashion")
//                        // Handle QR code scanning for fashion
//                    }
//                )
//            }
            Text(
                text = "Fashion",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.customColors.white
            )
        }

//        Spacer(modifier = Modifier.height(16.dp))
//
//        Text(
//            text = "Latest fashion trends and collections!",
//            fontSize = 18.sp,
//            color = MaterialTheme.customColors.primary,
//            modifier = Modifier.padding(horizontal = 16.dp)
//        )

        TabNavigationFashion()
    }
}
