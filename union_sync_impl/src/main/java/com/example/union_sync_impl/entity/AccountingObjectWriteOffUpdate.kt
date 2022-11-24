package com.example.union_sync_impl.entity

import androidx.room.Entity

@Entity
class AccountingObjectWriteOffUpdate(
    val id: String,
    val forWriteOff: Boolean,
    val updateDate: Long = System.currentTimeMillis()
)