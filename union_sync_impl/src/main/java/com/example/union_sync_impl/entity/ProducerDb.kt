package com.example.union_sync_impl.entity

import androidx.room.Entity
import com.example.union_sync_impl.entity.core.CatalogItemDb
import com.example.union_sync_impl.entity.core.SyncItemDb

@Entity(tableName = "producer")
class ProducerDb(
    id: String,
    override var catalogItemName: String,
    val name: String?,
    val code: String?,
    insertDate: Long?,
    updateDate: Long?,
    userInserted: String?,
    userUpdated: String?,
) : CatalogItemDb(
    id = id,
    insertDate = insertDate,
    updateDate = updateDate,
    userUpdated = userUpdated,
    userInserted = userInserted
)