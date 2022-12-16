package com.itrocket.union.identify.domain

import com.itrocket.union.R
import com.itrocket.union.accountingObjects.domain.entity.ObjectInfoDomain

data class NomenclatureReserveDomain(
    val nomenclatureId: String,
    val labelTypeId: String?,
    val nomenclature: String,
    val labelType: String?,
    val consignment: String?,
    val count: Int
) {
    fun getListInfo() = listOf(
        ObjectInfoDomain(title = R.string.label_type_title, value = labelType),
        ObjectInfoDomain(title = R.string.consignment_title, value = consignment)
    )
}