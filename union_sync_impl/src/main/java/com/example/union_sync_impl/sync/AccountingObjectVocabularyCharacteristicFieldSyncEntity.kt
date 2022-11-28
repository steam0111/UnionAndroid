package com.example.union_sync_impl.sync

import com.example.union_sync_impl.R
import com.squareup.moshi.Moshi
import org.openapitools.client.custom_api.SyncControllerApi
import org.openapitools.client.models.AccountingObjectCharacteristicValueDtoV2
import org.openapitools.client.models.AccountingObjectVocabularyCharacteristicValueDtoV2

class AccountingObjectVocabularyCharacteristicFieldSyncEntity(
    syncControllerApi: SyncControllerApi,
    moshi: Moshi,
    private val dbSaver: suspend (List<AccountingObjectVocabularyCharacteristicValueDtoV2>) -> Unit
) : SyncEntity<AccountingObjectVocabularyCharacteristicValueDtoV2>(syncControllerApi, moshi) {

    override val id: String
        get() = "accountingObjectVocabularyCharacteristicValue"

    override val table: String
        get() = "accountingObjectVocabularyCharacteristicValue"

    override val tableTitle: Int
        get() = R.string.accounting_objects_vocabulary_characteristics_field_table_name

    override suspend fun exportFromServer(syncId: String, exportPartId: String) {
        defaultGetAndSave<AccountingObjectVocabularyCharacteristicValueDtoV2>(syncId, exportPartId)
    }

    override suspend fun saveInDb(objects: List<AccountingObjectVocabularyCharacteristicValueDtoV2>) {
        dbSaver(objects)
    }
}