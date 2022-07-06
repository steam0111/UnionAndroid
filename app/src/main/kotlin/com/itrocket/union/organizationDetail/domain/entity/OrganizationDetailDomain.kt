package com.itrocket.union.organizationDetail.domain.entity

import com.itrocket.union.accountingObjects.domain.entity.ObjectInfoDomain

data class OrganizationDetailDomain(
    val listInfo: List<ObjectInfoDomain> = emptyList()
)