package com.itrocket.union.documents.data

import com.example.union_sync_api.data.AccountingObjectStatusSyncApi
import com.example.union_sync_api.data.ActionRemainsRecordSyncApi
import com.example.union_sync_api.data.DocumentSyncApi
import com.example.union_sync_api.entity.DocumentCreateSyncEntity
import com.example.union_sync_api.entity.DocumentReserveCountSyncEntity
import com.example.union_sync_api.entity.DocumentUpdateReservesSyncEntity
import com.example.union_sync_api.entity.DocumentUpdateSyncEntity
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.accountingObjects.domain.entity.ObjectStatus
import com.itrocket.union.accountingObjects.domain.entity.ObjectStatusType
import com.itrocket.union.documents.data.mapper.map
import com.itrocket.union.documents.domain.dependencies.DocumentRepository
import com.itrocket.union.documents.domain.entity.DocumentDomain
import com.itrocket.union.documents.domain.entity.DocumentTypeDomain
import com.itrocket.union.manual.ParamDomain
import com.itrocket.union.manual.getExploitingId
import com.itrocket.union.manual.getMolId
import com.itrocket.union.manual.getOrganizationId
import com.itrocket.union.manual.toParam
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class DocumentRepositoryImpl(
    private val documentSyncApi: DocumentSyncApi,
    private val coreDispatchers: CoreDispatchers,
    private val statusesSyncApi: AccountingObjectStatusSyncApi,
    private val documentRemainsRecordSyncApi: ActionRemainsRecordSyncApi
) : DocumentRepository {
    override suspend fun getAllDocuments(
        textQuery: String?,
        params: List<ParamDomain>?
    ): Flow<List<DocumentDomain>> {
        return documentSyncApi.getAllDocuments(
            textQuery = textQuery,
            molId = params?.getMolId(),
            exploitingId = params?.getExploitingId(),
            organizationId = params?.getOrganizationId()
        ).map { it.map() }
    }

    override suspend fun getAllDocumentsCount(
        textQuery: String?,
        params: List<ParamDomain>?
    ): Long {
        return withContext(coreDispatchers.io) {
            documentSyncApi.getAllDocumentsCount(
                textQuery = textQuery,
                molId = params?.getMolId(),
                exploitingId = params?.getExploitingId(),
                organizationId = params?.getOrganizationId()
            )
        }
    }

    override suspend fun getDocumentsByType(
        type: DocumentTypeDomain,
        textQuery: String?
    ): Flow<List<DocumentDomain>> {
        return documentSyncApi.getDocumentsByType(type.name, textQuery).map { it.map() }
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

    override suspend fun insertDocumentReserveCount(documentReserveCounts: List<DocumentReserveCountSyncEntity>) {
        return documentRemainsRecordSyncApi.updateCounts(documentReserveCounts)
    }

    override suspend fun getAvailableStatus(): ParamDomain? {
        return statusesSyncApi.getStatuses(ObjectStatusType.AVAILABLE.name).map { it.toParam() }
            .firstOrNull()
    }
}
