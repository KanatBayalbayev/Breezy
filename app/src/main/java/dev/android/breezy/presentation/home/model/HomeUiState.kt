package dev.android.breezy.presentation.home.model

import dev.android.breezy.domain.models.Location
import dev.android.breezy.domain.models.WeatherData

data class HomeUiState(
    val isLoading: Boolean = false,
    val weatherData: WeatherData? = null,
    val currentLocation: Location? = null,
    val error: String? = null,
    val isRefreshing: Boolean = false
)
