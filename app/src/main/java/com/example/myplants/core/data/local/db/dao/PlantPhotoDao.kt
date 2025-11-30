package com.example.myplants.core.data.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.myplants.core.data.local.entity.PlantPhoto
import kotlinx.coroutines.flow.Flow

@Dao
interface PlantPhotoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPhoto(photo: PlantPhoto): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPhotos(photos: List<PlantPhoto>)

    @Update
    suspend fun updatePhoto(photo: PlantPhoto)

    @Query("UPDATE plant_photo_table SET is_primary = CASE WHEN id = :photoId THEN 1 ELSE 0 END WHERE plant_id = :plantId")
    suspend fun setMainPhoto(plantId: Long, photoId: Long)

    @Query("SELECT * FROM plant_photo_table ORDER BY id DESC")
    suspend fun getAllPhoto(): List<PlantPhoto>

    @Query("SELECT * FROM plant_photo_table ORDER BY id DESC")
    fun getAllPhotoLive(): Flow<List<PlantPhoto>>

    @Query("SELECT * FROM plant_photo_table WHERE plant_id = :plantId ORDER BY is_primary DESC, id ASC")
    suspend fun getPhotosForPlant(plantId: Long): List<PlantPhoto>

    @Query("DELETE FROM plant_photo_table WHERE id = :photoId")
    suspend fun deletePhotoById(photoId: Long)

    @Query("DELETE FROM plant_photo_table")
    suspend fun deleteAllPhoto()

    @Query("DELETE FROM plant_photo_table WHERE plant_id = :plantId")
    suspend fun deletePhotosByPlantId(plantId: Long)
}
