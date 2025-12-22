package com.greencore.plantify.ui.screens.plant.detail

import android.graphics.Bitmap
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.greencore.plantify.data.photo.PhotoRepositoryInterface
import com.greencore.plantify.data.plant.PlantRepositoryInterface
import com.greencore.plantify.data.plant_with_photos.PlantWithPhotosRepositoryInterface
import com.greencore.plantify.core.data.local.entity.Plant
import com.greencore.plantify.core.data.local.entity.PlantPhoto
import com.greencore.plantify.core.data.local.relation.PlantWithPhotos
import com.greencore.plantify.ui.screens.plant.bitmapToByteArray
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlantDetailViewModel @Inject constructor(
    private val plantWithPhotosRepository: PlantWithPhotosRepositoryInterface,
    private val plantRepository: PlantRepositoryInterface,
    private val photoRepository: PhotoRepositoryInterface,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val plantId: Long = savedStateHandle["plantId"] ?: 0L

    val plantWithPhotos: Flow<PlantWithPhotos?> =
        plantWithPhotosRepository.getPlantWithPhotosById(plantId)

    private val _editedPlant = MutableStateFlow<Plant?>(null)
    val editedPlant: StateFlow<Plant?> = _editedPlant.asStateFlow()

    private val _editedPhotos = MutableStateFlow<List<PlantPhoto>>(emptyList())
    val editedPhotos: StateFlow<List<PlantPhoto?>> = _editedPhotos.asStateFlow()

    // Список для отслеживания удаленных фото
    private val _deletedPhotos = MutableStateFlow<MutableList<PlantPhoto>>(mutableListOf())
    private val deletedPhotos: List<PlantPhoto> get() = _deletedPhotos.value

    private var originalPlant: Plant? = null
    private var originalPhotos: List<PlantPhoto> = emptyList()

    init {
        viewModelScope.launch {
            plantWithPhotos.collect { data ->

                if (data != null) {
                    if (originalPlant == null) {
                        originalPlant = data.plant.copy()
                        originalPhotos = data.photos.map { it.copy() }
                    }

                    _editedPlant.value = data.plant
                    _editedPhotos.value = data.photos
                    _deletedPhotos.value.clear()
                }
            }
        }
    }

    fun updateEditedPlantFields(plant: Plant) {
        _editedPlant.value = plant
    }

    fun updateEditedPlantPhotos(photos: List<PlantPhoto>) {
        _editedPhotos.value = photos
    }

    fun addImageFromBitmap(bitmap: Bitmap) {
        val byteArray = bitmapToByteArray(bitmap)

        val newPhoto = PlantPhoto(
            plantId = plantId,
            imageData = byteArray,
            isPrimary = _editedPhotos.value.isEmpty()
        )

        _editedPhotos.value = _editedPhotos.value + newPhoto
    }

    fun replaceImageAt(index: Int, bitmap: Bitmap) {
        val byteArray = bitmapToByteArray(bitmap)

        val list = _editedPhotos.value.toMutableList()
        val old = list[index]

        list[index] = old.copy(imageData = byteArray)

        _editedPhotos.value = list
    }

    fun removeImageAt(index: Int) {
        val list = _editedPhotos.value.toMutableList()
        if (index !in list.indices) return

        val photoToRemove = list[index]

        _deletedPhotos.value.add(photoToRemove)
        list.removeAt(index)

        if (photoToRemove.isPrimary && list.isNotEmpty()) {
            list[0] = list[0].copy(isPrimary = true)
        }

        _editedPhotos.value = list
    }

    fun saveChanges() {
        val plant = _editedPlant.value ?: return
        val photos = _editedPhotos.value

        viewModelScope.launch {
            // 1. Обновляем растение
            plantRepository.updatePlant(plant)

            // 2. Удаляем фото, которые были удалены пользователем
            deletedPhotos.forEach { photo ->
                photoRepository.deletePhotoById(photo.id)
            }
            _deletedPhotos.value.clear()

            // 3. Сохраняем новые и обновленные фото
            photos.forEach { p ->
                if (p.id == 0L) {
                    photoRepository.insertPhoto(p.copy(plantId = plant.id))
                } else {
                    photoRepository.updatePhoto(p)
                }
            }
        }
    }

    fun resetChanges() {
        // Восстанавливаем оригинальные данные
        _editedPlant.value = originalPlant?.copy()
        _editedPhotos.value = originalPhotos.map { it.copy() }
        _deletedPhotos.value.clear()
    }

    fun deletePlant(onDeleted: () -> Unit) {
        val plant = _editedPlant.value ?: return

        viewModelScope.launch {
            plantRepository.deletePlantById(plant.id)
            onDeleted()
        }
    }
}