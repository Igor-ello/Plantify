package com.example.myplants.ui.screens.plant.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myplants.data.photo.PhotoRepositoryInterface
import com.example.myplants.data.plant.PlantRepositoryInterface
import com.example.myplants.data.plant_with_photos.PlantWithPhotosRepositoryInterface
import com.example.myplants.core.data.local.entity.Plant
import com.example.myplants.core.data.local.entity.PlantPhoto
import com.example.myplants.core.data.local.entity.PlantWithPhotos
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlantDetailViewModel @Inject constructor(
    plantWithPhotosRepository: PlantWithPhotosRepositoryInterface,
    private val plantRepository: PlantRepositoryInterface,
    private val photoRepository: PhotoRepositoryInterface,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val plantId: Long = savedStateHandle.get<Long>("plantId") ?: 0L

    val plantWithPhotos: Flow<PlantWithPhotos?> =
        plantWithPhotosRepository.getPlantWithPhotosById(plantId)

    private val _editedPlant = MutableStateFlow<Plant?>(null)
    val editedPlant: StateFlow<Plant?> = _editedPlant.asStateFlow()

    private val _editedPhotos = MutableStateFlow<List<PlantPhoto>>(emptyList())
    val editedPhotos: StateFlow<List<PlantPhoto>> = _editedPhotos.asStateFlow()

    // Сохраняем оригинальные данные для сброса
    private var originalPlant: Plant? = null
    private var originalPhotos: List<PlantPhoto> = emptyList()

    init {
        viewModelScope.launch {
            plantWithPhotos.collect { plantData ->
                plantData?.let {
                    // Сохраняем оригинальные значения при первой загрузке
                    if (originalPlant == null) {
                        originalPlant = it.plant.copy()
                        originalPhotos = ArrayList(it.photos)
                    }

                    // Обновляем edited значения только если они отличаются
                    if (_editedPlant.value != it.plant) {
                        _editedPlant.value = it.plant
                    }
                    if (_editedPhotos.value != it.photos) {
                        _editedPhotos.value = it.photos
                    }
                }
            }
        }
    }

    fun updateEditedPlant(plant: Plant) {
        _editedPlant.value = plant
    }

    fun updateEditedPhotos(photos: List<PlantPhoto>) {
        _editedPhotos.value = photos
    }

    fun addPhoto(photo: PlantPhoto) {
        _editedPhotos.value = _editedPhotos.value + photo
    }

    fun removePhoto(photo: PlantPhoto) {
        _editedPhotos.value = _editedPhotos.value - photo
    }

    fun saveChanges() {
        val plant = _editedPlant.value ?: return
        val photos = _editedPhotos.value ?: emptyList()
        viewModelScope.launch {
            plantRepository.updatePlant(plant)
            photos.forEach { photo ->
                if (photo.id == 0L) {
                    photoRepository.insertPhoto(photo.copy(plantId = plant.id))
                } else {
                    photoRepository.updatePhoto(photo)
                }
            }
        }
    }

    fun resetChanges() {
        originalPlant?.let { plant ->
            _editedPlant.value = plant.copy()
            _editedPhotos.value = ArrayList(originalPhotos)
        }
    }

    fun deletePlant(onDeleted: () -> Unit) {
        val plant = _editedPlant.value ?: return
        viewModelScope.launch {
            photoRepository.deletePhotosByPlantId(plant.id)
            plantRepository.deletePlantById(plant.id)
            onDeleted()
        }
    }
}
