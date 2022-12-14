package com.example.union_sync_api.data

import com.example.union_sync_api.entity.NomenclatureDetailSyncEntity
import com.example.union_sync_api.entity.NomenclatureSyncEntity

interface NomenclatureSyncApi {
    suspend fun getNomenclatures(
        groupId: String? = null,
        textQuery: String? = null,
        offset: Long? = null,
        limit: Long? = null,
        number: String? = null
    ): List<NomenclatureSyncEntity>

    suspend fun getNomenclaturesCount(
        groupId: String? = null,
        textQuery: String? = null
    ): Long

    suspend fun getNomenclatureDetail(id: String): NomenclatureDetailSyncEntity
}