package com.example.union_sync_impl.sync

import com.squareup.moshi.Moshi
import org.openapitools.client.custom_api.SyncControllerApi
import org.openapitools.client.models.InventoryRecordDtoV2

class InventoryRecordSyncEntity(
    syncControllerApi: SyncControllerApi,
    moshi: Moshi,
    private val dbSaver: suspend (List<InventoryRecordDtoV2>) -> Unit
) : SyncEntity<InventoryRecordDtoV2>(syncControllerApi, moshi) {

    override val id: String
        get() = "inventory"

    override val table: String
        get() = "inventoryRecord"

    override suspend fun exportFromServer(syncId: String, exportPartId: String) {
        defaultGetAndSave<InventoryRecordDtoV2>(syncId, exportPartId)
    }

    override suspend fun saveInDb(objects: List<InventoryRecordDtoV2>) {
        dbSaver(objects)
    }
}