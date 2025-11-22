package com.example.myplants.data.main_facade

import androidx.lifecycle.LiveData
import com.example.myplants.data.genus.GenusRepositoryInterface
import com.example.myplants.data.plant.PlantRepositoryInterface
import com.example.myplants.data.state.PlantStateRepositoryInterface
import com.example.myplants.data.plant_with_photos.PlantWithPhotosRepositoryInterface
import com.example.myplants.core.data.local.entity.Genus
import com.example.myplants.core.data.local.entity.Plant
import com.example.myplants.core.data.local.entity.PlantWithPhotos
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

    override fun getPlantWithPhotosById(plantId: Long): LiveData<PlantWithPhotos?> =
        plantWithPhotosRepository.getPlantWithPhotosById(plantId)

    // Genus

    override fun getAllGenus(): Flow<List<Genus>> =
        genusRepository.getAllGenus()

    override suspend fun getGenusById(genusId: Long): Genus =
        genusRepository.getGenusById(genusId)

    override suspend fun insertGenus(genus: Genus) =
        genusRepository.insertGenus(genus)

    override fun getGenusByIdLive(genusId: Long) =
        genusRepository.getGenusByIdLive(genusId)

    override fun getGenusByNameLive(genusName: String) =
        genusRepository.getGenusByNameLive(genusName)

    override suspend fun getGenusByName(genusName: String): Genus =
        genusRepository.getGenusByName(genusName)
}