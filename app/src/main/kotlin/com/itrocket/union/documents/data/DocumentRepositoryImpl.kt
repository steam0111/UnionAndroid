package com.itrocket.union.documents.data

import com.example.union_sync_api.data.ActionRemainsRecordSyncApi
import com.example.union_sync_api.data.DocumentSyncApi
import com.example.union_sync_api.data.TransitSyncApi
import com.example.union_sync_api.entity.DocumentCreateSyncEntity
import com.example.union_sync_api.entity.DocumentUpdateReservesSyncEntity
import com.example.union_sync_api.entity.DocumentUpdateSyncEntity
import com.example.union_sync_api.entity.ReserveCountSyncEntity
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.documents.data.mapper.map
import com.itrocket.union.documents.domain.dependencies.DocumentRepository
import com.itrocket.union.documents.domain.entity.DocumentDomain
import com.itrocket.union.documents.domain.entity.DocumentTypeDomain
import com.itrocket.union.manual.ManualType
import com.itrocket.union.manual.ParamDomain
import com.itrocket.union.manual.getDocumentCode
import com.itrocket.union.manual.getExploitingId
import com.itrocket.union.manual.getFilterStructuralLastId
import com.itrocket.union.manual.getMolId
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class DocumentRepositoryImpl(
    private val documentSyncApi: DocumentSyncApi,
    private val coreDispatchers: CoreDispatchers,
    private val documentRemainsRecordSyncApi: ActionRemainsRecordSyncApi,
    private val transitSyncApi: TransitSyncApi,
) : DocumentRepository {

    override suspend fun getDocumentsCount(
        type: DocumentTypeDomain,
        textQuery: String?,
        params: List<ParamDomain>?
    ): Long {
        return withContext(coreDispatchers.io) {
            documentSyncApi.getDocumentsCount(
                textQuery = textQuery,
                type = type.name,
                molId = params?.getMolId(),
                exploitingId = params?.getExploitingId(),
                structuralFromId = params?.getFilterStructuralLastId(ManualType.STRUCTURAL_FROM),
                structuralToId = params?.getFilterStructuralLastId(ManualType.STRUCTURAL_TO),
                code = params?.getDocumentCode()
            )
        }
    }

    override suspend fun getDocumentsByType(
        type: DocumentTypeDomain,
        textQuery: String?,
        params: List<ParamDomain>?
    ): Flow<List<DocumentDomain>> {
        return documentSyncApi.getDocumentsByType(
            type = type.name,
            textQuery = textQuery,
            molId = params?.getMolId(),
            exploitingId = params?.getExploitingId(),
            structuralFromId = params?.getFilterStructuralLastId(ManualType.STRUCTURAL_FROM),
            structuralToId = params?.getFilterStructuralLastId(ManualType.STRUCTURAL_TO),
            code = params?.getDocumentCode()
        ).map { it.map() }
    }

    override suspend fun getTransitDocument(textQuery: String?): Flow<List<DocumentDomain>> {
        return transitSyncApi.getAllTransit(textQuery = textQuery).map {
            it.map {
                it.map()
            }
        }
    }

    override suspend fun getDocumentById(id: String): DocumentDomain {
        return documentSyncApi.getDocumentById(id).map()
    }

    override suspend fun updateDocument(documentUpdateSyncEntity: DocumentUpdateSyncEntity) {
        return documentSyncApi.updateDocument(documentUpdateSyncEntity)
    }

    override suspend fun updateDocumentReserves(documentUpdateReservesSyncEntity: DocumentUpdateReservesSyncEntity) {
        return documentSyncApi.updateDocumentReserves(documentUpdateReservesSyncEntity)
    }

    override suspend fun createDocument(documentCreateSyncEntity: DocumentCreateSyncEntity): String {
        return documentSyncApi.createDocument(documentCreateSyncEntity)
    }

    override suspend fun insertDocumentReserveCount(documentReserveCounts: List<ReserveCountSyncEntity>) {
        return documentRemainsRecordSyncApi.updateCounts(documentReserveCounts)
    }
}
