package dev.android.breezy.domain.repositories

import kotlinx.coroutines.flow.Flow

interface PreferencesRepository {

    suspend fun setOnboardingCompleted(completed: Boolean)
    fun isOnboardingCompleted(): Flow<Boolean>
    suspend fun setLocationPermissionAsked(asked: Boolean)
    fun isLocationPermissionAsked(): Flow<Boolean>
    suspend fun setTemperatureUnit(unit: String)
    fun getTemperatureUnit(): Flow<String>
}