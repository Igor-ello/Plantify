package com.example.myplants.ui.componets.card_fields

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.myplants.R
import com.example.myplants.models.PlantEntityInterface


@Composable
fun CardBasicContent(
    entity: PlantEntityInterface,
    editable: Boolean,
    onValueChange: (PlantEntityInterface) -> Unit,
    showSpecies: Boolean = true
) {
    CardTextAndField(
        label = stringResource(R.string.plant_genus),
        value = entity.main.genus,
        editable = editable,
        onValueChange = {
            onValueChange(
                entity.update(main = entity.main.copy(genus = it))
            )
        }
    )

    if (showSpecies) {
        CardTextAndField(
            label = stringResource(R.string.plant_species),
            value = entity.main.species,
            editable = editable,
            onValueChange = {
                onValueChange(
                    entity.update(main = entity.main.copy(species = it))
                )
            }
        )
    }

    if (!entity.main.fullName.isNullOrBlank()) {
        CardTextAndField(
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
