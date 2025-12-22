package com.greencore.plantify.ui.screens.main

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
import com.greencore.plantify.R
import com.greencore.plantify.ui.componets.cards.genus.GenusCardEventHandler
import com.greencore.plantify.ui.componets.cards.genus.GenusCardMain
import com.greencore.plantify.ui.componets.cards.genus.GenusCardState
import com.greencore.plantify.ui.componets.topbar.TopBarAction
import com.greencore.plantify.ui.screens.plant.state.common.ErrorState
import com.greencore.plantify.ui.screens.plant.state.common.LoadingState
import com.greencore.plantify.ui.componets.topbar.TopBarStateViewModel
import com.greencore.plantify.ui.navigation.navigators.MainScreenNavigator

@Composable
fun MainScreen(
    navigator: MainScreenNavigator
) {
    val stateHolder: MainScreenStateHolder = hiltViewModel()
    MainView(
        stateHolder = stateHolder,
        navigator = navigator
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainView(
    stateHolder: MainScreenStateHolder,
    navigator: MainScreenNavigator
) {
    SetTopBar(navigator.toAddPlant)

    val eventHandler = remember(stateHolder) {
        MainScreenEventHandler(
            onPlantClick = navigator.toPlantDetail,
            onAddPlant = navigator.toAddPlant,
            onGenusClick = navigator.toGenusDetail,
            onToggleFavorite = { stateHolder.toggleFavorite(it) },
            onToggleWishlist = { stateHolder.toggleWishlist(it) },
            onToggleGenusExpanded = { stateHolder.toggleGenusExpansion(it) }
        )
    }

    val uiState by stateHolder.uiState.collectAsState()
    MainContent(
        state = uiState,
        eventHandler = eventHandler
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
    eventHandler: MainScreenEventHandler
) {
    when {
        state.isLoading -> LoadingState()
        state.error != null -> ErrorState(
            message = state.error,
            onRetry = { /* stateHolder.retry() */ }
        )
        else -> {
            val listState = rememberLazyListState()

            val groupedPlants by remember(state.plants) {
                derivedStateOf { state.groupedPlants }
            }

            LazyColumn(
                state = listState,
                modifier = Modifier.padding(horizontal = 16.dp),
                contentPadding = PaddingValues(bottom = 64.dp)
            ) {
                items(
                    items = groupedPlants.entries.toList(),
                    key = { it.key },
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
                                onGenusClick = eventHandler.onGenusClick,
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