package com.example.myplants.data.main_facade

import com.example.myplants.core.data.local.entity.Genus
import com.example.myplants.core.data.local.entity.Plant
import com.example.myplants.core.data.local.entity.PlantWithPhotos
import com.example.myplants.data.genus.GenusRepositoryInterface
import com.example.myplants.data.plant.PlantRepositoryInterface
import com.example.myplants.data.plant_with_photos.PlantWithPhotosRepositoryInterface
import com.example.myplants.data.state.PlantStateRepositoryInterface
import kotlinx.coroutines.flow.Flow

class MainFacade (
    private val plantRepository: PlantRepositoryInterface,
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

    override fun getAllGenus(): Flow<List<Genus>> =
        genusRepository.getAllGenusLive()
}