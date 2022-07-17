package com.itrocket.union.documents.domain.entity

import android.os.Parcelable
import com.example.union_sync_api.entity.DocumentCreateSyncEntity
import com.example.union_sync_api.entity.DocumentReserveCountSyncEntity
import com.example.union_sync_api.entity.DocumentUpdateSyncEntity
import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain
import com.itrocket.union.manual.ParamDomain
import com.itrocket.union.manual.getExploitingId
import com.itrocket.union.manual.getFilterLocationIds
import com.itrocket.union.manual.getMolId
import com.itrocket.union.manual.getOrganizationId
import com.itrocket.union.reserves.domain.entity.ReservesDomain
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class DocumentDomain(
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
    val isDocumentCreated: Boolean
        get() = number != null

    val isStatusCompleted: Boolean
        get() = documentStatus == DocumentStatus.COMPLETED
}

fun DocumentDomain.toUpdateSyncEntity(): DocumentUpdateSyncEntity {
    val organizationId = params.getOrganizationId()
    val molId = params.getMolId()
    val exploitingId = params.getExploitingId()
    val locationIds = params.getFilterLocationIds()
    val trueCompletionDate =
        if (documentStatus == DocumentStatus.COMPLETED) System.currentTimeMillis() else completionDate
    return DocumentUpdateSyncEntity(
        organizationId = organizationId,
        molId = molId,
        exploitingId = exploitingId,
        documentType = documentType.name,
        accountingObjectsIds = accountingObjects.map { it.id },
        creationDate = creationDate,
        locationIds = locationIds,
        id = number.orEmpty(),
        reservesIds = reserves.map {
            DocumentReserveCountSyncEntity(
                id = it.id,
                count = it.itemsCount
            )
        },
        documentStatusId = documentStatusId,
        completionDate = trueCompletionDate,
        documentStatus = documentStatus.name
    )
}

fun DocumentDomain.toCreateSyncEntity(): DocumentCreateSyncEntity {
    val organizationId = params.getOrganizationId()
    val molId = params.getMolId()
    val exploitingId = params.getExploitingId()
    val locationIds = params.getFilterLocationIds()
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
        locationIds = locationIds,
        reservesIds = reserves.map {
            DocumentReserveCountSyncEntity(
                id = it.id,
                count = it.itemsCount
            )
        },
        documentStatusId = documentStatusId,
        documentStatus = documentStatus.name
    )
}