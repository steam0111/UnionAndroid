package com.itrocket.union.authSelectUser.domain

import kotlinx.coroutines.withContext
import com.itrocket.union.authSelectUser.domain.dependencies.AuthSelectUserRepository
import com.itrocket.core.base.CoreDispatchers

class AuthSelectUserInteractor(
    private val repository: AuthSelectUserRepository,
    private val coreDispatchers: CoreDispatchers
) {

    suspend fun getExistUsers(searchText: String): List<String> {
        return withContext(coreDispatchers.io) {
            repository.getExistUsers(searchText)
        }
    }
}