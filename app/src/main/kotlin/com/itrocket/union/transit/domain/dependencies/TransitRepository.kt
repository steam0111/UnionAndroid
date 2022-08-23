package com.itrocket.union.transit.domain.dependencies

import com.example.union_sync_api.entity.ReserveCountSyncEntity
import com.example.union_sync_api.entity.TransitCreateSyncEntity
import com.example.union_sync_api.entity.TransitUpdateReservesSyncEntity
import com.example.union_sync_api.entity.TransitUpdateSyncEntity
import com.itrocket.union.documents.domain.entity.DocumentDomain

interface TransitRepository {

    suspend fun getTransitById(id: String): DocumentDomain

    suspend fun updateTransit(transitUpdateSyncEntity: TransitUpdateSyncEntity)

    suspend fun createTransit(transitCreateSyncEntity: TransitCreateSyncEntity): String

    suspend fun updateTransitReserves(updateTransitReservesSyncEntity: TransitUpdateReservesSyncEntity)

    suspend fun insertTransitReserveCount(transitReserveCounts: List<ReserveCountSyncEntity>)
}