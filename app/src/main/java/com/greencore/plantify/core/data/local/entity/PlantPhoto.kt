package com.greencore.plantify.core.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(
    tableName = "plant_photo_table",
    foreignKeys = [
        ForeignKey(
            entity = Plant::class,
            parentColumns = ["id"],
            childColumns = ["plant_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("plant_id")]
)
data class PlantPhoto(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    @ColumnInfo(name = "plant_id")
    val plantId: Long,

    @ColumnInfo(name = "uri")
    val uri: String? = null,

    @ColumnInfo(name = "image_data", typeAffinity = ColumnInfo.BLOB)
    val imageData: ByteArray? = null,

    @ColumnInfo(name = "is_primary")
    val isPrimary: Boolean = false,

    @ColumnInfo(name = "created_at")
    val createdAt: Long = System.currentTimeMillis()
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PlantPhoto

        if (id != other.id) return false
        if (plantId != other.plantId) return false
        if (isPrimary != other.isPrimary) return false
        if (createdAt != other.createdAt) return false
        if (uri != other.uri) return false
        if (!imageData.contentEquals(other.imageData)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + plantId.hashCode()
        result = 31 * result + isPrimary.hashCode()
        result = 31 * result + createdAt.hashCode()
        result = 31 * result + uri.hashCode()
        result = 31 * result + (imageData?.contentHashCode() ?: 0)
        return result
    }
}
