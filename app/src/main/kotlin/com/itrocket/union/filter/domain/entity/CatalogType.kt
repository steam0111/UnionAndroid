package com.itrocket.union.filter.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
enum class CatalogType : Parcelable {
    ACCOUNTING_OBJECTS,
    EMPLOYEES,
    BRANCHES,
    DEPARTMENTS,
    NOMENCLATURES,
    REGIONS,
    RESERVES,
    DOCUMENTS,
    DEFAULT,
    INVENTORIES,
    IDENTIFIES
}