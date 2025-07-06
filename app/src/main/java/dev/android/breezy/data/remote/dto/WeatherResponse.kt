package dev.android.breezy.data.remote.dto

import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    @SerializedName("location")
    val location: LocationDto,
    @SerializedName("current")
    val current: CurrentWeatherDto,
    @SerializedName("forecast")
    val forecast: ForecastDto?
)

data class LocationDto(
    @SerializedName("name")
    val name: String,
    @SerializedName("region")
    val region: String,
    @SerializedName("country")
    val country: String,
    @SerializedName("lat")
    val latitude: Double,
    @SerializedName("lon")
    val longitude: Double
)

data class CurrentWeatherDto(
    @SerializedName("temp_c")
    val temperatureCelsius: Double,
    @SerializedName("temp_f")
    val temperatureFahrenheit: Double,
    @SerializedName("condition")
    val condition: ConditionDto,
    @SerializedName("feelslike_c")
    val feelsLikeCelsius: Double,
    @SerializedName("feelslike_f")
    val feelsLikeFahrenheit: Double,
    @SerializedName("humidity")
    val humidity: Int,
    @SerializedName("wind_kph")
    val windSpeedKph: Double,
    @SerializedName("wind_mph")
    val windSpeedMph: Double,
    @SerializedName("wind_dir")
    val windDirection: String,
    @SerializedName("pressure_mb")
    val pressureMb: Double,
    @SerializedName("pressure_in")
    val pressureIn: Double,
    @SerializedName("uv")
    val uvIndex: Int,
    @SerializedName("vis_km")
    val visibilityKm: Double,
    @SerializedName("vis_miles")
    val visibilityMiles: Double
)

data class ConditionDto(
    @SerializedName("text")
    val text: String,
    @SerializedName("icon")
    val icon: String,
    @SerializedName("code")
    val code: Int
)

data class ForecastDto(
    @SerializedName("forecastday")
    val forecastDays: List<ForecastDayDto>
)

data class ForecastDayDto(
    @SerializedName("date")
    val date: String,
    @SerializedName("day")
    val day: DayDto,
    @SerializedName("hour")
    val hours: List<HourDto>
)

data class DayDto(
    @SerializedName("maxtemp_c")
    val maxTempCelsius: Double,
    @SerializedName("maxtemp_f")
    val maxTempFahrenheit: Double,
    @SerializedName("mintemp_c")
    val minTempCelsius: Double,
    @SerializedName("mintemp_f")
    val minTempFahrenheit: Double,
    @SerializedName("condition")
    val condition: ConditionDto,
    @SerializedName("daily_chance_of_rain")
    val chanceOfRain: Int
)

data class HourDto(
    @SerializedName("time")
    val time: String,
    @SerializedName("temp_c")
    val temperatureCelsius: Double,
    @SerializedName("temp_f")
    val temperatureFahrenheit: Double,
    @SerializedName("condition")
    val condition: ConditionDto,
    @SerializedName("chance_of_rain")
    val chanceOfRain: Int
)