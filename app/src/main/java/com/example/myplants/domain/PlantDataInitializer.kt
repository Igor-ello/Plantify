package com.example.myplants.domain

import com.example.myplants.data.plant.PlantRepositoryInterface
import com.example.myplants.models.Plant
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object PlantDataInitializer {

    fun initialize(repository: PlantRepositoryInterface) {
        CoroutineScope(Dispatchers.IO).launch {
            val currentPlants = repository.getAllPlants()
            if (currentPlants.isEmpty()) {
                addTestPlants(repository)
            }
        }
    }

    private suspend fun addTestPlants(repository: PlantRepositoryInterface) {
        val aloeVera = Plant(
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

        val ficus = Plant(
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

        repository.insertPlant(aloeVera)
        repository.insertPlant(ficus)
    }
}