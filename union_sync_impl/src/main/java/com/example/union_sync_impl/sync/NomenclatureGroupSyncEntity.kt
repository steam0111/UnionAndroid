package com.example.union_sync_impl.sync

import com.squareup.moshi.Moshi
import org.openapitools.client.custom_api.SyncControllerApi
import org.openapitools.client.models.NomenclatureGroupDtoV2

class NomenclatureGroupSyncEntity (
    syncControllerApi: SyncControllerApi,
    moshi: Moshi,
    private val dbSaver: suspend (List<NomenclatureGroupDtoV2>) -> Unit
) : SyncEntity<NomenclatureGroupDtoV2>(syncControllerApi, moshi) {

    override val id: String
        get() = "nomenclatureGroup"

    override suspend fun exportFromServer(syncId: String, exportPartId: String) {
        defaultGetAndSave<NomenclatureGroupDtoV2>(syncId, exportPartId)
    }

    override suspend fun saveInDb(objects: List<NomenclatureGroupDtoV2>) {
        dbSaver(objects)
    }
}