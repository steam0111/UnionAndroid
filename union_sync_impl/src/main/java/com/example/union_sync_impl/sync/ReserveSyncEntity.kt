package com.example.union_sync_impl.sync

import com.example.union_sync_impl.R
import com.example.union_sync_impl.dao.SyncDao
import com.squareup.moshi.Moshi
import kotlinx.coroutines.flow.Flow
import org.openapitools.client.custom_api.SyncControllerApi
import org.openapitools.client.models.ActionRemainsRecordDtoV2
import org.openapitools.client.models.RemainsDtoV2

class ReserveSyncEntity(
    syncControllerApi: SyncControllerApi,
    moshi: Moshi,
    private val dbSaver: suspend (List<RemainsDtoV2>) -> Unit,
    private val dbPartsCollector: Flow<List<RemainsDtoV2>>,
    syncDao: SyncDao
) : SyncEntity<RemainsDtoV2>(syncControllerApi, moshi, syncDao), UploadableSyncEntity {

    override val id: String
        get() = "remains"

    override val tableTitle: Int
        get() = R.string.reserve_table_name

    override val localTableName: String
        get() = "reserves"

    override suspend fun exportFromServer(syncId: String, exportPartId: String) {
        defaultGetAndSave<RemainsDtoV2>(syncId, exportPartId)
    }

    override suspend fun saveInDb(objects: List<RemainsDtoV2>) {
        dbSaver(objects.filter { !it.deleted })
    }

    override suspend fun upload(syncId: String) {
        defaultUpload(syncId, dbPartsCollector)
    }
}