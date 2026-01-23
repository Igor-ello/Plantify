package com.greencore.plantify.ui.screens.plant.add

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.greencore.plantify.core.data.local.entity.Genus
import com.greencore.plantify.core.data.local.entity.Plant
import com.greencore.plantify.core.data.local.entity.PlantPhoto
import com.greencore.plantify.core.data.local.entity.sections.CareInfo
import com.greencore.plantify.core.data.local.entity.sections.HealthInfo
import com.greencore.plantify.core.data.local.entity.sections.LifecycleInfo
import com.greencore.plantify.core.data.local.entity.sections.MainInfo
import com.greencore.plantify.core.data.local.entity.sections.StateInfo
import com.greencore.plantify.data.main_facade.MainFacadeInterface
import com.greencore.plantify.domain.DataInitializer
import com.greencore.plantify.ui.screens.plant.add.FieldCleaner
import com.greencore.plantify.ui.screens.plant.bitmapToByteArray
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddPlantViewModel @Inject constructor(
    private val facade: MainFacadeInterface
) : ViewModel() {

    private val _newPlant = MutableStateFlow(
        DataInitializer.getBlankPlant()
    )
    val newPlant: StateFlow<Plant> get() = _newPlant

    private val _newPlantPhotos = MutableStateFlow<List<PlantPhoto>>(emptyList())
    val newPlantPhotos: StateFlow<List<PlantPhoto>> get() = _newPlantPhotos

    // Обновляем состояния Plant
    fun updateNewPlantFields(plant: Plant) {
        _newPlant.value = plant
    }

    fun updateNewPlantPhotos(photos: List<PlantPhoto>) {
        _newPlantPhotos.value = photos
    }

    fun addImageFromBitmap(bitmap: Bitmap) {
        val byteArray = bitmapToByteArray(bitmap)

        val newPhoto = PlantPhoto(
            plantId = 0L,
            imageData = byteArray,
            isPrimary = _newPlantPhotos.value.isEmpty()
        )

        _newPlantPhotos.value = _newPlantPhotos.value + newPhoto
    }

    fun replaceImageAt(index: Int, bitmap: Bitmap) {
        val byteArray = bitmapToByteArray(bitmap)

        val list = _newPlantPhotos.value.toMutableList()
        val old = list[index]

        list[index] = old.copy(
            imageData = byteArray
        )

        _newPlantPhotos.value = list
    }

    fun removeImageAt(index: Int) {
        val list = _newPlantPhotos.value.toMutableList()
        if (index !in list.indices) return

        val wasPrimary = list[index].isPrimary
        list.removeAt(index)

        // Если удалили главное фото, то новое первое становится главным
        if (wasPrimary && list.isNotEmpty()) {
            list[0] = list[0].copy(isPrimary = true)
        }

        _newPlantPhotos.value = list
    }

    fun saveNewPlant(onClose: () -> Unit) {
        val plant = FieldCleaner.cleanPlant(newPlant.value)
        val photos = newPlantPhotos.value

        viewModelScope.launch {
            plant.genusId = createOrGetGenus(facade, plant.main.genus)
            val plantId = facade.insertPlant(plant)

            photos.forEach { photo ->
                facade.insertPhoto(
                    photo.copy(
                        plantId = plantId,
                        imageData = photo.imageData,
                        isPrimary = photo.isPrimary
                    )
                )
            }
            onClose()
        }
    }

    private suspend fun createOrGetGenus(
        facade: MainFacadeInterface,
        genusName: String
    ): Long {
        val genus = facade.getGenusByName(genusName)
        return if (genus == null) {
            val newGenus = Genus(
                main = MainInfo(
                    genus = genusName,
                    species = "",
                    fullName = genusName
                ),
                care = CareInfo(),
                lifecycle = LifecycleInfo(),
                health = HealthInfo(),
                state = StateInfo()
            )
            facade.insertGenus(newGenus)
        } else genus.id
    }
}
