package com.example.union_sync_impl.data

import com.example.union_sync_api.data.ProducerSyncApi
import com.example.union_sync_api.entity.ProducerSyncEntity
import com.example.union_sync_impl.dao.ProducerDao
import com.example.union_sync_impl.data.mapper.toSyncEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class ProducerSyncApiImpl(
    private val producerDao: ProducerDao
) : ProducerSyncApi {
    override suspend fun getProducers(textQuery: String?): Flow<List<ProducerSyncEntity>> {
        return flow {
            emit(producerDao.getAll().map { it.toSyncEntity() })
        }.distinctUntilChanged().flowOn(Dispatchers.IO)
    }

    override suspend fun getProducerDetail(id: String): ProducerSyncEntity {
        return producerDao.getById(id).toSyncEntity()
    }
}