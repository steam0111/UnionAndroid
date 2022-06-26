package com.itrocket.union.branches.data

import com.example.union_sync_api.data.BranchesSyncApi
import com.itrocket.union.branches.data.mapper.map
import com.itrocket.union.branches.domain.dependencies.BranchesRepository
import com.itrocket.union.branches.domain.entity.BranchesDomain
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.manual.ParamDomain
import com.itrocket.union.manual.getOrganizationId
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class BranchesRepositoryImpl(
    private val coreDispatchers: CoreDispatchers,
    private val branchesSyncApi: BranchesSyncApi
) : BranchesRepository {
    override suspend fun getBranches(
        textQuery: String?,
        params: List<ParamDomain>?,
    ): Flow<List<BranchesDomain>> {
        return withContext(coreDispatchers.io) {
            branchesSyncApi.getBranches(
                organizationId = params?.getOrganizationId(),
                textQuery = textQuery
            ).map { it.map() }
        }
    }

}