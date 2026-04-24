package com.example.qrakon.components.restaurants

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.qrakon.R
import com.example.qrakon.ui.theme.customColors

// region Data Classes
data class UpiData(
    val name: String,
    val icon: Int,
    val offerText: String? = null,
    val paymentMethod: String = name
)

data class CardData(
    val id: String,
    val bankName: String,
    val lastDigits: String,
    val icon: Int,
    val cardType: String
)

data class MoreOption(
    val title: String,
    val description: String?,
    val icon: Int
)

@Composable
fun PaymentOptionsF(
    onBackClick: () -> Unit = {},
    onPaymentComplete: (Map<String, String>) -> Unit = {},
    onNavigateToAddCard: () -> Unit = {},
    onNavigateToOTPVerification: (String) -> Unit = {}
) {
    var selectedCardId by remember { mutableStateOf("VISA") }
    var selectedUpi by remember { mutableStateOf(phonePeUpi) }
    var showAddCardScreen by remember { mutableStateOf(false) }

    if (showAddCardScreen) {
        AddCreditDebitCard(
            onBackClick = { showAddCardScreen = false },
            onAddCardClick = {
                // Handle card addition logic here
                showAddCardScreen = false
                onNavigateToAddCard()
            }
        )
    } else {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color(0xFFF5F5F5)
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                PaymentOptionsHeader(onBackClick = onBackClick)

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                ) {
                    DeliveryInfoSection()
                    OfferBanner()
                    HufkoUpiPromoBanner()

                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        PreferredPaymentSection(
                            selectedCardId = selectedCardId,
                            onCardSelected = { selectedCardId = it },
                            onPayClick = { onNavigateToOTPVerification("1710") } // Trigger OTP navigation
                        )
                        UpiSection(
                            selectedUpi = selectedUpi,
                            onUpiSelected = { selectedUpi = it },
                        )
                        CreditDebitCardsSection(
                            onAddCardClick = { showAddCardScreen = true }
                        )
                        MorePaymentOptionsSection()
                    }
                }
            }
        }
    }
}

// region Header Section
@Composable
fun PaymentOptionsHeader(onBackClick: () -> Unit = {}) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            Icons.Default.ArrowBack,
            contentDescription = "Back",
            tint = Color.Black,
            modifier = Modifier.clickable { onBackClick() }
        )

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = "Payment Options",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(4.dp))

            Row {
                Text(
                    text = "7 items • Total: ₹1710",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "Savings of ₹762.99",
                    fontSize = 13.sp,
                    color = Color(0xFF4CAF50),
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
    Divider(color = Color.LightGray.copy(alpha = 0.5f), thickness = 1.dp)
}
// endregion

// region Delivery Section
@Composable
fun DeliveryInfoSection() {
    Column(
        modifier = Modifier
            .background(Color.White)
            .padding(16.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Top
        ) {
            Image(
                painter = painterResource(R.drawable.ic_address_progress_bar),
                contentDescription = null,
                modifier = Modifier
                    .height(42.dp)
                    .width(15.dp),
                contentScale = ContentScale.FillBounds
            )

            Spacer(modifier = Modifier.width(10.dp))

            Column(modifier = Modifier.fillMaxWidth()) {
                DeliveryInfoRow()
                Spacer(modifier = Modifier.height(8.dp))
                AddressRow()
            }
        }
    }
}

@Composable
fun DeliveryInfoRow() {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = "Pizza Wings",
            fontSize = 15.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.Black
        )
        Text(text = "  |  ", fontSize = 14.sp, color = Color.Gray)
        Text(
            text = "Delivery in: 35-40 mins",
            fontSize = 13.sp,
            color = Color.Gray
        )
    }
}

@Composable
fun AddressRow() {
    Row {
        Text(
            text = "Home",
            fontSize = 15.sp,
            color = Color.Black,
            fontWeight = FontWeight.Medium
        )
        Text(text = "  |  ", fontSize = 14.sp, color = Color.Gray)
        Text(
            text = "F109, Arnab House, 4th Floor, Block -f, gal...",
            fontSize = 13.sp,
            color = Color.Gray,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(1f)
        )
    }
}
// endregion

