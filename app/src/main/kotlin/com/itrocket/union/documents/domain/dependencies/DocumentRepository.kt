package com.itrocket.union.documents.domain.dependencies

import com.example.union_sync_api.entity.DocumentCreateSyncEntity
import com.example.union_sync_api.entity.DocumentUpdateSyncEntity
import com.itrocket.union.documents.domain.entity.DocumentDomain
import com.itrocket.union.documents.domain.entity.DocumentTypeDomain

interface DocumentRepository {

    suspend fun getAllDocuments(textQuery: String?): List<DocumentDomain>

    suspend fun getDocuments(
        type: DocumentTypeDomain,
        textQuery: String?
    ): List<DocumentDomain>

    suspend fun getDocumentById(id: Long): DocumentDomain

    suspend fun updateDocument(documentUpdateSyncEntity: DocumentUpdateSyncEntity)

    suspend fun createDocument(documentCreateSyncEntity: DocumentCreateSyncEntity): Long
}