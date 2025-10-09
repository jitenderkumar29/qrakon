package com.example.qrakon

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.graphics.toArgb
import com.example.qrakon.components.homescreen.CategoryScreen
import com.example.qrakon.components.navigation.AppNavGraph
import com.example.qrakon.components.splashscreen.SplashScreen
import com.example.qrakon.ui.theme.QrakonAppTheme
import com.example.qrakon.ui.theme.customColors
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set up edge-to-edge display
        enableEdgeToEdge()

        setContent {
            QrakonAppTheme {
                AppContent(window = window)
            }
        }
    }

    private fun enableEdgeToEdge() {
        WindowCompat.setDecorFitsSystemWindows(window, false)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            window.attributes.layoutInDisplayCutoutMode =
                WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
        }

        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
    }
}

@Composable
fun AppContent(window: Window? = null) {
    var showSplash by remember { mutableStateOf(true) }

    val statusBarColor: Color = if (showSplash) {
        MaterialTheme.colorScheme.primary
    } else {
        MaterialTheme.customColors.footer
    }

    val navBarColor: Color = if (showSplash) {
        MaterialTheme.colorScheme.primaryContainer
    } else {
        MaterialTheme.customColors.footer
    }

    val currentWindow = rememberUpdatedState(window)
    DisposableEffect(currentWindow.value, statusBarColor, navBarColor) {
        val win = currentWindow.value
        if (win != null) {
            win.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            win.statusBarColor = statusBarColor.toArgb()
            win.navigationBarColor = navBarColor.toArgb()
            val insetsController = WindowInsetsControllerCompat(win, win.decorView)
            val lightIcons = statusBarColor.luminance() < 0.5f
            insetsController.isAppearanceLightStatusBars = !lightIcons
        }
        onDispose { }
    }

    Surface(modifier = Modifier.fillMaxSize()) {
        if (showSplash) {
            SplashScreen(
                onSplashEnd = { showSplash = false },
                duration = 500L
            )
        } else {
            val navController = rememberNavController()

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(statusBarColor)
                    .statusBarsPadding()
            ) {
                AppNavGraph(navController = navController)
            }
        }
    }
}