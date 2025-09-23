package com.example.qrakon.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

// Create CompositionLocal for custom colors
val LocalCustomColors = staticCompositionLocalOf {
    LightCustomColors
}

// Extension function to access custom colors from MaterialTheme
val MaterialTheme.customColors: CustomColors
    @Composable
    get() = LocalCustomColors.current

data class CustomColors(
    // Primary colors
    val primary: Color,
    val onPrimary: Color,
    val primaryContainer: Color,
    val onPrimaryContainer: Color,

    // Secondary colors
    val secondary: Color,
    val onSecondary: Color,
    val secondaryContainer: Color,
    val onSecondaryContainer: Color,

    // Tertiary colors
    val tertiary: Color,
    val onTertiary: Color,
    val tertiaryContainer: Color,
    val onTertiaryContainer: Color,

    // Background colors
    val background: Color,
    val onBackground: Color,

    // Surface colors
    val surface: Color,
    val onSurface: Color,
    val surfaceVariant: Color,
    val onSurfaceVariant: Color,

    // Error colors
    val error: Color,
    val onError: Color,
    val errorContainer: Color,
    val onErrorContainer: Color,

    // Outline colors
    val outline: Color,
    val outlineVariant: Color,

    // Inverse colors
    val inverseSurface: Color,
    val inverseOnSurface: Color,
    val inversePrimary: Color,

    // Scrim
    val scrim: Color,

    // Custom accent colors
    val lightAccent: Color,
    val darkAccent: Color,
    val footer: Color,
    val orange: Color,

    // Custom gray colors
    val spacerColor: Color,
    val lightGray: Color,
    val gray: Color,
    val darkGray: Color,

    // Additional custom colors
    val success: Color,
    val warning: Color,
    val info: Color,

    // Basic colors
    val white: Color,
    val black: Color,
    val linkColor: Color,
    val linkColorDark: Color,
    val buttonRed: Color,
    val buttonRedHover: Color,

//     Image BG Color
    val imageBgColor1: Color
)

val LightCustomColors = CustomColors(
    // Primary colors
    primary = Color(0xFF162271),
    onPrimary = Color.White,
    primaryContainer = Color(0xFFDDE1FF),
    onPrimaryContainer = Color(0xFF00105C),

    // Secondary colors
    secondary = Color(0xFF5A5D72),
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFDFE1F9),
    onSecondaryContainer = Color(0xFF171B2C),

    // Tertiary colors
    tertiary = Color(0xFF77536D),
    onTertiary = Color.White,
    tertiaryContainer = Color(0xFFFFD7F1),
    onTertiaryContainer = Color(0xFF2D1228),

    // Background colors
    background = Color(0xFFFEFBFF),
    onBackground = Color(0xFF1B1B1F),

    // Surface colors
    surface = Color(0xFFFEFBFF),
    onSurface = Color(0xFF1B1B1F),
    surfaceVariant = Color(0xFFE3E1EC),
    onSurfaceVariant = Color(0xFF46464F),

    // Error colors
    error = Color(0xFFBA1A1A),
    onError = Color.White,
    errorContainer = Color(0xFFFFDAD6),
    onErrorContainer = Color(0xFF410002),

    // Outline colors
    outline = Color(0xFF767680),
    outlineVariant = Color(0xFFC7C6D0),

    // Inverse colors
    inverseSurface = Color(0xFF303034),
    inverseOnSurface = Color(0xFFF2F0F4),
    inversePrimary = Color(0xFFB9C3FF),

    // Scrim
    scrim = Color(0xFF000000),

    // Custom accent colors
    lightAccent = Color(0xFF7B0003),
    darkAccent = Color(0xFF7B0003),
//    darkAccent = Color(0xFFF62626),
    footer = Color(0xFF760241),
    orange = Color(0xFFFB5E20),
//    lightAccent = Color(0xFFB9DC96),
//    darkAccent = Color(0xFFCBF0A3),
//    lightAccent = Color(0xFF162271),
//    darkAccent = Color(0xFF5A6EF1),

    // Custom gray colors
    spacerColor = Color(0xFFE3E2E2),
    lightGray = Color(0xFFF5F5F5),
    gray = Color(0xFF9E9E9E),
    darkGray = Color(0xFF424242),

    // Additional custom colors
    success = Color(0xFF2E7D32),
    warning = Color(0xFFF57C00),
    info = Color(0xFF0288D1),

    // Basic colors
    white = Color(0xFFFFFFFF),
    black = Color(0xFF000000),
    linkColor = Color(0xFF2162a1),
    linkColorDark = Color(0xFF0909a3),
    buttonRed = Color(0xFFE83F25),
    buttonRedHover = Color(0xFFA62C2C),

    //     Image BG Color
    imageBgColor1 = Color(0xFFDDF4FF)


)

