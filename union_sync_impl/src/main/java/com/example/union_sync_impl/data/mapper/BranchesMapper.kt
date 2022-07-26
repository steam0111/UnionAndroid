package com.example.union_sync_impl.data.mapper

import com.example.union_sync_api.entity.BranchDetailSyncEntity
import com.example.union_sync_api.entity.BranchSyncEntity
import com.example.union_sync_impl.entity.BranchesDb
import com.example.union_sync_impl.entity.FullBranchDb
import org.openapitools.client.models.Branch
import org.openapitools.client.models.BranchDtoV2

fun BranchDtoV2.toBranchesDb(): BranchesDb {
    return BranchesDb(
        id = id,
        catalogItemName = catalogItemName.orEmpty(),
        organizationId = organizationId,
        name = name,
        code = code,
        updateDate = System.currentTimeMillis()
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

fun FullBranchDb.toDetailSyncEntity() = BranchDetailSyncEntity(
    branch = branch.toSyncEntity(),
    organization = organization.toSyncEntity()
)