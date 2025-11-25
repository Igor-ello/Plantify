package com.example.myplants.ui.screens.plant.state

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myplants.core.data.local.entity.PlantWithPhotos
import com.example.myplants.ui.componets.cards.plants.PlantCardEventHandler
import com.example.myplants.ui.componets.cards.plants.PlantCardMain
import com.example.myplants.ui.componets.cards.plants.PlantCardState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.myplants.R
import com.example.myplants.ui.screens.plant.state.common.ErrorState
import com.example.myplants.ui.screens.plant.state.common.LoadingState
import com.example.myplants.ui.screens.plant.state.common.PlantListEmptyState
import com.example.myplants.ui.componets.topbar.TopBarStateViewModel

@Composable
fun PlantStateScreen(
    viewModel: PlantStateViewModel,
    listType: PlantStateType,
    onPlantClick: (PlantWithPhotos) -> Unit,
    modifier: Modifier = Modifier
) {
    val topBarState: TopBarStateViewModel = hiltViewModel()
    val currentListType by rememberUpdatedState(listType)

    // Устанавливаем заголовок и кнопки в TopBar
    val titleRes = when (currentListType) {
        PlantStateType.FAVORITES -> R.string.screen_favorites
        PlantStateType.WISHLIST -> R.string.screen_wishlist
        else -> R.string.app_name
    }
    val title = stringResource(titleRes)
    LaunchedEffect(currentListType) {
        viewModel.setListType(currentListType)
        topBarState.setTitle(title)
    }

    val uiState by viewModel.uiState.collectAsState()

    // ОПТИМИЗАЦИЯ: выносим обработчики в remember для стабильности
    val onToggleFavorite = remember(viewModel) {
        { plantWithPhotos: PlantWithPhotos ->
            viewModel.toggleFavorite(plantWithPhotos.plant)
        }
    }

    val onToggleWishlist = remember(viewModel) {
        { plantWithPhotos: PlantWithPhotos ->
            viewModel.toggleWishlist(plantWithPhotos.plant)
        }
    }

    when (val state = uiState) {
        is PlantListUiState.Loading -> LoadingState(modifier = modifier)
        is PlantListUiState.Error -> ErrorState(
            message = state.message,
            onRetry = { viewModel.retry() },
            modifier = modifier
        )
        is PlantListUiState.Success -> {
            // ПРОСТАЯ И ПРАВИЛЬНАЯ ВЕРСИЯ:
            PlantListContent(
                plants = state.plants,
                listType = currentListType,
                onPlantClick = onPlantClick,
                onToggleFavorite = onToggleFavorite,
                onToggleWishlist = onToggleWishlist,
                modifier = modifier
            )
        }
    }
}

@Composable
fun PlantListContent(
    plants: List<PlantWithPhotos>,
    listType: PlantStateType,
    onPlantClick: (PlantWithPhotos) -> Unit,
    onToggleFavorite: (PlantWithPhotos) -> Unit,
    onToggleWishlist: (PlantWithPhotos) -> Unit,
    modifier: Modifier = Modifier,
    emptyState: @Composable (PlantStateType) -> Unit = { type -> PlantListEmptyState(type) }
) {
    if (plants.isEmpty()) {
        emptyState(listType)
    } else {
        val lazyListState = rememberLazyListState()

        LazyColumn(
            state = lazyListState,
            modifier = modifier.padding(16.dp),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            items(
                items = plants,
                key = { it.plant.id },
                contentType = { "plant_card" }
            ) { plantWithPhotos ->
                val plantState by remember(plantWithPhotos) {
                    derivedStateOf {
                        PlantCardState(plantWithPhotos = plantWithPhotos)
                    }
                }

                val plantEventHandler = remember(plantWithPhotos) {
                    PlantCardEventHandler(
                        onClick = onPlantClick,
                        onToggleFavorite = onToggleFavorite,
                        onToggleWishlist = onToggleWishlist
                    )
                }

                PlantCardMain(
                    state = plantState,
                    eventHandler = plantEventHandler,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .animateItem()
                )
            }
        }
    }
}