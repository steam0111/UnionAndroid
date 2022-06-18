package com.itrocket.union.documents.domain.dependencies

import com.itrocket.union.documents.domain.entity.DocumentDomain
import com.itrocket.union.documents.domain.entity.DocumentTypeDomain

interface DocumentRepository {

    suspend fun getDocuments(type: DocumentTypeDomain): List<DocumentDomain>
}