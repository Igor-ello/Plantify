package com.example.myplants.ui.componets.cards.genus

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Preview
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myplants.core.data.local.relation.PlantWithPhotos
import com.example.myplants.core.ui.theme.GenusCardColor
import com.example.myplants.ui.componets.base.TitleLarge
import com.example.myplants.ui.componets.cards.plants.PlantCardEventHandler
import com.example.myplants.ui.componets.cards.plants.PlantCardMain
import com.example.myplants.ui.componets.cards.plants.PlantCardState

@Composable
fun GenusCardMain(
    state: GenusCardState,
    eventHandler: GenusCardEventHandler,
    modifier: Modifier = Modifier
) {
    // Оптимизация: используем производное состояние для предотвращения лишних рекомпозиций
    val cardContent by remember(state) {
        derivedStateOf {
            @Composable {
                Card(
                    modifier = modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    shape = CardDefaults.shape,
                    colors = CardDefaults.cardColors(containerColor = GenusCardColor)
                ) {
                    Column {
                        // Header
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { eventHandler.onToggleExpanded() }
                                .padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            TitleLarge(
                                text = state.genus.main.genus,
                                modifier = Modifier.weight(1f)
                            )

                            IconButton(
                                onClick = { eventHandler.onGenusClick(state.genus.id) },
                                modifier = Modifier.padding(start = 8.dp)
                            ) {
                                Icon(imageVector = Icons.Default.Preview, contentDescription = "Show detail")
                            }
                        }

                        // Plants list - Оптимизация: используем LazyRow вместо горизонтального скролла
                        if (state.isExpanded && state.plants.isNotEmpty()) {
                            PlantsHorizontalList(
                                plants = state.plants,
                                eventHandler = eventHandler
                            )
                        }
                    }
                }
            }
        }
    }

    cardContent()
}

@Composable
private fun PlantsHorizontalList(
    plants: List<PlantWithPhotos>,
    eventHandler: GenusCardEventHandler,
    modifier: Modifier = Modifier
) {
    val lazyRowState = rememberLazyListState()

    LazyRow(
        state = lazyRowState,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        contentPadding = PaddingValues(vertical = 10.dp)
    ) {
        items(
            items = plants,
            key = { it.plant.id },
            contentType = { "plant_card" }
        ) { plantWithPhotos ->
            PlantCardItem(
                plantWithPhotos = plantWithPhotos,
                eventHandler = eventHandler,
                modifier = Modifier
                    .width(280.dp)
                    .animateItem()
            )
        }
    }
}

@Composable
private fun PlantCardItem(
    plantWithPhotos: PlantWithPhotos,
    eventHandler: GenusCardEventHandler,
    modifier: Modifier = Modifier
) {
    val plantState = remember(plantWithPhotos) {
        PlantCardState(
            plantWithPhotos = plantWithPhotos,
            editable = false
        )
    }

    val plantEventHandler = remember(plantWithPhotos) {
        PlantCardEventHandler(
            onClick = { eventHandler.onPlantClick(plantWithPhotos.plant.id) },
            onToggleFavorite = { eventHandler.onToggleFavorite(plantWithPhotos) },
            onToggleWishlist = { eventHandler.onToggleWishlist(plantWithPhotos) },
        )
    }

    PlantCardMain(
        state = plantState,
        eventHandler = plantEventHandler,
        modifier = modifier
    )
}
