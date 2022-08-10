package com.example.union_sync_impl.entity.core

import androidx.room.Entity

@Entity
abstract class CatalogItemDb(
    id: String,
    insertDate: Long? = null,
    updateDate: Long? = null,
    userUpdated: String? = null,
    userInserted: String? = null,
) : SyncItemDb(id, insertDate, updateDate, userUpdated, userInserted) {
    abstract var catalogItemName: String
}