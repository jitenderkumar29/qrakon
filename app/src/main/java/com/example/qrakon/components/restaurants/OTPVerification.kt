package com.example.qrakon.components.restaurants

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.qrakon.R
import com.example.qrakon.ui.theme.customColors
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OTPVerification(
    totalAmount: Double = 4318.0,
    bankName: String = "SBI",
    onBackClick: () -> Unit = {},
    onPaymentComplete: () -> Unit = {},
    onResendOTP: () -> Unit = {},
    onGoToBankPage: () -> Unit = {}
) {
    var otp by remember { mutableStateOf("") }
    var isAutoReading by remember { mutableStateOf(true) }
    var remainingTime by remember { mutableStateOf(30) }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp

    // Calculate responsive OTP box size based on screen width
    val otpBoxSize = when {
        screenWidth < 360.dp -> 48.dp
        screenWidth < 400.dp -> 52.dp
        screenWidth < 480.dp -> 56.dp
        else -> 60.dp
    }

    val otpBoxSpacing = when {
        screenWidth < 360.dp -> 6.dp
        screenWidth < 400.dp -> 8.dp
        screenWidth < 480.dp -> 10.dp
        else -> 12.dp
    }

    // Simulate auto-reading OTP
    LaunchedEffect(isAutoReading) {
        if (isAutoReading) {
            delay(3000)
            otp = "123456"
            isAutoReading = false
            // Auto-submit after OTP is filled
            delay(500)
            onPaymentComplete()
        }
    }

    // Timer for resend OTP
    LaunchedEffect(remainingTime) {
        if (remainingTime > 0) {
            delay(1000)
            remainingTime--
        }
    }

    // Request keyboard focus when auto-reading completes
    LaunchedEffect(isAutoReading) {
        if (!isAutoReading && otp.isEmpty()) {
            focusRequester.requestFocus()
            keyboardController?.show()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column(
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Pay to HUFKO",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.Black
                        )
                        Row {
                            Text(
                                text = "Total Amount",
                                fontSize = 14.sp,
                                color = Color.Gray
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "₹${String.format("%.2f", totalAmount)}",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFFF6B00)
                            )
                        }
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.Black
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                    scrolledContainerColor = Color.White
                ),
                modifier = Modifier
            )
        }
    ) { paddingValues ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            color = Color.White
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 0.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Left side - SBI Card section
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.sbi_card),
                            contentDescription = "SBI",
                            modifier = Modifier.size(30.dp),
                            tint = Color.Unspecified
                        )
                    }

                    // Right side - Visa section
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.End
                    ) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(4.dp))
                                .border(1.dp, Color.LightGray, RoundedCornerShape(4.dp))
                                .background(Color.White)
                                .padding(horizontal = 6.dp, vertical = 8.dp)
                        ) {
                            Image(
                                painter = painterResource(R.drawable.ic_visa),
                                contentDescription = "VISA",
                                modifier = Modifier
                                    .height(12.dp)
                                    .width(30.dp),
                                contentScale = ContentScale.FillBounds
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // OTP Section Title
                Text(
                    text = "OTP Verification",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(12.dp))

                // OTP Info Text
                Text(
                    text = "An OTP has been sent to your mobile number linked to your $bankName.",
                    fontSize = 14.sp,
                    color = Color.DarkGray,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Auto-reading Status or OTP Input
                if (isAutoReading) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(20.dp),
                            color = Color(0xFFFF6B00),
                            strokeWidth = 2.dp
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = "Auto-Reading OTP...",
                            fontSize = 14.sp,
                            color = Color(0xFFFF6B00),
                            fontWeight = FontWeight.Medium
                        )
                    }
                } else {
                    // OTP Input Field with responsive sizing
                    OTPInputField(
                        otp = otp,
                        onOtpChange = { otp = it },
                        focusRequester = focusRequester,
                        otpBoxSize = otpBoxSize,
                        boxSpacing = otpBoxSpacing
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Submit & Pay Button
                Button(
                    onClick = onPaymentComplete,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.customColors.darkAccent2,
                        disabledContainerColor = MaterialTheme.customColors.darkAccent2.copy(0.3f)
                    ),
                    enabled = otp.length == 6 || isAutoReading
                ) {
                    Text(
                        text = "Submit & Pay ₹${String.format("%.2f", totalAmount)}",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Resend OTP Section
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (remainingTime > 0) {
                        Text(
                            text = "Resend OTP in ${remainingTime}s",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.customColors.buttonRed
                        )
                    } else {
                        Text(
                            text = "Resend OTP",
                            fontSize = 14.sp,
                            color = MaterialTheme.customColors.buttonRed,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.clickable {
                                onResendOTP()
                                remainingTime = 30
                                isAutoReading = true
                                otp = ""
                            }
                        )
                    }
                }

                Spacer(modifier = Modifier.weight(1f))

                // JUSPAY Section
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "POWERED BY",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Gray
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Icon(
                        painter = painterResource(id = R.drawable.juspay),
                        contentDescription = "JUSPAY",
                        modifier = Modifier.size(20.dp),
                        tint = Color.Unspecified
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "JUSPAY",
                        fontSize = 18.sp,
                        color = Color.Black
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Complete this payment on the bank website",
                        fontSize = 15.sp,
                        color = Color.Gray
                    )
                }

                Spacer(modifier = Modifier.height(0.dp))

                // Go to Bank Page Link
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Go to bank page",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFFFF6B00),
                        textDecoration = TextDecoration.Underline,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onGoToBankPage() }
                            .padding(vertical = 8.dp),
                        textAlign = TextAlign.Start
                    )
                }

