package com.example.union_sync_impl.entity.core

import androidx.room.Entity

@Entity
abstract class CatalogItemDb(
    id: String,
    updateDate: Long?,
    userUpdated: String? = null,
    userInserted: String? = null
) : SyncItemDb(id, updateDate, userUpdated, userInserted) {
    abstract var catalogItemName: String
}