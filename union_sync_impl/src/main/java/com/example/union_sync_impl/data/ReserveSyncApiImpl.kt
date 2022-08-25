package com.example.union_sync_impl.data

import com.example.union_sync_api.data.LocationSyncApi
import com.example.union_sync_api.data.ReserveSyncApi
import com.example.union_sync_api.data.StructuralSyncApi
import com.example.union_sync_api.entity.ReserveDetailSyncEntity
import com.example.union_sync_api.entity.ReserveShortSyncEntity
import com.example.union_sync_api.entity.ReserveSyncEntity
import com.example.union_sync_impl.dao.ReserveDao
import com.example.union_sync_api.entity.ReserveUpdateSyncEntity
import com.example.union_sync_impl.dao.LocationDao
import com.example.union_sync_impl.dao.StructuralDao
import com.example.union_sync_impl.dao.sqlReserveQuery
import com.example.union_sync_impl.data.mapper.toDetailSyncEntity
import com.example.union_sync_impl.data.mapper.toSyncEntity
import com.example.union_sync_impl.data.mapper.toLocationSyncEntity
import com.example.union_sync_impl.data.mapper.toReserveDb
import com.example.union_sync_impl.data.mapper.toReserveUpdate
import com.example.union_sync_impl.data.mapper.toStructuralSyncEntity

class ReserveSyncApiImpl(
    private val reserveDao: ReserveDao,
    private val structuralSyncApi: StructuralSyncApi,
    private val locationSyncApi: LocationSyncApi
) : ReserveSyncApi {
    override suspend fun getAll(
        structuralIds: List<String?>?,
        molId: String?,
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
                structuralIds = structuralIds,
                molId = molId,
                nomenclatureGroupId = nomenclatureGroupId,
                orderId = orderId,
                receptionItemCategoryId = receptionItemCategoryId,
                reservesIds = reservesIds,
                textQuery = textQuery,
                isFilterCount = false,
                locationIds = locationIds,
                reservesShorts = reservesShorts
            )
        )
            .map { it.toSyncEntity(location = listOfNotNull(locationSyncApi.getLocationById(it.locationDb?.parentId))) }
    }

    override suspend fun getById(id: String): ReserveDetailSyncEntity {
        val fullReserve = reserveDao.getById(id)
        val location = locationSyncApi.getLocationFullPath(fullReserve.locationDb?.parentId)
        val structurals =
            structuralSyncApi.getStructuralFullPath(
                fullReserve.structuralDb?.id,
                mutableListOf()
            ).orEmpty()
        val balanceUnitIndex = structurals.indexOfLast { it.balanceUnit }.takeIf { it >= 0 } ?: 0
        val balanceUnits = structurals.subList(0, balanceUnitIndex)
        return fullReserve.toDetailSyncEntity(
            balanceUnits = balanceUnits,
            structurals = structurals,
            location = location
        )
    }

    override suspend fun getReservesFilterCount(
        structuralIds: List<String?>?,
        molId: String?,
        nomenclatureGroupId: String?,
        orderId: String?,
        receptionItemCategoryId: String?,
        locationIds: List<String?>?
    ): Long {
        return reserveDao.getFilterCount(
            sqlReserveQuery(
                structuralIds = structuralIds,
                molId = molId,
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