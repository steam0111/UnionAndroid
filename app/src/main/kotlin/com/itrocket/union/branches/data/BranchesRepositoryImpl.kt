package com.itrocket.union.branches.data

import com.example.union_sync_api.data.BranchesSyncApi
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.branches.data.mapper.map
import com.itrocket.union.branches.domain.dependencies.BranchesRepository
import com.itrocket.union.branches.domain.entity.BranchesDomain
import com.itrocket.union.manual.ParamDomain
import com.itrocket.union.manual.getOrganizationId
import kotlinx.coroutines.withContext

class BranchesRepositoryImpl(
    private val coreDispatchers: CoreDispatchers,
    private val branchesSyncApi: BranchesSyncApi
) : BranchesRepository {
    override suspend fun getBranches(
        textQuery: String?,
        params: List<ParamDomain>?,
    ): List<BranchesDomain> {
        return withContext(coreDispatchers.io) {
            branchesSyncApi.getBranches(
                organizationId = params?.getOrganizationId(),
                textQuery = textQuery
            ).map()
        }
    }

    override suspend fun getBranchesCount(textQuery: String?, params: List<ParamDomain>?): Long {
        return withContext(coreDispatchers.io) {
            branchesSyncApi.getBranchesCount(
                organizationId = params?.getOrganizationId(),
                textQuery = textQuery
            )
        }
    }
}