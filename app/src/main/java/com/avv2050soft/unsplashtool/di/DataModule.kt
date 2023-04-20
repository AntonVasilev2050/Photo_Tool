package com.avv2050soft.unsplashtool.di

import android.content.Context
import com.avv2050soft.unsplashtool.data.repository.SharedPreferencesRepositoryImpl
import com.avv2050soft.unsplashtool.data.repository.UnsplashRepositoryImpl
import com.avv2050soft.unsplashtool.domain.repository.SharedPreferencesRepository
import com.avv2050soft.unsplashtool.domain.repository.UnsplashRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun provideSharedPreferencesRepository(@ApplicationContext context: Context): SharedPreferencesRepository {
        return SharedPreferencesRepositoryImpl(context = context)
    }

    @Provides
    @Singleton
    fun provideUnsplashRepository() : UnsplashRepository {
        return UnsplashRepositoryImpl()
    }
}