package com.itrocket.union.accountingObjects.domain.dependencies

import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain
import com.itrocket.union.manual.ParamDomain
import kotlinx.coroutines.flow.Flow

interface AccountingObjectRepository {

    suspend fun getAccountingObjects(): Flow<List<AccountingObjectDomain>>

    suspend fun getAccountingObjectsByParams(params: List<ParamDomain>): Flow<List<AccountingObjectDomain>>
}