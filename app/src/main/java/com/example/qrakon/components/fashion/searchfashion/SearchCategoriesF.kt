package com.example.qrakon.components.fashion.searchfashion

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.qrakon.R
import com.example.qrakon.ui.theme.customColors

@Composable
fun SearchCategoriesF(
    modifier: Modifier = Modifier,
    query: String = "",
    onQueryChange: (String) -> Unit = {},
    onSearch: (String) -> Unit = {},
    onQRCodeScan: () -> Unit = {},
    onCategoryClick: () -> Unit = {},
    placeholder: String = "Search categories, styles...",
    enabled: Boolean = true,
    backgroundColor: Color = MaterialTheme.customColors.surface,
    textColor: Color = MaterialTheme.colorScheme.onSurface,
    placeholderColor: Color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
    elevation: Int = 4,
    iconColor: Color = MaterialTheme.colorScheme.primary
) {
    var isFocused by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Category Icon with different styling
//        Box(
//            modifier = Modifier
//                .size(42.dp)
//                .shadow(
//                    elevation = 3.dp,
//                    shape = CircleShape,
//                    clip = true
//                )
//                .clip(CircleShape)
//                .background(MaterialTheme.colorScheme.primaryContainer)
//                .border(
//                    width = 1.5.dp,
//                    color = MaterialTheme.colorScheme.primary,
//                    shape = CircleShape
//                )
//                .clickable { onCategoryClick() },
//            contentAlignment = Alignment.Center
//        ) {
//            Icon(
//                painter = painterResource(id = R.drawable.ic_category_1),
//                contentDescription = "Categories",
//                tint = MaterialTheme.colorScheme.onPrimaryContainer,
//                modifier = Modifier.size(24.dp)
//            )
//        }

        // Search Box with different styling
        Box(
            modifier = Modifier
                .weight(1f)
                .height(45.dp)
                .shadow(
                    elevation = elevation.dp,
                    shape = RoundedCornerShape(8.dp),
                    clip = true
                )
                .clip(RoundedCornerShape(8.dp))
                .background(backgroundColor)
                .border(
                    width = 2.dp,
                    color = if (isFocused) MaterialTheme.customColors.white
                    else MaterialTheme.customColors.white,
//                    else MaterialTheme.colorScheme.outline.copy(alpha = 0.5f),
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(horizontal = 6.dp, vertical = 8.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                // Search icon
//                Icon(
//                    painter = painterResource(id = R.drawable.baseline_search_24),
//                    contentDescription = "Search Categories",
//                    tint = iconColor,
//                    modifier = Modifier
//                        .size(26.dp)
//                        .clickable { onQRCodeScan() }
//                )

                // Search text field
                BasicTextField(
                    value = query,
                    onValueChange = onQueryChange,
                    modifier = Modifier
                        .weight(1f)
                        .focusRequester(focusRequester)
                        .onFocusChanged { isFocused = it.isFocused },
                    textStyle = TextStyle(
                        color = textColor,
                        fontSize = 17.sp,
                        lineHeight = 24.sp,
                        fontWeight = FontWeight.Normal
                    ),
                    cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                    keyboardActions = KeyboardActions(
                        onSearch = {
                            onSearch(query)
                            focusManager.clearFocus()
                        }
                    ),
                    singleLine = true,
                    decorationBox = { innerTextField ->
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            if (query.isEmpty()) {
                                Text(
                                    text = placeholder,
                                    color = placeholderColor,
                                    fontSize = 17.sp,
                                    lineHeight = 24.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                            innerTextField()
                        }
                    }
                )

                // Camera Icon with different styling
                Icon(
                    painter = painterResource(id = R.drawable.outline_photo_camera_24),
                    contentDescription = "Scan QR Code",
                    tint = iconColor,
                    modifier = Modifier
                        .size(26.dp)
                        .clickable { onQRCodeScan() }
                )

                // Clear button with different styling
                if (query.isNotEmpty()) {
                    IconButton(
                        onClick = {
                            onQueryChange("")
                            focusRequester.requestFocus()
                        },
                        modifier = Modifier.size(26.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Clear search",
                            tint = MaterialTheme.colorScheme.error
                        )
                    }
                }
            }
        }

        // Wishlist Icon with different styling
        Box(
            modifier = Modifier
                .size(42.dp)
                .shadow(
                    elevation = 3.dp,
                    shape = CircleShape,
                    clip = true
                )
                .clip(CircleShape)
                .background(MaterialTheme.customColors.white)
                .border(
                    width = 1.5.dp,
                    color = MaterialTheme.customColors.white,
//                    color = MaterialTheme.colorScheme.secondary,
                    shape = CircleShape
                )
                .clickable { onQRCodeScan() },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_wishlist_outline),
                contentDescription = "Wishlist",
                tint = MaterialTheme.colorScheme.onSecondaryContainer,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

// Alternative simplified version
@Composable
fun SearchCategoriesFSimple(
    modifier: Modifier = Modifier,
    query: String = "",
    onQueryChange: (String) -> Unit = {},
    onSearch: (String) -> Unit = {},
    onQRCodeScan: () -> Unit = {},
    onCategoryClick: () -> Unit = {},
    placeholder: String = "Search categories, styles...",
    enabled: Boolean = true,
    backgroundColor: Color = MaterialTheme.customColors.surface,
    textColor: Color = MaterialTheme.colorScheme.onSurface,
    placeholderColor: Color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
    elevation: Int = 2,
    iconColor: Color = MaterialTheme.colorScheme.primary
) {
    var isFocused by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Category Icon with simple styling
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary)
                .clickable { onCategoryClick() },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_category_1),
                contentDescription = "Categories",
                tint = Color.White,
                modifier = Modifier.size(22.dp)
            )
        }

        // Search Box with simplified styling
        Box(
            modifier = Modifier
                .weight(1f)
                .height(46.dp)
                .shadow(
                    elevation = elevation.dp,
                    shape = RoundedCornerShape(16.dp),
                    clip = true
                )
                .clip(RoundedCornerShape(16.dp))
                .background(backgroundColor)
                .border(
                    width = 1.dp,
                    color = if (isFocused) MaterialTheme.colorScheme.primary
                    else MaterialTheme.colorScheme.outline.copy(alpha = 0.2f),
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(horizontal = 12.dp, vertical = 8.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_search_24),
                    contentDescription = "Search Categories",
                    tint = iconColor,
                    modifier = Modifier
                        .size(24.dp)
                        .clickable { onQRCodeScan() }
                )

                BasicTextField(
                    value = query,
                    onValueChange = onQueryChange,
                    modifier = Modifier
                        .weight(1f)
                        .focusRequester(focusRequester)
                        .onFocusChanged { isFocused = it.isFocused },
                    textStyle = TextStyle(
                        color = textColor,
                        fontSize = 16.sp,
                        lineHeight = 24.sp,
                        fontWeight = FontWeight.Normal
                    ),
                    cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                    keyboardActions = KeyboardActions(
                        onSearch = {
                            onSearch(query)
                            focusManager.clearFocus()
                        }
                    ),
                    singleLine = true,
                    decorationBox = { innerTextField ->
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            if (query.isEmpty()) {
                                Text(
                                    text = placeholder,
                                    color = placeholderColor,
                                    fontSize = 16.sp,
                                    lineHeight = 24.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                            innerTextField()
                        }
                    }
                )

                Icon(
                    painter = painterResource(id = R.drawable.outline_photo_camera_24),
                    contentDescription = "Scan QR Code",
                    tint = iconColor,
                    modifier = Modifier
                        .size(24.dp)
                        .clickable { onQRCodeScan() }
                )

                if (query.isNotEmpty()) {
                    IconButton(
                        onClick = {
                            onQueryChange("")
                            focusRequester.requestFocus()
                        },
                        modifier = Modifier.size(24.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Clear search",
                            tint = placeholderColor
                        )
                    }
                }
            }
        }

        // Wishlist Icon with simple styling
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.secondary)
                .clickable { onQRCodeScan() },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_wishlist_outline),
                contentDescription = "Wishlist",
                tint = Color.White,
                modifier = Modifier.size(22.dp)
            )
        }
    }
}

// Preview
@Preview(showBackground = true)
@Composable
fun SearchCategoriesFPreview() {
    MaterialTheme {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .background(MaterialTheme.colorScheme.background)
        ) {
            var query by remember { mutableStateOf("") }
            SearchCategoriesF(
                query = query,
                onQueryChange = { query = it },
                onSearch = { println("Searching categories: $it") },
                onQRCodeScan = { println("QR Code scan requested") },
                onCategoryClick = { println("Category clicked") }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SearchCategoriesFSimplePreview() {
    MaterialTheme {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .background(MaterialTheme.colorScheme.background)
        ) {
            var query by remember { mutableStateOf("") }
            SearchCategoriesFSimple(
                query = query,
                onQueryChange = { query = it },
                onSearch = { println("Searching categories: $it") },
                onQRCodeScan = { println("QR Code scan requested") },
                onCategoryClick = { println("Category clicked") }
            )
        }
    }
}