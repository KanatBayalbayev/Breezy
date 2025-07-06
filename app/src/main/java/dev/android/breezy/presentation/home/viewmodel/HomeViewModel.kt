package dev.android.breezy.presentation.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.android.breezy.domain.models.Location
import dev.android.breezy.domain.usecases.LocationUseCase
import dev.android.breezy.domain.usecases.WeatherUseCase
import dev.android.breezy.presentation.home.model.HomeUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val weatherUseCase: WeatherUseCase,
    private val locationUseCase: LocationUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadWeatherData()
    }

    fun loadWeatherData() {
        viewModelScope.launch {
            locationUseCase.getSelectedLocation().collect { location ->
                if (location != null) {
                    _uiState.value = _uiState.value.copy(
                        currentLocation = location,
                        isLoading = true,
                        error = null
                    )
                    fetchWeatherData(location)
                }
            }
        }
    }

    private suspend fun fetchWeatherData(location: Location) {
        try {
            val result = weatherUseCase.getForecast(location, 7)
            result.fold(
                onSuccess = { weatherData ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        weatherData = weatherData,
                        error = null,
                        isRefreshing = false
                    )
                },
                onFailure = { exception ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = exception.message ?: "Failed to load weather data",
                        isRefreshing = false
                    )
                }
            )
        } catch (e: Exception) {
            _uiState.value = _uiState.value.copy(
                isLoading = false,
                error = e.message ?: "Failed to load weather data",
                isRefreshing = false
            )
        }
    }

    fun refreshWeatherData() {
        val location = _uiState.value.currentLocation
        if (location != null) {
            viewModelScope.launch {
                _uiState.value = _uiState.value.copy(isRefreshing = true)
                fetchWeatherData(location)
            }
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}