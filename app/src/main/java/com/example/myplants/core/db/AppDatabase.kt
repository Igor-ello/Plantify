package com.example.myplants.core.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.myplants.dao.GenusDao
import com.example.myplants.dao.PlantDao
import com.example.myplants.dao.PlantPhotoDao
import com.example.myplants.dao.PlantStateDao
import com.example.myplants.dao.PlantWithPhotosDao
import com.example.myplants.models.Genus
import com.example.myplants.models.Plant
import com.example.myplants.models.PlantPhoto

@Database(
    entities = [Plant::class, PlantPhoto::class, Genus::class],
    version = 1,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {
    abstract val plantDao: PlantDao
    abstract val plantPhotoDao: PlantPhotoDao
    abstract val plantState: PlantStateDao
    abstract val plantWithPhotosDao: PlantWithPhotosDao
    abstract val genusDao: GenusDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "app_database"
                    )
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}