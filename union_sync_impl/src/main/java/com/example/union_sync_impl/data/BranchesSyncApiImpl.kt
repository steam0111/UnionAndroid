package com.example.union_sync_impl.data

import android.util.Log
import com.example.union_sync_api.data.BranchesSyncApi
import com.example.union_sync_api.entity.BranchSyncEntity
import com.example.union_sync_impl.dao.BranchesDao
import com.example.union_sync_impl.dao.OrganizationDao
import com.example.union_sync_impl.data.mapper.toBranchesDb
import com.example.union_sync_impl.data.mapper.toOrganizationDb
import com.example.union_sync_impl.data.mapper.toSyncEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.openapitools.client.custom_api.BranchesApi

class BranchesSyncApiImpl(
    private val branchesApi: BranchesApi,
    private val branchesDao: BranchesDao,
    private val organizationDao: OrganizationDao
) : BranchesSyncApi {
    override suspend fun getBranches(): Flow<List<BranchSyncEntity>> {
        return flow {
            emit(branchesDao.getAll().map { it.toSyncEntity() })
            val branchesNetwork = branchesApi.apiCatalogsBranchesGet().list.orEmpty()
            organizationDao.insertAll(branchesNetwork.mapNotNull { it.extendedOrganization?.toOrganizationDb() })
            branchesNetwork.map { it.toBranchesDb() }.let { dbBranches ->
                branchesDao.insertAll(dbBranches)
            }
            emit(branchesDao.getAll().map { it.toSyncEntity() })
        }.distinctUntilChanged().flowOn(Dispatchers.IO)
    }
}