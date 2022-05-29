package com.example.union_sync_impl.entity.core

import androidx.room.Entity

@Entity
open class SyncItem : DatabaseItem() {
    var dateInsert: Long = System.currentTimeMillis()
    var dateUpdate: Long = System.currentTimeMillis()
    var version: Long = 0
}