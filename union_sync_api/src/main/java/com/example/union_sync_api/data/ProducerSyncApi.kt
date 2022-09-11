package com.example.union_sync_api.data

import com.example.union_sync_api.entity.ProducerSyncEntity
import kotlinx.coroutines.flow.Flow

interface ProducerSyncApi {
    suspend fun getProducersFlow(textQuery: String? = null): Flow<List<ProducerSyncEntity>>

    suspend fun getProducers(
        textQuery: String? = null,
        offset: Long? = null,
        limit: Long? = null
    ): List<ProducerSyncEntity>

    suspend fun getProducerDetail(id: String): ProducerSyncEntity
}