package com.itrocket.union.producerDetail.domain.dependencies

import com.itrocket.union.producerDetail.domain.entity.ProducerDetailDomain

interface ProducerDetailRepository {
    suspend fun getProducerById(id: String): ProducerDetailDomain
}