package com.itrocket.union.authMain.domain.dependencies

import com.itrocket.token_auth.AuthCredentials
import com.itrocket.union.authMain.domain.entity.AuthCredsDomain
import com.itrocket.union.authMain.domain.entity.AuthDomain
import com.itrocket.union.authMain.domain.entity.MyConfigDomain
import kotlinx.coroutines.flow.Flow
import org.openapitools.client.models.GetMyPermissionsResponseV2

interface AuthMainRepository {

    suspend fun signIn(authCreds: AuthCredsDomain): AuthDomain

    suspend fun refreshToken(refreshToken: String, accessToken: String): AuthCredentials

    suspend fun saveAuthCredentials(credentials: AuthDomain)

    suspend fun saveLogin(login: String)

    suspend fun getLogin(): Flow<String>

    fun subscribeRefreshToken(): Flow<String>

    suspend fun clearAuthCredentials()

    suspend fun invalidateToken(accessToken: String): Boolean

    suspend fun getAccessTokenOrEmpty(): String

    suspend fun getMyConfig(): MyConfigDomain?

    suspend fun saveMyConfig(config: MyConfigDomain?)

    suspend fun getMyPreferencesConfig(): MyConfigDomain
}