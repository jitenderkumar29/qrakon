package com.example.qrakon.components.fashion.fashiontab

import InfoCategoriesF
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.qrakon.ui.theme.customColors

@Composable
fun CategoriesFTab(
    onCategorySelected: (FashionCategoryTabPage) -> Unit = {}
) {
    var selectedTabIndex by remember { mutableIntStateOf(0) }

    val tabs = listOf(
        "Women" to FashionCategoryTabPage.Women,
        "Men" to FashionCategoryTabPage.Men,
        "Kids" to FashionCategoryTabPage.Kids,
        "Home" to FashionCategoryTabPage.Home
    )

    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.customColors.skyBlue),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            tabs.forEachIndexed { index, (title, page) ->
                val isSelected = selectedTabIndex == index

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 4.dp)
//                        .padding(horizontal = 4.dp, vertical = 6.dp)
                        .clickable {
                            selectedTabIndex = index
                            onCategorySelected(page)
                        },
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // ✅ Extract theme colors *before* drawBehind
                    // Extract colors in composable scope
                    val backgroundColor = MaterialTheme.customColors.skyBlue
                    val borderColor = if (isSelected) MaterialTheme.customColors.gray else Color.Transparent
                    val bottomLineColor = if (isSelected) MaterialTheme.customColors.onPrimaryContainer else Color.Transparent

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp)) // Rounded top corners
//                            .background(backgroundColor) // Background respects clip
                            .drawBehind {
                                val strokeWidth = if (isSelected) 2.dp.toPx() else 1.dp.toPx()
                                val width = size.width
                                val height = size.height

//                                // Top border (optional)
//                                drawLine(
//                                    color = borderColor,
//                                    start = androidx.compose.ui.geometry.Offset(0f, strokeWidth / 2),
//                                    end = androidx.compose.ui.geometry.Offset(width, strokeWidth / 2),
//                                    strokeWidth = strokeWidth
//                                )
//
//                                // Left border
//                                drawLine(
//                                    color = borderColor,
//                                    start = androidx.compose.ui.geometry.Offset(strokeWidth / 2, 0f),
//                                    end = androidx.compose.ui.geometry.Offset(strokeWidth / 2, height),
//                                    strokeWidth = strokeWidth
//                                )
//
//                                // Right border
//                                drawLine(
//                                    color = borderColor,
//                                    start = androidx.compose.ui.geometry.Offset(width - strokeWidth / 2, 0f),
//                                    end = androidx.compose.ui.geometry.Offset(width - strokeWidth / 2, height),
//                                    strokeWidth = strokeWidth
//                                )
//
//                                // Bottom border (active highlight)
//                                drawLine(
//                                    color = bottomLineColor,
//                                    start = androidx.compose.ui.geometry.Offset(0f, height - strokeWidth / 2),
//                                    end = androidx.compose.ui.geometry.Offset(width, height - strokeWidth / 2),
//                                    strokeWidth = strokeWidth
//                                )
                            }
                            .padding(vertical = 8.dp),
                        contentAlignment = Alignment.Center
                    )

                    {
                        Text(
                            text = title,
                            fontSize = 15.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                            color = if (isSelected)
                                MaterialTheme.customColors.onPrimaryContainer
                            else
                                MaterialTheme.customColors.black,
                            textAlign = TextAlign.Center,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }

                    // ✅ Bottom border line (active indicator)
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(3.dp)
                            .background(
                                if (isSelected)
                                    MaterialTheme.customColors.onPrimaryContainer
                                else
                                    Color.Transparent
                            )
                    )
                }
            }
        }

        // ✅ Show tab content
        Box(modifier = Modifier.fillMaxWidth()) {
            when (selectedTabIndex) {
                0 -> WomenCategoriesFashionPage(onTabSelected = { println("Women: $it") })
                1 -> MenCategoriesFashionPage(onTabSelected = { println("Men: $it") })
                2 -> KidsCategoriesFashionPage(onTabSelected = { println("Kids: $it") })
                3 -> HomeCategoriesFashionPage(onTabSelected = { println("Home: $it") })
            }
        }
    }
}


@Composable
fun WomenCategoriesFashionPage(onTabSelected: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
//            .verticalScroll(rememberScrollState())
    ) {
        InfoCategoriesF()
//        WomenCategoryList()
    }
}

//@Composable
//fun WomenCategoriesFashionPage(onTabSelected: (String) -> Unit) {
//    Box(Modifier.fillMaxWidth()) {
//        InfoCategoriesF()
//        WomenCategoryList()
////        Text("Women Fashion Content")
//    }
//}

@Composable
fun MenCategoriesFashionPage(onTabSelected: (String) -> Unit) {
    Box(Modifier.fillMaxWidth()) {
        Text("Men Fashion Content")
    }
}

@Composable
fun KidsCategoriesFashionPage(onTabSelected: (String) -> Unit) {
    Box(Modifier.fillMaxWidth()) {
        Text("Kids Fashion Content")
    }
}

@Composable
fun HomeCategoriesFashionPage(onTabSelected: (String) -> Unit) {
    Box(Modifier.fillMaxWidth()) {
        Text("Home Fashion Content")
    }
}

sealed class FashionCategoryTabPage {
    object Women : FashionCategoryTabPage()
    object Men : FashionCategoryTabPage()
    object Kids : FashionCategoryTabPage()
    object Home : FashionCategoryTabPage()
}
