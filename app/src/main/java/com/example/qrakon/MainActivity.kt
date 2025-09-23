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
import com.example.qrakon.components.homescreen.CategoryHeader
import com.example.qrakon.components.homescreen.CategoryScreen
import com.example.qrakon.components.navigation.TabNavigationApp
import com.example.qrakon.components.splashscreen.SplashScreen
import com.example.qrakon.ui.theme.QrakonAppTheme
import com.example.qrakon.ui.theme.customColors

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Make the app edge-to-edge so we can control the system bars ourselves
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            QrakonAppTheme {
                AppContent(window = window)
            }
        }
    }
}


@Composable
fun AppContent(window: Window? = null) {
    var showSplash by remember { mutableStateOf(true) }

    val statusBarColor: Color = if (showSplash) {
        MaterialTheme.colorScheme.primary
    } else {
        MaterialTheme.customColors.lightAccent
    }

    // ✅ Nav bar color only changes after splash
    val navBarColor: Color = if (showSplash) {
        MaterialTheme.colorScheme.primaryContainer // or Color.Transparent
//        Color.Transparent // or Color.Transparent
    } else {
        MaterialTheme.customColors.footer
    }

    val currentWindow = rememberUpdatedState(window)
    DisposableEffect(currentWindow.value, statusBarColor, navBarColor) {
        val win = currentWindow.value
        if (win != null) {
            win.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            win.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)

            win.statusBarColor = statusBarColor.copy(alpha = 1f).toArgb()

            try {
                win.navigationBarColor = navBarColor.toArgb()
            } catch (_: Throwable) { /* ignore OEM issues */ }

            val insetsController = WindowInsetsControllerCompat(win, win.decorView)
            val lightIcons = statusBarColor.luminance() < 0.5f
            insetsController.isAppearanceLightStatusBars = !lightIcons

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val flags = win.decorView.systemUiVisibility
                win.decorView.systemUiVisibility = if (!lightIcons) {
                    flags or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                } else {
                    flags and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
                }
            }
        }
        onDispose { }
    }

    Surface(
        modifier = Modifier
            .fillMaxSize()
//            .background(MaterialTheme.colorScheme.background),
//        color = MaterialTheme.colorScheme.background
    ) {
        if (showSplash) {
            SplashScreen(
                onSplashEnd = { showSplash = false },
                duration = 500L
            )
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .statusBarsPadding() // content starts below status bar
            ) {
                CategoryScreen()
            }
//            TabNavigationApp()
        }
    }
}
