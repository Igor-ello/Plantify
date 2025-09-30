package com.example.myplants.models

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.myplants.models.sections.CareInfo
import com.example.myplants.models.sections.HealthInfo
import com.example.myplants.models.sections.LifecycleInfo
import com.example.myplants.models.sections.MainInfo
import com.example.myplants.models.sections.StateInfo
import kotlinx.serialization.Serializable

@Serializable
@Entity(
    tableName = "plant_table",
    foreignKeys = [
        ForeignKey(
            entity = Genus::class,
            parentColumns = ["id"],
            childColumns = ["genus_id"],
            onDelete = ForeignKey.SET_NULL
        )
    ],
    indices = [Index("genus_id")]
)
data class Plant(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,

    @ColumnInfo(name = "genus_id")
    var genusId: Long? = null,

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