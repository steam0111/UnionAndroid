package com.itrocket.union.authAndLicense.domain

import kotlinx.coroutines.withContext
import com.itrocket.union.authAndLicense.domain.dependencies.AuthAndLicenseRepository
import com.itrocket.core.base.CoreDispatchers

class AuthAndLicenseInteractor(
    private val repository: AuthAndLicenseRepository,
    private val coreDispatchers: CoreDispatchers
) {

}