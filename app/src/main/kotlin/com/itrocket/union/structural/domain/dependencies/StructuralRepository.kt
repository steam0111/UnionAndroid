package com.itrocket.union.structural.domain.dependencies

import com.example.union_sync_api.entity.StructuralSyncEntity
import com.itrocket.union.structural.domain.entity.StructuralDomain

interface StructuralRepository {
    suspend fun getStructuralList(
        selectedStructural: StructuralDomain?,
        searchText: String
    ): List<StructuralDomain>

    suspend fun getStructuralById(structuralId: String): StructuralSyncEntity?

    suspend fun getAllStructuralsIdsByParent(parentId: String?): List<String?>

    suspend fun getBalanceUnitFullPath(
        childId: String?,
        structurals: MutableList<StructuralSyncEntity>
    ): List<StructuralDomain>?
}