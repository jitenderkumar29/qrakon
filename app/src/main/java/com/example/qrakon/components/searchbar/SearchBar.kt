package com.example.qrakon.components.searchbar
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.qrakon.R
import com.example.qrakon.ui.theme.customColors

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    query: String = "",
    onQueryChange: (String) -> Unit = {},
    onSearch: (String) -> Unit = {},
    onQRCodeScan: () -> Unit = {},
    placeholder: String = "Search or ask a question",
    enabled: Boolean = true,
    backgroundColor: Color = MaterialTheme.customColors.onPrimary, // Changed to lightAccent
    textColor: Color = MaterialTheme.colorScheme.scrim, // Changed to onPrimary for better contrast
    placeholderColor: Color = MaterialTheme.colorScheme.scrim.copy(alpha = 0.7f), // Adjusted for lightAccent
    elevation: Int = 2,
    qrIconColor: Color = MaterialTheme.colorScheme.scrim // Changed to onPrimary for better contrast
) {
    var isFocused by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Search Box
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
                    width = 1.dp,
                    color = if (isFocused) MaterialTheme.colorScheme.primary
                    else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(horizontal = 8.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                // Search icon
//                Icon(
//                    imageVector = Icons.Default.Search,
//                    contentDescription = "Search",
//                    tint = if (isFocused) MaterialTheme.colorScheme.primary
//                    else placeholderColor,
//                    modifier = Modifier.size(24.dp)
//                )
                Icon(
                    painter = painterResource(id = R.drawable.baseline_search_24),
                    contentDescription = "Search",
                    tint = qrIconColor,
                    modifier = Modifier
                        .size(25.dp)
                        .clickable { onQRCodeScan() }
                )
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
                        fontSize = 16.sp,
                        lineHeight = 24.sp
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

                // camera Icon (inside the search box)
                Icon(
                    painter = painterResource(id = R.drawable.outline_photo_camera_24),
                    contentDescription = "Camera",
                    tint = qrIconColor,
                    modifier = Modifier
                        .size(25.dp)
                        .clickable { onQRCodeScan() }
                )

        //      Mic Icon (inside the search box)
                Icon(
                    painter = painterResource(id = R.drawable.outline_mic_24),
                    contentDescription = "Camera",
                    tint = qrIconColor,
                    modifier = Modifier
                        .size(25.dp)
                        .clickable { onQRCodeScan() }
                )

                // Clear button (only shown when there's text)
                if (query.isNotEmpty()) {
                    IconButton(
                        onClick = {
                            onQueryChange("")
                            focusRequester.requestFocus()
                        },
                        modifier = Modifier.size(25.dp)
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

        // QR Code Scanner Icon (outside the search box)
//        Icon(
//            painter = painterResource(id = R.drawable.outline_qr_code_scanner_24),
//            contentDescription = "Scan QR Code",
//            tint = qrIconColor,
//            modifier = Modifier
//                .size(35.dp)
//                .clickable { onQRCodeScan() }
//        )
    }
}

// Preview
@androidx.compose.ui.tooling.preview.Preview(showBackground = true)
@Composable
fun SearchBarPreview() {
    MaterialTheme {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .background(MaterialTheme.colorScheme.background)
        ) {
            var query by remember { mutableStateOf("") }
            SearchBar(
                query = query,
                onQueryChange = { query = it },
                onSearch = { println("Searching: $it") },
                onQRCodeScan = { println("QR Code scan requested") }
            )
        }
    }
}