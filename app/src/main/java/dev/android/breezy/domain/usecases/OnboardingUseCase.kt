package dev.android.breezy.domain.usecases

import dev.android.breezy.domain.repositories.PreferencesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class OnboardingUseCase @Inject constructor(
    private val preferencesRepository: PreferencesRepository
) {
    suspend fun completeOnboarding() {
        preferencesRepository.setOnboardingCompleted(true)
    }

    fun isOnboardingCompleted(): Flow<Boolean> {
        return preferencesRepository.isOnboardingCompleted()
    }
}