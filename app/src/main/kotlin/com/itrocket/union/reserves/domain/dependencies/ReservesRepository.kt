package com.itrocket.union.reserves.domain.dependencies

import com.itrocket.union.reserves.domain.entity.ReservesDomain

interface ReservesRepository {

    suspend fun getReserves(
        organizationId: String? = null,
        molId: String? = null,
        structuralSubdivisionId: String? = null,
        nomenclatureId: String? = null,
        nomenclatureGroupId: String? = null,
        orderId: String? = null,
        receptionItemCategoryId: String? = null,
        textQuery: String? = null,
    ): List<ReservesDomain>

    suspend fun getReserveById(id: String): ReservesDomain
}