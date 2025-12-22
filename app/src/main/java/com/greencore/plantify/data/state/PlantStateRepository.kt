package com.greencore.plantify.data.state

import com.greencore.plantify.core.data.local.db.dao.PlantStateDao
import com.greencore.plantify.core.data.local.relation.PlantWithPhotos
import kotlinx.coroutines.flow.Flow

class PlantStateRepository(
    private val plantStateDao: PlantStateDao
): PlantStateRepositoryInterface {
    override fun getFavorites(): Flow<List<PlantWithPhotos>> =
        plantStateDao.getFavorites()

    override suspend fun setFavorite(plantId: Long, isFavorite: Boolean) =
        plantStateDao.setFavorite(plantId, isFavorite)

    override fun getWishlist(): Flow<List<PlantWithPhotos>> =
        plantStateDao.getWishlist()

    override suspend fun setWishlist(plantId: Long, isWishlist: Boolean) =
        plantStateDao.setWishlist(plantId, isWishlist)
}