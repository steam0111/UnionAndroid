package com.itrocket.union.authMain.domain.dependencies

import com.itrocket.token_auth.AuthCredentials
import com.itrocket.union.authMain.domain.entity.AuthCredsDomain
import com.itrocket.union.authMain.domain.entity.AuthDomain
import kotlinx.coroutines.flow.Flow
import org.openapitools.client.models.AuthJwtResponse
import retrofit2.Response

interface AuthMainRepository {

    suspend fun signIn(authCreds: AuthCredsDomain): AuthDomain

    suspend fun refreshToken(refreshToken: String): AuthCredentials

    suspend fun saveAuthCredentials(credentials: AuthDomain)

    fun subscribeAccessToken(): Flow<String>

    fun subscribeRefreshToken(): Flow<String>

    suspend fun clearAuthCredentials()
}