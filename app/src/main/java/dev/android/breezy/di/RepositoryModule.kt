package dev.android.breezy.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.android.breezy.data.repositories.LocationRepositoryImpl
import dev.android.breezy.data.repositories.PreferencesRepositoryImpl
import dev.android.breezy.data.repositories.WeatherRepositoryImpl
import dev.android.breezy.domain.repositories.LocationRepository
import dev.android.breezy.domain.repositories.PreferencesRepository
import dev.android.breezy.domain.repositories.WeatherRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindWeatherRepository(
        weatherRepositoryImpl: WeatherRepositoryImpl
    ): WeatherRepository

    @Binds
    @Singleton
    abstract fun bindLocationRepository(
        locationRepositoryImpl: LocationRepositoryImpl
    ): LocationRepository

    @Binds
    @Singleton
    abstract fun bindPreferencesRepository(
        preferencesRepositoryImpl: PreferencesRepositoryImpl
    ): PreferencesRepository
}