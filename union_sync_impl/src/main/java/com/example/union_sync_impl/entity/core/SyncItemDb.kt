package com.example.union_sync_impl.entity.core

import androidx.room.Entity
import androidx.room.Ignore

@Entity
open class SyncItemDb(
    id: String,
    var insertDate: Long? = null,
    var updateDate: Long? = null,
    var userUpdated: String? = null,
    var userInserted: String? = null
) : DatabaseItemDb(id) {

    @Ignore
    var version: Long = 0
}