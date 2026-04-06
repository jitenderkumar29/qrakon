package com.example.qrakon.components.restaurants

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.qrakon.R
import com.example.qrakon.ui.theme.customColors
import kotlin.collections.chunked
import kotlin.collections.filter
import kotlin.collections.forEach
import kotlin.collections.forEachIndexed
import kotlin.collections.isNotEmpty
import kotlin.collections.map

// Data classes for better type safety and reusability
data class FilterChip(
    val id: String,
    val text: String,
    val type: FilterType,
    val icon: Int? = null,
    val rightIcon: Int? = null,
    val isSelected: Boolean = false,
    val metadata: Map<String, Any> = emptyMap(), // For additional data like price range, rating, etc.
    val customCornerRadius: Int? = null // Optional custom corner radius in dp
)

enum class FilterType {
    FILTER_DROPDOWN,
    SORT_DROPDOWN,
    WITH_LEFT_ICON,
    WITH_RIGHT_ICON,
    WITH_BOTH_ICONS,
    TEXT_ONLY
}

data class FilterConfig(
    val filters: List<FilterChip> = emptyList(),
    val rows: Int = 2, // Number of rows to display
    val rowSpacing: Int = 12, // Spacing between rows in dp
    val chipSpacing: Int = 12, // Spacing between chips in dp
    val chipHeight: Int = 40, // Height of each chip in dp
    val chipPadding: Int = 16, // Horizontal padding for chips in dp
    val showDividers: Boolean = false, // Whether to show dividers between rows
    val autoPartition: Boolean = true, // Whether to automatically partition filters into rows
    val cornerRadius: Int? = null // Optional global corner radius override (null = use chipHeight/2)
)

@Composable
fun FilterButtonFood(
    modifier: Modifier = Modifier,
    filterConfig: FilterConfig = FilterConfig(),
    onFilterClick: (FilterChip) -> Unit = {},
    onSortClick: () -> Unit = {}
) {
    if (filterConfig.filters.isEmpty()) {
        return // Don't render anything if no filters provided
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        val filterRows = if (filterConfig.autoPartition) {
            partitionFilters(filterConfig.filters, filterConfig.rows)
        } else {
            // If not auto-partitioning, assume filters are already organized by rows
            filterConfig.filters.chunked(filterConfig.filters.size / filterConfig.rows)
        }

        filterRows.forEachIndexed { index, rowFilters ->
            if (rowFilters.isNotEmpty()) {
                if (index > 0) {
                    Spacer(modifier = Modifier.height(filterConfig.rowSpacing.dp))
                }

                FilterRow(
                    filters = rowFilters,
                    chipHeight = filterConfig.chipHeight,
                    chipSpacing = filterConfig.chipSpacing,
                    chipPadding = filterConfig.chipPadding,
                    cornerRadius = filterConfig.cornerRadius,
                    onFilterClick = onFilterClick,
                    onSortClick = onSortClick
                )
            }
        }
    }
}

@Composable
private fun FilterRow(
    filters: List<FilterChip>,
    chipHeight: Int,
    chipSpacing: Int,
    chipPadding: Int,
    cornerRadius: Int?,
    onFilterClick: (FilterChip) -> Unit,
    onSortClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(chipSpacing.dp)
    ) {
        filters.forEach { filter ->
            FilterChipItem(
                filter = filter,
                chipHeight = chipHeight,
                chipPadding = chipPadding,
                cornerRadius = cornerRadius,
                onFilterClick = onFilterClick,
                onSortClick = onSortClick
            )
        }
    }
}

