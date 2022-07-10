package com.example.union_sync_impl.entity

import androidx.room.Entity

@Entity
data class ReserveUpdate(
    val id: String,
    val count: Long,
    val locationId: String?,
    val updateDate: Long
)