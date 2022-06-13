package com.example.union_sync_api.data

import com.example.union_sync_api.entity.OrganizationSyncEntity

interface OrganizationSyncApi {
    suspend fun getOrganizations(): List<OrganizationSyncEntity>
}