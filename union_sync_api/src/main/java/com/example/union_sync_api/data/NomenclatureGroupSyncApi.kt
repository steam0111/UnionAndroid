package com.example.union_sync_api.data

import com.example.union_sync_api.entity.NomenclatureGroupSyncEntity

interface NomenclatureGroupSyncApi {
    suspend fun getNomenclatureGroups(
        textQuery: String? = null,
        offset: Long? = null,
        limit: Long? = null
    ): List<NomenclatureGroupSyncEntity>

    suspend fun getNomenclatureGroupDetail(id: String): NomenclatureGroupSyncEntity
}