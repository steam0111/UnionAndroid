package com.itrocket.union.reserves.domain.dependencies

import com.itrocket.union.manual.ParamDomain
import com.itrocket.union.reserves.domain.entity.ReservesDomain

interface ReservesRepository {

    suspend fun getReserves(
        params: List<ParamDomain>? = null,
        textQuery: String? = null,
    ): List<ReservesDomain>

    suspend fun getReservesFilterCount(params: List<ParamDomain>? = null): Int

    suspend fun getReserveById(id: String): ReservesDomain
}