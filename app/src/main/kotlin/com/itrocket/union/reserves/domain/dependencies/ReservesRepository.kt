package com.itrocket.union.reserves.domain.dependencies

import com.example.union_sync_api.entity.ReserveShortSyncEntity
import com.example.union_sync_api.entity.ReserveSyncEntity
import com.example.union_sync_api.entity.ReserveUpdateSyncEntity
import com.itrocket.union.manual.ParamDomain
import com.itrocket.union.reserves.domain.entity.ReservesDomain

interface ReservesRepository {

    suspend fun getReserves(
        params: List<ParamDomain>? = null,
        reservesIds: List<String>? = null,
        textQuery: String? = null,
        reservesShorts: List<ReserveShortSyncEntity>? = null,
        selectedLocationIds: List<String?>?,
        structuralIds: List<String?>?
    ): List<ReservesDomain>

    suspend fun getReservesByIds(
        reservesIds: List<String>,
    ): List<ReserveSyncEntity>

    suspend fun getReservesByShorts(
        reservesShorts: List<ReserveShortSyncEntity>? = null
    ): List<ReserveSyncEntity>

    suspend fun getReservesFilterCount(
        params: List<ParamDomain>? = null,
        selectedLocationIds: List<String?>?,
        structuralIds: List<String?>?
    ): Long

    suspend fun getReserveById(id: String): ReservesDomain

    suspend fun updateReserves(reserves: List<ReserveUpdateSyncEntity>)

    suspend fun insertAll(reserves: List<ReserveSyncEntity>)
}