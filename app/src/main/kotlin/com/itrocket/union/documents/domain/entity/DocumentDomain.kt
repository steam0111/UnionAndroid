package com.itrocket.union.documents.domain.entity

import android.os.Parcelable
import com.example.union_sync_api.entity.DocumentUpdateSyncEntity
import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain
import com.itrocket.union.manual.ParamDomain
import com.itrocket.union.manual.getExploitingId
import com.itrocket.union.manual.getFilterLocationIds
import com.itrocket.union.manual.getMolId
import com.itrocket.union.manual.getOrganizationId
import com.itrocket.union.reserves.domain.entity.ReservesDomain
import kotlinx.parcelize.Parcelize

@Parcelize
data class DocumentDomain(
    val number: String,
    val documentStatus: DocumentStatus,
    val objectType: ObjectType,
    val documentType: DocumentTypeDomain,
    val params: List<ParamDomain>,
    val creationDate: Long,
    val completionDate: Long? = null,
    val accountingObjects: List<AccountingObjectDomain> = listOf(),
    val reserves: List<ReservesDomain> = listOf(),
    val documentStatusId: String,
) : Parcelable

fun DocumentDomain.toUpdateSyncEntity(): DocumentUpdateSyncEntity {
    val organizationId = params.getOrganizationId()
    val molId = params.getMolId()
    val exploitingId = params.getExploitingId()
    val locationIds = params.getFilterLocationIds()
    return DocumentUpdateSyncEntity(
        organizationId = organizationId,
        molId = molId,
        exploitingId = exploitingId,
        documentType = documentType.name,
        accountingObjectsIds = accountingObjects.map { it.id },
        creationDate = creationDate,
        locationIds = locationIds,
        id = number,
        objectType = objectType.name,
        reservesIds = reserves.map { it.id },
        documentStatusId = documentStatusId,
        completionDate = completionDate,
        documentStatus = documentStatus.name
    )
}