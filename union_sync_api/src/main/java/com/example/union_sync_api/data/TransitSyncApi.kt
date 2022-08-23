package com.example.union_sync_api.data

import com.example.union_sync_api.entity.ReserveCountSyncEntity
import com.example.union_sync_api.entity.TransitCreateSyncEntity
import com.example.union_sync_api.entity.TransitSyncEntity
import com.example.union_sync_api.entity.TransitUpdateReservesSyncEntity
import com.example.union_sync_api.entity.TransitUpdateSyncEntity
import kotlinx.coroutines.flow.Flow

interface TransitSyncApi {

    suspend fun createTransit(transitCreateSyncEntity: TransitCreateSyncEntity): String

    suspend fun getAllTransit(
        textQuery: String? = null,
        responsibleId: String? = null,
        structuralFromId: String? = null,
        structuralToId: String? = null,
        receivingId: String? = null,
    ): Flow<List<TransitSyncEntity>>

    suspend fun getTransitById(id: String): TransitSyncEntity

    suspend fun updateTransit(transitUpdateSyncEntity: TransitUpdateSyncEntity)

    suspend fun updateTransitReserves(updateTransitReservesSyncEntity: TransitUpdateReservesSyncEntity)

    suspend fun updateTransitReservesCount(transitReserveCounts: List<ReserveCountSyncEntity>)
}