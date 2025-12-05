package com.example.myplants.ui.componets.cards.common

import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.myplants.core.data.local.relation.PlantWithPhotos
import com.example.myplants.ui.componets.icons.AppIcon
import com.example.myplants.ui.componets.icons.AppIcons.wishlistAnimated

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
        AppIcon(icon = wishlistAnimated(plantWithPhotos.plant.state.isWishlist))
    }
}