@Composable
private fun FilterChipItem(
    filter: FilterChip,
    chipHeight: Int,
    chipPadding: Int,
    cornerRadius: Int?,
    onFilterClick: (FilterChip) -> Unit,
    onSortClick: () -> Unit
) {
    when (filter.type) {
        FilterType.FILTER_DROPDOWN -> {
            FilterChipWithBothIcons(
                leftIcon = filter.icon ?: R.drawable.ic_filter,
                text = filter.text,
                rightIcon = filter.rightIcon ?: R.drawable.outline_keyboard_arrow_down_24,
                isSelected = filter.isSelected,
                chipHeight = chipHeight,
                chipPadding = chipPadding,
                cornerRadius = filter.customCornerRadius ?: cornerRadius,
                onClick = { onFilterClick(filter) }
            )
        }
        FilterType.SORT_DROPDOWN -> {
            FilterChipWithRightIcon(
                text = filter.text,
                rightIcon = filter.rightIcon ?: R.drawable.outline_keyboard_arrow_down_24,
                isSelected = filter.isSelected,
                chipHeight = chipHeight,
                chipPadding = chipPadding,
                cornerRadius = filter.customCornerRadius ?: cornerRadius,
                onClick = { onSortClick() }
            )
        }
        FilterType.WITH_LEFT_ICON -> {
            FilterChipWithLeftIcon(
                icon = filter.icon ?: R.drawable.ic_pizza_cheese_burst,
                text = filter.text,
                isSelected = filter.isSelected,
                chipHeight = chipHeight,
                chipPadding = chipPadding,
                cornerRadius = filter.customCornerRadius ?: cornerRadius,
                onClick = { onFilterClick(filter) }
            )
        }
        FilterType.WITH_RIGHT_ICON -> {
            FilterChipWithRightIcon(
                text = filter.text,
                rightIcon = filter.rightIcon ?: R.drawable.outline_keyboard_arrow_down_24,
                isSelected = filter.isSelected,
                chipHeight = chipHeight,
                chipPadding = chipPadding,
                cornerRadius = filter.customCornerRadius ?: cornerRadius,
                onClick = { onFilterClick(filter) }
            )
        }
        FilterType.WITH_BOTH_ICONS -> {
            FilterChipWithBothIcons(
                leftIcon = filter.icon ?: R.drawable.ic_filter,
                text = filter.text,
                rightIcon = filter.rightIcon ?: R.drawable.outline_keyboard_arrow_down_24,
                isSelected = filter.isSelected,
                chipHeight = chipHeight,
                chipPadding = chipPadding,
                cornerRadius = filter.customCornerRadius ?: cornerRadius,
                onClick = { onFilterClick(filter) }
            )
        }
        FilterType.TEXT_ONLY -> {
            FilterChipTextOnly(
                text = filter.text,
                isSelected = filter.isSelected,
                chipHeight = chipHeight,
                chipPadding = chipPadding,
                cornerRadius = filter.customCornerRadius ?: cornerRadius,
                onClick = { onFilterClick(filter) }
            )
        }
    }
}

// Helper function to partition filters into rows
private fun partitionFilters(filters: List<FilterChip>, rows: Int): List<List<FilterChip>> {
    if (rows <= 1) return listOf(filters)

    val itemsPerRow = (filters.size + rows - 1) / rows // Ceiling division
    return filters.chunked(itemsPerRow)
}

// Helper function to get dynamic corner radius
private fun getCornerRadius(chipHeight: Int, customRadius: Int?): RoundedCornerShape {
    val radius = customRadius ?: (chipHeight / 2)
    return RoundedCornerShape(radius.dp)
}

// Reusable component for chips with left icon only
@Composable
fun FilterChipWithLeftIcon(
    icon: Int,
    text: String,
    isSelected: Boolean = false,
    chipHeight: Int = 40,
    chipPadding: Int = 16,
    cornerRadius: Int? = null,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = getFilterChipColors(isSelected)
    val cornerShape = getCornerRadius(chipHeight, cornerRadius)

    Row(
        modifier = modifier
            .height(chipHeight.dp)
            .clip(cornerShape)
            .background(colors.backgroundColor)
            .border(
                width = 1.dp,
                color = colors.borderColor,
                shape = cornerShape
            )
            .clickable { onClick() }
            .padding(horizontal = chipPadding.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = icon),
            contentDescription = null,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = text,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = colors.textColor
        )
    }
}

// Reusable component for chips with right icon only
@Composable
fun FilterChipWithRightIcon(
    text: String,
    rightIcon: Int,
    isSelected: Boolean = false,
    chipHeight: Int = 40,
    chipPadding: Int = 16,
    cornerRadius: Int? = null,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = getFilterChipColors(isSelected)
    val cornerShape = getCornerRadius(chipHeight, cornerRadius)

    Row(
        modifier = modifier
            .height(chipHeight.dp)
            .clip(cornerShape)
            .background(colors.backgroundColor)
            .border(
                width = 1.dp,
                color = colors.borderColor,
                shape = cornerShape
            )
            .clickable { onClick() }
            .padding(horizontal = chipPadding.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = colors.textColor
        )
        Spacer(modifier = Modifier.width(8.dp))
        Image(
            painter = painterResource(id = rightIcon),
            contentDescription = null,
            modifier = Modifier.size(24.dp)
        )
    }
}

