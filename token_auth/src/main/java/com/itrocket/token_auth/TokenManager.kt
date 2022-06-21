package com.itrocket.token_auth

interface TokenManager {

    suspend fun clearAuthCredentials()

    suspend fun refreshToken(refreshToken: String): AuthCredentials

    suspend fun getAccessToken(): String

    suspend fun getRefreshToken(): String

    suspend fun saveAuthCredentials(authCredentials: AuthCredentials)
}