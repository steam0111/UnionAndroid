package com.itrocket.union.organizationDetail.domain.entity

import com.itrocket.union.accountingObjects.domain.entity.ObjectInfoDomain

data class OrganizationDetailDomain(
    val id: String,
    val name: String,
    val listInfo: List<ObjectInfoDomain> = emptyList()
)