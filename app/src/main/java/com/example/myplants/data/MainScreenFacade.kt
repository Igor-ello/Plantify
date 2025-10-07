package com.example.myplants.data

import androidx.lifecycle.LiveData
import com.example.myplants.data.genus.GenusRepository
import com.example.myplants.data.plant.PlantRepository
import com.example.myplants.data.plant_with_photos.PlantStateRepository
import com.example.myplants.data.plant_with_photos.PlantWithPhotosRepository
import com.example.myplants.models.Genus
import com.example.myplants.models.Plant
import com.example.myplants.models.PlantWithPhotos

class MainFacade (
    private val plantRepository: PlantRepository,
    private val plantWithPhotosRepository: PlantWithPhotosRepository,
    private val plantStateRepository: PlantStateRepository,
    private val genusRepository: GenusRepository
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

    override fun getAllPlantsWithPhotos(): LiveData<List<PlantWithPhotos>> =
        plantWithPhotosRepository.getAllPlantsWithPhotos()

    override fun getPlantWithPhotosById(plantId: Long): LiveData<PlantWithPhotos?> =
        plantWithPhotosRepository.getPlantWithPhotosById(plantId)

    // Genus

    override suspend fun insertGenus(genus: Genus) =
        genusRepository.insertGenus(genus)

    override fun getGenusByIdLive(genusId: Long) =
        genusRepository.getGenusByIdLive(genusId)

    override fun getGenusByNameLive(genusName: String) =
        genusRepository.getGenusByNameLive(genusName)

    override suspend fun getGenusByName(genusName: String): Genus =
        genusRepository.getGenusByName(genusName)
}