package com.example.union_sync_impl.data.mapper

import com.example.union_sync_api.entity.AccountingObjectSyncEntity
import com.example.union_sync_api.entity.ActionBaseSyncEntity
import com.example.union_sync_api.entity.BranchSyncEntity
import com.example.union_sync_api.entity.DepartmentSyncEntity
import com.example.union_sync_api.entity.DocumentCreateSyncEntity
import com.example.union_sync_api.entity.DocumentSyncEntity
import com.example.union_sync_api.entity.DocumentUpdateSyncEntity
import com.example.union_sync_api.entity.EmployeeSyncEntity
import com.example.union_sync_api.entity.LocationSyncEntity
import com.example.union_sync_api.entity.OrganizationSyncEntity
import com.example.union_sync_api.entity.ReserveSyncEntity
import com.example.union_sync_impl.entity.DocumentDb
import com.example.union_sync_impl.utils.getMillisDateFromServerFormat
import com.example.union_sync_impl.utils.getStringDateFromMillis
import org.openapitools.client.models.ActionDtoV2

fun ActionDtoV2.toDocumentDb(): DocumentDb {
    return DocumentDb(
        id = id,
        organizationId = organizationId.orEmpty(),
        molId = molId.orEmpty(),
        exploitingId = exploitingId,
        documentType = actionTypeId ?: "GIVE",
        completionDate = getMillisDateFromServerFormat(completionDate),
        creationDate = getMillisDateFromServerFormat(creationDate) ?: System.currentTimeMillis(),
        documentStatus = extendedActionStatus?.id.orEmpty(),
        documentStatusId = actionStatusId.orEmpty(),
        updateDate = System.currentTimeMillis(),
        locationFromId = locationFromId,
        locationToId = locationToId,
        departmentFromId = departmentFromId,
        departmentToId = departmentToId,
        actionBaseId = actionBaseId,
        code = code,
        userUpdated = userUpdated,
        userInserted = userInserted
    )
}

fun DocumentDb.toActionDtoV2(): ActionDtoV2 {
    return ActionDtoV2(
        organizationId = organizationId.orEmpty(),
        molId = molId.orEmpty(),
        exploitingId = exploitingId,
        actionTypeId = documentType,
        actionBaseId = actionBaseId,
        locationToId = locationToId,
        locationFromId = locationFromId,
        departmentFromId = departmentFromId,
        departmentToId = departmentToId,
        actionStatusId = documentStatus,
        creationDate = getStringDateFromMillis(creationDate),
        dateUpdate = getStringDateFromMillis(System.currentTimeMillis()),
        id = id,
        deleted = false,
        userInserted = userInserted,
        userUpdated = userUpdated
    )
}

fun DocumentCreateSyncEntity.toDocumentDb(id: String): DocumentDb {
    return DocumentDb(
        id = id,
        organizationId = organizationId,
        molId = molId,
        exploitingId = exploitingId,
        documentType = documentType,
        locationToId = locationToId,
        locationFromId = locationFromId,
        departmentFromId = departmentFromId,
        departmentToId = departmentToId,
        branchId = branchId,
        actionBaseId = actionBaseId,
        creationDate = System.currentTimeMillis(),
        documentStatus = documentStatus,
        documentStatusId = documentStatusId,
        updateDate = System.currentTimeMillis(),
        code = code,
        userUpdated = userUpdated,
        userInserted = userInserted
    )
}

fun DocumentUpdateSyncEntity.toDocumentDb(): DocumentDb {
    return DocumentDb(
        id = id,
        organizationId = organizationId,
        molId = molId,
        exploitingId = exploitingId,
        documentType = documentType,
        locationToId = locationToId,
        locationFromId = locationFromId,
        departmentFromId = departmentFromId,
        departmentToId = departmentToId,
        branchId = branchId,
        actionBaseId = actionBaseId,
        documentStatusId = documentStatusId,
        documentStatus = documentStatus,
        creationDate = creationDate,
        completionDate = completionDate,
        updateDate = System.currentTimeMillis(),
        code = code,
        userUpdated = userUpdated,
        userInserted = userInserted
    )
}

fun DocumentDb.toDocumentSyncEntity(
    organizationSyncEntity: OrganizationSyncEntity?,
    mol: EmployeeSyncEntity?,
    exploiting: EmployeeSyncEntity?,
    accountingObjects: List<AccountingObjectSyncEntity>? = null,
    reserves: List<ReserveSyncEntity>? = null,
    locationFrom: LocationSyncEntity?,
    locationTo: LocationSyncEntity?,
    departmentFrom: DepartmentSyncEntity? = null,
    departmentTo: DepartmentSyncEntity? = null,
    branch: BranchSyncEntity? = null,
    actionBase: ActionBaseSyncEntity? = null
): DocumentSyncEntity {
    return DocumentSyncEntity(
        id = id,
        organizationSyncEntity = organizationSyncEntity,
        mol = mol,
        exploiting = exploiting,
        documentType = documentType,
        accountingObjects = accountingObjects.orEmpty(),
        reserves = reserves.orEmpty(),
        organizationId = organizationId,
        documentStatus = documentStatus,
        documentStatusId = documentStatusId,
        completionDate = completionDate,
        creationDate = creationDate,
        locationFrom = locationFrom,
        locationTo = locationTo,
        departmentFrom = departmentFrom,
        departmentTo = departmentTo,
        branch = branch,
        actionBase = actionBase,
        code = code,
        userInserted = userInserted
    )
}