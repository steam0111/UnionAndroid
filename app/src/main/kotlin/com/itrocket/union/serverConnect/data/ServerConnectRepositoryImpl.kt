package com.itrocket.union.serverConnect.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.itrocket.union.serverConnect.domain.dependencies.ServerConnectRepository

class ServerConnectRepositoryImpl(
    private val baseUrlPreferencesKey: Preferences.Key<String>,
    private val portPreferencesKey: Preferences.Key<String>,
    private val dataStore: DataStore<Preferences>
) : ServerConnectRepository {

    suspend fun saveBaseUrl(baseUrl: String) {
        dataStore.edit { preferences ->
            preferences[baseUrlPreferencesKey] = baseUrl
        }
    }

    suspend fun savePort(port: String) {
        dataStore.edit { preferences ->
            preferences[portPreferencesKey] = port
        }
    }
}