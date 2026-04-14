package com.example.qrakon.components.restaurants

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.qrakon.R
import com.example.qrakon.ui.theme.customColors

// Data class for add-on items
data class AddOnItem(
    val id: Int,
    val name: String,
    val price: Int,
    val iconRes: Int? = null,
    val isSelected: Boolean = false,
    val isBestSeller: Boolean = false,
    val isUnavailable: Boolean = false,
    val selectionType: SelectionType = SelectionType.CHECKBOX
)

enum class SelectionType {
    CHECKBOX,
    RADIO
}

// Data class for category sections
data class AddOnCategory(
    val title: String,
    val subtitle: String? = null,
    val selectLimit: String? = null,
    val items: List<AddOnItem>,
    val selectionType: SelectionType = SelectionType.CHECKBOX
)

// COLORS
val SectionBg = Color(0xFFF3F3F3)
val CardBg = Color.White

// -------------------- MAIN UI --------------------

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddRestItemPopUp(
    mainItemName: String,
    mainItemPrice: Int, mainItemIconRes: Int? = null,
    restaurantName: String,
    deliveryTime: String,
    location: String,
    rating: Double,
    ratingCount: String,
    categories: List<AddOnCategory>,
    onDismiss: () -> Unit,
    onAddToCart: (totalPrice: Int,
                  selectedItems: Map<Int, List<Int>>) -> Unit,
) {

    val selectedItemsState = remember { mutableStateOf(mutableMapOf<Int, MutableList<Int>>()) }
    val radioSelectionState = remember { mutableStateOf(mutableMapOf<Int, Int?>()) }
    var quantity by remember { mutableStateOf(1) }

    // -------------------- PRICE --------------------
    val totalPrice = remember(quantity, selectedItemsState.value, radioSelectionState.value) {
        var total = mainItemPrice * quantity

        selectedItemsState.value.values.forEach { list ->
            list.forEach { id ->
                categories.forEach { cat ->
                    cat.items.find { it.id == id }?.let { total += it.price }
                }
            }
        }

        radioSelectionState.value.values.forEach { selectedId ->
            selectedId?.let { id ->
                categories.forEach { cat ->
                    cat.items.find { it.id == id }?.let { total += it.price }
                }
            }
        }

        total
    }

    // -------------------- SHEET --------------------
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = Color.White,
        dragHandle = null
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // 🔥 CLOSE BUTTON - Top Center
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
                    .background(color = Color.Transparent),
                contentAlignment = Alignment.Center
            ) {
                IconButton(
                    onClick = onDismiss,
                    modifier = Modifier
                        .size(40.dp)
                        .background(
                            color = Color.Black.copy(alpha = 0.6f),
                            shape = RoundedCornerShape(50)
                        )
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close",
                        tint = Color.White
                    )
                }
            }

            // -------------------- SCROLLABLE CONTENT --------------------
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                // HEADER
                item {
                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        mainItemIconRes?.let {
                            Surface(
                                modifier = Modifier.size(28.dp),
                                shape = RoundedCornerShape(8.dp),
                                color = Color.Transparent
                            ) {
                                Icon(
                                    painter = painterResource(it),
                                    contentDescription = null,
                                    tint = Color.Unspecified,
                                    modifier = Modifier.fillMaxSize()
                                )
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                        }

                        Text(
                            text = mainItemName,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))
                }

                // -------------------- CATEGORIES --------------------
                categories.forEach { category ->
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(SectionBg)
                                .padding(12.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column {
                                    Text(
                                        category.title,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 16.sp
                                    )
                                    category.subtitle?.let {
                                        Text(it, fontSize = 12.sp, color = Color.Gray)
                                    }
                                }

                                if (category.selectionType == SelectionType.CHECKBOX && category.items.size > 1) {
                                    TextButton(
                                        onClick = {
                                            val map = selectedItemsState.value.toMutableMap()
                                            val categoryKey = category.title.hashCode()

                                            val allSelected = category.items.all { item ->
                                                map[categoryKey]?.contains(item.id) == true
                                            }

                                            if (allSelected) {
                                                map[categoryKey] = mutableListOf()
                                            } else {
                                                val allItemIds = category.items
                                                    .filter { !it.isUnavailable }
                                                    .map { it.id }
                                                    .toMutableList()
                                                map[categoryKey] = allItemIds
                                            }
                                            selectedItemsState.value = map
                                        }
                                    ) {
                                        Text(
                                            "Select All",
                                            color = MaterialTheme.customColors.success,
                                            fontSize = 13.sp,
                                            fontWeight = FontWeight.Medium
                                        )
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(CardBg, RoundedCornerShape(12.dp))
                                    .padding(horizontal = 12.dp, vertical = 8.dp)
                            ) {
                                category.items.forEachIndexed { index, item ->
                                    when (category.selectionType) {
                                        SelectionType.CHECKBOX -> {
                                            AddOnItemRowWithCheckbox(
                                                item = item,
                                                isSelected = selectedItemsState.value[category.title.hashCode()]?.contains(item.id) == true,
                                                onSelectionChange = { checked ->
                                                    val map = selectedItemsState.value.toMutableMap()
                                                    val list = map[category.title.hashCode()]?.toMutableList() ?: mutableListOf()

                                                    if (checked) list.add(item.id)
                                                    else list.remove(item.id)

                                                    map[category.title.hashCode()] = list
                                                    selectedItemsState.value = map
                                                }
                                            )
                                        }
                                        SelectionType.RADIO -> {
                                            AddOnItemRowWithRadio(
                                                item = item,
                                                isSelected = radioSelectionState.value[category.title.hashCode()] == item.id,
                                                onSelectionChange = {
                                                    val map = radioSelectionState.value.toMutableMap()
                                                    map[category.title.hashCode()] = it
                                                    radioSelectionState.value = map
                                                }
                                            )
                                        }
                                    }

                                    if (index != category.items.lastIndex) {
                                        Spacer(modifier = Modifier.height(12.dp))
                                    }
                                }
                            }
                        }
                    }
                }

                // Bottom padding to ensure content doesn't touch the edge
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }

            // -------------------- STICKY BOTTOM BAR --------------------
            BottomBar(
                quantity = quantity,
                onIncrease = { quantity++ },
                onDecrease = { if (quantity > 1) quantity-- },
                totalPrice = totalPrice,
                onAddClick = {
                    val selectedMap = selectedItemsState.value.mapValues { it.value.toList() }
                    onAddToCart(totalPrice, selectedMap)
                    onDismiss()
                }
            )
        }
    }
}

