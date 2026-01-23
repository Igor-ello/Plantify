package com.greencore.plantify.ui.screens.plant.add

import com.greencore.plantify.core.data.local.entity.Plant
import com.greencore.plantify.core.data.local.entity.sections.CareInfo
import com.greencore.plantify.core.data.local.entity.sections.FertilizerInfo
import com.greencore.plantify.core.data.local.entity.sections.HealthInfo
import com.greencore.plantify.core.data.local.entity.sections.LifecycleInfo
import com.greencore.plantify.core.data.local.entity.sections.MainInfo
import com.greencore.plantify.core.data.local.entity.sections.WateringInfo

object FieldCleaner {
    private val multipleSpacesRegex = Regex("\\s+")

    fun cleanString(value: String?): String {
        if (value == null) return ""
        return value.trim().replace(multipleSpacesRegex, " ")
    }

    fun cleanPlant(plant: Plant): Plant {
        return plant.copy(
            main = cleanMainInfo(plant.main),
            care = cleanCareInfo(plant.care),
            lifecycle = cleanLifecycleInfo(plant.lifecycle),
            health = cleanHealthInfo(plant.health)
        )
    }

    fun cleanMainInfo(mainInfo: MainInfo): MainInfo {
        return mainInfo.copy(
            species = cleanString(mainInfo.species),
            genus = cleanString(mainInfo.genus),
            fullName = cleanString(mainInfo.fullName)
        )
    }

    private fun cleanCareInfo(careInfo: CareInfo): CareInfo {
        return careInfo.copy(
            lighting = cleanString(careInfo.lighting),
            temperature = cleanString(careInfo.temperature),
            airHumidity = cleanString(careInfo.airHumidity),
            soilComposition = cleanString(careInfo.soilComposition),
            transfer = cleanString(careInfo.transfer),
            watering = cleanWateringInfo(careInfo.watering),
            fertilizer = cleanFertilizerInfo(careInfo.fertilizer)
        )
    }

    private fun cleanWateringInfo(wateringInfo: WateringInfo): WateringInfo {
        return wateringInfo.copy(
            watering = cleanString(wateringInfo.watering),
            lastDate = cleanString(wateringInfo.lastDate),
        )
    }

    private fun cleanFertilizerInfo(fertilizerInfo: FertilizerInfo): FertilizerInfo {
        return fertilizerInfo.copy(
            fertilizer = cleanString(fertilizerInfo.fertilizer),
            lastDate = cleanString(fertilizerInfo.lastDate)
        )
    }

    private fun cleanLifecycleInfo(lifecycleInfo: LifecycleInfo): LifecycleInfo {
        return lifecycleInfo.copy(
            lifecycle = cleanString(lifecycleInfo.lifecycle),
            bloom = cleanString(lifecycleInfo.bloom),
            reproduction = cleanString(lifecycleInfo.reproduction),
            firstLanding = cleanString(lifecycleInfo.firstLanding),
            aboutThePlant = cleanString(lifecycleInfo.aboutThePlant),
        )
    }

    private fun cleanHealthInfo(healthInfo: HealthInfo): HealthInfo {
        return healthInfo.copy(
            pests = cleanString(healthInfo.pests),
            diseases = cleanString(healthInfo.diseases)
        )
    }
}