package com.example.qrakon.components.categorytabs.mobile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.qrakon.R
import com.example.qrakon.ui.theme.customColors

/**
 * MobileCarousel.kt
 * A horizontally scrollable carousel of categories for mobile layout.
 */
@Composable
fun MobileCarousel(
    selectedCategory: String,
    onCategoryClick: (String) -> Unit
) {
    data class CategoryItem(
        val id: Int,
        val name: String,
        val imageResId: Int
    )

    val categories = remember {
        listOf(
            CategoryItem(0, "iphone", R.drawable.ic_iphone),
            CategoryItem(1, "motoriola", R.drawable.ic_motoriola),
            CategoryItem(2, "Samsung", R.drawable.ic_samsung),
            CategoryItem(3, "Vivo", R.drawable.ic_vivo),
            CategoryItem(4, "OPPO", R.drawable.ic_oppo),
            CategoryItem(5, "realme", R.drawable.ic_realme),
            CategoryItem(6, "Nothing", R.drawable.ic_nothing),
            CategoryItem(7, "POCO", R.drawable.ic_poco),
            CategoryItem(8, "Google", R.drawable.ic_google),
            CategoryItem(9, "AI+", R.drawable.ic_aiplus),
            CategoryItem(10, "Minutes", R.drawable.ic_minutes),
            CategoryItem(11, "Redmi", R.drawable.ic_redmi),
            CategoryItem(12, "Tecno", R.drawable.ic_tecno),
            CategoryItem(13, "Infinix", R.drawable.ic_infinix),
            CategoryItem(14, "Alcatel", R.drawable.ic_alcatel),
            CategoryItem(15, "Snapdragon", R.drawable.ic_snapdragon),
            CategoryItem(16, "Android", R.drawable.ic_android),
            CategoryItem(17, "Itel", R.drawable.ic_itel)
        )
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState())
            .padding(horizontal = 8.dp, vertical = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        categories.forEach { category ->
            MobileCategoryCard(
                name = category.name,
                imageResId = category.imageResId,
                isSelected = selectedCategory == category.name,
                modifier = Modifier.width(80.dp),
                onClick = { onCategoryClick(category.name) }
            )
        }
    }
}

@Composable
fun MobileCategoryCard(
    name: String,
    imageResId: Int,
    isSelected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier
            .clickable { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .height(80.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
//                .background(MaterialTheme.customColors.imageBgColor1)
                .padding(2.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = imageResId),
                contentDescription = name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(80.dp)
                    .clip(MaterialTheme.shapes.medium)
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = name,
            style = MaterialTheme.typography.bodySmall.copy(
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                color = if (isSelected) MaterialTheme.colorScheme.primary
                else MaterialTheme.colorScheme.onSurfaceVariant
            ),
            textAlign = TextAlign.Center,
            maxLines = 2,
            modifier = Modifier.fillMaxWidth()
        )
    }
}
