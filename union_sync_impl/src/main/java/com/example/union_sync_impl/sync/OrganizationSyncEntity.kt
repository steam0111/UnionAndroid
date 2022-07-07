package com.example.union_sync_impl.sync

import com.squareup.moshi.Moshi
import org.openapitools.client.custom_api.SyncControllerApi
import org.openapitools.client.models.OrganizationDtoV2

class OrganizationSyncEntity (
    syncControllerApi: SyncControllerApi,
    moshi: Moshi,
    private val dbSaver: suspend (List<OrganizationDtoV2>) -> Unit
) : SyncEntity<OrganizationDtoV2>(syncControllerApi, moshi) {

    override val id: String
        get() = "organization"

    override suspend fun exportFromServer(syncId: String, exportPartId: String) {
        defaultGetAndSave<OrganizationDtoV2>(syncId, exportPartId)
    }

    override suspend fun saveInDb(objects: List<OrganizationDtoV2>) {
        dbSaver(objects)
    }
}