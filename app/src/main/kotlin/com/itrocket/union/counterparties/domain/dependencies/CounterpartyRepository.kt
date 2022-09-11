package com.itrocket.union.counterparties.domain.dependencies

import com.itrocket.union.counterparties.domain.entity.CounterpartyDomain
import kotlinx.coroutines.flow.Flow

interface CounterpartyRepository {
    suspend fun getCounterparties(
        textQuery: String?,
        offset: Long?,
        limit: Long?
    ): List<CounterpartyDomain>
}