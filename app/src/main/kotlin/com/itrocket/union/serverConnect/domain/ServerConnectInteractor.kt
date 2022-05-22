package com.itrocket.union.serverConnect.domain

import androidx.core.text.isDigitsOnly
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.serverConnect.domain.dependencies.ServerConnectRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
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

    fun validatePort(port: String): Boolean {
        return port.isDigitsOnly() && port.length <= MAX_PORT_LENGTH && port.isNotBlank()
    }

    fun validateServerAddress(serverAddress: String): Boolean {
        return serverAddress.toHttpUrlOrNull() != null
    }

    fun getServerAddress(): Flow<String> {
        return repository.getBaseUrl().combine(repository.getPort()) { baseUrl, port ->
            if (baseUrl.isNotBlank()) {
                "$baseUrl:$port/"
            } else {
                ""
            }
        }.distinctUntilChanged()
    }

    companion object {
        const val MAX_PORT_LENGTH = 5
    }
}