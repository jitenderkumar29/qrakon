import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.qrakon.R
import com.example.qrakon.ui.theme.customColors

@Composable
fun DisclaimerFood(
    restaurantName: String = "Anshu Dhabo",
    outlet: String = "Saiket",
    address: String = "Shop No.-21 Ground Floor, Local Shopping Centre, Madangiri, Pushpa Bhowan, Madan Gir, Hauz Khas, South , Delhi - 110062",
    fssaiLicense: String = "23318008000752",
    onReportIssueClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(16.dp)
    ) {
        // Title
        Text(
            text = "Disclaimer:",
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Disclaimer points
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "•",
                    fontSize = 20.sp,
                    color = MaterialTheme.customColors.darkGray,
                    modifier = Modifier.width(20.dp)
                )
                Text(
                    text = "All prices are set directly by the restaurant.",
                    fontSize = 13.sp,
                    color = MaterialTheme.customColors.darkGray,
                    lineHeight = 16.sp,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(2.dp))

            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "•",
                    fontSize = 20.sp,
                    color = MaterialTheme.customColors.darkGray,
                    modifier = Modifier.width(20.dp)
                )
                Text(
                    text = "All nutritional information is indicative, values are per serve as shared by the restaurant and may vary depending on the ingredients and portion size.",
                    fontSize = 13.sp,
                    color = MaterialTheme.customColors.darkGray,
                    lineHeight = 16.sp,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "•",
                    fontSize = 20.sp,
                    color = MaterialTheme.customColors.darkGray,
                    modifier = Modifier.width(20.dp)
                )
                Text(
                    text = "An average active adult requires 2,000 kcal energy per day, however, calorie needs may vary.",
                    fontSize = 13.sp,
                    color = MaterialTheme.customColors.darkGray,
                    lineHeight = 16.sp,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "•",
                    fontSize = 20.sp,
                    color = MaterialTheme.customColors.darkGray,
                    modifier = Modifier.width(20.dp)
                )
                Text(
                    text = "Dish details might be AI crafted for a better experience",
                    fontSize = 13.sp,
                    color = MaterialTheme.customColors.darkGray,
                    lineHeight = 16.sp,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Divider
        HorizontalDivider(color = Color(0xFFE0E0E0), thickness = 1.dp)

        // Report an issue
        Spacer(modifier = Modifier.height(12.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onReportIssueClick() }
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_report),
                contentDescription = null,
                modifier = Modifier.size(18.dp),
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                text = "Report an issue with the menu",
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.customColors.darkGray
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                painter = painterResource(R.drawable.baseline_arrow_right_24),
                contentDescription = null,
                modifier = Modifier.size(18.dp),
                tint = MaterialTheme.customColors.darkGray
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Divider
        HorizontalDivider(color = Color(0xFFE0E0E0), thickness = 1.dp)

        // License
        Spacer(modifier = Modifier.height(12.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_license),
                contentDescription = null,
                modifier = Modifier
                    .height(30.dp)
                    .width(90.dp),
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                text = "FSSAI License No. $fssaiLicense",
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.customColors.darkGray,
                modifier = Modifier.padding(top = 5.dp)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Divider
        HorizontalDivider(color = Color(0xFFE0E0E0), thickness = 1.dp)

        Spacer(modifier = Modifier.height(16.dp))

        // Restaurant name - Dynamic
        if (restaurantName.isNotEmpty()) {
            Text(
                text = restaurantName,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(2.dp))
        }

        // Outlet - Dynamic
        if (outlet.isNotEmpty()) {
            Text(
                text = "(Outlet : $outlet)",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.customColors.darkGray,
            )

            Spacer(modifier = Modifier.height(8.dp))
        }

        // Address - Dynamic
        if (address.isNotEmpty()) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    painter = painterResource(R.drawable.baseline_location_pin_24),
                    contentDescription = null,
                    modifier = Modifier.size(18.dp),
                    tint = Color(0xFF757575)
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    text = address,
                    fontSize = 13.sp,
                    color = MaterialTheme.customColors.darkGray,
                    lineHeight = 18.sp
                )
            }

            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@Composable
fun ViewCartButton(
    itemCount: Int = 1,
    onViewCartClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 20.dp)
//            .shadow(
//                elevation = 8.dp,
//                shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
//                clip = false // keep shadow visible outside
//            )
            .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
            .background(MaterialTheme.customColors.white)
            .padding(top = 8.dp, bottom = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 5.dp)
                .shadow(
                    elevation = 6.dp,
                    shape = RoundedCornerShape(12.dp)
                )
                .background(
                    color = MaterialTheme.customColors.greenButton,
                    shape = RoundedCornerShape(12.dp)
                )
                .clickable { onViewCartClick() }
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                // Left Text
                Text(
                    text = "$itemCount ${if (itemCount > 1) "Items" else "Item"} added",
                    color = Color.White,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium
                )

                // Right Section
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "View Cart",
                        color = Color.White,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold
                    )

                    Spacer(modifier = Modifier.width(6.dp))

                    Icon(
                        painter = painterResource(id = R.drawable.outline_arrow_forward_ios_24),
                        contentDescription = "View Cart",
                        tint = Color.White,
                        modifier = Modifier.size(17.dp)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 800)
@Composable
fun ViewCartButtonPreview() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5)), // screen background
        contentAlignment = Alignment.BottomCenter
    ) {
        ViewCartButton(itemCount = 1)
    }
}