package com.example.qrakon.components.restaurants

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.zIndex
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import com.example.qrakon.R
import com.example.qrakon.ui.theme.customColors
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.abs

// Data classes for offer items
data class OfferItem(
    val discountText: String,
    val couponCode: String,
    val imageResId: Int
)

// Sample data - replace with your actual images
val offerItems = listOf(
    OfferItem("Flat 50% off", "NO CODE REQUIRED | ON SELECT ITEM...", R.drawable.ic_offer_rest_1),
    OfferItem("DEAL OF DAY", "Items at ₹329 | ON SELECT ITEMS", R.drawable.ic_offer_rest_2),
    OfferItem("Flat £150 off", "USE AXISREWARDS | ABOVE £500", R.drawable.ic_offer_rest_3),
    OfferItem("VISA", "10% off upto ₹75 | USE VISAPLATINUMDC | ABOVE ₹300", R.drawable.ic_offer_rest_4),
    OfferItem("Flat ₹125 off", "USE IDFCDC125 | ABOVE ₹499", R.drawable.ic_offer_rest_5)
)

// Video Player Composable with completion listener and play/pause control
@androidx.annotation.OptIn(UnstableApi::class)
@OptIn(UnstableApi::class)
@Composable
fun VideoPlayer(
    videoResId: Int,
    modifier: Modifier = Modifier,
    autoPlay: Boolean = true,
    loop: Boolean = false,
    muted: Boolean = true,
    isPlaying: Boolean = true,
    onVideoComplete: (() -> Unit)? = null,
    onVideoReady: (() -> Unit)? = null
) {
    val context = LocalContext.current

    val exoPlayer = remember(videoResId) {
        ExoPlayer.Builder(context).build().apply {
            val mediaItem = MediaItem.fromUri("android.resource://${context.packageName}/$videoResId")
            setMediaItem(mediaItem)
            prepare()
            if (autoPlay && isPlaying) play()
            volume = if (muted) 0f else 1f

            addListener(object : Player.Listener {
                override fun onPlaybackStateChanged(playbackState: Int) {
                    when (playbackState) {
                        Player.STATE_READY -> {
                            onVideoReady?.invoke()
                        }
                        Player.STATE_ENDED -> {
                            onVideoComplete?.invoke()
                        }
                    }
                }
            })
        }
    }

    // Handle play/pause state changes
    LaunchedEffect(isPlaying, videoResId) {
        if (isPlaying) {
            exoPlayer.playWhenReady = true
            exoPlayer.play()
        } else {
            exoPlayer.pause()
        }
    }

    // Handle loop mode
    LaunchedEffect(loop) {
        exoPlayer.repeatMode = if (loop) Player.REPEAT_MODE_ONE else Player.REPEAT_MODE_OFF
    }

    // Handle mute/unmute
    LaunchedEffect(muted) {
        exoPlayer.volume = if (muted) 0f else 1f
    }

    DisposableEffect(exoPlayer) {
        onDispose {
            exoPlayer.release()
        }
    }

    AndroidView(
        factory = { ctx ->
            PlayerView(ctx).apply {
                player = exoPlayer
                useController = false
                resizeMode = AspectRatioFrameLayout.RESIZE_MODE_ZOOM
            }
        },
        modifier = modifier
    )
}

