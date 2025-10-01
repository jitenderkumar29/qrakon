package com.example.qrakon.components.homescreen

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import com.example.qrakon.ui.theme.customColors

@Composable
fun BannerHome(
    images: List<Painter>,
    onImageClick: (Int) -> Unit = {},
    modifier: Modifier = Modifier,
    height: Dp = 270.dp,
    dotSize: Dp = 4.dp,
    dotPadding: Dp = 3.dp,
    selectedDotColor: Color = MaterialTheme.customColors.linkColor,
    unselectedDotColor: Color = Color.White.copy(alpha = 0.9f),
    backgroundColor: Color = Color.White,
    contentScale: ContentScale = ContentScale.FillBounds,
    autoScrollDelay: Long = 5000, // 5 seconds
    autoScrollEnabled: Boolean = true
) {
    require(images.isNotEmpty()) { "Images list cannot be empty" }

    val pagerState = rememberPagerState(
        pageCount = { images.size },
        initialPage = 0
    )
    val coroutineScope = rememberCoroutineScope()

    // ✅ Auto-scroll effect with smooth animation
    LaunchedEffect(autoScrollEnabled) {
        if (!autoScrollEnabled) return@LaunchedEffect

        while (true) {
            delay(autoScrollDelay)
            val nextPage = (pagerState.currentPage + 1) % images.size
            pagerState.animateScrollToPage(nextPage)
        }
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(backgroundColor),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(height)
        ) {
            // Pager with images
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize()
            ) { page ->
                Image(
                    painter = images[page],
                    contentDescription = "Banner ${page + 1}",
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable { onImageClick(page) },
                    contentScale = contentScale
                )
            }

            // Overlay dots at bottom
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 6.dp) // spacing from bottom
            ) {
                OverlayDots(
                    pagerState = pagerState,
                    imageCount = images.size,
                    dotHeight = 5.dp, // width factor reference
//                    dotSize = 5.dp, // width factor reference
                    dotPadding = 3.dp,
                    selectedDotColor = Color.White,
                    unselectedDotColor = Color.Gray,
                    indicatorDuration = autoScrollDelay
                )
            }
        }
//            // Overlay dots
//            Row(
//                horizontalArrangement = Arrangement.Center,
//                modifier = Modifier
//                    .align(Alignment.BottomCenter)
//                    .padding(bottom = dotPadding)
//            ) {
//            OverlayDots(
//                pagerState = pagerState,
//                imageCount = images.size, // only the count is required
//                dotSize = dotSize,
//                dotPadding = dotPadding,
//                selectedDotColor = selectedDotColor,
//                unselectedDotColor = unselectedDotColor,
//                indicatorDuration = autoScrollDelay
//            )
//        }

//            Row(
//                horizontalArrangement = Arrangement.Center,
//                modifier = Modifier
//                    .align(Alignment.BottomCenter)
//                    .padding(bottom = dotPadding)
//            ) {
//                repeat(images.size) { index ->
//                    val isSelected = pagerState.currentPage == index
//                    val animatedDotSize = animateDpAsState(
//                        targetValue = if (isSelected) dotSize else dotSize
//                    )
//                    val dotColor = animateColorAsState(
//                        targetValue = if (isSelected) selectedDotColor else unselectedDotColor
//                    )
//
//                    Box(
//                        modifier = Modifier
//                            .padding(horizontal = dotPadding)
//                            .size(animatedDotSize.value)
//                            .background(color = dotColor.value, shape = CircleShape)
//                            .clickable {
//                                coroutineScope.launch {
//                                    pagerState.animateScrollToPage(index)
//                                }
//                            }
//                    )
//                }
//            }
        }
    }
//}

