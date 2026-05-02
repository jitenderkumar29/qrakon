package com.example.qrakon.components.restaurants

import com.example.qrakon.R

/**
 * Main RestaurantData object that combines all food items from different restaurant categories.
 * Uses lazy initialization to avoid method size limitations.
 */
object RestaurantData {
    val allFoodItems by lazy {
        mutableListOf<FoodItemDoubleF>().apply {
            // Pizza items
            addAll(RestaurantDataPizza.pizzaItems)

            // Burger items
            addAll(RestaurantDataBurger.burgerItems)

            // Biryani items
            addAll(RestaurantDataBiryani.biryaniItems)

            // North Indian - Kitchen Exotica
            addAll(RestaurantDataNorthIndian.northIndianItems)

            // North Indian - Sanjha Chulha
            addAll(RestaurantDataSanjhaChulha.sanjhaChulhaItems)

            // Kitchen Exotica Badarpur
            addAll(RestaurantDataKitchenExoticaBadarpur.kitchenExoticaBadarpurItems)

            // Biryani By Kilo
            addAll(RestaurantDataBiryaniByKilo.biryaniByKiloItems)

            // Charcoal Eats
            addAll(RestaurantDataCharcoalEats.charcoalEatsItems)

            // Goila Butter Chicken
            addAll(RestaurantDataGoilaButterChicken.goilaButterChickenItems)

            // McDonald's
            addAll(RestaurantDataMcDonalds.mcdonaldsItems)

            // KFC
            addAll(RestaurantDataKFC.kfcItems)

            // Curry Queen
            addAll(RestaurantDataCurryQueen.curryQueenItems)

            // Dana Choga
            addAll(RestaurantDataDanaChoga.danaChogaItems)
        }
    }

    /**
     * Get all food items filtered by category
     * @param category The category to filter by (e.g., "pizza", "burger", "biryani")
     * @return List of FoodItemDoubleF objects matching the category
     */
    fun getItemsByCategory(category: String): List<FoodItemDoubleF> {
        return allFoodItems.filter { it.category.equals(category, ignoreCase = true) }
    }

    /**
     * Get all bestseller items
     * @return List of FoodItemDoubleF objects where bestSeller is true
     */
    fun getBestsellerItems(): List<FoodItemDoubleF> {
        return allFoodItems.filter { it.bestSeller == true }
    }

    /**
     * Get a specific food item by its ID
     * @param id The unique ID of the food item
     * @return FoodItemDoubleF object if found, null otherwise
     */
    fun getItemById(id: Int): FoodItemDoubleF? {
        return allFoodItems.find { it.id == id }
    }

    /**
     * Get all wishlisted items
     * @return List of FoodItemDoubleF objects where isWishlisted is true
     */
    fun getWishlistedItems(): List<FoodItemDoubleF> {
        return allFoodItems.filter { it.isWishlisted == true }
    }

    /**
     * Get all Top Picks items
     * @return List of FoodItemDoubleF objects where toppicks is true
     */
    fun getTopPicksItems(): List<FoodItemDoubleF> {
        return allFoodItems.filter { it.toppicks == true }
    }

    /**
     * Get all Free Delivery items
     * @return List of FoodItemDoubleF objects where freedelivery is true
     */
    fun getFreeDeliveryItems(): List<FoodItemDoubleF> {
        return allFoodItems.filter { it.freedelivery == true }
    }

    /**
     * Get all Combo items
     * @return List of FoodItemDoubleF objects where combo is true
     */
    fun getComboItems(): List<FoodItemDoubleF> {
        return allFoodItems.filter { it.combo == true }
    }

    /**
     * Get all Recommended items
     * @return List of FoodItemDoubleF objects where recommended is true
     */
    fun getRecommendedItems(): List<FoodItemDoubleF> {
        return allFoodItems.filter { it.recommended == true }
    }

    /**
     * Get all Big Value items
     * @return List of FoodItemDoubleF objects where bigValue is true
     */
    fun getBigValueItems(): List<FoodItemDoubleF> {
        return allFoodItems.filter { it.bigValue == true }
    }

    /**
     * Get all Super Saver items
     * @return List of FoodItemDoubleF objects where superSaver is true
     */
    fun getSuperSaverItems(): List<FoodItemDoubleF> {
        return allFoodItems.filter { it.superSaver == true }
    }

    /**
     * Get all Salad items
     * @return List of FoodItemDoubleF objects where salad is true
     */
    fun getSaladItems(): List<FoodItemDoubleF> {
        return allFoodItems.filter { it.salad == true }
    }

    /**
     * Get items by restaurant name
     * @param restaurantName The name of the restaurant
     * @return List of FoodItemDoubleF objects from that restaurant
     */
    fun getItemsByRestaurant(restaurantName: String): List<FoodItemDoubleF> {
        return allFoodItems.filter { it.restaurantName.equals(restaurantName, ignoreCase = true) }
    }

    /**
     * Get high protein items
     * @return List of FoodItemDoubleF objects where isHighProtein is true
     */
    fun getHighProteinItems(): List<FoodItemDoubleF> {
        return allFoodItems.filter { it.isHighProtein == true }
    }
}