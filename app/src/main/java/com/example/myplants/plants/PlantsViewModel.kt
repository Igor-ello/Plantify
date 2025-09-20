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
    private val plants: LiveData<List<Plant>> get() = _plants

    init {
        loadPlants()
        dao.getAll().observeForever { plantList ->
            _plants.value = plantList
            if (plantList.isEmpty()) {
                addTestPlant()
            }
        }
    }

    fun getPlantById(plantId: Long): Plant? {
        return _plants.value?.find { it.id == plantId }
    }

    fun getAllPlants(): LiveData<List<Plant>> {
        return plants
    }

    private fun loadPlants() {
        dao.getAll().observeForever { plantList ->
            _plants.value = plantList
        }
    }

    private fun addTestPlant() {
        viewModelScope.launch {
            val newPlant = Plant(
                name = "Aloe Vera",
                species = "Aloe",
                subSpecies = "Barbadensis",
                lighting = "Яркий рассеянный свет",
                bloom = "Цветёт редко",
                soilComposition = "Легкая, песчаная почва",
                transfer = "Пересаживать каждые 2-3 года",
                temperature = "От +15°C до +25°C",
                airHumidity = "Низкая влажность воздуха",
                restPeriod = "Зима",
                reproduction = "Делением корневища",
                pests = "Щитовка, мучнистый червец",
                diseases = "Корневая гниль",
                firstLanding = "5 лет",
                sunlightRequirement = "Среднее освещение",
                isToxic = false,
                aboutThePlant = "Полезное лекарственное растение.",
                watering = "Умеренно поливать",
                wateringFrequency = 14,
                lastWateringDate = "2023-06-15",
                fertilizer = "Комплексные минеральные удобрения",
                fertilizationFrequency = 2
            )
            val anotherPlant = Plant(
                name = "Фикус Бенджамина",
                species = "Ficus benjamina",
                subSpecies = "Variegata",
                lighting = "Рассеянный яркий свет",
                bloom = "Редко цветёт дома",
                soilComposition = "Универсальная смесь для фикусов",
                transfer = "Ежегодно молодые растения, взрослые — раз в 2-3 года",
                temperature = "+18°C — +25°C",
                airHumidity = "Умеренная влажность",
                restPeriod = "Осень-зима",
                reproduction = "Черенкование",
                pests = "Паутинный клещ, щитовка",
                diseases = "Желтеют листья при переувлажнении",
                firstLanding = "2 года",
                sunlightRequirement = "Частичное затенение",
                isToxic = false,
                aboutThePlant = "Популярный декоративный домашний цветок.",
                watering = "Регулярно умеренно поливать",
                wateringFrequency = 7,
                lastWateringDate = "2023-06-20",
                fertilizer = "Специальные жидкие удобрения для фикусов",
                fertilizationFrequency = 3
            )
            dao.insert(newPlant)
            dao.insert(anotherPlant)
        }
    }

}