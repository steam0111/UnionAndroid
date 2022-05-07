package com.itrocket.union.reserves.domain

import kotlinx.coroutines.withContext
import com.itrocket.union.reserves.domain.dependencies.ReservesRepository
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.reserves.domain.entity.ReservesDomain

class ReservesInteractor(
    private val repository: ReservesRepository,
    private val coreDispatchers: CoreDispatchers
) {

    suspend fun getReserves(): List<ReservesDomain> = withContext(coreDispatchers.io) {
        repository.getReserves()
    }
}