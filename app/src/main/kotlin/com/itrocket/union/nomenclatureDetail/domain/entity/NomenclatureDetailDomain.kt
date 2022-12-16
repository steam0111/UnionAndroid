package com.itrocket.union.nomenclatureDetail.domain.entity

import com.itrocket.union.accountingObjects.domain.entity.ObjectInfoDomain

data class NomenclatureDetailDomain(
    val name: String,
    val listInfo: List<ObjectInfoDomain>
)