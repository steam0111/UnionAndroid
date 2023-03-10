package com.example.union_sync_impl.sync

import com.example.union_sync_impl.R
import com.example.union_sync_impl.dao.SyncDao
import com.squareup.moshi.Moshi
import kotlinx.coroutines.flow.Flow
import org.openapitools.client.custom_api.SyncControllerApi
import org.openapitools.client.models.TransitRemainsRecordDtoV2

class TransitRemainsRecordSyncEntity(
    syncControllerApi: SyncControllerApi,
    moshi: Moshi,
    private val dbSaver: suspend (List<TransitRemainsRecordDtoV2>) -> Unit,
    private val dbPartsCollector: Flow<List<TransitRemainsRecordDtoV2>>,
    syncDao: SyncDao
) : SyncEntity<TransitRemainsRecordDtoV2>(syncControllerApi, moshi, syncDao), UploadableSyncEntity {

    override val id: String
        get() = "Transit"

    override val table: String
        get() = "transitRemainsRecord"

    override val tableTitle: Int
        get() = R.string.transit_remains_record_table_name

    override suspend fun exportFromServer(syncId: String, exportPartId: String) {
        defaultGetAndSave<TransitRemainsRecordDtoV2>(syncId, exportPartId)
    }

    override suspend fun saveInDb(objects: List<TransitRemainsRecordDtoV2>) {
        dbSaver(objects)
    }

    override suspend fun upload(syncId: String) {
        defaultUpload(syncId, dbPartsCollector)
    }
}