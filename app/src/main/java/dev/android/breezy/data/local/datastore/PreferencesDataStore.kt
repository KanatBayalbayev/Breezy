package dev.android.breezy.data.local.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton


val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "breezy_preferences")

@Singleton
class PreferencesDataStore @Inject constructor(
    private val context: Context
) {
    private val dataStore = context.dataStore

    suspend fun setOnboardingCompleted(completed: Boolean) {
        dataStore.edit { preferences ->
            preferences[ONBOARDING_COMPLETED] = completed
        }
    }

    fun isOnboardingCompleted(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[ONBOARDING_COMPLETED] ?: false
        }
    }

    suspend fun setLocationPermissionAsked(asked: Boolean) {
        dataStore.edit { preferences ->
            preferences[LOCATION_PERMISSION_ASKED] = asked
        }
    }

    fun isLocationPermissionAsked(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[LOCATION_PERMISSION_ASKED] ?: false
        }
    }

    suspend fun setTemperatureUnit(unit: String) {
        dataStore.edit { preferences ->
            preferences[TEMPERATURE_UNIT] = unit
        }
    }

    fun getTemperatureUnit(): Flow<String> {
        return dataStore.data.map { preferences ->
            preferences[TEMPERATURE_UNIT] ?: "celsius"
        }
    }

    suspend fun saveSelectedLocation(
        latitude: Double,
        longitude: Double,
        name: String,
        country: String,
        region: String
    ) {
        dataStore.edit { preferences ->
            preferences[SELECTED_LOCATION_LAT] = latitude.toString()
            preferences[SELECTED_LOCATION_LON] = longitude.toString()
            preferences[SELECTED_LOCATION_NAME] = name
            preferences[SELECTED_LOCATION_COUNTRY] = country
            preferences[SELECTED_LOCATION_REGION] = region
        }
    }

    fun getSelectedLocation(): Flow<Triple<Double, Double, String>?> {
        return dataStore.data.map { preferences ->
            val lat = preferences[SELECTED_LOCATION_LAT]?.toDoubleOrNull()
            val lon = preferences[SELECTED_LOCATION_LON]?.toDoubleOrNull()
            val name = preferences[SELECTED_LOCATION_NAME]

            if (lat != null && lon != null && name != null) {
                Triple(lat, lon, name)
            } else {
                null
            }
        }
    }

    companion object {
        val ONBOARDING_COMPLETED = booleanPreferencesKey("onboarding_completed")
        val LOCATION_PERMISSION_ASKED = booleanPreferencesKey("location_permission_asked")
        val TEMPERATURE_UNIT = stringPreferencesKey("temperature_unit")
        val SELECTED_LOCATION_LAT = stringPreferencesKey("selected_location_lat")
        val SELECTED_LOCATION_LON = stringPreferencesKey("selected_location_lon")
        val SELECTED_LOCATION_NAME = stringPreferencesKey("selected_location_name")
        val SELECTED_LOCATION_COUNTRY = stringPreferencesKey("selected_location_country")
        val SELECTED_LOCATION_REGION = stringPreferencesKey("selected_location_region")
    }
}