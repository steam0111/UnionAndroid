package com.example.union_sync_impl.sync

import com.example.union_sync_impl.R
import com.example.union_sync_impl.dao.SyncDao
import com.squareup.moshi.Moshi
import org.openapitools.client.custom_api.SyncControllerApi
import org.openapitools.client.models.NomenclatureDtoV2

class NomenclatureSyncEntity(
    syncControllerApi: SyncControllerApi,
    moshi: Moshi,
    private val dbSaver: suspend (List<NomenclatureDtoV2>) -> Unit,
    syncDao: SyncDao
) : SyncEntity<NomenclatureDtoV2>(syncControllerApi, moshi, syncDao) {

    override val id: String
        get() = "nomenclature"

    override val tableTitle: Int
        get() = R.string.nomenclature_table_name

    override val localTableName: String
        get() = "nomenclature"

    override suspend fun exportFromServer(syncId: String, exportPartId: String) {
        defaultGetAndSave<NomenclatureDtoV2>(syncId, exportPartId)
    }

    override suspend fun saveInDb(objects: List<NomenclatureDtoV2>) {
        dbSaver(objects.filter { !it.deleted })
    }
}