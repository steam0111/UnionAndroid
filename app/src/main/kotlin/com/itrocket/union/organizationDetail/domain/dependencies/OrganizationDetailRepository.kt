package com.itrocket.union.organizationDetail.domain.dependencies

import com.itrocket.union.organizationDetail.domain.entity.OrganizationDetailDomain

interface OrganizationDetailRepository {
    suspend fun getOrganizationDetail(id: String): OrganizationDetailDomain
}