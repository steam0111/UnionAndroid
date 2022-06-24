package com.itrocket.union.organizationDetail.data

import com.example.union_sync_api.data.OrganizationSyncApi
import com.itrocket.union.organizationDetail.data.mapper.toOrganizationDetailDomain
import com.itrocket.union.organizationDetail.domain.dependencies.OrganizationDetailRepository
import com.itrocket.union.organizationDetail.domain.entity.OrganizationDetailDomain

class OrganizationDetailRepositoryImpl(
    private val syncApi: OrganizationSyncApi
) : OrganizationDetailRepository {

    override suspend fun getOrganizationDetail(id: String): OrganizationDetailDomain {
        return syncApi.getOrganizationDetail(id).toOrganizationDetailDomain()
    }
}
