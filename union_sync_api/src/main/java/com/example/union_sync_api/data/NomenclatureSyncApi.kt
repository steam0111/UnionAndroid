package com.example.union_sync_api.data

import com.example.union_sync_api.entity.NomenclatureDetailSyncEntity
import com.example.union_sync_api.entity.NomenclatureSyncEntity

interface NomenclatureSyncApi {
    suspend fun getNomenclatures(): List<NomenclatureSyncEntity>

    suspend fun getNomenclatureDetail(id: String): NomenclatureDetailSyncEntity
}