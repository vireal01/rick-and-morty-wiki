package com.example.rickandmortywiki.ui.onboarding

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.rickandmortywiki.data.UserPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserStateViewModel @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    private var _showOnboarding = MutableStateFlow(true)
    val showOnboarding: StateFlow<Boolean> = _showOnboarding

    private val userPreferencesFlow = userPreferencesRepository.userPreferencesFlow

    init {
        viewModelScope.launch {
            _showOnboarding.value = userPreferencesFlow.first().showOnboarding
        }
    }

    suspend fun getShowOnboardingState(): Boolean = userPreferencesFlow.first().showOnboarding

    val initialSetupEvent = liveData {
        emit(userPreferencesRepository.fetchInitialPreferences())
    }

    fun showOnboarding(show: Boolean) {
        viewModelScope.launch {
            userPreferencesRepository.updateShowOnboarding(show)
        }
    }

    @SuppressLint("LogNotTimber")
    fun showUserPreferencesFlow() {
        viewModelScope.launch {
            Log.d("DataStore", userPreferencesFlow.first().showOnboarding.toString())
        }
    }
}