// Video Slideshow Composable with swipe gestures and auto-play next (only when video finishes)
@Composable
fun VideoSlideshow(
    videoUrls: List<Int>?,
    modifier: Modifier = Modifier
) {
    if (videoUrls.isNullOrEmpty()) return

    var currentVideoIndex by remember { mutableIntStateOf(0) }
    var isMuted by remember { mutableStateOf(true) }
    var isPlaying by remember { mutableStateOf(true) }

    val scope = rememberCoroutineScope()

    fun nextVideo() {
        currentVideoIndex =
            if (currentVideoIndex >= videoUrls.lastIndex) 0
            else currentVideoIndex + 1
    }

    fun previousVideo() {
        currentVideoIndex =
            if (currentVideoIndex <= 0) videoUrls.lastIndex
            else currentVideoIndex - 1
    }

    Box(
        modifier = modifier
    ) {

        // Swipe Area
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(420.dp)

                // CONSUME VIDEO CLICKS
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) {
                    // No Action
                }

                .pointerInput(currentVideoIndex) {

                    var totalDrag = 0f

                    detectHorizontalDragGestures(

                        onDragStart = {
                            totalDrag = 0f
                        },

                        onHorizontalDrag = { change, dragAmount ->
                            change.consume()
                            totalDrag += dragAmount
                        },

                        onDragEnd = {

                            when {
                                totalDrag > 150f -> {
                                    previousVideo()
                                }

                                totalDrag < -150f -> {
                                    nextVideo()
                                }
                            }
                        }
                    )
                }
        ) {

            // IMPORTANT KEY
            key(currentVideoIndex) {

                VideoPlayer(
                    videoResId = videoUrls[currentVideoIndex],
                    modifier = Modifier.fillMaxSize(),
                    autoPlay = true,
                    loop = false,
                    muted = isMuted,
                    isPlaying = isPlaying,

                    onVideoComplete = {
                        scope.launch {
                            delay(200)
                            nextVideo()
                        }
                    }
                )
            }
        }

        // Indicators
        if (videoUrls.size > 1) {

            Row(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 12.dp)
                    .background(
                        Color.Black.copy(alpha = 0.5f),
                        RoundedCornerShape(20.dp)
                    )
                    .padding(horizontal = 12.dp, vertical = 8.dp),

                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                videoUrls.forEachIndexed { index, _ ->

                    val active = index == currentVideoIndex

                    Box(
                        modifier = Modifier
                            .size(if (active) 8.dp else 6.dp)
                            .clip(CircleShape)
                            .background(
                                if (active)
                                    Color.White
                                else
                                    Color.White.copy(alpha = 0.4f)
                            )
                            .clickable {
                                currentVideoIndex = index
                            }
                    )
                }
            }
        }

        // Play Pause
//        Surface(
//            shape = CircleShape,
//            color = Color.Black.copy(alpha = 0.6f),
//            modifier = Modifier
//                .align(Alignment.Center)
//                .size(48.dp)
//                .clickable {
//                    isPlaying = !isPlaying
//                }
//        ) {
//
//            Icon(
//                painter = painterResource(
//                    if (isPlaying)
//                        R.drawable.baseline_pause_24
//                    else
//                        R.drawable.baseline_play_arrow_24
//                ),
//                contentDescription = null,
//                tint = Color.White,
//                modifier = Modifier.padding(12.dp)
//            )
//        }

        // Mute
