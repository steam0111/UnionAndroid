package com.example.union_sync_impl.sync

import com.example.union_sync_impl.R
import com.example.union_sync_impl.dao.SyncDao
import com.squareup.moshi.Moshi
import kotlinx.coroutines.flow.Flow
import org.openapitools.client.custom_api.SyncControllerApi
import org.openapitools.client.models.TransitDtoV2

class TransitSyncEntity(
    syncControllerApi: SyncControllerApi,
    moshi: Moshi,
    private val dbSaver: suspend (List<TransitDtoV2>) -> Unit,
    private val dbPartsCollector: Flow<List<TransitDtoV2>>,
    syncDao: SyncDao
) : SyncEntity<TransitDtoV2>(syncControllerApi, moshi, syncDao), UploadableSyncEntity {

    override val id: String
        get() = "Transit"

    override val table: String
        get() = "transit"

    override val tableTitle: Int
        get() = R.string.transit_table_name

    override suspend fun exportFromServer(syncId: String, exportPartId: String) {
        defaultGetAndSave<TransitDtoV2>(syncId, exportPartId)
    }

    override suspend fun saveInDb(objects: List<TransitDtoV2>) {
        dbSaver(objects)
    }

    override suspend fun upload(syncId: String) {
        defaultUpload(syncId, dbPartsCollector)
    }
}