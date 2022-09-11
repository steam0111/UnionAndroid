package com.itrocket.union.producer.data

import com.example.union_sync_api.data.ProducerSyncApi
import com.itrocket.union.producer.data.mapper.map
import com.itrocket.union.producer.domain.dependencies.ProducerRepository
import com.itrocket.union.producer.domain.entity.ProducerDomain
import com.itrocket.core.base.CoreDispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class ProducerRepositoryImpl(
    private val coreDispatchers: CoreDispatchers,
    private val producerSyncApi: ProducerSyncApi
) : ProducerRepository {

    override suspend fun getProducers(
        textQuery: String?,
        offset: Long?,
        limit: Long?
    ): List<ProducerDomain> =
        withContext(coreDispatchers.io) {
            producerSyncApi.getProducers(
                textQuery,
                limit = limit,
                offset = offset
            ).map()
        }
}