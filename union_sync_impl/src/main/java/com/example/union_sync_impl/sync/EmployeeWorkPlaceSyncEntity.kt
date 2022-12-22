package com.example.union_sync_impl.sync

import com.example.union_sync_impl.R
import com.squareup.moshi.Moshi
import org.openapitools.client.custom_api.SyncControllerApi
import org.openapitools.client.models.EmployeeLocationDto

class EmployeeWorkPlaceSyncEntity(
    syncControllerApi: SyncControllerApi,
    moshi: Moshi,
    private val dbSaver: suspend (List<EmployeeLocationDto>) -> Unit
) : SyncEntity<EmployeeLocationDto>(syncControllerApi, moshi) {
    override val id: String
        get() = "employeeWorkplaceRecord"
    override val tableTitle: Int
        get() = R.string.employee_work_places

    override val table: String
        get() = "employeeWorkplaceRecord"

    override suspend fun exportFromServer(syncId: String, exportPartId: String) {
        defaultGetAndSave<EmployeeLocationDto>(syncId, exportPartId)
    }

    override suspend fun saveInDb(objects: List<EmployeeLocationDto>) {
        dbSaver(objects)
    }
}