package com.example.union_sync_impl.sync

import com.example.union_sync_impl.R
import com.example.union_sync_impl.dao.SyncDao
import com.squareup.moshi.Moshi
import kotlinx.coroutines.flow.Flow
import org.openapitools.client.custom_api.SyncControllerApi
import org.openapitools.client.models.ActionRecordDtoV2
import org.openapitools.client.models.ActionRemainsRecordDtoV2

class ActionRemainsRecordSyncEntity(
    syncControllerApi: SyncControllerApi,
    moshi: Moshi,
    private val dbSaver: suspend (List<ActionRemainsRecordDtoV2>) -> Unit,
    private val dbPartsCollector: Flow<List<ActionRemainsRecordDtoV2>>,
    syncDao: SyncDao
) : SyncEntity<ActionRemainsRecordDtoV2>(syncControllerApi, moshi, syncDao), UploadableSyncEntity {

    override val id: String
        get() = "action"

    override val table: String
        get() = "actionRemainsRecord"

    override val tableTitle: Int
        get() = R.string.action_remains_record_table_name

    override val localTableName: String
        get() = "action_remains_record"

    override suspend fun exportFromServer(syncId: String, exportPartId: String) {
        defaultGetAndSave<ActionRemainsRecordDtoV2>(syncId, exportPartId)
    }

    override suspend fun saveInDb(objects: List<ActionRemainsRecordDtoV2>) {
        dbSaver(objects.filter { !it.deleted })
    }

    override suspend fun upload(syncId: String) {
        defaultUpload(syncId, dbPartsCollector)
    }
}