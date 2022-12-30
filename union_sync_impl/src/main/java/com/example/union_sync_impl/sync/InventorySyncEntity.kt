package com.example.union_sync_impl.sync

import com.example.union_sync_impl.R
import com.example.union_sync_impl.dao.SyncDao
import com.squareup.moshi.Moshi
import kotlinx.coroutines.flow.Flow
import org.openapitools.client.custom_api.SyncControllerApi
import org.openapitools.client.models.InventoryDtoV2

class InventorySyncEntity(
    syncControllerApi: SyncControllerApi,
    moshi: Moshi,
    private val dbSaver: suspend (List<InventoryDtoV2>) -> Unit,
    private val dbPartsCollector: Flow<List<InventoryDtoV2>>,
    syncDao: SyncDao
) : SyncEntity<InventoryDtoV2>(syncControllerApi, moshi, syncDao), UploadableSyncEntity {

    override val id: String
        get() = "inventory"

    override val tableTitle: Int
        get() = R.string.inventory_table_name

    override val localTableName: String
        get() = "inventories"

    override suspend fun exportFromServer(syncId: String, exportPartId: String) {
        defaultGetAndSave<InventoryDtoV2>(syncId, exportPartId)
    }

    override suspend fun saveInDb(objects: List<InventoryDtoV2>) {
        dbSaver(objects.filter { !it.deleted })
    }

    override suspend fun upload(syncId: String) {
        defaultUpload(syncId, dbPartsCollector)
    }
}