//        Surface(
//            shape = CircleShape,
//            color = Color.Black.copy(alpha = 0.6f),
//            modifier = Modifier
//                .align(Alignment.TopEnd)
//                .padding(12.dp)
//                .size(32.dp)
//                .clickable {
//                    isMuted = !isMuted
//                }
//        ) {
//
//            Icon(
//                painter = painterResource(
//                    if (isMuted)
//                        R.drawable.baseline_volume_off_24
//                    else
//                        R.drawable.baseline_volume_up_24
//                ),
//                contentDescription = null,
//                tint = Color.White,
//                modifier = Modifier.padding(6.dp)
//            )
//        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RestaurantCard(
    item: TopRatedRestaurantItem,
    modifier: Modifier = Modifier,
    onItemClick: (TopRatedRestaurantItem) -> Unit = {},
    onInfoIconClick: (TopRatedRestaurantItem) -> Unit = {},
    onScheduleLaterClick: (TopRatedRestaurantItem) -> Unit = {}
) {
    val currentItem = item

    // State for bottom sheet
    var showScheduleSheet by remember { mutableStateOf(false) }
    var selectedDateIndex by remember { mutableStateOf(0) }
    var selectedTimeIndex by remember { mutableStateOf(3) }

    // Check if accepting orders banner should be shown (priority over video)
    val showAcceptingOrdersBanner = currentItem.acceptingOrders != null
    // Only show video if accepting orders is null
    val showVideo = !currentItem.videoUrls.isNullOrEmpty() && !showAcceptingOrdersBanner

    var paddingTopCard = 0.dp
    if (showAcceptingOrdersBanner) {
        if (currentItem.acceptingOrders == true) {
            paddingTopCard = 55.dp
        } else if (currentItem.acceptingOrders == false) {
            paddingTopCard = 55.dp
        }
    } else {
        paddingTopCard = 5.dp
    }

    // Box for overlapping effect (ribbon/sticker style)
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.customColors.blackHeader)
    ) {
        // Card
        Card(
            modifier = modifier
                .fillMaxWidth()
                .clickable { onItemClick(currentItem) }
                .padding(top = paddingTopCard, bottom = 15.dp, start = 12.dp, end = 12.dp),
            shape = RoundedCornerShape(30.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.customColors.background),
            ) {
                // ===== VIDEO SECTION AT TOP (only if no accepting orders banner) =====
                if (showVideo) {
                    VideoSlideshow(
                        videoUrls = currentItem.videoUrls,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
                    )
                }

                // ===== CONTENT SECTION =====
                Column(
                    modifier = Modifier.padding(15.dp)
                ) {
                    // Show accepting orders message when banner is present
                    if (showAcceptingOrdersBanner) {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                        ) {
                            if (currentItem.acceptingOrders == true) {
                                Text(
                                    text = currentItem.acceptingOrdersMsg ?: "",
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.customColors.greenTitle
                                )
                            } else if (currentItem.acceptingOrders == false) {
                                Text(
                                    text = currentItem.acceptingOrdersMsg ?: "",
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.customColors.buttonRed
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))
                        HorizontalDivider(
                            color =
                         Color.LightGray,
                            thickness = 0.5.dp
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                    }

                    // Restaurant Seal
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_high_protein),
                            contentDescription = "icon",
                            modifier = Modifier.size(15.dp),
                        )
                        Spacer(modifier = Modifier.width(3.dp))
                        Text(
                            text = "High Protein",
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.customColors.darkAccent2
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        Image(
                            painter = painterResource(id = R.drawable.hufko_seal),
                            contentDescription = "icon",
                            modifier = Modifier.size(15.dp),
                        )
                        Spacer(modifier = Modifier.width(2.dp))
                        Text(
                            text = "Hufko Seal",
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.customColors.darkAccent2
                        )
                    }

                    // Restaurant Header
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = currentItem.restaurantName?.limitChars(20) ?: "",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Image(
                                painter = painterResource(id = R.drawable.info_exclamation_mark_icon),
                                contentDescription = "info icon",
                                modifier = Modifier
                                    .size(18.dp)
                                    .clickable { onInfoIconClick(currentItem) },
                            )
                        }

                        Surface(
                            shape = RoundedCornerShape(20.dp),
                            color = MaterialTheme.customColors.success,
                            modifier = Modifier.size(width = 65.dp, height = 30.dp)
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(horizontal = 4.dp)
                            ) {
                                Text(
                                    text = formatRating(currentItem.rating),
                                    fontSize = 17.sp,
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold
                                )
                                Spacer(modifier = Modifier.width(2.dp))
                                Text(
                                    text = "★",
                                    fontSize = 18.sp,
                                    color = Color.White,
                                    modifier = Modifier.padding(bottom = 5.dp)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    // Distance & Address
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(5.dp)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.baseline_location_pin_24),
                                contentDescription = "icon",
                                modifier = Modifier.size(18.dp),
                                colorFilter = ColorFilter.tint(MaterialTheme.customColors.success)
                            )
                            Text(
                                text = currentItem.distance ?: "",
                                fontSize = 14.sp,
                                color = Color.DarkGray,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "•",
                                fontSize = 20.sp,
                                color = Color.DarkGray,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = currentItem.address ?: "",
                                fontSize = 14.sp,
                                color = Color.DarkGray,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                fontWeight = FontWeight.Bold
                            )
                            Box(
                                modifier = Modifier
                                    .offset(x = (-5).dp)
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.outline_arrow_drop_down_24),
                                    contentDescription = "Dropdown arrow",
                                    modifier = Modifier.size(30.dp),
                                    tint = Color.DarkGray
                                )
                            }
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "${getRandomRatings()}K+ ratings",
                                fontSize = 11.sp,
                                color = Color.DarkGray
                            )
                        }
                    }

                    // Delivery Time & Schedule
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(5.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.outline_flash_on_24),
                            contentDescription = "icon",
                            modifier = Modifier.size(18.dp)
                                .padding(top = 3.dp),
                            colorFilter = ColorFilter.tint(MaterialTheme.customColors.success)
                        )
                        Text(
                            text = currentItem.deliveryTime ?: "",
                            fontSize = 14.sp,
                            color = Color.DarkGray,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "•",
                            fontSize = 20.sp,
                            color = Color.DarkGray,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Schedule for later",
                            fontSize = 14.sp,
                            color = Color.DarkGray,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.clickable {
                                onScheduleLaterClick(currentItem)
                                showScheduleSheet = true
                            }
                        )
                        Icon(
                            painter = painterResource(id = R.drawable.outline_keyboard_arrow_down_24),
                            contentDescription = "Dropdown arrow",
                            modifier = Modifier
                                .size(25.dp)
                                .padding(top = 1.dp)
                                .clickable {
                                    onScheduleLaterClick(currentItem)
                                    showScheduleSheet = true
                                },
                            tint = Color.DarkGray
                        )
                    }

                    Spacer(modifier = Modifier.height(5.dp))

                    HorizontalDivider(
                        color = Color.LightGray,
                        thickness = 0.5.dp
                    )

                    Spacer(modifier = Modifier.height(5.dp))

                    // Offer Section
                    OfferSection(
                        offerItems = offerItems,
                        modifier = Modifier.fillMaxWidth(),
                        autoRotate = true
                    )
                }
            }
        }

        // Banner on top - Show when accepting orders is not null
        if (showAcceptingOrdersBanner) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.TopCenter)
                    .zIndex(1f),
                horizontalArrangement = Arrangement.Center
            ) {
                if (currentItem.acceptingOrders == true) {
                    Image(
                        painter = painterResource(id = R.drawable.accepting_orders),
                        contentDescription = "accepting orders banner",
                        modifier = Modifier
                            .height(70.dp)
                            .width(170.dp)
                            .offset(y = 2.dp)
                    )
                } else {
                    Image(
                        painter = painterResource(id = R.drawable.not_accepting_orders),
                        contentDescription = "not accepting orders banner",
                        modifier = Modifier
                            .height(70.dp)
                            .width(170.dp)
                            .offset(y = 2.dp)
                    )
                }
            }
        }
    }

    // Bottom Sheet for Schedule Later
    if (showScheduleSheet) {
        ModalBottomSheet(
            onDismissRequest = { showScheduleSheet = false },
            containerColor = Color.Transparent,
            shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
            dragHandle = {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .width(0.dp)
                            .height(0.dp)
                            .background(
                                color = Color.Transparent,
                                shape = RoundedCornerShape(2.dp)
                            )
                    )
                }
            }
        ) {
            ScheduleLaterRestOrder(
                selectedDateIndex = selectedDateIndex,
                selectedTimeIndex = selectedTimeIndex,
                onDateSelected = { selectedDateIndex = it },
                onTimeSelected = { selectedTimeIndex = it },
                onConfirm = {
                    showScheduleSheet = false
                    onScheduleLaterClick(currentItem)
                },
                onDismiss = { showScheduleSheet = false }
            )
        }
    }
}

