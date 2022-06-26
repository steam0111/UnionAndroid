package com.itrocket.union.producer.domain.dependencies

import com.itrocket.union.producer.domain.entity.ProducerDomain
import kotlinx.coroutines.flow.Flow

interface ProducerRepository {
    suspend fun getProducers(textQuery: String?): Flow<List<ProducerDomain>>
}