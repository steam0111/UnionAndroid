package com.example.union_sync_api.entity

import java.math.BigDecimal

data class ReserveCountSyncEntity(
    val id: String,
    val count: BigDecimal?,
    val userUpdated: String?
)