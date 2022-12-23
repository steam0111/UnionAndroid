package com.example.union_sync_impl.data

import com.example.union_sync_api.data.NetworkSyncApi
import com.example.union_sync_impl.dao.NetworkSyncDao
import com.example.union_sync_impl.entity.NetworkSyncDb

class NetworkSyncApiImpl(private val networkSyncDao: NetworkSyncDao) : NetworkSyncApi {
    override suspend fun changeSyncFilesEnabled(enabled: Boolean) {
        val networkSync = networkSyncDao.getNetworkSync() ?: NetworkSyncDb()
        networkSyncDao.insert(networkSync.copy(syncFileEnabled = enabled))
    }

    override suspend fun getSyncFileEnabled(): Boolean {
        return networkSyncDao.getNetworkSync()?.syncFileEnabled ?: false
    }
}