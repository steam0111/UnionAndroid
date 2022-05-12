package com.itrocket.union.authContainer.domain

import com.itrocket.union.authContainer.domain.dependencies.AuthContainerRepository
import com.itrocket.core.base.CoreDispatchers

class AuthContainerInteractor(
    private val containerRepository: AuthContainerRepository,
    private val coreDispatchers: CoreDispatchers
) {

}