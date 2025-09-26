package com.example.myplants.ui

import com.example.myplants.data.backup.BackupRepositoryInterface
import com.example.myplants.data.plant.PlantRepositoryInterface

interface AppContainerInterface {
    val plantRepository: PlantRepositoryInterface
    val backupRepository: BackupRepositoryInterface
}