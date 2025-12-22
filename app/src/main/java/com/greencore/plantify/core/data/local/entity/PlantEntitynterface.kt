package com.greencore.plantify.core.data.local.entity

import com.greencore.plantify.core.data.local.entity.sections.CareInfo
import com.greencore.plantify.core.data.local.entity.sections.HealthInfo
import com.greencore.plantify.core.data.local.entity.sections.LifecycleInfo
import com.greencore.plantify.core.data.local.entity.sections.MainInfo
import com.greencore.plantify.core.data.local.entity.sections.StateInfo

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