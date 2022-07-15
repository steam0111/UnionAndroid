package com.example.union_sync_impl.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "network_sync")
data class NetworkSyncDb(
    @PrimaryKey(autoGenerate = false)
    val id: Long = 0L,
    val lastSyncTime: Long? = null,
    val isSynced: Boolean = false
)