package com.itrocket.union.branches.domain.dependencies

import com.itrocket.union.branches.domain.entity.BranchesDomain
import kotlinx.coroutines.flow.Flow

interface BranchesRepository {

    suspend fun getBranches(): Flow<List<BranchesDomain>>
}