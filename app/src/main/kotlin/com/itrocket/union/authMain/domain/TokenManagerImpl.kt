package com.itrocket.union.authMain.domain

import com.itrocket.token_auth.AuthCredentials
import com.itrocket.token_auth.TokenManager
import com.itrocket.union.authMain.data.mapper.toAuthDomain
import com.itrocket.union.authMain.domain.dependencies.AuthMainRepository
import com.itrocket.union.network.NetworkInfo

class TokenManagerImpl(
    private val authMainRepository: AuthMainRepository,
    private val networkInfo: NetworkInfo
) : TokenManager {
    override suspend fun clearAuthCredentials() {
        authMainRepository.clearAuthCredentials()
    }

    override suspend fun refreshToken(refreshToken: String): AuthCredentials {
        return authMainRepository.refreshToken(refreshToken, networkInfo.accessToken)
    }

    override fun getAccessToken(): String {
        return networkInfo.accessToken
    }

    override fun getRefreshToken(): String {
        return networkInfo.refreshToken
    }

    override suspend fun saveAuthCredentials(authCredentials: AuthCredentials) {
        authMainRepository.saveAuthCredentials(authCredentials.toAuthDomain())
    }
}