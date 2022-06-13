package com.example.union_sync_impl.data

import com.example.union_sync_api.data.OrganizationSyncApi
import com.example.union_sync_api.entity.OrganizationSyncEntity
import com.example.union_sync_impl.dao.OrganizationDao
import com.example.union_sync_impl.data.mapper.toOrganizationDb
import com.example.union_sync_impl.data.mapper.toSyncEntity
import org.openapitools.client.custom_api.OrganizationApi

class OrganizationSyncApiImpl(
    private val organizationApi: OrganizationApi,
    private val organizationDao: OrganizationDao
) : OrganizationSyncApi {

    override suspend fun getOrganizations(): List<OrganizationSyncEntity> {
        val organizationDb = organizationDao.getAll()
        if (organizationDb.isEmpty()) {
            val organizationNetwork =
                organizationApi.apiCatalogsOrganizationGet().list ?: return emptyList()
            organizationDao.insertAll(organizationNetwork.map { it.toOrganizationDb() })
            return organizationDao.getAll().map { it.toSyncEntity() }
        }
        return organizationDb.map { it.toSyncEntity() }
    }
}