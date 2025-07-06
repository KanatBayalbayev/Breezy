package dev.android.breezy.data.repositories

import dev.android.breezy.BuildConfig
import dev.android.breezy.data.mapper.toDomain
import dev.android.breezy.data.remote.api.WeatherApiService
import dev.android.breezy.domain.models.City
import dev.android.breezy.domain.models.Location
import dev.android.breezy.domain.models.WeatherData
import dev.android.breezy.domain.repositories.WeatherRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherRepositoryImpl @Inject constructor(
    private val weatherApiService: WeatherApiService
) : WeatherRepository {

    override suspend fun getCurrentWeather(location: Location): Result<WeatherData> {
        return try {
            val locationQuery = "${location.latitude},${location.longitude}"
            val response = weatherApiService.getCurrentWeather(
                apiKey = BuildConfig.WEATHER_API_KEY,
                location = locationQuery
            )

            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!.toDomain())
            } else {
                Result.failure(Exception("Failed to fetch weather data: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getForecast(location: Location, days: Int): Result<WeatherData> {
        return try {
            val locationQuery = "${location.latitude},${location.longitude}"
            val response = weatherApiService.getForecast(
                apiKey = BuildConfig.WEATHER_API_KEY,
                location = locationQuery,
                days = days
            )

            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!.toDomain())
            } else {
                Result.failure(Exception("Failed to fetch forecast data: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun searchCities(query: String): Result<List<City>> {
        return try {
            val response = weatherApiService.searchCities(
                apiKey = BuildConfig.WEATHER_API_KEY,
                query = query
            )

            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!.map { it.toDomain() })
            } else {
                Result.failure(Exception("Failed to search cities: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}