package com.example.myplants.core.data.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.myplants.core.data.local.entity.Genus
import kotlinx.coroutines.flow.Flow

@Dao
interface GenusDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertGenus(genus: Genus): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertGenera(genera: List<Genus>)

    @Update
    suspend fun updateGenus(genus: Genus)

    @Query("SELECT * FROM genus_table WHERE id = :id")
    suspend fun getGenusById(id: Long): Genus?

    @Query("SELECT * FROM genus_table WHERE id = :id")
    fun getGenusByIdLive(id: Long): Flow<Genus?>

    @Query("SELECT * FROM genus_table WHERE main_genus = :genusName")
    suspend fun getGenusByName(genusName: String): Genus?

    @Query("SELECT * FROM genus_table WHERE main_genus = :genusName")
    fun getGenusByNameLive(genusName: String): Flow<Genus?>

    @Query("SELECT * FROM genus_table ORDER BY id DESC")
    fun getAllGenus(): List<Genus>

    @Query("SELECT * FROM genus_table ORDER BY id DESC")
    fun getAllGenusLive(): Flow<List<Genus>>

    @Query("DELETE FROM genus_table WHERE id = :id ")
    suspend fun deleteGenusById(id: Long)

    @Query("DELETE FROM genus_table")
    suspend fun deleteAllGenus()
}