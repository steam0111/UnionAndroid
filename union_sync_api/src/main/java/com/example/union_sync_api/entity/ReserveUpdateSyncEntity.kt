package com.example.union_sync_api.entity

import java.math.BigDecimal

data class ReserveUpdateSyncEntity(
    val id: String,
    val count: BigDecimal,
    val locationId: String?,
    val userUpdated: String?,
    val dateUpdate: Long
)