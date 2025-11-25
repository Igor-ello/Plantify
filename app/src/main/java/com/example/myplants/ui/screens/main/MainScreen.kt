package com.example.myplants.ui.screens.main

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.myplants.R
import com.example.myplants.core.data.local.entity.PlantWithPhotos
import com.example.myplants.ui.componets.cards.genus.GenusCardEventHandler
import com.example.myplants.ui.componets.cards.genus.GenusCardMain
import com.example.myplants.ui.componets.cards.genus.GenusCardState
import com.example.myplants.ui.componets.topbar.TopBarAction
import com.example.myplants.ui.screens.plant.state.common.ErrorState
import com.example.myplants.ui.screens.plant.state.common.LoadingState
import com.example.myplants.ui.componets.topbar.TopBarStateViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainScreen(
    stateHolder: MainScreenStateHolder,
    onPlantClick: (PlantWithPhotos) -> Unit,
    onAddPlant: () -> Unit,
    onNavigateToGenusDetail: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    SetTopBar(onAddPlant)

    val eventHandler = remember(stateHolder, onPlantClick, onNavigateToGenusDetail) {
        MainScreenEventHandler(
            onPlantClick = onPlantClick,
            onAddPlant = onAddPlant,
            onToggleFavorite = { stateHolder.toggleFavorite(it) },
            onToggleWishlist = { stateHolder.toggleWishlist(it) },
            onNavigateToGenusDetail = onNavigateToGenusDetail,
            onToggleGenusExpanded = { stateHolder.toggleGenusExpansion(it) }
        )
    }

    val uiState by stateHolder.uiState.collectAsState()
    MainContent(
        state = uiState,
        eventHandler = eventHandler,
        modifier = modifier
    )
}

@Composable
private fun SetTopBar(
    onAddPlant: () -> Unit
) {
    val topBarState: TopBarStateViewModel = hiltViewModel()
    val title = stringResource(R.string.screen_all_plants)

    LaunchedEffect(Unit) {
        topBarState.setTitle(title)
        topBarState.actionsManager.set(
            TopBarAction(
                id = "add",
                icon = { Icon(Icons.Default.Add, contentDescription = "Add") },
                onClick = onAddPlant
            )
        )
    }
}

@Composable
private fun MainContent(
    state: MainScreenState,
    eventHandler: MainScreenEventHandler,
    modifier: Modifier = Modifier
) {
    when {
        state.isLoading -> LoadingState(modifier = modifier)
        state.error != null -> ErrorState(
            message = state.error,
            onRetry = { /* stateHolder.retry() */ },
            modifier = modifier
        )
        else -> {
            val listState = rememberLazyListState()

            // Оптимизация: используем производное состояние для groupedPlants
            val groupedPlants by remember(state.plants) {
                derivedStateOf { state.groupedPlants }
            }

            LazyColumn(
                state = listState,
                modifier = modifier.padding(horizontal = 16.dp),
                contentPadding = PaddingValues(bottom = 64.dp)
            ) {
                items(
                    items = groupedPlants.entries.toList(),
                    key = { it.key }, // Используем genusName как ключ
                    contentType = { "genus_card" }
                ) { (genusName, plants) ->
                    val genus = state.genusMap[genusName]
                    if (genus != null) {
                        val genusState = remember(genus.id, plants, state.isGenusExpanded(genus.id)) {
                            GenusCardState(
                                genus = genus,
                                plants = plants,
                                isExpanded = state.isGenusExpanded(genus.id)
                            )
                        }

                        val genusEventHandler = remember(genus.id, eventHandler) {
                            GenusCardEventHandler(
                                onPlantClick = eventHandler.onPlantClick,
                                onToggleFavorite = eventHandler.onToggleFavorite,
                                onToggleWishlist = eventHandler.onToggleWishlist,
                                onNavigateToGenusDetail = eventHandler.onNavigateToGenusDetail,
                                onToggleExpanded = { eventHandler.onToggleGenusExpanded(genus.id) }
                            )
                        }

                        GenusCardMain(
                            state = genusState,
                            eventHandler = genusEventHandler,
                            modifier = Modifier
                                .padding(top = 12.dp)
                                .animateItem()
                        )
                    }
                }
            }
        }
    }
}