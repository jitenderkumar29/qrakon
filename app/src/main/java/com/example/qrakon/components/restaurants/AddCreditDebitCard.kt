package com.example.qrakon.components.restaurants

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.qrakon.R
import com.example.qrakon.ui.theme.customColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCreditDebitCard(
    onBackClick: () -> Unit = {},
    onAddCardClick: (Map<String, String>) -> Unit = {}
) {
    var cardNumber by remember { mutableStateOf("") }
    var validThru by remember { mutableStateOf("") }
    var cvv by remember { mutableStateOf("") }
    var nameOnCard by remember { mutableStateOf("") }
    var cardNickname by remember { mutableStateOf("") }
    var showCvv by remember { mutableStateOf(false) }

    // Format card number with spaces every 4 digits
    fun formatCardNumber(input: String): String {
        val cleanInput = input.replace(" ", "")
        return cleanInput.chunked(4).joinToString(" ").take(19)
    }

    // Format valid thru (MM/YY)
    fun formatValidThru(input: String): String {
        val cleanInput = input.replace("/", "")
        return when {
            cleanInput.length >= 2 -> "${cleanInput.take(2)}/${cleanInput.drop(2).take(2)}"
            else -> cleanInput
        }
    }

    // Detect card type
    fun getCardType(cardNum: String): String {
        val cleanNum = cardNum.replace(" ", "")
        return when {
            cleanNum.startsWith("4") -> "Visa"
            cleanNum.startsWith("5") -> "Mastercard"
            cleanNum.startsWith("3") -> "Amex"
            cleanNum.startsWith("6") -> "Discover"
            else -> "Card"
        }
    }

    // Get card icon resource
    fun getCardIconRes(cardNum: String): Int? {
        val cleanNum = cardNum.replace(" ", "")
        return when {
            cleanNum.startsWith("4") -> R.drawable.ic_visa
            cleanNum.startsWith("5") -> R.drawable.ic_mastercard
            else -> null
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Add New Card",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black
                    )
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
                )
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
                    .verticalScroll(rememberScrollState())
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Card Preview Section
//                CardPreviewSection(
//                    cardNumber = cardNumber,
//                    validThru = validThru,
//                    nameOnCard = nameOnCard,
//                    cardType = getCardType(cardNumber),
//                    cardIconRes = getCardIconRes(cardNumber)
//                )
//
//                Spacer(modifier = Modifier.height(24.dp))

                // Card Form
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    // Card Number Field
                    OutlinedTextField(
                        value = cardNumber,
                        onValueChange = {
                            val formatted = formatCardNumber(it)
                            if (formatted.length <= 19) {
                                cardNumber = formatted
                            }
                        },
                        placeholder = {
                            Text(
                                text = "Card Number",
                                color = Color.DarkGray,
                                modifier = Modifier.fillMaxWidth()
                            )
                        },
                        leadingIcon = {
                            getCardIconRes(cardNumber)?.let { iconRes ->
                                Box(
                                    modifier = Modifier.size(24.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Image(
                                        painter = painterResource(iconRes),
                                        contentDescription = null,
                                        modifier = Modifier.size(24.dp),
                                        contentScale = ContentScale.Fit
                                    )
                                }
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.customColors.buttonRed,
                            unfocusedBorderColor = Color.DarkGray,
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.Black
                        )
                    )

                    // Valid Through and CVV Row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        // Valid Through Field
                        OutlinedTextField(
                            value = validThru,
                            onValueChange = {
                                val formatted = formatValidThru(it)
                                if (formatted.length <= 7) {
                                    validThru = formatted
                                }
                            },
                            placeholder = { Text("Valid Through (MM/YY)", color = Color.DarkGray) },
                            modifier = Modifier.weight(0.7f),
                            shape = RoundedCornerShape(12.dp),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = MaterialTheme.customColors.buttonRed,
                                unfocusedBorderColor = Color.DarkGray,
                                focusedTextColor = Color.Black,
                                unfocusedTextColor = Color.Black
                            )
                        )

                        // CVV Field
                        OutlinedTextField(
                            value = cvv,
                            onValueChange = {
                                if (it.length <= 4 && it.all { char -> char.isDigit() }) {
                                    cvv = it
                                }
                            },
                            placeholder = { Text("CVV", color = Color.DarkGray) },
                            modifier = Modifier.weight(0.3f),
                            shape = RoundedCornerShape(12.dp),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                            singleLine = true,
                            visualTransformation = if (showCvv) VisualTransformation.None else PasswordVisualTransformation(),
                            trailingIcon = {
                                IconButton(onClick = { showCvv = !showCvv }) {
                                    Icon(
                                        imageVector = if (showCvv) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                        contentDescription = if (showCvv) "Hide CVV" else "Show CVV",
                                        tint = Color.Gray,
                                        modifier = Modifier.size(18.dp)
                                    )
                                }
                            },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = MaterialTheme.customColors.buttonRed,
                                unfocusedBorderColor = Color.DarkGray,
                                focusedTextColor = Color.Black,
                                unfocusedTextColor = Color.Black
                            )
                        )
                    }

                    // Name on Card Field
                    OutlinedTextField(
                        value = nameOnCard,
                        onValueChange = { nameOnCard = it.uppercase() },
                        placeholder = { Text("Name on Card", color = Color.DarkGray) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.customColors.buttonRed,
                            unfocusedBorderColor = Color.DarkGray,
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.Black
                        )
                    )

                    // Card Nickname Field
                    OutlinedTextField(
                        value = cardNickname,
                        onValueChange = { cardNickname = it },
                        placeholder = { Text("Card Nickname (for easy identification)", color = Color.DarkGray) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.customColors.buttonRed,
                            unfocusedBorderColor = Color.DarkGray,
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.Black
                        )
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                // Protected Footer
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = "Protected",
                        tint = Color.Gray,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Your card details are 100% secure and encrypted",
                        fontSize = 12.sp,
                        color = Color.Gray,
                        fontWeight = FontWeight.Medium
                    )
                }

                // Add Card Button
                Button(
                    onClick = {
                        val cardData = mapOf(
                            "cardNumber" to cardNumber.replace(" ", ""),
                            "validThru" to validThru,
                            "cvv" to cvv,
                            "nameOnCard" to nameOnCard,
                            "cardNickname" to cardNickname,
                            "cardType" to getCardType(cardNumber)
                        )
                        onAddCardClick(cardData)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.customColors.buttonRed,
                        disabledContainerColor = MaterialTheme.customColors.buttonRed.copy(alpha = 0.5f)
                    ),
                    enabled = cardNumber.replace(" ", "").length >= 15 &&
                            validThru.length == 5 &&
                            cvv.length >= 3 &&
                            nameOnCard.isNotBlank()
                ) {
                    Text(
                        text = "Add Card",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun CardPreviewSection(
    cardNumber: String,
    validThru: String,
    nameOnCard: String,
    cardType: String,
    cardIconRes: Int?
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF1a1a2e)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            Color(0xFF1a1a2e),
                            Color(0xFF16213e)
                        )
                    )
                )
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                // Top section
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    // Card logo
                    if (cardIconRes != null && cardNumber.isNotEmpty()) {
                        Image(
                            painter = painterResource(cardIconRes),
                            contentDescription = cardType,
                            modifier = Modifier
                                .height(35.dp)
                                .width(50.dp),
                            contentScale = ContentScale.Fit,
                            colorFilter = if (cardType == "Visa")
                                androidx.compose.ui.graphics.ColorFilter.tint(Color.White)
                            else null
                        )
                    } else {
                        Text(
                            text = "••••",
                            color = Color.White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    // Chip
                    Box(
                        modifier = Modifier
                            .width(40.dp)
                            .height(30.dp)
                            .background(
                                color = Color(0xFFD4AF37),
                                shape = RoundedCornerShape(6.dp)
                            )
                    )
                }

                // Card number
                Text(
                    text = if (cardNumber.isNotEmpty()) cardNumber else "**** **** **** ****",
                    color = Color.White,
                    fontSize = 18.sp,
                    letterSpacing = 2.sp,
                    fontWeight = FontWeight.Medium
                )

                // Bottom section
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(
                            text = "VALID THRU",
                            color = Color.White.copy(alpha = 0.6f),
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Medium
                        )
                        Text(
                            text = if (validThru.isNotEmpty()) validThru else "MM/YY",
                            color = Color.White,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }

                    Column(
                        horizontalAlignment = Alignment.End
                    ) {
                        Text(
                            text = "CARD HOLDER",
                            color = Color.White.copy(alpha = 0.6f),
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Medium
                        )
                        Text(
                            text = if (nameOnCard.isNotEmpty()) nameOnCard else "YOUR NAME",
                            color = Color.White,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
        }
    }
}

@Preview(
    name = "Add Credit/Debit Card",
    showBackground = true,
    backgroundColor = 0xFFFFFFFF
)
@Composable
fun PreviewAddCreditDebitCard() {
    AddCreditDebitCard(
        onBackClick = { println("Back clicked") },
        onAddCardClick = { cardData ->
            println("Add card clicked: $cardData")
        }
    )
}