// region Offer Components
@Composable
fun OfferBanner() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFFC8F9E4))
            .clickable { /* handle click */ }
            .padding(horizontal = 12.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OfferIcon()
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = "Save upto ₹350 more with payment offers",
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.customColors.greenButton,
            modifier = Modifier.weight(1f)
        )
        Icon(
            painter = painterResource(id = R.drawable.outline_arrow_forward_ios_24),
            contentDescription = null,
            tint = MaterialTheme.customColors.greenButton,
            modifier = Modifier.size(12.dp)
        )
    }
}

@Composable
fun OfferIcon() {
    Box(
        modifier = Modifier
            .size(28.dp)
            .background(Color.White, RoundedCornerShape(50)),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(R.drawable.ic_discount),
            contentDescription = null,
            modifier = Modifier
                .height(25.dp)
                .width(25.dp),
            contentScale = ContentScale.FillBounds
        )
    }
}
// endregion

// region Payment Sections
@Composable
fun PreferredPaymentSection(
    selectedCardId: String,
    onCardSelected: (String) -> Unit,
    onPayClick: () -> Unit = {} // This will trigger OTP navigation
) {
    Column {
        Text(
            text = "Preferred Payment",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column {
                PaymentCardItem(
                    card = visaCard,
                    isSelected = selectedCardId == "VISA",
                    onSelect = { onCardSelected("VISA") },
                    showCvvInfo = true,
                    onPayClick = onPayClick // Pass the callback
                )

                DottedDivider()

                PaymentCardItem(
                    card = hdfcCard,
                    isSelected = selectedCardId == "HDFC",
                    onSelect = { onCardSelected("HDFC") },
                    showCvvInfo = false,
                    onPayClick = onPayClick // Pass the callback
                )
            }
        }
    }
}

@Composable
fun PaymentCardItem(
    card: CardData,
    isSelected: Boolean,
    onSelect: () -> Unit,
    showCvvInfo: Boolean = false,
    onPayClick: () -> Unit = {} // This parameter exists
) {
    Column(modifier = Modifier.padding(16.dp)) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            PaymentCardIcon(card.icon)
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = "${card.bankName} Card  |  •••• ${card.lastDigits}",
                modifier = Modifier.weight(1f),
                fontSize = 14.sp
            )
            RadioButton(
                selected = isSelected,
                onClick = onSelect,
                colors = RadioButtonDefaults.colors(selectedColor = Color(0xFF1BA672))
            )
        }

        if (isSelected) {
            Spacer(modifier = Modifier.height(12.dp))
            PayButton(
                amount = "₹1710",
                onClick = onPayClick // ✅ This should already be correct
            )
            if (showCvvInfo) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "CVV is not needed for VISA cards saved as per RBI guidelines",
                    fontSize = 11.sp,
                    color = Color.Gray
                )
            }
        }
    }
}

@Composable
fun PaymentCardIcon(iconRes: Int) {
    Box(
        modifier = Modifier
            .size(28.dp)
            .background(Color.White, RoundedCornerShape(12.dp))
            .border(1.dp, Color.LightGray, RoundedCornerShape(12.dp)),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(iconRes),
            contentDescription = null,
            modifier = Modifier.size(20.dp),
            contentScale = ContentScale.Fit
        )
    }
}

@Composable
fun DottedDivider() {
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
    ) {
        val dashWidth = 10f
        val gapWidth = 8f
        var startX = 0f

        while (startX < size.width) {
            drawLine(
                color = Color(0xFFE0E0E0),
                start = Offset(startX, 0f),
                end = Offset(startX + dashWidth, 0f),
                strokeWidth = 2f
            )
            startX += dashWidth + gapWidth
        }
    }
}
// endregion

