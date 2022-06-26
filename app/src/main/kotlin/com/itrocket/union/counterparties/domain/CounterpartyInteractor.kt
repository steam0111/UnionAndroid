package com.itrocket.union.counterparties.domain

import kotlinx.coroutines.withContext
import com.itrocket.union.counterparties.domain.dependencies.CounterpartyRepository
import com.itrocket.core.base.CoreDispatchers

class CounterpartyInteractor(
    private val repository: CounterpartyRepository,
    private val coreDispatchers: CoreDispatchers
) {

    suspend fun getCounterparties(searchQuery: String = "") = withContext(coreDispatchers.io) {
        repository.getCounterparties(searchQuery)
    }
}