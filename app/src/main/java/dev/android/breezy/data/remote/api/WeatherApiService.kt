package dev.android.breezy.data.remote.api

import dev.android.breezy.data.remote.dto.CityDto
import dev.android.breezy.data.remote.dto.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {

    @GET("current.json")
    suspend fun getCurrentWeather(
        @Query("key") apiKey: String,
        @Query("q") location: String,
        @Query("aqi") includeAqi: String = "no"
    ): Response<WeatherResponse>

    @GET("forecast.json")
    suspend fun getForecast(
        @Query("key") apiKey: String,
        @Query("q") location: String,
        @Query("days") days: Int,
        @Query("aqi") includeAqi: String = "no",
        @Query("alerts") includeAlerts: String = "no"
    ): Response<WeatherResponse>

    @GET("search.json")
    suspend fun searchCities(
        @Query("key") apiKey: String,
        @Query("q") query: String
    ): Response<List<CityDto>>
}