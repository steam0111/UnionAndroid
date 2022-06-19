package com.itrocket.union.documents.domain.entity

import android.os.Parcelable
import com.example.union_sync_api.entity.DocumentCreateSyncEntity
import com.example.union_sync_api.entity.DocumentSyncEntity
import com.example.union_sync_api.entity.DocumentUpdateSyncEntity
import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain
import com.itrocket.union.accountingObjects.domain.entity.ObjectStatus
import com.itrocket.union.manual.ManualType
import com.itrocket.union.manual.ParamDomain
import com.itrocket.union.reserves.domain.entity.ReservesDomain
import com.itrocket.union.utils.getStringDateFromMillis
import com.itrocket.union.utils.getTimeFromMillis
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
    val organizationId = requireNotNull(params.find { it.type == ManualType.ORGANIZATION }?.id)
    val molId = requireNotNull(params.find { it.type == ManualType.MOL }?.id)
    val exploitingId = params.find { it.type == ManualType.EXPLOITING }?.id
    return DocumentUpdateSyncEntity(
        organizationId = organizationId,
        molId = molId,
        exploitingId = exploitingId,
        documentType = documentType.name,
        accountingObjectsIds = accountingObjects.map { it.id },
        date = date,
        id = number.toLong()
    )
}