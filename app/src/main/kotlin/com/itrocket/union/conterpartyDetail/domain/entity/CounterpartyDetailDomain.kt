package com.itrocket.union.conterpartyDetail.domain.entity

import com.itrocket.union.accountingObjects.domain.entity.ObjectInfoDomain

data class CounterpartyDetailDomain(
    val listInfo: List<ObjectInfoDomain> = emptyList()
)