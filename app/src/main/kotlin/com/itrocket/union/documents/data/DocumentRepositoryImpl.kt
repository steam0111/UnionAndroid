package com.itrocket.union.documents.data

import com.example.union_sync_api.data.DocumentSyncApi
import com.example.union_sync_api.entity.DocumentCreateSyncEntity
import com.example.union_sync_api.entity.DocumentUpdateSyncEntity
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.documents.data.mapper.map
import com.itrocket.union.documents.domain.dependencies.DocumentRepository
import com.itrocket.union.documents.domain.entity.DocumentDomain
import com.itrocket.union.documents.domain.entity.DocumentTypeDomain
import kotlinx.coroutines.withContext

class DocumentRepositoryImpl(
    private val documentSyncApi: DocumentSyncApi,
    private val coreDispatchers: CoreDispatchers
) : DocumentRepository {
    override suspend fun getAllDocuments(textQuery: String?): List<DocumentDomain> =
        withContext(coreDispatchers.io) {
            documentSyncApi.getAllDocuments(textQuery).map { it.map() }
        }

    override suspend fun getDocuments(
        type: DocumentTypeDomain,
        textQuery: String?
    ): List<DocumentDomain> = withContext(coreDispatchers.io) {
        documentSyncApi.getDocuments(type.name, textQuery).map { it.map() }
    }

    override suspend fun getDocumentById(id: Long): DocumentDomain {
        return documentSyncApi.getDocumentById(id).map()
    }

    override suspend fun updateDocument(documentUpdateSyncEntity: DocumentUpdateSyncEntity) {
        return documentSyncApi.updateDocument(documentUpdateSyncEntity)
    }

    override suspend fun createDocument(documentCreateSyncEntity: DocumentCreateSyncEntity): Long {
        return documentSyncApi.createDocument(documentCreateSyncEntity)
    }
}
