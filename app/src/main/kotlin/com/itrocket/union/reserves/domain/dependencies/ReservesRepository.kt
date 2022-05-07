package com.itrocket.union.reserves.domain.dependencies

import com.itrocket.union.reserves.domain.entity.ReservesDomain

interface ReservesRepository {

    suspend fun getReserves(): List<ReservesDomain>
}