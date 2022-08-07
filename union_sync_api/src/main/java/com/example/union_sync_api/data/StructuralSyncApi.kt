package com.example.union_sync_api.data

import com.example.union_sync_api.entity.StructuralSyncEntity

interface StructuralSyncApi {
    /**
     * Если structuralParentId == null то возвращается корневой список
     */
    suspend fun getStructurals(
        structuralId: String? = null,
        textQuery: String? = null
    ): List<StructuralSyncEntity>

    suspend fun getAllStructuralsIdsByParentId(parentId: String?): List<String?>

    suspend fun getStructuralById(structuralId: String?): StructuralSyncEntity?

    suspend fun getStructuralsByIds(ids: List<String?>): List<StructuralSyncEntity>
}