package com.itrocket.union.documentsMenu.domain

import com.itrocket.union.core.CoreDispatchers
import kotlinx.coroutines.withContext
import com.itrocket.union.documentsMenu.domain.dependencies.DocumentMenuRepository

class DocumentMenuInteractor(
    private val repository: DocumentMenuRepository,
    private val coreDispatchers: CoreDispatchers
) {

    suspend fun getDocuments() = withContext(coreDispatchers.io) {
        repository.getDocuments()
    }
}