package com.example.union_sync_api.data

import com.example.union_sync_api.entity.EnumSyncEntity
import com.example.union_sync_api.entity.EnumType

interface EnumsSyncApi {
    suspend fun getAllByType(
        enumType: EnumType,
        textQuery: String? = null,
        id: String? = null
    ): List<EnumSyncEntity>
}