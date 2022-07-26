package com.example.union_sync_impl.entity.core

import androidx.room.Entity
import androidx.room.Ignore

@Entity
open class SyncItemDb(id: String, var updateDate: Long?) : DatabaseItemDb(id) {

    @Ignore
    var version: Long = 0
}