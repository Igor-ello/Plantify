package com.example.myplants.plants

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myplants.dao.PlantDao
import com.example.myplants.models.Plant
import kotlinx.coroutines.launch

class PlantsViewModel(val dao: PlantDao) : ViewModel() {

    // Объявляем LiveData для списка растений
    private val _plants = MutableLiveData<List<Plant>>()
    val plants: LiveData<List<Plant>> get() = _plants

    init {
        loadPlants()
        dao.getAll().observeForever { plantList ->
            _plants.value = plantList
            if (plantList.isEmpty()) {
                addTestPlant()
            }
        }
    }


    private fun loadPlants() {
        dao.getAll().observeForever { plantList ->
            _plants.value = plantList
        }
    }

    fun showPlants() : String {
        val currentPlants = _plants.value ?: emptyList()
        return formatPlants(currentPlants)
    }

    private fun formatPlants(plants: List<Plant>): String {
        if (plants.isEmpty()) return "No plants available."

        return plants.joinToString(separator = "\n") { formatPlant(it) }
    }

    fun formatPlant(plant: Plant): String {
        return buildString {
            append("ID: ${plant.id}\n")
            append("Name: ${plant.name}\n")
            append("Species: ${plant.species}\n")
            append("Fertilization Frequency: ${plant.fertilizationFrequency} month(s)\n")
        }
    }

    fun addTestPlant() {
        viewModelScope.launch {
            val newPlant = Plant(
                id = 0, // ID будет автоматически сгенерирован
                name = "Aloe Vera",
                species = "Aloe",
                sunlightRequirement = "Medium",
                isToxic = false,
                fertilizationFrequency = 2
            )
            dao.insert(newPlant)
        }
    }

}