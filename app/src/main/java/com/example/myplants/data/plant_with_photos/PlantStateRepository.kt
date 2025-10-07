package com.example.myplants.data.plant_with_photos

import com.example.myplants.dao.PlantStateDao

class PlantStateRepository(
    private val plantStateDao: PlantStateDao
) {
    fun getFavorites() =
        plantStateDao.getFavorites()

    suspend fun setFavorite(plantId: Long, isFavorite: Boolean) =
        plantStateDao.setFavorite(plantId, isFavorite)

    fun getWishlist() =
        plantStateDao.getWishlist()

    suspend fun setWishlist(plantId: Long, isWishlist: Boolean) =
        plantStateDao.setWishlist(plantId, isWishlist)
}