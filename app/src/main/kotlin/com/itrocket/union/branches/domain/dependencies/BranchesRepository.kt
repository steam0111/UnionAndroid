package com.itrocket.union.branches.domain.dependencies

import com.itrocket.union.branches.domain.entity.BranchesDomain
import com.itrocket.union.manual.ParamDomain

interface BranchesRepository {

    suspend fun getBranches(
        textQuery: String? = null,
        params: List<ParamDomain>?
    ): List<BranchesDomain>
}