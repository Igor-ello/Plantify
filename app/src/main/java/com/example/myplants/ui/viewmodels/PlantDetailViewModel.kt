package com.example.myplants.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myplants.data.photo.PhotoRepository
import com.example.myplants.data.photo.PhotoRepositoryInterface
import com.example.myplants.data.plant.PlantRepository
import com.example.myplants.data.plant.PlantRepositoryInterface
import com.example.myplants.data.plant_with_photos.PlantWithPhotosRepository
import com.example.myplants.models.Plant
import com.example.myplants.models.PlantPhoto
import com.example.myplants.models.PlantWithPhotos
import kotlinx.coroutines.launch

class PlantDetailViewModel(
    private val plantWithPhotosRepository: PlantWithPhotosRepository,
    private val plantRepository: PlantRepositoryInterface,
    private val photoRepository: PhotoRepositoryInterface,
    plantId: Long
) : ViewModel() {

    val plantWithPhotos: LiveData<PlantWithPhotos?> =
        plantWithPhotosRepository.getPlantWithPhotosById(plantId)

    private val _editedPlant = MutableLiveData<Plant>()
    val editedPlant: LiveData<Plant> get() = _editedPlant

    private val _editedPhotos = MutableLiveData<List<PlantPhoto>>(emptyList())
    val editedPhotos: LiveData<List<PlantPhoto>> get() = _editedPhotos

    init {
        // Сразу синхронизируем с LiveData от репозитория
        plantWithPhotos.observeForever { plantData ->
            plantData?.let {
                _editedPlant.value = it.plant
                _editedPhotos.value = it.photos
            }
        }
    }

    fun updateEditedPlant(plant: Plant) {
        _editedPlant.value = plant
    }

    fun updateEditedPhotos(photos: List<PlantPhoto>) {
        _editedPhotos.value = photos
    }

    fun saveChanges() {
        val plant = _editedPlant.value ?: return
        val photos = _editedPhotos.value ?: emptyList()
        viewModelScope.launch {
            plantRepository.updatePlant(plant)
            photos.forEach {
                if (it.id == 0L) photoRepository.insertPhoto(it.copy(plantId = plant.id))
                else photoRepository.updatePhoto(it)
            }
        }
    }

    fun resetChanges() {
        plantWithPhotos.value?.let {
            _editedPlant.value = it.plant
            _editedPhotos.value = it.photos
        }
    }

    fun deletePlant(onDeleted: () -> Unit) {
        val plant = _editedPlant.value ?: return
        viewModelScope.launch {
            plantWithPhotosRepository.deletePlantWithPhotos(plant.id)
            onDeleted()
        }
    }
}
