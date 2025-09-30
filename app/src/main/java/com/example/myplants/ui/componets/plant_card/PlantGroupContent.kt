package com.example.myplants.ui.componets.plant_card

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.myplants.R
import com.example.myplants.models.Plant
import com.example.myplants.ui.componets.card.CardCheckbox
import com.example.myplants.ui.componets.card.CardTextField

@Composable
fun PlantGroupContent(
    plant: Plant,
    editable: Boolean,
    onValueChange: (Plant) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Полное имя растения
        CardTextField(
            label = stringResource(R.string.plant_full_name),
            value = plant.main.fullName ?: "",
            editable = editable,
            onValueChange = { onValueChange(plant.copy(main = plant.main.copy(fullName = it))) }
        )

        // Вид
        CardTextField(
            label = stringResource(R.string.plant_species),
            value = plant.main.species,
            editable = editable,
            onValueChange = { onValueChange(plant.copy(main = plant.main.copy(species = it))) }
        )

        // Род
        CardTextField(
            label = stringResource(R.string.plant_genus),
            value = plant.main.genus,
            editable = editable,
            onValueChange = { onValueChange(plant.copy(main = plant.main.copy(genus = it))) }
        )

        // Освещение
        CardTextField(
            label = stringResource(R.string.plant_lighting),
            value = plant.care.lighting ?: "",
            editable = editable,
            onValueChange = { onValueChange(plant.copy(care = plant.care.copy(lighting = it))) }
        )

        // Температура
        CardTextField(
            label = stringResource(R.string.plant_temperature),
            value = plant.care.temperature ?: "",
            editable = editable,
            onValueChange = { onValueChange(plant.copy(care = plant.care.copy(temperature = it))) }
        )

        // Влажность воздуха
        CardTextField(
            label = stringResource(R.string.plant_air_humidity),
            value = plant.care.airHumidity ?: "",
            editable = editable,
            onValueChange = { onValueChange(plant.copy(care = plant.care.copy(airHumidity = it))) }
        )

        // Состав почвы
        CardTextField(
            label = stringResource(R.string.soil_composition),
            value = plant.care.soilComposition ?: "",
            editable = editable,
            onValueChange = { onValueChange(plant.copy(care = plant.care.copy(soilComposition = it))) }
        )

        // Пересадка
        CardTextField(
            label = stringResource(R.string.plant_transfer),
            value = plant.care.transfer ?: "",
            editable = editable,
            onValueChange = { onValueChange(plant.copy(care = plant.care.copy(transfer = it))) }
        )

        // Цикл жизни
        CardTextField(
            label = stringResource(R.string.plant_life_cycle),
            value = plant.lifecycle.lifecycle ?: "",
            editable = editable,
            onValueChange = { onValueChange(plant.copy(lifecycle = plant.lifecycle.copy(lifecycle = it))) }
        )

        // Цветение
        CardTextField(
            label = stringResource(R.string.plant_bloom),
            value = plant.lifecycle.bloom ?: "",
            editable = editable,
            onValueChange = { onValueChange(plant.copy(lifecycle = plant.lifecycle.copy(bloom = it))) }
        )

        // Размножение
        CardTextField(
            label = stringResource(R.string.plant_reproduction),
            value = plant.lifecycle.reproduction ?: "",
            editable = editable,
            onValueChange = { onValueChange(plant.copy(lifecycle = plant.lifecycle.copy(reproduction = it))) }
        )

        // Вредители
        CardTextField(
            label = stringResource(R.string.plant_pests),
            value = plant.health.pests ?: "",
            editable = editable,
            onValueChange = { onValueChange(plant.copy(health = plant.health.copy(pests = it))) }
        )

        // Болезни
        CardTextField(
            label = stringResource(R.string.plant_diseases),
            value = plant.health.diseases ?: "",
            editable = editable,
            onValueChange = { onValueChange(plant.copy(health = plant.health.copy(diseases = it))) }
        )

        // Ядовитость
        CardCheckbox(
            label = stringResource(R.string.plant_toxic),
            checked = plant.lifecycle.isToxic ?: false,
            editable = editable,
            onCheckedChange = { onValueChange(plant.copy(lifecycle = plant.lifecycle.copy(isToxic = it))) }
        )

        // Описание растения
        CardTextField(
            label = stringResource(R.string.plant_about),
            value = plant.lifecycle.aboutThePlant ?: "",
            editable = editable,
            onValueChange = { onValueChange(plant.copy(lifecycle = plant.lifecycle.copy(aboutThePlant = it))) }
        )

        // Полив
        CardTextField(
            label = stringResource(R.string.plant_watering),
            value = plant.care.watering.watering ?: "",
            editable = editable,
            onValueChange = { onValueChange(plant.copy(care = plant.care.copy(watering = plant.care.watering.copy(watering = it)))) }
        )

        // TODO частота полива в период вегетации и сна
//        CardNumberField(
//            label = stringResource(R.string.plant_watering_frequency),
//            value = plant.care.watering.frequencyPerMonth?.toString() ?: "",
//            editable = editable,
//            onValueChange = {
//                val num = it.toIntOrNull()
//                onValueChange(plant.copy(care = plant.care.copy(watering = plant.care.watering.copy(frequencyPerMonth = num))))
//            }
//        )

        // TODO дата последнего полива
//        CardTextField(
//            label = stringResource(R.string.plant_last_watering_date),
//            value = plant.care.watering.lastDate ?: "",
//            editable = editable,
//            onValueChange = {
//                onValueChange(plant.copy(care = plant.care.copy(watering = plant.care.watering.copy(lastDate = it))))
//            }
//        )

        // TODO частота удобрения в месяцах
//        CardNumberField(
//            label = stringResource(R.string.plant_fertilization_frequency),
//            value = plant.care.fertilizer.frequencyPerMonth?.toString() ?: "",
//            editable = editable,
//            onValueChange = {
//                val num = it.toIntOrNull()
//                onValueChange(plant.copy(care = plant.care.copy(fertilizer = plant.care.fertilizer.copy(frequencyPerMonth = num))))
//            }
//        )
    }
}
