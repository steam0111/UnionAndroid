package com.example.union_sync_impl.sync

import com.example.union_sync_impl.R
import com.squareup.moshi.Moshi
import org.openapitools.client.custom_api.SyncControllerApi
import org.openapitools.client.models.AccountingObjectVocabularyAdditionalFieldValueDtoV2
import org.openapitools.client.models.VocabularyAdditionalFieldValueDtoV2

class AccountingObjectVocabularyAdditionalFieldSyncEntity(
    syncControllerApi: SyncControllerApi,
    moshi: Moshi,
    private val dbSaver: suspend (List<AccountingObjectVocabularyAdditionalFieldValueDtoV2>) -> Unit
) : SyncEntity<AccountingObjectVocabularyAdditionalFieldValueDtoV2>(syncControllerApi, moshi) {

    override val id: String
        get() = "accountingObjectVocabularyAdditionalFieldValue"

    override val table: String
        get() = "accountingObjectVocabularyAdditionalFieldValue"

    override val tableTitle: Int
        get() = R.string.accounting_object_vocabulary_additional_table_name

    override suspend fun exportFromServer(syncId: String, exportPartId: String) {
        defaultGetAndSave<AccountingObjectVocabularyAdditionalFieldValueDtoV2>(syncId, exportPartId)
    }

    override suspend fun saveInDb(objects: List<AccountingObjectVocabularyAdditionalFieldValueDtoV2>) {
        dbSaver(objects)
    }
}