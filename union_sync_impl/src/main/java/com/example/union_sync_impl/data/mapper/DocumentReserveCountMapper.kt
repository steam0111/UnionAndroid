package com.example.union_sync_impl.data.mapper

import com.example.union_sync_api.entity.ReserveCountSyncEntity
import com.example.union_sync_impl.entity.DocumentReserveCount

fun ReserveCountSyncEntity.toDocumentReserveCount() = DocumentReserveCount(
    id = id,
    count = count,
    userUpdated = userUpdated
)