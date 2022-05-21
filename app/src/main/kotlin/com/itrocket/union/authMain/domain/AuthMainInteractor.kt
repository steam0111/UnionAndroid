package com.itrocket.union.authMain.domain

import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.authMain.domain.dependencies.AuthMainRepository
import com.itrocket.union.authMain.domain.entity.AuthCredsDomain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AuthMainInteractor(
    private val repository: AuthMainRepository,
    private val coreDispatchers: CoreDispatchers
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

    suspend fun getAccessToken() = withContext(Dispatchers.IO) {
        repository.getAccessToken()
    }
}