package com.itrocket.union.authMain.domain

import com.itrocket.token_auth.AuthCredentials
import com.itrocket.token_auth.TokenManager
import com.itrocket.union.authMain.data.mapper.toAuthDomain
import com.itrocket.union.authMain.domain.dependencies.AuthMainRepository

class TokenManagerImpl(
    private val authMainRepository: AuthMainRepository
) : TokenManager {
    override suspend fun clearAuthCredentials() {
        authMainRepository.clearAuthCredentials()
    }

    override suspend fun refreshToken(refreshToken: String): AuthCredentials {
        return authMainRepository.refreshToken(refreshToken, authMainRepository.getAccessTokenOrEmpty())
    }

    override suspend fun getAccessToken(): String {
        return authMainRepository.getAccessTokenOrEmpty()
    }

    override suspend fun getRefreshToken(): String {
        return authMainRepository.getRefreshTokenOrEmpty()
    }

    override suspend fun saveAuthCredentials(authCredentials: AuthCredentials) {
        authMainRepository.saveAuthCredentials(authCredentials.toAuthDomain())
    }
}