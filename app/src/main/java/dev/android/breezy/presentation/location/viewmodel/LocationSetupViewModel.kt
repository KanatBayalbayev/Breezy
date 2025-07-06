package dev.android.breezy.presentation.location.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.android.breezy.domain.usecases.LocationUseCase
import dev.android.breezy.presentation.location.model.LocationSetupUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationSetupViewModel @Inject constructor(
    private val locationUseCase: LocationUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(LocationSetupUiState())
    val uiState: StateFlow<LocationSetupUiState> = _uiState.asStateFlow()

    fun getCurrentLocation() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                error = null
            )

            try {
                val result = locationUseCase.getCurrentLocation()
                result.fold(
                    onSuccess = { location ->
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            currentLocation = location,
                            locationObtained = true,
                            error = null
                        )
                        // Save the location
                        locationUseCase.saveSelectedLocation(location)
                    },
                    onFailure = { exception ->
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            error = exception.message ?: "Failed to get location",
                            locationObtained = false
                        )
                    }
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message ?: "Failed to get location",
                    locationObtained = false
                )
            }
        }
    }

    fun onPermissionResult(granted: Boolean) {
        viewModelScope.launch {
            locationUseCase.setLocationPermissionAsked(true)
            _uiState.value = _uiState.value.copy(permissionRequested = true)

            if (granted) {
                getCurrentLocation()
            } else {
                _uiState.value = _uiState.value.copy(
                    error = "Location permission is required for accurate weather forecasts"
                )
            }
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }

    fun getLastKnownLocation() {
        viewModelScope.launch {
            try {
                val lastLocation = locationUseCase.getLastKnownLocation()
                if (lastLocation != null) {
                    _uiState.value = _uiState.value.copy(
                        currentLocation = lastLocation,
                        locationObtained = true
                    )
                    locationUseCase.saveSelectedLocation(lastLocation)
                }
            } catch (e: Exception) {
                // Ignore errors for last known location
            }
        }
    }
}