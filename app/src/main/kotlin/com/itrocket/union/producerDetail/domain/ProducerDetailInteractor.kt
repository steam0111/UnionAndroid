package com.itrocket.union.producerDetail.domain

import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.producerDetail.domain.dependencies.ProducerDetailRepository
import com.itrocket.union.producerDetail.domain.entity.ProducerDetailDomain
import kotlinx.coroutines.withContext

class ProducerDetailInteractor(
    private val repository: ProducerDetailRepository,
    private val coreDispatchers: CoreDispatchers
) {
    suspend fun getProducerDetail(id: String): ProducerDetailDomain =
        withContext(coreDispatchers.io) {
            repository.getProducerById(id)
        }
}