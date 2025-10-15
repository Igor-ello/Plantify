package com.obsessed.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.example.myplants.models.PlantWithPhotos

@Dao
interface PlantWithPhotosDao {

    @Transaction
    @Query("SELECT * FROM plant_table ORDER BY id DESC")
    fun getAllPlantsWithPhotos(): LiveData<List<PlantWithPhotos>>

    @Transaction
    @Query("SELECT * FROM plant_table WHERE id = :plantId")
    fun getPlantWithPhotosById(plantId: Long): LiveData<PlantWithPhotos?>
}