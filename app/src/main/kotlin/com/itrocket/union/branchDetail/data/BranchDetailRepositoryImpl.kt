package com.itrocket.union.branchDetail.data

import com.example.union_sync_api.data.BranchesSyncApi
import com.itrocket.union.branchDetail.data.mapper.toBranchDetailDomain
import com.itrocket.union.branchDetail.domain.dependencies.BranchDetailRepository
import com.itrocket.union.branchDetail.domain.entity.BranchDetailDomain

class BranchDetailRepositoryImpl(
    private val syncApi: BranchesSyncApi
) : BranchDetailRepository {

    override suspend fun getBranchDetail(id: String): BranchDetailDomain {
        return syncApi.getBranchDetail(id).toBranchDetailDomain()
    }
}
