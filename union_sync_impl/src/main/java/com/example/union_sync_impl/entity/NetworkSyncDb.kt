package com.example.union_sync_impl.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "network_sync")
data class NetworkSyncDb(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val isOrganizationSync: Boolean = false,
    val isLocationsSync: Boolean = false,
    val isAllSynced: Boolean = false
)