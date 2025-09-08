package com.example.myplants.ui.plant_card

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.myplants.R
import com.example.myplants.models.Plant
import com.example.myplants.ui.componets.LabeledCheckbox
import com.example.myplants.ui.componets.LabeledDropdown
import com.example.myplants.ui.componets.LabeledNumberField
import com.example.myplants.ui.componets.LabeledTextField


@Composable
fun ParametersSection(
    plant: Plant,
    editable: Boolean,
    onValueChange: (Plant) -> Unit
) {
    Column {
        LabeledTextField(
            label = stringResource(R.string.plant_subspecies),
            value = plant.subSpecies ?: "",
            editable = editable,
            onValueChange = { onValueChange(plant.copy(subSpecies = it)) }
        )

        LabeledTextField(
            label = stringResource(R.string.plant_lighting),
            value = plant.lighting ?: "",
            editable = editable,
            onValueChange = { onValueChange(plant.copy(lighting = it)) }
        )

        LabeledTextField(
            label = stringResource(R.string.plant_bloom),
            value = plant.bloom ?: "",
            editable = editable,
            onValueChange = { onValueChange(plant.copy(bloom = it)) }
        )

        LabeledTextField(
            label = stringResource(R.string.soil_composition),
            value = plant.soilComposition ?: "",
            editable = editable,
            onValueChange = { onValueChange(plant.copy(soilComposition = it)) }
        )

        LabeledTextField(
            label = stringResource(R.string.plant_transfer),
            value = plant.transfer ?: "",
            editable = editable,
            onValueChange = { onValueChange(plant.copy(transfer = it)) }
        )

        LabeledTextField(
            label = stringResource(R.string.plant_temperature),
            value = plant.temperature ?: "",
            editable = editable,
            onValueChange = { onValueChange(plant.copy(temperature = it)) }
        )

        LabeledTextField(
            label = stringResource(R.string.plant_air_humidity),
            value = plant.airHumidity ?: "",
            editable = editable,
            onValueChange = { onValueChange(plant.copy(airHumidity = it)) }
        )

        LabeledTextField(
            label = stringResource(R.string.plant_rest_period),
            value = plant.restPeriod ?: "",
            editable = editable,
            onValueChange = { onValueChange(plant.copy(restPeriod = it)) }
        )

        LabeledTextField(
            label = stringResource(R.string.plant_reproduction),
            value = plant.reproduction ?: "",
            editable = editable,
            onValueChange = { onValueChange(plant.copy(reproduction = it)) }
        )

        LabeledTextField(
            label = stringResource(R.string.plant_pests),
            value = plant.pests ?: "",
            editable = editable,
            onValueChange = { onValueChange(plant.copy(pests = it)) }
        )

        LabeledTextField(
            label = stringResource(R.string.plant_diseases),
            value = plant.diseases ?: "",
            editable = editable,
            onValueChange = { onValueChange(plant.copy(diseases = it)) }
        )

        LabeledTextField(
            label = stringResource(R.string.palnt_first_landing),
            value = plant.firstLanding ?: "",
            editable = editable,
            onValueChange = { onValueChange(plant.copy(firstLanding = it)) }
        )

        LabeledDropdown(
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

        LabeledCheckbox(
            label = stringResource(R.string.plant_toxic),
            checked = plant.isToxic ?: false,
            editable = editable,
            onCheckedChange = { onValueChange(plant.copy(isToxic = it)) }
        )

        LabeledTextField(
            label = stringResource(R.string.plant_about),
            value = plant.aboutThePlant ?: "",
            editable = editable,
            onValueChange = { onValueChange(plant.copy(aboutThePlant = it)) }
        )

        LabeledTextField(
            label = stringResource(R.string.plant_watering),
            value = plant.watering ?: "",
            editable = editable,
            onValueChange = { onValueChange(plant.copy(watering = it)) }
        )

        LabeledNumberField(
            label = stringResource(R.string.plant_watering_frequency),
            value = plant.wateringFrequency?.toString() ?: "",
            editable = editable,
            onValueChange = {
                val num = it.toIntOrNull()
                onValueChange(plant.copy(wateringFrequency = num))
            }
        )

        LabeledTextField(
            label = stringResource(R.string.plant_last_watering_date),
            value = plant.lastWateringDate ?: "",
            editable = editable,
            onValueChange = { onValueChange(plant.copy(lastWateringDate = it)) }
        )


        LabeledTextField(
            label = stringResource(R.string.plant_fertilizer),
            value = plant.fertilizer ?: "",
            editable = editable,
            onValueChange = { onValueChange(plant.copy(fertilizer = it)) }
        )

        LabeledNumberField(
            label = stringResource(R.string.plant_fertilization_frequency),
            value = plant.fertilizationFrequency?.toString() ?: "",
            editable = editable,
            onValueChange = {
                val num = it.toIntOrNull()
                onValueChange(plant.copy(fertilizationFrequency = num))
            }
        )
    }
}