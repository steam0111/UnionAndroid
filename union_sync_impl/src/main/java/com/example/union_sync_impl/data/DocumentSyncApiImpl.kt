package com.example.union_sync_impl.data

import com.example.union_sync_api.data.DocumentSyncApi
import com.example.union_sync_api.entity.DocumentCreateSyncEntity
import com.example.union_sync_api.entity.DocumentSyncEntity
import com.example.union_sync_api.entity.DocumentUpdateSyncEntity
import com.example.union_sync_impl.dao.DocumentDao
import com.example.union_sync_impl.data.mapper.toDocumentDb
import com.example.union_sync_impl.data.mapper.toDocumentSyncEntity
import com.example.union_sync_impl.data.mapper.toSyncEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DocumentSyncApiImpl(private val documentDao: DocumentDao) : DocumentSyncApi {
    override suspend fun createDocument(documentCreateSyncEntity: DocumentCreateSyncEntity): Long {
        return documentDao.insert(documentCreateSyncEntity.toDocumentDb())
    }

    override suspend fun getAllDocuments(): Flow<List<DocumentSyncEntity>> {
        return documentDao.getAll().map {
            it.map {
                it.documentDb.toDocumentSyncEntity(
                    organizationSyncEntity = it.organizationDb?.toSyncEntity(),
                    mol = it.molDb?.toSyncEntity(),
                    exploiting = it.exploitingDb?.toSyncEntity()
                )
            }
        }
    }
    override suspend fun getDocuments(type: String): Flow<List<DocumentSyncEntity>> {
        return documentDao.getDocumentsByType(type).map {
            it.map {
                it.documentDb.toDocumentSyncEntity(
                    organizationSyncEntity = it.organizationDb?.toSyncEntity(),
                    mol = it.molDb?.toSyncEntity(),
                    exploiting = it.exploitingDb?.toSyncEntity()
                )
            }
        }
    }


    override suspend fun getDocumentById(id: Long): DocumentSyncEntity {
        val fullDocument = documentDao.getDocumentById(id)
        return fullDocument.documentDb.toDocumentSyncEntity(
            organizationSyncEntity = fullDocument.organizationDb?.toSyncEntity(),
            mol = fullDocument.molDb?.toSyncEntity(),
            exploiting = fullDocument.exploitingDb?.toSyncEntity()
        )
    }

    override suspend fun updateDocument(documentUpdateSyncEntity: DocumentUpdateSyncEntity) {
        documentDao.update(documentUpdateSyncEntity.toDocumentDb())
    }
}