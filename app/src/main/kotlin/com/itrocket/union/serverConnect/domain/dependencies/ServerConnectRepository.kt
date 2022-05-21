package com.itrocket.union.serverConnect.domain.dependencies

import kotlinx.coroutines.flow.Flow


interface ServerConnectRepository {
    suspend fun saveBaseUrl(baseUrl: String)

    suspend fun savePort(port: String)

    fun getBaseUrl(): Flow<String>

    fun getPort(): Flow<String>
}