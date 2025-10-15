package com.obsessed.ui.card_fields

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.myplants.R
import com.example.myplants.models.PlantEntityInterface
import com.example.myplants.models.sections.CareInfo
import com.example.myplants.models.sections.FertilizerInfo
import com.example.myplants.models.sections.WateringInfo


@Composable
fun CardDetailContent(
    entity: PlantEntityInterface,
    editable: Boolean,
    onValueChange: (PlantEntityInterface) -> Unit,
    showSpecies: Boolean = true
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        if (entity.main.fullName.isNullOrBlank()) {
            CardTextField(
                label = stringResource(R.string.plant_full_name),
                value = entity.main.fullName ?: "",
                editable = editable,
                onValueChange = {
                    onValueChange(entity.update(main = entity.main.copy(fullName = it)))
                }
            )
        }

        ShowMainInfo(
            entity = entity,
            editable = editable,
            onValueChange = onValueChange,
            showSpecies = showSpecies
        )

        ShowCareInfo(
            entity = entity,
            editable = editable,
            onValueChange = onValueChange
        )

        ShowLifecycleInfo(
            entity = entity,
            editable = editable,
            onValueChange = onValueChange
        )

        ShowHealthInfo(
            entity = entity,
            editable = editable,
            onValueChange = onValueChange
        )
    }
}

@Composable
fun ShowHealthInfo(
    entity: PlantEntityInterface,
    editable: Boolean,
    onValueChange: (PlantEntityInterface) -> Unit
) {
    // Вредители
    CardTextField(
        label = stringResource(R.string.plant_pests),
        value = entity.health.pests ?: "",
        editable = editable,
        onValueChange = {
            onValueChange(entity.update(health = entity.health.copy(pests = it)))
        }
    )

    // Болезни
    CardTextField(
        label = stringResource(R.string.plant_diseases),
        value = entity.health.diseases ?: "",
        editable = editable,
        onValueChange = {
            onValueChange(entity.update(health = entity.health.copy(diseases = it)))
        }
    )
}

@Composable
fun ShowLifecycleInfo(
    entity: PlantEntityInterface,
    editable: Boolean,
    onValueChange: (PlantEntityInterface) -> Unit
) {
    // Цикл жизни
    CardTextField(
        label = stringResource(R.string.plant_life_cycle),
        value = entity.lifecycle.lifecycle ?: "",
        editable = editable,
        onValueChange = {
            onValueChange(entity.update(lifecycle = entity.lifecycle.copy(lifecycle = it)))
        }
    )

    // Цветение
    CardTextField(
        label = stringResource(R.string.plant_bloom),
        value = entity.lifecycle.bloom ?: "",
        editable = editable,
        onValueChange = {
            onValueChange(entity.update(lifecycle = entity.lifecycle.copy(bloom = it)))
        }
    )

    // Размножение
    CardTextField(
        label = stringResource(R.string.plant_reproduction),
        value = entity.lifecycle.reproduction ?: "",
        editable = editable,
        onValueChange = {
            onValueChange(entity.update(lifecycle = entity.lifecycle.copy(reproduction = it)))
        }
    )

    // Дата первого посадки
    CardTextField(
        label = stringResource(R.string.plant_first_landing),
        value = entity.lifecycle.firstLanding ?: "",
        editable = editable,
        onValueChange = {
            onValueChange(entity.update(lifecycle = entity.lifecycle.copy(firstLanding = it)))
        }
    )

    // Ядовитость
    CardCheckbox(
        label = stringResource(R.string.plant_toxic),
        checked = entity.lifecycle.isToxic ?: false,
        editable = editable,
        onCheckedChange = {
            onValueChange(entity.update(lifecycle = entity.lifecycle.copy(isToxic = it)))
        }
    )

    // Описание растения
    CardTextField(
        label = stringResource(R.string.plant_about),
        value = entity.lifecycle.aboutThePlant ?: "",
        editable = editable,
        onValueChange = {
            onValueChange(entity.update(lifecycle = entity.lifecycle.copy(aboutThePlant = it)))
        }
    )
}

