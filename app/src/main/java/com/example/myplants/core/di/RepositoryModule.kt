package com.example.myplants.core.di

import android.content.Context
import com.example.myplants.dao.GenusDao
import com.example.myplants.dao.PlantDao
import com.example.myplants.dao.PlantPhotoDao
import com.example.myplants.dao.PlantStateDao
import com.example.myplants.dao.PlantWithPhotosDao
import com.example.myplants.data.main_facade.MainFacade
import com.example.myplants.data.main_facade.MainFacadeInterface
import com.example.myplants.data.backup.BackupRepository
import com.example.myplants.data.backup.BackupRepositoryInterface
import com.example.myplants.data.genus.GenusRepository
import com.example.myplants.data.genus.GenusRepositoryInterface
import com.example.myplants.data.photo.PhotoRepository
import com.example.myplants.data.photo.PhotoRepositoryInterface
import com.example.myplants.data.plant.PlantRepository
import com.example.myplants.data.plant.PlantRepositoryInterface
import com.example.myplants.data.state.PlantStateRepository
import com.example.myplants.data.state.PlantStateRepositoryInterface
import com.example.myplants.data.plant_with_photos.PlantWithPhotosRepository
import com.example.myplants.data.plant_with_photos.PlantWithPhotosRepositoryInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun providePlantRepository(
        plantDao: PlantDao
    ): PlantRepositoryInterface =
        PlantRepository (plantDao = plantDao)

    @Provides
    @Singleton
    fun providePhotoRepository(
        photoDao: PlantPhotoDao
    ): PhotoRepositoryInterface =
        PhotoRepository (photoDao = photoDao)

    @Provides
    @Singleton
    fun provideGenusRepository(
        genusDao: GenusDao
    ): GenusRepositoryInterface =
        GenusRepository (genusDao = genusDao)

    @Provides
    @Singleton
    fun providePlantStateRepository(
        plantStateDao: PlantStateDao
    ): PlantStateRepositoryInterface =
        PlantStateRepository (plantStateDao = plantStateDao)

    @Provides
    @Singleton
    fun providePlantWithPhotosRepository(
        plantWithPhotosDao: PlantWithPhotosDao
    ): PlantWithPhotosRepositoryInterface =
        PlantWithPhotosRepository(plantWithPhotosDao)

    @Provides
    @Singleton
    fun provideBackupRepository(
        plantRepository: PlantRepositoryInterface,
        photoRepository: PhotoRepositoryInterface,
        genusRepository: GenusRepositoryInterface,
        @ApplicationContext context: Context
    ): BackupRepositoryInterface = BackupRepository(
        plantRepository,
        photoRepository,
        genusRepository,
        context
    )

    @Provides
    @Singleton
    fun provideMainFacade(
        plantRepository: PlantRepositoryInterface,
        plantWithPhotosRepository: PlantWithPhotosRepositoryInterface,
        plantStateRepository: PlantStateRepositoryInterface,
        genusRepository: GenusRepositoryInterface
    ): MainFacadeInterface = MainFacade(
        plantRepository = plantRepository as PlantRepository,
        plantWithPhotosRepository = plantWithPhotosRepository,
        plantStateRepository = plantStateRepository,
        genusRepository = genusRepository as GenusRepository
    )
}
