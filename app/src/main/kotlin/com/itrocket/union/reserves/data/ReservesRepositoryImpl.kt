package com.itrocket.union.reserves.data

import com.example.union_sync_api.data.ReserveSyncApi
import com.itrocket.union.reserveDetail.data.mapper.map
import com.itrocket.union.reserves.data.mapper.map
import com.itrocket.union.reserves.domain.dependencies.ReservesRepository
import com.itrocket.union.reserves.domain.entity.ReservesDomain

class ReservesRepositoryImpl(
    private val syncApi: ReserveSyncApi
) : ReservesRepository {
    override suspend fun getReserves(
        organizationId: String?,
        molId: String?,
        structuralSubdivisionId: String?,
        nomenclatureId: String?,
        nomenclatureGroupId: String?,
        orderId: String?,
        receptionItemCategoryId: String?,
        textQuery: String?,
    ): List<ReservesDomain> {
        return syncApi.getAll(
            organizationId = organizationId,
            molId = molId,
            structuralSubdivisionId = structuralSubdivisionId,
            nomenclatureId = nomenclatureId,
            nomenclatureGroupId = nomenclatureGroupId,
            orderId = orderId,
            receptionItemCategoryId = receptionItemCategoryId,
            textQuery = textQuery
        ).map()
    }

    override suspend fun getReserveById(id: String): ReservesDomain {
        return syncApi.getById(id).map()
    }
}