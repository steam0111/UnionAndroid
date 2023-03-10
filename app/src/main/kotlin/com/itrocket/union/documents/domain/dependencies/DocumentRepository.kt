package com.itrocket.union.documents.domain.dependencies

import com.example.union_sync_api.entity.DocumentCreateSyncEntity
import com.example.union_sync_api.entity.DocumentUpdateReservesSyncEntity
import com.example.union_sync_api.entity.DocumentUpdateSyncEntity
import com.example.union_sync_api.entity.ReserveCountSyncEntity
import com.itrocket.union.documents.domain.entity.DocumentDomain
import com.itrocket.union.documents.domain.entity.DocumentTypeDomain
import com.itrocket.union.manual.ParamDomain
import kotlinx.coroutines.flow.Flow

interface DocumentRepository {

    suspend fun getDocumentsCount(
        type: DocumentTypeDomain,
        textQuery: String? = null,
        params: List<ParamDomain>?
    ): Long

    suspend fun getDocumentsByType(
        type: DocumentTypeDomain,
        textQuery: String?,
        params: List<ParamDomain>?
    ): Flow<List<DocumentDomain>>

    suspend fun getTransitDocument(textQuery: String?): Flow<List<DocumentDomain>>

    suspend fun getDocumentById(id: String): DocumentDomain

    suspend fun updateDocument(documentUpdateSyncEntity: DocumentUpdateSyncEntity)

    suspend fun createDocument(documentCreateSyncEntity: DocumentCreateSyncEntity): String

    suspend fun updateDocumentReserves(documentUpdateReservesSyncEntity: DocumentUpdateReservesSyncEntity)

    suspend fun insertDocumentReserveCount(documentReserveCounts: List<ReserveCountSyncEntity>)
}