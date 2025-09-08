package com.example.myplants.plants

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myplants.dao.PlantDao

class PlantsViewModelFactory(private val dao: PlantDao) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PlantsViewModel::class.java)) {
            return PlantsViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel")
    }
}