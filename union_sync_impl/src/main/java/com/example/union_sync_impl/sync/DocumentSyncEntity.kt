package com.example.union_sync_impl.sync

import com.squareup.moshi.Moshi
import org.openapitools.client.custom_api.SyncControllerApi
import org.openapitools.client.models.ActionDtoV2

class DocumentSyncEntity(
    syncControllerApi: SyncControllerApi,
    moshi: Moshi,
    private val dbSaver: suspend (List<ActionDtoV2>) -> Unit
) : SyncEntity<ActionDtoV2>(syncControllerApi, moshi) {

    override val id: String
        get() = "action"

    override suspend fun exportFromServer(syncId: String, exportPartId: String) {
        defaultGetAndSave<ActionDtoV2>(syncId, exportPartId)
    }

    override suspend fun saveInDb(objects: List<ActionDtoV2>) {
        dbSaver(objects)
    }
}