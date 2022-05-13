package com.itrocket.union.authMain.domain

import kotlinx.coroutines.withContext
import com.itrocket.union.authMain.domain.dependencies.AuthMainRepository
import com.itrocket.core.base.CoreDispatchers

class AuthMainInteractor(
    private val repository: AuthMainRepository,
    private val coreDispatchers: CoreDispatchers
) {

}