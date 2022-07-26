package com.example.union_sync_impl.sync

import com.squareup.moshi.Moshi
import org.openapitools.client.custom_api.SyncControllerApi
import org.openapitools.client.models.DepartmentDtoV2

class DepartmentSyncEntity (
    syncControllerApi: SyncControllerApi,
    moshi: Moshi,
    private val dbSaver: suspend (List<DepartmentDtoV2>) -> Unit
) : SyncEntity<DepartmentDtoV2>(syncControllerApi, moshi) {

    override val id: String
        get() = "department"

    override suspend fun exportFromServer(syncId: String, exportPartId: String) {
        defaultGetAndSave<DepartmentDtoV2>(syncId, exportPartId)
    }

    override suspend fun saveInDb(objects: List<DepartmentDtoV2>) {
        dbSaver(objects)
    }
}