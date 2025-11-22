package com.example.myplants.data.state

import com.example.myplants.core.data.local.db.dao.PlantStateDao

class PlantStateRepository(
    private val plantStateDao: PlantStateDao
): PlantStateRepositoryInterface {
    override fun getFavorites() =
        plantStateDao.getFavorites()

    override suspend fun setFavorite(plantId: Long, isFavorite: Boolean) =
        plantStateDao.setFavorite(plantId, isFavorite)

    override fun getWishlist() =
        plantStateDao.getWishlist()

    override suspend fun setWishlist(plantId: Long, isWishlist: Boolean) =
        plantStateDao.setWishlist(plantId, isWishlist)
}