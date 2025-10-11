package com.example.qrakon.components.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.qrakon.components.fashion.CategoriesFashionPage
import com.example.qrakon.components.fashion.FashionScreen
import com.example.qrakon.components.homescreen.CategoryScreen

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "category_screen"
    ) {
        composable("category_screen") {
            CategoryScreen(
                onOpenFashion = {
                    navController.navigate("fashion_screen")
                }
            )
        }

        composable("fashion_screen") {
            FashionScreen(
                onBack = { navController.popBackStack() },
                navController = navController // Pass navController here
            )
        }

        composable("fashion_categories") {
            CategoriesFashionPage(
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}