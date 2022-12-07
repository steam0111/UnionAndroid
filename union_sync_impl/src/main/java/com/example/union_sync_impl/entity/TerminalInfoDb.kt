package com.example.union_sync_impl.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "terminal_info")
data class TerminalInfoDb(
    @PrimaryKey(autoGenerate = false)
    val id: Long = 0L,
    val terminalPrefix: String
)