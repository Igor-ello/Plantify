package com.example.myplants.ui.componets.plant_card

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.myplants.R
import com.example.myplants.models.Plant
import com.example.myplants.ui.componets.card.CardCheckbox
import com.example.myplants.ui.componets.card.CardDropdown
import com.example.myplants.ui.componets.card.CardNumberField
import com.example.myplants.ui.componets.card.CardTextField


@Composable
fun PlantDetailContent(
    plant: Plant,
    editable: Boolean,
    onValueChange: (Plant) -> Unit
) {
    Column (
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        if (plant.subSpecies.isNullOrBlank()){
            CardTextField(
                label = stringResource(R.string.plant_subspecies),
                value = plant.subSpecies ?: "",
                editable = editable,
                onValueChange = { onValueChange(plant.copy(subSpecies = it)) }
            )
        }

        CardTextField(
            label = stringResource(R.string.plant_lighting),
            value = plant.lighting ?: "",
            editable = editable,
            onValueChange = { onValueChange(plant.copy(lighting = it)) }
        )

        CardDropdown(
            label = stringResource(R.string.plant_sunlight_requirement),
            items = listOf(
                stringResource(R.string.sunlight_shadeloving),
                stringResource(R.string.plant_moderate),
                stringResource(R.string.plant_light_loving)
            ),
            selectedItem = plant.sunlightRequirement ?: "",
            editable = editable,
            onItemSelected = {
                onValueChange(plant.copy(sunlightRequirement = it.ifEmpty { null }))
            }
        )

        CardTextField(
            label = stringResource(R.string.plant_watering),
            value = plant.watering ?: "",
            editable = editable,
            onValueChange = { onValueChange(plant.copy(watering = it)) }
        )

        CardNumberField(
            label = stringResource(R.string.plant_watering_frequency),
            value = plant.wateringFrequency?.toString() ?: "",
            editable = editable,
            onValueChange = {
                val num = it.toIntOrNull()
                onValueChange(plant.copy(wateringFrequency = num))
            }
        )

        CardTextField(
            label = stringResource(R.string.plant_last_watering_date),
            value = plant.lastWateringDate ?: "",
            editable = editable,
            onValueChange = { onValueChange(plant.copy(lastWateringDate = it)) }
        )

        CardTextField(
            label = stringResource(R.string.plant_temperature),
            value = plant.temperature ?: "",
            editable = editable,
            onValueChange = { onValueChange(plant.copy(temperature = it)) }
        )

        CardTextField(
            label = stringResource(R.string.plant_air_humidity),
            value = plant.airHumidity ?: "",
            editable = editable,
            onValueChange = { onValueChange(plant.copy(airHumidity = it)) }
        )

        CardTextField(
            label = stringResource(R.string.plant_life_cycle),
            value = plant.restPeriod ?: "",
            editable = editable,
            onValueChange = { onValueChange(plant.copy(restPeriod = it)) }
        )

        CardTextField(
            label = stringResource(R.string.plant_bloom),
            value = plant.bloom ?: "",
            editable = editable,
            onValueChange = { onValueChange(plant.copy(bloom = it)) }
        )

        CardTextField(
            label = stringResource(R.string.soil_composition),
            value = plant.soilComposition ?: "",
            editable = editable,
            onValueChange = { onValueChange(plant.copy(soilComposition = it)) }
        )

        CardTextField(
            label = stringResource(R.string.plant_transfer),
            value = plant.transfer ?: "",
            editable = editable,
            onValueChange = { onValueChange(plant.copy(transfer = it)) }
        )

        CardTextField(
            label = stringResource(R.string.plant_reproduction),
            value = plant.reproduction ?: "",
            editable = editable,
            onValueChange = { onValueChange(plant.copy(reproduction = it)) }
        )

        CardTextField(
            label = stringResource(R.string.plant_fertilizer),
            value = plant.fertilizer ?: "",
            editable = editable,
            onValueChange = { onValueChange(plant.copy(fertilizer = it)) }
        )

        CardNumberField(
            label = stringResource(R.string.plant_fertilization_frequency),
            value = plant.fertilizationFrequency?.toString() ?: "",
            editable = editable,
            onValueChange = {
                val num = it.toIntOrNull()
                onValueChange(plant.copy(fertilizationFrequency = num))
            }
        )

        CardTextField(
            label = stringResource(R.string.plant_pests),
            value = plant.pests ?: "",
            editable = editable,
            onValueChange = { onValueChange(plant.copy(pests = it)) }
        )

        CardTextField(
            label = stringResource(R.string.plant_diseases),
            value = plant.diseases ?: "",
            editable = editable,
            onValueChange = { onValueChange(plant.copy(diseases = it)) }
        )

        CardCheckbox(
            label = stringResource(R.string.plant_toxic),
            checked = plant.isToxic ?: false,
            editable = editable,
            onCheckedChange = { onValueChange(plant.copy(isToxic = it)) }
        )

        CardTextField(
            label = stringResource(R.string.palnt_first_landing),
            value = plant.firstLanding ?: "",
            editable = editable,
            onValueChange = { onValueChange(plant.copy(firstLanding = it)) }
        )

        CardTextField(
            label = stringResource(R.string.plant_about),
            value = plant.aboutThePlant ?: "",
            editable = editable,
            onValueChange = { onValueChange(plant.copy(aboutThePlant = it)) }
        )
    }
}