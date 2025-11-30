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
import com.example.myplants.data.photo.PhotoRepositoryInterface
import com.example.myplants.data.plant.PlantRepositoryInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddPlantViewModel @Inject constructor(
    private val facade: MainFacadeInterface
) : ViewModel() {
    private val _newPlant = MutableStateFlow(Plant(main = MainInfo(genus = "", species = "")))
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

    // Сброс всех полей нового растения
    fun clearNewPlant() {
        _newPlant.value = Plant(main = MainInfo(genus = "", species = ""))
        _newPlantPhotos.value = emptyList()
    }

    // Сохранение нового растения и его фото в репозиторий
    fun saveNewPlant(onSaved: () -> Unit) {
        val plant = newPlant.value
        val photos = newPlantPhotos.value

        viewModelScope.launch {
            val plantId = facade.insertPlant(plant)
            photos.forEach { photo ->
                facade.insertPhoto(photo.copy(plantId = plantId))
            }
            createGenus(facade)
            // Очищаем форму
            clearNewPlant()
            onSaved()
        }
    }

    suspend fun createGenus(facade: MainFacadeInterface) {
        val plantsList = facade.getAllPlants()
        val genusNames = plantsList.map { it.main.genus }.distinct()

        genusNames.forEach { genusName ->
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
                facade.insertGenus(newGenus)
            }
        }
    }
}
