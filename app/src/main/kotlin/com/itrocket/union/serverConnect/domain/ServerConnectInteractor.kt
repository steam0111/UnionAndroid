package com.itrocket.union.serverConnect.domain

import androidx.core.text.isDigitsOnly
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.serverConnect.domain.dependencies.ServerConnectRepository
import com.itrocket.union.serverConnect.domain.entity.ColorDomain
import java.io.File
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.withContext
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull

class ServerConnectInteractor(
    private val repository: ServerConnectRepository,
    private val coreDispatchers: CoreDispatchers
) {
    suspend fun saveBaseUrl(baseUrl: String) {
        repository.saveBaseUrl(baseUrl)
    }

    suspend fun savePort(port: String) {
        repository.savePort(port)
    }

    suspend fun getBaseUrl(): String? {
        return repository.getBaseUrl().firstOrNull()
    }

    suspend fun getPort(): String? {
        return repository.getPort().firstOrNull()
    }

    fun validatePort(port: String): Boolean {
        return port.isDigitsOnly() && port.length <= MAX_PORT_LENGTH && port.isNotBlank()
    }

    fun validateServerAddress(serverAddress: String): Boolean {
        return serverAddress.toHttpUrlOrNull() != null
    }

    suspend fun clearAllSyncDataIfNeeded(newServerAddress: String, newPort: String) =
        withContext(coreDispatchers.io) {
            repository.clearAllSyncDataIfNeeded(newServerAddress, newPort)
        }

    companion object {
        const val MAX_PORT_LENGTH = 5
    }
}