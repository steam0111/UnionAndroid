package com.itrocket.union.serverConnect.domain

import android.net.Uri
import androidx.core.text.isDigitsOnly
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.serverConnect.domain.dependencies.ServerConnectRepository
import com.itrocket.union.uniqueDeviceId.data.UniqueDeviceIdRepository
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.withContext
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull

class ServerConnectInteractor(
    private val repository: ServerConnectRepository,
    private val coreDispatchers: CoreDispatchers,
    private val fileInteractor: FileInteractor,
    private val deviceIdRepository: UniqueDeviceIdRepository
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

    suspend fun saveDeviceId(uri: Uri, deviceId: String): Boolean =
        withContext(coreDispatchers.io) {
            return@withContext fileInteractor.writeTextToFile(
                uri,
                deviceId
            ).apply {
                deviceIdRepository.saveDeviceId(deviceId)
            }
        }

    suspend fun readAndSaveDeviceId(uri: Uri): Boolean = withContext(coreDispatchers.io) {
        val text = fileInteractor.readTextFromFileAtTime(uri)
        if (text.isEmpty() || text.length != UUID_LENGTH) {
            return@withContext false
        }
        deviceIdRepository.saveDeviceId(text)
        return@withContext true
    }

    companion object {
        const val MAX_PORT_LENGTH = 5
        const val UUID_LENGTH = 36
    }
}