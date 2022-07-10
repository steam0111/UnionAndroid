package com.example.union_sync_impl.sync

import com.squareup.moshi.Moshi
import org.openapitools.client.custom_api.SyncControllerApi
import org.openapitools.client.models.ActionRecordDtoV2
import org.openapitools.client.models.ActionRemainsRecordDtoV2

class ActionRemainsRecordSyncEntity(
    syncControllerApi: SyncControllerApi,
    moshi: Moshi,
    private val dbSaver: suspend (List<ActionRemainsRecordDtoV2>) -> Unit
) : SyncEntity<ActionRemainsRecordDtoV2>(syncControllerApi, moshi) {

    override val id: String
        get() = "action"

    override val table: String
        get() = "actionRemainsRecord"

    override suspend fun exportFromServer(syncId: String, exportPartId: String) {
        defaultGetAndSave<ActionRemainsRecordDtoV2>(syncId, exportPartId)
    }

    override suspend fun saveInDb(objects: List<ActionRemainsRecordDtoV2>) {
        dbSaver(objects)
    }
}