// -------------------- BOTTOM BAR --------------------
@Composable
fun BottomBar(
    quantity: Int,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit,
    totalPrice: Int,
    onAddClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        // 🔢 QUANTITY SELECTOR
        Row(
            modifier = Modifier
                .border(1.dp, Color.LightGray, RoundedCornerShape(8.dp))
                .height(40.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            TextButton(onClick = onDecrease) {
                Text("-", fontSize = 18.sp, color = Color.Black)
            }

            Text(
                text = "$quantity",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 8.dp)
            )

            TextButton(onClick = onIncrease) {
                Text("+", fontSize = 18.sp, color = Color.Black)
            }
        }

        Spacer(modifier = Modifier.width(12.dp))

        // 🟢 ADD BUTTON
        Button(
            onClick = onAddClick,
            modifier = Modifier
                .weight(1f)
                .height(40.dp),
            shape = RoundedCornerShape(5.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.customColors.success
            )
        ) {
            Text(
                text = "Add Item | ₹$totalPrice",
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
}

// -------------------- ROWS --------------------

@Composable
fun AddOnItemRowWithCheckbox(
    item: AddOnItem,
    isSelected: Boolean,
    onSelectionChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            item.iconRes?.let {
                Icon(
                    painter = painterResource(it),
                    contentDescription = null,
                    tint = Color.Unspecified,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
            }

            Text(
                item.name,
                fontSize = 14.sp,
                color = if (item.isUnavailable) Color.Gray else Color.Black,
                textDecoration = if (item.isUnavailable) TextDecoration.LineThrough else null
            )

            if (item.isBestSeller) {
                Spacer(modifier = Modifier.width(8.dp))
                Box(
                    modifier = Modifier
                        .background(
                            color = Color(0xFFFFE0B2),
                            shape = RoundedCornerShape(4.dp)
                        )
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = "Bestseller",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFFFF6F00)
                    )
                }
            }
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            if (!item.isUnavailable) {
                Text(
                    text = "+ ₹${item.price}",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.width(8.dp))
                Checkbox(
                    checked = isSelected,
                    onCheckedChange = onSelectionChange,
                    modifier = Modifier.size(20.dp)
                )
            } else {
                Text(
                    text = "Unavailable",
                    fontSize = 12.sp,
                    color = Color.Red
                )
            }
        }
    }
}

@Composable
fun AddOnItemRowWithRadio(
    item: AddOnItem,
    isSelected: Boolean,
    onSelectionChange: (Int?) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            item.iconRes?.let {
                Icon(
                    painter = painterResource(it),
                    contentDescription = null,
                    tint = Color.Unspecified,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
            }

            Text(
                item.name,
                fontSize = 14.sp,
                color = if (item.isUnavailable) Color.Gray else Color.Black,
                textDecoration = if (item.isUnavailable) TextDecoration.LineThrough else null
            )

            if (item.isBestSeller) {
                Spacer(modifier = Modifier.width(8.dp))
                Box(
                    modifier = Modifier
                        .background(
                            color = Color(0xFFFFE0B2),
                            shape = RoundedCornerShape(4.dp)
                        )
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = "Bestseller",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFFFF6F00)
                    )
                }
            }
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            if (!item.isUnavailable) {
                Text(
                    text = "+ ₹${item.price}",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.width(8.dp))
                RadioButton(
                    selected = isSelected,
                    onClick = { onSelectionChange(item.id) },
                    modifier = Modifier.size(20.dp)
                )
            } else {
                Text(
                    text = "Unavailable",
                    fontSize = 12.sp,
                    color = Color.Red
                )
            }
        }
    }
}

