import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun KidsFilter(
    modifier: Modifier = Modifier,
    onAgeRangeSelected: (String) -> Unit = {}
) {
    val ageRanges = listOf(
        "0-6 Months",
        "6-24 Months",
        "2-4 Years",
        "4-6 Years",
        "6-12 Years"
    )

    var selectedRange by remember { mutableStateOf(ageRanges[0]) }
    val scrollState = rememberScrollState()

    Row(
        modifier = modifier
            .horizontalScroll(scrollState)
            .padding(vertical = 8.dp, horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        ageRanges.forEach { range ->
            AgeRangeChip(
                text = range,
                isSelected = range == selectedRange,
                onClick = {
                    selectedRange = range
                    onAgeRangeSelected(range)
                }
            )
        }
    }
}

@Composable
fun AgeRangeChip(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor = if (isSelected) Color(0xFF1976D2) else Color(0xFFF5F5F5)
    val textColor = if (isSelected) Color.White else Color.Black
    val shadowColor = if (isSelected) Color(0xFF0D47A1) else Color(0xFFBDBDBD)

    // 3D effect colors
    val topLightColor = if (isSelected) Color(0xFF42A5F5) else Color.White
    val bottomDarkColor = if (isSelected) Color(0xFF1565C0) else Color(0xFFE0E0E0)

    Text(
        text = text,
        color = textColor,
        fontSize = 14.sp,
        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
        modifier = Modifier
            .shadow(
                elevation = if (isSelected) 4.dp else 2.dp,
                shape = RoundedCornerShape(12.dp),
                clip = false,
                ambientColor = shadowColor,
                spotColor = shadowColor
            )
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(topLightColor, backgroundColor, bottomDarkColor)
                ),
                shape = RoundedCornerShape(20.dp)
            )
            .border(
                width = 1.dp,
                brush = Brush.verticalGradient(
                    colors = listOf(
                        if (isSelected) Color(0xFF64B5F6) else Color.White,
                        if (isSelected) Color(0xFF0D47A1) else Color(0xFF9E9E9E)
                    )
                ),
                shape = RoundedCornerShape(20.dp)
            )
            .padding(horizontal = 16.dp, vertical = 5.dp)
            .clickable { onClick() }
    )
}