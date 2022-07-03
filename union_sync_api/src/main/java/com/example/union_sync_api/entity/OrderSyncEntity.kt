package com.example.union_sync_api.entity

data class OrderSyncEntity(
    val id: String,
    var catalogItemName: String,
    val number: String?,
    val summary: String?,
    val date: String?
)