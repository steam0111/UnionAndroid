package com.example.union_sync_impl.sync

import com.example.union_sync_impl.R
import com.example.union_sync_impl.dao.SyncDao
import com.squareup.moshi.Moshi
import kotlinx.coroutines.flow.Flow
import org.openapitools.client.custom_api.SyncControllerApi
import org.openapitools.client.models.AccountingObjectUnionImageDtoV2

class AccountingObjectUnionImageSyncEntity(
    syncControllerApi: SyncControllerApi,
    moshi: Moshi,
    private val dbSaver: suspend (List<AccountingObjectUnionImageDtoV2>) -> Unit,
    private val dbPartsCollector: Flow<List<AccountingObjectUnionImageDtoV2>>,
    syncDao: SyncDao
) : SyncEntity<AccountingObjectUnionImageDtoV2>(syncControllerApi, moshi, syncDao),
    UploadableSyncEntity {

    override val id: String
        get() = "accountingObjectUnionImage"

    override val tableTitle: Int
        get() = R.string.accounting_object_image_table_name

    override val localTableName: String
        get() = "accounting_object_union_image"

    override suspend fun exportFromServer(syncId: String, exportPartId: String) {
        defaultGetAndSave<AccountingObjectUnionImageDtoV2>(syncId, exportPartId)
    }

    override suspend fun saveInDb(objects: List<AccountingObjectUnionImageDtoV2>) {
        dbSaver(objects.filter { !it.deleted })
    }

    override suspend fun upload(syncId: String) {
        defaultUpload(syncId, dbPartsCollector)
    }
}