package com.itrocket.union.reserves.data

import com.example.union_sync_api.data.ReserveSyncApi
import com.itrocket.union.manual.ParamDomain
import com.itrocket.union.manual.getDepartmentId
import com.itrocket.union.manual.getMolId
import com.itrocket.union.manual.getNomenclatureGroupId
import com.itrocket.union.manual.getOrganizationId
import com.itrocket.union.manual.getReceptionCategoryId
import com.itrocket.union.reserveDetail.data.mapper.map
import com.itrocket.union.reserves.data.mapper.map
import com.itrocket.union.reserves.domain.dependencies.ReservesRepository
import com.itrocket.union.reserves.domain.entity.ReservesDomain

class ReservesRepositoryImpl(
    private val syncApi: ReserveSyncApi
) : ReservesRepository {
    override suspend fun getReserves(
        params: List<ParamDomain>?,
        reservesIds: List<String>?,
        textQuery: String?,
    ): List<ReservesDomain> {
        return syncApi.getAll(
            organizationId = params?.getOrganizationId(),
            molId = params?.getMolId(),
            structuralSubdivisionId = params?.getDepartmentId(),
            nomenclatureGroupId = params?.getNomenclatureGroupId(),
            receptionItemCategoryId = params?.getReceptionCategoryId(),
            reservesIds = reservesIds,
            textQuery = textQuery
        ).map()
    }

    override suspend fun getReservesFilterCount(params: List<ParamDomain>?): Int {
        return syncApi.getReservesFilterCount(
            organizationId = params?.getOrganizationId(),
            molId = params?.getMolId(),
            structuralSubdivisionId = params?.getDepartmentId(),
            nomenclatureGroupId = params?.getNomenclatureGroupId(),
            receptionItemCategoryId = params?.getReceptionCategoryId(),
        )
    }

    override suspend fun getReserveById(id: String): ReservesDomain {
        return syncApi.getById(id).map()
    }
}