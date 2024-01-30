package com.example.rickandmortywiki.data

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

data class UserPreferences(
    val showOnboarding: Boolean,
)

class UserPreferencesRepository @Inject constructor(private val dataStore: DataStore<Preferences>) {

    private val tag: String = "UserPreferencesRepo"

    private object PreferencesKeys {
        val SHOW_ONBOARDING = booleanPreferencesKey("show_onboarding")
    }

    val userPreferencesFlow: Flow<UserPreferences> = dataStore.data
        .catch { exception ->
            // dataStore.data throws an IOException when an error is encountered when reading data
            if (exception is IOException) {
                Log.e(tag, "Error reading preferences.", exception)
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            mapUserPreferences(preferences)
        }

    suspend fun updateShowOnboarding(showOnboarding: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.SHOW_ONBOARDING] = showOnboarding
        }
    }

    suspend fun fetchInitialPreferences() =
        mapUserPreferences(dataStore.data.first().toPreferences())

    private fun mapUserPreferences(preferences: Preferences): UserPreferences {

        // Get our show completed value, defaulting to false if not set:
        val showOnboarding = preferences[PreferencesKeys.SHOW_ONBOARDING] ?: true
        return UserPreferences(showOnboarding)
    }
}
