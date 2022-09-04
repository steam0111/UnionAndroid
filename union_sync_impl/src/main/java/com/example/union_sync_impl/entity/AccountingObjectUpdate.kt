package com.example.union_sync_impl.entity

import androidx.room.Entity

@Entity
data class AccountingObjectUpdate(
    val id: String,
    val status: EnumDb?,
    val statusId: String?,
    val exploitingId: String?,
    val locationId: String?,
    val updateDate: Long?,
    val molId: String?,
    val userUpdated: String?,
    val structuralId: String?
)