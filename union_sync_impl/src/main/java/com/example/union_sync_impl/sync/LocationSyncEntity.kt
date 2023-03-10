package com.example.union_sync_impl.sync

import com.example.union_sync_impl.R
import com.example.union_sync_impl.dao.SyncDao
import com.squareup.moshi.Moshi
import org.openapitools.client.custom_api.SyncControllerApi
import org.openapitools.client.models.LocationDtoV2

class LocationSyncEntity(
    syncControllerApi: SyncControllerApi,
    moshi: Moshi,
    private val dbSaver: suspend (List<LocationDtoV2>) -> Unit,
    syncDao: SyncDao
) : SyncEntity<LocationDtoV2>(syncControllerApi, moshi, syncDao) {

    override val id: String
        get() = "location"

    override val tableTitle: Int
        get() = R.string.location_table_name

    override val localTableName: String
        get() = "location"

    override suspend fun exportFromServer(syncId: String, exportPartId: String) {
        defaultGetAndSave<LocationDtoV2>(syncId, exportPartId)
    }

    override suspend fun saveInDb(objects: List<LocationDtoV2>) {
        dbSaver(objects.filter { !it.deleted })
    }
}