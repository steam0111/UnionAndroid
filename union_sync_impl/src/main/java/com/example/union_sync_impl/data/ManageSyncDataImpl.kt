package com.example.union_sync_impl.data

import com.example.union_sync_api.data.ManageSyncDataApi
import com.example.union_sync_impl.UnionDatabase
import com.example.union_sync_impl.dao.NetworkSyncDao

class ManageSyncDataImpl(
    private val unionDatabase: UnionDatabase,
    private val networkSyncDao: NetworkSyncDao
) : ManageSyncDataApi {

    override suspend fun clearAll() {
        unionDatabase.clearAllTables()
    }

    override suspend fun isSynced(): Boolean {
        return networkSyncDao.getNetworkSync()?.isSynced ?: false
    }
}