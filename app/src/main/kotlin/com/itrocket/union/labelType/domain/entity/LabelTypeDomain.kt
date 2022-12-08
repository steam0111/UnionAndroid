package com.itrocket.union.labelType.domain.entity

import com.itrocket.union.R
import com.itrocket.union.common.DefaultItem

data class LabelTypeDomain(
    val id: String,
    val catalogItemName: String,
    val name: String,
    val code: String,
    val description: String?
)

fun LabelTypeDomain.toDefaultItem(): DefaultItem {
    val subtitles = mutableMapOf<Int, String>()
    subtitles[R.string.equipment_type_code] = code

    description?.let {
        subtitles[R.string.label_type_description] = it
    }
    return DefaultItem(
        id = id,
        title = name,
        subtitles = subtitles
    )
}