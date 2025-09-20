package com.example.myplants.ui.navigation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class UiStateViewModel : ViewModel() {
    private var _drawerTitle by mutableStateOf("My Plants")
    val drawerTitle: String
        get() = _drawerTitle

    fun setDrawerTitle(title: String) {
        _drawerTitle = title
    }

    fun resetDrawerTitle() {
        _drawerTitle = "My Plants"
    }
}
