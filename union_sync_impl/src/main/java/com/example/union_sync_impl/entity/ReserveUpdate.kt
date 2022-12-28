package com.example.union_sync_impl.entity

import androidx.room.Entity
import java.math.BigDecimal

@Entity
data class ReserveUpdate(
    val id: String,
    val count: BigDecimal,
    val locationId: String?,
    val updateDate: Long?,
    val userUpdated: String?
)