//@OptIn(ExperimentalMaterial3Api::class)
//@RequiresApi(Build.VERSION_CODES.O)
//@Composable
//fun RestaurantCard(
//    item: TopRatedRestaurantItem,
//    modifier: Modifier = Modifier,
//    onItemClick: (TopRatedRestaurantItem) -> Unit = {},
//    onInfoIconClick: (TopRatedRestaurantItem) -> Unit = {},
//    onScheduleLaterClick: (TopRatedRestaurantItem) -> Unit = {}
//) {
//
//    val currentItem = item
//
//    // Bottom sheet state
//    var showScheduleSheet by remember { mutableStateOf(false) }
//    var selectedDateIndex by remember { mutableStateOf(0) }
//    var selectedTimeIndex by remember { mutableStateOf(3) }
//
//    var paddingTopCard = 0.dp
//    if (currentItem.acceptingOrders != null) {
//        if (currentItem.acceptingOrders == true) {
//            paddingTopCard = 55.dp
//        } else if (currentItem.acceptingOrders == false) {
//            paddingTopCard = 55.dp
//        }
//    } else {
//        paddingTopCard = 5.dp
//    }
//
//    var paddingTopStart = 0.dp
//    var paddingTopEnd = 0.dp
//    if (currentItem.videoUrls != null) {
//        paddingTopStart = 0.dp
//        paddingTopEnd = 0.dp
//    } else {
//        paddingTopStart = 30.dp
//        paddingTopEnd = 30.dp
//    }
//
//    Box(
//        modifier = Modifier
//            .fillMaxWidth()
//            .background(MaterialTheme.customColors.blackHeader)
//    ) {
//
//        Card(
//            modifier = modifier
//                .fillMaxWidth()
//                .clickable { onItemClick(currentItem) },
////            .padding(top = paddingTopCard, bottom = 15.dp, start = 12.dp, end = 12.dp),
//            shape = RoundedCornerShape(30.dp),
//
//            elevation = CardDefaults.cardElevation(
//                defaultElevation = 4.dp
//            ),
//
//            colors = CardDefaults.cardColors(
//                containerColor = Color.White
//            )
//        ) {
//
//            Column(
//                modifier = Modifier
//                    .fillMaxWidth()
////                    .padding(15.dp)
//                    .background(MaterialTheme.customColors.background)
//            ) {
//
//                // VIDEO SECTION
//                if (!currentItem.videoUrls.isNullOrEmpty()) {
//
//                    VideoSlideshow(
//                        videoUrls = currentItem.videoUrls,
//                        modifier = Modifier.fillMaxWidth()
//                    )
//                }
//
//                // CONTENT SECTION
//                Column(
//                    modifier = Modifier
//                        .background(MaterialTheme.customColors.blackHeader)
//                        .padding(
//                            top = paddingTopCard,
//                            bottom = 15.dp,
//                            start = 12.dp,
//                            end = 12.dp
//                        )
//                        .clip(
//                            RoundedCornerShape(
//                                topStart = paddingTopStart,
//                                topEnd = paddingTopEnd,
//                                bottomStart = 30.dp,
//                                bottomEnd = 30.dp
//                            )
//                        )
//                        .background(Color.White)
//                        .padding(
//                            start = 15.dp,
//                            end = 15.dp,
//                            bottom = 15.dp,
//                            top = if (currentItem.acceptingOrders != null) 40.dp else 15.dp
//                        )
//                ) {
//
//
//                    // ACCEPTING ORDERS MESSAGE
//                    if (currentItem.acceptingOrders != null) {
//
//                        Text(
//                            text = currentItem.acceptingOrdersMsg ?: "",
//
//                            fontSize = 15.sp,
//
//                            fontWeight = FontWeight.Bold,
//
//                            color = if (currentItem.acceptingOrders == true)
//                                MaterialTheme.customColors.greenTitle
//                            else
//                                MaterialTheme.customColors.buttonRed
//                        )
//
//                        Spacer(modifier = Modifier.height(10.dp))
//
//                        HorizontalDivider(
//                            color = Color.LightGray,
//                            thickness = 0.5.dp
//                        )
//
//                        Spacer(modifier = Modifier.height(10.dp))
//                    }
//
//                    // RESTAURANT SEALS
//                    Row(
//                        modifier = Modifier.fillMaxWidth(),
//                        verticalAlignment = Alignment.CenterVertically
//                    ) {
//
//                        Image(
//                            painter = painterResource(id = R.drawable.ic_high_protein),
//                            contentDescription = "icon",
//                            modifier = Modifier.size(15.dp)
//                        )
//
//                        Spacer(modifier = Modifier.width(3.dp))
//
//                        Text(
//                            text = "High Protein",
//                            fontSize = 17.sp,
//                            fontWeight = FontWeight.Bold,
//                            color = MaterialTheme.customColors.darkAccent2
//                        )
//
//                        Spacer(modifier = Modifier.width(5.dp))
//
//                        Image(
//                            painter = painterResource(id = R.drawable.hufko_seal),
//                            contentDescription = "icon",
//                            modifier = Modifier.size(15.dp)
//                        )
//
//                        Spacer(modifier = Modifier.width(2.dp))
//
//                        Text(
//                            text = "Hufko Seal",
//                            fontSize = 17.sp,
//                            fontWeight = FontWeight.Bold,
//                            color = MaterialTheme.customColors.darkAccent2
//                        )
//                    }
//
//                    Spacer(modifier = Modifier.height(5.dp))
//
//                    // RESTAURANT HEADER
//                    Row(
//                        modifier = Modifier.fillMaxWidth(),
//                        horizontalArrangement = Arrangement.SpaceBetween,
//                        verticalAlignment = Alignment.CenterVertically
//                    ) {
//
//                        Row(
//                            verticalAlignment = Alignment.CenterVertically
//                        ) {
//
//                            Text(
//                                text = currentItem.restaurantName?.limitChars(20) ?: "",
//                                fontSize = 20.sp,
//                                fontWeight = FontWeight.Bold,
//                                color = Color.Black,
//                                maxLines = 1,
//                                overflow = TextOverflow.Ellipsis
//                            )
//
//                            Spacer(modifier = Modifier.width(4.dp))
//
//                            Image(
//                                painter = painterResource(
//                                    id = R.drawable.info_exclamation_mark_icon
//                                ),
//
//                                contentDescription = "info icon",
//
//                                modifier = Modifier
//                                    .size(18.dp)
//                                    .clickable {
//                                        onInfoIconClick(currentItem)
//                                    }
//                            )
//                        }
//
//                        Surface(
//                            shape = RoundedCornerShape(20.dp),
//                            color = MaterialTheme.customColors.success,
//
//                            modifier = Modifier.size(
//                                width = 65.dp,
//                                height = 30.dp
//                            )
//                        ) {
//
//                            Row(
//                                horizontalArrangement = Arrangement.Center,
//                                verticalAlignment = Alignment.CenterVertically,
//                                modifier = Modifier.padding(horizontal = 4.dp)
//                            ) {
//
//                                Text(
//                                    text = formatRating(currentItem.rating),
//                                    fontSize = 17.sp,
//                                    color = Color.White,
//                                    fontWeight = FontWeight.Bold
//                                )
//
//                                Spacer(modifier = Modifier.width(2.dp))
//
//                                Text(
//                                    text = "★",
//                                    fontSize = 18.sp,
//                                    color = Color.White,
//                                    modifier = Modifier.padding(bottom = 5.dp)
//                                )
//                            }
//                        }
//                    }
//
//                    Spacer(modifier = Modifier.height(4.dp))
//
//                    // DISTANCE & ADDRESS
//                    Row(
//                        modifier = Modifier.fillMaxWidth(),
//                        horizontalArrangement = Arrangement.SpaceBetween,
//                        verticalAlignment = Alignment.CenterVertically
//                    ) {
//
//                        Row(
//                            verticalAlignment = Alignment.CenterVertically,
//                            horizontalArrangement = Arrangement.spacedBy(5.dp)
//                        ) {
//
//                            Image(
//                                painter = painterResource(
//                                    id = R.drawable.baseline_location_pin_24
//                                ),
//
//                                contentDescription = "icon",
//
//                                modifier = Modifier.size(18.dp),
//
//                                colorFilter = ColorFilter.tint(
//                                    MaterialTheme.customColors.success
//                                )
//                            )
//
//                            Text(
//                                text = currentItem.distance ?: "",
//                                fontSize = 14.sp,
//                                color = Color.DarkGray,
//                                fontWeight = FontWeight.Bold
//                            )
//
//                            Text(
//                                text = "•",
//                                fontSize = 20.sp,
//                                color = Color.DarkGray,
//                                fontWeight = FontWeight.Bold
//                            )
//
//                            Text(
//                                text = currentItem.address ?: "",
//                                fontSize = 14.sp,
//                                color = Color.DarkGray,
//                                maxLines = 1,
//                                overflow = TextOverflow.Ellipsis,
//                                fontWeight = FontWeight.Bold
//                            )
//
//                            Box(
//                                modifier = Modifier.offset(x = (-5).dp)
//                            ) {
//
//                                Icon(
//                                    painter = painterResource(
//                                        id = R.drawable.outline_arrow_drop_down_24
//                                    ),
//
//                                    contentDescription = "Dropdown arrow",
//
//                                    modifier = Modifier.size(30.dp),
//
//                                    tint = Color.DarkGray
//                                )
//                            }
//                        }
//
//                        Row(
//                            verticalAlignment = Alignment.CenterVertically
//                        ) {
//
//                            Text(
//                                text = "${getRandomRatings()}K+ ratings",
//                                fontSize = 11.sp,
//                                color = Color.DarkGray
//                            )
//                        }
//                    }
//
//                    // DELIVERY TIME
//                    Row(
//                        modifier = Modifier.fillMaxWidth(),
//                        horizontalArrangement = Arrangement.spacedBy(5.dp)
//                    ) {
//
//                        Image(
//                            painter = painterResource(
//                                id = R.drawable.outline_flash_on_24
//                            ),
//
//                            contentDescription = "icon",
//
//                            modifier = Modifier
//                                .size(18.dp)
//                                .padding(top = 3.dp),
//
//                            colorFilter = ColorFilter.tint(
//                                MaterialTheme.customColors.success
//                            )
//                        )
//
//                        Text(
//                            text = currentItem.deliveryTime ?: "",
//                            fontSize = 14.sp,
//                            color = Color.DarkGray,
//                            fontWeight = FontWeight.Bold
//                        )
//
//                        Text(
//                            text = "•",
//                            fontSize = 20.sp,
//                            color = Color.DarkGray,
//                            fontWeight = FontWeight.Bold
//                        )
//
//                        Text(
//                            text = "Schedule for later",
//                            fontSize = 14.sp,
//                            color = Color.DarkGray,
//                            fontWeight = FontWeight.Bold,
//
//                            modifier = Modifier.clickable {
//
//                                onScheduleLaterClick(currentItem)
//                                showScheduleSheet = true
//                            }
//                        )
//
//                        Icon(
//                            painter = painterResource(
//                                id = R.drawable.outline_keyboard_arrow_down_24
//                            ),
//
//                            contentDescription = "Dropdown arrow",
//
//                            modifier = Modifier
//                                .size(25.dp)
//                                .padding(top = 1.dp)
//                                .clickable {
//
//                                    onScheduleLaterClick(currentItem)
//                                    showScheduleSheet = true
//                                },
//
//                            tint = Color.DarkGray
//                        )
//                    }
//
//                    Spacer(modifier = Modifier.height(5.dp))
//
//                    HorizontalDivider(
//                        color = Color.LightGray,
//                        thickness = 0.5.dp
//                    )
//
//                    Spacer(modifier = Modifier.height(5.dp))
//
//                    // OFFER SECTION
//                    OfferSection(
//                        offerItems = offerItems,
//                        modifier = Modifier.fillMaxWidth(),
//                        autoRotate = true
//                    )
//                }
//            }
//        }
//    }
//
//    // BOTTOM SHEET
//    if (showScheduleSheet) {
//
//        ModalBottomSheet(
//            onDismissRequest = {
//                showScheduleSheet = false
//            },
//
//            containerColor = Color.Transparent,
//
//            shape = RoundedCornerShape(
//                topStart = 20.dp,
//                topEnd = 20.dp
//            ),
//
//            dragHandle = {
//
//                Box(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(0.dp),
//
//                    contentAlignment = Alignment.Center
//                ) {
//
//                    Box(
//                        modifier = Modifier
//                            .width(0.dp)
//                            .height(0.dp)
//                            .background(
//                                color = Color.Transparent,
//                                shape = RoundedCornerShape(2.dp)
//                            )
//                    )
//                }
//            }
//        ) {
//
//            ScheduleLaterRestOrder(
//                selectedDateIndex = selectedDateIndex,
//                selectedTimeIndex = selectedTimeIndex,
//
//                onDateSelected = {
//                    selectedDateIndex = it
//                },
//
//                onTimeSelected = {
//                    selectedTimeIndex = it
//                },
//
//                onConfirm = {
//
//                    showScheduleSheet = false
//                    onScheduleLaterClick(currentItem)
//                },
//
//                onDismiss = {
//                    showScheduleSheet = false
//                }
//            )
//        }
//    }
//}

