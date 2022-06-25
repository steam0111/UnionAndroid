package com.itrocket.union.branchDetail.domain.entity

import com.itrocket.union.accountingObjects.domain.entity.ObjectInfoDomain

data class BranchDetailDomain(
    val listInfo: List<ObjectInfoDomain> = emptyList()
)