package com.example.myplants.ui.componets.cards.plants

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.myplants.domain.usecase.initialization.DataInitializer.getSamplePlantWithPhotos
import com.example.myplants.ui.componets.cards.common.CardDetailContent
import com.example.myplants.ui.componets.cards.common.CardPhotoEditable

@Composable
fun PlantCardFull(
    state: PlantCardState,
    eventHandler: PlantCardEventHandler,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(4.dp),
        shape = CardDefaults.shape,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (state.editable) {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    itemsIndexed(state.photos) { index, photo ->
                        CardPhotoEditable(
                            photo = photo,
                            onReplace = { bitmap -> eventHandler.onReplacePhoto(index, bitmap) },
                            onDelete = { eventHandler.onDeletePhoto(index) }
                        )
                    }

                    // Кнопка добавления нового фото
                    item {
                        CardPhotoEditable(
                            photo = null,
                            onAdd = { bitmap -> eventHandler.onAddPhoto(bitmap) }
                        )
                    }
                }

            } else {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(state.photos) { photo ->
                        CardPhotoEditable(
                            photo = photo,
                            modifier = Modifier
                                .fillParentMaxWidth()
                        )
                    }
                }
            }

            CardDetailContent(state.plant, state.editable, eventHandler.onValueChange)
        }
    }
}

@Composable
@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
fun PlantCardFullPreview() {
    val plantsWithPhotos = listOf(
        getSamplePlantWithPhotos(idToInit = 1L),
        getSamplePlantWithPhotos(idToInit = 2L)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        plantsWithPhotos.forEach { plantWithPhotos ->
            PlantCardFull(
                state = PlantCardState (
                    plantWithPhotos = plantWithPhotos,
                    editable = true
                ),
                eventHandler = PlantCardEventHandler ()
            )

            PlantCardFull(
                state = PlantCardState (
                    plantWithPhotos = plantWithPhotos,
                    editable = false
                ),
                eventHandler = PlantCardEventHandler ()
            )
        }
    }
}
