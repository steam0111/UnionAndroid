package com.itrocket.union.branchDetail.domain

import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.branchDetail.domain.dependencies.BranchDetailRepository
import com.itrocket.union.branchDetail.domain.entity.BranchDetailDomain
import kotlinx.coroutines.withContext

class BranchDetailInteractor(
    private val repository: BranchDetailRepository,
    private val coreDispatchers: CoreDispatchers
) {

    suspend fun getBranchDetail(id: String): BranchDetailDomain =
        withContext(coreDispatchers.io) {
            repository.getBranchDetail(id)
        }
}