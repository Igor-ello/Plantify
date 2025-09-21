package com.example.myplants.db

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("""
                    CREATE TABLE plant_photo_table (
                        id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        plant_id INTEGER,
                        uri TEXT NOT NULL,
                        created_at INTEGER NOT NULL,
                        is_primary INTEGER NOT NULL,
                        FOREIGN KEY(plant_id) REFERENCES plant_table(id) ON DELETE SET NULL
                    )
                """)

        // Создаем индекс для быстрого поиска фотографий по plantId
        database.execSQL("CREATE INDEX index_plant_photos_plantId ON plant_photos(plantId)")
    }
}