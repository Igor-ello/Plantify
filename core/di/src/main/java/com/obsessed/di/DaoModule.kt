package com.obsessed.di

import com.example.myplants.core.db.AppDatabase
import com.example.myplants.dao.GenusDao
import com.example.myplants.dao.PlantDao
import com.example.myplants.dao.PlantPhotoDao
import com.example.myplants.dao.PlantStateDao
import com.example.myplants.dao.PlantWithPhotosDao
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
}