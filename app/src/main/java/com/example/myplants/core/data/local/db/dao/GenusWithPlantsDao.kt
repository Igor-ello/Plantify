package com.example.myplants.core.data.local.db.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.example.myplants.core.data.local.relation.GenusWithPlants
import kotlinx.coroutines.flow.Flow

@Dao
interface GenusWithPlantsDao {

    @Transaction
    @Query("SELECT * FROM genus_table")
    fun getAllPlantsByGenera(): Flow<List<GenusWithPlants>>

    @Transaction
    @Query("SELECT * FROM genus_table WHERE id = :id")
    fun getAllPlantsByGenusId(id: Long): Flow<List<GenusWithPlants>>

}
