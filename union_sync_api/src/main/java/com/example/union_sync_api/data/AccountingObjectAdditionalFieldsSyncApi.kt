package com.example.union_sync_api.data

import com.example.union_sync_api.entity.AccountingObjectAdditionalFieldSyncEntity

interface AccountingObjectAdditionalFieldsSyncApi {

    suspend fun getVocabularyAdditionalFields(accountingObjectId: String): List<AccountingObjectAdditionalFieldSyncEntity>

    suspend fun getSimpleAdditionalFields(accountingObjectId: String): List<AccountingObjectAdditionalFieldSyncEntity>

}