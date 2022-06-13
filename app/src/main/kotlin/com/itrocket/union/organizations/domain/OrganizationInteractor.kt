package com.itrocket.union.organizations.domain

import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.organizations.domain.dependencies.OrganizationRepository
import com.itrocket.union.organizations.domain.entity.OrganizationDomain
import kotlinx.coroutines.withContext

class OrganizationInteractor(
    private val repository: OrganizationRepository,
    private val coreDispatchers: CoreDispatchers
) {
    suspend fun getOrganizations(): List<OrganizationDomain> = withContext(coreDispatchers.io) {
        repository.getOrganizations()
    }
}