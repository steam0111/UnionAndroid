package com.example.union_sync_impl.sync

import com.squareup.moshi.Moshi
import org.openapitools.client.custom_api.SyncControllerApi
import org.openapitools.client.models.RemainsDtoV2

class ReserveSyncEntity (
    syncControllerApi: SyncControllerApi,
    moshi: Moshi,
    private val dbSaver: suspend (List<RemainsDtoV2>) -> Unit
) : SyncEntity<RemainsDtoV2>(syncControllerApi, moshi) {

    override val id: String
        get() = "remains"

    override suspend fun exportFromServer(syncId: String, exportPartId: String) {
        defaultGetAndSave<RemainsDtoV2>(syncId, exportPartId)
    }

    override suspend fun saveInDb(objects: List<RemainsDtoV2>) {
        dbSaver(objects)
    }
}