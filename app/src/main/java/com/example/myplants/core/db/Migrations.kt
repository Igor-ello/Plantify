package com.example.myplants.core.db

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

val MIGRATION_2_3 = object : Migration(2, 3) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL(
            "ALTER TABLE plant_table ADD COLUMN is_favorite INTEGER NOT NULL DEFAULT 0"
        )
    }
}

val MIGRATION_3_4 = object : Migration(3, 4) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL(
            "ALTER TABLE plant_table ADD COLUMN is_wishlist INTEGER NOT NULL DEFAULT 0"
        )
    }
}