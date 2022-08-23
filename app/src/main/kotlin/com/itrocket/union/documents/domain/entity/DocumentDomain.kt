package com.itrocket.union.documents.domain.entity

import android.os.Parcelable
import com.example.union_sync_api.entity.DocumentCreateSyncEntity
import com.example.union_sync_api.entity.ReserveCountSyncEntity
import com.example.union_sync_api.entity.DocumentUpdateSyncEntity
import com.example.union_sync_api.entity.TransitCreateSyncEntity
import com.example.union_sync_api.entity.TransitUpdateSyncEntity
import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain
import com.itrocket.union.manual.ManualType
import com.itrocket.union.manual.ParamDomain
import com.itrocket.union.manual.getActionBaseId
import com.itrocket.union.manual.getExploitingId
import com.itrocket.union.manual.getFilterLocationLastId
import com.itrocket.union.manual.getFilterStructuralLastId
import com.itrocket.union.manual.getMolId
import com.itrocket.union.manual.getReceivingId
import com.itrocket.union.reserves.domain.entity.ReservesDomain
import com.itrocket.union.transit.domain.TransitTypeDomain
import kotlinx.parcelize.Parcelize

@Parcelize
data class DocumentDomain(
    val id: String? = null,
    val number: String?,
    val documentStatus: DocumentStatus,
    val documentType: DocumentTypeDomain,
    val params: List<ParamDomain> = emptyList(),
    val creationDate: Long?,
    val completionDate: Long? = null,
    val accountingObjects: List<AccountingObjectDomain> = listOf(),
    val reserves: List<ReservesDomain> = listOf(),
    val documentStatusId: String,
    val transitType: TransitTypeDomain? = null,
    val userInserted: String?,
    val userUpdated: String?,
) : Parcelable {
    val isDocumentExists: Boolean
        get() = id != null

    val isStatusCompleted: Boolean
        get() = documentStatus == DocumentStatus.COMPLETED
}

fun DocumentDomain.toUpdateSyncEntity(): DocumentUpdateSyncEntity {
    val molId = params.getMolId()
    val exploitingId = params.getExploitingId()
    val trueCompletionDate =
        if (documentStatus == DocumentStatus.COMPLETED) System.currentTimeMillis() else completionDate
    return DocumentUpdateSyncEntity(
        id = id.orEmpty(),
        molId = molId,
        exploitingId = exploitingId,
        documentType = documentType.name,
        accountingObjectsIds = accountingObjects.map { it.id },
        creationDate = creationDate,
        reservesIds = reserves.map {
            ReserveCountSyncEntity(
                id = it.id,
                count = it.itemsCount,
                userUpdated = userUpdated
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
        actionBaseId = params.getActionBaseId(),
        code = number,
        userUpdated = userUpdated,
        userInserted = userInserted,
        structuralToId = params.getFilterStructuralLastId(ManualType.STRUCTURAL_TO),
        structuralFromId = params.getFilterStructuralLastId(ManualType.STRUCTURAL_FROM)
    )
}

fun DocumentDomain.toCreateSyncEntity(): DocumentCreateSyncEntity {
    val molId = params.getMolId()
    val exploitingId = params.getExploitingId()
    val trueCompletionDate = if (documentStatus == DocumentStatus.COMPLETED) {
        System.currentTimeMillis()
    } else {
        completionDate
    }

    return DocumentCreateSyncEntity(
        molId = molId.orEmpty(),
        exploitingId = exploitingId,
        documentType = documentType.name,
        accountingObjectsIds = accountingObjects.map { it.id },
        completionDate = trueCompletionDate,
        reservesIds = reserves.map {
            ReserveCountSyncEntity(
                id = it.id,
                count = it.itemsCount,
                userUpdated = userUpdated
            )
        },
        structuralToId = params.getFilterStructuralLastId(ManualType.STRUCTURAL_TO),
        structuralFromId = params.getFilterStructuralLastId(ManualType.STRUCTURAL_FROM),
        documentStatusId = documentStatusId,
        documentStatus = documentStatus.name,
        locationFromId = params.getFilterLocationLastId(ManualType.LOCATION_FROM),
        locationToId = if (documentType == DocumentTypeDomain.RELOCATION) {
            params.getFilterLocationLastId(ManualType.RELOCATION_LOCATION_TO)
        } else {
            params.getFilterLocationLastId(ManualType.LOCATION_TO)
        },
        actionBaseId = params.getActionBaseId(),
        code = number,
        userInserted = userInserted,
        userUpdated = userUpdated
    )
}

fun DocumentDomain.toTransitUpdateSyncEntity(): TransitUpdateSyncEntity {
    val molId = params.getMolId()
    val trueCompletionDate =
        if (documentStatus == DocumentStatus.COMPLETED) System.currentTimeMillis() else completionDate
    return TransitUpdateSyncEntity(
        id = id.orEmpty(),
        molId = molId,
        accountingObjectsIds = accountingObjects.map { it.id },
        creationDate = creationDate ?: System.currentTimeMillis(),
        reservesIds = reserves.map {
            ReserveCountSyncEntity(
                id = it.id,
                count = it.itemsCount,
                userUpdated = userUpdated
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
        code = number,
        transitType = transitType?.name.orEmpty(),
        transitStatusId = documentStatusId,
        transitStatus = documentStatus.name,
        receivingId = params.getReceivingId(),
        responsibleId = params.getMolId(),
        vehicleId = params.getFilterLocationLastId(ManualType.TRANSIT),
        structuralUnitFromId = params.getFilterStructuralLastId(ManualType.STRUCTURAL_FROM),
        structuralUnitToId = params.getFilterStructuralLastId(ManualType.STRUCTURAL_TO),
        userUpdated = userUpdated,
        userInserted = userInserted,
    )
}

fun DocumentDomain.toTransitCreateSyncEntity(): TransitCreateSyncEntity {
    return TransitCreateSyncEntity(
        accountingObjectsIds = accountingObjects.map { it.id },
        reservesIds = reserves.map {
            ReserveCountSyncEntity(
                id = it.id,
                count = it.itemsCount,
                userUpdated = userUpdated
            )
        },
        locationFromId = params.getFilterLocationLastId(ManualType.LOCATION_FROM),
        locationToId = params.getFilterLocationLastId(ManualType.LOCATION_TO),
        vehicleId = params.getFilterLocationLastId(ManualType.TRANSIT),
        code = number,
        transitStatus = documentStatus.name,
        transitStatusId = documentStatusId,
        transitType = transitType?.name.orEmpty(),
        receivingId = params.getReceivingId(),
        responsibleId = params.getMolId(),
        structuralUnitFromId = params.getFilterStructuralLastId(ManualType.STRUCTURAL_FROM),
        structuralUnitToId = params.getFilterStructuralLastId(ManualType.STRUCTURAL_TO),
        userUpdated = userUpdated,
        userInserted = userInserted,
    )
}