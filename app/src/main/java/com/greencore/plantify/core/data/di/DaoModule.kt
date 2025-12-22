package com.greencore.plantify.core.data.di

import com.greencore.plantify.core.data.local.db.AppDatabase
import com.greencore.plantify.core.data.local.db.dao.GenusDao
import com.greencore.plantify.core.data.local.db.dao.GenusWithPlantsDao
import com.greencore.plantify.core.data.local.db.dao.PlantDao
import com.greencore.plantify.core.data.local.db.dao.PlantPhotoDao
import com.greencore.plantify.core.data.local.db.dao.PlantStateDao
import com.greencore.plantify.core.data.local.db.dao.PlantWithPhotosDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DaoModule {

    @Provides
    @Singleton
    fun providePlantDao(
        appDatabase: AppDatabase
    ): PlantDao = appDatabase.plantDao

    @Provides
    @Singleton
    fun providePhotoDao(
        database: AppDatabase
    ): PlantPhotoDao = database.plantPhotoDao

    @Provides
    @Singleton
    fun provideGenusDao(
        database: AppDatabase
    ): GenusDao = database.genusDao

    @Provides
    @Singleton
    fun providePlantStateDao(
        database: AppDatabase
    ): PlantStateDao = database.plantStateDao

    @Provides
    @Singleton
    fun providePlantWithPhotosDao(
        database: AppDatabase
    ): PlantWithPhotosDao = database.plantWithPhotosDao

    @Provides
    @Singleton
    fun provideGenusWithPlantsDao(
        database: AppDatabase
    ): GenusWithPlantsDao = database.genusWithPlantsDao
}