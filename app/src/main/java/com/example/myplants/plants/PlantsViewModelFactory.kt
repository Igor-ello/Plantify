package com.example.myplants.plants

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myplants.backup.BackupRepository
import com.example.myplants.dao.PlantDao

class PlantsViewModelFactory(
    private val repository: PlantRepository,
    private val backupRepository: BackupRepository
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PlantsViewModel::class.java)) {
            return PlantsViewModel(repository, backupRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
