package com.example.union_sync_impl.sync

import com.example.union_sync_impl.R
import com.example.union_sync_impl.dao.SyncDao
import com.squareup.moshi.Moshi
import org.openapitools.client.custom_api.SyncControllerApi
import org.openapitools.client.models.StructuralUnitPathDtoV2

class StructuralPathSyncEntity(
    syncControllerApi: SyncControllerApi,
    moshi: Moshi,
    private val dbSaver: suspend (List<StructuralUnitPathDtoV2>) -> Unit,
    syncDao: SyncDao
) : SyncEntity<StructuralUnitPathDtoV2>(syncControllerApi, moshi, syncDao) {

    override val id: String
        get() = "structuralUnitPath"

    override val tableTitle: Int
        get() = R.string.structural_path_table_name

    override val localTableName: String
        get() = "structuralPath"

    override suspend fun exportFromServer(syncId: String, exportPartId: String) {
        defaultGetAndSave<StructuralUnitPathDtoV2>(syncId, exportPartId)
    }

    override suspend fun saveInDb(objects: List<StructuralUnitPathDtoV2>) {
        dbSaver(objects)
    }
}