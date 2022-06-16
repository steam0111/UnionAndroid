package com.example.union_sync_impl.entity.core

import androidx.room.Entity
import androidx.room.Ignore

@Entity
open class SyncItemDb(id: String) : DatabaseItemDb(id) {
    @Ignore
    var dateInsert: Long = System.currentTimeMillis()

    @Ignore
    var dateUpdate: Long = System.currentTimeMillis()

    @Ignore
    var version: Long = 0
}