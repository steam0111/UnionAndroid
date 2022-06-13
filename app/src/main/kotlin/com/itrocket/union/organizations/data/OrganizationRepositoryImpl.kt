package com.itrocket.union.organizations.data

import com.example.union_sync_api.data.OrganizationSyncApi
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.organizations.domain.dependencies.OrganizationRepository
import com.itrocket.union.organizations.domain.entity.OrganizationDomain
import kotlinx.coroutines.withContext
import com.itrocket.union.organizations.data.mapper.map

class OrganizationRepositoryImpl(
    private val coreDispatchers: CoreDispatchers,
    private val organizationSyncApi: OrganizationSyncApi
) : OrganizationRepository {

    override suspend fun getOrganizations(): List<OrganizationDomain> =
        withContext(coreDispatchers.io) {
            organizationSyncApi.getOrganizations().map()
        }
}