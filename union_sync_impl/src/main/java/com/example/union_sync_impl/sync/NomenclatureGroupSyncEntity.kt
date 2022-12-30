package com.example.union_sync_impl.sync

import com.example.union_sync_impl.R
import com.example.union_sync_impl.dao.SyncDao
import com.squareup.moshi.Moshi
import org.openapitools.client.custom_api.SyncControllerApi
import org.openapitools.client.models.NomenclatureGroupDtoV2

class NomenclatureGroupSyncEntity(
    syncControllerApi: SyncControllerApi,
    moshi: Moshi,
    private val dbSaver: suspend (List<NomenclatureGroupDtoV2>) -> Unit,
    syncDao: SyncDao
) : SyncEntity<NomenclatureGroupDtoV2>(syncControllerApi, moshi, syncDao) {

    override val id: String
        get() = "nomenclatureGroup"

    override val tableTitle: Int
        get() = R.string.nomenclature_group_table_name

    override val localTableName: String
        get() = "nomenclature_group"

    override suspend fun exportFromServer(syncId: String, exportPartId: String) {
        defaultGetAndSave<NomenclatureGroupDtoV2>(syncId, exportPartId)
    }

    override suspend fun saveInDb(objects: List<NomenclatureGroupDtoV2>) {
        dbSaver(objects.filter { !it.deleted })
    }
}