package com.example.union_sync_impl.sync

import com.squareup.moshi.Moshi
import org.openapitools.client.custom_api.SyncControllerApi
import org.openapitools.client.models.InventoryCheckerDto
import org.openapitools.client.models.InventoryRecordDtoV2

class InventoryCheckerSyncEntity(
    syncControllerApi: SyncControllerApi,
    moshi: Moshi,
    private val dbSaver: suspend (List<InventoryCheckerDto>) -> Unit,
) : SyncEntity<InventoryCheckerDto>(syncControllerApi, moshi) {

    override val id: String
        get() = "inventory"

    override val table: String
        get() = "inventoryChecker"

    override suspend fun exportFromServer(syncId: String, exportPartId: String) {
        defaultGetAndSave<InventoryRecordDtoV2>(syncId, exportPartId)
    }

    override suspend fun saveInDb(objects: List<InventoryCheckerDto>) {
        dbSaver(objects)
    }
}