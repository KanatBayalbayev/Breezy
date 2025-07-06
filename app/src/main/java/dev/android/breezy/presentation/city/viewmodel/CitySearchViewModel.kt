package dev.android.breezy.presentation.city.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.android.breezy.domain.models.City
import dev.android.breezy.domain.models.Location
import dev.android.breezy.domain.usecases.LocationUseCase
import dev.android.breezy.domain.usecases.WeatherUseCase
import dev.android.breezy.presentation.city.model.CitySearchUiState
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CitySearchViewModel @Inject constructor(
    private val weatherUseCase: WeatherUseCase,
    private val locationUseCase: LocationUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(CitySearchUiState())
    val uiState: StateFlow<CitySearchUiState> = _uiState.asStateFlow()

    private var searchJob: Job? = null

    fun updateSearchQuery(query: String) {
        _uiState.value = _uiState.value.copy(searchQuery = query)

        // Cancel previous search
        searchJob?.cancel()

        if (query.length >= 3) {
            // Debounce search
            searchJob = viewModelScope.launch {
                delay(500) // Wait 500ms before searching
                searchCities(query)
            }
        } else {
            _uiState.value = _uiState.value.copy(
                cities = emptyList(),
                isSearching = false,
                error = null
            )
        }
    }

    private fun searchCities(query: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isSearching = true,
                error = null
            )

            try {
                val result = weatherUseCase.searchCities(query)
                result.fold(
                    onSuccess = { cities ->
                        _uiState.value = _uiState.value.copy(
                            cities = cities,
                            isSearching = false,
                            error = null
                        )
                    },
                    onFailure = { exception ->
                        _uiState.value = _uiState.value.copy(
                            cities = emptyList(),
                            isSearching = false,
                            error = exception.message ?: "Failed to search cities"
                        )
                    }
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    cities = emptyList(),
                    isSearching = false,
                    error = e.message ?: "Failed to search cities"
                )
            }
        }
    }

    fun selectCity(city: City, onComplete: () -> Unit) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                selectedCity = city
            )

            try {
                val location = Location(
                    latitude = city.latitude,
                    longitude = city.longitude,
                    name = city.name,
                    country = city.country,
                    region = city.region
                )

                locationUseCase.saveSelectedLocation(location)
                onComplete()
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message ?: "Failed to save location"
                )
            }
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}