// OfferSection with dot indicators
@Composable
fun OfferSection(
    offerItems: List<OfferItem>,
    modifier: Modifier = Modifier,
    autoRotate: Boolean = true,
    autoRotateIntervalMs: Long = 3000
) {
    var offerIndex by remember { mutableIntStateOf(0) }

    // Auto-rotation effect - ONLY for offers
    if (autoRotate && offerItems.isNotEmpty()) {
        LaunchedEffect(Unit) {
            while (true) {
                delay(autoRotateIntervalMs)
                offerIndex = (offerIndex + 1) % offerItems.size
            }
        }
    }

    if (offerItems.isEmpty()) return

    Surface(
        shape = RoundedCornerShape(8.dp),
        color = MaterialTheme.customColors.background,
        modifier = modifier
    ) {
        AnimatedContent(
            targetState = offerIndex,
            transitionSpec = {
                slideInHorizontally(
                    animationSpec = tween(500, easing = FastOutSlowInEasing),
                    initialOffsetX = { fullWidth -> fullWidth }
                ) togetherWith slideOutHorizontally(
                    animationSpec = tween(500, easing = FastOutSlowInEasing),
                    targetOffsetX = { fullWidth -> -fullWidth }
                )
            },
            label = "offerTransition"
        ) { index ->
            Row(
                modifier = Modifier.padding(start = 0.dp, top = 0.dp, end = 0.dp, bottom = 0.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = offerItems[index].imageResId),
                    contentDescription = "Offer Image",
                    modifier = Modifier
                        .size(48.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )

                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = offerItems[index].discountText,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.customColors.black
                    )
                    Text(
                        text = offerItems[index].couponCode,
                        fontSize = 15.sp,
                        color = MaterialTheme.customColors.gray,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Column(
                    modifier = Modifier.wrapContentWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    // Dot indicators for offers
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(top = 4.dp)
                    ) {
                        offerItems.forEachIndexed { dotIndex, _ ->
                            val isActive = dotIndex == index
                            val animatedSize by animateDpAsState(
                                targetValue = if (isActive) 8.dp else 6.dp,
                                animationSpec = tween(300, easing = FastOutSlowInEasing),
                                label = "dotSize"
                            )
                            val animatedColor by animateColorAsState(
                                targetValue = if (isActive)
                                    MaterialTheme.customColors.orangeButton
                                else
                                    Color.LightGray,
                                animationSpec = tween(300, easing = FastOutSlowInEasing),
                                label = "dotColor"
                            )

                            Box(
                                modifier = Modifier
                                    .padding(horizontal = 2.dp)
                                    .size(animatedSize)
                                    .background(
                                        color = animatedColor,
                                        shape = CircleShape
                                    )
                            )
                        }
                    }
                }
            }
        }
    }
}

