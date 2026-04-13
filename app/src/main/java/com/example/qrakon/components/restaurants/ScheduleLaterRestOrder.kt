package com.example.qrakon.components.restaurants

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale
import kotlin.math.abs

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ScheduleLaterRestOrder(
    modifier: Modifier = Modifier,
    selectedDateIndex: Int = 0,
    selectedTimeIndex: Int = 0,
    onDateSelected: (Int) -> Unit = {},
    onTimeSelected: (Int) -> Unit = {},
    onConfirm: () -> Unit = {},
    onDismiss: () -> Unit = {}
) {

    // ✅ Dynamic Dates
    val today = LocalDate.now()
    val formatter = DateTimeFormatter.ofPattern("dd MMM", Locale.getDefault())
    val dayFormatter = DateTimeFormatter.ofPattern("EEEE", Locale.getDefault())

    val dates = listOf(
        today,
        today.plusDays(1),
        today.plusDays(2)
    ).mapIndexed { index, date ->
        Triple(
            date.format(formatter),
            when (index) {
                0 -> "Today"
                1 -> "Tomorrow"
                else -> date.format(dayFormatter)
            },
            false
        )
    }

    val timeSlots = listOf(
        "9:30 – 10 PM",
        "10 – 10:30 PM",
        "10:30 – 11 PM",
        "11 – 11:30 PM",
        "11:30 – 12 PM",
        "12 – 12:30 AM",
        "12:30 – 1 AM"
    )

    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    // 🔥 Infinite List Setup
    val infiniteListSize = Int.MAX_VALUE
    val middleIndex = infiniteListSize / 2
    val startIndex = middleIndex - (middleIndex % timeSlots.size) + selectedTimeIndex

    LaunchedEffect(Unit) {
        listState.scrollToItem(startIndex)
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 24.dp)
    ) {

        // Close Button
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            IconButton(
                onClick = onDismiss,
                modifier = Modifier
                    .size(40.dp)
                    .background(
                        color = Color.Black.copy(alpha = 0.6f),
                        shape = RoundedCornerShape(50)
                    )
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Close",
                    tint = Color.White
                )
            }
        }

        // Main Card
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
                .background(Color.White)
        ) {

            // Title
            Text(
                text = "Select your delivery time",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(20.dp)
            )

            HorizontalDivider()

            Spacer(modifier = Modifier.height(16.dp))

            // Date Tabs
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
//                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                dates.forEachIndexed { index, (date, label, _) ->
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .clickable { onDateSelected(index) },
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = date,
                            fontSize = 14.sp,
                            fontWeight = if (selectedDateIndex == index) FontWeight.SemiBold else FontWeight.Normal,
                            color = if (selectedDateIndex == index) Color.Black else Color.Gray
                        )

                        Spacer(modifier = Modifier.height(2.dp))

                        Text(
                            text = label,
                            fontSize = 12.sp,
                            color = Color.Gray
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Box(
                            modifier = Modifier
                                .height(2.dp)
                                .fillMaxWidth()
                                .background(
                                    if (selectedDateIndex == index) Color(0xFFE53935)
                                    else Color.LightGray
                                )
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // 🔥 Infinite Wheel Picker
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            ) {
                LazyColumn(
                    state = listState,
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    contentPadding = PaddingValues(vertical = 80.dp)
                ) {
                    items(infiniteListSize) { index ->

                        val actualIndex = index % timeSlots.size
                        val time = timeSlots[actualIndex]

                        val isCentered = remember(listState.layoutInfo) {
                            listState.layoutInfo.visibleItemsInfo.any { item ->
                                val center = listState.layoutInfo.viewportEndOffset / 2
                                val itemCenter = item.offset + item.size / 2
                                item.index == index &&
                                        abs(center - itemCenter) < item.size / 2
                            }
                        }

                        val scale by animateFloatAsState(
                            targetValue = if (isCentered) 1.15f else 0.9f,
                            animationSpec = tween(250),
                            label = "scale"
                        )

                        val alpha by animateFloatAsState(
                            targetValue = if (isCentered) 1f else 0.3f,
                            animationSpec = tween(250),
                            label = "alpha"
                        )

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 6.dp)
                                .clip(RoundedCornerShape(12.dp))  // Clip first
                                .background(
                                    if (isCentered) Color(0xFFF3F4F8)
                                    else Color.Transparent
                                )
                                .scale(scale)  // Then scale
                                .alpha(alpha)  // Then alpha
                                .clickable {
                                    coroutineScope.launch {
                                        listState.animateScrollToItem(index)
                                        onTimeSelected(actualIndex)
                                    }
                                }
                                .padding(vertical = 18.dp, horizontal = 16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = time,
                                fontSize = if (isCentered) 16.sp else 13.sp,
                                fontWeight = if (isCentered) FontWeight.Bold else FontWeight.Medium,
                                color = Color.Black,
                                modifier = Modifier
                                    .padding(horizontal = 16.dp)
                            )
                        }
                    }
                }
            }

            HorizontalDivider()

            Spacer(modifier = Modifier.height(12.dp))

            // Confirm Button
            Button(
                onClick = onConfirm,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .padding(horizontal = 16.dp),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE53935))
            ) {
                Text(
                    text = "Confirm",
                    color = Color.White,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true, backgroundColor = 0xFFF5F5F5)
@Composable
fun PreviewScheduleLaterRestOrder() {

    var selectedDate by remember { mutableStateOf(0) }
    var selectedTime by remember { mutableStateOf(3) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5)),
        contentAlignment = Alignment.BottomCenter
    ) {
        ScheduleLaterRestOrder(
            selectedDateIndex = selectedDate,
            selectedTimeIndex = selectedTime,
            onDateSelected = { selectedDate = it },
            onTimeSelected = { selectedTime = it },
            onConfirm = {},
            onDismiss = {}
        )
    }
}