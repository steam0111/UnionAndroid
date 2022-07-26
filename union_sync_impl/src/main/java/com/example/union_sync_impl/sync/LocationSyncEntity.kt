package com.example.union_sync_impl.sync

import com.squareup.moshi.Moshi
import org.openapitools.client.custom_api.SyncControllerApi
import org.openapitools.client.models.LocationDtoV2

class LocationSyncEntity (
    syncControllerApi: SyncControllerApi,
    moshi: Moshi,
    private val dbSaver: suspend (List<LocationDtoV2>) -> Unit
) : SyncEntity<LocationDtoV2>(syncControllerApi, moshi) {

    override val id: String
        get() = "location"

    override suspend fun exportFromServer(syncId: String, exportPartId: String) {
        defaultGetAndSave<LocationDtoV2>(syncId, exportPartId)
    }

    override suspend fun saveInDb(objects: List<LocationDtoV2>) {
        dbSaver(objects)
    }
}