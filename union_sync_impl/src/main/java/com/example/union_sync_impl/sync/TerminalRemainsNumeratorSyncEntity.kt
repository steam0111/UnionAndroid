package com.example.union_sync_impl.sync

import com.example.union_sync_impl.R
import com.example.union_sync_impl.dao.SyncDao
import com.squareup.moshi.Moshi
import kotlinx.coroutines.flow.Flow
import org.openapitools.client.custom_api.SyncControllerApi
import org.openapitools.client.models.TerminalRemainsNumeratorDtoV2

class TerminalRemainsNumeratorSyncEntity(
    syncControllerApi: SyncControllerApi,
    moshi: Moshi,
    private val dbSaver: suspend (List<TerminalRemainsNumeratorDtoV2>) -> Unit,
    private val dbPartsCollector: Flow<List<TerminalRemainsNumeratorDtoV2>>,
    syncDao: SyncDao
) : SyncEntity<TerminalRemainsNumeratorDtoV2>(syncControllerApi, moshi, syncDao),
    UploadableSyncEntity {

    override val id: String
        get() = "terminalRemainsNumerator"

    override val tableTitle: Int
        get() = R.string.terminal_remains_numerator_name

    override val localTableName: String
        get() = "terminal_remains_numerator"

    override suspend fun exportFromServer(syncId: String, exportPartId: String) {
        defaultGetAndSave<TerminalRemainsNumeratorDtoV2>(syncId, exportPartId)
    }

    override suspend fun saveInDb(objects: List<TerminalRemainsNumeratorDtoV2>) {
        dbSaver(objects.filter { !it.deleted })
    }

    override suspend fun upload(syncId: String) {
        defaultUpload(syncId, dbPartsCollector)
    }
}