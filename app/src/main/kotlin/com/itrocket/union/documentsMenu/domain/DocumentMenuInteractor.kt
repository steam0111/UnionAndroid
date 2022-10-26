package com.itrocket.union.documentsMenu.domain

import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.authMain.domain.AuthMainInteractor
import com.itrocket.union.documentsMenu.domain.dependencies.DocumentMenuRepository
import com.itrocket.union.documentsMenu.domain.entity.DocumentMenuDomain
import com.itrocket.union.employeeDetail.domain.EmployeeDetailInteractor
import com.itrocket.union.unionPermissions.domain.UnionPermissionsInteractor
import com.itrocket.union.unionPermissions.domain.UnionPermissionsInteractor.Companion.PERMISSION_TAG
import com.itrocket.union.unionPermissions.domain.entity.UnionPermission
import kotlinx.coroutines.withContext
import timber.log.Timber

class DocumentMenuInteractor(
    private val repository: DocumentMenuRepository,
    private val coreDispatchers: CoreDispatchers,
    private val permissionsInteractor: UnionPermissionsInteractor,
    private val authMainInteractor: AuthMainInteractor,
    private val employeeDetailInteractor: EmployeeDetailInteractor
) {

    suspend fun getDocuments(currentDocument: DocumentMenuDomain? = null) =
        withContext(coreDispatchers.io) {
            filterByPermission(repository.getDocuments(currentDocument))
        }

    private suspend fun filterByPermission(documents: List<DocumentMenuDomain>): List<DocumentMenuDomain> {
        Timber.tag(PERMISSION_TAG)
            .d("documents $documents")

        return documents.filter { documentMenuDomain ->
            val isPermitted = isDocumentMenuPermitted(documentMenuDomain)
            Timber.tag(PERMISSION_TAG)
                .d("permission ${documentMenuDomain.unionPermission} : $isPermitted")
            return@filter isPermitted
        }
    }

    private suspend fun isDocumentMenuPermitted(documentMenuDomain: DocumentMenuDomain): Boolean {
        return when {
            documentMenuDomain.customAction != null -> {
                permissionsInteractor.canMakeAction(
                    unionPermission = documentMenuDomain.unionPermission,
                    action = documentMenuDomain.customAction
                )
            }
            documentMenuDomain.unionPermission == UnionPermission.INVENTORY -> {
                permissionsInteractor.canRead(documentMenuDomain.unionPermission) || permissionsInteractor.canCreate(
                    documentMenuDomain.unionPermission
                )
            }
            else -> permissionsInteractor.canRead(documentMenuDomain.unionPermission)
        }
    }

    suspend fun getUsername(currentEmployeeId: String?) = kotlin.runCatching {
        if (currentEmployeeId != null) {
            employeeDetailInteractor.getEmployeeDetail(currentEmployeeId)
        } else {
            authMainInteractor.getLogin()
            null
        }
    }.getOrNull()
}