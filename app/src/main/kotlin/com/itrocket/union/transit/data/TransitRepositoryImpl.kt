package com.itrocket.union.transit.data

import com.example.union_sync_api.data.TransitSyncApi
import com.example.union_sync_api.entity.ReserveCountSyncEntity
import com.example.union_sync_api.entity.TransitCreateSyncEntity
import com.example.union_sync_api.entity.TransitUpdateReservesSyncEntity
import com.example.union_sync_api.entity.TransitUpdateSyncEntity
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.documents.data.mapper.map
import com.itrocket.union.documents.domain.entity.DocumentDomain
import com.itrocket.union.transit.domain.dependencies.TransitRepository

class TransitRepositoryImpl(
    private val transitSyncApi: TransitSyncApi,
    private val coreDispatchers: CoreDispatchers
) : TransitRepository {
    override suspend fun getTransitById(id: String): DocumentDomain {
        return transitSyncApi.getTransitById(id).map()
    }

    override suspend fun updateTransit(transitUpdateSyncEntity: TransitUpdateSyncEntity) {
        transitSyncApi.updateTransit(transitUpdateSyncEntity)
    }

    override suspend fun createTransit(transitCreateSyncEntity: TransitCreateSyncEntity): String {
        return transitSyncApi.createTransit(transitCreateSyncEntity)
    }

    override suspend fun updateTransitReserves(updateTransitReservesSyncEntity: TransitUpdateReservesSyncEntity) {
        return transitSyncApi.updateTransitReserves(updateTransitReservesSyncEntity)
    }

    override suspend fun insertTransitReserveCount(transitReserveCounts: List<ReserveCountSyncEntity>) {
       return transitSyncApi.updateTransitReservesCount(transitReserveCounts)
    }

}