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

    // LiveData для нового растения и его фотографий
    private val _newPlant = MutableLiveData(Plant(name = "", species = ""))
    val newPlant: LiveData<Plant> get() = _newPlant

    private val _newPlantPhotos = MutableLiveData<List<PlantPhoto>>(emptyList())
    val newPlantPhotos: LiveData<List<PlantPhoto>> get() = _newPlantPhotos

    // Режим применения поля
    enum class ApplyMode { Overwrite, Prepend, Append }

    // Обновление текущего растения
    fun updateNewPlant(plant: Plant) {
        _newPlant.value = plant
    }

    // Обновление фотографий
    fun updateNewPlantPhotos(photos: List<PlantPhoto>) {
        _newPlantPhotos.value = photos
    }

    // Сброс всех полей нового растения
    fun clearNewPlant() {
        _newPlant.value = Plant(name = "", species = "")
        _newPlantPhotos.value = emptyList()
    }

    // Сохранение нового растения и его фото в репозиторий
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
