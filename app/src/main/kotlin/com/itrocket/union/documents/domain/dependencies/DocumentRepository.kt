package com.itrocket.union.documents.domain.dependencies

import com.itrocket.union.documents.domain.entity.DocumentDomain

interface DocumentRepository {

    suspend fun getDocuments(): List<DocumentDomain>
}