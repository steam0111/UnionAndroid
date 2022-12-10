package com.itrocket.union.serverConnect.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.example.union_sync_api.data.ManageSyncDataApi
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.serverConnect.domain.dependencies.ServerConnectRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent

class ServerConnectRepositoryImpl(
    private val baseUrlPreferencesKey: Preferences.Key<String>,
    private val portPreferencesKey: Preferences.Key<String>,
    private val dataStore: DataStore<Preferences>,
    private val coreDispatchers: CoreDispatchers,
    private val manageSyncDataApi: ManageSyncDataApi,
) : ServerConnectRepository, KoinComponent {

    private var baseUrl: String? = null
    private var port: String? = null

    init {
        updateServerInfo()
    }

    private fun updateServerInfo() {
        runBlocking {
            baseUrl = getBaseUrl().firstOrNull()
            port = getPort().firstOrNull()
        }
    }

    override suspend fun saveBaseUrl(baseUrl: String) {
        this.baseUrl = baseUrl
        dataStore.edit { preferences ->
            preferences[baseUrlPreferencesKey] = baseUrl
        }
    }

    override suspend fun savePort(port: String) {
        this.port = port
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
        }.distinctUntilChanged()
    }

    override fun getReadyServerUrl(): String {
        val serverAddress = if (baseUrl != null && port != null) {
            "$baseUrl:$port/"
        } else {
            null
        }

        return serverAddress ?: throw IllegalStateException(
            "Call getServerUrl() and wait for collect Url before call it"
        )
    }

    override suspend fun clearAllSyncDataIfNeeded(
        newServerAddress: String,
        newPort: String
    ) = withContext(coreDispatchers.io) {
        if (newServerAddress != getBaseUrl().firstOrNull() ||
            newPort != getPort().firstOrNull()
        ) {
            manageSyncDataApi.clearAll()
        }
    }
}