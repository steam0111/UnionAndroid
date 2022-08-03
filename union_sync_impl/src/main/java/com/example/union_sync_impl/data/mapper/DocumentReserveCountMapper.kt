package com.example.union_sync_impl.data.mapper

import com.example.union_sync_api.entity.DocumentReserveCountSyncEntity
import com.example.union_sync_impl.entity.DocumentReserveCount

fun DocumentReserveCountSyncEntity.toDocumentReserveCount() = DocumentReserveCount(
    id = id,
    count = count,
    userUpdated = userUpdated
)