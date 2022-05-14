package com.itrocket.union.network

import com.itrocket.union.authMain.data.AuthRequest
import com.itrocket.union.authMain.data.AuthResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("auth/sign-in")
    suspend fun signIn(@Body body: AuthRequest): AuthResponse?
}