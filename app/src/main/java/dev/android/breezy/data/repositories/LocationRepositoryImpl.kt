package dev.android.breezy.data.repositories

import android.annotation.SuppressLint
import android.content.Context
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Priority
import dev.android.breezy.data.local.datastore.PreferencesDataStore
import dev.android.breezy.domain.models.Location
import dev.android.breezy.domain.repositories.LocationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume

@Singleton
class LocationRepositoryImpl @Inject constructor(
    private val context: Context,
    private val fusedLocationClient: FusedLocationProviderClient,
    private val preferencesDataStore: PreferencesDataStore
) : LocationRepository {

    @SuppressLint("MissingPermission")
    override suspend fun getCurrentLocation(): Result<Location> {
        return try {
            val androidLocation = suspendCancellableCoroutine { continuation ->
                fusedLocationClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, null)
                    .addOnSuccessListener { location ->
                        continuation.resume(location)
                    }
                    .addOnFailureListener { exception ->
                        continuation.resume(null)
                    }
            }

            if (androidLocation != null) {
                val location = Location(
                    latitude = androidLocation.latitude,
                    longitude = androidLocation.longitude,
                    name = "Current Location",
                    country = "",
                    region = ""
                )
                Result.success(location)
            } else {
                Result.failure(Exception("Unable to get current location"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    @SuppressLint("MissingPermission")
    override suspend fun getLastKnownLocation(): Location? {
        return try {
            val androidLocation = suspendCancellableCoroutine { continuation ->
                fusedLocationClient.lastLocation
                    .addOnSuccessListener { location ->
                        continuation.resume(location)
                    }
                    .addOnFailureListener {
                        continuation.resume(null)
                    }
            }

            androidLocation?.let {
                Location(
                    latitude = it.latitude,
                    longitude = it.longitude,
                    name = "Last Known Location",
                    country = "",
                    region = ""
                )
            }
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun saveSelectedLocation(location: Location) {
        preferencesDataStore.saveSelectedLocation(
            latitude = location.latitude,
            longitude = location.longitude,
            name = location.name,
            country = location.country,
            region = location.region
        )
    }

    override fun getSelectedLocation(): Flow<Location?> {
        return preferencesDataStore.getSelectedLocation().map { locationData ->
            locationData?.let { (lat, lon, name) ->
                Location(
                    latitude = lat,
                    longitude = lon,
                    name = name,
                    country = "",
                    region = ""
                )
            }
        }
    }
}