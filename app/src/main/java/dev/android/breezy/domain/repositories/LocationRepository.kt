package dev.android.breezy.domain.repositories

import dev.android.breezy.domain.models.Location
import kotlinx.coroutines.flow.Flow

interface LocationRepository {

    suspend fun getCurrentLocation(): Result<Location>
    suspend fun getLastKnownLocation(): Location?
    suspend fun saveSelectedLocation(location: Location)
    fun getSelectedLocation(): Flow<Location?>
}