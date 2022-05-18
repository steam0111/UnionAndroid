package com.itrocket.union.serverConnect.domain.dependencies


interface ServerConnectRepository {
    suspend fun saveBaseUrl(baseUrl: String)

    suspend fun savePort(port: String)
}