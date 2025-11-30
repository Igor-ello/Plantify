package com.example.myplants.data.photo

import com.example.myplants.core.data.local.db.dao.PlantPhotoDao
import com.example.myplants.core.data.local.entity.PlantPhoto
import kotlinx.coroutines.flow.Flow

class PhotoRepository(
    private val photoDao: PlantPhotoDao
) : PhotoRepositoryInterface {

    override suspend fun insertPhoto(photo: PlantPhoto): Long =
        photoDao.insertPhoto(photo)

    override suspend fun insertPhotos(photos: List<PlantPhoto>) =
        photoDao.insertPhotos(photos)

    override suspend fun updatePhoto(photo: PlantPhoto) =
        photoDao.updatePhoto(photo)

    override suspend fun setMainPhoto(plantId: Long, photoId: Long) =
        photoDao.setMainPhoto(plantId, photoId)

    override suspend fun getAllPhoto(): List<PlantPhoto> =
        photoDao.getAllPhoto()

    override fun getAllPhotoLive(): Flow<List<PlantPhoto>> =
        photoDao.getAllPhotoLive()

    override suspend fun getPhotosForPlant(plantId: Long): List<PlantPhoto> =
        photoDao.getPhotosForPlant(plantId)

    override suspend fun deletePhotoById(id: Long) =
        photoDao.deletePhotoById(id)

    override suspend fun deleteAllPhoto() =
        photoDao.deleteAllPhoto()

    override suspend fun deletePhotosByPlantId(plantId: Long) =
        photoDao.deletePhotosByPlantId(plantId)
}