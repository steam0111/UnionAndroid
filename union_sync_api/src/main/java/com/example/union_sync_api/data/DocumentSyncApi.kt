package com.example.union_sync_api.data

import com.example.union_sync_api.entity.DocumentCreateSyncEntity
import com.example.union_sync_api.entity.DocumentSyncEntity
import com.example.union_sync_api.entity.DocumentUpdateReservesSyncEntity
import com.example.union_sync_api.entity.DocumentUpdateSyncEntity
import kotlinx.coroutines.flow.Flow

interface DocumentSyncApi {
    suspend fun getDocumentsCount(
        type: String,
        textQuery: String? = null,
        molId: String? = null,
        exploitingId: String?,
        structuralFromId: String? = null,
        structuralToId: String? = null,
        code: String? = null
    ): Long

    suspend fun createDocument(documentCreateSyncEntity: DocumentCreateSyncEntity): String
    suspend fun getDocumentsByType(
        type: String,
        textQuery: String? = null,
        molId: String? = null,
        exploitingId: String?,
        structuralFromId: String? = null,
        structuralToId: String? = null,
        code: String? = null
    ): Flow<List<DocumentSyncEntity>>

    suspend fun getDocumentById(id: String): DocumentSyncEntity
    suspend fun updateDocument(documentUpdateSyncEntity: DocumentUpdateSyncEntity)
    suspend fun updateDocumentReserves(documentUpdateReservesSyncEntity: DocumentUpdateReservesSyncEntity)
    suspend fun getDocumentsCodes(number: String, documentType: String?): List<String>
}