package com.itrocket.union.documents.domain.entity

import android.os.Parcelable
import com.example.union_sync_api.entity.DocumentCreateSyncEntity
import com.example.union_sync_api.entity.DocumentReserveCountSyncEntity
import com.example.union_sync_api.entity.DocumentUpdateSyncEntity
import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain
import com.itrocket.union.manual.ManualType
import com.itrocket.union.manual.ParamDomain
import com.itrocket.union.manual.getActionBaseId
import com.itrocket.union.manual.getBranchId
import com.itrocket.union.manual.getDepartmentId
import com.itrocket.union.manual.getExploitingId
import com.itrocket.union.manual.getFilterLocationLastId
import com.itrocket.union.manual.getMolId
import com.itrocket.union.manual.getOrganizationId
import com.itrocket.union.reserves.domain.entity.ReservesDomain
import kotlinx.parcelize.Parcelize

@Parcelize
data class DocumentDomain(
    val id: String? = null,
    val number: String?,
    val documentStatus: DocumentStatus,
    val documentType: DocumentTypeDomain,
    val params: List<ParamDomain> = emptyList(),
    val creationDate: Long,
    val completionDate: Long? = null,
    val accountingObjects: List<AccountingObjectDomain> = listOf(),
    val reserves: List<ReservesDomain> = listOf(),
    val documentStatusId: String,
) : Parcelable {
    val isDocumentExists: Boolean
        get() = id != null

    val isStatusCompleted: Boolean
        get() = documentStatus == DocumentStatus.COMPLETED
}

fun DocumentDomain.toUpdateSyncEntity(): DocumentUpdateSyncEntity {
    val organizationId = params.getOrganizationId()
    val molId = params.getMolId()
    val exploitingId = params.getExploitingId()
    val trueCompletionDate =
        if (documentStatus == DocumentStatus.COMPLETED) System.currentTimeMillis() else completionDate
    return DocumentUpdateSyncEntity(
        id = id.orEmpty(),
        organizationId = organizationId,
        molId = molId,
        exploitingId = exploitingId,
        documentType = documentType.name,
        accountingObjectsIds = accountingObjects.map { it.id },
        creationDate = creationDate,
        reservesIds = reserves.map {
            DocumentReserveCountSyncEntity(
                id = it.id,
                count = it.itemsCount
            )
        },
        documentStatusId = documentStatusId,
        completionDate = trueCompletionDate,
        documentStatus = documentStatus.name,
        locationFromId = params.getFilterLocationLastId(ManualType.LOCATION_FROM),
        locationToId = if (documentType == DocumentTypeDomain.RELOCATION) {
            params.getFilterLocationLastId(ManualType.RELOCATION_LOCATION_TO)
        } else {
            params.getFilterLocationLastId(ManualType.LOCATION_TO)
        },
        departmentFromId = params.getDepartmentId(type = ManualType.DEPARTMENT_FROM),
        departmentToId = params.getDepartmentId(type = ManualType.DEPARTMENT_TO),
        branchId = params.getBranchId(),
        actionBaseId = params.getActionBaseId(),
        code = number
    )
}

fun DocumentDomain.toCreateSyncEntity(): DocumentCreateSyncEntity {
    val organizationId = params.getOrganizationId()
    val molId = params.getMolId()
    val exploitingId = params.getExploitingId()
    val trueCompletionDate = if (documentStatus == DocumentStatus.COMPLETED) {
        System.currentTimeMillis()
    } else {
        completionDate
    }

    return DocumentCreateSyncEntity(
        organizationId = organizationId.orEmpty(),
        molId = molId.orEmpty(),
        exploitingId = exploitingId,
        documentType = documentType.name,
        accountingObjectsIds = accountingObjects.map { it.id },
        creationDate = creationDate,
        completionDate = trueCompletionDate,
        reservesIds = reserves.map {
            DocumentReserveCountSyncEntity(
                id = it.id,
                count = it.itemsCount
            )
        },
        documentStatusId = documentStatusId,
        documentStatus = documentStatus.name,
        locationFromId = params.getFilterLocationLastId(ManualType.LOCATION_FROM),
        locationToId = if (documentType == DocumentTypeDomain.RELOCATION) {
            params.getFilterLocationLastId(ManualType.RELOCATION_LOCATION_TO)
        } else {
            params.getFilterLocationLastId(ManualType.LOCATION_TO)
        },
        departmentFromId = params.getDepartmentId(type = ManualType.DEPARTMENT_FROM),
        departmentToId = params.getDepartmentId(type = ManualType.DEPARTMENT_TO),
        branchId = params.getBranchId(),
        actionBaseId = params.getActionBaseId(),
        code = number
    )
}