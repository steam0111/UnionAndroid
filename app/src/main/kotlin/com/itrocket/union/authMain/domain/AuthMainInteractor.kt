package com.itrocket.union.authMain.domain

import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.authMain.domain.dependencies.AuthMainRepository
import com.itrocket.union.authMain.domain.entity.AuthCredsDomain
import kotlin.math.log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.withContext

class AuthMainInteractor(
    private val repository: AuthMainRepository,
    private val coreDispatchers: CoreDispatchers
) {

    suspend fun signIn(login: String, password: String) {
        withContext(coreDispatchers.io) {
            val auth = repository.signIn(AuthCredsDomain(login = login, password = password))
            repository.saveAuthCredentials(auth)
            repository.saveLogin(login)
            val config = repository.getMyConfig()
            repository.saveMyConfig(config)
        }
    }

    suspend fun getLogin(): String? {
        return repository.getLogin().firstOrNull()
    }

    fun validatePassword(password: String): Boolean {
        return password.isNotBlank()
    }

    suspend fun logout() {
        withContext(coreDispatchers.io) {
            val accessToken = repository.getAccessTokenOrEmpty()
            repository.clearAuthCredentials()
            repository.invalidateToken(accessToken)
        }
    }

    suspend fun getMyConfig() = withContext(coreDispatchers.io) {
        repository.getMyPreferencesConfig()
    }
}