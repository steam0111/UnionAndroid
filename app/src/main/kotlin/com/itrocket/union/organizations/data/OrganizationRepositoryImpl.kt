package com.itrocket.union.organizations.data

import com.example.union_sync_api.data.OrganizationSyncApi
import com.itrocket.union.organizations.data.mapper.map
import com.itrocket.union.organizations.domain.dependencies.OrganizationRepository
import com.itrocket.union.organizations.domain.entity.OrganizationDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class OrganizationRepositoryImpl(
    private val organizationSyncApi: OrganizationSyncApi
) : OrganizationRepository {

    override suspend fun getOrganizations(textQuery: String?): Flow<List<OrganizationDomain>> =
        organizationSyncApi.getOrganizations(textQuery).map { it.map() }

}