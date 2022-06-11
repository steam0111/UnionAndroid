package com.itrocket.union.authMain.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.itrocket.token_auth.AuthCredentials
import com.itrocket.union.authMain.data.mapper.toAuthCredentials
import com.itrocket.union.authMain.data.mapper.toAuthDomain
import com.itrocket.union.authMain.data.mapper.toAuthJwtRequest
import com.itrocket.union.authMain.domain.dependencies.AuthMainRepository
import com.itrocket.union.authMain.domain.entity.AuthCredsDomain
import com.itrocket.union.authMain.domain.entity.AuthDomain
import com.itrocket.union.network.InvalidNetworkDataException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import org.openapitools.client.custom_auth.AuthApi
import org.openapitools.client.models.RefreshJwtRequest

class AuthMainRepositoryImpl(
    private val api: AuthApi,
    private val dataStore: DataStore<Preferences>,
    private val accessTokenPreferencesKey: Preferences.Key<String>,
    private val refreshTokenPreferencesKey: Preferences.Key<String>,
) : AuthMainRepository {
    override suspend fun signIn(authCreds: AuthCredsDomain): AuthDomain {
        return (api.apiAuthSignInPost(authCreds.toAuthJwtRequest()).body()
            ?: throw InvalidNetworkDataException()).toAuthDomain()
    }

    override suspend fun refreshToken(refreshToken: String, accessToken: String): AuthCredentials {
        return (api.apiAuthRefreshTokenPost(RefreshJwtRequest(refreshToken), "$BEARER_TOKEN $accessToken").body()
            ?: throw InvalidNetworkDataException()).toAuthCredentials()
    }

    override suspend fun saveAuthCredentials(credentials: AuthDomain) {
        dataStore.edit { preferences ->
            preferences[accessTokenPreferencesKey] = credentials.accessToken
            preferences[refreshTokenPreferencesKey] = credentials.refreshToken
        }
    }

    override fun subscribeRefreshToken(): Flow<String> {
        return dataStore.data.map {
            it[refreshTokenPreferencesKey].orEmpty()
        }
    }

    override suspend fun clearAuthCredentials() {
        dataStore.edit { preferences ->
            preferences[accessTokenPreferencesKey] = ""
            preferences[refreshTokenPreferencesKey] = ""
        }
    }

    override suspend fun invalidateToken(accessToken: String): Boolean {
        return api.apiAuthInvalidateTokenPost("$BEARER_TOKEN $accessToken").body()?.success ?: false
    }

    override suspend fun getAccessTokenOrEmpty(): String {
        return dataStore.data.map {
            it[refreshTokenPreferencesKey].orEmpty()
        }.firstOrNull().orEmpty()
    }

    companion object {
        private const val BEARER_TOKEN = "Bearer"
    }
}