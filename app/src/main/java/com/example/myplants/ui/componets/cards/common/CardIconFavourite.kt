package com.example.myplants.ui.componets.cards.common

import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.myplants.core.data.local.relation.PlantWithPhotos
import com.example.myplants.ui.componets.icons.AppIcon
import com.example.myplants.ui.componets.icons.AppIcons.favoriteAnimated

@Composable
fun CardIconFavourite(
    onToggleFavorite: (PlantWithPhotos) -> Unit,
    plantWithPhotos: PlantWithPhotos,
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = { onToggleFavorite(plantWithPhotos) },
        modifier = modifier
    ) {
        AppIcon(icon = favoriteAnimated(plantWithPhotos.plant.state.isFavorite))
    }
}