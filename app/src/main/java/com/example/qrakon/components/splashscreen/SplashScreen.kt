package com.example.qrakon.components.splashscreen

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.EaseOutBack
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.qrakon.R
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    onSplashEnd: () -> Unit = {}, // Default empty lambda for preview
    duration: Long = 2000L
) {
    val alphaAnimation = remember { Animatable(0f) }
    val scaleAnimation = remember { Animatable(0.8f) }

    LaunchedEffect(Unit) {
        // Parallel animations - fade in and scale up
        alphaAnimation.animateTo(1f, tween(durationMillis = 800))
        scaleAnimation.animateTo(
            targetValue = 1f,
            animationSpec = tween(
                durationMillis = 800,
                easing = EaseOutBack
            )
        )

        // Wait for specified duration
        delay(duration)

        // Fade out animation
        alphaAnimation.animateTo(0f, tween(durationMillis = 500))

        // Notify that splash screen should end
        delay(500) // Wait for fade out to complete
        onSplashEnd()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.primary,
                        MaterialTheme.colorScheme.primaryContainer
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // App logo - using your custom logo
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "App Logo",
                modifier = Modifier
                    .size(320.dp)
                    .alpha(alphaAnimation.value)
                    .scale(scaleAnimation.value)
            )

            // Add some spacing between image and text
            Spacer(modifier = Modifier.height(24.dp))

            // App name text
//            Text(
//                text = "QRakon Test",
//                style = MaterialTheme.typography.headlineMedium.copy(
//                    fontWeight = FontWeight.Bold,
//                    color = MaterialTheme.colorScheme.onPrimary
//                ),
//                textAlign = TextAlign.Center,
//                modifier = Modifier.alpha(alphaAnimation.value)
//            )

            // Optional subtitle
//            Text(
//                text = "Scan and Connect",
//                style = MaterialTheme.typography.bodyLarge.copy(
//                    color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f)
//                ),
//                textAlign = TextAlign.Center,
//                modifier = Modifier
//                    .alpha(alphaAnimation.value)
//                    .padding(top = 8.dp)
//            )
        }
    }
}

// Simple version with text
@Composable
fun SimpleSplashScreen(
    onSplashEnd: () -> Unit = {}, // Default empty lambda for preview
    duration: Long = 2000L
) {
    val alphaAnimation = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        alphaAnimation.animateTo(1f, tween(1000))
        delay(duration)
        onSplashEnd()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primaryContainer)
            .alpha(alphaAnimation.value),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "App Logo",
                modifier = Modifier.size(120.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

//            Text(
//                text = "Splash Screen For Qrakon",
//                style = MaterialTheme.typography.headlineMedium.copy(
//                    fontWeight = FontWeight.Bold,
//                    color = MaterialTheme.colorScheme.onPrimaryContainer
//                ),
//                textAlign = TextAlign.Center,
//                modifier = Modifier.padding(horizontal = 16.dp)
//            )
        }
    }
}

// Preview-specific composable without parameters
@Composable
fun PreviewSplashScreen() {
    SplashScreen(onSplashEnd = {})
}

@Composable
fun PreviewSimpleSplashScreen() {
    SimpleSplashScreen(onSplashEnd = {})
}

// Preview functions
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SplashScreenPreview() {
    MaterialTheme {
        PreviewSplashScreen()
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SimpleSplashScreenPreview() {
    MaterialTheme {
        PreviewSimpleSplashScreen()
    }
}

@Preview(showBackground = true, showSystemUi = true, name = "Dark Theme")
@Composable
fun SplashScreenDarkPreview() {
    MaterialTheme(
        colorScheme = androidx.compose.material3.darkColorScheme()
    ) {
        PreviewSplashScreen()
    }
}