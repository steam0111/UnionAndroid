package com.example.union_sync_impl.sync

import com.squareup.moshi.Moshi
import org.openapitools.client.custom_api.SyncControllerApi
import org.openapitools.client.models.ActionBaseDtoV2

class ActionBasesSyncEntity(
    syncControllerApi: SyncControllerApi,
    moshi: Moshi,
    private val dbSaver: suspend (List<ActionBaseDtoV2>) -> Unit,
) : SyncEntity<ActionBaseDtoV2>(syncControllerApi, moshi) {

    override val id: String
        get() = "ActionBase"

    override suspend fun exportFromServer(syncId: String, exportPartId: String) {
        defaultGetAndSave<ActionBaseDtoV2>(syncId, exportPartId)
    }

    override suspend fun saveInDb(objects: List<ActionBaseDtoV2>) {
        dbSaver(objects)
    }
}