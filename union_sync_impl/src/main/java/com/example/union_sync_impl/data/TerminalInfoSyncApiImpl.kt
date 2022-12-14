package com.example.union_sync_impl.data

import com.example.union_sync_api.data.TerminalInfoSyncApi
import com.example.union_sync_api.entity.TerminalInfoSyncEntity
import com.example.union_sync_impl.dao.TerminalInfoDao
import com.example.union_sync_impl.data.mapper.toSyncEntity

class TerminalInfoSyncApiImpl(private val terminalInfoDao: TerminalInfoDao) : TerminalInfoSyncApi {
    override suspend fun getTerminalInfo(): TerminalInfoSyncEntity? {
        return terminalInfoDao.getTerminalInfo()?.toSyncEntity()
    }
}