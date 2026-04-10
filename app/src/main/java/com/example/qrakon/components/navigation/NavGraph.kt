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
import com.example.qrakon.components.location.LocationAddress
import com.example.qrakon.components.restaurants.RestaurantDetails
import com.example.qrakon.components.restaurants.TopRatedRestaurantItem
import com.example.qrakon.components.restaurants.completeRestaurantItems

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "category_screen"
    ) {
        // ✅ Home category screen
        composable("category_screen") {
            CategoryScreen(
                navController = navController,
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
            route = "categoryDetail/{categoryName}/{id}",
            arguments = listOf(
                navArgument("categoryName") { type = NavType.StringType },
                navArgument("id") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val categoryName = backStackEntry.arguments?.getString("categoryName")
            val categoryId = backStackEntry.arguments?.getInt("id")

            CategoryDetailScreen(
                categoryName = categoryName,
                categoryId = categoryId,
                onBackClick = { navController.popBackStack() },
                onTabSelected = { category ->
                    navController.navigate("category/${category.id}")
                },
                onBanner1Click = { navController.navigate("banner1") },
                onBanner2Click = { navController.navigate("banner2") },
                onBanner3Click = { navController.navigate("banner3") }
            )
        }


        // RestaurantDetails Route
        composable(
            "restaurant_details/{restaurantId}/{category}",
            arguments = listOf(
                navArgument("restaurantId") { type = NavType.IntType },
                navArgument("category") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val restaurantId = backStackEntry.arguments?.getInt("restaurantId")
            val category = backStackEntry.arguments?.getString("category") ?: "all"

            // Find the restaurant item by ID from your data source
            val restaurantItem = findRestaurantById(restaurantId)

            RestaurantDetails(
                onBackClick = { navController.popBackStack() },
                navController = navController,
                restaurantItem = restaurantItem,
                category = category  // Pass the category
            )
        }

        composable("location") {
            LocationAddress(
                onBackClick = {
                    navController.popBackStack()
                },

                onLocationSelected = { location ->
                    navController.previousBackStackEntry
                        ?.savedStateHandle
                        ?.set("selected_location", location)

                    navController.popBackStack()
                },

                onUseCurrentLocation = {
                    navController.previousBackStackEntry
                        ?.savedStateHandle
                        ?.set("selected_location", "Current Location")

                    navController.popBackStack()
                }
            )
        }
    }
}

fun findRestaurantById(id: Int?): TopRatedRestaurantItem? {
    return completeRestaurantItems.find { it.id == id }
}
