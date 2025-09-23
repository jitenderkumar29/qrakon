package com.example.shopping.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.qrakon.R

// Sidebar category
data class SidebarItem(
    val title: String,
    val imageRes: Int,
    val subcategories: List<SubcategoryItem>
)

// Subcategory for right-side content
data class SubcategoryItem(
    val title: String,
    val imageRes: Int
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoreCategoryPage() {
    val tabs = listOf(
        SidebarItem(
            "Home",
            imageRes = R.drawable.bazaar,
            subcategories = listOf(
                SubcategoryItem("Sofas", R.drawable.bazaar),
                SubcategoryItem("Tables", R.drawable.bazaar),
                SubcategoryItem("Chairs", R.drawable.bazaar),
                SubcategoryItem("Beds", R.drawable.bazaar),
                SubcategoryItem("Wardrobes", R.drawable.bazaar),
                SubcategoryItem("Shelves", R.drawable.bazaar)
            )
        ),
        SidebarItem(
            "Favorites",
            imageRes = R.drawable.bazaar,
            subcategories = listOf(
                SubcategoryItem("Shoes", R.drawable.bazaar),
                SubcategoryItem("Watches", R.drawable.bazaar),
                SubcategoryItem("Bags", R.drawable.bazaar),
                SubcategoryItem("Jewelry", R.drawable.bazaar),
                SubcategoryItem("Accessories", R.drawable.bazaar)
            )
        ),
        SidebarItem(
            "Profile",
            imageRes = R.drawable.bazaar,
            subcategories = listOf(
                SubcategoryItem("Orders", R.drawable.bazaar),
                SubcategoryItem("Wishlist", R.drawable.bazaar),
                SubcategoryItem("Coupons", R.drawable.bazaar),
                SubcategoryItem("Addresses", R.drawable.bazaar),
                SubcategoryItem("Payment Methods", R.drawable.bazaar)
            )
        ),
        SidebarItem(
            "Settings",
            imageRes = R.drawable.bazaar,
            subcategories = listOf(
                SubcategoryItem("Account", R.drawable.bazaar),
                SubcategoryItem("Notifications", R.drawable.bazaar),
                SubcategoryItem("Privacy", R.drawable.bazaar),
                SubcategoryItem("Language", R.drawable.bazaar),
                SubcategoryItem("Help & Support", R.drawable.bazaar)
            )
        )
    )

    var selectedTab by remember { mutableStateOf(0) }

    Row(modifier = Modifier.fillMaxSize()) {
        // Sidebar
        Column(
            modifier = Modifier
                .width(90.dp)
                .fillMaxHeight()
                .background(Color(0xFFF5F5F5)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            tabs.forEachIndexed { index, item ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(vertical = 12.dp)
                        .clickable { selectedTab = index }
                        .background(
                            if (selectedTab == index) MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
                            else Color.Transparent
                        )
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    Image(
                        painter = painterResource(id = item.imageRes),
                        contentDescription = item.title,
                        modifier = Modifier
                            .size(36.dp)
                            .clip(MaterialTheme.shapes.small)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = item.title,
                        fontSize = 12.sp,
                        color = if (selectedTab == index) MaterialTheme.colorScheme.primary else Color.Gray,
                        fontWeight = if (selectedTab == index) FontWeight.Bold else FontWeight.Normal
                    )
                }
            }
        }

        // Right side content switches with tab
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .background(Color.White)
        ) {
            TabContent(tab = tabs[selectedTab])
        }
    }
}

@Composable
fun TabContent(tab: SidebarItem) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = tab.title,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 20.dp)
        )

        // Grid of items for this tab - FIXED
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.weight(1f)
        ) {
            items(tab.subcategories) { sub ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .clickable {
                            // Handle subcategory click here
                            // You can navigate to a product list page or show products
                        }
                ) {
                    Card(
                        modifier = Modifier
                            .size(80.dp)
                            .clip(MaterialTheme.shapes.medium),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Image(
                            painter = painterResource(id = sub.imageRes),
                            contentDescription = sub.title,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = sub.title,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.DarkGray,
                        maxLines = 1
                    )
                }
            }
        }
    }
}