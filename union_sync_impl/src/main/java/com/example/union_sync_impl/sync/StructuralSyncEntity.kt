package com.example.union_sync_impl.sync

import com.example.union_sync_impl.R
import com.example.union_sync_impl.dao.SyncDao
import com.squareup.moshi.Moshi
import org.openapitools.client.custom_api.SyncControllerApi
import org.openapitools.client.models.StructuralUnitDtoV2

class StructuralSyncEntity(
    syncControllerApi: SyncControllerApi,
    moshi: Moshi,
    private val dbSaver: suspend (List<StructuralUnitDtoV2>) -> Unit,
    syncDao: SyncDao
) : SyncEntity<StructuralUnitDtoV2>(syncControllerApi, moshi, syncDao) {

    override val id: String
        get() = "structuralUnit"

    override val tableTitle: Int
        get() = R.string.structural_table_name

    override val localTableName: String
        get() = "structural"

    override suspend fun exportFromServer(syncId: String, exportPartId: String) {
        defaultGetAndSave<StructuralUnitDtoV2>(syncId, exportPartId)
    }

    override suspend fun saveInDb(objects: List<StructuralUnitDtoV2>) {
        dbSaver(objects.filter { !it.deleted })
    }
}