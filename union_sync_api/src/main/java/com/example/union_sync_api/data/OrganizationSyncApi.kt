package com.example.union_sync_api.data

import com.example.union_sync_api.entity.OrganizationDetailSyncEntity
import com.example.union_sync_api.entity.OrganizationSyncEntity
import kotlinx.coroutines.flow.Flow

interface OrganizationSyncApi {
    suspend fun getOrganizations(textQuery: String? = null): Flow<List<OrganizationSyncEntity>>

    suspend fun getOrganizationDetail(id: String): OrganizationDetailSyncEntity
}