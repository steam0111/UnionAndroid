package com.example.union_sync_api.data

import com.example.union_sync_api.entity.EquipmentTypesSyncEntity
import kotlinx.coroutines.flow.Flow

interface EquipmentTypesSyncApi {
    suspend fun getEquipmentTypes(): Flow<List<EquipmentTypesSyncEntity>>
}