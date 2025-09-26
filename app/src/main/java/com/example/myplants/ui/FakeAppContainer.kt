package com.example.myplants.ui

import com.example.myplants.data.backup.BackupRepositoryInterface
import com.example.myplants.data.plant.PlantRepositoryInterface

class FakeAppContainer(
    override val plantRepository: PlantRepositoryInterface,
    override val backupRepository: BackupRepositoryInterface
) : AppContainerInterface