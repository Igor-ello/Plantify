package com.example.myplants.ui.utils

sealed class Routes(val route: String) {
    object AllPlants : Routes("all_plants")
    object AddPlant : Routes("add_plant")
    object Favorites : Routes("favorites")
    object Wishlist : Routes("wishlist")
    object Settings : Routes("settings")
    object Help : Routes("help")

    object PlantDetail : Routes("plant_detail/{plantId}") {
        fun createRoute(plantId: Long) = "plant_detail/$plantId"
    }

    object GenusDetail : Routes("genus_detail/{genusId}") {
        fun createRoute(genusId: Long) = "genus_detail/$genusId"
    }
}