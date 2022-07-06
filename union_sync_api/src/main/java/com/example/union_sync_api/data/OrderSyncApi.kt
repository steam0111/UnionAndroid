package com.example.union_sync_api.data

import com.example.union_sync_api.entity.OrderSyncEntity

interface OrderSyncApi {
    suspend fun getAll(textQuery: String? = null): List<OrderSyncEntity>
}