// Reusable component for chips with both left and right icons
@Composable
fun FilterChipWithBothIcons(
    leftIcon: Int,
    text: String,
    rightIcon: Int,
    isSelected: Boolean = false,
    chipHeight: Int = 40,
    chipPadding: Int = 16,
    cornerRadius: Int? = null,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = getFilterChipColors(isSelected)
    val cornerShape = getCornerRadius(chipHeight, cornerRadius)

    Row(
        modifier = modifier
            .height(chipHeight.dp)
            .clip(cornerShape)
            .background(colors.backgroundColor)
            .border(
                width = 1.dp,
                color = colors.borderColor,
                shape = cornerShape
            )
            .clickable { onClick() }
            .padding(horizontal = chipPadding.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = leftIcon),
            contentDescription = null,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = text,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = colors.textColor
        )
        Spacer(modifier = Modifier.width(8.dp))
        Image(
            painter = painterResource(id = rightIcon),
            contentDescription = null,
            modifier = Modifier.size(24.dp)
        )
    }
}

// Reusable component for text-only chips
@Composable
fun FilterChipTextOnly(
    text: String,
    isSelected: Boolean = false,
    chipHeight: Int = 40,
    chipPadding: Int = 16,
    cornerRadius: Int? = null,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = getFilterChipColors(isSelected)
    val cornerShape = getCornerRadius(chipHeight, cornerRadius)

    Row(
        modifier = modifier
            .height(chipHeight.dp)
            .clip(cornerShape)
            .background(colors.backgroundColor)
            .border(
                width = 1.dp,
                color = colors.borderColor,
                shape = cornerShape
            )
            .clickable { onClick() }
            .padding(horizontal = chipPadding.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = colors.textColor
        )
    }
}

// Helper data class for filter chip colors
private data class FilterChipColors(
    val backgroundColor: Color,
    val borderColor: Color,
    val textColor: Color
)

// FIXED: Added @Composable annotation to this function
@Composable
private fun getFilterChipColors(isSelected: Boolean): FilterChipColors {
    val customColors = MaterialTheme.customColors
    return if (isSelected) {
        FilterChipColors(
            backgroundColor = customColors.primary.copy(alpha = 0.1f),
            borderColor = customColors.primary,
            textColor = customColors.primary
        )
    } else {
        FilterChipColors(
            backgroundColor = customColors.white,
            borderColor = customColors.gray.copy(alpha = 0.3f),
            textColor = customColors.black
        )
    }
}

// Predefined filter configurations for common use cases
object FilterPresets {

    // Pizza filters
    val pizzaFilters = FilterConfig(
        filters = listOf(
            FilterChip(
                id = "filters",
                text = "Filters",
                type = FilterType.FILTER_DROPDOWN,
                icon = R.drawable.ic_filter,
                rightIcon = R.drawable.outline_keyboard_arrow_down_24
            ),
            FilterChip(
                id = "sort",
                text = "Sort by",
                type = FilterType.SORT_DROPDOWN,
                rightIcon = R.drawable.outline_keyboard_arrow_down_24
            ),
            FilterChip(
                id = "cheese_burst",
                text = "Cheese Burst",
                type = FilterType.WITH_LEFT_ICON,
                icon = R.drawable.ic_pizza_cheese_burst
            ),
            FilterChip(
                id = "farmhouse",
                text = "Farmhouse",
                type = FilterType.WITH_LEFT_ICON,
                icon = R.drawable.ic_pizza_farmhouse
            ),
            FilterChip(
                id = "under_150",
                text = "Under ₹150",
                type = FilterType.TEXT_ONLY
            ),
            FilterChip(
                id = "rating_4",
                text = "Rating 4.0+",
                type = FilterType.TEXT_ONLY
            )
        ),
        rows = 2
    )

    // Burger filters
    val burgerFilters = FilterConfig(
        filters = listOf(
            FilterChip(
                id = "filters",
                text = "Filters",
                type = FilterType.FILTER_DROPDOWN,
                icon = R.drawable.ic_filter,
                rightIcon = R.drawable.outline_keyboard_arrow_down_24
            ),
            FilterChip(
                id = "veg",
                text = "Veg Only",
                type = FilterType.TEXT_ONLY
            ),
            FilterChip(
                id = "chicken",
                text = "Chicken",
                type = FilterType.WITH_LEFT_ICON,
                icon = R.drawable.ic_non_veg_pepperoni
            ),
            FilterChip(
                id = "cheese",
                text = "Extra Cheese",
                type = FilterType.TEXT_ONLY
            )
        ),
        rows = 1
    )

    // Minimal filters
    val minimalFilters = FilterConfig(
        filters = listOf(
            FilterChip(
                id = "sort",
                text = "Sort",
                type = FilterType.SORT_DROPDOWN,
                rightIcon = R.drawable.outline_keyboard_arrow_down_24
            )
        ),
        rows = 1
    )

