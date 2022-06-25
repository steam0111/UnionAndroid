package com.itrocket.union.branchDetail.domain.dependencies

import com.itrocket.union.branchDetail.domain.entity.BranchDetailDomain

interface BranchDetailRepository {
    suspend fun getBranchDetail(id: String): BranchDetailDomain
}