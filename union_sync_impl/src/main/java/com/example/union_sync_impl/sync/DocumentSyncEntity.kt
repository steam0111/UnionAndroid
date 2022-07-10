package com.example.union_sync_impl.sync

import com.squareup.moshi.Moshi
import kotlinx.coroutines.flow.Flow
import org.openapitools.client.custom_api.SyncControllerApi
import org.openapitools.client.models.ActionDtoV2
import org.openapitools.client.models.InventoryDtoV2

class DocumentSyncEntity(
    syncControllerApi: SyncControllerApi,
    moshi: Moshi,
    private val dbSaver: suspend (List<ActionDtoV2>) -> Unit,
    private val dbPartsCollector: Flow<List<ActionDtoV2>>
) : SyncEntity<ActionDtoV2>(syncControllerApi, moshi), UploadableSyncEntity {

    override val id: String
        get() = "action"

    override suspend fun exportFromServer(syncId: String, exportPartId: String) {
        defaultGetAndSave<ActionDtoV2>(syncId, exportPartId)
    }

    override suspend fun saveInDb(objects: List<ActionDtoV2>) {
        dbSaver(objects)
    }

    override suspend fun upload(syncId: String) {
        defaultUpload(syncId, dbPartsCollector)
    }
}