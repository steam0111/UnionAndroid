package com.itrocket.union.counterparties.domain.dependencies

import com.itrocket.union.counterparties.domain.entity.CounterpartyDomain
import kotlinx.coroutines.flow.Flow

interface CounterpartyRepository {
    suspend fun getCounterparties(): Flow<List<CounterpartyDomain>>
}