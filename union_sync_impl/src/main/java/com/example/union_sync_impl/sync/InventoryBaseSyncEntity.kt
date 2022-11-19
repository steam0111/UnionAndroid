package com.example.union_sync_impl.sync

import com.example.union_sync_impl.R
import com.squareup.moshi.Moshi
import org.openapitools.client.custom_api.SyncControllerApi
import org.openapitools.client.models.EnumDtoV2

class InventoryBaseSyncEntity(
    syncControllerApi: SyncControllerApi,
    moshi: Moshi,
    private val dbSaver: suspend (List<EnumDtoV2>) -> Unit
) : SyncEntity<EnumDtoV2>(syncControllerApi, moshi) {

    override val id: String
        get() = "inventoryBase"

    override val tableTitle: Int
        get() = R.string.inventory_bases_table_name

    override suspend fun exportFromServer(syncId: String, exportPartId: String) {
        defaultGetAndSave<EnumDtoV2>(syncId, exportPartId)
    }

    override suspend fun saveInDb(objects: List<EnumDtoV2>) {
        dbSaver(objects)
    }
}