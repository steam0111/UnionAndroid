package com.itrocket.token_auth

interface TokenManager {

    suspend fun clearAuthCredentials()

    suspend fun refreshToken(refreshToken: String): AuthCredentials

    fun getAccessToken(): String

    fun getRefreshToken(): String

    suspend fun saveAuthCredentials(authCredentials: AuthCredentials)
}