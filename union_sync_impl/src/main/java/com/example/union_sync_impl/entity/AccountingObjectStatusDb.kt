package com.example.union_sync_impl.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "statuses")
data class AccountingObjectStatusDb(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val name: String?
)