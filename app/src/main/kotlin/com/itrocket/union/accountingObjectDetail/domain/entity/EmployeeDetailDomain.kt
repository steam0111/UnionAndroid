package com.itrocket.union.accountingObjectDetail.domain.entity

import com.itrocket.union.accountingObjects.domain.entity.ObjectInfoDomain

data class EmployeeDetailDomain(
    val listInfo: List<ObjectInfoDomain> = emptyList()
)