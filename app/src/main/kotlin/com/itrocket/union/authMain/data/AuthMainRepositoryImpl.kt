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
import kotlinx.coroutines.flow.map
import org.openapitools.client.apis.JwtAuthControllerApi
import org.openapitools.client.models.RefreshJwtRequest

class AuthMainRepositoryImpl(
    private val api: JwtAuthControllerApi,
    private val dataStore: DataStore<Preferences>,
    private val accessTokenPreferencesKey: Preferences.Key<String>,
    private val refreshTokenPreferencesKey: Preferences.Key<String>,
) : AuthMainRepository {
    override suspend fun signIn(authCreds: AuthCredsDomain): AuthDomain {
        return (api.apiAuthSignInPost(authCreds.toAuthJwtRequest()).body()
            ?: throw InvalidNetworkDataException()).toAuthDomain()
    }

    override suspend fun refreshToken(refreshToken: String): AuthCredentials {
        return (api.apiAuthRefreshTokenPost(RefreshJwtRequest(refreshToken)).body()
            ?: throw InvalidNetworkDataException()).toAuthCredentials()
    }

    override suspend fun saveAuthCredentials(credentials: AuthDomain) {
        dataStore.edit { preferences ->
            preferences[accessTokenPreferencesKey] = credentials.accessToken
            preferences[refreshTokenPreferencesKey] = credentials.refreshToken
        }
    }

    override fun subscribeAccessToken(): Flow<String> {
        return dataStore.data.map {
            it[accessTokenPreferencesKey].orEmpty()
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
}