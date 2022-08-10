package com.itrocket.union.branches.domain

import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.branches.domain.dependencies.BranchesRepository
import com.itrocket.union.branches.domain.entity.BranchesDomain
import com.itrocket.union.manual.ManualType
import com.itrocket.union.manual.ParamDomain
import kotlinx.coroutines.withContext

class BranchesInteractor(
    private val repository: BranchesRepository,
    private val coreDispatchers: CoreDispatchers
) {

    suspend fun getBranches(
        params: List<ParamDomain>?,
        searchQuery: String = ""
    ): List<BranchesDomain> =
        withContext(coreDispatchers.io) { repository.getBranches(searchQuery, params) }

    fun getFilters(): List<ParamDomain> {
        return listOf(
            ParamDomain(
                type = ManualType.ORGANIZATION,
                value = ""
            )
        )
    }
}