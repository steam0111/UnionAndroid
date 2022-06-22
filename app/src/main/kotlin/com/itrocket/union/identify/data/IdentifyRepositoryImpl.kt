package com.itrocket.union.identify.data

import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain
import com.itrocket.union.identify.domain.dependencies.IdentifyRepository
import com.itrocket.union.identify.domain.entity.IdentifyDomain

class IdentifyRepositoryImpl: IdentifyRepository {
    override suspend fun getIdentify(): List<AccountingObjectDomain> {
        TODO("Not yet implemented")
    }
}