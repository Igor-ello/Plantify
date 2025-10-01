package com.example.myplants.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myplants.models.Genus

@Dao
interface GenusDao {
    @Query("SELECT * FROM genus_table ORDER BY id DESC")
    suspend fun getAll(): List<Genus>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(genus: Genus): Long
}