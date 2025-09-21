package com.example.myplants.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.myplants.models.PlantPhoto

@Dao
interface PlantPhotoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPhoto(photo: PlantPhoto)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPhotos(photos: List<PlantPhoto>)

    @Query("SELECT * FROM plant_photo_table WHERE plant_id = :plantId ORDER BY is_primary DESC, id ASC")
    suspend fun getPhotosForPlant(plantId: Long): List<PlantPhoto>

    @Query("UPDATE plant_photo_table SET is_primary = CASE WHEN id = :photoId THEN 1 ELSE 0 END WHERE plant_id = :plantId")
    suspend fun setMainPhoto(plantId: Long, photoId: Long)

    @Delete
    suspend fun deletePhoto(photo: PlantPhoto)

    @Update
    suspend fun updatePhoto(photo: PlantPhoto)

    @Query("DELETE FROM plant_photo_table WHERE plant_id = :plantId")
    suspend fun deletePhotosByPlantId(plantId: Long)
}
