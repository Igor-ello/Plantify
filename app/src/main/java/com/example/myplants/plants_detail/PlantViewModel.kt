package com.example.myplants.plants_detail


import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myplants.dao.PlantDao
import com.example.myplants.models.Plant
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class PlantViewModel(val plantDao: PlantDao) : ViewModel() {
    // LiveData или StateFlow для наблюдения за данными
    private val _plant = MutableStateFlow<Plant?>(null)
    val plant: StateFlow<Plant?> = _plant.asStateFlow()

    // Загрузка растения по ID
    fun loadPlant(plantId: Long) {
        viewModelScope.launch {
            val plant = plantDao.get(plantId)
            _plant.value = plant
        }
    }

    // Другие методы: обновление, удаление и т.д.
    fun updatePlant(plant: Plant) {
        viewModelScope.launch {
            plantDao.update(plant)
            _plant.value = plant // Обновляем состояние
        }
    }
}