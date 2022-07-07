package com.example.union_sync_impl.sync

import com.squareup.moshi.Moshi
import org.openapitools.client.custom_api.SyncControllerApi
import org.openapitools.client.models.EquipmentTypeDtoV2

class EquipmentSyncEntity (
    syncControllerApi: SyncControllerApi,
    moshi: Moshi,
    private val dbSaver: suspend (List<EquipmentTypeDtoV2>) -> Unit
) : SyncEntity<EquipmentTypeDtoV2>(syncControllerApi, moshi) {

    override val id: String
        get() = "equipmentType"

    override suspend fun exportFromServer(syncId: String, exportPartId: String) {
        defaultGetAndSave<EquipmentTypeDtoV2>(syncId, exportPartId)
    }

    override suspend fun saveInDb(objects: List<EquipmentTypeDtoV2>) {
        dbSaver(objects)
    }
}