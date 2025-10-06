package com.example.myplants.ui

import com.example.myplants.data.backup.BackupRepositoryInterface
import com.example.myplants.data.plant.PlantRepositoryInterface
import com.example.myplants.ui.viewmodels.GenusRepository

interface AppContainerInterface {
    val plantRepository: PlantRepositoryInterface
    val backupRepository: BackupRepositoryInterface
    val genusRepository: GenusRepository
}