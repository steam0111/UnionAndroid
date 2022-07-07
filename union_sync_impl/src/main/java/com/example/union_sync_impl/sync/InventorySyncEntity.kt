package com.example.union_sync_impl.sync

import android.util.Log
import com.squareup.moshi.Moshi
import org.openapitools.client.custom_api.SyncControllerApi
import org.openapitools.client.models.InventoryDtoV2

class InventorySyncEntity(
    syncControllerApi: SyncControllerApi,
    moshi: Moshi,
    private val dbSaver: suspend (List<InventoryDtoV2>) -> Unit
) : SyncEntity<InventoryDtoV2>(syncControllerApi, moshi) {

    override val id: String
        get() = "inventory"

    override suspend fun exportFromServer(syncId: String, exportPartId: String) {
        defaultGetAndSave<InventoryDtoV2>(syncId, exportPartId)
    }

    override suspend fun saveInDb(objects: List<InventoryDtoV2>) {
        dbSaver(objects)
    }
}