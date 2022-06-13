package com.itrocket.union.nomenclatureGroup.domain.entity

import com.itrocket.union.common.DefaultItem

data class NomenclatureGroupDomain(val id: String, val name: String = "")

fun NomenclatureGroupDomain.toDefaultItem() =
    DefaultItem(
        id = id,
        title = name
    )