// Preview
@Preview(showBackground = true, heightDp = 800)
@Composable
fun PreviewAddRestItemPopUp() {
    val sampleCategories = listOf(
        AddOnCategory(
            title = "Add Extra Gravy",
            selectionType = SelectionType.RADIO,
            items = listOf(
                AddOnItem(
                    id = 1,
                    name = "Gravy (80g)",
                    price = 49,
                    iconRes = R.drawable.ic_veg_rest
                )
            )
        ),
        AddOnCategory(
            title = "Add a Starter",
            subtitle = "Select upto 4",
            selectLimit = "Select upto 4",
            selectionType = SelectionType.CHECKBOX,
            items = listOf(
                AddOnItem(
                    id = 2,
                    name = "Cheesy Chicken Meatballs (3 pcs)",
                    price = 69,
                    iconRes = R.drawable.ic_non_veg_rest
                ),
                AddOnItem(
                    id = 3,
                    name = "French Fries (M)",
                    price = 99,
                    iconRes = R.drawable.ic_veg_rest
                ),
                AddOnItem(
                    id = 4,
                    name = "Falafel Nuggets with Mayo Dip (12 pcs)",
                    price = 109,
                    iconRes = R.drawable.ic_veg_rest
                ),
                AddOnItem(
                    id = 5,
                    name = "Chicken Tikki (5 pcs)",
                    price = 139,
                    iconRes = R.drawable.ic_non_veg_rest
                )
            )
        ),
        AddOnCategory(
            title = "Add a Beverage",
            subtitle = "Select upto 5",
            selectLimit = "Select upto 5",
            selectionType = SelectionType.CHECKBOX,
            items = listOf(
                AddOnItem(
                    id = 6,
                    name = "Masala Lemonade (200 ml)",
                    price = 59,
                    iconRes = R.drawable.ic_veg_rest
                ),
                AddOnItem(
                    id = 7,
                    name = "Jeera Masala Cola (250 ml)",
                    price = 59,
                    iconRes = R.drawable.ic_veg_rest
                ),
                AddOnItem(
                    id = 8,
                    name = "Coca-Cola Bottle (475 ml)",
                    price = 69,
                    iconRes = R.drawable.ic_veg_rest
                ),
                AddOnItem(
                    id = 9,
                    name = "Lemon Ice Tea (250 ml)",
                    price = 79,
                    iconRes = R.drawable.ic_veg_rest
                )
            )
        ),
        AddOnCategory(
            title = "Add a Dessert",
            subtitle = "Select upto 4",
            selectLimit = "Select upto 4",
            selectionType = SelectionType.CHECKBOX,
            items = listOf(
                AddOnItem(
                    id = 10,
                    name = "Gulab Jamun (1 pc)",
                    price = 29,
                    isBestSeller = true,
                    iconRes = R.drawable.ic_veg_rest
                ),
                AddOnItem(
                    id = 11,
                    name = "Gulab Jamun (2 pcs)",
                    price = 58,
                    isBestSeller = true,
                    iconRes = R.drawable.ic_veg_rest
                ),
                AddOnItem(
                    id = 12,
                    name = "Choco Chip Brownie",
                    price = 109,
                    iconRes = R.drawable.ic_veg_rest
                ),
                AddOnItem(
                    id = 13,
                    name = "Walnut Brownie",
                    price = 109,
                    iconRes = R.drawable.ic_veg_rest
                ),
                AddOnItem(
                    id = 14,
                    name = "Choco Lava Cake",
                    price = 0,
                    isUnavailable = true,
                    iconRes = R.drawable.ic_veg_rest
                )
            )
        )
    )

    AddRestItemPopUp(
        mainItemName = "Grilled Chicken Rice Bowl with...",
        mainItemPrice = 359,
        mainItemIconRes = R.drawable.restaurant_image_special_chicken_hyderabadi_boneless_biryani_85,
        restaurantName = "The Good Bowl",
        deliveryTime = "35-40 mins",
        location = "Sarita Vihari",
        rating = 4.4,
        ratingCount = "3.2K+",
        categories = sampleCategories,
        onDismiss = {},
        onAddToCart = { totalPrice, selectedItems ->
            println("Added to cart: ₹$totalPrice")
        }
    )
}