    // Restaurant filters
    val restaurantFilters = FilterConfig(
        filters = listOf(
            FilterChip(
                id = "filters",
                text = "Filters",
                type = FilterType.FILTER_DROPDOWN,
                icon = R.drawable.ic_filter,
                rightIcon = R.drawable.outline_keyboard_arrow_down_24
            ),
            FilterChip(
                id = "sort",
                text = "Sort by",
                type = FilterType.SORT_DROPDOWN,
                rightIcon = R.drawable.outline_keyboard_arrow_down_24
            ),
            FilterChip(
                id = "rating_4",
                text = "Rating 4.0+",
                type = FilterType.TEXT_ONLY
            ),
            FilterChip(
                id = "pure_veg",
                text = "Pure Veg",
                type = FilterType.TEXT_ONLY
            ),
            FilterChip(
                id = "under_30",
                text = "Under 30 mins",
                type = FilterType.TEXT_ONLY
            ),
            FilterChip(
                id = "offers",
                text = "Offers",
                type = FilterType.TEXT_ONLY
            )
        ),
        rows = 2
    )
}

// Extension function for easy filter creation
fun createFilterChip(
    id: String,
    text: String,
    type: FilterType,
    icon: Int? = null,
    rightIcon: Int? = null,
    isSelected: Boolean = false,
    metadata: Map<String, Any> = emptyMap(),
    customCornerRadius: Int? = null
): FilterChip {
    return FilterChip(
        id = id,
        text = text,
        type = type,
        icon = icon,
        rightIcon = rightIcon,
        isSelected = isSelected,
        metadata = metadata,
        customCornerRadius = customCornerRadius
    )
}

// Usage examples with the new reusable approach:
@Composable
fun ExampleUsageFilter() {
    // Using preset configurations
    FilterButtonFood(
        filterConfig = FilterPresets.pizzaFilters,
        onFilterClick = { filter ->
            println("Filter clicked: ${filter.text}")
            // Handle filter logic
        },
        onSortClick = {
            println("Sort clicked")
            // Handle sort logic
        }
    )

    // Custom configuration with custom corner radius
    FilterButtonFood(
        filterConfig = FilterConfig(
            filters = listOf(
                createFilterChip(
                    id = "custom_filter",
                    text = "Custom Filter",
                    type = FilterType.TEXT_ONLY,
                    isSelected = true,
                    customCornerRadius = 8 // Individual chip custom radius
                ),
                createFilterChip(
                    id = "another_filter",
                    text = "Another",
                    type = FilterType.WITH_LEFT_ICON,
                    icon = R.drawable.ic_filter
                )
            ),
            rows = 1,
            chipHeight = 36,
            chipSpacing = 8,
            cornerRadius = 18 // Global corner radius (pill shape for 36dp height)
        ),
        onFilterClick = { filter ->
            println("Custom filter clicked: ${filter.text}")
        },
        onSortClick = {
            println("Custom sort clicked")
        }
    )

    // Example with square corners
    FilterButtonFood(
        filterConfig = FilterConfig(
            filters = listOf(
                createFilterChip(
                    id = "square_chip",
                    text = "Square Corners",
                    type = FilterType.TEXT_ONLY
                )
            ),
            chipHeight = 40,
            cornerRadius = 4 // Small radius for slightly rounded corners
        ),
        onFilterClick = { }
    )

    // Empty case - won't render anything
    FilterButtonFood(filterConfig = FilterConfig())

    // Single row configuration
    FilterButtonFood(
        filterConfig = FilterPresets.minimalFilters,
        onFilterClick = { filter ->
            println("Minimal filter clicked: ${filter.text}")
        },
        onSortClick = {
            println("Minimal sort clicked")
        }
    )
}

// Helper functions for common operations
fun updateFilterSelection(filters: List<FilterChip>, selectedId: String): List<FilterChip> {
    return filters.map { filter ->
        if (filter.id == selectedId) {
            filter.copy(isSelected = !filter.isSelected)
        } else {
            filter
        }
    }
}

fun getSelectedFilters(filters: List<FilterChip>): List<FilterChip> {
    return filters.filter { it.isSelected }
}

fun clearAllSelections(filters: List<FilterChip>): List<FilterChip> {
    return filters.map { it.copy(isSelected = false) }
}

// Helper functions implementation (optional)
fun showFilterDialog() {
    println("Show filter dialog")
}

fun showSortOptionsDialog() {
    println("Show sort options dialog")
}

fun applyFilters(selectedFilters: List<FilterChip>) {
    println("Applied filters: ${selectedFilters.map { it.text }}")
}

fun applySorting(sortOption: String) {
    println("Applied sorting: $sortOption")
}