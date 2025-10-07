package com.example.myplants.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myplants.data.MainFacade
import com.example.myplants.data.MainFacadeInterface
import com.example.myplants.data.plant.PlantRepositoryInterface

class MainViewModelFactory(
    private val facade: MainFacadeInterface
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(facade) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
