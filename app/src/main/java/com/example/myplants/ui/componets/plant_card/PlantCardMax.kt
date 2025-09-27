package com.example.myplants.ui.componets.plant_card


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import com.example.myplants.models.Plant
import com.example.myplants.models.PlantPhoto
import com.example.myplants.models.PlantWithPhotos
import com.example.myplants.ui.componets.card.CardPhotoEditable
import com.example.myplants.ui.theme.GreenLight

@Composable
fun PlantCardMax(
    plantWithPhotos: PlantWithPhotos,
    editable: Boolean,
    onValueChange: (Plant) -> Unit,
    onPhotosChanged: (List<PlantPhoto>) -> Unit = {},
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(4.dp),
        shape = CardDefaults.shape,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = GreenLight)
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

            PlantBasicContent(plantWithPhotos.plant, editable, onValueChange)
            PlantDetailContent(plantWithPhotos.plant, editable, onValueChange)
        }
    }
}

@Composable
@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
fun PlantCardMaxPreview_Editable() {
    val samplePlant = Plant(
        id = 1L,
        name = "Монстера",
        species = "Monstera deliciosa"
    )

    val samplePhotos = listOf(
        PlantPhoto(
            id = 1L,
            plantId = 1L,
            uri = "https://example.com/plant1.jpg",
            isPrimary = true
        )
    )

    val plantWithPhotos = PlantWithPhotos(samplePlant, samplePhotos)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        PlantCardMax(
            plantWithPhotos = plantWithPhotos,
            editable = true,
            onValueChange = { /* preview - ничего не делаем */ },
            onPhotosChanged = { /* preview - ничего не делаем */ }
        )
        Spacer(modifier = Modifier.height(1000.dp))
    }
}