package com.example.myplants.models

import com.example.myplants.models.sections.CareInfo
import com.example.myplants.models.sections.HealthInfo
import com.example.myplants.models.sections.LifecycleInfo
import com.example.myplants.models.sections.MainInfo
import com.example.myplants.models.sections.StateInfo

sealed interface PlantEntityInterface {
    val id: Long
    val main: MainInfo
    val care: CareInfo
    val lifecycle: LifecycleInfo
    val health: HealthInfo
    val state: StateInfo

    fun update(
        main: MainInfo = this.main,
        care: CareInfo = this.care,
        lifecycle: LifecycleInfo = this.lifecycle,
        health: HealthInfo = this.health,
        state: StateInfo = this.state
    ): PlantEntityInterface
}