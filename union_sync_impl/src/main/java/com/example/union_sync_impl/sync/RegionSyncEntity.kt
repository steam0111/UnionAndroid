package com.example.union_sync_impl.sync

import com.squareup.moshi.Moshi
import org.openapitools.client.custom_api.SyncControllerApi
import org.openapitools.client.models.RegionDtoV2

class RegionSyncEntity (
    syncControllerApi: SyncControllerApi,
    moshi: Moshi,
    private val dbSaver: suspend (List<RegionDtoV2>) -> Unit
) : SyncEntity<RegionDtoV2>(syncControllerApi, moshi) {

    override val id: String
        get() = "region"

    override suspend fun exportFromServer(syncId: String, exportPartId: String) {
        defaultGetAndSave<RegionDtoV2>(syncId, exportPartId)
    }

    override suspend fun saveInDb(objects: List<RegionDtoV2>) {
        dbSaver(objects)
    }
}