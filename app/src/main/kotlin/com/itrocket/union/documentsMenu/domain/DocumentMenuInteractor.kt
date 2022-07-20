package com.itrocket.union.documentsMenu.domain

import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.documentsMenu.domain.dependencies.DocumentMenuRepository
import com.itrocket.union.documentsMenu.domain.entity.DocumentMenuDomain
import kotlinx.coroutines.withContext

class DocumentMenuInteractor(
    private val repository: DocumentMenuRepository,
    private val coreDispatchers: CoreDispatchers
) {

    suspend fun getDocuments(currentDocument: DocumentMenuDomain? = null) =
        withContext(coreDispatchers.io) {
            repository.getDocuments(currentDocument)
        }
}