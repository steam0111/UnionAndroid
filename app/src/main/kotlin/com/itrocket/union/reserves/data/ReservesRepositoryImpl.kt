package com.itrocket.union.reserves.data

import com.example.union_sync_api.data.ReserveSyncApi
import com.example.union_sync_api.entity.ReserveShortSyncEntity
import com.example.union_sync_api.entity.ReserveSyncEntity
import com.example.union_sync_api.entity.ReserveUpdateSyncEntity
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
        reservesShorts: List<ReserveShortSyncEntity>?,
        selectedLocationIds: List<String?>
    ): List<ReservesDomain> {
        return syncApi.getAll(
            organizationId = params?.getOrganizationId(),
            molId = params?.getMolId(),
            structuralSubdivisionId = params?.getDepartmentId(),
            nomenclatureGroupId = params?.getNomenclatureGroupId(),
            receptionItemCategoryId = params?.getReceptionCategoryId(),
            reservesIds = reservesIds,
            textQuery = textQuery,
            locationIds = selectedLocationIds
        ).map()
    }

    override suspend fun getReservesFilterCount(params: List<ParamDomain>?,  selectedLocationIds: List<String?>): Long {
        return syncApi.getReservesFilterCount(
            organizationId = params?.getOrganizationId(),
            molId = params?.getMolId(),
            structuralSubdivisionId = params?.getDepartmentId(),
            nomenclatureGroupId = params?.getNomenclatureGroupId(),
            receptionItemCategoryId = params?.getReceptionCategoryId(),
            locationIds = selectedLocationIds
        )
    }

    override suspend fun getReserveById(id: String): ReservesDomain {
        return syncApi.getById(id).map()
    }

    override suspend fun getReservesByIds(reservesIds: List<String>): List<ReserveSyncEntity> {
        return syncApi.getAll(reservesIds = reservesIds)
    }

    override suspend fun getReservesByShorts(reservesShorts: List<ReserveShortSyncEntity>?): List<ReserveSyncEntity> {
        return syncApi.getAll(reservesShorts = reservesShorts)
    }

    override suspend fun updateReserves(reserves: List<ReserveUpdateSyncEntity>) {
        return syncApi.updateReserves(reserves)
    }

    override suspend fun insertAll(reserves: List<ReserveSyncEntity>) {
        return syncApi.insertAll(reserves)
    }
}