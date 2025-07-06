package dev.android.breezy.domain.repositories

import dev.android.breezy.domain.models.City
import dev.android.breezy.domain.models.Location
import dev.android.breezy.domain.models.WeatherData

interface WeatherRepository {

    suspend fun getCurrentWeather(location: Location): Result<WeatherData>
    suspend fun getForecast(location: Location, days: Int = 7): Result<WeatherData>
    suspend fun searchCities(query: String): Result<List<City>>
}