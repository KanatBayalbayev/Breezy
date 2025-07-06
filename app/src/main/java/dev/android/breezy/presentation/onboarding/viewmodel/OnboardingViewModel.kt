package dev.android.breezy.presentation.onboarding.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.android.breezy.domain.usecases.OnboardingUseCase
import dev.android.breezy.presentation.onboarding.model.OnboardingPage
import dev.android.breezy.presentation.onboarding.model.OnboardingUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val onboardingUseCase: OnboardingUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(OnboardingUiState())
    val uiState: StateFlow<OnboardingUiState> = _uiState.asStateFlow()

    val onboardingPages = listOf(
        OnboardingPage(
            title = "Welcome to Breezy",
            description = "Get accurate weather forecasts and stay prepared for any weather condition.",
            lottieAnimationRes = "weather_animation_1.json"
        ),
        OnboardingPage(
            title = "Location-Based Forecasts",
            description = "Receive precise weather information based on your current location or search for any city worldwide.",
            lottieAnimationRes = "weather_animation_2.json"
        ),
        OnboardingPage(
            title = "Stay Informed",
            description = "Get detailed forecasts, hourly updates, and weather alerts to plan your day perfectly.",
            lottieAnimationRes = "weather_animation_3.json"
        )
    )

    fun nextPage() {
        val currentPage = _uiState.value.currentPage
        if (currentPage < onboardingPages.size - 1) {
            _uiState.value = _uiState.value.copy(currentPage = currentPage + 1)
        }
    }

    fun previousPage() {
        val currentPage = _uiState.value.currentPage
        if (currentPage > 0) {
            _uiState.value = _uiState.value.copy(currentPage = currentPage - 1)
        }
    }

    fun goToPage(page: Int) {
        if (page in 0 until onboardingPages.size) {
            _uiState.value = _uiState.value.copy(currentPage = page)
        }
    }

    fun completeOnboarding(onComplete: () -> Unit) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                onboardingUseCase.completeOnboarding()
                onComplete()
            } catch (e: Exception) {
                // Handle error if needed
            } finally {
                _uiState.value = _uiState.value.copy(isLoading = false)
            }
        }
    }
}