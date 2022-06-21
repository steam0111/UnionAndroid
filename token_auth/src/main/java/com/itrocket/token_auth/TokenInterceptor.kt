package com.itrocket.token_auth

import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

class TokenInterceptor(private val tokenManager: TokenManager) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response = runBlocking {
        val requestBuilder = chain.request().newBuilder()
        if (tokenManager.getAccessToken().isNotBlank()) {
            requestBuilder.addAuthHeader(tokenManager.getAccessToken())
        }
        chain.proceed(requestBuilder.build())
    }
}