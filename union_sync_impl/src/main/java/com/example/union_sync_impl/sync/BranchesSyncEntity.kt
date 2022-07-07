package com.example.union_sync_impl.sync

import com.squareup.moshi.Moshi
import org.openapitools.client.custom_api.SyncControllerApi
import org.openapitools.client.models.BranchDtoV2

class BranchesSyncEntity (
    syncControllerApi: SyncControllerApi,
    moshi: Moshi,
    private val dbSaver: suspend (List<BranchDtoV2>) -> Unit
) : SyncEntity<BranchDtoV2>(syncControllerApi, moshi) {

    override val id: String
        get() = "branch"

    override suspend fun exportFromServer(syncId: String, exportPartId: String) {
        defaultGetAndSave<BranchDtoV2>(syncId, exportPartId)
    }

    override suspend fun saveInDb(objects: List<BranchDtoV2>) {
        dbSaver(objects)
    }
}