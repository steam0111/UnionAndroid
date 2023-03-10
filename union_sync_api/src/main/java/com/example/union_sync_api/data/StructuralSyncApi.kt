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

    /**
     * Метод для получения полного пути до childId структуры
     **/
    suspend fun getStructuralFullPath(
        childId: String?,
        structurals: MutableList<StructuralSyncEntity>
    ): List<StructuralSyncEntity>?

    suspend fun getStructuralById(structuralId: String?): StructuralSyncEntity?

    suspend fun getStructuralsByIds(ids: List<String?>): List<StructuralSyncEntity>
}