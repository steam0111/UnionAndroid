package com.itrocket.union.authMain.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.itrocket.token_auth.AuthCredentials
import com.itrocket.union.authMain.data.mapper.toAuthCredentials
import com.itrocket.union.authMain.data.mapper.toAuthDomain
import com.itrocket.union.authMain.data.mapper.toAuthJwtRequestV2
import com.itrocket.union.authMain.data.mapper.toMyConfigDomain
import com.itrocket.union.authMain.domain.dependencies.AuthMainRepository
import com.itrocket.union.authMain.domain.entity.AuthCredsDomain
import com.itrocket.union.authMain.domain.entity.AuthDomain
import com.itrocket.union.authMain.domain.entity.MyConfigDomain
import com.itrocket.union.authMain.domain.entity.MyConfigPermission
import com.itrocket.union.network.InvalidNetworkDataException
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.openapitools.client.apis.ExtractMyUserInformationControllerApi
import org.openapitools.client.apis.JwtAuthControllerApi
import org.openapitools.client.custom_api.AuthApi
import org.openapitools.client.models.RefreshJwtRequest
import java.lang.reflect.Type

class AuthMainRepositoryImpl(
    private val api: AuthApi,
    private val jwtAuthControllerApi: JwtAuthControllerApi,
    private val dataStore: DataStore<Preferences>,
    private val accessTokenPreferencesKey: Preferences.Key<String>,
    private val refreshTokenPreferencesKey: Preferences.Key<String>,
    private val loginPreferencesKey: Preferences.Key<String>,
    private val myEmployeePreferencesKey: Preferences.Key<String>,
    private val myPermissionsPreferencesKey: Preferences.Key<String>,
    private val moshi: Moshi
) : AuthMainRepository, KoinComponent {

    private val myUserInformationControllerApi: ExtractMyUserInformationControllerApi by inject()

    override suspend fun signIn(
        authCreds: AuthCredsDomain,
        isActiveDirectory: Boolean
    ): AuthDomain {
        return if (isActiveDirectory) {
            jwtAuthControllerApi.apiAuthSignInFromActiveDirectoryPost(authCreds.toAuthJwtRequestV2())
                .body()
        } else {
            jwtAuthControllerApi.apiAuthSignInPost(authCreds.toAuthJwtRequestV2()).body()
        }?.toAuthDomain() ?: throw InvalidNetworkDataException()
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
        return myUserInformationControllerApi.apiSecurityPermissionsMyGet().body()
            .toMyConfigDomain()
    }

    override suspend fun saveMyConfig(config: MyConfigDomain?) {
        dataStore.edit { preferences ->
            config?.employeeId?.let {
                preferences[myEmployeePreferencesKey] = it
            }
            config?.permissions?.let {
                val type: Type =
                    Types.newParameterizedType(List::class.java, MyConfigPermission::class.java)
                val jsonAdapter: JsonAdapter<List<MyConfigPermission>> = moshi.adapter(type)

                val permissionsString = jsonAdapter.toJson(it)
                preferences[myPermissionsPreferencesKey] = permissionsString
            }
        }
    }

    override suspend fun getMyPreferencesConfig(): MyConfigDomain {
        val employeeId = dataStore.data.map { it[myEmployeePreferencesKey] }.firstOrNull()
        val permissionsString = dataStore.data.map { it[myPermissionsPreferencesKey] }.firstOrNull()

        val type: Type =
            Types.newParameterizedType(List::class.java, MyConfigPermission::class.java)
        val jsonAdapter: JsonAdapter<List<MyConfigPermission>> = moshi.adapter(type)

        val permissions = if (permissionsString != null) {
            jsonAdapter.fromJson(permissionsString)
        } else {
            null
        }

        return MyConfigDomain(
            employeeId = employeeId,
            permissions = permissions
        )
    }

    companion object {
        private const val BEARER_TOKEN = "Bearer"
    }
}