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
        value = plant.main.genus,
        editable = editable,
        onValueChange = {
            onValueChange(
                plant.copy(main = plant.main.copy(genus = it))
            )
        }
    )

    CardTextField(
        label = stringResource(R.string.plant_species),
        value = plant.main.species,
        editable = editable,
        onValueChange = {
            onValueChange(
                plant.copy(main = plant.main.copy(species = it))
            )
        }
    )

    if (!plant.main.fullName.isNullOrBlank()){
        CardTextField(
            label = stringResource(R.string.plant_full_name),
            value = plant.main.fullName!!,
            editable = editable,
            onValueChange = {
                onValueChange(
                    plant.copy(main = plant.main.copy(fullName = it))
                )
            }
        )
    }
}