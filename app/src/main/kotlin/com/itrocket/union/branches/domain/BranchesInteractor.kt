package com.itrocket.union.branches.domain

import kotlinx.coroutines.withContext
import com.itrocket.union.branches.domain.dependencies.BranchesRepository
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.branches.domain.entity.BranchesDomain
import kotlinx.coroutines.flow.Flow

class BranchesInteractor(
    private val repository: BranchesRepository,
    private val coreDispatchers: CoreDispatchers
) {

    suspend fun getBranches(): Flow<List<BranchesDomain>> =
        withContext(coreDispatchers.io) { repository.getBranches() }
}