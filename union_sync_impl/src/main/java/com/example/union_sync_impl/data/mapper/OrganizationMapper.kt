package com.example.union_sync_impl.data.mapper

import com.example.union_sync_api.entity.OrganizationDetailSyncEntity
import com.example.union_sync_api.entity.OrganizationSyncEntity
import com.example.union_sync_impl.entity.FullOrganizationDb
import com.example.union_sync_impl.entity.OrganizationDb
import org.openapitools.client.models.CustomOrganizationDto
import org.openapitools.client.models.OrganizationDtoV2

fun CustomOrganizationDto.toOrganizationDb(): OrganizationDb {
    return OrganizationDb(
        catalogItemName = catalogItemName.orEmpty(),
        name = name.orEmpty(),
        id = id,
        actualAddress = actualAddress,
        legalAddress = legalAddress,
        kpp = kpp,
        inn = inn,
        employeeId = employeeId,
        comment = comment
    )
}

fun OrganizationDtoV2.toOrganizationDb(): OrganizationDb {
    return OrganizationDb(
        catalogItemName = catalogItemName.orEmpty(),
        name = name.orEmpty(),
        id = id,
        actualAddress = actualAddress,
        legalAddress = legalAddress,
        kpp = kpp,
        inn = inn,
        employeeId = employeeId,
        comment = comment
    )
}

fun OrganizationDb.toSyncEntity(): OrganizationSyncEntity {
    return OrganizationSyncEntity(
        id = id,
        catalogItemName = catalogItemName,
        name = name,
        actualAddress = actualAddress,
        legalAddress = legalAddress,
        kpp = kpp,
        inn = inn,
        employeeId = employeeId,
        comment = comment
    )
}

fun FullOrganizationDb.toDetailSyncEntity() = OrganizationDetailSyncEntity(
    organization = organizationDb.toSyncEntity(),
    employee = employeeDb?.toSyncEntity()
)