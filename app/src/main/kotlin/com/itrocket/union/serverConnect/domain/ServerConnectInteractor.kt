package com.itrocket.union.serverConnect.domain

import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.serverConnect.domain.dependencies.ServerConnectRepository

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
}