package com.example.myplants.domain

import com.example.myplants.data.main_facade.MainFacadeInterface
import com.example.myplants.models.Plant
import com.example.myplants.models.sections.CareInfo
import com.example.myplants.models.sections.FertilizerInfo
import com.example.myplants.models.sections.HealthInfo
import com.example.myplants.models.sections.LifecycleInfo
import com.example.myplants.models.sections.MainInfo
import com.example.myplants.models.sections.StateInfo
import com.example.myplants.models.sections.WateringInfo

object PlantDataInitializer {

    suspend fun initialize(facade: MainFacadeInterface) {
        val currentPlants = facade.getAllPlants()
        if (currentPlants.isEmpty()) {
            addTestPlants(facade)
        }
    }

    private suspend fun addTestPlants(facade: MainFacadeInterface) {
        val aloeVera = Plant(
            main = MainInfo(
                genus = "Aloe",
                species = "Aloe Vera",
                fullName = "Aloe Vera Barbadensis"
            ),
            care = CareInfo(
                lighting = "Яркий рассеянный свет",
                temperature = "От +15°C до +25°C",
                airHumidity = "Низкая влажность воздуха",
                soilComposition = "Легкая, песчаная почва",
                transfer = "Пересаживать каждые 2-3 года",
                watering = WateringInfo(
                    watering = "Умеренно поливать",
                    frequencyPerMonth = 14,
                    lastDate = "2023-06-15",
                    isActive = true
                ),
                fertilizer = FertilizerInfo(
                    fertilizer = "Комплексные минеральные удобрения",
                    frequencyPerMonth = 2,
                    isActive = true
                )
            ),
            lifecycle = LifecycleInfo(
                lifecycle = "Зима",
                bloom = "Цветёт редко",
                reproduction = "Делением корневища",
                firstLanding = "5 лет",
                aboutThePlant = "Полезное лекарственное растение.",
                isToxic = false
            ),
            health = HealthInfo(
                pests = "Щитовка, мучнистый червец",
                diseases = "Корневая гниль"
            ),
            state = StateInfo(
                isFavorite = false,
                isWishlist = false,
                isHideEmpty = false
            )
        )

        val ficus = Plant(
            main = MainInfo(
                genus = "Фикус",
                species = "Фикус Бенджамина",
                fullName = "Фикус Бенджамина Variegata"
            ),
            care = CareInfo(
                lighting = "Рассеянный яркий свет",
                temperature = "+18°C — +25°C",
                airHumidity = "Умеренная влажность",
                soilComposition = "Универсальная смесь для фикусов",
                transfer = "Ежегодно молодые растения, взрослые — раз в 2-3 года",
                watering = WateringInfo(
                    watering = "Регулярно умеренно поливать",
                    frequencyPerMonth = 7,
                    lastDate = "2023-06-20",
                    isActive = true
                ),
                fertilizer = FertilizerInfo(
                    fertilizer = "Специальные жидкие удобрения для фикусов",
                    frequencyPerMonth = 3,
                    isActive = true
                )
            ),
            lifecycle = LifecycleInfo(
                lifecycle = "Осень-зима",
                bloom = "Редко цветёт дома",
                reproduction = "Черенкование",
                firstLanding = "2 года",
                aboutThePlant = "Популярный декоративный домашний цветок.",
                isToxic = false
            ),
            health = HealthInfo(
                pests = "Паутинный клещ, щитовка",
                diseases = "Желтеют листья при переувлажнении"
            ),
            state = StateInfo(
                isFavorite = false,
                isWishlist = false,
                isHideEmpty = false
            )
        )

        facade.insertPlant(aloeVera)
        facade.insertPlant(ficus)
    }
}