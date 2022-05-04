package com.itrocket.union.documentsMenu.domain.dependencies

import com.itrocket.union.documentsMenu.domain.entity.DocumentMenuDomain

interface DocumentMenuRepository {
    suspend fun getDocuments(): List<DocumentMenuDomain>
}