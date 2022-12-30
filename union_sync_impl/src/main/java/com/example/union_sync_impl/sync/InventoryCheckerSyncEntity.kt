package com.example.union_sync_impl.sync

import com.example.union_sync_impl.R
import com.example.union_sync_impl.dao.SyncDao
import com.squareup.moshi.Moshi
import org.openapitools.client.custom_api.SyncControllerApi
import org.openapitools.client.models.InventoryCheckerDto
import org.openapitools.client.models.InventoryRecordDtoV2

class InventoryCheckerSyncEntity(
    syncControllerApi: SyncControllerApi,
    moshi: Moshi,
    private val dbSaver: suspend (List<InventoryCheckerDto>) -> Unit,
    syncDao: SyncDao
) : SyncEntity<InventoryCheckerDto>(syncControllerApi, moshi, syncDao) {

    override val id: String
        get() = "inventory"

    override val table: String
        get() = "inventoryChecker"

    override val localTableName: String
        get() = "inventory_checker"

    override val tableTitle: Int
        get() = R.string.inventory_checker_table_name

    override suspend fun exportFromServer(syncId: String, exportPartId: String) {
        defaultGetAndSave<InventoryCheckerDto>(syncId, exportPartId)
    }

    override suspend fun saveInDb(objects: List<InventoryCheckerDto>) {
        dbSaver(objects.filter { !it.deleted })
    }
}