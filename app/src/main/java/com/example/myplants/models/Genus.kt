package com.example.myplants.models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.myplants.models.sections.CareInfo
import com.example.myplants.models.sections.HealthInfo
import com.example.myplants.models.sections.LifecycleInfo
import com.example.myplants.models.sections.MainInfo
import com.example.myplants.models.sections.StateInfo
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "genus_table")
data class Genus (
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,

    @Embedded(prefix = "main_")
    var main: MainInfo,

    @Embedded(prefix = "care_")
    var care: CareInfo = CareInfo(),

    @Embedded(prefix = "lifecycle_")
    var lifecycle: LifecycleInfo = LifecycleInfo(),

    @Embedded(prefix = "health_")
    var health: HealthInfo = HealthInfo(),

    @Embedded(prefix = "state_")
    var state: StateInfo = StateInfo()
)