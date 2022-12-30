package com.example.union_sync_impl.sync

import com.example.union_sync_impl.R
import com.example.union_sync_impl.dao.SyncDao
import com.squareup.moshi.Moshi
import org.openapitools.client.custom_api.SyncControllerApi
import org.openapitools.client.models.LocationPathDto

class LocationPathSyncEntity(
    syncControllerApi: SyncControllerApi,
    moshi: Moshi,
    private val dbSaver: suspend (List<LocationPathDto>) -> Unit,
    syncDao: SyncDao
) : SyncEntity<LocationPathDto>(syncControllerApi, moshi, syncDao) {

    override val id: String
        get() = "locationPath"

    override val tableTitle: Int
        get() = R.string.location_path_table_name

    override val localTableName: String
        get() = "locationPath"

    override suspend fun exportFromServer(syncId: String, exportPartId: String) {
        defaultGetAndSave<LocationPathDto>(syncId, exportPartId)
    }

    override suspend fun saveInDb(objects: List<LocationPathDto>) {
        dbSaver(objects)
    }
}