// region UPI Section
@Composable
fun UpiSection(selectedUpi: UpiData, onUpiSelected: (UpiData) -> Unit) {
    Column {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(R.drawable.ic_upi),
                contentDescription = null,
                modifier = Modifier
                    .height(20.dp)
                    .width(30.dp),
                contentScale = ContentScale.Fit
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Pay by any UPI App",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column {
                UnlockUpiBanner()
                Divider(color = MaterialTheme.customColors.veryLightGray)

                UpiOptionItem(
                    upi = phonePeUpi,
                    isSelected = selectedUpi == phonePeUpi,
                    onSelect = { onUpiSelected(phonePeUpi) }
                )

                Divider(color = MaterialTheme.customColors.veryLightGray)

                UpiOptionItem(
                    upi = paytmUpi,
                    isSelected = selectedUpi == paytmUpi,
                    onSelect = { onUpiSelected(paytmUpi) }
                )
            }
        }
    }
}

@Composable
fun UpiOptionItem(
    upi: UpiData,
    isSelected: Boolean,
    onSelect: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onSelect() }
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        UpiItemRow(upi = upi, isSelected = isSelected)

        if (isSelected) {
            Spacer(modifier = Modifier.height(8.dp))
            PayButton(paymentMethod = upi.paymentMethod)
        }
    }
}

@Composable
fun UpiItemRow(upi: UpiData, isSelected: Boolean) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        UpiIcon(iconRes = upi.icon)
        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = upi.name,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black
            )

            upi.offerText?.let {
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = it,
                    fontSize = 11.sp,
                    color = if (isSelected) Color(0xFF1BA672) else Color.Gray,
                    lineHeight = 14.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }

        SelectionIndicator(isSelected = isSelected)
    }
}

@Composable
fun UpiIcon(iconRes: Int) {
    Box(
        modifier = Modifier
            .size(36.dp)
            .background(Color.White, RoundedCornerShape(8.dp))
            .border(1.dp, Color(0xFFE0E0E0), RoundedCornerShape(8.dp)),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(iconRes),
            contentDescription = null,
            modifier = Modifier.size(25.dp),
            contentScale = ContentScale.Fit
        )
    }
}

@Composable
fun SelectionIndicator(isSelected: Boolean) {
    if (isSelected) {
        Box(
            modifier = Modifier
                .size(22.dp)
                .background(Color(0xFF1BA672), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "✓",
                color = Color.White,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )
        }
    } else {
        Box(
            modifier = Modifier
                .size(22.dp)
                .border(1.dp, Color(0xFFBDBDBD), CircleShape)
        )
    }
}

@Composable
fun UnlockUpiBanner() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { /* handle click */ }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        UpiIcon(iconRes = R.drawable.ic_hufko_upi)
        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "Unlock Hufko UPI",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.width(6.dp))
                NewBadge()
            }
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = "Activate fastest UPI in 10 seconds",
                fontSize = 12.sp,
                color = Color.Gray
            )
        }

        Icon(
            painter = painterResource(R.drawable.outline_arrow_forward_ios_24),
            contentDescription = null,
            tint = Color.Gray,
            modifier = Modifier.size(16.dp)
        )
    }
}

@Composable
fun NewBadge() {
    Box(
        modifier = Modifier
            .background(Color(0xFFFF6D00), RoundedCornerShape(4.dp))
            .padding(horizontal = 6.dp, vertical = 2.dp)
    ) {
        Text(
            text = "NEW",
            fontSize = 9.sp,
            color = Color.White,
            fontWeight = FontWeight.Bold
        )
    }
}
// endregion

// region Credit/Debit Cards Section
@Composable
fun CreditDebitCardsSection(onAddCardClick: () -> Unit = {}) {
    Column {
        Text(
            text = "Credit & Debit Cards",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(8.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            AddNewCardOption(onAddCardClick = onAddCardClick)
        }
    }
}

@Composable
fun AddNewCardOption(onAddCardClick: () -> Unit = {}) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onAddCardClick() }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .background(Color.White, RoundedCornerShape(8.dp))
                .border(1.dp, Color(0xFFE0E0E0), RoundedCornerShape(8.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = android.R.drawable.ic_input_add),
                contentDescription = "Add",
                tint = MaterialTheme.customColors.orangeButton,
                modifier = Modifier.size(24.dp)
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column {
            Text(
                text = "Add New Card",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.customColors.orangeButton
            )
            Text(
                text = "Save and Pay via Cards.",
                fontSize = 12.sp,
                color = Color.Gray
            )
        }
    }
}
// endregion

