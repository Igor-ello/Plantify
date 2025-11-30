package com.example.myplants.data.state

import com.example.myplants.core.data.local.relation.PlantWithPhotos
import kotlinx.coroutines.flow.Flow

interface PlantStateRepositoryInterface {
    fun getFavorites(): Flow<List<PlantWithPhotos>>

    suspend fun setFavorite(plantId: Long, isFavorite: Boolean)

    fun getWishlist(): Flow<List<PlantWithPhotos>>

    suspend fun setWishlist(plantId: Long, isWishlist: Boolean)
}