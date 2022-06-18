package com.example.union_sync_api.data

import com.example.union_sync_api.entity.CounterpartySyncEntity
import kotlinx.coroutines.flow.Flow

interface CounterpartySyncApi {
    suspend fun getCounterparties(): Flow<List<CounterpartySyncEntity>>
}