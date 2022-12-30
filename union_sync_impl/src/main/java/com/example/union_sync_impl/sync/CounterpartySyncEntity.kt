package com.example.union_sync_impl.sync

import com.example.union_sync_impl.R
import com.example.union_sync_impl.dao.SyncDao
import com.squareup.moshi.Moshi
import org.openapitools.client.custom_api.SyncControllerApi
import org.openapitools.client.models.CounterpartyDtoV2

class CounterpartySyncEntity(
    syncControllerApi: SyncControllerApi,
    moshi: Moshi,
    private val dbSaver: suspend (List<CounterpartyDtoV2>) -> Unit,
    syncDao: SyncDao
) : SyncEntity<CounterpartyDtoV2>(syncControllerApi, moshi, syncDao) {

    override val id: String
        get() = "counterparty"

    override val tableTitle: Int
        get() = R.string.counterparty_table_name

    override val localTableName: String
        get() = "counterparty"

    override suspend fun exportFromServer(syncId: String, exportPartId: String) {
        defaultGetAndSave<CounterpartyDtoV2>(syncId, exportPartId)
    }

    override suspend fun saveInDb(objects: List<CounterpartyDtoV2>) {
        dbSaver(objects.filter { !it.deleted })
    }
}