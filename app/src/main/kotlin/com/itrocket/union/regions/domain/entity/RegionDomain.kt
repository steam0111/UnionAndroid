package com.itrocket.union.regions.domain.entity

import com.itrocket.union.R
import com.itrocket.union.common.DefaultItem

data class RegionDomain(
    val id: String,
    val catalogItemName: String,
    val name: String,
    val code: String,
)

fun RegionDomain.toDefaultItem() =
    DefaultItem(
        id = id,
        title = name,
        subtitles = mapOf(R.string.region_code to code)
    )