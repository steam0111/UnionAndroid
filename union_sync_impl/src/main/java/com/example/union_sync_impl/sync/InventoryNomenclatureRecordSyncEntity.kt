package com.example.union_sync_impl.sync

import com.example.union_sync_impl.R
import com.example.union_sync_impl.dao.SyncDao
import com.squareup.moshi.Moshi
import kotlinx.coroutines.flow.Flow
import org.openapitools.client.custom_api.SyncControllerApi
import org.openapitools.client.models.InventoryNomenclatureRecordDtoV2

class InventoryNomenclatureRecordSyncEntity (
    syncControllerApi: SyncControllerApi,
    moshi: Moshi,
    private val dbSaver: suspend (List<InventoryNomenclatureRecordDtoV2>) -> Unit,
    private val dbPartsCollector: Flow<List<InventoryNomenclatureRecordDtoV2>>,
    syncDao: SyncDao
) : SyncEntity<InventoryNomenclatureRecordDtoV2>(syncControllerApi, moshi, syncDao), UploadableSyncEntity {

    override val id: String
        get() = "inventory"

    override val table: String
        get() = "inventoryNomenclatureRecord"

    override val tableTitle: Int
        get() = R.string.inventory_record_table_name

    override val localTableName: String
        get() = "inventory_nomenclature_record"

    override suspend fun exportFromServer(syncId: String, exportPartId: String) {
        defaultGetAndSave<InventoryNomenclatureRecordDtoV2>(syncId, exportPartId)
    }

    override suspend fun saveInDb(objects: List<InventoryNomenclatureRecordDtoV2>) {
        dbSaver(objects.filter { !it.deleted })
    }

    override suspend fun upload(syncId: String) {
        defaultUpload(syncId, dbPartsCollector)
    }
}