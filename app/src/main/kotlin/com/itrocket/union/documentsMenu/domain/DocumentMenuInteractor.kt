package com.itrocket.union.documentsMenu.domain

import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.documentsMenu.domain.dependencies.DocumentMenuRepository
import com.itrocket.union.documentsMenu.domain.entity.DocumentMenuDomain
import com.itrocket.union.unionPermissions.domain.UnionPermissionsInteractor
import com.itrocket.union.unionPermissions.domain.UnionPermissionsInteractor.Companion.PERMISSION_TAG
import kotlinx.coroutines.withContext
import timber.log.Timber

class DocumentMenuInteractor(
    private val repository: DocumentMenuRepository,
    private val coreDispatchers: CoreDispatchers,
    private val permissionsInteractor: UnionPermissionsInteractor
) {

    suspend fun getDocuments(currentDocument: DocumentMenuDomain? = null) =
        withContext(coreDispatchers.io) {
            filterByReadPermission(repository.getDocuments(currentDocument))
        }

    private suspend fun filterByReadPermission(documents: List<DocumentMenuDomain>): List<DocumentMenuDomain> {
        Timber.tag(PERMISSION_TAG)
            .d("documents $documents")

        return documents.filter { documentMenuDomain ->
            val canRead = permissionsInteractor.canRead(documentMenuDomain.unionPermission)
            Timber.tag(PERMISSION_TAG)
                .d("permission ${documentMenuDomain.unionPermission} : $canRead")
            return@filter canRead
        }
    }
}