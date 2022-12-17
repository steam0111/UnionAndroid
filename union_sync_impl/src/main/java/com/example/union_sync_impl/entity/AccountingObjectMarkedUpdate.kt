package com.example.union_sync_impl.entity

import androidx.room.Entity

@Entity
class AccountingObjectMarkedUpdate(
    val id: String,
    val rfidValue: String,
    val marked: Boolean = true,
    val updateDate: Long = System.currentTimeMillis()
)