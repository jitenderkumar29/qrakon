// File: com.example.qrakon.components.homescreen.BannerBackgroundManager.kt
package com.example.qrakon.components.homescreen

import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color

// Global object to manage banner background color
object BannerBackgroundManager {
    private val _backgroundColor = mutableStateOf(Color.Transparent)
    val backgroundColor: State<Color> = _backgroundColor

    fun updateBackgroundColor(color: Color) {
        _backgroundColor.value = color
    }
}