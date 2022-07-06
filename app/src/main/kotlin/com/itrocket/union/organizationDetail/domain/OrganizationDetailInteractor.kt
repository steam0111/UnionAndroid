package com.itrocket.union.organizationDetail.domain

import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.organizationDetail.domain.dependencies.OrganizationDetailRepository
import com.itrocket.union.organizationDetail.domain.entity.OrganizationDetailDomain
import kotlinx.coroutines.withContext

class OrganizationDetailInteractor(
    private val repository: OrganizationDetailRepository,
    private val coreDispatchers: CoreDispatchers
) {

    suspend fun getOrganizationDetail(id: String): OrganizationDetailDomain =
        withContext(coreDispatchers.io) {
            repository.getOrganizationDetail(id)
        }
}