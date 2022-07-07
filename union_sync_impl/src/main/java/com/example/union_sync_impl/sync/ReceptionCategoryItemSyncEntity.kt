package com.example.union_sync_impl.sync

import com.squareup.moshi.Moshi
import org.openapitools.client.custom_api.SyncControllerApi
import org.openapitools.client.models.ReceptionItemCategoryDtoV2

class ReceptionCategoryItemSyncEntity (
    syncControllerApi: SyncControllerApi,
    moshi: Moshi,
    private val dbSaver: suspend (List<ReceptionItemCategoryDtoV2>) -> Unit
) : SyncEntity<ReceptionItemCategoryDtoV2>(syncControllerApi, moshi) {

    override val id: String
        get() = "reception"

    override suspend fun exportFromServer(syncId: String, exportPartId: String) {
        defaultGetAndSave<ReceptionItemCategoryDtoV2>(syncId, exportPartId)
    }

    override suspend fun saveInDb(objects: List<ReceptionItemCategoryDtoV2>) {
        dbSaver(objects)
    }
}