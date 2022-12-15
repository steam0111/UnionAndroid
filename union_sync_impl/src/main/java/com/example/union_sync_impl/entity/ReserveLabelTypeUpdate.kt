package com.example.union_sync_impl.entity

import androidx.room.Entity

@Entity
class ReserveLabelTypeUpdate(
    val id: String,
    val labelTypeId: String,
    val updateDate: Long = System.currentTimeMillis()
)