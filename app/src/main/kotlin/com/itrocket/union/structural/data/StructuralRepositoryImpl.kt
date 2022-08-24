package com.itrocket.union.structural.data

import com.example.union_sync_api.data.StructuralSyncApi
import com.example.union_sync_api.entity.StructuralSyncEntity
import com.itrocket.union.structural.data.mapper.map
import com.itrocket.union.structural.domain.dependencies.StructuralRepository
import com.itrocket.union.structural.domain.entity.StructuralDomain
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.structural.data.mapper.toStructuralDomain
import kotlinx.coroutines.withContext

class StructuralRepositoryImpl(
    private val syncApi: StructuralSyncApi,
    private val coreDispatchers: CoreDispatchers
) : StructuralRepository {

    override suspend fun getStructuralList(
        selectedStructural: StructuralDomain?,
        searchText: String
    ): List<StructuralDomain> = withContext(coreDispatchers.io) {
        getLocations(selectedStructural, textQuery = searchText)
    }

    override suspend fun getStructuralById(structuralId: String): StructuralSyncEntity? =
        withContext(coreDispatchers.io) {
            syncApi.getStructuralById(structuralId)
        }

    override suspend fun getAllStructuralsIdsByParent(parentId: String?): List<String?> {
        return syncApi.getAllStructuralsIdsByParentId(parentId)
    }

    override suspend fun getBalanceUnitFullPath(
        childId: String?,
        structurals: MutableList<StructuralSyncEntity>
    ): List<StructuralDomain>? {
        return withContext(coreDispatchers.io) {
            val structuralFullPath = syncApi.getStructuralFullPath(childId, structurals)
            val indexOfBalanceUnit =
                structuralFullPath?.indexOf(structuralFullPath.lastOrNull { it.balanceUnit })
            if (indexOfBalanceUnit == null || indexOfBalanceUnit < 0) return@withContext null

            structuralFullPath.slice(IntRange(0, indexOfBalanceUnit))
                .map { it.toStructuralDomain() }
        }
    }

    private suspend fun getLocations(
        selectedStructural: StructuralDomain?,
        textQuery: String?
    ): List<StructuralDomain> {
        return syncApi.getStructurals(
            structuralId = selectedStructural?.id,
            textQuery = textQuery
        ).map {
            it.toStructuralDomain()
        }
    }

}