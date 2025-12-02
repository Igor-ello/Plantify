package com.example.myplants.core.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.myplants.core.data.local.entity.sections.CareInfo
import com.example.myplants.core.data.local.entity.sections.HealthInfo
import com.example.myplants.core.data.local.entity.sections.LifecycleInfo
import com.example.myplants.core.data.local.entity.sections.MainInfo
import com.example.myplants.core.data.local.entity.sections.StateInfo
import kotlinx.serialization.Serializable

@Serializable
@Entity(
    tableName = "plant_table",
    foreignKeys = [
        ForeignKey(
            entity = Genus::class,
            parentColumns = ["id"],
            childColumns = ["genus_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("genus_id")]
)
data class Plant(
    @PrimaryKey(autoGenerate = true)
    override var id: Long = 0L,

    @ColumnInfo(name = "genus_id")
    var genusId: Long,

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