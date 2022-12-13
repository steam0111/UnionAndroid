package com.example.union_sync_impl.data

import com.example.union_sync_api.data.AccountingObjectUnionImageSyncApi
import com.example.union_sync_api.entity.AccountingObjectUnionImageSyncEntity
import com.example.union_sync_impl.dao.AccountingObjectUnionImageDao
import com.example.union_sync_impl.dao.sqlAccountingObjectUnionImageQuery
import com.example.union_sync_impl.data.mapper.toDb
import com.example.union_sync_impl.data.mapper.toSyncEntity

class AccountingObjectUnionImageSyncApiImpl(private val accountingObjectUnionImageDao: AccountingObjectUnionImageDao) :
    AccountingObjectUnionImageSyncApi {

    override suspend fun getAccountingObjectImagesById(accountingObjectId: String): List<AccountingObjectUnionImageSyncEntity> {
        return accountingObjectUnionImageDao.getAll(
            sqlAccountingObjectUnionImageQuery(
                accountingObjectId = accountingObjectId
            )
        ).map { it.toSyncEntity() }
    }

    override suspend fun saveAccountingObjectImage(syncEntity: AccountingObjectUnionImageSyncEntity) {
        accountingObjectUnionImageDao.insert(syncEntity.toDb())
    }
}