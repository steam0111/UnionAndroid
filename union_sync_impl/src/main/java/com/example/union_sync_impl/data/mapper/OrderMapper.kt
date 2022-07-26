package com.example.union_sync_impl.data.mapper

import com.example.union_sync_api.entity.OrderSyncEntity
import com.example.union_sync_impl.entity.OrderDb
import org.openapitools.client.models.CustomOrderDto
import org.openapitools.client.models.OrderDtoV2

fun OrderDtoV2.toOrderDb(): OrderDb {
    return OrderDb(
        id = id,
        catalogItemName = catalogItemName.orEmpty(),
        number = number,
        summary = summary,
        date = date,
        updateDate = System.currentTimeMillis()
    )
}

fun OrderDb.toSyncEntity(): OrderSyncEntity {
    return OrderSyncEntity(
        id = id,
        catalogItemName = catalogItemName,
        number = number,
        summary = summary,
        date = date
    )
}