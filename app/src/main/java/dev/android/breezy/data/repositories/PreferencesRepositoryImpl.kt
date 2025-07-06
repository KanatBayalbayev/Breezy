package dev.android.breezy.data.repositories

import dev.android.breezy.data.local.datastore.PreferencesDataStore
import dev.android.breezy.domain.repositories.PreferencesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PreferencesRepositoryImpl @Inject constructor(
    private val preferencesDataStore: PreferencesDataStore
) : PreferencesRepository {

    override suspend fun setOnboardingCompleted(completed: Boolean) {
        preferencesDataStore.setOnboardingCompleted(completed)
    }

    override fun isOnboardingCompleted(): Flow<Boolean> {
        return preferencesDataStore.isOnboardingCompleted()
    }

    override suspend fun setLocationPermissionAsked(asked: Boolean) {
        preferencesDataStore.setLocationPermissionAsked(asked)
    }

    override fun isLocationPermissionAsked(): Flow<Boolean> {
        return preferencesDataStore.isLocationPermissionAsked()
    }

    override suspend fun setTemperatureUnit(unit: String) {
        preferencesDataStore.setTemperatureUnit(unit)
    }

    override fun getTemperatureUnit(): Flow<String> {
        return preferencesDataStore.getTemperatureUnit()
    }
}