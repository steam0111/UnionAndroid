package com.example.union_sync_impl.data.mapper

import com.example.union_sync_api.entity.DocumentReserveCountSyncEntity
import com.example.union_sync_impl.entity.DocumentReserveCountDb

fun DocumentReserveCountDb.toSyncEntity() = DocumentReserveCountSyncEntity(
    id = id,
    count = count
)

fun DocumentReserveCountSyncEntity.toDocumentReserveCountDb() = DocumentReserveCountDb(
    id = id,
    count = count
)