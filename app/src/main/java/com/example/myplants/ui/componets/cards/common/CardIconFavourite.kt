package com.example.myplants.ui.componets.cards.common

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.example.myplants.R
import com.example.myplants.core.data.local.entity.PlantWithPhotos

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
        Icon(
            painter = painterResource(
                id = if (plantWithPhotos.plant.state.isFavorite)
                    R.drawable.favourite_active
                else
                    R.drawable.favourite_unactive
            ),
            contentDescription = if (plantWithPhotos.plant.state.isFavorite) "Unfavorite" else "Favorite",
            tint = Color.Unspecified
        )
    }
}