package com.itrocket.union.organizations.domain.entity

import com.itrocket.union.R
import com.itrocket.union.common.DefaultItem

data class OrganizationDomain(
    val id: String,
    val name: String,
    val actualAddress: String?,
    val legalAddress: String?,
)

fun OrganizationDomain.toDefaultItem() =
    DefaultItem(
        id = id,
        title = name,
        subtitles = mapOf(
            R.string.organization_actual_address to actualAddress,
            R.string.organization_legal_address to legalAddress
        )
    )