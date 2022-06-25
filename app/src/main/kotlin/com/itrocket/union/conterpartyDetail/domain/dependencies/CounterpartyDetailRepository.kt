package com.itrocket.union.conterpartyDetail.domain.dependencies

import com.itrocket.union.conterpartyDetail.domain.entity.CounterpartyDetailDomain

interface CounterpartyDetailRepository {
    suspend fun getCounterpartyDetail(id: String): CounterpartyDetailDomain
}