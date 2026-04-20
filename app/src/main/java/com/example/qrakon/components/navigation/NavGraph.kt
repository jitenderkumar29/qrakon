package com.example.qrakon.components.navigation

import RestaurantInfo
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.qrakon.components.fashion.FashionScreen
import com.example.qrakon.components.fashion.fashiontab.CategoriesFashionPage
import com.example.qrakon.components.homescreen.CategoryScreen
import com.example.qrakon.components.fashion.categorydetail.CategoryDetailScreen
import com.example.qrakon.components.homescreen.AddressMap
import com.example.qrakon.components.location.LocationAddress
import com.example.qrakon.components.restaurants.CheckOutFood
import com.example.qrakon.components.restaurants.RestaurantDetails
import com.example.qrakon.components.restaurants.TopRatedRestaurantItem
import com.example.qrakon.components.restaurants.completeRestaurantItems

@RequiresApi(Build.VERSION_CODES.O)
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

        // ✅ Category detail route
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
                category = category
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

        composable("address_map") {
            AddressMap(
                onBackClick = { navController.popBackStack() },
                onSaveAddress = {
                    // Handle save address logic
                    navController.popBackStack()
                }
            )
        }

        // ✅ Fixed: Restaurant Info Route - Using completeRestaurantItems instead of restaurantItems
        composable(
            "restaurant_info/{restaurantName}",
            arguments = listOf(navArgument("restaurantName") { type = NavType.StringType })
        ) { backStackEntry ->
            val restaurantName = backStackEntry.arguments?.getString("restaurantName") ?: ""
            // Fixed: Use completeRestaurantItems instead of restaurantItems
            val restaurantItem = completeRestaurantItems.find { it.restaurantName == restaurantName }

            RestaurantInfo(
                restaurantName = restaurantItem?.restaurantName ?: "",
//                cuisineInfo = restaurantItem?.cuisineInfo ?: "",
                address = restaurantItem?.address ?: "",
//                openStatus = restaurantItem?.openStatus ?: "Open now",
//                closeStatus = restaurantItem?.closeStatus ?: "Closes 11:00 pm",
//                serviceType = restaurantItem?.serviceType ?: "Provides both delivery & dining",
//                sinceYear = restaurantItem?.sinceYear ?: "2020",
//                legalName = restaurantItem?.legalName ?: "",
//                gstNumber = restaurantItem?.gstNumber ?: "",
//                isBikgane = restaurantItem?.isBikgane ?: false,
//                bikganeLegalName = restaurantItem?.bikganeLegalName ?: "",
//                bikganeGst = restaurantItem?.bikganeGst ?: "",
//                fssaiLicense = restaurantItem?.fssaiLicense ?: "",
                onBackPressed = { navController.popBackStack() },
                onSaveRestaurant = { /* Handle save */ },
                onShareRestaurant = { /* Handle share */ },
                onViewDiningPage = { /* Handle dining page */ },
                onHideRestaurant = { /* Handle hide */ },
                onGoBackToMenu = { navController.popBackStack() }
            )
        }


        // ✅ ADD THIS: CheckOutFood route
        composable("checkout_food") {
            CheckOutFood(
                onBackClick = { navController.popBackStack() },
                onConfirmOrder = {
                    // Handle order confirmation - navigate to success screen or pop back
                    navController.navigate("order_confirmation") {
                        popUpTo("checkout_food") { inclusive = true }
                    }
                },
                // Optional: Pass cart items data if needed
                // cartItems = cartItemsList,
                // totalAmount = totalAmount
            )
        }

    }
}

fun findRestaurantById(id: Int?): TopRatedRestaurantItem? {
    return completeRestaurantItems.find { it.id == id }
}