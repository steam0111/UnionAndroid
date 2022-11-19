package com.example.union_sync_impl.sync

import com.example.union_sync_impl.R
import com.squareup.moshi.Moshi
import org.openapitools.client.custom_api.SyncControllerApi
import org.openapitools.client.models.ProducerDtoV2

class ProducerSyncEntity (
    syncControllerApi: SyncControllerApi,
    moshi: Moshi,
    private val dbSaver: suspend (List<ProducerDtoV2>) -> Unit
) : SyncEntity<ProducerDtoV2>(syncControllerApi, moshi) {

    override val id: String
        get() = "producer"

    override val tableTitle: Int
        get() = R.string.producer_table_name

    override suspend fun exportFromServer(syncId: String, exportPartId: String) {
        defaultGetAndSave<ProducerDtoV2>(syncId, exportPartId)
    }

    override suspend fun saveInDb(objects: List<ProducerDtoV2>) {
        dbSaver(objects)
    }
}