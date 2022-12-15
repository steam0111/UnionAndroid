package com.example.union_sync_api.data

import com.example.union_sync_api.entity.LabelType
import com.example.union_sync_api.entity.ReserveDetailSyncEntity
import com.example.union_sync_api.entity.ReserveShortSyncEntity
import com.example.union_sync_api.entity.ReserveSyncEntity
import com.example.union_sync_api.entity.ReserveUpdateSyncEntity

interface ReserveSyncApi {

    suspend fun getAll(
        structuralIds: List<String?>? = null,
        molId: String? = null,
        nomenclatureGroupId: String? = null,
        orderId: String? = null,
        receptionItemCategoryId: String? = null,
        reservesIds: List<String>? = null,
        reservesShorts: List<ReserveShortSyncEntity>? = null,
        textQuery: String? = null,
        locationIds: List<String?>? = null,
        offset: Long? = null,
        limit: Long? = null,
        hideZeroReserves: Boolean,
        nomenclatureId: String? = null
    ): List<ReserveSyncEntity>

    suspend fun getById(id: String): ReserveDetailSyncEntity

    suspend fun getReservesFilterCount(
        structuralIds: List<String?>? = null,
        molId: String? = null,
        nomenclatureGroupId: String? = null,
        orderId: String? = null,
        receptionItemCategoryId: String? = null,
        locationIds: List<String?>? = null,
        hideZeroReserves: Boolean,
        nomenclatureId: String?
    ): Long

    suspend fun updateReserves(reserves: List<ReserveUpdateSyncEntity>)

    suspend fun insertAll(reserves: List<ReserveSyncEntity>)

    suspend fun updateLabelType(labelType: LabelType)
}