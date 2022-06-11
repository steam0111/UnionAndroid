package com.example.union_sync_impl.entity.core

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
open class DatabaseItemDb {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
    var isDeleted: Boolean = false
}