//                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Composable
fun OTPInputField(
    otp: String,
    onOtpChange: (String) -> Unit,
    focusRequester: FocusRequester,
    otpBoxSize: Dp = 56.dp,
    boxSpacing: Dp = 12.dp
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        // Responsive OTP Boxes Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            repeat(6) { index ->
                // Calculate total width to ensure proper centering
                val totalBoxesWidth = (otpBoxSize * 6) + (boxSpacing * 5)

                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    OTPDigitBox(
                        digit = otp.getOrNull(index)?.toString() ?: "",
                        isActive = index == otp.length && otp.length < 6,
                        boxSize = otpBoxSize
                    )
                }

                // Add spacing between boxes except after the last one
                if (index < 5) {
                    Spacer(modifier = Modifier.width(boxSpacing))
                }
            }
        }

        // Hidden text field for OTP input
        BasicTextField(
            value = otp,
            onValueChange = {
                if (it.length <= 6 && it.all { char -> char.isDigit() }) {
                    onOtpChange(it)
                }
            },
            modifier = Modifier
                .offset(y = (-100).dp)
                .focusRequester(focusRequester),
            textStyle = TextStyle(fontSize = 0.sp),
            keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(
                keyboardType = androidx.compose.ui.text.input.KeyboardType.NumberPassword
            )
        )
    }
}

@Composable
fun OTPDigitBox(
    digit: String,
    isActive: Boolean,
    boxSize: Dp = 56.dp,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(boxSize)
            .clip(RoundedCornerShape(12.dp))
            .background(
                when {
                    digit.isNotEmpty() -> Color(0xFFFFF3E0)
                    isActive -> Color(0xFFFFF3E0)
                    else -> Color(0xFFF5F5F5)
                }
            )
            .border(
                width = if (isActive && digit.isEmpty()) 2.dp else 1.dp,
                color = if (isActive && digit.isEmpty()) Color(0xFFFF6B00) else Color.LightGray,
                shape = RoundedCornerShape(12.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        val fontSize = when {
            boxSize < 50.dp -> 18.sp
            boxSize < 56.dp -> 20.sp
            else -> 24.sp
        }

        Text(
            text = digit.ifEmpty { "" },
            fontSize = fontSize,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
    }
}

// Previews
@Preview(
    name = "OTP Verification - Auto-reading",
    showBackground = true,
    backgroundColor = 0xFFFFFFFF
)
@Composable
fun PreviewOTPVerificationAutoReading() {
    OTPVerification(
        totalAmount = 4318.0,
        bankName = "SBI",
        onBackClick = { println("Back clicked") },
        onPaymentComplete = {
            println("Payment completed")
        },
        onResendOTP = {
            println("Resend OTP clicked")
        },
        onGoToBankPage = {
            println("Go to bank page clicked")
        }
    )
}

@Preview(
    name = "OTP Verification - Different Amount",
    showBackground = true
)
@Composable
fun PreviewOTPVerificationDifferentAmount() {
    OTPVerification(
        totalAmount = 1299.0,
        bankName = "HDFC",
        onBackClick = {},
        onPaymentComplete = {},
        onResendOTP = {},
        onGoToBankPage = {}
    )
}

@Preview(
    name = "OTP Verification - Small Screen",
    showBackground = true,
    widthDp = 360,
    heightDp = 640
)
@Composable
fun PreviewOTPVerificationSmallScreen() {
    OTPVerification(
        totalAmount = 4318.0,
        bankName = "SBI",
        onBackClick = {},
        onPaymentComplete = {},
        onResendOTP = {},
        onGoToBankPage = {}
    )
}

@Preview(
    name = "OTP Verification - Extra Small Screen",
    showBackground = true,
    widthDp = 320,
    heightDp = 640
)
@Composable
fun PreviewOTPVerificationExtraSmallScreen() {
    OTPVerification(
        totalAmount = 4318.0,
        bankName = "SBI",
        onBackClick = {},
        onPaymentComplete = {},
        onResendOTP = {},
        onGoToBankPage = {}
    )
}