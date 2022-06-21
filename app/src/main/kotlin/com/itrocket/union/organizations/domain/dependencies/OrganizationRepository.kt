package com.itrocket.union.organizations.domain.dependencies

import com.itrocket.union.organizations.domain.entity.OrganizationDomain
import kotlinx.coroutines.flow.Flow

interface OrganizationRepository {
    suspend fun getOrganizations(): Flow<List<OrganizationDomain>>
}