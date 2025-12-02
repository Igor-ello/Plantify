package com.example.myplants.ui.screens.plant.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myplants.core.data.local.entity.Genus
import com.example.myplants.core.data.local.entity.Plant
import com.example.myplants.core.data.local.entity.PlantPhoto
import com.example.myplants.core.data.local.entity.sections.CareInfo
import com.example.myplants.core.data.local.entity.sections.HealthInfo
import com.example.myplants.core.data.local.entity.sections.LifecycleInfo
import com.example.myplants.core.data.local.entity.sections.MainInfo
import com.example.myplants.core.data.local.entity.sections.StateInfo
import com.example.myplants.data.main_facade.MainFacadeInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddPlantViewModel @Inject constructor(
    private val facade: MainFacadeInterface
) : ViewModel() {
    private val _newPlant = MutableStateFlow(Plant(main = MainInfo(genus = "", species = ""), genusId = 0L))
    val newPlant: StateFlow<Plant> get() = _newPlant

    private val _newPlantPhotos = MutableStateFlow<List<PlantPhoto>>(emptyList())
    val newPlantPhotos: StateFlow<List<PlantPhoto>> get() = _newPlantPhotos

    // Обновление текущего растения
    fun updateNewPlant(plant: Plant) {
        _newPlant.value = plant
    }

    // Обновление фотографий
    fun updateNewPlantPhotos(photos: List<PlantPhoto>) {
        _newPlantPhotos.value = photos
    }

//    // Сброс всех полей нового растения
//    fun clearNewPlant() {
//        _newPlant.value = Plant(main = MainInfo(genus = "", species = ""))
//        _newPlantPhotos.value = emptyList()
//    }

    fun saveNewPlant(onSaved: () -> Unit) {
        val plant = newPlant.value
        val photos = newPlantPhotos.value

        viewModelScope.launch {
            plant.genusId = createOrGetGenus(facade, plant.main.genus)
            val plantId = facade.insertPlant(plant)
            photos.forEach { photo ->
                facade.insertPhoto(photo.copy(plantId = plantId))
            }
//            clearNewPlant()
            onSaved()
        }
    }

    private suspend fun createOrGetGenus(facade: MainFacadeInterface, genusName: String): Long {
        val genus = facade.getGenusByName(genusName)
        if (genus == null) {
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
            return facade.insertGenus(newGenus)
        } else
            return genus.id
    }
}
