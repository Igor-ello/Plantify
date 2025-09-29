package com.example.myplants.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.myplants.models.Plant
import com.example.myplants.models.PlantWithPhotos

@Dao
interface PlantDao {
    @Insert
    suspend fun insert(plant: Plant): Long

    @Update
    suspend fun update(plant: Plant)

    @Query("SELECT * FROM plant_table WHERE id = :plantId")
    fun getById(plantId: Long): LiveData<Plant>

    @Query("SELECT * FROM plant_table ORDER BY id DESC")
    suspend fun getAll(): List<Plant>

    @Query("SELECT * FROM plant_table WHERE name = :name")
    suspend fun getPlantsByName(name: String): List<Plant>

    @Query("DELETE FROM plant_table WHERE id = :plantId")
    suspend fun deleteById(plantId: Long)

    @Query("DELETE FROM plant_table")
    suspend fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(plants: List<Plant>)

    // Additional
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
    suspend fun getAllPlants(): List<Plant>

    @Transaction
    @Query("SELECT * FROM plant_table WHERE is_favorite = 1 ORDER BY id DESC")
    fun getFavorites(): LiveData<List<PlantWithPhotos>>

    @Transaction
    @Query("SELECT * FROM plant_table WHERE is_wishlist = 1 ORDER BY id DESC")
    fun getWishlist(): LiveData<List<PlantWithPhotos>>
}