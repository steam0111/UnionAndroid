package com.example.union_sync_impl.data

import com.example.union_sync_api.data.ProducerSyncApi
import com.example.union_sync_api.entity.ProducerSyncEntity
import com.example.union_sync_impl.dao.ProducerDao
import com.example.union_sync_impl.data.mapper.toProducerDb
import com.example.union_sync_impl.data.mapper.toSyncEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.openapitools.client.custom_api.ProducerApi

class ProducerSyncApiImpl(
    private val producerDao: ProducerDao,
    private val producerApi: ProducerApi
) : ProducerSyncApi {
    override suspend fun getProducers(): Flow<List<ProducerSyncEntity>> {
        return flow {
            emit(producerDao.getAll().map { it.toSyncEntity() })

            val producerNetwork = producerApi.apiCatalogsProducerGet().list.orEmpty()
            producerNetwork.map { it.toProducerDb() }.let { dbProducer ->
                producerDao.insertAll(dbProducer)
            }

            emit(producerDao.getAll().map { it.toSyncEntity() })
        }.distinctUntilChanged().flowOn(Dispatchers.IO)
    }
}