@Composable
fun ShowCareInfo(
    entity: PlantEntityInterface,
    editable: Boolean,
    onValueChange: (PlantEntityInterface) -> Unit
) {
    // Освещение
    CardTextField(
        label = stringResource(R.string.plant_lighting),
        value = entity.care.lighting ?: "",
        editable = editable,
        onValueChange = { newValue ->
            onValueChange(entity.updateCare { it.copy(lighting = newValue) })
        }
    )

    // Температура
    CardTextField(
        label = stringResource(R.string.plant_temperature),
        value = entity.care.temperature ?: "",
        editable = editable,
        onValueChange = { newValue ->
            onValueChange(entity.updateCare { it.copy(temperature = newValue) })
        }
    )

    // Влажность воздуха
    CardTextField(
        label = stringResource(R.string.plant_air_humidity),
        value = entity.care.airHumidity ?: "",
        editable = editable,
        onValueChange = {newValue ->
            onValueChange(entity.updateCare { it.copy(airHumidity = newValue) })
        }
    )

    // Состав почвы
    CardTextField(
        label = stringResource(R.string.soil_composition),
        value = entity.care.soilComposition ?: "",
        editable = editable,
        onValueChange = {newValue ->
            onValueChange(entity.updateCare { it.copy(soilComposition = newValue) })
        }
    )

    // Пересадка
    CardTextField(
        label = stringResource(R.string.plant_transfer),
        value = entity.care.transfer ?: "",
        editable = editable,
        onValueChange = {newValue ->
            onValueChange(entity.updateCare { it.copy(transfer = newValue) })
        }
    )

    // Вложенные таблицы

    // Полив
    CardTextField(
        label = stringResource(R.string.plant_watering),
        value = entity.care.watering.watering ?: "",
        editable = editable,
        onValueChange = { newValue ->
            onValueChange(entity.updateWatering { it.copy(watering = newValue) })
        }
    )

    // Частота полива
    CardNumberField(
        label = stringResource(R.string.plant_watering_frequency),
        value = entity.care.watering.frequencyPerMonth?.toString() ?: "",
        editable = editable,
        onValueChange = { str ->
            val num = str.toIntOrNull()
            onValueChange(entity.updateWatering { it.copy(frequencyPerMonth = num) })
        }
    )

    // Последний полив
    CardTextField(
        label = stringResource(R.string.plant_last_watering_date),
        value = entity.care.watering.lastDate ?: "",
        editable = editable,
        onValueChange = { newValue ->
            onValueChange(entity.updateWatering { it.copy(lastDate = newValue)})
        }
    )

    // Удобрения
    CardTextField(
        label = stringResource(R.string.plant_fertilizer),
        value = entity.care.fertilizer.fertilizer ?: "",
        editable = editable,
        onValueChange = { newValue ->
            onValueChange(entity.updateFertilizer { it.copy(fertilizer = newValue) })
        }
    )

    // Частота удобрений
    CardNumberField(
        label = stringResource(R.string.plant_fertilization_frequency),
        value = entity.care.fertilizer.frequencyPerMonth?.toString() ?: "",
        editable = editable,
        onValueChange = { str ->
            val num = str.toIntOrNull()
            onValueChange(entity.updateFertilizer { it.copy(frequencyPerMonth = num) })
        }
    )

    // Последнее удобрение
    CardTextField(
        label = stringResource(R.string.plant_fertilizer_last_date),
        value = entity.care.fertilizer.lastDate ?: "",
        editable = editable,
        onValueChange = { newValue ->
            onValueChange(entity.updateFertilizer { it.copy(lastDate = newValue) })
        }
    )
}

@Composable
fun ShowMainInfo(
    entity: PlantEntityInterface,
    editable: Boolean,
    onValueChange: (PlantEntityInterface) -> Unit,
    showSpecies: Boolean
) {
    // Вид
    if (showSpecies) {
        CardTextField(
            label = stringResource(R.string.plant_species),
            value = entity.main.species,
            editable = editable,
            onValueChange = {
                onValueChange(
                    entity.update(main = entity.main.copy(species = it)))
            }
        )
    }

    // Род
    CardTextField(
        label = stringResource(R.string.plant_genus),
        value = entity.main.genus,
        editable = editable,
        onValueChange = {
            onValueChange(entity.update(main = entity.main.copy(genus = it)))
        }
    )

    // Полное название
    if (!entity.main.fullName.isNullOrBlank()) {
        CardTextField(
            label = stringResource(R.string.plant_full_name),
            value = entity.main.fullName!!,
            editable = editable,
            onValueChange = {
                onValueChange(
                    entity.update(main = entity.main.copy(fullName = it))
                )
            }
        )
    }
}

fun PlantEntityInterface.updateCare(transform: (CareInfo) -> CareInfo): PlantEntityInterface =
    update(care = transform(care))

fun PlantEntityInterface.updateWatering(update: (WateringInfo) -> WateringInfo): PlantEntityInterface =
    updateCare { it.copy(watering = update(it.watering)) }

fun PlantEntityInterface.updateFertilizer(update: (FertilizerInfo) -> FertilizerInfo): PlantEntityInterface =
    updateCare { it.copy(fertilizer = update(it.fertilizer)) }

