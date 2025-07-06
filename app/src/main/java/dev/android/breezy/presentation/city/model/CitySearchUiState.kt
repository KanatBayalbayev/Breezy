package dev.android.breezy.presentation.city.model

import dev.android.breezy.domain.models.City

data class CitySearchUiState(
    val searchQuery: String = "",
    val cities: List<City> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val isSearching: Boolean = false,
    val selectedCity: City? = null
)
