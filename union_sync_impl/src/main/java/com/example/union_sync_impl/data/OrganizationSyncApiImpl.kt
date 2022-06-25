package com.example.union_sync_impl.data

import com.example.union_sync_api.data.OrganizationSyncApi
import com.example.union_sync_api.entity.OrganizationDetailSyncEntity
import com.example.union_sync_api.entity.OrganizationSyncEntity
import com.example.union_sync_impl.dao.OrganizationDao
import com.example.union_sync_impl.data.mapper.toDetailSyncEntity
import com.example.union_sync_impl.data.mapper.toSyncEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class OrganizationSyncApiImpl(
    private val organizationDao: OrganizationDao
) : OrganizationSyncApi {

    override suspend fun getOrganizations(): Flow<List<OrganizationSyncEntity>> {
        return flow {
            emit(organizationDao.getAll().map { it.toSyncEntity() })
        }.distinctUntilChanged().flowOn(Dispatchers.IO)
    }

    override suspend fun getOrganizationDetail(id: String): OrganizationDetailSyncEntity {
        return organizationDao.getFullById(id).toDetailSyncEntity()
    }
}