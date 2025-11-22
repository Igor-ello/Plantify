package com.example.myplants.ui.componets.cards.common

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.myplants.R
import com.example.myplants.core.data.local.entity.PlantEntityInterface
import com.example.myplants.ui.componets.base.NamedTextField


@Composable
fun CardBasicContent(
    entity: PlantEntityInterface,
    editable: Boolean,
    onValueChange: (PlantEntityInterface) -> Unit,
    showSpecies: Boolean = true
) {
    NamedTextField(
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
        NamedTextField(
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
        NamedTextField(
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
