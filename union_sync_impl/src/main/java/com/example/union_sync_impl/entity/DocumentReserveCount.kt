package com.example.union_sync_impl.entity

import androidx.room.Entity
import java.math.BigDecimal

@Entity
data class DocumentReserveCount(
    val id: String,
    val count: BigDecimal?,
    val userUpdated: String?
)