package com.example.myplants.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myplants.data.plant.PlantRepositoryInterface
import com.example.myplants.models.Plant
import com.example.myplants.models.PlantPhoto
import kotlinx.coroutines.launch

class AddPlantViewModel(
    private val repository: PlantRepositoryInterface
) : ViewModel() {

    private val _newPlant = MutableLiveData(Plant(name = "", species = ""))
    val newPlant: LiveData<Plant> get() = _newPlant

    private val _newPlantPhotos = MutableLiveData<List<PlantPhoto>>(emptyList())
    val newPlantPhotos: LiveData<List<PlantPhoto>> get() = _newPlantPhotos

    fun updateNewPlant(plant: Plant) {
        _newPlant.value = plant
    }

    fun updateNewPlantPhotos(photos: List<PlantPhoto>) {
        _newPlantPhotos.value = photos
    }

    fun clearNewPlant() {
        _newPlant.value = Plant(name = "", species = "")
        _newPlantPhotos.value = emptyList()
    }

    fun saveNewPlant(onSaved: () -> Unit) {
        val plant = _newPlant.value ?: return
        val photos = _newPlantPhotos.value ?: emptyList()
        viewModelScope.launch {
            val plantId = repository.insertPlant(plant)
            photos.forEach { repository.insertPhoto(it.copy(plantId = plantId)) }
            clearNewPlant()
            onSaved()
        }
    }
}
