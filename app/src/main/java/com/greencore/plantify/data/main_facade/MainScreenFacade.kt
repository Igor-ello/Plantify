package com.greencore.plantify.data.main_facade

import com.greencore.plantify.core.data.local.entity.Genus
import com.greencore.plantify.core.data.local.entity.Plant
import com.greencore.plantify.core.data.local.entity.PlantPhoto
import com.greencore.plantify.core.data.local.relation.PlantWithPhotos
import com.greencore.plantify.data.genus.GenusRepositoryInterface
import com.greencore.plantify.data.photo.PhotoRepositoryInterface
import com.greencore.plantify.data.plant.PlantRepositoryInterface
import com.greencore.plantify.data.plant_with_photos.PlantWithPhotosRepositoryInterface
import com.greencore.plantify.data.state.PlantStateRepositoryInterface
import kotlinx.coroutines.flow.Flow

class MainFacade (
    private val plantRepository: PlantRepositoryInterface,
    private val photoRepository: PhotoRepositoryInterface,
    private val plantWithPhotosRepository: PlantWithPhotosRepositoryInterface,
    private val plantStateRepository: PlantStateRepositoryInterface,
    private val genusRepository: GenusRepositoryInterface
) : MainFacadeInterface {

    // Plant

    override suspend fun updatePlant(plant: Plant) =
        plantRepository.updatePlant(plant)

    override suspend fun insertPlant(plant: Plant) =
        plantRepository.insertPlant(plant)

    override suspend fun getAllPlants() =
        plantRepository.getAllPlants()

    // Photo

    override suspend fun insertPhoto(photo: PlantPhoto): Long =
        photoRepository.insertPhoto(photo)

    // State

    override fun getFavorites() =
        plantStateRepository.getFavorites()

    override suspend fun setFavorite(plantId: Long, isFavorite: Boolean) =
        plantStateRepository.setFavorite(plantId, isFavorite)

    override fun getWishlist() =
        plantStateRepository.getWishlist()

    override suspend fun setWishlist(plantId: Long, isWishlist: Boolean) =
        plantStateRepository.setWishlist(plantId, isWishlist)

    // Plant with photos

    override fun getAllPlantsWithPhotos(): Flow<List<PlantWithPhotos>> =
        plantWithPhotosRepository.getAllPlantsWithPhotos()

    override fun getPlantWithPhotosById(plantId: Long): Flow<PlantWithPhotos?> =
        plantWithPhotosRepository.getPlantWithPhotosById(plantId)

    // Genus

    override suspend fun insertGenus(genus: Genus): Long =
        genusRepository.insertGenus(genus)

    override suspend fun getGenusById(genusId: Long): Genus? =
        genusRepository.getGenusById(genusId)

    override fun getGenusByIdLive(genusId: Long): Flow<Genus?> =
        genusRepository.getGenusByIdLive(genusId)

    override suspend fun getGenusByName(genusName: String): Genus? =
        genusRepository.getGenusByName(genusName)

    override fun getGenusByNameLive(genusName: String): Flow<Genus?> =
        genusRepository.getGenusByNameLive(genusName)

    override suspend fun getAllGenus(): List<Genus> =
        genusRepository.getAllGenus()

    override fun getAllGenusLive(): Flow<List<Genus>> =
        genusRepository.getAllGenusLive()
}