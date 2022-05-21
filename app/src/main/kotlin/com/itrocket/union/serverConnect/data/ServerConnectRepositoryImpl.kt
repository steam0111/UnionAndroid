package com.itrocket.union.serverConnect.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.itrocket.union.serverConnect.domain.dependencies.ServerConnectRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ServerConnectRepositoryImpl(
    private val baseUrlPreferencesKey: Preferences.Key<String>,
    private val portPreferencesKey: Preferences.Key<String>,
    private val dataStore: DataStore<Preferences>
) : ServerConnectRepository {

    override suspend fun saveBaseUrl(baseUrl: String) {
        dataStore.edit { preferences ->
            preferences[baseUrlPreferencesKey] = baseUrl
        }
    }

    override suspend fun savePort(port: String) {
        dataStore.edit { preferences ->
            preferences[portPreferencesKey] = port
        }
    }

    override fun getBaseUrl(): Flow<String> {
        return dataStore.data.map {
            it[baseUrlPreferencesKey].orEmpty()
        }
    }

    override fun getPort(): Flow<String> {
        return dataStore.data.map {
            it[portPreferencesKey].orEmpty()
        }
    }
}