package com.itrocket.union.counterparties.data

import com.example.union_sync_api.data.CounterpartySyncApi
import com.itrocket.union.counterparties.data.mapper.map
import com.itrocket.union.counterparties.domain.dependencies.CounterpartyRepository
import com.itrocket.union.counterparties.domain.entity.CounterpartyDomain
import com.itrocket.core.base.CoreDispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class CounterpartyRepositoryImpl(
    private val coreDispatchers: CoreDispatchers,
    private val counterpartySyncApi: CounterpartySyncApi
) : CounterpartyRepository {

    override suspend fun getCounterparties(
        textQuery: String?,
        offset: Long?,
        limit: Long?
    ): List<CounterpartyDomain> = withContext(coreDispatchers.io) {
        counterpartySyncApi.getCounterparties(textQuery, offset = offset, limit = limit).map()
    }
}