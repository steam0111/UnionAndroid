package com.itrocket.token_auth

import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

class Authenticator(private val tokenManager: TokenManager) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        return runBlocking {
            try {
                val refreshToken = tokenManager.getRefreshToken()
                val authCredentials = tokenManager.refreshToken(refreshToken)
                tokenManager.saveAuthCredentials(authCredentials)

                response.request().newBuilder()
                    .removeAuthHeader()
                    .addAuthHeader(authCredentials.accessToken)
                    .build()
            } catch (t: Throwable) {
                tokenManager.clearAuthCredentials()
                null
            }
        }
    }
}