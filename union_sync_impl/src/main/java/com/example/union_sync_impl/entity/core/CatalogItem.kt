package com.example.union_sync_impl.entity.core

import androidx.room.Entity

@Entity
abstract class CatalogItem : SyncItem() {
    abstract var catalogItemName: String
}