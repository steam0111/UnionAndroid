package com.example.union_sync_impl.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
open class DatabaseItem {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}