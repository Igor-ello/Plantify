package com.example.myplants.data.plant_with_photos

import androidx.lifecycle.LiveData
import com.example.myplants.models.PlantWithPhotos

interface PlantStateRepositoryInterface {
    fun getFavorites(): LiveData<List<PlantWithPhotos>>

    suspend fun setFavorite(plantId: Long, isFavorite: Boolean)

    fun getWishlist(): LiveData<List<PlantWithPhotos>>

    suspend fun setWishlist(plantId: Long, isWishlist: Boolean)
}