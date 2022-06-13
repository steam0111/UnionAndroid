package com.itrocket.union.nomenclature.domain.entity

import com.itrocket.union.common.DefaultItem

data class NomenclatureDomain(val id: String, val name: String = "")

fun NomenclatureDomain.toDefaultItem() =
    DefaultItem(
        id = id,
        title = name
    )