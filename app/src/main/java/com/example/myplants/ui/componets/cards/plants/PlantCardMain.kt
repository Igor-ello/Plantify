package com.example.myplants.ui.componets.cards.plants

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myplants.core.data.local.relation.PlantWithPhotos
import com.example.myplants.core.ui.theme.CardColors
import com.example.myplants.core.ui.theme.MyPlantsTheme
import com.example.myplants.domain.usecase.initialization.DataInitializer
import com.example.myplants.ui.componets.cards.common.CardBasicContent
import com.example.myplants.ui.componets.cards.common.CardIconFavourite
import com.example.myplants.ui.componets.cards.common.CardIconWishlist
import com.example.myplants.ui.componets.cards.common.CardPhoto

private object PlantCardDefaults {
    val cardElevation = 4.dp
    val cardShape = RoundedCornerShape(8.dp)
    val contentPadding = 16.dp
    val actionsSpacing = 8.dp
}

@Composable
fun PlantCardMain(
    state: PlantCardState,
    eventHandler: PlantCardEventHandler,
    modifier: Modifier = Modifier
) {
    val backgroundColor = remember() {
        CardColors.colors.get((state.plant.id % CardColors.colors.size).toInt())
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = LocalIndication.current
            ) { eventHandler.onClick(state.plantWithPhotos) },
        shape = PlantCardDefaults.cardShape,
        elevation = CardDefaults.cardElevation(PlantCardDefaults.cardElevation),
        colors = CardDefaults.cardColors(containerColor = backgroundColor)
    ) {
        Column(
            modifier = Modifier.padding(PlantCardDefaults.contentPadding),
            verticalArrangement = Arrangement.spacedBy(PlantCardDefaults.actionsSpacing)
        ) {
            // Photo section
            CardPhoto(
                imageData = state.mainPhotoBytes
            )

            // Actions row
            PlantCardActions(
                plantWithPhotos = state.plantWithPhotos,
                eventHandler = eventHandler
            )

            // Basic content
            CardBasicContent(state.plant, state.editable,
                eventHandler.onValueChange
            )
        }
    }
}

@Composable
private fun PlantCardActions(
    plantWithPhotos: PlantWithPhotos,
    eventHandler: PlantCardEventHandler,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CardIconFavourite(
                onToggleFavorite = { eventHandler.onToggleFavorite(plantWithPhotos) },
                plantWithPhotos = plantWithPhotos
            )
            CardIconWishlist(
                onToggleWishlist = { eventHandler.onToggleWishlist(plantWithPhotos) },
                plantWithPhotos = plantWithPhotos
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PlantCardMainPreview() {
    MyPlantsTheme {
        val plantWithPhotos = PlantWithPhotos(
            plant = DataInitializer.getBlankPlant(
                species = "Фикус Бенджамина",
                genus = "Фикус"
            ),
            photos = emptyList()
        )

        val state = PlantCardState(
            plantWithPhotos = plantWithPhotos,
            editable = false
        )

        val eventHandler = PlantCardEventHandler(
            onClick = { /* preview */ },
            onToggleFavorite = { /* preview */ },
            onToggleWishlist = { /* preview */ },
        )

        PlantCardMain(
            state = state,
            eventHandler = eventHandler,
            modifier = Modifier.padding(16.dp)
        )
    }
}
