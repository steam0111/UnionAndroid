package com.example.union_sync_impl.data.mapper

import com.example.union_sync_api.entity.OrganizationSyncEntity
import com.example.union_sync_impl.entity.OrganizationDb
import org.openapitools.client.models.CustomOrganizationDto

fun CustomOrganizationDto.toOrganizationDb(): OrganizationDb {
    return OrganizationDb(
        catalogItemName = catalogItemName.orEmpty(),
        name = name.orEmpty(),
        id = id,
        actualAddress = actualAddress,
        legalAddress = legalAddress
    )
}

fun OrganizationDb.toSyncEntity(): OrganizationSyncEntity {
    return OrganizationSyncEntity(
        id = id,
        catalogItemName = catalogItemName,
        name = name,
        actualAddress = actualAddress,
        legalAddress = legalAddress
    )
}