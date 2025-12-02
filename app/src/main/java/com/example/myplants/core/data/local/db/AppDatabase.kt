package com.example.myplants.core.data.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.myplants.core.data.local.db.dao.GenusDao
import com.example.myplants.core.data.local.db.dao.GenusWithPlantsDao
import com.example.myplants.core.data.local.db.dao.PlantDao
import com.example.myplants.core.data.local.db.dao.PlantPhotoDao
import com.example.myplants.core.data.local.db.dao.PlantStateDao
import com.example.myplants.core.data.local.db.dao.PlantWithPhotosDao
import com.example.myplants.core.data.local.entity.Genus
import com.example.myplants.core.data.local.entity.Plant
import com.example.myplants.core.data.local.entity.PlantPhoto

@Database(
    entities = [Plant::class, PlantPhoto::class, Genus::class],
    version = 1,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {
    abstract val plantDao: PlantDao
    abstract val plantPhotoDao: PlantPhotoDao
    abstract val plantStateDao: PlantStateDao
    abstract val plantWithPhotosDao: PlantWithPhotosDao
    abstract val genusDao: GenusDao
    abstract val genusWithPlantsDao: GenusWithPlantsDao

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
                    ).build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}