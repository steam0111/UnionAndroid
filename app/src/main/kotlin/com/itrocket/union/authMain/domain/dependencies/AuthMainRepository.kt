package com.itrocket.union.authMain.domain.dependencies

import com.itrocket.union.authMain.domain.entity.AuthCredsDomain
import com.itrocket.union.authMain.domain.entity.AuthDomain
import org.openapitools.client.models.AuthJwtResponse
import retrofit2.Response

interface AuthMainRepository {

    suspend fun signIn(authCreds: AuthCredsDomain): AuthDomain

    suspend fun saveAuthCredentials(credentials: AuthDomain)

    suspend fun getAccessToken(): String
}