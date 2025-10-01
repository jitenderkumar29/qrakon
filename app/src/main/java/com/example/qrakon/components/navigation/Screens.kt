package com.example.qrakon.components.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.qrakon.R

// Welcome Screens for each tab

@Composable
fun ExploreScreen() {
    WelcomeScreen(
        title = "Explore",
        message = "Discover amazing content and features in the Explore section of Qrakon!",
        imageResource = R.drawable.outline_person_24
    )
}

@Composable
fun FavoritesScreen() {
    WelcomeScreen(
        title = "Favorites",
        message = "Your saved favorites and bookmarks are safely stored here in Qrakon.",
        imageResource = R.drawable.outline_account_balance_wallet_24
    )
}

@Composable
fun ProfileScreen() {
    WelcomeScreen(
        title = "Profile",
        message = "Manage your Qrakon profile, settings, and personal information here.",
        imageResource = R.drawable.outline_shopping_cart_24
    )
}

@Composable
fun SettingsScreen() {
    WelcomeScreen(
        title = "Settings",
        message = "Customize your Qrakon experience with various settings and preferences.",
        imageResource = R.drawable.ic_category_24
    )
}

@Composable
fun NotificationsScreen() {
    WelcomeScreen(
        title = "Notifications",
        message = "Stay updated with all your Qrakon notifications and alerts.",
        imageResource = R.drawable.outline_more_vert_24
    )
}

// Reusable Welcome Screen Component
@Composable
fun WelcomeScreen(title: String, message: String, imageResource: Int? = null) {
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = title,
                style = androidx.compose.material3.MaterialTheme.typography.headlineMedium,
                color = androidx.compose.material3.MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = message,
                style = androidx.compose.material3.MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                color = androidx.compose.material3.MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(24.dp))

            if (imageResource != null) {
                Image(
                    painter = painterResource(id = imageResource),
                    contentDescription = "$title Icon",
                    modifier = Modifier.size(64.dp)
                )
            } else {
                Icon(
                    imageVector = when (title) {
                        "Home" -> Icons.Default.Home
                        "You" -> Icons.Default.Person
                        "Wallet" -> Icons.Default.Star
                        "Cart" -> Icons.Default.ShoppingCart
                        "Category" -> Icons.Default.Menu
                        else -> Icons.Default.Notifications
                    },
                    contentDescription = "$title Icon",
                    tint = androidx.compose.material3.MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(64.dp)
                )
            }
        }
    }
}