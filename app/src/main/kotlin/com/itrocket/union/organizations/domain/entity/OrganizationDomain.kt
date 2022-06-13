package com.itrocket.union.organizations.domain.entity

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
        subtitles = mapOf("Фактический адрес" to actualAddress, "Юридический адрес" to legalAddress)
    )