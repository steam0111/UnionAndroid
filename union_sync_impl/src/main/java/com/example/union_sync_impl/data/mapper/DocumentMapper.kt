package com.example.union_sync_impl.data.mapper

import android.util.Log
import com.example.union_sync_api.entity.AccountingObjectSyncEntity
import com.example.union_sync_api.entity.DocumentCreateSyncEntity
import com.example.union_sync_api.entity.DocumentSyncEntity
import com.example.union_sync_api.entity.DocumentUpdateSyncEntity
import com.example.union_sync_api.entity.EmployeeSyncEntity
import com.example.union_sync_api.entity.LocationShortSyncEntity
import com.example.union_sync_api.entity.OrganizationSyncEntity
import com.example.union_sync_api.entity.ReserveSyncEntity
import com.example.union_sync_impl.entity.DocumentDb
import org.openapitools.client.models.ActionDtoV2

fun ActionDtoV2.toDocumentDb(): DocumentDb {
    return DocumentDb(
        organizationId = organizationId.orEmpty(),
        molId = molId.orEmpty(),
        exploitingId = exploitingId,
        accountingObjectsIds = listOf(),
        documentType = extendedActionType?.name.orEmpty(),
        locationIds = listOf(locationToId.orEmpty()),
        creationDate = System.currentTimeMillis(),
        documentStatus = extendedActionStatus?.name.orEmpty(),
        documentStatusId = actionStatusId.orEmpty(),
        reservesIds = listOf(),
        objectType = "MAIN_ASSETS",
        updateDate = System.currentTimeMillis()
    )
}

fun DocumentCreateSyncEntity.toDocumentDb(id: String): DocumentDb {
    return DocumentDb(
        id = id,
        organizationId = organizationId,
        molId = molId,
        exploitingId = exploitingId,
        accountingObjectsIds = accountingObjectsIds,
        documentType = documentType,
        locationIds = locationIds,
        creationDate = System.currentTimeMillis(),
        documentStatus = documentStatus,
        documentStatusId = documentStatusId,
        reservesIds = reservesIds,
        objectType = objectType,
        updateDate = System.currentTimeMillis()
    )
}

fun DocumentUpdateSyncEntity.toDocumentDb(): DocumentDb {
    return DocumentDb(
        id = id,
        organizationId = organizationId,
        molId = molId,
        exploitingId = exploitingId,
        accountingObjectsIds = accountingObjectsIds,
        documentType = documentType,
        locationIds = locationIds,
        documentStatusId = documentStatusId,
        documentStatus = documentStatus,
        creationDate = creationDate,
        completionDate = completionDate,
        objectType = objectType,
        reservesIds = reservesIds,
        updateDate = System.currentTimeMillis()
    )
}

fun DocumentDb.toDocumentSyncEntity(
    organizationSyncEntity: OrganizationSyncEntity?,
    mol: EmployeeSyncEntity?,
    exploiting: EmployeeSyncEntity?,
    locations: List<LocationShortSyncEntity>?,
    accountingObjects: List<AccountingObjectSyncEntity>? = null,
    reserves: List<ReserveSyncEntity>? = null
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
        locations = locations,
        documentStatus = documentStatus,
        documentStatusId = documentStatusId,
        completionDate = completionDate,
        creationDate = creationDate,
        objectType = objectType
    )
}