package dev.android.breezy

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.android.breezy.domain.usecases.LocationUseCase
import dev.android.breezy.domain.usecases.OnboardingUseCase
import dev.android.breezy.presentation.navigation.Screen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val onboardingUseCase: OnboardingUseCase,
    private val locationUseCase: LocationUseCase
) : ViewModel() {

    private val _startDestination = MutableStateFlow<String?>(null)
    val startDestination: StateFlow<String?> = _startDestination.asStateFlow()

    init {
        determineStartDestination()
    }

    private fun determineStartDestination() {
        viewModelScope.launch {
            combine(
                onboardingUseCase.isOnboardingCompleted(),
                locationUseCase.getSelectedLocation()
            ) { isOnboardingCompleted, selectedLocation ->
                when {
                    !isOnboardingCompleted -> Screen.Onboarding.route
                    selectedLocation == null -> Screen.LocationSetup.route
                    else -> Screen.Home.route
                }
            }.collect { destination ->
                _startDestination.value = destination
            }
        }
    }
}