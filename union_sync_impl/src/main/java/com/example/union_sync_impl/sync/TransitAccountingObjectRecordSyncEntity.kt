package com.example.union_sync_impl.sync

import com.example.union_sync_impl.R
import com.example.union_sync_impl.dao.SyncDao
import com.squareup.moshi.Moshi
import kotlinx.coroutines.flow.Flow
import org.openapitools.client.custom_api.SyncControllerApi
import org.openapitools.client.models.TransitAccountingObjectRecordDtoV2

class TransitAccountingObjectRecordSyncEntity(
    syncControllerApi: SyncControllerApi,
    moshi: Moshi,
    private val dbSaver: suspend (List<TransitAccountingObjectRecordDtoV2>) -> Unit,
    private val dbPartsCollector: Flow<List<TransitAccountingObjectRecordDtoV2>>,
    syncDao: SyncDao
) : SyncEntity<TransitAccountingObjectRecordDtoV2>(syncControllerApi, moshi, syncDao), UploadableSyncEntity {

    override val id: String
        get() = "Transit"

    override val table: String
        get() = "transitAccountingObjectRecord"

    override val tableTitle: Int
        get() = R.string.transit_accounting_object_record_table_name


    override suspend fun exportFromServer(syncId: String, exportPartId: String) {
        defaultGetAndSave<TransitAccountingObjectRecordDtoV2>(syncId, exportPartId)
    }

    override suspend fun saveInDb(objects: List<TransitAccountingObjectRecordDtoV2>) {
        dbSaver(objects)
    }

    override suspend fun upload(syncId: String) {
        defaultUpload(syncId, dbPartsCollector)
    }
}