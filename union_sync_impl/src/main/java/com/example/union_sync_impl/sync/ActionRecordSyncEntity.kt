package com.example.union_sync_impl.sync

import com.example.union_sync_impl.R
import com.example.union_sync_impl.dao.SyncDao
import com.squareup.moshi.Moshi
import kotlinx.coroutines.flow.Flow
import org.openapitools.client.custom_api.SyncControllerApi
import org.openapitools.client.models.AccountingObjectDtoV2
import org.openapitools.client.models.ActionRecordDtoV2

class ActionRecordSyncEntity(
    syncControllerApi: SyncControllerApi,
    moshi: Moshi,
    private val dbSaver: suspend (List<ActionRecordDtoV2>) -> Unit,
    private val dbPartsCollector: Flow<List<ActionRecordDtoV2>>,
    syncDao: SyncDao
) : SyncEntity<ActionRecordDtoV2>(syncControllerApi, moshi, syncDao), UploadableSyncEntity {

    override val id: String
        get() = "action"

    override val table: String
        get() = "actionRecord"

    override val tableTitle: Int
        get() = R.string.action_record_table_name

    override val localTableName: String
        get() = "action_record"

    override suspend fun exportFromServer(syncId: String, exportPartId: String) {
        defaultGetAndSave<ActionRecordDtoV2>(syncId, exportPartId)
    }

    override suspend fun saveInDb(objects: List<ActionRecordDtoV2>) {
        dbSaver(objects.filter { !it.deleted })
    }

    override suspend fun upload(syncId: String) {
        defaultUpload(syncId, dbPartsCollector)
    }
}