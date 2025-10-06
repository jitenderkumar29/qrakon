package com.example.qrakon.components.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
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

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.navigationBars

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
            Column(
                modifier = Modifier
                    .windowInsetsPadding( // ✅ Adds padding above system nav bar
                        WindowInsets.navigationBars.only(WindowInsetsSides.Bottom)
                    )
            ) {
                // 🔶 Top border bar (active tab indicator)
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .height(5.dp) // slightly smaller border
                ) {
                    tabItems.forEachIndexed { index, _ ->
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight()
                                .clip(
                                    RoundedCornerShape(
                                        bottomStart = 4.dp,
                                        bottomEnd = 4.dp
                                    )
                                )
                                .background(
                                    if (selectedTabIndex == index)
                                        MaterialTheme.customColors.orange
                                    else
                                        MaterialTheme.customColors.footer
                                )
                        )
                    }
                }

                // Bottom navigation bar
                NavigationBar(
                    containerColor = MaterialTheme.customColors.footer,
                    modifier = Modifier
                        .height(65.dp)
                        .padding(horizontal = 4.dp)
                        .navigationBarsPadding() // ✅ keeps bar above gesture nav/status bar
                ) {
                    tabItems.forEachIndexed { index, item ->
                        NavigationBarItem(
                            selected = selectedTabIndex == index,
                            onClick = { selectedTabIndex = index },
                            modifier = Modifier
                                .weight(1f) // equally distribute width, avoids extra spacing
                                .padding(horizontal = 0.dp), // reduce internal padding
                            icon = {
                                if (item.imageResource != null) {
                                    androidx.compose.foundation.Image(
                                        painter = painterResource(id = item.imageResource),
                                        contentDescription = item.title,
                                        modifier = Modifier.size(28.dp),
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
                                        modifier = Modifier.size(28.dp),
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
                                indicatorColor = MaterialTheme.customColors.footer
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
