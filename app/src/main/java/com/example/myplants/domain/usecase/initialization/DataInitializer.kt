package com.example.myplants.domain.usecase.initialization

import com.example.myplants.core.data.local.entity.Genus
import com.example.myplants.core.data.local.entity.Plant
import com.example.myplants.core.data.local.entity.PlantPhoto
import com.example.myplants.core.data.local.entity.sections.CareInfo
import com.example.myplants.core.data.local.entity.sections.FertilizerInfo
import com.example.myplants.core.data.local.entity.sections.HealthInfo
import com.example.myplants.core.data.local.entity.sections.LifecycleInfo
import com.example.myplants.core.data.local.entity.sections.MainInfo
import com.example.myplants.core.data.local.entity.sections.StateInfo
import com.example.myplants.core.data.local.entity.sections.WateringInfo
import com.example.myplants.core.data.local.relation.PlantWithPhotos
import com.example.myplants.data.main_facade.MainFacadeInterface

object DataInitializer {

    fun getBlankPlant(
        idToInit: Long = 0L,
        species: String = "",
        genus: String = ""
    ): Plant {
        return Plant(
            id = idToInit,
            genusId = idToInit,
            main = MainInfo(species = species, genus = genus)
        )
    }

    fun getBlankGenus(
        idToInit: Long = 0L,
        genus: String = ""
    ): Genus {
        return Genus(
            id = idToInit,
            main = MainInfo(species = "", genus=genus)
        )
    }

    suspend fun initialize(facade: MainFacadeInterface) {
        val currentGenus = facade.getAllGenus()
        if (currentGenus.isEmpty()) {
            addTestPlants(facade)
        }
    }

    private suspend fun addTestPlants(facade: MainFacadeInterface) {
        facade.insertPlant(getSamplePlant1(createGenus(facade, "Aloe")))
        facade.insertPlant(getSamplePlant2(createGenus(facade, "Ficus")))
    }

    private suspend fun createGenus(facade: MainFacadeInterface, genusName: String): Long {
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
    }

    fun getSamplePlantWithPhotos(idToInit: Long = 1L): PlantWithPhotos {
        val photo = listOf(PlantPhoto(id = idToInit, plantId = idToInit, isPrimary = true))
        return when (idToInit) {
            1L -> PlantWithPhotos(getSamplePlant1(), photo)
            2L -> PlantWithPhotos(getSamplePlant2(), photo)
            else -> throw IllegalArgumentException("Invalid id: $idToInit")
        }
    }

    fun getSamplePlant1(genusId: Long = 1L): Plant {
        return Plant(
            genusId = genusId,
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
    }

    fun getSamplePlant2(genusId: Long = 2L): Plant {
        return Plant(
            genusId = genusId,
            main = MainInfo(
                genus = "Ficus",
                species = "Ficus Benjamin",
                fullName = "Ficus Benjamin Variegate"
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
    }
}