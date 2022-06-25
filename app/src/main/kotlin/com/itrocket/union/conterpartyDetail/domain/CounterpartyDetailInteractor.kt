package com.itrocket.union.conterpartyDetail.domain

import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.conterpartyDetail.domain.dependencies.CounterpartyDetailRepository
import com.itrocket.union.conterpartyDetail.domain.entity.CounterpartyDetailDomain
import kotlinx.coroutines.withContext

class CounterpartyDetailInteractor(
    private val repository: CounterpartyDetailRepository,
    private val coreDispatchers: CoreDispatchers
) {

    suspend fun getCounterpartyDetail(id: String): CounterpartyDetailDomain =
        withContext(coreDispatchers.io) {
            repository.getCounterpartyDetail(id)
        }
}