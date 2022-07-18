package com.example.union_sync_impl.data

import com.example.union_sync_api.data.ActionBaseSyncApi
import com.example.union_sync_api.entity.ActionBaseSyncEntity
import com.example.union_sync_impl.dao.ActionBaseDao
import com.example.union_sync_impl.dao.sqlActionBaseQuery
import com.example.union_sync_impl.data.mapper.toSyncEntity

class ActionBaseSyncApiImpl(
    private val actionBaseDao: ActionBaseDao
) : ActionBaseSyncApi {
    override suspend fun getActionBases(textQuery: String?): List<ActionBaseSyncEntity> {
        return actionBaseDao.getAll(sqlActionBaseQuery(textQuery)).map { it.toSyncEntity() }
    }
}