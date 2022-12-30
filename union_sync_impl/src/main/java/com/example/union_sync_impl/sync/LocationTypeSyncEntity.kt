package com.example.union_sync_impl.sync

import com.example.union_sync_impl.R
import com.example.union_sync_impl.dao.SyncDao
import com.example.union_sync_impl.dao.sqlRemoveDeletedItemsQuery
import com.squareup.moshi.Moshi
import org.openapitools.client.custom_api.SyncControllerApi
import org.openapitools.client.models.LocationsTypeDtoV2

class LocationTypeSyncEntity(
    syncControllerApi: SyncControllerApi,
    moshi: Moshi,
    private val dbSaver: suspend (List<LocationsTypeDtoV2>) -> Unit,
    syncDao: SyncDao
) : SyncEntity<LocationsTypeDtoV2>(syncControllerApi, moshi, syncDao) {

    override val id: String
        get() = "locationType"

    override val tableTitle: Int
        get() = R.string.location_type_table_name

    override val localTableName: String
        get() = "locationTypes"

    override suspend fun exportFromServer(syncId: String, exportPartId: String) {
        defaultGetAndSave<LocationsTypeDtoV2>(syncId, exportPartId)
    }

    override suspend fun saveInDb(objects: List<LocationsTypeDtoV2>) {
        dbSaver(objects.filter { !it.deleted })
    }
}