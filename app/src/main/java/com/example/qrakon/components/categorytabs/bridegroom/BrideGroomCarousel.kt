package com.example.qrakon.components.categorytabs.bridegroom

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

@Composable
fun BrideGroomCarousel(
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
            CategoryItem(0, "Men", R.drawable.ic_men_bride_groom),
            CategoryItem(1, "Women", R.drawable.ic_women_bride_groom),
            CategoryItem(2, "Kids", R.drawable.ic_kids_bride_groom),
            CategoryItem(3, "Bridal Wear", R.drawable.ic_bridal_wear),
            CategoryItem(4, "Groom Wear", R.drawable.ic_groom_wear),
            CategoryItem(5, "Bridal Jewelry", R.drawable.ic_bridal_jewelry),
            CategoryItem(6, "Wedding Shoes", R.drawable.ic_wedding_shoes),
            CategoryItem(7, "Bridal Accessories", R.drawable.ic_bridal_accessories),
            CategoryItem(8, "Groom Accessories", R.drawable.ic_groom_accessories),
            CategoryItem(9, "Wedding Rings", R.drawable.ic_wedding_rings),
            CategoryItem(10, "Mehndi", R.drawable.ic_mehndi),
            CategoryItem(11, "Wedding Decor", R.drawable.ic_wedding_decor),
            CategoryItem(12, "Invitations", R.drawable.ic_invitations),
            CategoryItem(13, "Bridal Beauty", R.drawable.ic_bridal_beauty),
            CategoryItem(14, "Groom Grooming", R.drawable.ic_groom_grooming),
            CategoryItem(15, "Wedding Favors", R.drawable.ic_wedding_favors),
            CategoryItem(16, "Honeymoon", R.drawable.ic_honeymoon),
            CategoryItem(17, "Bridal Party", R.drawable.ic_bridal_party),
            CategoryItem(18, "Wedding Planners", R.drawable.ic_wedding_planners)
        )
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        categories.forEach { category ->
            CategoryCard(
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
fun CategoryCard(
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
                .background(MaterialTheme.customColors.imageBgColor1)
                .padding(4.dp),
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

        Spacer(modifier = Modifier.height(8.dp))

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