package com.example.union_sync_impl.data

import com.example.union_sync_api.data.DocumentReserveCountSyncApi
import com.example.union_sync_api.entity.DocumentReserveCountSyncEntity
import com.example.union_sync_impl.dao.DocumentReserveCountDao
import com.example.union_sync_impl.dao.sqlDocumentReserveCountQuery
import com.example.union_sync_impl.data.mapper.toDocumentReserveCountDb
import com.example.union_sync_impl.data.mapper.toSyncEntity

class DocumentReserveCountSyncApiImpl(
    private val documentReserveCountDao: DocumentReserveCountDao
) : DocumentReserveCountSyncApi {
    override suspend fun getAll(reserveIds: List<String>?): List<DocumentReserveCountSyncEntity> {
        return documentReserveCountDao.getAll(sqlDocumentReserveCountQuery(reserveIds = reserveIds))
            .map {
                it.toSyncEntity()
            }
    }

    override suspend fun insertAll(documentReserveCountSyncEntities: List<DocumentReserveCountSyncEntity>) {
        documentReserveCountDao.insertAll(documentReserveCountSyncEntities.map { it.toDocumentReserveCountDb() })
    }
}