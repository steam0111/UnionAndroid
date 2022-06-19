package com.example.union_sync_api.data

import com.example.union_sync_api.entity.DocumentCreateSyncEntity
import com.example.union_sync_api.entity.DocumentSyncEntity
import com.example.union_sync_api.entity.DocumentUpdateSyncEntity
import kotlinx.coroutines.flow.Flow

interface DocumentSyncApi {
    suspend fun getAllDocuments(): Flow<List<DocumentSyncEntity>>
    suspend fun createDocument(documentCreateSyncEntity: DocumentCreateSyncEntity): Long
    suspend fun getDocuments(type: String): Flow<List<DocumentSyncEntity>>
    suspend fun getDocumentById(id: Long): DocumentSyncEntity
    suspend fun updateDocument(documentUpdateSyncEntity: DocumentUpdateSyncEntity)
}