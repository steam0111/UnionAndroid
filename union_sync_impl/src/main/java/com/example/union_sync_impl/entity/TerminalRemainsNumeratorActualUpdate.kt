package com.example.union_sync_impl.entity

import androidx.room.Entity

@Entity
data class TerminalRemainsNumeratorActualUpdate(
    val id: String,
    val actualNumber: Int,
    val updateDate: Long = System.currentTimeMillis()
)