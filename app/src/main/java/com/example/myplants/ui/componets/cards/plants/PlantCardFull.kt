package com.example.myplants.ui.componets.cards.plants

import android.graphics.Bitmap
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.myplants.core.data.local.relation.PlantWithPhotos
import com.example.myplants.ui.componets.cards.common.CardDetailContent
import com.example.myplants.ui.componets.cards.common.CardPhotoEditable

@Composable
fun PlantCardFull(
    plantWithPhotos: PlantWithPhotos,
    editable: Boolean,
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
            val photos = plantWithPhotos.photos
            if (editable) {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    itemsIndexed(photos) { index, photo ->
                        CardPhotoEditable(
                            photo = photo,
                            onReplace = { bitmap -> eventHandler.onReplacePhoto(index, bitmap) },
                            onDelete = { eventHandler.onDeletePhoto(index) },
                            modifier = Modifier
                                .fillParentMaxWidth()
                                .sizeIn(minHeight = 180.dp, maxHeight = 300.dp)
                        )
                    }

                    // Кнопка добавления нового фото
                    item {
                        CardPhotoEditable(
                            photo = null,
                            onAdd = { bitmap -> eventHandler.onAddPhoto(bitmap) },
                            modifier = Modifier
                                .fillParentMaxWidth()
                                .sizeIn(minHeight = 180.dp, maxHeight = 300.dp)
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
                            model = photo.imageData,
                            contentDescription = "Plant image",
                            modifier = Modifier
                                .fillParentMaxWidth()
                                .sizeIn(minHeight = 180.dp),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }

            // Передаём eventHandler.onValueChange
            CardDetailContent(plantWithPhotos.plant, editable, eventHandler.onValueChange)
        }
    }
}

//@Composable
//@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
//fun PlantCardFullPreview() {
//    val samplePlant1 = Plant(
//        id = 1L,
//        genusId = 1L,
//        main = MainInfo(genus = "Монстера", species = "Monstera deliciosa")
//    )
//    val samplePlant2 = Plant(
//        id = 2L,
//        genusId = 2L,
//        main = MainInfo(genus = "Фикус", species = "Ficus lyrata")
//    )
//
//    val samplePhotos1 = listOf(
//        PlantPhoto(id = 1L, plantId = 1L, uri = "", isPrimary = true)
//    )
//    val samplePhotos2 = listOf(
//        PlantPhoto(id = 2L, plantId = 2L, uri = "", isPrimary = true)
//    )
//
//    val plantsWithPhotos = listOf(
//        PlantWithPhotos(samplePlant1, samplePhotos1),
//        PlantWithPhotos(samplePlant2, samplePhotos2)
//    )
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .verticalScroll(rememberScrollState())
//            .padding(16.dp),
//        verticalArrangement = Arrangement.spacedBy(16.dp)
//    ) {
//        plantsWithPhotos.forEach { plantWithPhotos ->
//            PlantCardFull(
//                plantWithPhotos = plantWithPhotos,
//                editable = true,
//                onValueChange = {},
//                onPhotosChanged = {}
//            )
//
//            PlantCardFull(
//                plantWithPhotos = plantWithPhotos,
//                editable = false,
//                onValueChange = {},
//                onPhotosChanged = {}
//            )
//        }
//    }
//}
