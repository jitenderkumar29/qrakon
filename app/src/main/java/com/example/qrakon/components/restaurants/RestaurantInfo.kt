import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.qrakon.R
import com.example.qrakon.ui.theme.customColors

// Dummy data for photos - replace with your actual image resources
private val dummyPhotos = listOf(
    R.drawable.restaurant_image_special_chicken_hyderabadi_boneless_biryani_85,
    R.drawable.combo_bikkgane_biryani_91,
    R.drawable.restaurant_image_aloo_65_mini_74,
    R.drawable.restaurant_image_veg_dum_biryani_103,
    R.drawable.restaurant_image_veg_biryani_combo_111,
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RestaurantInfo(
    modifier: Modifier = Modifier,
    restaurantName: String = "The Pizza Theatre",
    cuisineInfo: String = "Pizza · Burger · Fast Food · ₹250 for one",
    address: String = "Main Surajkund Road, Dayal Bagh, Faridabad",
    openStatus: String = "Open now",
    closeStatus: String = "Closes 11:00 pm",
    serviceType: String = "Provides both delivery & dining",
    sinceYear: String = "2020",
    legalName: String = "AMPT FOODS PRIVATE LIMITED",
    gstNumber: String = "",
    isBikgane: Boolean = false,
    bikganeLegalName: String = "BIKKGANE BIRYANI LLP",
    bikganeGst: String = "0900000000X1Z3",
    fssaiLicense: String = "12722052000368",
    onBackPressed: () -> Unit = {},
    onSaveRestaurant: () -> Unit = {},
    onShareRestaurant: () -> Unit = {},
    onViewDiningPage: () -> Unit = {},
    onHideRestaurant: () -> Unit = {},
    onGoBackToMenu: () -> Unit = {}
) {
    var isExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
    ) {
        // Header Section
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = Color(0xFFF5F5F5),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 0.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Back Icon
                Icon(
                    painter = painterResource(id = R.drawable.baseline_arrow_back_ios_24),
                    contentDescription = "Back",
                    tint = Color.Black,
                    modifier = Modifier
                        .size(24.dp)
                        .clickableWithoutRipple { onBackPressed() }
                )

                // Save and Share Icons
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.outline_bookmark_24),
                        contentDescription = "Save",
                        tint = Color.Black,
                        modifier = Modifier
                            .size(24.dp)
                            .clickableWithoutRipple { onSaveRestaurant() }
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.outline_share_24),
                        contentDescription = "Share",
                        tint = Color.Black,
                        modifier = Modifier
                            .size(24.dp)
                            .clickableWithoutRipple { onShareRestaurant() }
                    )
                }
            }
        }

        // Scrollable Content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            // Main Content
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    // Restaurant Name
                    Text(
                        text = if (!isBikgane) restaurantName else bikganeLegalName.split(" ").take(2)
                            .joinToString(" "),
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    // Cuisine & Price Info
                    Text(
                        text = if (!isBikgane) cuisineInfo else "Biryani · Fast Food · ₹300 for one",
                        fontSize = 14.sp,
                        color = Color.DarkGray
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Address
                    Text(
                        text = if (!isBikgane) address else "Somewhere in Faridabad, Near Metro Station",
                        fontSize = 13.sp,
                        color = Color.DarkGray,
                        lineHeight = 18.sp
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Dining Page Link with Location Icon
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
//                        Box(
//                            modifier = Modifier
//                                .size(36.dp)
//                                .clip(RoundedCornerShape(20.dp))
//                                .background(Color.White)
//                                .border(
//                                    1.dp,
//                                    MaterialTheme.customColors.lightGray,
//                                    RoundedCornerShape(20.dp)
//                                ),
//                            contentAlignment = Alignment.Center
//                        ) {
//                            Icon(
//                                painter = painterResource(id = R.drawable.ic_call),
//                                contentDescription = "Call",
//                                modifier = Modifier.size(18.dp),
//                                tint = Color.Unspecified
//                            )
//                        }
//                        Spacer(modifier = Modifier.width(8.dp))
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(RoundedCornerShape(20.dp))
                                .background(Color.White)
                                .border(
                                    1.dp,
                                    MaterialTheme.customColors.lightGray,
                                    RoundedCornerShape(20.dp)
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_share_24),
                                contentDescription = "Share",
                                modifier = Modifier.size(18.dp),
                                tint = Color.Unspecified
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))

                        Row(
                            modifier = Modifier
                                .clip(RoundedCornerShape(20.dp))
                                .background(Color.White)
                                .border(
                                    1.dp,
                                    MaterialTheme.customColors.lightGray,
                                    RoundedCornerShape(20.dp)
                                )
                                .clickableWithoutRipple { onViewDiningPage() }
                                .padding(horizontal = 12.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_dinning_24),
                                contentDescription = "Location",
                                modifier = Modifier.size(18.dp),
                                tint = Color.Unspecified
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "View dining page",
                                fontSize = 14.sp,
                                color = MaterialTheme.customColors.black,
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Divider
                    HorizontalDivider(color = Color(0xFFE0E0E0), thickness = 1.dp)

                    Spacer(modifier = Modifier.height(8.dp))
                    // Open Status Row with Clock Icon
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { isExpanded = !isExpanded } // toggle
                    ) {
                        Icon(
                            imageVector = Icons.Default.AccessTime,
                            contentDescription = "Open Status",
                            tint = MaterialTheme.customColors.gray,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))

                        Text(
                            text = openStatus,
                            fontSize = 14.sp,
                            color = MaterialTheme.customColors.success,
                            fontWeight = FontWeight.Medium
                        )

                        Spacer(modifier = Modifier.width(2.dp))

                        Text(
                            text = "•",
                            fontSize = 14.sp,
                            color = Color(0xFF2E7D32),
                            fontWeight = FontWeight.Medium
                        )

                        Spacer(modifier = Modifier.width(2.dp))

                        Text(
                            text = closeStatus,
                            fontSize = 14.sp,
                            color = MaterialTheme.customColors.buttonRed,
                            fontWeight = FontWeight.Medium
                        )

                        Spacer(modifier = Modifier.weight(1f))

                        Icon(
                            painter = painterResource(R.drawable.outline_keyboard_arrow_down_24),
                            contentDescription = null,
                            modifier = Modifier
                                .size(30.dp)
                                .rotate(if (isExpanded) 180f else 0f), // rotate arrow
                            tint = MaterialTheme.customColors.darkGray
                        )
                    }


                    AnimatedVisibility(
                        visible = isExpanded,
                        enter = expandVertically(),
                        exit = shrinkVertically()
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 28.dp, top = 8.dp, end = 8.dp)
                        ) {

                            val timings = listOf(
                                "Monday" to listOf("12:00 am - 1:00 am", "11:30 am - 11:59 pm"),
                                "Tuesday" to listOf("11:30 am - 11:59 pm"),
                                "Wednesday" to listOf("11:30 am - 11:59 pm"),
                                "Thursday" to listOf("11:30 am - 11:59 pm"),
                                "Friday" to listOf("11:30 am - 11:59 pm"),
                                "Saturday" to listOf("12:00 am - 1:00 am", "11:30 am - 11:59 pm"),
                                "Sunday" to listOf("12:00 am - 1:00 am", "11:30 am - 11:59 pm")
                            )

                            timings.forEach { (day, hoursList) ->

                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 6.dp),
                                    verticalAlignment = Alignment.Top
                                ) {

                                    // LEFT: Day
                                    Text(
                                        text = day,
                                        fontSize = 13.sp,
                                        color = Color.Gray,
                                        modifier = Modifier.width(90.dp) // 👈 fixed width for alignment
                                    )

                                    // RIGHT: Timings (can be multiple lines)
                                    Column {
                                        hoursList.forEach { time ->
                                            Text(
                                                text = time,
                                                fontSize = 13.sp,
                                                color = Color.DarkGray
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    HorizontalDivider(color = Color(0xFFE0E0E0), thickness = 1.dp)

                    Spacer(modifier = Modifier.height(8.dp))
                    // Open Status Row with Clock Icon


                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(0.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_delivery),
                            contentDescription = null,
                            modifier = Modifier.size(20.dp),
                            tint = Color.Unspecified
                        )

                        Spacer(modifier = Modifier.width(10.dp))

                        Column(
                            modifier = Modifier.weight(1f) // ✅ takes remaining space properly
                        ) {
                            Text(
                                text = "This is a delivery-only kitchen",
                                fontSize = 14.sp,
                                color = MaterialTheme.customColors.darkGray,
                                fontWeight = FontWeight.Medium
                            )

                            Spacer(modifier = Modifier.height(2.dp))

                            Text(
                                text = "There are multiple brands delivering from this kitchen",
                                fontSize = 12.sp,
                                color = MaterialTheme.customColors.darkGray,
                                fontWeight = FontWeight.Normal
                            )
                        }
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowRight, // ✅ use Right arrow like UI
                            contentDescription = null,
                            modifier = Modifier.size(32.dp),
                            tint = MaterialTheme.customColors.darkGray
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Divider
                    HorizontalDivider(color = Color(0xFFE0E0E0), thickness = 1.dp)

                    Spacer(modifier = Modifier.height(8.dp))
                    // Service Type Row with Store Icon
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(
                            imageVector = Icons.Default.Store,
                            contentDescription = "Service Type",
                            tint = MaterialTheme.customColors.gray,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = serviceType,
                            fontSize = 14.sp,
                            color = Color.DarkGray
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))

                    // Divider
                    HorizontalDivider(color = Color(0xFFE0E0E0), thickness = 1.dp)

                    Spacer(modifier = Modifier.height(8.dp))

                    // Photos Section
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_photo),
                            contentDescription = "Photos",
                            tint = Color.Unspecified,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Photos",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.Black
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(if (!isBikgane) dummyPhotos else dummyPhotos.take(3)) { photoRes ->
                            Box(
                                modifier = Modifier
                                    .size(120.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(Color(0xFFF0F0F0))
                            ) {
                                Image(
                                    painter = painterResource(id = photoRes),
                                    contentDescription = "Restaurant photo",
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.Crop
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Live since info
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.outline_mobile_3_24),
                            contentDescription = "Live since",
                            tint = MaterialTheme.customColors.gray,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Live on Hufko since $sinceYear",
                            fontSize = 14.sp,
                            color = Color.DarkGray
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }

            // Hide restaurant option
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 0.dp),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.clickableWithoutRipple { onHideRestaurant() }
                    ) {
                        Text(
                            text = "Had a bad experience here?",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.Black
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.clickableWithoutRipple { onHideRestaurant() }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_hide),
                            contentDescription = "Hide",
                            tint = Color.Unspecified,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Hide this restaurant",
                            fontSize = 13.sp,
                            color = Color.Black,
                            modifier = Modifier.padding(start = 0.dp)
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Icon(
                            painter = painterResource(R.drawable.outline_arrow_forward_ios_24),
                            contentDescription = null,
                            modifier = Modifier.size(18.dp),
                            tint = MaterialTheme.customColors.darkGray
                        )
                    }
                }
            }

            // Legal Info Column
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                Spacer(modifier = Modifier.height(8.dp))
                // Legal Info
                Text(
                    text = "Legal Name",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.DarkGray
                )
                Text(
                    text = if (!isBikgane) legalName else bikganeLegalName,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "GST Number",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.DarkGray
                )
                Text(
                    text = if (!isBikgane) (gstNumber.ifEmpty { "09XXXXXXXXXZ3" }) else bikganeGst,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Gray
                )

                // Additional fields for Bikgane Biryani
                if (isBikgane) {
                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "FSSAI Lic No",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.DarkGray
                    )
                    Text(
                        text = fssaiLicense,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Gray
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Terms of service and back to menu
                Row {
                    Text(
                        text = "Please review the terms of service for Hufko ",
                        fontSize = 14.sp,
                        color = Color.DarkGray,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = "here",
                        fontSize = 14.sp,
                        color = MaterialTheme.customColors.buttonRed,
                        fontWeight = FontWeight.Medium,
                        textDecoration = TextDecoration.Underline,
                        modifier = Modifier.clickableWithoutRipple { }
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Go Back to Menu Button
                Button(
                    onClick = { onGoBackToMenu() },
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFF3B5C)
                    ),
                    contentPadding = PaddingValues(vertical = 12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "Go back to menu",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.White
                    )
                }

                Spacer(modifier = Modifier.height(20.dp)) // Bottom padding for better scrolling experience
            }
        }
    }
}

// Helper extension to avoid ripple for these simple text clicks
private fun Modifier.clickableWithoutRipple(onClick: () -> Unit): Modifier = this.clickable(
    indication = null,
    interactionSource = MutableInteractionSource()
) { onClick() }

@Preview(showBackground = true, backgroundColor = 0xFFF5F5F5)
@Composable
private fun PreviewRestaurantInfo() {
    MaterialTheme {
        Column {
            RestaurantInfo()
            Spacer(modifier = Modifier.height(16.dp))
            RestaurantInfo(
                isBikgane = true,
                address = "Plot No 23, Sector 62, Noida",
                openStatus = "Open now • Closes 10:30 pm",
                serviceType = "Delivery only"
            )
        }
    }
}