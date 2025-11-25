package com.example.myplants.ui.componets.topbar

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf

data class TopBarAction(
    val id: String,
    val icon: @Composable () -> Unit,
    val onClick: () -> Unit
)

// TODO
class TopBarActionsManager {
    private val _actions = mutableStateListOf<TopBarAction>()
    val actions: List<TopBarAction> get() = _actions

    fun setActions(newActions: List<TopBarAction>) {
        _actions.clear()
        _actions.addAll(newActions)
    }

    fun addAction(action: TopBarAction) {
        _actions.add(action)
    }

    fun removeAction(actionId: String) {
        _actions.removeAll { it.id == actionId }
    }
}
