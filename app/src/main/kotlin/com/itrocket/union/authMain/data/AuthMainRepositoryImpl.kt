package com.itrocket.union.authMain.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.itrocket.token_auth.AuthCredentials
import com.itrocket.union.authMain.data.mapper.toAuthCredentials
import com.itrocket.union.authMain.data.mapper.toAuthDomain
import com.itrocket.union.authMain.data.mapper.toAuthJwtRequest
import com.itrocket.union.authMain.data.mapper.toMyConfigDomain
import com.itrocket.union.authMain.domain.dependencies.AuthMainRepository
import com.itrocket.union.authMain.domain.entity.AuthCredsDomain
import com.itrocket.union.authMain.domain.entity.AuthDomain
import com.itrocket.union.authMain.domain.entity.MyConfigDomain
import com.itrocket.union.network.InvalidNetworkDataException
import kotlin.math.log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.openapitools.client.apis.ExtractMyUserInformationControllerApi
import org.openapitools.client.custom_api.AuthApi
import org.openapitools.client.models.GetMyPermissionsResponseV2
import org.openapitools.client.models.RefreshJwtRequest

class AuthMainRepositoryImpl(
    private val api: AuthApi,
    private val dataStore: DataStore<Preferences>,
    private val accessTokenPreferencesKey: Preferences.Key<String>,
    private val refreshTokenPreferencesKey: Preferences.Key<String>,
    private val loginPreferencesKey: Preferences.Key<String>,
    private val myOrganizationPreferencesKey: Preferences.Key<String>,
    private val myEmployeePreferencesKey: Preferences.Key<String>,
) : AuthMainRepository, KoinComponent {

    private val myUserInformationControllerApi: ExtractMyUserInformationControllerApi by inject()

    override suspend fun signIn(authCreds: AuthCredsDomain): AuthDomain {
        return (api.apiAuthSignInPost(authCreds.toAuthJwtRequest()).body()
            ?: throw InvalidNetworkDataException()).toAuthDomain()
    }

    override suspend fun refreshToken(refreshToken: String, accessToken: String): AuthCredentials {
        return (api.apiAuthRefreshTokenPost(
            RefreshJwtRequest(refreshToken),
            "$BEARER_TOKEN $accessToken"
        ).body()
            ?: throw InvalidNetworkDataException()).toAuthCredentials()
    }

    override suspend fun saveAuthCredentials(credentials: AuthDomain) {
        dataStore.edit { preferences ->
            preferences[accessTokenPreferencesKey] = credentials.accessToken
            preferences[refreshTokenPreferencesKey] = credentials.refreshToken
        }
    }

    override suspend fun getLogin(): Flow<String> {
        return dataStore.data.map {
            it[loginPreferencesKey].orEmpty()
        }
    }

    override suspend fun saveLogin(login: String) {
        dataStore.edit { preferences ->
            preferences[loginPreferencesKey] = login
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

    override suspend fun getMyConfig(): MyConfigDomain {
        return myUserInformationControllerApi.apiSecurityPermissionsMyGet()
            .toMyConfigDomain()
    }

    override suspend fun saveMyConfig(config: MyConfigDomain?) {
        dataStore.edit { preferences ->
            preferences[myOrganizationPreferencesKey] = config?.organizationId.orEmpty()
            preferences[myEmployeePreferencesKey] = config?.employeeId.orEmpty()
        }
    }

    override suspend fun getMyPreferencesConfig(): MyConfigDomain {
        val organizationId = dataStore.data.map { it[myOrganizationPreferencesKey] }.firstOrNull()
        val employeeId = dataStore.data.map { it[myEmployeePreferencesKey] }.firstOrNull()
        return MyConfigDomain(
            organizationId = organizationId,
            employeeId = employeeId
        )
    }

    companion object {
        private const val BEARER_TOKEN = "Bearer"
    }
}