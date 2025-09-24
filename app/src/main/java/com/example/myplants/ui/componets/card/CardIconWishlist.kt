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
fun CardIconWishlist(
    onToggleWishlist: (PlantWithPhotos) -> Unit,
    plantWithPhotos: PlantWithPhotos,
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = { onToggleWishlist(plantWithPhotos) },
        modifier = modifier
    ) {
        Icon(
            painter = painterResource(
                id = if (plantWithPhotos.plant.isWishlist)
                    R.drawable.wishlist_active
                else
                    R.drawable.wishlist_unactive
            ),
            contentDescription = if (plantWithPhotos.plant.isFavorite) "Unfavorite" else "Favorite",
            tint = Color.Unspecified
        )
    }
}