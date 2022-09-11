package com.example.union_sync_impl.data

import com.example.union_sync_api.data.AccountingObjectAdditionalFieldsSyncApi
import com.example.union_sync_api.entity.AccountingObjectAdditionalFieldSyncEntity
import com.example.union_sync_impl.dao.AccountingObjectSimpleAdditionalFieldDao
import com.example.union_sync_impl.dao.AccountingObjectVocabularyAdditionalFieldDao
import com.example.union_sync_impl.data.mapper.toSyncEntity

class AccountingObjectAdditionalFieldsSyncApiImpl(
    private val vocabularyDao: AccountingObjectVocabularyAdditionalFieldDao,
    private val simpleDao: AccountingObjectSimpleAdditionalFieldDao
) : AccountingObjectAdditionalFieldsSyncApi {
    override suspend fun getVocabularyAdditionalFields(accountingObjectId: String): List<AccountingObjectAdditionalFieldSyncEntity> {
        return vocabularyDao.getAll(accountingObjectId).map { it.toSyncEntity() }
    }

    override suspend fun getSimpleAdditionalFields(accountingObjectId: String): List<AccountingObjectAdditionalFieldSyncEntity> {
        return simpleDao.getAll(accountingObjectId).map { it.toSyncEntity() }
    }
}