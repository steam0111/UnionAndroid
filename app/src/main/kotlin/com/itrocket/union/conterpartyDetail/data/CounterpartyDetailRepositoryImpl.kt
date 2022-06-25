package com.itrocket.union.conterpartyDetail.data

import com.example.union_sync_api.data.CounterpartySyncApi
import com.itrocket.union.conterpartyDetail.data.mapper.toCounterpartyDetailDomain
import com.itrocket.union.conterpartyDetail.domain.dependencies.CounterpartyDetailRepository
import com.itrocket.union.conterpartyDetail.domain.entity.CounterpartyDetailDomain

class CounterpartyDetailRepositoryImpl(
    private val syncApi: CounterpartySyncApi
) : CounterpartyDetailRepository {

    override suspend fun getCounterpartyDetail(id: String): CounterpartyDetailDomain {
        return syncApi.getCounterpartyDetail(id).toCounterpartyDetailDomain()
    }
}
