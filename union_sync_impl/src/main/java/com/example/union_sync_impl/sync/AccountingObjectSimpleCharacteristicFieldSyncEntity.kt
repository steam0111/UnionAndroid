package com.example.union_sync_impl.sync

import com.example.union_sync_impl.R
import com.example.union_sync_impl.dao.SyncDao
import com.squareup.moshi.Moshi
import org.openapitools.client.custom_api.SyncControllerApi
import org.openapitools.client.models.AccountingObjectCharacteristicValueDtoV2

class AccountingObjectSimpleCharacteristicFieldSyncEntity(
    syncControllerApi: SyncControllerApi,
    moshi: Moshi,
    private val dbSaver: suspend (List<AccountingObjectCharacteristicValueDtoV2>) -> Unit,
    syncDao: SyncDao
) : SyncEntity<AccountingObjectCharacteristicValueDtoV2>(syncControllerApi, moshi, syncDao) {

    override val id: String
        get() = "accountingObjectCharacteristicValue"

    override val table: String
        get() = "accountingObjectCharacteristicValue"

    override val tableTitle: Int
        get() = R.string.accounting_objects_characteristics_field_table_name

    override val localTableName: String
        get() = "accountingObjectsSimpleCharacteristicValue"

    override suspend fun exportFromServer(syncId: String, exportPartId: String) {
        defaultGetAndSave<AccountingObjectCharacteristicValueDtoV2>(syncId, exportPartId)
    }

    override suspend fun saveInDb(objects: List<AccountingObjectCharacteristicValueDtoV2>) {
        dbSaver(objects.filter { !it.deleted })
    }
}