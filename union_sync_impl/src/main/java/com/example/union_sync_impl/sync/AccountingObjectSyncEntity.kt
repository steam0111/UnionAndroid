package com.example.union_sync_impl.sync

import com.squareup.moshi.Moshi
import org.openapitools.client.custom_api.SyncControllerApi
import org.openapitools.client.models.AccountingObjectDtoV2

class AccountingObjectSyncEntity(
    syncControllerApi: SyncControllerApi,
    moshi: Moshi,
    private val dbSaver: suspend (List<AccountingObjectDtoV2>) -> Unit
) : SyncEntity<AccountingObjectDtoV2>(syncControllerApi, moshi) {

    override val id: String
        get() = "accountingObject"

    override suspend fun exportFromServer(syncId: String, exportPartId: String) {
        defaultGetAndSave<AccountingObjectDtoV2>(syncId, exportPartId)
    }

    override suspend fun saveInDb(objects: List<AccountingObjectDtoV2>) {
        dbSaver(objects)
    }
}