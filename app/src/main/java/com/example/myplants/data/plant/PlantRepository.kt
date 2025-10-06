package com.example.myplants.data.plant

import androidx.lifecycle.LiveData
import com.example.myplants.dao.GenusDao
import com.example.myplants.dao.PlantDao
import com.example.myplants.dao.PlantPhotoDao
import com.example.myplants.models.Genus
import com.example.myplants.models.Plant
import com.example.myplants.models.PlantPhoto
import com.example.myplants.models.PlantWithPhotos

class PlantRepository(
    private val plantDao: PlantDao,
    private val plantPhotoDao: PlantPhotoDao,
    private val genusDao: GenusDao
) : PlantRepositoryInterface {

    // Растения с их фотографиями
    override fun getAllPlantsWithPhotos(): LiveData<List<PlantWithPhotos>> =
        plantDao.getAllWithPhotos()

    override fun getPlantWithPhotosById(plantId: Long): LiveData<PlantWithPhotos?> =
        plantDao.getByIdWithPhotos(plantId)


    // Работа с растениями
    override suspend fun insertPlant(plant: Plant): Long = plantDao.insert(plant)

    override suspend fun updatePlant(plant: Plant) = plantDao.update(plant)

    override suspend fun getAllPlants(): List<Plant> = plantDao.getAllPlants()

    // Favourite
    override fun getFavorites(): LiveData<List<PlantWithPhotos>> = plantDao.getFavorites()

    override suspend fun setFavorite(plantId: Long, isFavorite: Boolean) =
        plantDao.setFavorite(plantId, isFavorite)

    // Wishlist

    override fun getWishlist(): LiveData<List<PlantWithPhotos>> = plantDao.getWishlist()

    override suspend fun setWishlist(plantId: Long, isWishlist: Boolean) =
        plantDao.setWishlist(plantId, isWishlist)

    // Работа с фотографиями
    override suspend fun insertPhoto(photo: PlantPhoto) = plantPhotoDao.insertPhoto(photo)

    override suspend fun insertPhotos(photos: List<PlantPhoto>) = plantPhotoDao.insertPhotos(photos)

    override suspend fun updatePhoto(photo: PlantPhoto) = plantPhotoDao.updatePhoto(photo)

    override suspend fun deletePhoto(photoId: Long) = plantPhotoDao.deleteById(photoId)

    override suspend fun setMainPhoto(plantId: Long, photoId: Long) = plantPhotoDao.setMainPhoto(plantId, photoId)

    override suspend fun getPhotosForPlant(plantId: Long): List<PlantPhoto> = plantPhotoDao.getPhotosForPlant(plantId)

    // --- Комплексные операции ---
    override suspend fun deletePlantWithPhotos(plantId: Long) {
        // Удаляем сначала фото, потом само растение
        plantPhotoDao.deletePhotosByPlantId(plantId)
        plantDao.deleteById(plantId)
    }

    override suspend fun getGenusByName(name: String) =
        genusDao.getGenusByName(name)

    override suspend fun createGenus(genus: Genus): Long {
        return genusDao.insertGenus(genus)
    }

    override suspend fun getGenusById(genusId: Long): Genus {
        return genusDao.getGenusById(genusId)
    }
}