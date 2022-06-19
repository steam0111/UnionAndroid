package com.itrocket.union.equipmentTypes.domain.entity

import com.itrocket.union.R
import com.itrocket.union.common.DefaultItem

data class EquipmentTypesDomain(
    val id: String,
    val catalogItemName: String,
    val name: String,
    val code: String
)

fun EquipmentTypesDomain.toDefaultItem() =
    DefaultItem(
        id = id,
        title = name,
        subtitles = mapOf(R.string.equipment_type_code to code)
    )