// region More Payment Options
@Composable
fun MorePaymentOptionsSection() {
    Text(
        text = "More Payment Options",
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
        color = Color.Black
    )
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(0.dp)) {
            moreOptionsList.forEachIndexed { index, option ->
                MoreOptionItem(option = option, showDivider = index < moreOptionsList.size - 1)
            }
        }
    }
}

@Composable
fun MoreOptionItem(option: MoreOption, showDivider: Boolean) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { }
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.customColors.veryLightGray,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = option.icon),
                    contentDescription = null,
                    tint = Color.Unspecified,
                    modifier = Modifier.size(18.dp)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = option.title,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black
                )

                option.description?.let {
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = it,
                        fontSize = 12.sp,
                        color = Color(0xFF757575),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            Icon(
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = null,
                tint = Color(0xFFBDBDBD),
                modifier = Modifier.size(20.dp)
            )
        }

        if (showDivider) {
            Divider(
                modifier = Modifier.padding(0.dp),
                color = Color(0xFFEAEAEA),
                thickness = 0.6.dp
            )
        }
    }
}
// endregion

// region Common Components
@Composable
fun PayButton(
    amount: String? = null,
    paymentMethod: String? = null,
    onClick: () -> Unit = {} // Add onClick parameter
) {
    Button(
        onClick = onClick, // Use the onClick parameter
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1BA672))
    ) {
        Text(
            text = amount?.let { "Pay $it" } ?: "Pay via $paymentMethod",
            color = Color.White,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun HufkoUpiPromoBanner() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 0.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        Color(0xFF0F6D5E),
                        Color(0xFF117A65)
                    )
                )
            )
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "UPI payments, now 3X Faster",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "Unlock faster in-app UPI for instant payments!",
                    fontSize = 17.sp,
                    color = Color.White.copy(alpha = 0.9f)
                )

                Spacer(modifier = Modifier.height(12.dp))

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(50))
                        .background(Color.White)
                        .clickable { /* action */ }
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Text(
                        text = "Activate in 10s",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF0F6D5E)
                    )
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            Image(
                painter = painterResource(id = R.drawable.ic_upi_hufko),
                contentDescription = null,
                modifier = Modifier
                    .height(70.dp)
                    .width(100.dp),
                contentScale = ContentScale.FillBounds
            )
        }
    }
}
// endregion

// region Data Constants
private val visaCard = CardData(
    id = "VISA",
    bankName = "SBI",
    lastDigits = "6247",
    icon = R.drawable.ic_visa,
    cardType = "VISA"
)

private val hdfcCard = CardData(
    id = "HDFC",
    bankName = "HDFC Bank",
    lastDigits = "0452",
    icon = R.drawable.ic_hdfc_card,
    cardType = "HDFC"
)

private val phonePeUpi = UpiData(
    name = "PhonePe UPI",
    icon = R.drawable.ic_phonepe,
    paymentMethod = "PhonePe"
)

private val paytmUpi = UpiData(
    name = "Paytm UPI",
    icon = R.drawable.ic_paytm,
    offerText = "FLAT ₹30 cashback on Paytm UPI payments above ₹100 (T&Cs apply*)",
    paymentMethod = "Paytm"
)

private val moreOptionsList = listOf(
    MoreOption("Pluxee", "Pluxee card valid only on Food & Instamart", R.drawable.ic_card),
    MoreOption("Wallets", "PhonePe, Amazon Pay & more", R.drawable.ic_wallet),
    MoreOption("Netbanking", "Select from a list of banks", R.drawable.ic_bank),
    MoreOption("CRED pay", null, R.drawable.ic_cred),
    MoreOption("Pay on Delivery", "Pay in cash or pay online.", R.drawable.ic_cod)
)
// endregion

// region Preview
@Preview(showBackground = true)
@Composable
fun PaymentOptionsPreview() {
    PaymentOptionsF()
}
// endregion