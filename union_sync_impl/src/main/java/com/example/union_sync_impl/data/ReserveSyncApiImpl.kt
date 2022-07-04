package com.example.union_sync_impl.data

import android.util.Log
import com.example.union_sync_api.data.ReserveSyncApi
import com.example.union_sync_api.entity.LocationSyncEntity
import com.example.union_sync_api.entity.ReserveDetailSyncEntity
import com.example.union_sync_api.entity.ReserveSyncEntity
import com.example.union_sync_impl.dao.LocationDao
import com.example.union_sync_impl.dao.ReserveDao
import com.example.union_sync_impl.dao.sqlReserveQuery
import com.example.union_sync_impl.data.mapper.toDetailSyncEntity
import com.example.union_sync_impl.data.mapper.toLocationSyncEntity
import com.example.union_sync_impl.data.mapper.toSyncEntity
import com.example.union_sync_impl.entity.location.LocationDb
import com.example.union_sync_impl.entity.location.LocationTypeDb

class ReserveSyncApiImpl(
    private val reserveDao: ReserveDao,
    private val locationDao: LocationDao,
) : ReserveSyncApi {
    override suspend fun getAll(
        organizationId: String?,
        molId: String?,
        structuralSubdivisionId: String?,
        nomenclatureGroupId: String?,
        orderId: String?,
        receptionItemCategoryId: String?,
        textQuery: String?,
    ): List<ReserveSyncEntity> {
        return reserveDao.getAll(
            sqlReserveQuery(
                organizationId = organizationId,
                molId = molId,
                structuralSubdivisionId = structuralSubdivisionId,
                nomenclatureGroupId = nomenclatureGroupId,
                orderId = orderId,
                receptionItemCategoryId = receptionItemCategoryId,
                textQuery = textQuery,
                isFilterCount = false
            )
        ).map { it.toSyncEntity(getLocationSyncEntity(it.locationDb)) }
    }

    override suspend fun getById(id: String): ReserveDetailSyncEntity {
        val fullReserve = reserveDao.getById(id)
        return fullReserve.toDetailSyncEntity(getLocationSyncEntity(fullReserve.locationDb))
    }

    override suspend fun getReservesFilterCount(
        organizationId: String?,
        molId: String?,
        structuralSubdivisionId: String?,
        nomenclatureGroupId: String?,
        orderId: String?,
        receptionItemCategoryId: String?
    ): Int {
        return reserveDao.getFilterCount(
            sqlReserveQuery(
                organizationId = organizationId,
                molId = molId,
                structuralSubdivisionId = structuralSubdivisionId,
                nomenclatureGroupId = nomenclatureGroupId,
                orderId = orderId,
                receptionItemCategoryId = receptionItemCategoryId,
                isFilterCount = true
            )
        )
    }

    //TODO переделать на join
    private suspend fun getLocationSyncEntity(locationDb: LocationDb?): LocationSyncEntity? {
        if (locationDb == null) {
            return null
        }
        val locationTypeId = locationDb.locationTypeId ?: return null
        val locationTypeDb: LocationTypeDb =
            locationDao.getLocationTypeById(locationTypeId) ?: return null

        return locationDb.toLocationSyncEntity(locationTypeDb)
    }
}