package com.example.myplants.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.myplants.models.Genus

@Dao
interface GenusDao {
    @Query("SELECT * FROM genus_table ORDER BY id DESC")
    suspend fun getAll(): List<Genus>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(genus: Genus): Long

    @Query("SELECT * FROM genus_table WHERE id = :id")
    suspend fun getGenusById(id: Long): Genus

    @Query("SELECT * FROM genus_table WHERE main_genus = :name")
    suspend fun getGenusByName(name: String): Genus?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGenus(genus: Genus): Long

    @Update
    suspend fun updateGenus(genus: Genus)

    @Delete
    suspend fun deleteGenus(genus: Genus)
}