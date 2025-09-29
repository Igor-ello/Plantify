package com.example.myplants.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myplants.data.plant.PlantRepositoryInterface

class AddGroupViewModelFactory(
    private val repository: PlantRepositoryInterface
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddGroupViewModel::class.java)) {
            return AddGroupViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}