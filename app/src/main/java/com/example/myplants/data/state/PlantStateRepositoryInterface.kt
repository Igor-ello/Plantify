package com.example.myplants.data.state

import com.example.myplants.models.PlantWithPhotos
import kotlinx.coroutines.flow.Flow

interface PlantStateRepositoryInterface {
    fun getFavorites(): Flow<List<PlantWithPhotos>>

    suspend fun setFavorite(plantId: Long, isFavorite: Boolean)

    fun getWishlist(): Flow<List<PlantWithPhotos>>

    suspend fun setWishlist(plantId: Long, isWishlist: Boolean)
}