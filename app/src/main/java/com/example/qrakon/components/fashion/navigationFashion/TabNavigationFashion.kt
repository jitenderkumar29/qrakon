package com.example.qrakon.components.fashion.navigationFashion

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
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.only
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
import androidx.navigation.NavHostController
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.navigationBars
import com.example.qrakon.components.fashion.fashiontab.FashionTab
import com.example.qrakon.components.homescreen.HomeFashionScreen
import com.example.qrakon.ui.theme.customColors

@Composable
fun TabNavigationFashion(
    navController: NavHostController // Add NavController parameter
) {
    var selectedTabIndex by rememberSaveable { mutableIntStateOf(0) }

    val tabItems = listOf(
        TabItemFashion.Home,
        TabItemFashion.Explore,
        TabItemFashion.Favorites,
        TabItemFashion.Profile,
        TabItemFashion.Settings,
        TabItemFashion.Notifications
    )

    Scaffold(
        containerColor = MaterialTheme.customColors.footer,
        bottomBar = {
            Column(
                modifier = Modifier
                    .windowInsetsPadding(
                        WindowInsets.navigationBars.only(WindowInsetsSides.Bottom)
                    )
            ) {
                // 🔶 Top border bar (active tab indicator)
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .height(5.dp)
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
                        .navigationBarsPadding()
                ) {
                    tabItems.forEachIndexed { index, item ->
                        NavigationBarItem(
                            selected = selectedTabIndex == index,
                            onClick = { selectedTabIndex = index },
                            modifier = Modifier
                                .weight(1f)
                                .padding(horizontal = 0.dp),
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
            // Pass the navController to the Home screen (which should contain FashionTab)
            when (selectedTabIndex) {
                0 -> HomeFashionScreen(navController = navController) // Home tab shows fashion categories
                else -> tabItems[selectedTabIndex].screen()
            }
        }
    }
}

// Create a Home screen that contains the FashionTab
//@Composable
//fun HomeFashionScreen(navController: NavHostController) {
//    FashionTab(
//        onCategorySelected = { categoryPage ->
//            // Handle category selection if needed
//            println("Category selected: $categoryPage")
//        },
//        onOpenFashionCategory = {
//            // Navigate to categories page when category icon is clicked
//            navController.navigate("fashion_categories")
//        }
//    )
//}