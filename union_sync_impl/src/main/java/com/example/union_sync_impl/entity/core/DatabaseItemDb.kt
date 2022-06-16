package com.example.union_sync_impl.entity.core

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity
open class DatabaseItemDb(
    @PrimaryKey(autoGenerate = false)
    val id: String
) {
    @Ignore
    var isDeleted: Boolean = false
}