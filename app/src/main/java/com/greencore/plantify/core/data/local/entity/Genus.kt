package com.greencore.plantify.core.data.local.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.greencore.plantify.core.data.local.entity.sections.CareInfo
import com.greencore.plantify.core.data.local.entity.sections.HealthInfo
import com.greencore.plantify.core.data.local.entity.sections.LifecycleInfo
import com.greencore.plantify.core.data.local.entity.sections.MainInfo
import com.greencore.plantify.core.data.local.entity.sections.StateInfo
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "genus_table")
data class Genus (
    @PrimaryKey(autoGenerate = true)
    override var id: Long = 0L,

    @Embedded(prefix = "main_")
    override var main: MainInfo,

    @Embedded(prefix = "care_")
    override var care: CareInfo = CareInfo(),

    @Embedded(prefix = "lifecycle_")
    override var lifecycle: LifecycleInfo = LifecycleInfo(),

    @Embedded(prefix = "health_")
    override var health: HealthInfo = HealthInfo(),

    @Embedded(prefix = "state_")
    override var state: StateInfo = StateInfo()
) : PlantEntityInterface {

    override fun update(
        main: MainInfo,
        care: CareInfo,
        lifecycle: LifecycleInfo,
        health: HealthInfo,
        state: StateInfo
    ): PlantEntityInterface = copy(
        main = main,
        care = care,
        lifecycle = lifecycle,
        health = health,
        state = state
    )
}