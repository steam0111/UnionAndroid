package com.example.union_sync_impl.data.mapper

import com.example.union_sync_api.entity.BranchSyncEntity
import com.example.union_sync_impl.entity.BranchesDb
import org.openapitools.client.models.Branch

fun Branch.toBranchesDb(): BranchesDb {
    return BranchesDb(
        id = id,
        catalogItemName = catalogItemName.orEmpty(),
        organizationId = organizationId,
        name = name,
        code = code
    )
}

fun BranchesDb.toSyncEntity(): BranchSyncEntity {
    return BranchSyncEntity(
        id = id,
        catalogItemName = catalogItemName,
        organizationId = organizationId,
        name = name,
        code = code
    )
}