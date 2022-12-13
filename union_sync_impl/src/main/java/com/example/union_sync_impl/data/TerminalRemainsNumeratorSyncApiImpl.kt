package com.example.union_sync_impl.data

import com.example.union_sync_api.data.TerminalRemainsNumeratorSyncApi
import com.example.union_sync_api.entity.TerminalRemainsNumeratorSyncEntity
import com.example.union_sync_api.entity.UpdateTerminalRemainsNumerator
import com.example.union_sync_impl.dao.TerminalRemainsNumeratorDao
import com.example.union_sync_impl.dao.sqlTerminalRemainsNumeratorQuery
import com.example.union_sync_impl.data.mapper.toDb
import com.example.union_sync_impl.data.mapper.toSyncEntity
import com.example.union_sync_impl.data.mapper.toUpdate

class TerminalRemainsNumeratorSyncApiImpl(private val terminalRemainsNumeratorDao: TerminalRemainsNumeratorDao) :
    TerminalRemainsNumeratorSyncApi {

    override suspend fun getAllRemainsNumerators(): List<TerminalRemainsNumeratorSyncEntity> {
        return terminalRemainsNumeratorDao.getAllRemainsNumerators(sqlTerminalRemainsNumeratorQuery())
            .map { it.toSyncEntity() }
    }

    override suspend fun getNumeratorByRemainId(id: String): TerminalRemainsNumeratorSyncEntity? {
        return terminalRemainsNumeratorDao.getRemainNumeratorById(id)?.toSyncEntity()
    }

    override suspend fun createTerminalRemainsNumerator(
        numerator: TerminalRemainsNumeratorSyncEntity,
        userInserted: String?
    ) {
        return terminalRemainsNumeratorDao.insert(numerator = numerator.toDb(userInserted))
    }

    override suspend fun updateTerminalRemainsNumerator(update: UpdateTerminalRemainsNumerator) {
        return terminalRemainsNumeratorDao.update(update.toUpdate())
    }
}