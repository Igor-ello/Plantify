package com.greencore.plantify.ui.componets.topbar

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf

data class TopBarAction(
    val id: String,
    val icon: @Composable () -> Unit,
    val onClick: () -> Unit
)

class TopBarActionsManager(
    private val onActionsChanged: (List<TopBarAction>) -> Unit
) {

    private val _actions = mutableStateListOf<TopBarAction>()
    val actions: List<TopBarAction> get() = _actions

    fun set(vararg newActions: TopBarAction) {
        _actions.clear()
        _actions.addAll(newActions)
        onActionsChanged(_actions)
    }

    fun add(action: TopBarAction) {
        _actions.add(action)
        onActionsChanged(_actions)
    }

    fun remove(actionId: String) {
        _actions.removeAll { it.id == actionId }
        onActionsChanged(_actions)
    }
}
