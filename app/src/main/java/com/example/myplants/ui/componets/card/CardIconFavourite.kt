package com.example.myplants.ui.componets.card

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.example.myplants.R
import com.example.myplants.models.PlantWithPhotos

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
                    R.drawable.star_active
                else
                    R.drawable.star_unactive
            ),
            contentDescription = if (plantWithPhotos.plant.state.isFavorite) "Unfavorite" else "Favorite",
            tint = Color.Unspecified
        )
    }
}