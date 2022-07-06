package com.example.union_sync_api.data

import com.example.union_sync_api.entity.EquipmentTypeSyncEntity
import kotlinx.coroutines.flow.Flow

interface EquipmentTypeSyncApi {
    suspend fun getEquipmentTypes(textQuery: String?): Flow<List<EquipmentTypeSyncEntity>>

    suspend fun getEquipmentTypeDetail(id: String): EquipmentTypeSyncEntity
}