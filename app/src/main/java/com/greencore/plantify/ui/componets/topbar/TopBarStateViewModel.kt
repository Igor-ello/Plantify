package com.greencore.plantify.ui.componets.topbar

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

@HiltViewModel
class TopBarStateViewModel @Inject constructor() : ViewModel() {

    private val _uiState = mutableStateOf(TopBarUiState())
    val uiState: State<TopBarUiState> = _uiState

    val actionsManager = TopBarActionsManager { actions ->
        _uiState.value = _uiState.value.copy(actions = actions)
    }

    private val _events = Channel<TopBarEvent>(Channel.Factory.BUFFERED)
    val events = _events.receiveAsFlow()

    fun setTitle(title: String) {
        _uiState.value = _uiState.value.copy(title = title)
    }

    fun showMenuButton(show: Boolean) {
        _uiState.value = _uiState.value.copy(showMenu = show)
    }

    fun showSearchField(show: Boolean) {
        _uiState.value = _uiState.value.copy(showSearch = show)
    }

    fun updateSearchQuery(query: String) {
        _uiState.value = _uiState.value.copy(searchQuery = query)
    }

    fun sendEvent(event: TopBarEvent) {
        viewModelScope.launch { _events.send(event) }
    }
}