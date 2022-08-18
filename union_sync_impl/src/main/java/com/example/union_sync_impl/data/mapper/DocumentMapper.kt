package com.example.union_sync_impl.data.mapper

import com.example.union_sync_api.entity.AccountingObjectSyncEntity
import com.example.union_sync_api.entity.ActionBaseSyncEntity
import com.example.union_sync_api.entity.DocumentCreateSyncEntity
import com.example.union_sync_api.entity.DocumentSyncEntity
import com.example.union_sync_api.entity.DocumentUpdateSyncEntity
import com.example.union_sync_api.entity.EmployeeSyncEntity
import com.example.union_sync_api.entity.LocationSyncEntity
import com.example.union_sync_api.entity.ReserveSyncEntity
import com.example.union_sync_api.entity.StructuralSyncEntity
import com.example.union_sync_impl.entity.DocumentDb
import com.example.union_sync_impl.utils.getMillisDateFromServerFormat
import com.example.union_sync_impl.utils.getStringDateFromMillis
import org.openapitools.client.models.ActionDtoV2

fun ActionDtoV2.toDocumentDb(): DocumentDb {
    return DocumentDb(
        id = id,
        structuralFromId = structuralUnitFromId,
        structuralToId = structuralUnitToId,
        molId = molReceivingId.orEmpty(),
        exploitingId = exploitingId,
        documentType = actionTypeId ?: "GIVE",
        completionDate = getMillisDateFromServerFormat(completionDate),
        creationDate = getMillisDateFromServerFormat(creationDate),
        documentStatus = extendedActionStatus?.id.orEmpty(),
        documentStatusId = actionStatusId.orEmpty(),
        updateDate = getMillisDateFromServerFormat(dateUpdate),
        locationFromId = locationFromId,
        locationToId = locationToId,
        actionBaseId = actionBaseId,
        code = code,
        userUpdated = userUpdated,
        userInserted = userInserted,
    )
}

fun DocumentDb.toActionDtoV2(): ActionDtoV2 {
    return ActionDtoV2(
        structuralUnitFromId = structuralFromId,
        structuralUnitToId = structuralToId,
        molId = molId.orEmpty(),
        exploitingId = exploitingId,
        actionTypeId = documentType,
        actionBaseId = actionBaseId,
        locationToId = locationToId,
        locationFromId = locationFromId,
        actionStatusId = documentStatus,
        creationDate = getStringDateFromMillis(creationDate),
        dateUpdate = getStringDateFromMillis(updateDate),
        id = id,
        deleted = false,
        userInserted = userInserted,
        userUpdated = userUpdated,
        molReceivingId = molId.orEmpty()
    )
}

fun DocumentCreateSyncEntity.toDocumentDb(id: String): DocumentDb {
    return DocumentDb(
        id = id,
        molId = molId,
        exploitingId = exploitingId,
        documentType = documentType,
        locationToId = locationToId,
        locationFromId = locationFromId,
        actionBaseId = actionBaseId,
        creationDate = System.currentTimeMillis(),
        documentStatus = documentStatus,
        documentStatusId = documentStatusId,
        updateDate = System.currentTimeMillis(),
        code = code,
        userUpdated = userUpdated,
        userInserted = userInserted,
        structuralFromId = structuralFromId,
        structuralToId = structuralToId,
    )
}

fun DocumentUpdateSyncEntity.toDocumentDb(): DocumentDb {
    return DocumentDb(
        id = id,
        molId = molId,
        exploitingId = exploitingId,
        documentType = documentType,
        locationToId = locationToId,
        locationFromId = locationFromId,
        actionBaseId = actionBaseId,
        documentStatusId = documentStatusId,
        documentStatus = documentStatus,
        creationDate = creationDate,
        completionDate = completionDate,
        updateDate = System.currentTimeMillis(),
        code = code,
        userUpdated = userUpdated,
        userInserted = userInserted,
        structuralFromId = structuralFromId,
        structuralToId = structuralToId,
    )
}

fun DocumentDb.toDocumentSyncEntity(
    structuralFromSyncEntity: List<StructuralSyncEntity>?,
    structuralToSyncEntity: List<StructuralSyncEntity>?,
    mol: EmployeeSyncEntity?,
    exploiting: EmployeeSyncEntity?,
    accountingObjects: List<AccountingObjectSyncEntity>? = null,
    reserves: List<ReserveSyncEntity>? = null,
    locationFrom: LocationSyncEntity?,
    locationTo: LocationSyncEntity?,
    actionBase: ActionBaseSyncEntity? = null
): DocumentSyncEntity {
    return DocumentSyncEntity(
        id = id,
        structuralFromSyncEntities = structuralFromSyncEntity,
        structuralToSyncEntities = structuralToSyncEntity,
        mol = mol,
        exploiting = exploiting,
        documentType = documentType,
        accountingObjects = accountingObjects.orEmpty(),
        reserves = reserves.orEmpty(),
        documentStatus = documentStatus,
        documentStatusId = documentStatusId,
        completionDate = completionDate,
        creationDate = creationDate,
        locationFrom = locationFrom,
        locationTo = locationTo,
        actionBase = actionBase,
        code = code,
        userInserted = userInserted,
        userUpdated = userUpdated
    )
}