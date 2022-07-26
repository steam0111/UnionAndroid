package com.example.union_sync_impl.data

import com.example.union_sync_api.data.ReserveSyncApi
import com.example.union_sync_api.entity.ReserveDetailSyncEntity
import com.example.union_sync_api.entity.ReserveShortSyncEntity
import com.example.union_sync_api.entity.ReserveSyncEntity
import com.example.union_sync_impl.dao.ReserveDao
import com.example.union_sync_api.entity.ReserveUpdateSyncEntity
import com.example.union_sync_impl.dao.sqlReserveQuery
import com.example.union_sync_impl.data.mapper.toDetailSyncEntity
import com.example.union_sync_impl.data.mapper.toSyncEntity
import com.example.union_sync_impl.data.mapper.toLocationSyncEntity
import com.example.union_sync_impl.data.mapper.toReserveDb
import com.example.union_sync_impl.data.mapper.toReserveUpdate

class ReserveSyncApiImpl(
    private val reserveDao: ReserveDao
) : ReserveSyncApi {
    override suspend fun getAll(
        organizationId: String?,
        molId: String?,
        structuralSubdivisionId: String?,
        nomenclatureGroupId: String?,
        orderId: String?,
        receptionItemCategoryId: String?,
        reservesIds: List<String>?,
        reservesShorts: List<ReserveShortSyncEntity>?,
        textQuery: String?,
        locationIds: List<String?>?
    ): List<ReserveSyncEntity> {
        return reserveDao.getAll(
            sqlReserveQuery(
                organizationId = organizationId,
                molId = molId,
                structuralSubdivisionId = structuralSubdivisionId,
                nomenclatureGroupId = nomenclatureGroupId,
                orderId = orderId,
                receptionItemCategoryId = receptionItemCategoryId,
                reservesIds = reservesIds,
                textQuery = textQuery,
                isFilterCount = false,
                locationIds = locationIds,
                reservesShorts = reservesShorts
            )
        ).map { it.toSyncEntity() }
    }

    override suspend fun getById(id: String): ReserveDetailSyncEntity {
        val fullReserve = reserveDao.getById(id)
        return fullReserve.toDetailSyncEntity()
    }

    override suspend fun getReservesFilterCount(
        organizationId: String?,
        molId: String?,
        structuralSubdivisionId: String?,
        nomenclatureGroupId: String?,
        orderId: String?,
        receptionItemCategoryId: String?,
        locationIds: List<String?>?
    ): Long {
        return reserveDao.getFilterCount(
            sqlReserveQuery(
                organizationId = organizationId,
                molId = molId,
                structuralSubdivisionId = structuralSubdivisionId,
                nomenclatureGroupId = nomenclatureGroupId,
                orderId = orderId,
                receptionItemCategoryId = receptionItemCategoryId,
                isFilterCount = true,
                locationIds = locationIds
            )
        )
    }

    override suspend fun updateReserves(reserves: List<ReserveUpdateSyncEntity>) {
        reserveDao.update(reserves.map { it.toReserveUpdate() })
    }

    override suspend fun insertAll(reserves: List<ReserveSyncEntity>) {
        reserveDao.insertAll(reserves.map { it.toReserveDb() })
    }
}