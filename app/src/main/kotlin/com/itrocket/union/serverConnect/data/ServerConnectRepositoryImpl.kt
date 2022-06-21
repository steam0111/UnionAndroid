package com.itrocket.union.serverConnect.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.itrocket.union.serverConnect.domain.dependencies.ServerConnectRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

class ServerConnectRepositoryImpl(
    private val baseUrlPreferencesKey: Preferences.Key<String>,
    private val portPreferencesKey: Preferences.Key<String>,
    private val dataStore: DataStore<Preferences>
) : ServerConnectRepository {

    private val serverUrl = MutableSharedFlow<String>(replay = 1)

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

    override fun getServerAddress(): Flow<String> {
        return getBaseUrl().filter {
            it.isNotBlank()
        }.combine(
            getPort().filter {
                it.isNotBlank()
            }
        ) { baseUrl, port ->
            "$baseUrl:$port/"
        }.distinctUntilChanged().onEach {
            serverUrl.emit(it)
        }
    }

    override fun getReadyServerUrl(): String {
        return serverUrl.replayCache.firstOrNull() ?: throw IllegalStateException(
            "Call getServerUrl() and wait for collect Url before call it"
        )
    }
}