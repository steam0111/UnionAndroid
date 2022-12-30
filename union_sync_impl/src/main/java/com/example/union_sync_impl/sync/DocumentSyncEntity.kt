package com.example.union_sync_impl.sync

import com.example.union_sync_impl.R
import com.example.union_sync_impl.dao.SyncDao
import com.squareup.moshi.Moshi
import kotlinx.coroutines.flow.Flow
import org.openapitools.client.custom_api.SyncControllerApi
import org.openapitools.client.models.ActionDtoV2

class DocumentSyncEntity(
    syncControllerApi: SyncControllerApi,
    moshi: Moshi,
    private val dbSaver: suspend (List<ActionDtoV2>) -> Unit,
    private val dbPartsCollector: Flow<List<ActionDtoV2>>,
    syncDao: SyncDao
) : SyncEntity<ActionDtoV2>(syncControllerApi, moshi, syncDao), UploadableSyncEntity {

    override val id: String
        get() = "action"

    override val tableTitle: Int
        get() = R.string.document_table_name

    override val localTableName: String
        get() = "documents"

    override suspend fun exportFromServer(syncId: String, exportPartId: String) {
        defaultGetAndSave<ActionDtoV2>(syncId, exportPartId)
    }

    override suspend fun saveInDb(objects: List<ActionDtoV2>) {
        dbSaver(objects.filter { !it.deleted })
    }

    override suspend fun upload(syncId: String) {
        defaultUpload(syncId, dbPartsCollector)
    }
}