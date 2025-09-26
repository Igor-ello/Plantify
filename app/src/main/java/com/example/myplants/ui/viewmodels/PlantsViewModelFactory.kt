package com.example.myplants.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myplants.data.backup.BackupRepositoryInterface
import com.example.myplants.data.plant.PlantRepositoryInterface

class PlantsViewModelFactory(
    private val repository: PlantRepositoryInterface,
    private val backupRepository: BackupRepositoryInterface
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PlantsViewModel::class.java)) {
            return PlantsViewModel(repository, backupRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
