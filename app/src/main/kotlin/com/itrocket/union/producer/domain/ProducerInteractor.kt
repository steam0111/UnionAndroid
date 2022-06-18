package com.itrocket.union.producer.domain

import kotlinx.coroutines.withContext
import com.itrocket.union.producer.domain.dependencies.ProducerRepository
import com.itrocket.core.base.CoreDispatchers

class ProducerInteractor(
    private val repository: ProducerRepository,
    private val coreDispatchers: CoreDispatchers
) {

    suspend fun getProducers() = withContext(coreDispatchers.io){
        repository.getProducers()
    }
}