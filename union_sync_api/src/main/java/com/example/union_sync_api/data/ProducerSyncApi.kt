package com.example.union_sync_api.data

import com.example.union_sync_api.entity.ProducerSyncEntity
import kotlinx.coroutines.flow.Flow

interface ProducerSyncApi {
    suspend fun getProducers(): Flow<List<ProducerSyncEntity>>

    suspend fun getProducerDetail(id: String): ProducerSyncEntity
}