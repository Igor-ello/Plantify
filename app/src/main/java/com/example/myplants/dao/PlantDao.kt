package com.example.myplants.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.myplants.models.Plant

@Dao
interface PlantDao {
    @Insert
    suspend fun insert(task: Plant)

    @Update
    suspend fun update(task: Plant)

    @Delete
    suspend fun delete(task: Plant)

    @Query("SELECT * FROM plant_table WHERE id = :plantId")
    fun get(plantId: Long): LiveData<Plant>

    @Query("SELECT * FROM plant_table ORDER BY id DESC")
    fun getAll(): LiveData<List<Plant>>
}