package com.example.myplants.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.myplants.models.Genus

@Dao
interface GenusDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertGenus(genus: Genus): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertGenera(genera: List<Genus>)

    @Update
    suspend fun updateGenus(genus: Genus)

    @Query("SELECT * FROM genus_table ORDER BY id DESC")
    fun getAllGenusLive(): LiveData<List<Genus>>

    @Query("SELECT * FROM genus_table ORDER BY id DESC")
    suspend fun getAllGenus(): List<Genus>

    @Query("SELECT * FROM genus_table WHERE id = :id")
    fun getGenusByIdLive(id: Long): LiveData<Genus>

    @Query("SELECT * FROM genus_table WHERE id = :id")
    suspend fun getGenusById(id: Long): Genus

    @Query("SELECT * FROM genus_table WHERE main_genus = :genusName")
    fun getGenusByNameLive(genusName: String): LiveData<Genus>

    @Query("SELECT * FROM genus_table WHERE main_genus = :genusName")
    suspend fun getGenusByName(genusName: String): Genus

    @Query("DELETE FROM genus_table")
    fun deleteAllGenus()

    @Query("DELETE FROM genus_table WHERE id = :id ")
    suspend fun deleteGenusById(id: Long)
}