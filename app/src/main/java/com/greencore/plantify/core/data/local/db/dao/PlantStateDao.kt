package com.greencore.plantify.core.data.local.db.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.greencore.plantify.core.data.local.relation.PlantWithPhotos
import kotlinx.coroutines.flow.Flow

@Dao
interface PlantStateDao {

    @Transaction
    @Query("SELECT * FROM plant_table WHERE state_is_favorite = 1 ORDER BY id DESC")
    fun getFavorites(): Flow<List<PlantWithPhotos>>

    @Query("UPDATE plant_table SET state_is_favorite = :isFavorite WHERE id = :plantId")
    suspend fun setFavorite(plantId: Long, isFavorite: Boolean)

    @Transaction
    @Query("SELECT * FROM plant_table WHERE state_is_wishlist = 1 ORDER BY id DESC")
    fun getWishlist(): Flow<List<PlantWithPhotos>>

    @Query("UPDATE plant_table SET state_is_wishlist = :isWishlist WHERE id = :plantId")
    suspend fun setWishlist(plantId: Long, isWishlist: Boolean)
}