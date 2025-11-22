package com.example.myplants.core.data.local.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.example.myplants.core.data.local.entity.PlantWithPhotos
import kotlinx.coroutines.flow.Flow

@Dao
interface PlantWithPhotosDao {

    @Transaction
    @Query("SELECT * FROM plant_table ORDER BY id DESC")
    fun getAllPlantsWithPhotos(): Flow<List<PlantWithPhotos>>

    @Transaction
    @Query("SELECT * FROM plant_table WHERE id = :plantId")
    fun getPlantWithPhotosById(plantId: Long): LiveData<PlantWithPhotos?>
}