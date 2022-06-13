package com.example.union_sync_impl.entity.core

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
open class DatabaseItemDb(
    @PrimaryKey(autoGenerate = false)
    val id: String
) {
    var isDeleted: Boolean = false
}