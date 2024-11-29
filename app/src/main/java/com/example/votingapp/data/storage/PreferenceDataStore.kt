package com.example.votingapp.data.storage

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PreferenceDataStore @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {
    companion object {
        const val PREFS_NAME = "voting_app_prefs"
    }

    object PreferencesKeys {
        val PREF_LOGGED_IN = booleanPreferencesKey("pref_logged_in")
        val ACCESS_TOKEN = stringPreferencesKey("access_token")
    }


    suspend fun saveAccessToken(token: String) {
        dataStore.edit {
            it[PreferencesKeys.ACCESS_TOKEN] = token
        }
    }

    val accessToken: Flow<String?> =
        dataStore.data.map { it[PreferencesKeys.ACCESS_TOKEN] }
}