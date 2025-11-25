package com.example.myplants.ui.screens.topbar

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myplants.ui.componets.topbar.TopBarAction
import com.example.myplants.ui.componets.topbar.TopBarEvent
import com.example.myplants.ui.componets.topbar.TopBarUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

@HiltViewModel
class TopBarStateViewModel @Inject constructor() : ViewModel() {

    private val _uiState = mutableStateOf(TopBarUiState())
    val uiState: State<TopBarUiState> = _uiState

    private val _events = Channel<TopBarEvent>(Channel.BUFFERED)
    val events = _events.receiveAsFlow()

    // --- Методы для обновления отдельных полей ---

    fun setTitle(title: String) {
        _uiState.value = _uiState.value.copy(title)
    }

    fun showMenuButton(show: Boolean) {
        _uiState.value = _uiState.value.copy(showMenu = show)
    }

    fun showSearchField(show: Boolean) {
        _uiState.value = _uiState.value.copy(showSearch = show)
    }

    fun setActions(actions: List<TopBarAction>) {
        _uiState.value = _uiState.value.copy(actions = actions)
    }

    fun updateSearchQuery(query: String) {
        _uiState.value = _uiState.value.copy(searchQuery = query)
    }

    // --- События TopBar ---
    fun sendEvent(event: TopBarEvent) {
        viewModelScope.launch { _events.send(event) }
    }
}