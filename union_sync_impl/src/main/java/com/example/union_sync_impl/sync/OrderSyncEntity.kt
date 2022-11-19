package com.example.union_sync_impl.sync

import com.example.union_sync_impl.R
import com.squareup.moshi.Moshi
import org.openapitools.client.custom_api.SyncControllerApi
import org.openapitools.client.models.OrderDtoV2

class OrderSyncEntity (
    syncControllerApi: SyncControllerApi,
    moshi: Moshi,
    private val dbSaver: suspend (List<OrderDtoV2>) -> Unit
) : SyncEntity<OrderDtoV2>(syncControllerApi, moshi) {

    override val id: String
        get() = "order"

    override val tableTitle: Int
        get() = R.string.order_table_name

    override suspend fun exportFromServer(syncId: String, exportPartId: String) {
        defaultGetAndSave<OrderDtoV2>(syncId, exportPartId)
    }

    override suspend fun saveInDb(objects: List<OrderDtoV2>) {
        dbSaver(objects)
    }
}