package com.itrocket.union.reserves.data

import com.example.union_sync_api.data.ReserveSyncApi
import com.example.union_sync_api.entity.ReserveShortSyncEntity
import com.example.union_sync_api.entity.ReserveSyncEntity
import com.example.union_sync_api.entity.ReserveUpdateSyncEntity
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.manual.ParamDomain
import com.itrocket.union.manual.getMolId
import com.itrocket.union.manual.getNomenclatureId
import com.itrocket.union.manual.getNomenclatureGroupId
import com.itrocket.union.manual.getReceptionCategoryId
import com.itrocket.union.reserveDetail.data.mapper.map
import com.itrocket.union.reserves.data.mapper.map
import com.itrocket.union.reserves.domain.dependencies.ReservesRepository
import com.itrocket.union.reserves.domain.entity.ReservesDomain
import kotlinx.coroutines.withContext

class ReservesRepositoryImpl(
    private val coreDispatchers: CoreDispatchers,
    private val syncApi: ReserveSyncApi
) : ReservesRepository {
    override suspend fun getReserves(
        params: List<ParamDomain>?,
        reservesIds: List<String>?,
        textQuery: String?,
        reservesShorts: List<ReserveShortSyncEntity>?,
        selectedLocationIds: List<String?>?,
        structuralIds: List<String?>?,
        offset: Long?,
        limit: Long?,
        hideZeroReserves: Boolean
    ): List<ReservesDomain> =
        withContext(coreDispatchers.io) {
            syncApi.getAll(
                structuralIds = structuralIds,
                molId = params?.getMolId(),
                nomenclatureGroupId = params?.getNomenclatureGroupId(),
                receptionItemCategoryId = params?.getReceptionCategoryId(),
                reservesIds = reservesIds,
                textQuery = textQuery,
                locationIds = selectedLocationIds,
                offset = offset,
                limit = limit,
                hideZeroReserves = hideZeroReserves,
                nomenclatureId = params?.getNomenclatureId()
            ).map()
        }

    override suspend fun getReservesFilterCount(
        params: List<ParamDomain>?,
        selectedLocationIds: List<String?>?,
        structuralIds: List<String?>?,
        hideZeroReserves: Boolean
    ): Long {
        return syncApi.getReservesFilterCount(
            structuralIds = structuralIds,
            molId = params?.getMolId(),
            nomenclatureGroupId = params?.getNomenclatureGroupId(),
            receptionItemCategoryId = params?.getReceptionCategoryId(),
            locationIds = selectedLocationIds,
            hideZeroReserves = hideZeroReserves,
            nomenclatureId = params?.getNomenclatureId()
        )
    }

    override suspend fun getReserveById(id: String): ReservesDomain {
        return syncApi.getById(id).map()
    }

    override suspend fun getReservesByIds(reservesIds: List<String>): List<ReserveSyncEntity> {
        return syncApi.getAll(reservesIds = reservesIds, hideZeroReserves = false)
    }

    override suspend fun getReservesByShorts(reservesShorts: List<ReserveShortSyncEntity>?): List<ReserveSyncEntity> {
        return syncApi.getAll(reservesShorts = reservesShorts, hideZeroReserves = false)
    }

    override suspend fun updateReserves(reserves: List<ReserveUpdateSyncEntity>) {
        return syncApi.updateReserves(reserves)
    }

    override suspend fun insertAll(reserves: List<ReserveSyncEntity>) {
        return syncApi.insertAll(reserves)
    }
}