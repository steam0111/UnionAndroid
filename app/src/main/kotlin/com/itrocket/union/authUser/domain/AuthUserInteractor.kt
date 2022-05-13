package com.itrocket.union.authUser.domain

import kotlinx.coroutines.withContext
import com.itrocket.union.authUser.domain.dependencies.AuthUserRepository
import com.itrocket.core.base.CoreDispatchers

class AuthUserInteractor(
    private val repository: AuthUserRepository,
    private val coreDispatchers: CoreDispatchers
) {

}