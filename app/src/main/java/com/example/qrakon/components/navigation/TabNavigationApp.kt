package com.example.qrakon.components.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.qrakon.ui.theme.customColors

@Composable
fun TabNavigationApp() {
    var selectedTabIndex by rememberSaveable { mutableIntStateOf(0) }

    val tabItems = listOf(
        TabItem.Home,
        TabItem.Explore,
        TabItem.Favorites,
        TabItem.Profile,
        TabItem.Settings,
        TabItem.Notifications
    )

    Scaffold(
        containerColor = MaterialTheme.customColors.footer,
        bottomBar = {
            Column {
                // 🔶 Top border bar (active tab indicator with rounded bottom corners)
                Row(modifier = Modifier.fillMaxWidth()) {
                    tabItems.forEachIndexed { index, _ ->
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(8.dp)
                                .clip(
                                    RoundedCornerShape(
                                        bottomStart = 4.dp, // rounded bottom-left
                                        bottomEnd = 4.dp    // rounded bottom-right
                                    )
                                )
                                .background(
                                    if (selectedTabIndex == index)
                                        MaterialTheme.customColors.orange
                                    else
                                        MaterialTheme.customColors.footer // hide for inactive tabs
                                )
                        )
                    }
                }

                // Bottom navigation bar
                NavigationBar(
                    containerColor = MaterialTheme.customColors.footer
                ) {
                    tabItems.forEachIndexed { index, item ->
                        NavigationBarItem(
                            selected = selectedTabIndex == index,
                            onClick = { selectedTabIndex = index },
                            icon = {
                                if (item.imageResource != null) {
                                    androidx.compose.foundation.Image(
                                        painter = painterResource(id = item.imageResource),
                                        contentDescription = item.title,
                                        modifier = Modifier.size(24.dp),
                                        colorFilter = ColorFilter.tint(
                                            if (selectedTabIndex == index)
                                                MaterialTheme.customColors.orange
                                            else
                                                MaterialTheme.customColors.white
                                        )
                                    )
                                } else {
                                    Icon(
                                        imageVector = item.icon,
                                        contentDescription = item.title,
                                        tint = if (selectedTabIndex == index)
                                            MaterialTheme.customColors.orange
                                        else
                                            MaterialTheme.customColors.white
                                    )
                                }
                            },
                            label = {
                                Text(
                                    text = item.title,
                                    fontSize = 11.sp,
                                    maxLines = 1,
                                    color = if (selectedTabIndex == index)
                                        MaterialTheme.customColors.orange
                                    else
                                        MaterialTheme.customColors.white
                                )
                            },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = MaterialTheme.customColors.orange,
                                selectedTextColor = MaterialTheme.customColors.orange,
                                unselectedIconColor = MaterialTheme.customColors.white,
                                unselectedTextColor = MaterialTheme.customColors.white,
                                indicatorColor = MaterialTheme.customColors.footer // ✅ No background highlight
                            )
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .background(MaterialTheme.customColors.footer)
                .fillMaxWidth()
                .fillMaxSize()
        ) {
            tabItems[selectedTabIndex].screen()
        }
    }
}
