package com.example.rickandmortywiki.ui.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmortywiki.data.UserPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserStateViewModel @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    val showOnboarding = userPreferencesRepository.onboardingState

    fun showOnboarding(show: Boolean) {
        viewModelScope.launch {
            userPreferencesRepository.updateShowOnboarding(show)
        }
    }
}
