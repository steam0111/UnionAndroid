package com.itrocket.union.serverConnect.domain.dependencies

import com.itrocket.union.serverConnect.domain.entity.ColorDomain
import java.io.File
import kotlinx.coroutines.flow.Flow


interface ServerConnectRepository {
    suspend fun saveBaseUrl(baseUrl: String)

    suspend fun savePort(port: String)

    fun getBaseUrl(): Flow<String>

    fun getPort(): Flow<String>

    fun getServerAddress(): Flow<String>

    fun getReadyServerUrl(): String

    suspend fun clearAllSyncDataIfNeeded(newServerAddress: String, newPort: String)
}