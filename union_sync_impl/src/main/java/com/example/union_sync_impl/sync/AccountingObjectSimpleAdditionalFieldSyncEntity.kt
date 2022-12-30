package com.example.union_sync_impl.sync

import com.example.union_sync_impl.R
import com.example.union_sync_impl.dao.SyncDao
import com.squareup.moshi.Moshi
import org.openapitools.client.custom_api.SyncControllerApi
import org.openapitools.client.models.AccountingObjectSimpleAdditionalFieldValueDtoV2

class AccountingObjectSimpleAdditionalFieldSyncEntity(
    syncControllerApi: SyncControllerApi,
    moshi: Moshi,
    private val dbSaver: suspend (List<AccountingObjectSimpleAdditionalFieldValueDtoV2>) -> Unit,
    syncDao: SyncDao
) : SyncEntity<AccountingObjectSimpleAdditionalFieldValueDtoV2>(syncControllerApi, moshi, syncDao) {

    override val id: String
        get() = "accountingObjectSimpleAdditionalFieldValue"

    override val table: String
        get() = "accountingObjectSimpleAdditionalFieldValue"

    override val localTableName: String
        get() = "accountingObjectSimpleAdditionalFieldValue"

    override val tableTitle: Int
        get() = R.string.accounting_objects_additional_field_table_name

    override suspend fun exportFromServer(syncId: String, exportPartId: String) {
        defaultGetAndSave<AccountingObjectSimpleAdditionalFieldValueDtoV2>(syncId, exportPartId)
    }

    override suspend fun saveInDb(objects: List<AccountingObjectSimpleAdditionalFieldValueDtoV2>) {
        dbSaver(objects.filter { !it.deleted })
    }
}