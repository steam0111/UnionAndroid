package com.example.union_sync_impl.data

import com.example.union_sync_api.data.ActionRecordSyncApi
import com.example.union_sync_api.entity.ActionRecordSyncEntity
import com.example.union_sync_impl.dao.ActionRecordDao
import com.example.union_sync_impl.dao.sqlActionRecordQuery
import com.example.union_sync_impl.data.mapper.toSyncEntity

class ActionRecordSyncApiImpl(private val actionRecordDao: ActionRecordDao) : ActionRecordSyncApi {

    override fun getAll(actionId: String): List<ActionRecordSyncEntity> {
        return actionRecordDao.getAll(sqlActionRecordQuery(actionId = actionId)).map {
            it.toSyncEntity()
        }
    }
}