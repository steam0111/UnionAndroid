package com.itrocket.union.reserveDetail.domain

import kotlinx.coroutines.withContext
import com.itrocket.union.reserveDetail.domain.dependencies.ReserveDetailRepository
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.reserves.domain.dependencies.ReservesRepository

class ReserveDetailInteractor(
    private val repository: ReservesRepository,
    private val coreDispatchers: CoreDispatchers
) {
    suspend fun getReserveById(id: String) = withContext(coreDispatchers.io) {
        repository.getReserveById(id)
    }
}