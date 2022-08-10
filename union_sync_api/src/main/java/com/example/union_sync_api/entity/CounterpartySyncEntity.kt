package com.example.union_sync_api.entity

class CounterpartySyncEntity(
    val id: String,
    val catalogItemName: String,
    val name: String?,
    val actualAddress: String?,
    val legalAddress: String?,
    val inn: String?,
    val kpp: String?,
    val code: String?
)