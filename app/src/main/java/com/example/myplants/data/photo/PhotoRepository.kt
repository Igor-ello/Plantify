package com.example.myplants.data.photo

import com.example.myplants.core.data.local.db.dao.PlantPhotoDao
import com.example.myplants.core.data.local.entity.PlantPhoto

class PhotoRepository(
    private val photoDao: PlantPhotoDao
) : PhotoRepositoryInterface {

    override suspend fun insertPhoto(photo: PlantPhoto) =
        photoDao.insertPhoto(photo)

    override suspend fun insertPhotos(photos: List<PlantPhoto>) =
        photoDao.insertPhotos(photos)

    override suspend fun updatePhoto(photo: PlantPhoto) =
        photoDao.updatePhoto(photo)

    override suspend fun deletePhotoById(id: Long) =
        photoDao.deletePhotoById(id)

    override suspend fun setMainPhoto(plantId: Long, photoId: Long) =
        photoDao.setMainPhoto(plantId, photoId)

    override suspend fun getPhotosForPlant(plantId: Long) =
        photoDao.getPhotosForPlant(plantId)

    override suspend fun deleteAllPhoto() =
        photoDao.deleteAllPhoto()

    override suspend fun getAllPhoto() =
        photoDao.getAllPhoto()

    override suspend fun deletePhotosByPlantId(plantId: Long) =
        photoDao.deletePhotosByPlantId(plantId)
}