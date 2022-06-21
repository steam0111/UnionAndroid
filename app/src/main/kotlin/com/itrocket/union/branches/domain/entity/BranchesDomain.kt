package com.itrocket.union.branches.domain.entity

import com.itrocket.union.R
import com.itrocket.union.common.DefaultItem

data class BranchesDomain(
    val id: String,
    val catalogItemName: String,
    val name: String,
    val code: String,
)

fun BranchesDomain.toDefaultItem() =
    DefaultItem(
        id = id,
        title = name,
        subtitles = mapOf(R.string.branches_code to code)
    )