package com.example.union_sync_impl.entity

import androidx.room.Entity

@Entity
data class AccountingObjectUpdate(
    val id: String,
    val status: AccountingObjectStatusDb?,
    val statusId: String?,
    val exploitingId: String?,
    val locationId: String?,
)