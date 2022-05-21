package com.itrocket.union.network

import okhttp3.Interceptor
import okhttp3.Response

class ErrorHandlerInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)

        val responseBodyString = response.body?.string().toString()
        val networkException = NetworkException.parseFromString(responseBodyString)
        if (networkException != null && !networkException.code.isNullOrBlank()) {
            throw networkException
        }
        return chain.proceed(request)
    }
}