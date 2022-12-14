package com.example.union_sync_impl.entity

import androidx.room.Entity

@Entity
data class UpdateAccountingObjectImageMain(
    val isMainImage: Boolean,
    val id: String,
    val updateDate: Long = System.currentTimeMillis()
)