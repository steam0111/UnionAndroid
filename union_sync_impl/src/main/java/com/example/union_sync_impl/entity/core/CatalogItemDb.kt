package com.example.union_sync_impl.entity.core

import androidx.room.Entity

@Entity
abstract class CatalogItemDb(id: String) : SyncItemDb(id) {
    abstract var catalogItemName: String
}