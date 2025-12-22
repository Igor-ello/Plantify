package com.greencore.plantify.core.data.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.greencore.plantify.core.data.local.entity.Plant
import kotlinx.coroutines.flow.Flow

@Dao
interface PlantDao {
    @Insert
    suspend fun insertPlant(plant: Plant): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPlants(plants: List<Plant>)

    @Update
    suspend fun updatePlant(plant: Plant)

    @Query("SELECT * FROM plant_table WHERE id = :plantId")
    suspend fun getPlantById(plantId: Long): Plant?

    @Query("SELECT * FROM plant_table WHERE id = :plantId")
    fun getPlantByIdLive(plantId: Long): Flow<Plant?>

    @Query("SELECT * FROM plant_table ORDER BY id DESC")
    suspend fun getAllPlants(): List<Plant>

    @Query("SELECT * FROM plant_table ORDER BY id DESC")
    fun getAllPlantsLive(): Flow<List<Plant>>

    @Query("DELETE FROM plant_table WHERE id = :plantId")
    suspend fun deletePlantById(plantId: Long)

    @Query("DELETE FROM plant_table")
    suspend fun deleteAllPlants()
}