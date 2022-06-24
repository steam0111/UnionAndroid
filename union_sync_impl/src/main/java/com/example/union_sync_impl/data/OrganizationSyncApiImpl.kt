package com.example.union_sync_impl.data

import com.example.union_sync_api.data.OrganizationSyncApi
import com.example.union_sync_api.entity.OrganizationDetailSyncEntity
import com.example.union_sync_api.entity.OrganizationSyncEntity
import com.example.union_sync_impl.dao.NetworkSyncDao
import com.example.union_sync_impl.dao.OrganizationDao
import com.example.union_sync_impl.data.mapper.toDetailSyncEntity
import com.example.union_sync_impl.data.mapper.toOrganizationDb
import com.example.union_sync_impl.data.mapper.toSyncEntity
import com.example.union_sync_impl.entity.NetworkSyncDb
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import org.openapitools.client.custom_api.OrganizationApi

class OrganizationSyncApiImpl(
    private val organizationApi: OrganizationApi,
    private val organizationDao: OrganizationDao,
    private val networkSyncDao: NetworkSyncDao
) : OrganizationSyncApi {

    override suspend fun getOrganizations(): Flow<List<OrganizationSyncEntity>> {
        return flow {
            emit(organizationDao.getAll().map { it.toSyncEntity() })
            val networkSyncDb = networkSyncDao.getNetworkSync()
            if (networkSyncDb?.isOrganizationSync != true) {
                val organizationNetwork = organizationApi.apiCatalogsOrganizationGet().list
                organizationNetwork?.map { it.toOrganizationDb() }?.let { dbOrganizations ->
                    organizationDao.insertAll(dbOrganizations)
                }
                networkSyncDao.insert(
                    networkSyncDb?.copy(isOrganizationSync = true) ?: NetworkSyncDb(
                        isOrganizationSync = true
                    )
                )
                emit(organizationDao.getAll().map { it.toSyncEntity() })
            }
        }.distinctUntilChanged().flowOn(Dispatchers.IO)
    }

    override suspend fun getOrganizationDetail(id: String): OrganizationDetailSyncEntity {
        return organizationDao.getFullById(id).toDetailSyncEntity()
    }
}