package com.example.myplants.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.myplants.models.Plant
import com.example.myplants.models.PlantWithPhotos

@Dao
interface PlantDao {
    @Insert
    suspend fun insert(task: Plant): Long

    @Update
    suspend fun update(task: Plant)

    @Delete
    suspend fun delete(task: Plant)

    @Query("DELETE FROM plant_table WHERE id = :plantId")
    suspend fun deleteById(plantId: Long)

    @Query("SELECT * FROM plant_table WHERE id = :plantId")
    fun get(plantId: Long): LiveData<Plant>

    @Query("SELECT * FROM plant_table ORDER BY id DESC")
    fun getAll(): LiveData<List<Plant>>

    // Favourite
    @Query("UPDATE plant_table SET is_favorite = :isFavorite WHERE id = :plantId")
    suspend fun setFavorite(plantId: Long, isFavorite: Boolean)

    @Query("UPDATE plant_table SET is_wishlist = :isWishlist WHERE id = :plantId")
    suspend fun setWishlist(plantId: Long, isWishlist: Boolean)

    // Для PlantWithPhotos
    @Transaction
    @Query("SELECT * FROM plant_table ORDER BY id DESC")
    fun getAllWithPhotos(): LiveData<List<PlantWithPhotos>>

    @Transaction
    @Query("SELECT * FROM plant_table WHERE id = :plantId")
    fun getByIdWithPhotos(plantId: Long): LiveData<PlantWithPhotos?>

    @Query("SELECT * FROM plant_table ORDER BY id DESC")
    suspend fun getAllSnapshot(): List<Plant>

    @Transaction
    @Query("SELECT * FROM plant_table WHERE is_favorite = 1 ORDER BY id DESC")
    fun getFavorites(): LiveData<List<PlantWithPhotos>>

    @Transaction
    @Query("SELECT * FROM plant_table WHERE is_wishlist = 1 ORDER BY id DESC")
    fun getWishlist(): LiveData<List<PlantWithPhotos>>
}