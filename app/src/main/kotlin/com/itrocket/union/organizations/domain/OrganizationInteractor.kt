package com.itrocket.union.organizations.domain

import com.itrocket.union.organizations.domain.dependencies.OrganizationRepository
import com.itrocket.union.organizations.domain.entity.OrganizationDomain
import kotlinx.coroutines.flow.Flow

class OrganizationInteractor(
    private val repository: OrganizationRepository
) {
    suspend fun getOrganizations(searchQuery: String = ""): Flow<List<OrganizationDomain>> =
        repository.getOrganizations()

}