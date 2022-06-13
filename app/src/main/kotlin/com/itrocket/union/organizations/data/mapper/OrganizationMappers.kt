package com.itrocket.union.organizations.data.mapper

import com.example.union_sync_api.entity.OrganizationSyncEntity
import com.itrocket.union.organizations.domain.entity.OrganizationDomain

fun List<OrganizationSyncEntity>.map(): List<OrganizationDomain> = map {
    OrganizationDomain(
        id = it.id,
        name = it.name,
        actualAddress = it.actualAddress,
        legalAddress = it.legalAddress
    )
}