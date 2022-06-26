package com.itrocket.union.branches.domain.dependencies

import com.itrocket.union.branches.domain.entity.BranchesDomain
import com.itrocket.union.manual.ParamDomain
import kotlinx.coroutines.flow.Flow

interface BranchesRepository {

    suspend fun getBranches(
        textQuery: String?,
        params: List<ParamDomain>?
    ): Flow<List<BranchesDomain>>
}