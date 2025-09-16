package com.example.myplants.plants_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myplants.dao.PlantDao

class PlantViewModelFactory(private val dao: PlantDao) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PlantViewModel::class.java)) {
            return PlantViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel")
    }
}