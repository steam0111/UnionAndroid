package com.example.union_sync_impl.entity

import androidx.room.Entity

@Entity
data class DocumentReserveCount(
    val id: String,
    val count: Long?,
    val userUpdated: String?
)