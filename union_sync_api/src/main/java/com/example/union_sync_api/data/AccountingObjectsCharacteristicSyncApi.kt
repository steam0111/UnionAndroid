package com.example.union_sync_api.data

import com.example.union_sync_api.entity.AccountingObjectCharacteristicSyncEntity

interface AccountingObjectsCharacteristicSyncApi {
    suspend fun getVocabularyCharacteristics(accountingObjectId: String): List<AccountingObjectCharacteristicSyncEntity>

    suspend fun getSimpleCharacteristics(accountingObjectId: String): List<AccountingObjectCharacteristicSyncEntity>
}