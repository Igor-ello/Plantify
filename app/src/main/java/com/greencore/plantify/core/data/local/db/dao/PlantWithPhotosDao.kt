package com.greencore.plantify.core.data.local.db.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.greencore.plantify.core.data.local.relation.PlantWithPhotos
import kotlinx.coroutines.flow.Flow

@Dao
interface PlantWithPhotosDao {

    @Transaction
    @Query("SELECT * FROM plant_table ORDER BY id DESC")
    fun getAllPlantsWithPhotos(): Flow<List<PlantWithPhotos>>

    @Transaction
    @Query("SELECT * FROM plant_table WHERE id = :plantId")
    fun getPlantWithPhotosById(plantId: Long): Flow<PlantWithPhotos?>
}