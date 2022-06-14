package com.itrocket.union.network

import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody

class ErrorHandlerInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)

        val responseBodyString = response.body?.string().toString()
        val networkException = NetworkException.parseFromString(responseBodyString)
        if (networkException != null && !networkException.code.isNullOrBlank()) {
            throw networkException
        }

        val httpException = HttpException.parseFromString(responseBodyString)
        if (httpException != null && !httpException.error.isNullOrBlank()) {
            throw httpException
        }
        val body = responseBodyString.toResponseBody(response.body?.contentType())
        return response.newBuilder().body(body).build()
    }
}