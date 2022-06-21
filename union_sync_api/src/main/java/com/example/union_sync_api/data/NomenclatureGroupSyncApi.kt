package com.example.union_sync_api.data

import com.example.union_sync_api.entity.NomenclatureGroupSyncEntity

interface NomenclatureGroupSyncApi {
    suspend fun getNomenclatureGroups(): List<NomenclatureGroupSyncEntity>
}