package com.example.union_sync_api.data

import com.example.union_sync_api.entity.DocumentCreateSyncEntity
import com.example.union_sync_api.entity.DocumentSyncEntity
import com.example.union_sync_api.entity.DocumentUpdateSyncEntity
import kotlinx.coroutines.flow.Flow

interface DocumentSyncApi {
    suspend fun getAllDocuments(
        textQuery: String? = null,
        molId: String? = null,
        exploitingId: String?,
        organizationId: String? = null
    ): Flow<List<DocumentSyncEntity>>

    suspend fun createDocument(documentCreateSyncEntity: DocumentCreateSyncEntity): String
    suspend fun getDocuments(
        type: String,
        textQuery: String? = null
    ): Flow<List<DocumentSyncEntity>>

    suspend fun getDocumentById(id: String): DocumentSyncEntity
    suspend fun updateDocument(documentUpdateSyncEntity: DocumentUpdateSyncEntity)
}