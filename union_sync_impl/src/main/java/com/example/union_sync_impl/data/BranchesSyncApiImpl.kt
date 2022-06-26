package com.example.union_sync_impl.data

import com.example.union_sync_api.data.BranchesSyncApi
import com.example.union_sync_api.entity.BranchDetailSyncEntity
import com.example.union_sync_api.entity.BranchSyncEntity
import com.example.union_sync_impl.dao.BranchesDao
import com.example.union_sync_impl.dao.sqlBranchesQuery
import com.example.union_sync_impl.data.mapper.toDetailSyncEntity
import com.example.union_sync_impl.data.mapper.toSyncEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class BranchesSyncApiImpl(
    private val branchesDao: BranchesDao
) : BranchesSyncApi {

    override suspend fun getBranches(
        textQuery: String?,
        organizationId: String?
    ): Flow<List<BranchSyncEntity>> {
        return flow {
            emit(branchesDao.getAll(sqlBranchesQuery(organizationId)).map { it.toSyncEntity() })
        }.distinctUntilChanged().flowOn(Dispatchers.IO)
    }

    override suspend fun getBranchDetail(id: String): BranchDetailSyncEntity {
        return branchesDao.getFullById(id).toDetailSyncEntity()
    }
}