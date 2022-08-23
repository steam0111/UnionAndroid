package com.example.union_sync_impl.data

import com.example.union_sync_api.data.ActionRemainsRecordSyncApi
import com.example.union_sync_api.entity.ActionRemainsRecordSyncEntity
import com.example.union_sync_api.entity.ReserveCountSyncEntity
import com.example.union_sync_impl.dao.ActionRemainsRecordDao
import com.example.union_sync_impl.dao.sqlActionRemainsRecordQuery
import com.example.union_sync_impl.data.mapper.toDocumentReserveCount
import com.example.union_sync_impl.data.mapper.toSyncEntity

class ActionRemainsRecordSyncApiImpl(private val actionRemainsRecordDao: ActionRemainsRecordDao) :
    ActionRemainsRecordSyncApi {

    override fun getAll(actionId: String?): List<ActionRemainsRecordSyncEntity> {
        return actionRemainsRecordDao.getAll(sqlActionRemainsRecordQuery(actionId = actionId))
            .map { it.toSyncEntity() }
    }

    override suspend fun updateCounts(list: List<ReserveCountSyncEntity>) {
        return actionRemainsRecordDao.update(list.map { it.toDocumentReserveCount() })
    }
}