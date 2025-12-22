package com.greencore.plantify.core.data.di

import android.content.Context
import com.greencore.plantify.core.data.local.db.dao.GenusDao
import com.greencore.plantify.core.data.local.db.dao.GenusWithPlantsDao
import com.greencore.plantify.core.data.local.db.dao.PlantDao
import com.greencore.plantify.core.data.local.db.dao.PlantPhotoDao
import com.greencore.plantify.core.data.local.db.dao.PlantStateDao
import com.greencore.plantify.core.data.local.db.dao.PlantWithPhotosDao
import com.greencore.plantify.data.main_facade.MainFacade
import com.greencore.plantify.data.main_facade.MainFacadeInterface
import com.greencore.plantify.data.backup.BackupRepository
import com.greencore.plantify.data.backup.BackupRepositoryInterface
import com.greencore.plantify.data.genus.GenusRepository
import com.greencore.plantify.data.genus.GenusRepositoryInterface
import com.greencore.plantify.data.genus_with_plants.GenusWithPlantsRepository
import com.greencore.plantify.data.genus_with_plants.GenusWithPlantsRepositoryInterface
import com.greencore.plantify.data.photo.PhotoRepository
import com.greencore.plantify.data.photo.PhotoRepositoryInterface
import com.greencore.plantify.data.plant.PlantRepository
import com.greencore.plantify.data.plant.PlantRepositoryInterface
import com.greencore.plantify.data.state.PlantStateRepository
import com.greencore.plantify.data.state.PlantStateRepositoryInterface
import com.greencore.plantify.data.plant_with_photos.PlantWithPhotosRepository
import com.greencore.plantify.data.plant_with_photos.PlantWithPhotosRepositoryInterface
import com.greencore.plantify.ui.componets.topbar.TopBarStateViewModel
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
    fun provideTopBarStateViewModel():
            TopBarStateViewModel = TopBarStateViewModel()

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
    fun provideGenusWithPlantsRepository(
        genusWithPlantsDao: GenusWithPlantsDao
    ): GenusWithPlantsRepositoryInterface =
        GenusWithPlantsRepository(genusWithPlantsDao)

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
        photoRepository: PhotoRepositoryInterface,
        plantWithPhotosRepository: PlantWithPhotosRepositoryInterface,
        plantStateRepository: PlantStateRepositoryInterface,
        genusRepository: GenusRepositoryInterface
    ): MainFacadeInterface = MainFacade(
        plantRepository = plantRepository as PlantRepository,
        photoRepository = photoRepository as PhotoRepository,
        plantWithPhotosRepository = plantWithPhotosRepository,
        plantStateRepository = plantStateRepository,
        genusRepository = genusRepository as GenusRepository
    )
}
