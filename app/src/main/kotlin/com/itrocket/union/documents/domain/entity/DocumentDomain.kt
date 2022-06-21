package com.itrocket.union.documents.domain.entity

import android.os.Parcelable
import com.example.union_sync_api.entity.DocumentUpdateSyncEntity
import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain
import com.itrocket.union.manual.LocationParamDomain
import com.itrocket.union.manual.ManualType
import com.itrocket.union.manual.ParamDomain
import com.itrocket.union.manual.getExploitingId
import com.itrocket.union.manual.getLocationIds
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
    val date: Long,
    val accountingObjects: List<AccountingObjectDomain> = listOf(),
    val reserves: List<ReservesDomain> = listOf(),
) : Parcelable

fun DocumentDomain.toUpdateSyncEntity(): DocumentUpdateSyncEntity {
    val organizationId = requireNotNull(params.getOrganizationId())
    val molId = requireNotNull(params.getMolId())
    val exploitingId = params.getExploitingId()
    val locationIds = params.getLocationIds()
    return DocumentUpdateSyncEntity(
        organizationId = organizationId,
        molId = molId,
        exploitingId = exploitingId,
        documentType = documentType.name,
        accountingObjectsIds = accountingObjects.map { it.id },
        date = date,
        locationIds = locationIds,
        id = number.toLong()
    )
}