//Min Height dot line
@Composable
fun OverlayDots(
    pagerState: PagerState,
    imageCount: Int,
    dotHeight: Dp = 3.dp, // reduced height of the line
    dotPadding: Dp = 3.dp,
    selectedDotColor: Color = Color.White,
    unselectedDotColor: Color = Color.Gray,
    indicatorDuration: Long = 3000L // auto-scroll duration
) {
    val coroutineScope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 2.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.padding(bottom = dotPadding)
        ) {
            repeat(imageCount) { index ->
                val isSelected = pagerState.currentPage == index
                val isHighlighted = index <= pagerState.currentPage // ✅ highlight previous + current

                // Progress animation only for the current active dot
                val progress = remember { Animatable(0f) }
                if (isSelected) {
                    LaunchedEffect(pagerState.currentPage) {
                        progress.snapTo(0f)
                        progress.animateTo(
                            targetValue = 1f,
                            animationSpec = tween(
                                durationMillis = indicatorDuration.toInt(),
                                easing = LinearEasing
                            )
                        )
                    }
                } else {
                    LaunchedEffect(pagerState.currentPage) {
                        progress.snapTo(0f)
                    }
                }

                // Width: active = 5×, inactive = 2×
                val targetWidth = if (isSelected) dotHeight * 5 else dotHeight * 5
                val animatedWidth = animateDpAsState(targetValue = targetWidth)
                val animatedHeight = animateDpAsState(targetValue = dotHeight)

                Box(
                    modifier = Modifier
                        .padding(horizontal = dotPadding)
                        .width(animatedWidth.value)
                        .height(animatedHeight.value)
                        .clip(RoundedCornerShape(50))
                        .background(unselectedDotColor)
                        .clickable {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        }
                ) {
                    if (isHighlighted) {
                        Box(
                            modifier = Modifier
                                .fillMaxHeight()
                                .fillMaxWidth(
                                    if (isSelected) progress.value else 1f
                                ) // ✅ current animates, past full
                                .clip(RoundedCornerShape(50))
                                .background(selectedDotColor)
                        )
                    }
                }
            }
        }
    }
}

//
//@Composable
//fun OverlayDots(
//    pagerState: PagerState,
//    imageCount: Int,
//    dotSize: Dp = 6.dp, // height of the line
//    dotPadding: Dp = 3.dp,
//    selectedDotColor: Color = Color.White,
//    unselectedDotColor: Color = Color.Gray,
//    indicatorDuration: Long = 3000L // auto-scroll duration
//) {
//    val coroutineScope = rememberCoroutineScope()
//
//    // Full-width container to lock indicators at the bottom
//    Box(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(bottom = 2.dp),
//        contentAlignment = Alignment.BottomCenter // ✅ keep bottom centered
//    ) {
//        Row(
//            horizontalArrangement = Arrangement.Center,
//            modifier = Modifier.padding(bottom = dotPadding)
//        ) {
//            repeat(imageCount) { index ->
//                val isSelected = pagerState.currentPage == index
//
//                // Progress animation for the active indicator
//                val progress = remember { Animatable(0f) }
//                if (isSelected) {
//                    LaunchedEffect(pagerState.currentPage) {
//                        progress.snapTo(0f)
//                        progress.animateTo(
//                            targetValue = 1f,
//                            animationSpec = tween(
//                                durationMillis = indicatorDuration.toInt(),
//                                easing = LinearEasing
//                            )
//                        )
//                    }
//                } else {
//                    LaunchedEffect(pagerState.currentPage) {
//                        progress.snapTo(0f)
//                    }
//                }
//
//                // Width: active = 2× inactive
//                val targetWidth = if (isSelected) dotSize * 5 else dotSize * 3
//                val animatedWidth = animateDpAsState(targetValue = targetWidth)
//                val animatedHeight = animateDpAsState(targetValue = dotSize)
//
//                Box(
//                    modifier = Modifier
//                        .padding(horizontal = dotPadding)
//                        .width(animatedWidth.value)
//                        .height(animatedHeight.value)
//                        .clip(RoundedCornerShape(50))
//                        .background(unselectedDotColor)
//                        .clickable {
//                            coroutineScope.launch {
//                                pagerState.animateScrollToPage(index)
//                            }
//                        }
//                ) {
//                    if (isSelected) {
//                        Box(
//                            modifier = Modifier
//                                .fillMaxHeight()
//                                .fillMaxWidth(progress.value) // animate line growth
//                                .clip(RoundedCornerShape(50))
//                                .background(selectedDotColor)
//                        )
//                    }
//                }
//            }
//        }
//    }
//}

