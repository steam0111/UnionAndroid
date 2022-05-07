package com.itrocket.union.reserves.domain.entity

import com.itrocket.union.accountingObjects.domain.entity.ObjectInfoDomain

data class ReservesDomain(
    val id: String,
    val title: String,
    val isBarcode: Boolean,
    val listInfo: List<ObjectInfoDomain>,
    val itemsCount: Int,
    val comment: String = ""
)