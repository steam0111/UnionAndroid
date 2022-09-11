package com.example.union_sync_impl.sync

import com.squareup.moshi.Moshi
import org.openapitools.client.custom_api.SyncControllerApi
import org.openapitools.client.models.AccountingObjectSimpleAdditionalFieldValueDtoV2

class AccountingObjectSimpleAdditionalFieldSyncEntity(
    syncControllerApi: SyncControllerApi,
    moshi: Moshi,
    private val dbSaver: suspend (List<AccountingObjectSimpleAdditionalFieldValueDtoV2>) -> Unit
) : SyncEntity<AccountingObjectSimpleAdditionalFieldValueDtoV2>(syncControllerApi, moshi) {

    override val id: String
        get() = "accountingObjectSimpleAdditionalFieldValue"

    override val table: String
        get() = "accountingObjectSimpleAdditionalFieldValue"

    override suspend fun exportFromServer(syncId: String, exportPartId: String) {
        defaultGetAndSave<AccountingObjectSimpleAdditionalFieldValueDtoV2>(syncId, exportPartId)
    }

    override suspend fun saveInDb(objects: List<AccountingObjectSimpleAdditionalFieldValueDtoV2>) {
        dbSaver(objects)
    }
}