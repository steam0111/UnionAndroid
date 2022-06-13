package com.itrocket.union.organizations.domain.dependencies

import com.itrocket.union.organizations.domain.entity.OrganizationDomain

interface OrganizationRepository {
    suspend fun getOrganizations(): List<OrganizationDomain>
}