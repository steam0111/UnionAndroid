package com.example.union_sync_impl.entity.core

import androidx.room.Entity

@Entity
abstract class CatalogItemDb : SyncItemDb() {
    abstract var catalogItemName: String
}