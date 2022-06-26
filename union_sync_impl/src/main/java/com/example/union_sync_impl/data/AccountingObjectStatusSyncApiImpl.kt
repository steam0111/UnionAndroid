package com.example.union_sync_impl.data

import com.example.union_sync_api.data.AccountingObjectStatusSyncApi
import com.example.union_sync_api.entity.AccountingObjectStatusSyncEntity
import com.example.union_sync_impl.dao.AccountingObjectStatusDao
import com.example.union_sync_impl.data.mapper.toSyncEntity

class AccountingObjectStatusSyncApiImpl(
    private val dao: AccountingObjectStatusDao
) : AccountingObjectStatusSyncApi {

    override suspend fun getStatuses(): List<AccountingObjectStatusSyncEntity> {
        return dao.getAll().map { it.toSyncEntity() }
    }
}