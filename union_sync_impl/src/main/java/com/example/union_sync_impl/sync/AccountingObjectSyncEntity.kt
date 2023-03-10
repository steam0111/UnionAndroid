package com.example.union_sync_impl.sync

import com.example.union_sync_impl.R
import com.example.union_sync_impl.dao.SyncDao
import com.squareup.moshi.Moshi
import kotlinx.coroutines.flow.Flow
import org.openapitools.client.custom_api.SyncControllerApi
import org.openapitools.client.models.AccountingObjectDtoV2

class AccountingObjectSyncEntity(
    syncControllerApi: SyncControllerApi,
    moshi: Moshi,
    private val dbSaver: suspend (List<AccountingObjectDtoV2>) -> Unit,
    private val dbPartsCollector: Flow<List<AccountingObjectDtoV2>>,
    syncDao: SyncDao
) : SyncEntity<AccountingObjectDtoV2>(syncControllerApi, moshi, syncDao), UploadableSyncEntity {

    override val id: String
        get() = "accountingObject"

    override val tableTitle: Int
        get() = R.string.accounting_object_table_name

    override val localTableName: String
        get() = "accounting_objects"

    override suspend fun exportFromServer(syncId: String, exportPartId: String) {
        defaultGetAndSave<AccountingObjectDtoV2>(syncId, exportPartId)
    }

    override suspend fun saveInDb(objects: List<AccountingObjectDtoV2>) {
        dbSaver(objects.filter { !it.deleted })
    }

    override suspend fun upload(syncId: String) {
        defaultUpload(syncId, dbPartsCollector)
    }
}