// Helper function to safely format rating
internal fun formatRating(rating: Any?): String {
    return when (rating) {
        null -> "0.0"
        is String -> rating
        is Double -> String.format("%.1f", rating)
        is Float -> String.format("%.1f", rating)
        is Int -> rating.toString()
        else -> rating.toString()
    }
}

// Helper functions
fun getRandomRatings(): String {
    val ratings = listOf(298, 79, 299, 250)
    return ratings.random().toString()
}

private fun getDiscountText(item: TopRatedRestaurantItem): String {
    val price = item.price ?: "0"
    val discountAmount = item.discountAmount ?: "₹0"

    return when {
        item.discount == "ITEMS" -> "Items at ₹$price"
        item.discount?.contains("%") == true -> "${item.discount} off"
        else -> "Flat $discountAmount off"
    }
}

private fun getCouponCode(item: TopRatedRestaurantItem): String {
    val restaurantName = item.restaurantName ?: ""

    return when {
        item.discount == "ITEMS" -> "ON SELECT ITEMS"
        restaurantName == "Hunger Cure" -> "USE AXISREWARDS | ABOVE ₹500"
        restaurantName == "Amiche Pizza" -> "USE VISAPLATINUMIDC | ABOVE ₹300"
        else -> "USE CODE: ${restaurantName.uppercase().replace(" ", "")}"
    }
}

fun String.limitChars(maxChars: Int): String {
    return if (length > maxChars) take(maxChars) + "..." else this
}