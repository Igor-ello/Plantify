package com.example.myplants.plants

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myplants.dao.PlantDao
import com.example.myplants.models.Plant
import com.example.myplants.models.PlantPhoto
import com.example.myplants.models.PlantWithPhotos
import kotlinx.coroutines.launch

class PlantsViewModel(private val repository: PlantRepository) : ViewModel() {

    val plants: LiveData<List<PlantWithPhotos>> = repository.getAllPlantsWithPhotos()

    private val _newPlant = MutableLiveData(Plant(name = "", species = ""))
    val newPlant: LiveData<Plant> get() = _newPlant

    private val _newPlantPhotos = MutableLiveData<List<PlantPhoto>>(emptyList())
    val newPlantPhotos: LiveData<List<PlantPhoto>> get() = _newPlantPhotos

    fun updateNewPlantPhotos(photos: List<PlantPhoto>) {
        _newPlantPhotos.value = photos
    }

    init {
        viewModelScope.launch {
            PlantDataInitializer.initialize(repository)
        }
    }

    fun updateNewPlant(plant: Plant) {
        _newPlant.value = plant
    }

    fun saveNewPlant(photos: List<PlantPhoto>) {
        val plant = _newPlant.value ?: return
        viewModelScope.launch {
            val plantId = repository.insertPlant(plant)
            photos.forEach { repository.insertPhoto(it.copy(plantId = plantId)) }
            clearNewPlant()
        }
    }

    fun clearNewPlant() {
        _newPlant.value = Plant(name = "", species = "")
    }

    fun updatePlant(plant: Plant, photos: List<PlantPhoto>) {
        viewModelScope.launch {
            repository.updatePlant(plant)
            photos.forEach {
                if (it.id == 0L) repository.insertPhoto(it.copy(plantId = plant.id))
                else repository.insertPhoto(it) // или updatePhoto если нужно
            }
        }
    }

    fun deletePlant(plant: Plant) {
        viewModelScope.launch {
            repository.deletePlantWithPhotos(plant.id)
        }
    }
}