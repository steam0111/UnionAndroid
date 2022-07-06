package com.itrocket.union.accountingObjectDetail.data

import com.example.union_sync_api.data.AccountingObjectSyncApi
import com.itrocket.union.accountingObjectDetail.data.mapper.toAccountingObjectDetailDomain
import com.itrocket.union.accountingObjectDetail.domain.dependencies.AccountingObjectDetailRepository
import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain

class AccountingObjectDetailRepositoryImpl(
    private val syncApi: AccountingObjectSyncApi
) : AccountingObjectDetailRepository {

    override suspend fun getAccountingObject(id: String): AccountingObjectDomain {
        return syncApi.getAccountingObjectDetailById(id).toAccountingObjectDetailDomain()
    }
}