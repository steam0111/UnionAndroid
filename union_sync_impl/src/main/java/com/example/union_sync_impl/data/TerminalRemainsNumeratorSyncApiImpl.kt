package com.example.union_sync_impl.data

import com.example.union_sync_api.data.TerminalRemainsNumeratorSyncApi
import com.example.union_sync_api.entity.TerminalRemainsNumeratorSyncEntity
import com.example.union_sync_impl.dao.TerminalRemainsNumeratorDao
import com.example.union_sync_impl.dao.sqlTerminalRemainsNumeratorQuery
import com.example.union_sync_impl.data.mapper.toSyncEntity

class TerminalRemainsNumeratorSyncApiImpl(private val terminalRemainsNumeratorDao: TerminalRemainsNumeratorDao) :
    TerminalRemainsNumeratorSyncApi {

    override suspend fun getAllRemainsNumerators(): List<TerminalRemainsNumeratorSyncEntity> {
        return terminalRemainsNumeratorDao.getAllRemainsNumerators(sqlTerminalRemainsNumeratorQuery())
            .map { it.toSyncEntity() }
    }

    override suspend fun getNumeratorByRemainId(id: String): TerminalRemainsNumeratorSyncEntity {
        return terminalRemainsNumeratorDao.getRemainNumeratorById(id).toSyncEntity()
    }
}