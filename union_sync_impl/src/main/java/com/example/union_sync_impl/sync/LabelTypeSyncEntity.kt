package com.example.union_sync_impl.sync

import com.example.union_sync_impl.R
import com.squareup.moshi.Moshi
import org.openapitools.client.custom_api.SyncControllerApi
import org.openapitools.client.models.LabelTypeDtoV2

class LabelTypeSyncEntity(
    syncControllerApi: SyncControllerApi,
    moshi: Moshi,
    private val dbSaver: suspend (List<LabelTypeDtoV2>) -> Unit,
) : SyncEntity<LabelTypeDtoV2>(syncControllerApi, moshi) {

    override val id: String
        get() = "labelType"

    override val tableTitle: Int
        get() = R.string.entity_label_type_table_name

    override suspend fun exportFromServer(syncId: String, exportPartId: String) {
        defaultGetAndSave<LabelTypeDtoV2>(syncId, exportPartId)
    }

    override suspend fun saveInDb(objects: List<LabelTypeDtoV2>) {
        dbSaver(objects)
    }
}