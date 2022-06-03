package com.itrocket.union.authMain.domain

import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.authMain.domain.dependencies.AuthMainRepository
import com.itrocket.union.authMain.domain.entity.AuthCredsDomain
import com.itrocket.union.network.NetworkInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.withContext

class AuthMainInteractor(
    private val repository: AuthMainRepository,
    private val coreDispatchers: CoreDispatchers,
    private val networkInfo: NetworkInfo
) {

    suspend fun signIn(login: String, password: String) {
        withContext(coreDispatchers.io) {
            val auth = repository.signIn(AuthCredsDomain(login = login, password = password))
            repository.saveAuthCredentials(auth)
        }
    }

    fun validatePassword(password: String): Boolean {
        return password.isNotBlank()
    }

    fun checkAccessTokenExpired(): Flow<String> {
        return subscribeAccessToken().drop(TOKEN_VALUE_DROP_COUNT)
    }

    fun subscribeAccessToken(): Flow<String> {
        return repository.subscribeAccessToken().distinctUntilChanged()
    }

    fun subscribeRefreshToken(): Flow<String> {
        return repository.subscribeRefreshToken().distinctUntilChanged()
    }

    suspend fun logout() {
        withContext(Dispatchers.IO) {
            val accessToken = networkInfo.accessToken
            repository.clearAuthCredentials()
            repository.invalidateToken(accessToken)
        }
    }

    companion object {
        // Первый элемент пропускаем, потому что это инитовое значение для показа auth/documentMenu
        private const val TOKEN_VALUE_DROP_COUNT = 1
    }
}