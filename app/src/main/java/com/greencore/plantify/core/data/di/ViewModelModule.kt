package com.greencore.plantify.core.data.di

import com.greencore.plantify.ui.componets.topbar.TopBarStateViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ViewModelModule {

    @Provides
    @Singleton
    fun provideTopBarStateViewModel():
            TopBarStateViewModel = TopBarStateViewModel()
}