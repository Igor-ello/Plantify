package com.example.myplants.ui.componets.plant_card

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.myplants.R
import com.example.myplants.models.Plant
import com.example.myplants.ui.componets.card.CardTextField


@Composable
fun PlantBasicContent(
    plant: Plant,
    editable: Boolean,
    onValueChange: (Plant) -> Unit
) {
    CardTextField(
        label = stringResource(R.string.plant_name),
        value = plant.name,
        editable = editable,
        onValueChange = { onValueChange(plant.copy(name = it)) }
    )

    CardTextField(
        label = stringResource(R.string.plant_species),
        value = plant.species,
        editable = editable,
        onValueChange = { onValueChange(plant.copy(species = it)) }
    )
}