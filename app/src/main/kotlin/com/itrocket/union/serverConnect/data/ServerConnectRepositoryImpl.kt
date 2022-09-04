package com.itrocket.union.serverConnect.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.example.union_sync_api.data.ManageSyncDataApi
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.serverConnect.data.mappers.toDomain
import com.itrocket.union.serverConnect.domain.dependencies.ServerConnectRepository
import com.itrocket.union.serverConnect.domain.entity.ColorDomain
import com.itrocket.union.theme.data.MediaRepositoryImpl
import java.io.File
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import okio.buffer
import okio.sink
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.openapitools.client.custom_api.StyleControllerApi

class ServerConnectRepositoryImpl(
    private val baseUrlPreferencesKey: Preferences.Key<String>,
    private val portPreferencesKey: Preferences.Key<String>,
    private val dataStore: DataStore<Preferences>,
    private val coreDispatchers: CoreDispatchers,
    private val manageSyncDataApi: ManageSyncDataApi,
    private val applicationContext: Context
) : ServerConnectRepository, KoinComponent {

    private val serverUrl = MutableSharedFlow<String>(replay = 1)

    private val styleApi by inject<StyleControllerApi>()

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

    override suspend fun getStyleSettings(): ColorDomain {
        return styleApi.getStyleSettings().toDomain()
    }

    override suspend fun getLogoFile(): File {
        val response = styleApi.getLogoFile()
        val fileName = MediaRepositoryImpl.LOGO_FILE_NAME
        return convertResponseToFile(response, fileName)
    }

    override suspend fun getHeaderFile(): File {
        val response = styleApi.getHeaderFile()
        val fileName = MediaRepositoryImpl.HEADER_FILE_NAME
        return convertResponseToFile(response, fileName)
    }

    private suspend fun convertResponseToFile(response: ResponseBody, fileName: String): File {
        val cacheDir = applicationContext.cacheDir
        val file = File(cacheDir, fileName)
        file.sink().buffer().use {
            it.writeAll(response.source())
        }
        return file
    }
}