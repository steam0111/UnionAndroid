package com.example.union_sync_impl.data

import com.example.union_sync_api.data.AccountingObjectUnionImageSyncApi
import com.example.union_sync_api.entity.AccountingObjectImageMainUpdate
import com.example.union_sync_api.entity.AccountingObjectUnionImageSyncEntity
import com.example.union_sync_impl.dao.AccountingObjectUnionImageDao
import com.example.union_sync_impl.dao.sqlAccountingObjectUnionImageQuery
import com.example.union_sync_impl.data.mapper.toDb
import com.example.union_sync_impl.data.mapper.toSyncEntity
import com.example.union_sync_impl.data.mapper.toUpdate
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AccountingObjectUnionImageSyncApiImpl(private val accountingObjectUnionImageDao: AccountingObjectUnionImageDao) :
    AccountingObjectUnionImageSyncApi {

    override suspend fun getAccountingObjectImagesByIdFlow(accountingObjectId: String): Flow<List<AccountingObjectUnionImageSyncEntity>> {
        return accountingObjectUnionImageDao.getAllFlow(accountingObjectId = accountingObjectId)
            .map {
                it.map { it.toSyncEntity() }
            }
    }

    override suspend fun getAccountingObjectImages(
        accountingObjectId: String?,
        updateDate: Long?
    ): List<AccountingObjectUnionImageSyncEntity> {
        return accountingObjectUnionImageDao.getAll(
            sqlAccountingObjectUnionImageQuery(
                accountingObjectId = accountingObjectId,
                updateDate = updateDate
            )
        ).map { it.toSyncEntity() }
    }

    override suspend fun saveAccountingObjectImage(
        syncEntity: AccountingObjectUnionImageSyncEntity,
        oldMainImageId: String?
    ) {
        oldMainImageId?.let { accountingObjectUnionImageDao.unsetMainImage(it) }
        accountingObjectUnionImageDao.insert(syncEntity.toDb())
    }

    override suspend fun changeMainImage(update: List<AccountingObjectImageMainUpdate>) {
        return accountingObjectUnionImageDao.update(update.map { it.toUpdate() })
    }

    override suspend fun deleteAccountingObjectImage(imageId: String) {
        return accountingObjectUnionImageDao.deleteImageById(imageId)
    }
}