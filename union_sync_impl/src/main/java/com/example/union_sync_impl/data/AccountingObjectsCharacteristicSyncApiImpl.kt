package com.example.union_sync_impl.data

import com.example.union_sync_api.data.AccountingObjectsCharacteristicSyncApi
import com.example.union_sync_api.entity.AccountingObjectCharacteristicSyncEntity
import com.example.union_sync_impl.dao.AccountingObjectsSimpleCharacteristicsDao
import com.example.union_sync_impl.dao.AccountingObjectsVocabularyCharacteristicsDao
import com.example.union_sync_impl.data.mapper.toSyncEntity

class AccountingObjectsCharacteristicSyncApiImpl(
    private val vocabularyDao: AccountingObjectsVocabularyCharacteristicsDao,
    private val simpleDao: AccountingObjectsSimpleCharacteristicsDao
) : AccountingObjectsCharacteristicSyncApi {
    override suspend fun getVocabularyCharacteristics(accountingObjectId: String): List<AccountingObjectCharacteristicSyncEntity> {
        return vocabularyDao.getAll(accountingObjectId).map { it.toSyncEntity() }
    }

    override suspend fun getSimpleCharacteristics(accountingObjectId: String): List<AccountingObjectCharacteristicSyncEntity> {
        return simpleDao.getAll(accountingObjectId).map { it.toSyncEntity() }
    }
}