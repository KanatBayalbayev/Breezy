package dev.android.breezy.domain.models

data class WeatherData(
    val location: Location,
    val current: CurrentWeather,
    val forecast: List<DayForecast>
)

data class CurrentWeather(
    val temperature: Double,
    val condition: String,
    val conditionIcon: String,
    val feelsLike: Double,
    val humidity: Int,
    val windSpeed: Double,
    val windDirection: String,
    val pressure: Double,
    val uvIndex: Int,
    val visibility: Double
)

data class DayForecast(
    val date: String,
    val maxTemp: Double,
    val minTemp: Double,
    val condition: String,
    val conditionIcon: String,
    val chanceOfRain: Int,
    val hours: List<HourForecast>
)

data class HourForecast(
    val time: String,
    val temperature: Double,
    val condition: String,
    val conditionIcon: String,
    val chanceOfRain: Int
)