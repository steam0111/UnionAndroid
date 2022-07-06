package com.itrocket.union.producerDetail.data

import com.example.union_sync_api.data.ProducerSyncApi
import com.itrocket.union.producerDetail.data.mapper.toDomain
import com.itrocket.union.producerDetail.domain.dependencies.ProducerDetailRepository
import com.itrocket.union.producerDetail.domain.entity.ProducerDetailDomain

class ProducerDetailRepositoryImpl(
    private val syncApi: ProducerSyncApi
) : ProducerDetailRepository {
    override suspend fun getProducerById(id: String): ProducerDetailDomain {
        return syncApi.getProducerDetail(id).toDomain()
    }
}