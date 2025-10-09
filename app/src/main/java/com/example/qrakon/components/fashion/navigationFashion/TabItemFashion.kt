package com.example.qrakon.components.fashion.navigationFashion

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.qrakon.R
import com.example.qrakon.components.homescreen.HomeFashionScreen
import com.example.qrakon.components.homescreen.HomeScreen

sealed class TabItemFashion(
    val title: String,
    val icon: ImageVector,
    val imageResource: Int? = null,
    val screen: @Composable () -> Unit
) {
    data object Home : TabItemFashion(
        title = "Home",
        icon = Icons.Filled.Home,
        imageResource = R.drawable.outline_home_24, // Add this image to your drawable resources
        screen = { HomeFashionScreen() }
    )

    data object Explore : TabItemFashion(
        title = "You",
        icon = Icons.Filled.Person,
        imageResource = R.drawable.outline_person_24, // Add this image to your drawable resources
        screen = { ExploreScreen() }
    )

    data object Favorites : TabItemFashion(
        title = "Wallet",
        icon = Icons.Filled.Star,
        imageResource = R.drawable.outline_account_balance_wallet_24, // Add this image to your drawable resources
        screen = { FavoritesScreen() }
    )

    data object Profile : TabItemFashion(
        title = "Cart",
        icon = Icons.Filled.ShoppingCart,
        imageResource = R.drawable.outline_shopping_cart_24, // Add this image to your drawable resources
        screen = { ProfileScreen() }
    )

    data object Settings : TabItemFashion(
        title = "Category",
        icon = Icons.Filled.Menu,
        imageResource = R.drawable.ic_category_24, // Add this image to your drawable resources
        screen = { SettingsScreen() }
    )

    data object Notifications : TabItemFashion(
        title = "Pay",
        icon = Icons.Filled.Notifications,
        imageResource = R.drawable.baseline_payment_24, // Add this image to your drawable resources
        screen = { NotificationsScreen() }
    )
}