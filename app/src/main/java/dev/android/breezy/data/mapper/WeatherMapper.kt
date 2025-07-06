package dev.android.breezy.data.mapper

import dev.android.breezy.data.remote.dto.CityDto
import dev.android.breezy.data.remote.dto.CurrentWeatherDto
import dev.android.breezy.data.remote.dto.ForecastDayDto
import dev.android.breezy.data.remote.dto.HourDto
import dev.android.breezy.data.remote.dto.LocationDto
import dev.android.breezy.data.remote.dto.WeatherResponse
import dev.android.breezy.domain.models.City
import dev.android.breezy.domain.models.CurrentWeather
import dev.android.breezy.domain.models.DayForecast
import dev.android.breezy.domain.models.HourForecast
import dev.android.breezy.domain.models.Location
import dev.android.breezy.domain.models.WeatherData

fun LocationDto.toDomain(): Location = Location(
    latitude = latitude,
    longitude = longitude,
    name = name,
    country = country,
    region = region
)

fun CurrentWeatherDto.toDomain(): CurrentWeather = CurrentWeather(
    temperature = temperatureCelsius,
    condition = condition.text,
    conditionIcon = "https:${condition.icon}",
    feelsLike = feelsLikeCelsius,
    humidity = humidity,
    windSpeed = windSpeedKph,
    windDirection = windDirection,
    pressure = pressureMb,
    uvIndex = uvIndex,
    visibility = visibilityKm
)

fun ForecastDayDto.toDomain(): DayForecast = DayForecast(
    date = date,
    maxTemp = day.maxTempCelsius,
    minTemp = day.minTempCelsius,
    condition = day.condition.text,
    conditionIcon = "https:${day.condition.icon}",
    chanceOfRain = day.chanceOfRain,
    hours = hours.map { it.toDomain() }
)

fun HourDto.toDomain(): HourForecast = HourForecast(
    time = time,
    temperature = temperatureCelsius,
    condition = condition.text,
    conditionIcon = "https:${condition.icon}",
    chanceOfRain = chanceOfRain
)

fun WeatherResponse.toDomain(): WeatherData = WeatherData(
    location = location.toDomain(),
    current = current.toDomain(),
    forecast = forecast?.forecastDays?.map { it.toDomain() } ?: emptyList()
)

fun CityDto.toDomain(): City = City(
    id = id,
    name = name,
    region = region,
    country = country,
    latitude = latitude,
    longitude = longitude
)