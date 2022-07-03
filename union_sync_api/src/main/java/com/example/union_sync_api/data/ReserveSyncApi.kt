package com.example.union_sync_api.data

import com.example.union_sync_api.entity.ReserveSyncEntity

interface ReserveSyncApi {

    suspend fun getAll(
        organizationId: String? = null,
        molId: String? = null,
        structuralSubdivisionId: String? = null,
        nomenclatureId: String? = null,
        nomenclatureGroupId: String? = null,
        orderId: String? = null,
        receptionItemCategoryId: String? = null,
        textQuery: String? = null,
    ): List<ReserveSyncEntity>
}