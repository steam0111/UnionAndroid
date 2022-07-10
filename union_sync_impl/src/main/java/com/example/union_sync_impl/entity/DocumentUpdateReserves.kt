package com.example.union_sync_impl.entity

import androidx.room.Entity

@Entity
class DocumentUpdateReserves(
    val id: String,
    val reservesIds: List<String>
)