package com.example.rickandmortywiki.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

data class UserPreferences(
    val showOnboarding: Boolean,
)

class UserPreferencesRepository @Inject constructor(private val dataStore: DataStore<Preferences>) {

    private object PreferencesKeys {
        val SHOW_ONBOARDING = booleanPreferencesKey("show_onboarding")
    }

    val onboardingState: Boolean get() = runBlocking {
        dataStore.data.map {
            it[PreferencesKeys.SHOW_ONBOARDING] ?: true
        }.first()
    }

    suspend fun updateShowOnboarding(showOnboarding: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.SHOW_ONBOARDING] = showOnboarding
        }
    }
}
