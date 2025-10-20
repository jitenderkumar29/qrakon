package com.example.qrakon.components.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.qrakon.components.fashion.FashionScreen
import com.example.qrakon.components.fashion.fashiontab.CategoriesFashionPage
import com.example.qrakon.components.homescreen.CategoryScreen
import com.example.qrakon.components.fashion.categorydetail.CategoryDetailScreen // <- create this

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "category_screen"
    ) {
        // ✅ Home category screen
        composable("category_screen") {
            CategoryScreen(
                onOpenFashion = {
                    navController.navigate("fashion_screen")
                }
            )
        }

        // ✅ Fashion section
        composable("fashion_screen") {
            FashionScreen(
                onBack = { navController.popBackStack() },
                navController = navController
            )
        }

        // ✅ Fashion categories tab page
        composable("fashion_categories") {
            CategoriesFashionPage(
                onBackClick = { navController.popBackStack() },
                navController = navController
            )
        }

        // ✅ NEW: Category detail route
        composable(
            route = "categoryDetail/{categoryName}",
            arguments = listOf(navArgument("categoryName") { type = NavType.StringType })
        ) { backStackEntry ->
            val categoryName = backStackEntry.arguments?.getString("categoryName")
            // Example usage in a navigation setup
            CategoryDetailScreen(
                categoryName = categoryName,
                onBackClick = { navController.popBackStack() },
                onTabSelected = { category ->
                    // Navigate to the selected category screen
                    navController.navigate("category/${category.id}") {
                        // Add navigation options if needed
                    }
                },
                onBanner1Click = {
                    // Navigate to banner 1 destination
                    navController.navigate("banner1")
                },
                onBanner2Click = {
                    // Navigate to banner 2 destination
                    navController.navigate("banner2")
                },
                onBanner3Click = {
                    // Navigate to banner 3 destination
                    navController.navigate("banner3")
                }
            )
//            CategoryDetailScreen(
//                categoryName = categoryName,
//                onBackClick = { navController.popBackStack() }
//            )
        }
    }
}
