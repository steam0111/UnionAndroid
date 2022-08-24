package com.example.union_sync_impl.data

import com.example.union_sync_api.data.StructuralSyncApi
import com.example.union_sync_api.entity.StructuralSyncEntity
import com.example.union_sync_impl.dao.StructuralDao
import com.example.union_sync_impl.dao.sqlStructuralsQuery
import com.example.union_sync_impl.data.mapper.toStructuralSyncEntity

class StructuralSyncApiImpl(
    private val structuralDao: StructuralDao,
) : StructuralSyncApi {

    override suspend fun getStructurals(
        structuralId: String?,
        textQuery: String?
    ): List<StructuralSyncEntity> {
        val structurals = structuralDao.getStructuralsByParentId(
            sqlStructuralsQuery(
                parentId = structuralId,
                textQuery = if (textQuery.isNullOrEmpty()) {
                    null
                } else {
                    textQuery
                }
            )
        )
        return structurals.map {
            it.toStructuralSyncEntity()
        }
    }

    override suspend fun getAllStructuralsIdsByParentId(parentId: String?): List<String?> {
        return buildList {
            add(parentId)
            addAll(structuralDao.getAllStructuralsByParentId(parentId).map { it.id })
        }
    }

    override suspend fun getStructuralFullPath(
        childId: String?,
        structurals: MutableList<StructuralSyncEntity>
    ): List<StructuralSyncEntity>? {
        val childStructural = structuralDao.getStructuralById(childId)
        childStructural?.let {
            structurals.add(it.toStructuralSyncEntity())
        }
        return if (childStructural?.parentId == null) {
            structurals.reversed()
        } else {
            getStructuralFullPath(childStructural.parentId, structurals)
        }
    }

    override suspend fun getStructuralById(structuralId: String?): StructuralSyncEntity? {
        return structuralDao.getStructuralById(structuralId)?.toStructuralSyncEntity()
    }

    override suspend fun getStructuralsByIds(ids: List<String?>): List<StructuralSyncEntity> {
        return structuralDao.getStructuralsByIds(ids).map {
            it.toStructuralSyncEntity()
        }
    }
}