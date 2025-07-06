package dev.android.breezy.domain.usecases

import dev.android.breezy.domain.models.City
import dev.android.breezy.domain.models.Location
import dev.android.breezy.domain.models.WeatherData
import dev.android.breezy.domain.repositories.WeatherRepository
import javax.inject.Inject

class WeatherUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository
) {
    suspend fun getCurrentWeather(location: Location): Result<WeatherData> {
        return weatherRepository.getCurrentWeather(location)
    }

    suspend fun getForecast(location: Location, days: Int = 7): Result<WeatherData> {
        return weatherRepository.getForecast(location, days)
    }

    suspend fun searchCities(query: String): Result<List<City>> {
        return weatherRepository.searchCities(query)
    }
}