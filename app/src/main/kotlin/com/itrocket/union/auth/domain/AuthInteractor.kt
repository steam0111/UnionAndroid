package com.itrocket.union.auth.domain

import kotlinx.coroutines.withContext
import com.itrocket.union.auth.domain.dependencies.AuthRepository
import com.itrocket.core.base.CoreDispatchers

class AuthInteractor(
    private val repository: AuthRepository,
    private val coreDispatchers: CoreDispatchers
) {

}