val DarkCustomColors = CustomColors(
    // Primary colors
    primary = Color(0xFFB9C3FF),
    onPrimary = Color(0xFF00268A),
    primaryContainer = Color(0xFF0039B9),
    onPrimaryContainer = Color(0xFFDDE1FF),

    // Secondary colors
    secondary = Color(0xFFC3C5DD),
    onSecondary = Color(0xFF2C2F42),
    secondaryContainer = Color(0xFF424659),
    onSecondaryContainer = Color(0xFFDFE1F9),

    // Tertiary colors
    tertiary = Color(0xFFE6BAD8),
    onTertiary = Color(0xFF44263D),
    tertiaryContainer = Color(0xFF5D3C54),
    onTertiaryContainer = Color(0xFFFFD7F1),

    // Background colors
    background = Color(0xFF1B1B1F),
    onBackground = Color(0xFFE4E2E6),

    // Surface colors
    surface = Color(0xFF1B1B1F),
    onSurface = Color(0xFFE4E2E6),
    surfaceVariant = Color(0xFF46464F),
    onSurfaceVariant = Color(0xFFC7C6D0),

    // Error colors
    error = Color(0xFFFFB4AB),
    onError = Color(0xFF690005),
    errorContainer = Color(0xFF93000A),
    onErrorContainer = Color(0xFFFFDAD6),

    // Outline colors
    outline = Color(0xFF90909A),
    outlineVariant = Color(0xFF46464F),

    // Inverse colors
    inverseSurface = Color(0xFFE4E2E6),
    inverseOnSurface = Color(0xFF1B1B1F),
    inversePrimary = Color(0xFF162271),

    // Scrim
    scrim = Color(0xFF000000),

    // Custom accent colors
    lightAccent = Color(0xFF7B0003),
    darkAccent = Color(0xFFF62626),
    footer = Color(0xFF760241),
    orange = Color(0xFFFB5E20),

    // Custom gray colors
    spacerColor = Color(0xFFE3E2E2),
    lightGray = Color(0xFF2D2D2D),
    gray = Color(0xFF555555),
    darkGray = Color(0xFF1A1A1A),

    // Additional custom colors
    success = Color(0xFF81C784),
    warning = Color(0xFFFFB74D),
    info = Color(0xFF4FC3F7),

    // Basic colors
    white = Color(0xFFFFFFFF),
    black = Color(0xFF000000),
    linkColor = Color(0xFF2162a1),
    linkColorDark = Color(0xFF0909a3),
    buttonRed = Color(0xFFE83F25),
    buttonRedHover = Color(0xFFA62C2C),
    imageBgColor1 = Color(0xFFDDF4FF)
)

private val LightColorScheme = lightColorScheme(
    primary = LightCustomColors.primary,
    onPrimary = LightCustomColors.onPrimary,
    primaryContainer = LightCustomColors.primaryContainer,
    onPrimaryContainer = LightCustomColors.onPrimaryContainer,
    secondary = LightCustomColors.secondary,
    onSecondary = LightCustomColors.onSecondary,
    secondaryContainer = LightCustomColors.secondaryContainer,
    onSecondaryContainer = LightCustomColors.onSecondaryContainer,
    tertiary = LightCustomColors.tertiary,
    onTertiary = LightCustomColors.onTertiary,
    tertiaryContainer = LightCustomColors.tertiaryContainer,
    onTertiaryContainer = LightCustomColors.onTertiaryContainer,
    background = LightCustomColors.background,
    onBackground = LightCustomColors.onBackground,
    surface = LightCustomColors.surface,
    onSurface = LightCustomColors.onSurface,
    surfaceVariant = LightCustomColors.surfaceVariant,
    onSurfaceVariant = LightCustomColors.onSurfaceVariant,
    error = LightCustomColors.error,
    onError = LightCustomColors.onError,
    errorContainer = LightCustomColors.errorContainer,
    onErrorContainer = LightCustomColors.onErrorContainer,
    outline = LightCustomColors.outline,
    outlineVariant = LightCustomColors.outlineVariant,
    inverseSurface = LightCustomColors.inverseSurface,
    inverseOnSurface = LightCustomColors.inverseOnSurface,
    inversePrimary = LightCustomColors.inversePrimary,
    scrim = LightCustomColors.scrim
)

private val DarkColorScheme = darkColorScheme(
    primary = DarkCustomColors.primary,
    onPrimary = DarkCustomColors.onPrimary,
    primaryContainer = DarkCustomColors.primaryContainer,
    onPrimaryContainer = DarkCustomColors.onPrimaryContainer,
    secondary = DarkCustomColors.secondary,
    onSecondary = DarkCustomColors.onSecondary,
    secondaryContainer = DarkCustomColors.secondaryContainer,
    onSecondaryContainer = DarkCustomColors.onSecondaryContainer,
    tertiary = DarkCustomColors.tertiary,
    onTertiary = DarkCustomColors.onTertiary,
    tertiaryContainer = DarkCustomColors.tertiaryContainer,
    onTertiaryContainer = DarkCustomColors.onTertiaryContainer,
    background = DarkCustomColors.background,
    onBackground = DarkCustomColors.onBackground,
    surface = DarkCustomColors.surface,
    onSurface = DarkCustomColors.onSurface,
    surfaceVariant = DarkCustomColors.surfaceVariant,
    onSurfaceVariant = DarkCustomColors.onSurfaceVariant,
    error = DarkCustomColors.error,
    onError = DarkCustomColors.onError,
    errorContainer = DarkCustomColors.errorContainer,
    onErrorContainer = DarkCustomColors.onErrorContainer,
    outline = DarkCustomColors.outline,
    outlineVariant = DarkCustomColors.outlineVariant,
    inverseSurface = DarkCustomColors.inverseSurface,
    inverseOnSurface = DarkCustomColors.inverseOnSurface,
    inversePrimary = DarkCustomColors.inversePrimary,
    scrim = DarkCustomColors.scrim
)

@Composable
fun QrakonAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    val customColors = if (darkTheme) DarkCustomColors else LightCustomColors

    CompositionLocalProvider(LocalCustomColors provides customColors) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography(),
            content = content
        )
    }
}

// Alternative access method
object AppTheme {
    val customColors: CustomColors
        @Composable
        get() = LocalCustomColors.current
}