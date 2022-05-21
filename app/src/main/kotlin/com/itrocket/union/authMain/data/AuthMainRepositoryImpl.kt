package com.itrocket.union.authMain.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.itrocket.union.authMain.data.mapper.toAuthDomain
import com.itrocket.union.authMain.data.mapper.toAuthJwtRequest
import com.itrocket.union.authMain.domain.dependencies.AuthMainRepository
import com.itrocket.union.authMain.domain.entity.AuthCredsDomain
import com.itrocket.union.authMain.domain.entity.AuthDomain
import com.itrocket.union.network.InvalidNetworkDataException
import kotlinx.coroutines.flow.firstOrNull
import org.openapitools.client.apis.JwtAuthControllerApi

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

    override suspend fun saveAuthCredentials(credentials: AuthDomain) {
        dataStore.edit { preferences ->
            preferences[accessTokenPreferencesKey] = credentials.accessToken
            preferences[refreshTokenPreferencesKey] = credentials.refreshToken
        }
    }

    override suspend fun getAccessToken(): String {
        val preferences = dataStore.data.firstOrNull()
        return preferences?.get(accessTokenPreferencesKey).orEmpty()
    }
}