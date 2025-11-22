package com.example.myplants.ui.componets.cards.plants

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import com.example.myplants.core.data.local.entity.Plant
import com.example.myplants.core.data.local.entity.PlantEntityInterface
import com.example.myplants.core.data.local.entity.PlantPhoto
import com.example.myplants.core.data.local.entity.PlantWithPhotos
import com.example.myplants.core.data.local.entity.sections.MainInfo
import com.example.myplants.ui.componets.cards.common.CardDetailContent
import com.example.myplants.ui.componets.cards.common.CardPhotoEditable

@Composable
fun PlantCardFull(
    plantWithPhotos: PlantWithPhotos,
    editable: Boolean,
    onValueChange: (PlantEntityInterface) -> Unit,
    onPhotosChanged: (List<PlantPhoto>) -> Unit = {},
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
            val photos = plantWithPhotos.photos.toMutableList()

            if (editable) {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(photos) { photo ->
                        CardPhotoEditable(
                            photo = photo,
                            onPhotoSelected = { uri ->
                                val updatedPhotos = photos.map {
                                    if (it.id == photo.id) it.copy(uri = uri.toString()) else it
                                }
                                onPhotosChanged(updatedPhotos)
                            },
                            modifier = Modifier
                                .fillParentMaxWidth()
                                .sizeIn(minHeight = 180.dp)
                        )
                    }
                    item {
                        CardPhotoEditable(
                            photo = null,
                            onPhotoSelected = { uri ->
                                val newPhoto = PlantPhoto(
                                    id = 0L,
                                    plantId = plantWithPhotos.plant.id,
                                    uri = uri.toString(),
                                    isPrimary = photos.isEmpty()
                                )
                                onPhotosChanged(photos + newPhoto)
                            },
                            modifier = Modifier
                                .fillParentMaxWidth()
                                .sizeIn(minHeight = 180.dp)
                        )
                    }
                }
            } else {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(photos) { photo ->
                        AsyncImage(
                            model = photo.uri,
                            contentDescription = "Plant image",
                            modifier = Modifier
                                .fillParentMaxWidth()
                                .sizeIn(minHeight = 180.dp),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }

            CardDetailContent(plantWithPhotos.plant, editable, onValueChange)
        }
    }
}

@Composable
@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
fun PlantCardFullPreview() {
    val samplePlant1 = Plant(
        id = 1L,
        main = MainInfo(genus = "Монстера", species = "Monstera deliciosa")
    )
    val samplePlant2 = Plant(
        id = 2L,
        main = MainInfo(genus = "Фикус", species = "Ficus lyrata")
    )

    val samplePhotos1 = listOf(
        PlantPhoto(id = 1L, plantId = 1L, uri = "", isPrimary = true)
    )
    val samplePhotos2 = listOf(
        PlantPhoto(id = 2L, plantId = 2L, uri = "", isPrimary = true)
    )

    val plantsWithPhotos = listOf(
        PlantWithPhotos(samplePlant1, samplePhotos1),
        PlantWithPhotos(samplePlant2, samplePhotos2)
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
                plantWithPhotos = plantWithPhotos,
                editable = true,
                onValueChange = {},
                onPhotosChanged = {}
            )

            PlantCardFull(
                plantWithPhotos = plantWithPhotos,
                editable = false,
                onValueChange = {},
                onPhotosChanged = {}
            )
        }
    }
}
