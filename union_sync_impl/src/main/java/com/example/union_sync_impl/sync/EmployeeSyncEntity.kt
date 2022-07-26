package com.example.union_sync_impl.sync

import com.squareup.moshi.Moshi
import org.openapitools.client.custom_api.SyncControllerApi
import org.openapitools.client.models.EmployeeDtoV2

class EmployeeSyncEntity (
    syncControllerApi: SyncControllerApi,
    moshi: Moshi,
    private val dbSaver: suspend (List<EmployeeDtoV2>) -> Unit
) : SyncEntity<EmployeeDtoV2>(syncControllerApi, moshi) {

    override val id: String
        get() = "employee"

    override suspend fun exportFromServer(syncId: String, exportPartId: String) {
        defaultGetAndSave<EmployeeDtoV2>(syncId, exportPartId)
    }

    override suspend fun saveInDb(objects: List<EmployeeDtoV2>) {
        dbSaver(objects)
    }
}