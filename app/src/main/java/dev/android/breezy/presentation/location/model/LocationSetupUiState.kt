package dev.android.breezy.presentation.location.model

import dev.android.breezy.domain.models.Location

data class LocationSetupUiState(
    val isLoading: Boolean = false,
    val currentLocation: Location? = null,
    val error: String? = null,
    val permissionRequested: Boolean = false,
    val locationObtained: Boolean = false
)
