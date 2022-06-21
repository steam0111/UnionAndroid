package com.itrocket.union.identify.domain

import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.accountingObjects.domain.dependencies.AccountingObjectRepository
import com.itrocket.union.identify.domain.dependencies.IdentifyRepository
import kotlinx.coroutines.withContext

class IdentifyInteractor (
    private val repository: IdentifyRepository,
    private val coreDispatchers: CoreDispatchers
)
{
    suspend fun getIdentify() = withContext(coreDispatchers.io) {
        repository.getIdentify()
    }
}