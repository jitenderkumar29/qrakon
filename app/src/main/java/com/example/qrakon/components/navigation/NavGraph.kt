package com.example.qrakon.components.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.qrakon.components.fashion.FashionScreen
import com.example.qrakon.components.homescreen.CategoryScreen
import com.example.qrakon.components.homescreen.FashionScreen

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "category_screen"
    ) {
        // 🏠 Main screen with all categories
        composable("category_screen") {
            CategoryScreen(
                onOpenFashion = {
                    navController.navigate("fashion_screen")
                }
            )
        }

        // 👗 Fashion page
        composable("fashion_screen") {
            FashionScreen(
                onBack = { navController.popBackStack() }
            )
        }
    }
}
