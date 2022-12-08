package com.example.union_sync_impl.sync

import com.example.union_sync_impl.R
import com.squareup.moshi.Moshi
import kotlinx.coroutines.flow.Flow
import org.openapitools.client.custom_api.SyncControllerApi
import org.openapitools.client.models.TerminalRemainsNumeratorDtoV2

class TerminalRemainsNumeratorSyncEntity(
    syncControllerApi: SyncControllerApi,
    moshi: Moshi,
    private val dbSaver: suspend (List<TerminalRemainsNumeratorDtoV2>) -> Unit,
    private val dbPartsCollector: Flow<List<TerminalRemainsNumeratorDtoV2>>
) : SyncEntity<TerminalRemainsNumeratorDtoV2>(syncControllerApi, moshi), UploadableSyncEntity {

    override val id: String
        get() = "terminalRemainsNumerator"

    override val tableTitle: Int
        get() = R.string.action_record_table_name

    override suspend fun exportFromServer(syncId: String, exportPartId: String) {
        defaultGetAndSave<TerminalRemainsNumeratorDtoV2>(syncId, exportPartId)
    }

    override suspend fun saveInDb(objects: List<TerminalRemainsNumeratorDtoV2>) {
        dbSaver(objects)
    }

    override suspend fun upload(syncId: String) {
        defaultUpload(syncId, dbPartsCollector)
    }
}