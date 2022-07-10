package com.example.union_sync_impl.sync

import com.squareup.moshi.Moshi
import org.openapitools.client.custom_api.SyncControllerApi
import org.openapitools.client.models.ActionRecordDtoV2

class ActionRecordSyncEntity(
    syncControllerApi: SyncControllerApi,
    moshi: Moshi,
    private val dbSaver: suspend (List<ActionRecordDtoV2>) -> Unit
) : SyncEntity<ActionRecordDtoV2>(syncControllerApi, moshi) {

    override val id: String
        get() = "action"

    override val table: String
        get() = "actionRecord"

    override suspend fun exportFromServer(syncId: String, exportPartId: String) {
        defaultGetAndSave<ActionRecordDtoV2>(syncId, exportPartId)
    }

    override suspend fun saveInDb(objects: List<ActionRecordDtoV2>) {
        dbSaver(objects)
    }
}