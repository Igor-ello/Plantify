package com.obsessed.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.example.myplants.models.PlantWithPhotos

@Dao
interface PlantStateDao {

    @Transaction
    @Query("SELECT * FROM plant_table WHERE state_is_favorite = 1 ORDER BY id DESC")
    fun getFavorites(): LiveData<List<PlantWithPhotos>>

    @Query("UPDATE plant_table SET state_is_favorite = :isFavorite WHERE id = :plantId")
    suspend fun setFavorite(plantId: Long, isFavorite: Boolean)

    @Transaction
    @Query("SELECT * FROM plant_table WHERE state_is_wishlist = 1 ORDER BY id DESC")
    fun getWishlist(): LiveData<List<PlantWithPhotos>>

    @Query("UPDATE plant_table SET state_is_wishlist = :isWishlist WHERE id = :plantId")
    suspend fun setWishlist(plantId: Long, isWishlist: Boolean)
}