package com.example.union_sync_impl.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "document_reserve_count")
class DocumentReserveCountDb(
    @PrimaryKey(autoGenerate = false)
    val id: String = "",
    val count: Long?
)