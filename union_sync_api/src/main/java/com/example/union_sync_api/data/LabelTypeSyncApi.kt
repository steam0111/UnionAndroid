package com.example.union_sync_api.data

import com.example.union_sync_api.entity.LabelTypeSyncEntity

interface LabelTypeSyncApi {

    suspend fun getLabelTypeById(id: String): LabelTypeSyncEntity

    suspend fun getLabelTypes(
        textQuery: String? = null,
        offset: Long? = null,
        limit: Long? = null
    ): List<LabelTypeSyncEntity>
}