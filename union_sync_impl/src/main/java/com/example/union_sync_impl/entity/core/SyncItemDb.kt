package com.example.union_sync_impl.entity.core

import androidx.room.Entity
import androidx.room.Ignore

@Entity
open class SyncItemDb(
    id: String,
    var updateDate: Long?,
    var userUpdated: String?,
    var userInserted: String?
) : DatabaseItemDb(id) {

    @Ignore
    var version: Long = 0
}