package com.example.union_sync_impl.sync

import com.squareup.moshi.Moshi
import org.openapitools.client.custom_api.SyncControllerApi
import org.openapitools.client.models.StructuralUnitDtoV2

class StructuralSyncEntity  (
    syncControllerApi: SyncControllerApi,
    moshi: Moshi,
    private val dbSaver: suspend (List<StructuralUnitDtoV2>) -> Unit
) : SyncEntity<StructuralUnitDtoV2>(syncControllerApi, moshi) {

    override val id: String
        get() = "structuralUnit"

    override suspend fun exportFromServer(syncId: String, exportPartId: String) {
        defaultGetAndSave<StructuralUnitDtoV2>(syncId, exportPartId)
    }

    override suspend fun saveInDb(objects: List<StructuralUnitDtoV2>) {
        dbSaver(objects)
    }
}