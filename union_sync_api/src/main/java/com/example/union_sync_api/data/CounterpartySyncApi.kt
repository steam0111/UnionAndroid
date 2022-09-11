package com.example.union_sync_api.data

import com.example.union_sync_api.entity.CounterpartySyncEntity
import kotlinx.coroutines.flow.Flow

interface CounterpartySyncApi {
    suspend fun getCounterparties(
        textQuery: String? = null,
        offset: Long? = null,
        limit: Long? = null
    ): List<CounterpartySyncEntity>

    suspend fun getCounterpartiesFlow(textQuery: String? = null): Flow<List<CounterpartySyncEntity>>

    suspend fun getCounterpartyDetail(id: String): CounterpartySyncEntity
}