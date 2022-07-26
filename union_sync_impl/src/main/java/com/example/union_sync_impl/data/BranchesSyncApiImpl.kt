package com.example.union_sync_impl.data

import com.example.union_sync_api.data.BranchesSyncApi
import com.example.union_sync_api.entity.BranchDetailSyncEntity
import com.example.union_sync_api.entity.BranchSyncEntity
import com.example.union_sync_impl.dao.BranchesDao
import com.example.union_sync_impl.dao.sqlBranchesQuery
import com.example.union_sync_impl.data.mapper.toDetailSyncEntity
import com.example.union_sync_impl.data.mapper.toSyncEntity

class BranchesSyncApiImpl(
    private val branchesDao: BranchesDao
) : BranchesSyncApi {

    override suspend fun getBranches(
        organizationId: String?,
        textQuery: String?
    ): List<BranchSyncEntity> {
        return branchesDao.getAll(
            sqlBranchesQuery(
                organizationId = organizationId,
                textQuery = textQuery
            )
        ).map { it.toSyncEntity() }
    }

    override suspend fun getBranchesCount(organizationId: String?, textQuery: String?): Long {
        return branchesDao.getCount(
            sqlBranchesQuery(
                organizationId = organizationId,
                textQuery = textQuery,
                isFilterCount = true
            )
        )
    }

    override suspend fun getBranchDetail(id: String): BranchDetailSyncEntity {
        return branchesDao.getFullById(id).toDetailSyncEntity()
    }
}