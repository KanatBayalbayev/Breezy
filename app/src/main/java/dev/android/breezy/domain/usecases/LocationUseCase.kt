package dev.android.breezy.domain.usecases

import dev.android.breezy.domain.models.Location
import dev.android.breezy.domain.repositories.LocationRepository
import dev.android.breezy.domain.repositories.PreferencesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocationUseCase @Inject constructor(
    private val locationRepository: LocationRepository,
    private val preferencesRepository: PreferencesRepository
) {
    suspend fun getCurrentLocation(): Result<Location> {
        return locationRepository.getCurrentLocation()
    }

    suspend fun getLastKnownLocation(): Location? {
        return locationRepository.getLastKnownLocation()
    }

    suspend fun saveSelectedLocation(location: Location) {
        locationRepository.saveSelectedLocation(location)
    }

    fun getSelectedLocation(): Flow<Location?> {
        return locationRepository.getSelectedLocation()
    }

    suspend fun setLocationPermissionAsked(asked: Boolean) {
        preferencesRepository.setLocationPermissionAsked(asked)
    }

    fun isLocationPermissionAsked(): Flow<Boolean> {
        return preferencesRepository.isLocationPermissionAsked()
    }
}