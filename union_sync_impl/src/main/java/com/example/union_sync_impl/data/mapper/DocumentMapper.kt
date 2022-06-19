package com.example.union_sync_impl.data.mapper

import com.example.union_sync_api.entity.AccountingObjectSyncEntity
import com.example.union_sync_api.entity.DocumentCreateSyncEntity
import com.example.union_sync_api.entity.DocumentSyncEntity
import com.example.union_sync_api.entity.DocumentUpdateSyncEntity
import com.example.union_sync_api.entity.EmployeeSyncEntity
import com.example.union_sync_api.entity.LocationShortSyncEntity
import com.example.union_sync_api.entity.OrganizationSyncEntity
import com.example.union_sync_impl.entity.DocumentDb

fun DocumentCreateSyncEntity.toDocumentDb(): DocumentDb {
    return DocumentDb(
        organizationId = organizationId,
        molId = molId,
        exploitingId = exploitingId,
        accountingObjectsIds = accountingObjectsIds,
        documentType = documentType,
        date = System.currentTimeMillis(),
        locationIds = locationIds
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
        date = date,
        locationIds = locationIds
    )
}

fun DocumentDb.toDocumentSyncEntity(
    organizationSyncEntity: OrganizationSyncEntity?,
    mol: EmployeeSyncEntity?,
    exploiting: EmployeeSyncEntity?,
    locations: List<LocationShortSyncEntity>?,
    accountingObjects: List<AccountingObjectSyncEntity>
): DocumentSyncEntity {
    return DocumentSyncEntity(
        id = id.toString(),
        date = date,
        organizationSyncEntity = organizationSyncEntity,
        mol = mol,
        exploiting = exploiting,
        documentType = documentType,
        accountingObjects = accountingObjects,
        organizationId = organizationId,
        locations = locations
    )
}